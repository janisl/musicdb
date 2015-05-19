package janisl.musicdb.repositories;

import janisl.musicdb.models.BeatportArtist;
import java.util.List;

public class BeatportArtistRepositoryImpl implements BeatportArtistRepository {
    
    private final UnitOfWorkImpl unitOfWork;

    public BeatportArtistRepositoryImpl(UnitOfWorkImpl unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    @Override
    public List<BeatportArtist> getAll() {
        return (List<BeatportArtist>) (unitOfWork.getSession().createCriteria(BeatportArtist.class).list());
    }

    @Override
    public BeatportArtist get(Integer id) {
        return (BeatportArtist) unitOfWork.getSession().get(BeatportArtist.class, id);
    }

    @Override
    public void add(BeatportArtist artist) {
        unitOfWork.getSession().save(artist);
    }

    @Override
    public void update(BeatportArtist artist) {
        unitOfWork.getSession().save(artist);
    }

    @Override
    public void delete(BeatportArtist artist) {
        unitOfWork.getSession().delete(artist);
    }
    
}
