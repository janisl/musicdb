package janisl.musicdb.repositories;

import janisl.musicdb.models.Artist;
import java.util.List;
import org.hibernate.criterion.Restrictions;

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
    public List<Artist> getByName(String name) {
        return (List<Artist>) (unitOfWork.getSession().createCriteria(Artist.class).add(Restrictions.eq("name", name)).list());
    }

    @Override
    public List<Artist> getByBeatportId(Integer beatportId) {
        return (List<Artist>) (unitOfWork.getSession().createCriteria(Artist.class).add(Restrictions.eq("beatportId", beatportId)).list());
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
