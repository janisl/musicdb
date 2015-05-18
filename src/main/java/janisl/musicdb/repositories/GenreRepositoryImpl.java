package janisl.musicdb.repositories;

import janisl.musicdb.models.Genre;
import java.util.List;

public class GenreRepositoryImpl implements GenreRepository {
    
    private final UnitOfWorkImpl unitOfWork;

    public GenreRepositoryImpl(UnitOfWorkImpl unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    @Override
    public List<Genre> getAll() {
        return (List<Genre>) (unitOfWork.getSession().createCriteria(Genre.class).list());
    }

    @Override
    public Genre get(Integer id) {
        return (Genre) unitOfWork.getSession().get(Genre.class, id);
    }

    @Override
    public void add(Genre genre) {
        unitOfWork.getSession().save(genre);
    }

    @Override
    public void update(Genre genre) {
        unitOfWork.getSession().save(genre);
    }

    @Override
    public void delete(Genre genre) {
        unitOfWork.getSession().delete(genre);
    }
    
}
