package janisl.musicdb.beatport;

import janisl.musicdb.models.BeatportEntity;
import janisl.musicdb.models.BeatportGenre;
import janisl.musicdb.repositories.UnitOfWork;

public class BeatportUtils {

    public static String ParseBeatportUrl(String url, BeatportEntity entity) {
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
        if (!"genre".equals(BeatportUtils.ParseBeatportUrl(url, genre))) {
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
}
