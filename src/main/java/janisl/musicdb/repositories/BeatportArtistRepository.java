package janisl.musicdb.repositories;

import janisl.musicdb.models.BeatportArtist;
import java.util.List;

public interface BeatportArtistRepository {
    
    public List<BeatportArtist> getAll();

    public BeatportArtist get( Integer id );

    public void add(BeatportArtist artist);
    
    public void update(BeatportArtist artist);
    
    public void delete(BeatportArtist artist);
    
}
