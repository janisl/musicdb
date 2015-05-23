package janisl.musicdb.repositories;

import janisl.musicdb.models.MixxxPlaylist;
import java.util.List;

public class MixxxPlaylistRepositoryImpl implements MixxxPlaylistRepository {
    
    private final UnitOfWorkImpl unitOfWork;

    public MixxxPlaylistRepositoryImpl(UnitOfWorkImpl unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    @Override
    public List<MixxxPlaylist> getAll() {
        return (List<MixxxPlaylist>) (unitOfWork.getMixxxSession().createCriteria(MixxxPlaylist.class).list());
    }

    @Override
    public MixxxPlaylist get(Integer id) {
        return (MixxxPlaylist) unitOfWork.getMixxxSession().get(MixxxPlaylist.class, id);
    }

    @Override
    public void update(MixxxPlaylist playlist) {
        unitOfWork.getMixxxSession().save(playlist);
    }
    
}
