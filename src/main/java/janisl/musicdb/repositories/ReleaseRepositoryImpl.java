package janisl.musicdb.repositories;

import janisl.musicdb.models.Release;
import janisl.musicdb.models.ReleaseDetails;
import java.util.List;

public class ReleaseRepositoryImpl implements ReleaseRepository {
    
    private final UnitOfWorkImpl unitOfWork;

    public ReleaseRepositoryImpl(UnitOfWorkImpl unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    @Override
    public List<Release> getAll() {
        return (List<Release>) (unitOfWork.getSession().createCriteria(Release.class).list());
    }

    @Override
    public ReleaseDetails get(Integer id) {
        return (ReleaseDetails) unitOfWork.getSession().get(ReleaseDetails.class, id);
    }

    @Override
    public void add(ReleaseDetails release) {
        unitOfWork.getSession().save(release);
    }

    @Override
    public void update(ReleaseDetails release) {
        unitOfWork.getSession().save(release);
    }

    @Override
    public void delete(ReleaseDetails release) {
        unitOfWork.getSession().delete(release);
    }
    
}
