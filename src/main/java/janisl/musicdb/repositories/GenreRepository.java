package janisl.musicdb.repositories;

import janisl.musicdb.models.Genre;
import java.util.List;

public interface GenreRepository {
    
    public List<Genre> getAll();

    public Genre get( Integer id );

    public List<Genre> getByName(String name);

    public void add(Genre genre);
    
    public void update(Genre genre);
    
    public void delete(Genre genre);
    
}
