package janisl.musicdb.repositories;

import janisl.musicdb.models.MixxxPlaylist;
import java.util.List;

public interface MixxxPlaylistRepository {
    
    public List<MixxxPlaylist> getAll();

    public MixxxPlaylist get(Integer id);

    public void update(MixxxPlaylist playlist);
    
}
