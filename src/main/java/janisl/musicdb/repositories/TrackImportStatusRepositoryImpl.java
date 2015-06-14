package janisl.musicdb.repositories;

import janisl.musicdb.models.TrackImportStatus;
import java.util.List;

public class TrackImportStatusRepositoryImpl implements TrackImportStatusRepository {
    
    private final UnitOfWorkImpl unitOfWork;

    public TrackImportStatusRepositoryImpl(UnitOfWorkImpl unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    @Override
    public List<TrackImportStatus> getAll() {
        return (List<TrackImportStatus>) (unitOfWork.getSession().createCriteria(TrackImportStatus.class).list());
    }

    @Override
    public TrackImportStatus get(Integer id) {
        return (TrackImportStatus) unitOfWork.getSession().get(TrackImportStatus.class, id);
    }

    @Override
    public void add(TrackImportStatus status) {
        unitOfWork.getSession().save(status);
    }

    @Override
    public void update(TrackImportStatus status) {
        unitOfWork.getSession().save(status);
    }

    @Override
    public void delete(TrackImportStatus status) {
        unitOfWork.getSession().delete(status);
    }

}
