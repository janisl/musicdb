package janisl.musicdb.repositories;

import janisl.musicdb.models.Release;
import janisl.musicdb.models.ReleaseDetails;
import janisl.musicdb.models.ReleaseImportStatus;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

public class ReleaseRepositoryImpl implements ReleaseRepository {

    private final UnitOfWorkImpl unitOfWork;

    public ReleaseRepositoryImpl(UnitOfWorkImpl unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    @Override
    public List<Release> getAll() {
        return (List<Release>) (unitOfWork.getSession().createCriteria(Release.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list());
    }

    @Override
    public ReleaseDetails get(Integer id) {
        return (ReleaseDetails) unitOfWork.getSession().get(ReleaseDetails.class, id);
    }

    @Override
    public List<Release> getByImportStatus(ReleaseImportStatus importStatus) {
        return (List<Release>) (unitOfWork.getSession().createCriteria(Release.class).add(Restrictions.eq("importStatus", importStatus)).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list());
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
