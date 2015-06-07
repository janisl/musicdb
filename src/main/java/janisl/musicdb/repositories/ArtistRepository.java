package janisl.musicdb.repositories;

import janisl.musicdb.models.Artist;
import java.util.List;

public interface ArtistRepository {
    
    public List<Artist> getAll();

    public Artist get( Integer id );

    public List<Artist> getByName(String name);

    public List<Artist> getByBeatportId(Integer beatportId);

    public void add(Artist artist);
    
    public void update(Artist artist);
    
    public void delete(Artist artist);
    
}
