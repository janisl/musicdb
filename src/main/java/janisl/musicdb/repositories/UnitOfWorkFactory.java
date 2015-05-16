package janisl.musicdb.repositories;

public class UnitOfWorkFactory {

    public static UnitOfWork create() {
        return new UnitOfWorkImpl();
    }
    
}
