package janisl.musicdb.repositories;

public interface UnitOfWork extends AutoCloseable {

    public void commit();
    
    public LabelRepository getLabelRepository();
    
    public ArtistRepository getArtistRepository();
    
    public ReleaseRepository getReleaseRepository();
    
}
