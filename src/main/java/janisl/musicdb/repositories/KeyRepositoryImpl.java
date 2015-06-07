package janisl.musicdb.repositories;

import janisl.musicdb.models.Key;
import java.util.List;
import org.hibernate.criterion.Restrictions;

public class KeyRepositoryImpl implements KeyRepository {

    private final UnitOfWorkImpl unitOfWork;

    public KeyRepositoryImpl(UnitOfWorkImpl unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    @Override
    public List<Key> getAll() {
        return (List<Key>) (unitOfWork.getSession().createCriteria(Key.class).list());
    }

    @Override
    public Key get(Integer id) {
        return (Key) unitOfWork.getSession().get(Key.class, id);
    }

    @Override
    public List<Key> getByBeatportName(String beatportName) {
        return (List<Key>) (unitOfWork.getSession().createCriteria(Key.class).add(Restrictions.eq("beatportName", beatportName)).list());
    }

}
