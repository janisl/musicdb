package janisl.musicdb.repositories;

import janisl.musicdb.models.MixxxPlaylistTrack;
import java.util.List;

public interface MixxxPlaylistTrackRepository {
    
    public List<MixxxPlaylistTrack> getByPlaylistId(Integer playlistId);

}
