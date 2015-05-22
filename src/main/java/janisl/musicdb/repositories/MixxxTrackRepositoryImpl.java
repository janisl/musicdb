package janisl.musicdb.repositories;

import janisl.musicdb.models.MixxxTrack;
import java.util.List;

public class MixxxTrackRepositoryImpl implements MixxxTrackRepository {
    
    private final UnitOfWorkImpl unitOfWork;

    public MixxxTrackRepositoryImpl(UnitOfWorkImpl unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    @Override
    public List<MixxxTrack> getAll() {
        return (List<MixxxTrack>) (unitOfWork.getMixxxSession().createCriteria(MixxxTrack.class).list());
    }

    @Override
    public MixxxTrack get(Integer id) {
        return (MixxxTrack) unitOfWork.getMixxxSession().get(MixxxTrack.class, id);
    }

    @Override
    public void update(MixxxTrack track) {
        unitOfWork.getMixxxSession().save(track);
    }
    
}
