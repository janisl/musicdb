package janisl.musicdb.repositories;

import janisl.musicdb.models.Label;
import java.util.List;

public interface LabelRepository {

    public List<Label> getAll();

    public Label get( Integer id );

    public void add(Label label);
    
    public void update(Label label);
    
    public void delete(Label label);
    
}
