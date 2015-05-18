package janisl.musicdb.repositories;

import janisl.musicdb.models.Track;
import java.util.List;

public class TrackRepositoryImpl implements TrackRepository {
    
    private final UnitOfWorkImpl unitOfWork;

    public TrackRepositoryImpl(UnitOfWorkImpl unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    @Override
    public List<Track> getAll() {
        return (List<Track>) (unitOfWork.getSession().createCriteria(Track.class).list());
    }

    @Override
    public Track get(Integer id) {
        return (Track) unitOfWork.getSession().get(Track.class, id);
    }

    @Override
    public void add(Track track) {
        unitOfWork.getSession().save(track);
    }

    @Override
    public void update(Track track) {
        unitOfWork.getSession().save(track);
    }

    @Override
    public void delete(Track track) {
        unitOfWork.getSession().delete(track);
    }
    
}
