package janisl.musicdb.repositories;

import janisl.musicdb.models.BeatportTrack;
import java.util.List;
import org.hibernate.Criteria;

public class BeatportTrackRepositoryImpl implements BeatportTrackRepository {
    
    private final UnitOfWorkImpl unitOfWork;

    public BeatportTrackRepositoryImpl(UnitOfWorkImpl unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    @Override
    public List<BeatportTrack> getAll() {
        return (List<BeatportTrack>) (unitOfWork.getSession().createCriteria(BeatportTrack.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list());
    }

    @Override
    public BeatportTrack get(Integer id) {
        return (BeatportTrack) unitOfWork.getSession().get(BeatportTrack.class, id);
    }

    @Override
    public void add(BeatportTrack track) {
        unitOfWork.getSession().save(track);
    }

    @Override
    public void update(BeatportTrack track) {
        unitOfWork.getSession().save(track);
    }

    @Override
    public void delete(BeatportTrack track) {
        unitOfWork.getSession().delete(track);
    }
    
}
