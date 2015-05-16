package janisl.musicdb.repositories;

import janisl.musicdb.HibernateUtil;
import org.hibernate.Session;

public class UnitOfWorkImpl implements UnitOfWork {

    private Session session;
    private Boolean transactionStarted = false;
    
    @Override
    public Session getSession() {
        if (session == null) {
            session = HibernateUtil.getSessionFactory().openSession();
        }
        if (!transactionStarted) {
            session.beginTransaction();
            transactionStarted = true;
        }
        return session;
    }

    @Override
    public void commit() {
        if (session != null && transactionStarted) {
            session.getTransaction().commit();
            transactionStarted = false;
        }
    }
    
    @Override
    public void close() throws Exception {
        if (session != null) {
            session.close();
        }
    }
    
}
