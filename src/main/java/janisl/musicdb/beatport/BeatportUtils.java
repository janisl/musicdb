package janisl.musicdb.beatport;

import janisl.musicdb.models.BeatportEntity;

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
    
}
