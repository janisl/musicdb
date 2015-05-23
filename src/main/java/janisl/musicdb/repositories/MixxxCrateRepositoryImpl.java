package janisl.musicdb.repositories;

import janisl.musicdb.models.MixxxCrate;
import java.util.List;

public class MixxxCrateRepositoryImpl implements MixxxCrateRepository {
    
    private final UnitOfWorkImpl unitOfWork;

    public MixxxCrateRepositoryImpl(UnitOfWorkImpl unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    @Override
    public List<MixxxCrate> getAll() {
        return (List<MixxxCrate>) (unitOfWork.getMixxxSession().createCriteria(MixxxCrate.class).list());
    }

    @Override
    public MixxxCrate get(Integer id) {
        return (MixxxCrate) unitOfWork.getMixxxSession().get(MixxxCrate.class, id);
    }
    
}
