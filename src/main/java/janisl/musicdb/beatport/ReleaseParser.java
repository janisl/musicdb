package janisl.musicdb.beatport;

import janisl.musicdb.models.BeatportLabel;
import janisl.musicdb.models.BeatportRelease;
import janisl.musicdb.repositories.UnitOfWork;
import java.io.IOException;
import java.sql.Date;
import org.jsoup.Jsoup;
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
            save();
        }
        return release;
    }

    public void reimport(BeatportRelease release) throws IOException {
        this.release = release;
        downloadAndParseBeatportPage();
        save();
    }

    private void createFromUrl(String url) throws BeatportInvalidPathException {
        release = new BeatportRelease();
        if (!"release".equals(BeatportUtils.ParseBeatportUrl(url, release))) {
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
    }

    private void downloadPage() throws IOException {
        doc = Jsoup.connect("https://pro.beatport.com/release/" + release.getSlug() + "/" + release.getId())
                .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.90 Safari/537.36")
                .timeout(15000)
                .get();
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
                    release.setLabelId(label.getId());
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
