package janisl.musicdb.repositories;

import janisl.musicdb.models.BeatportLabel;
import java.util.List;

public interface BeatportLabelRepository {
    
    public List<BeatportLabel> getAll();

    public BeatportLabel get( Integer id );

    public void add(BeatportLabel label);
    
    public void update(BeatportLabel label);
    
    public void delete(BeatportLabel label);
    
}
