package janisl.musicdb.repositories;

import janisl.musicdb.models.BeatportLabel;
import java.util.List;

public class BeatportLabelRepositoryImpl implements BeatportLabelRepository {
    
    private final UnitOfWorkImpl unitOfWork;

    public BeatportLabelRepositoryImpl(UnitOfWorkImpl unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    @Override
    public List<BeatportLabel> getAll() {
        return (List<BeatportLabel>) (unitOfWork.getSession().createCriteria(BeatportLabel.class).list());
    }

    @Override
    public BeatportLabel get(Integer id) {
        return (BeatportLabel) unitOfWork.getSession().get(BeatportLabel.class, id);
    }

    @Override
    public void add(BeatportLabel label) {
        unitOfWork.getSession().save(label);
    }

    @Override
    public void update(BeatportLabel label) {
        unitOfWork.getSession().save(label);
    }

    @Override
    public void delete(BeatportLabel label) {
        unitOfWork.getSession().delete(label);
    }
    
}
