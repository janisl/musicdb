package janisl.musicdb.repositories;

import janisl.musicdb.models.BeatportGenre;
import java.util.List;

public interface BeatportGenreRepository {
    
    public List<BeatportGenre> getAll();

    public BeatportGenre get( Integer id );

    public void add(BeatportGenre genre);
    
    public void update(BeatportGenre genre);
    
    public void delete(BeatportGenre genre);
    
}
