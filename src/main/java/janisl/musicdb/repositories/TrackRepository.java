package janisl.musicdb.repositories;

import janisl.musicdb.models.Track;
import java.util.List;

public interface TrackRepository {
    
    public List<Track> getAll();

    public Track get( Integer id );

    public void add(Track track);
    
    public void update(Track track);
    
    public void delete(Track track);
    
}
