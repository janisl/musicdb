package janisl.musicdb.beatport;

import janisl.musicdb.models.BeatportArtist;
import janisl.musicdb.repositories.UnitOfWork;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class ArtistParser {

    private final UnitOfWork unitOfWork;
    private BeatportArtist artist;
    private Boolean isExisting = false;

    public ArtistParser(UnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    public BeatportArtist parseUrl(String url) throws IOException {
        createFromUrl(url);
        if (checkForExisting()) {
            downloadAndParseBeatportPage();
            save();
        }
        return artist;
    }

    public void reimport(BeatportArtist artist) throws IOException {
        this.artist = artist;
        downloadAndParseBeatportPage();
        save();
    }
    
    private void createFromUrl(String url) throws BeatportInvalidPathException {
        artist = new BeatportArtist();
        if (!"artist".equals(BeatportUtils.ParseBeatportUrl(url, artist))) {
            throw new BeatportInvalidPathException();
        }
    }

    private Boolean checkForExisting() {
        BeatportArtist existingArtist = unitOfWork.getBeatportArtistRepository().get(artist.getId());
        if (existingArtist == null) {
            return true;
        }
        Boolean needsRefresh = (existingArtist.getSlug() == null ? artist.getSlug() != null : !existingArtist.getSlug().equals(artist.getSlug()));
        existingArtist.setSlug(artist.getSlug());
        artist = existingArtist;
        isExisting = true;
        return needsRefresh;
    }

    private void downloadAndParseBeatportPage() throws IOException {
        Document doc = Jsoup.connect("https://pro.beatport.com/artist/" + artist.getSlug() + "/" + artist.getId())
                .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.90 Safari/537.36")
                .timeout(15000)
                .get();
        Element mainElement = doc.select("main").first();
        artist.setName(mainElement.select("h1").first().text());
        artist.setImageUrl(mainElement.select("img.interior-artist-artwork").first().attr("src"));
    }

    private void save() {
        if (isExisting) {
            unitOfWork.getBeatportArtistRepository().update(artist);
        } else {
            unitOfWork.getBeatportArtistRepository().add(artist);
        }
    }
}
