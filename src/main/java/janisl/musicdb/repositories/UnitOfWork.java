package janisl.musicdb.repositories;

public interface UnitOfWork extends AutoCloseable {

    public void commit();
    
    public ArtistRepository getArtistRepository();
    
    public GenreRepository getGenreRepository();
    
    public LabelRepository getLabelRepository();
    
    public ReleaseRepository getReleaseRepository();
    
    public TrackRepository getTrackRepository();
    
}
