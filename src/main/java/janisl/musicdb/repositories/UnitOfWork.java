package janisl.musicdb.repositories;

public interface UnitOfWork extends AutoCloseable {

    public void commit();
    
    public ArtistRepository getArtistRepository();
    
    public GenreRepository getGenreRepository();
    
    public LabelRepository getLabelRepository();
    
    public ReleaseRepository getReleaseRepository();
    
    public TrackRepository getTrackRepository();
    
    public BeatportArtistRepository getBeatportArtistRepository();
    
    public BeatportGenreRepository getBeatportGenreRepository();
    
    public BeatportLabelRepository getBeatportLabelRepository();
    
    public BeatportReleaseRepository getBeatportReleaseRepository();
    
    public BeatportTrackRepository getBeatportTrackRepository();
    
    public MixxxTrackRepository getMixxxTrackRepository();
    
}
