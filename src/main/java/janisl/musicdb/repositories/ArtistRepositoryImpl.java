package janisl.musicdb.repositories;

import janisl.musicdb.models.Artist;
import java.util.List;

public class ArtistRepositoryImpl implements ArtistRepository {
    
    private final UnitOfWorkImpl unitOfWork;

    public ArtistRepositoryImpl(UnitOfWorkImpl unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    @Override
    public List<Artist> getAll() {
        return (List<Artist>) (unitOfWork.getSession().createCriteria(Artist.class).list());
    }

    @Override
    public Artist get(Integer id) {
        return (Artist) unitOfWork.getSession().get(Artist.class, id);
    }

    @Override
    public void add(Artist artist) {
        unitOfWork.getSession().save(artist);
    }

    @Override
    public void update(Artist artist) {
        unitOfWork.getSession().save(artist);
    }

    @Override
    public void delete(Artist artist) {
        unitOfWork.getSession().delete(artist);
    }
    
}
