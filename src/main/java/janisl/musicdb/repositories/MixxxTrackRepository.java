package janisl.musicdb.repositories;

import janisl.musicdb.models.MixxxTrack;
import java.util.List;

public interface MixxxTrackRepository {
    
    public List<MixxxTrack> getAll(Integer mixxxDeleted);

    public MixxxTrack get( Integer id );

    public void update(MixxxTrack track);
    
}
