package janisl.musicdb.repositories;

import janisl.musicdb.models.MixxxPlaylistTrack;
import java.util.List;
import org.hibernate.criterion.Restrictions;

public class MixxxPlaylistTrackRepositoryImpl implements MixxxPlaylistTrackRepository {
    
    private final UnitOfWorkImpl unitOfWork;

    public MixxxPlaylistTrackRepositoryImpl(UnitOfWorkImpl unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    @Override
    public List<MixxxPlaylistTrack> getByPlaylistId(Integer playlistId) {
        return (List<MixxxPlaylistTrack>) (unitOfWork.getMixxxSession().createCriteria(MixxxPlaylistTrack.class).add(Restrictions.eq("playlistId", playlistId)).list());
    }

}
