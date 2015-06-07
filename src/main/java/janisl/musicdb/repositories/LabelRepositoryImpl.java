package janisl.musicdb.repositories;

import janisl.musicdb.models.Label;
import java.util.List;
import org.hibernate.criterion.Restrictions;

public class LabelRepositoryImpl implements LabelRepository {

    private final UnitOfWorkImpl unitOfWork;

    public LabelRepositoryImpl(UnitOfWorkImpl unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    @Override
    public List<Label> getAll() {
        return (List<Label>) (unitOfWork.getSession().createCriteria(Label.class).list());
    }

    @Override
    public Label get(Integer id) {
        return (Label) unitOfWork.getSession().get(Label.class, id);
    }

    @Override
    public List<Label> getByBeatportId(Integer beatportId) {
        return (List<Label>) (unitOfWork.getSession().createCriteria(Label.class).add(Restrictions.eq("beatportId", beatportId)).list());
    }
    
    @Override
    public void add(Label label) {
        unitOfWork.getSession().save(label);
    }

    @Override
    public void update(Label label) {
        unitOfWork.getSession().save(label);
    }

    @Override
    public void delete(Label label) {
        unitOfWork.getSession().delete(label);
    }

}
