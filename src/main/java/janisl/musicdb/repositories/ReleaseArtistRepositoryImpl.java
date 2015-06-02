package janisl.musicdb.repositories;

import janisl.musicdb.models.ReleaseDetailsArtist;

public class ReleaseArtistRepositoryImpl implements ReleaseArtistRepository {
    
    private final UnitOfWorkImpl unitOfWork;

    public ReleaseArtistRepositoryImpl(UnitOfWorkImpl unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    @Override
    public void delete(ReleaseDetailsArtist releaseArtist) {
        unitOfWork.getSession().delete(releaseArtist);
    }
    
}
