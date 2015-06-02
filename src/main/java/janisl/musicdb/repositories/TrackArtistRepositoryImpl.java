package janisl.musicdb.repositories;

import janisl.musicdb.models.TrackArtist;

public class TrackArtistRepositoryImpl implements TrackArtistRepository {
    
    private final UnitOfWorkImpl unitOfWork;

    public TrackArtistRepositoryImpl(UnitOfWorkImpl unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    @Override
    public void delete(TrackArtist trackArtist) {
        unitOfWork.getSession().delete(trackArtist);
    }
    
}
