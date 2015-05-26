package janisl.musicdb.beatport;

import janisl.musicdb.models.BeatportArtist;
import janisl.musicdb.models.BeatportLabel;
import janisl.musicdb.models.BeatportRelease;
import janisl.musicdb.models.BeatportTrack;
import janisl.musicdb.repositories.UnitOfWork;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.HashSet;
import java.util.Set;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

public class ReleaseParser {

    private final UnitOfWork unitOfWork;
    private BeatportRelease release;
    private Boolean isExisting = false;
    private Element mainElement;
    private Document doc;

    public ReleaseParser(UnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    public BeatportRelease parseUrl(String url) throws IOException {
        createFromUrl(url);
        if (checkForExisting()) {
            downloadAndParseBeatportPage();
        }
        return release;
    }

    public void reimport(BeatportRelease release) throws IOException {
        this.release = release;
        downloadAndParseBeatportPage();
    }

    private void createFromUrl(String url) throws BeatportInvalidPathException {
        release = new BeatportRelease();
        if (!"release".equals(BeatportUtils.parseBeatportUrl(url, release))) {
            throw new BeatportInvalidPathException();
        }
    }

    private Boolean checkForExisting() {
        BeatportRelease existingRelease = unitOfWork.getBeatportReleaseRepository().get(release.getId());
        if (existingRelease == null) {
            return true;
        }
        Boolean needsRefresh = (existingRelease.getSlug() == null ? release.getSlug() != null : !existingRelease.getSlug().equals(release.getSlug()));
        existingRelease.setSlug(release.getSlug());
        release = existingRelease;
        isExisting = true;
        return needsRefresh;
    }

    private void downloadAndParseBeatportPage() throws IOException {
        downloadPage();
        parseMainInfo();
        save();
        parseTracks();
    }

    private void downloadPage() throws IOException {
        doc = BeatportUtils.downloadPage("release", release);
        mainElement = doc.select("main").first();
    }

    private void parseMainInfo() throws IOException {
        release.setTitle(mainElement.select("h1").first().text());
        release.setImageUrl(mainElement.select("img.interior-release-chart-artwork").first().attr("src"));
        release.setIsExclusive(!mainElement.select("span.exclusive-marker").isEmpty());

        Elements infoElements = mainElement.select("li.interior-release-chart-content-item");
        for (Element infoElement : infoElements) {
            Element categorySpan = infoElement.select("span.category").first();
            if (categorySpan == null) {
                continue;
            }

            String category = categorySpan.text();
            Element valueSpan = infoElement.select("span.value").first();
            switch (category) {
                case "Release Date":
                    release.setReleaseDate(Date.valueOf(valueSpan.text()));
                    break;
                case "Labels":
                    Element link = valueSpan.select("a").first();
                    BeatportLabel label = new LabelParser(unitOfWork).parseUrl(link.attr("href"));
                    release.setLabel(label);
                    break;
                case "Catalog":
                    release.setCatalogNumber(valueSpan.text());
                    break;
                case "Description":
                    // Go throug nodes to properly replace <br> with newlines.
                    String description = "";
                    for (Node descrNode : valueSpan.childNodes()) {
                        if (descrNode instanceof Element) {
                            if ("br".equals(((Element) descrNode).tagName())) {
                                description += "\n";
                            } else {
                                description += ((Element) descrNode).text();
                            }
                        } else if (descrNode instanceof TextNode) {
                            description += ((TextNode) descrNode).text();
                        }
                    }
                    release.setDescription(description);
                    break;
                case "Artists":
                    Set<BeatportArtist> artists = new HashSet<>(0);
                    for (Element artistElement : valueSpan.select("a")) {
                        BeatportArtist artist = new ArtistParser(unitOfWork).parseUrl(artistElement.attr("href"));
                        if (!artists.contains(artist))
                            artists.add(artist);
                    }
                    release.setArtists(artists);
            }
        }
    }
    
    private void parseTracks() throws IOException {
        Elements trackElements = mainElement.select("li.track");
        for (Element trackElement : trackElements) {
            Element titleElement = trackElement.select("p.buk-track-title").first();
            Element trackLink = titleElement.select("a").first();

            BeatportTrack track = new BeatportTrack();
            if (!"track".equals(BeatportUtils.parseBeatportUrl(trackLink.attr("href"), track))) {
                throw new BeatportInvalidPathException();
            }
            
            BeatportTrack existingTrack = unitOfWork.getBeatportTrackRepository().get(track.getId());
            if (existingTrack != null) {
                existingTrack.setSlug(track.getSlug());
                track = existingTrack;
            }
            
            track.setRelease(release);
            
            Element orderNumberElement = trackElement.select("div.buk-track-num").first();
            track.setOrderNumber(Integer.parseInt(orderNumberElement.text()));
            
            Element titleSpan = trackLink.select("span.buk-track-primary-title").first();
            track.setTitle(titleSpan.text());
            Element remixedSpan = trackLink.select("span.buk-track-remixed").first();
            track.setRemix(remixedSpan.text());
            
            Elements artistElements = trackElement.select("p.buk-track-artists").first().select("a");
            Set<BeatportArtist> artists = new HashSet<>(0);
            for (Element artistElement : artistElements) {
                artists.add(new ArtistParser(unitOfWork).parseUrl(artistElement.attr("href")));
            }
            track.setArtists(artists);
            
            Elements remixerElements = trackElement.select("p.buk-track-remixers").first().select("a");
            Set<BeatportArtist> remixers = new HashSet<>(0);
            for (Element remixerElement : remixerElements) {
                remixers.add(new ArtistParser(unitOfWork).parseUrl(remixerElement.attr("href")));
            }
            track.setRemixers(remixers);

            Element genreElement = trackElement.select("p.buk-track-genre").select("a").first();
            track.setGenre(BeatportUtils.resolveGenre(unitOfWork, genreElement.attr("href"), genreElement.text()));

            Element bpmElement = trackElement.select("p.buk-track-bpm").first();
            if (!"".equals(bpmElement.text())) {
                track.setBpm(Integer.parseInt(bpmElement.text()));
            }
            
            Element keyElement = trackElement.select("p.buk-track-key").first();
            if (!"".equals(keyElement.text())) {
                track.setKeytone(keyElement.text());
            }
            
            Element durationElement = trackElement.select("p.buk-track-length").first();
            if (!"".equals(durationElement.text())) {
                String[] parts = durationElement.text().split(":");
                int hour, min, sec;
                if (parts.length == 3) {
                    hour = Integer.parseInt(parts[0]);
                    min = Integer.parseInt(parts[1]);
                    sec = Integer.parseInt(parts[2]);
                } else {
                    hour = 0;
                    min = Integer.parseInt(parts[0]);
                    sec = Integer.parseInt(parts[1]);
                }
                track.setDuration(new Time(hour, min, sec));
            }
            
            if (existingTrack != null) {
                unitOfWork.getBeatportTrackRepository().update(track);
            } else {
                unitOfWork.getBeatportTrackRepository().add(track);
            }
        }
    }

    private void save() {
        if (isExisting) {
            unitOfWork.getBeatportReleaseRepository().update(release);
        } else {
            unitOfWork.getBeatportReleaseRepository().add(release);
        }
    }
}
