package janisl.musicdb.repositories;

import janisl.musicdb.models.Key;
import java.util.List;

public interface KeyRepository {

    public List<Key> getAll();

    public Key get( Integer id );
    
}
