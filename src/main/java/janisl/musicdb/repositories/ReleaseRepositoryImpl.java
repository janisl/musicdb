package janisl.musicdb.repositories;

import janisl.musicdb.models.Release;
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
    public Release get(Integer id) {
        return (Release) unitOfWork.getSession().get(Release.class, id);
    }

    @Override
    public void add(Release release) {
        unitOfWork.getSession().save(release);
    }

    @Override
    public void update(Release release) {
        unitOfWork.getSession().save(release);
    }

    @Override
    public void delete(Release release) {
        unitOfWork.getSession().delete(release);
    }
    
}
