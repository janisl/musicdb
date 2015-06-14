package janisl.musicdb.repositories;

import janisl.musicdb.models.ReleaseImportStatus;
import java.util.List;

public class ReleaseImportStatusRepositoryImpl implements ReleaseImportStatusRepository {
    
    private final UnitOfWorkImpl unitOfWork;

    public ReleaseImportStatusRepositoryImpl(UnitOfWorkImpl unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    @Override
    public List<ReleaseImportStatus> getAll() {
        return (List<ReleaseImportStatus>) (unitOfWork.getSession().createCriteria(ReleaseImportStatus.class).list());
    }

    @Override
    public ReleaseImportStatus get(Integer id) {
        return (ReleaseImportStatus) unitOfWork.getSession().get(ReleaseImportStatus.class, id);
    }

    @Override
    public void add(ReleaseImportStatus status) {
        unitOfWork.getSession().save(status);
    }

    @Override
    public void update(ReleaseImportStatus status) {
        unitOfWork.getSession().save(status);
    }

    @Override
    public void delete(ReleaseImportStatus status) {
        unitOfWork.getSession().delete(status);
    }

}
