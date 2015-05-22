package janisl.musicdb.repositories;

import janisl.musicdb.models.MixxxTrackCue;
import java.util.List;
import org.hibernate.criterion.Restrictions;

public class MixxxTrackCueRepositoryImpl implements MixxxTrackCueRepository {
    
    private final UnitOfWorkImpl unitOfWork;

    public MixxxTrackCueRepositoryImpl(UnitOfWorkImpl unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    @Override
    public List<MixxxTrackCue> getAll() {
        return (List<MixxxTrackCue>) (unitOfWork.getMixxxSession().createCriteria(MixxxTrackCue.class).list());
    }

    @Override
    public List<MixxxTrackCue> getByTrackId(Integer trackId) {
        return (List<MixxxTrackCue>) (unitOfWork.getMixxxSession().createCriteria(MixxxTrackCue.class).add(Restrictions.eq("trackId", trackId)).list());
    }

    @Override
    public MixxxTrackCue get(Integer id) {
        return (MixxxTrackCue) unitOfWork.getMixxxSession().get(MixxxTrackCue.class, id);
    }
    
}
