package janisl.musicdb.repositories;

import org.hibernate.Session;

public interface UnitOfWork extends AutoCloseable {

    public Session getSession();
    
    public void commit();
    
}
