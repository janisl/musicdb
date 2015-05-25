package janisl.musicdb.repositories;

import janisl.musicdb.models.BeatportRelease;
import java.util.List;
import org.hibernate.Criteria;

public class BeatportReleaseRepositoryImpl implements BeatportReleaseRepository {
    
    private final UnitOfWorkImpl unitOfWork;

    public BeatportReleaseRepositoryImpl(UnitOfWorkImpl unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    @Override
    public List<BeatportRelease> getAll() {
        return (List<BeatportRelease>) (unitOfWork.getSession().createCriteria(BeatportRelease.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list());
    }

    @Override
    public BeatportRelease get(Integer id) {
        return (BeatportRelease) unitOfWork.getSession().get(BeatportRelease.class, id);
    }

    @Override
    public void add(BeatportRelease release) {
        unitOfWork.getSession().save(release);
    }

    @Override
    public void update(BeatportRelease release) {
        unitOfWork.getSession().save(release);
    }

    @Override
    public void delete(BeatportRelease release) {
        unitOfWork.getSession().delete(release);
    }
    
}
