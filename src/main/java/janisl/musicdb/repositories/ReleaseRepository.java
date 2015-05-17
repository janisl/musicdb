package janisl.musicdb.repositories;

import janisl.musicdb.models.Release;
import java.util.List;

public interface ReleaseRepository {
    
    public List<Release> getAll();

    public Release get( Integer id );

    public void add(Release release);
    
    public void update(Release release);
    
    public void delete(Release release);
    
}
