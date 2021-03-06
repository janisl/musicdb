package janisl.musicdb.repositories;

public interface UnitOfWork extends AutoCloseable {

    public void commit();
    
    public ArtistRepository getArtistRepository();
    
    public GenreRepository getGenreRepository();
    
    public KeyRepository getKeyRepository();
    
    public LabelRepository getLabelRepository();
    
    public ReleaseRepository getReleaseRepository();
    
    public ReleaseArtistRepository getReleaseArtistRepository();
    
    public ReleaseImportStatusRepository getReleaseImportStatusRepository();
    
    public TrackRepository getTrackRepository();
    
    public TrackArtistRepository getTrackArtistRepository();
    
    public TrackImportStatusRepository getTrackImportStatusRepository();
    
    public BeatportArtistRepository getBeatportArtistRepository();
    
    public BeatportGenreRepository getBeatportGenreRepository();
    
    public BeatportLabelRepository getBeatportLabelRepository();
    
    public BeatportReleaseRepository getBeatportReleaseRepository();
    
    public BeatportTrackRepository getBeatportTrackRepository();
    
    public MixxxCrateRepository getMixxxCrateRepository();
    
    public MixxxPlaylistRepository getMixxxPlaylistRepository();
    
    public MixxxPlaylistTrackRepository getMixxxPlaylistTrackRepository();
    
    public MixxxTrackRepository getMixxxTrackRepository();
    
    public MixxxTrackCueRepository getMixxxTrackCueRepository();
    
}
