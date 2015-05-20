package janisl.musicdb.repositories;

import janisl.musicdb.models.BeatportRelease;
import java.util.List;

public interface BeatportReleaseRepository {
    
    public List<BeatportRelease> getAll();

    public BeatportRelease get( Integer id );

    public void add(BeatportRelease release);
    
    public void update(BeatportRelease release);
    
    public void delete(BeatportRelease release);
    
}
