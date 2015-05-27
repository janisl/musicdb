package janisl.musicdb.beatport;

import janisl.musicdb.models.BeatportEntity;
import janisl.musicdb.models.BeatportGenre;
import janisl.musicdb.repositories.UnitOfWork;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class BeatportUtils {

    public static Document downloadPage(String type, BeatportEntity item) throws IOException {
        return downloadPage(type, item, "");
    }
    
    public static Document downloadPage(String type, BeatportEntity item, String suffix) throws IOException {
        return downloadPage("/" + type + "/" + item.getSlug() + "/" + item.getId() + suffix);
    }

    public static Document downloadPage(String url) throws IOException {
        return Jsoup.connect("https://pro.beatport.com" + url)
                .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.90 Safari/537.36")
                .timeout(15000)
                .get();
    }

    public static String parseBeatportUrl(String url, BeatportEntity entity) {
        if (url.startsWith("http://pro.beatport.com"))
            url = url.substring(23);
        if (url.startsWith("https://pro.beatport.com"))
            url = url.substring(24);
        String[] parts = url.split("/");
        if (parts.length != 4)
            throw new BeatportInvalidPathException();
        entity.setSlug(parts[2]);
        entity.setId(Integer.parseInt(parts[3]));
        return parts[1];
    }

    public static BeatportGenre resolveGenre(UnitOfWork unitOfWork, String url, String name) {
        BeatportGenre genre = new BeatportGenre();
        if (!"genre".equals(BeatportUtils.parseBeatportUrl(url, genre))) {
            throw new BeatportInvalidPathException();
        }
        BeatportGenre existingGenre = unitOfWork.getBeatportGenreRepository().get(genre.getId());
        if (existingGenre == null) {
            genre.setName(name);
            unitOfWork.getBeatportGenreRepository().add(genre);
            return genre;
        } else {
            if (!existingGenre.getSlug().equals(genre.getSlug()) || !existingGenre.getName().equals(name)) {
                existingGenre.setSlug(genre.getSlug());
                existingGenre.setName(name);
                unitOfWork.getBeatportGenreRepository().update(existingGenre);
            }
            return existingGenre;
        }
    }

    public static void parseReleasesPage(Document doc, UnitOfWork unitOfWork) throws IOException {
        Element mainElement = doc.select("main").first();
        
        Elements releaseElements = mainElement.select("div.horz-release-artwork-parent");
        for (Element releaseElement : releaseElements) {
            Element releaseLink = releaseElement.select("a").first();
            new ReleaseParser(unitOfWork).parseUrl(releaseLink.attr("href"));
            unitOfWork.commit();
        }
        
        // Next page
        Element nextPageElement = mainElement.select("a.pag-next").first();
        if (nextPageElement != null) {
            parseReleasesPage(downloadPage(nextPageElement.attr("href")), unitOfWork);
        }
    }
}
