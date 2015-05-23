package janisl.musicdb.repositories;

import janisl.musicdb.models.MixxxCrate;
import java.util.List;

public interface MixxxCrateRepository {

    public List<MixxxCrate> getAll();

    public MixxxCrate get(Integer id);

}
