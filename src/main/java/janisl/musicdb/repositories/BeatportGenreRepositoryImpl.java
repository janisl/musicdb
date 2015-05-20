package janisl.musicdb.repositories;

import janisl.musicdb.models.BeatportGenre;
import java.util.List;

public class BeatportGenreRepositoryImpl implements BeatportGenreRepository {
    
    private final UnitOfWorkImpl unitOfWork;

    public BeatportGenreRepositoryImpl(UnitOfWorkImpl unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    @Override
    public List<BeatportGenre> getAll() {
        return (List<BeatportGenre>) (unitOfWork.getSession().createCriteria(BeatportGenre.class).list());
    }

    @Override
    public BeatportGenre get(Integer id) {
        return (BeatportGenre) unitOfWork.getSession().get(BeatportGenre.class, id);
    }

    @Override
    public void add(BeatportGenre genre) {
        unitOfWork.getSession().save(genre);
    }

    @Override
    public void update(BeatportGenre genre) {
        unitOfWork.getSession().save(genre);
    }

    @Override
    public void delete(BeatportGenre genre) {
        unitOfWork.getSession().delete(genre);
    }
    
}
