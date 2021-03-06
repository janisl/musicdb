package janisl.musicdb.repositories;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class UnitOfWorkImpl implements UnitOfWork {

    private static final SessionFactory sessionFactory;
    private static final ServiceRegistry serviceRegistry;
    private static final SessionFactory mixxxSessionFactory;
    private static final ServiceRegistry mixxxServiceRegistry;

    static {
        try {
            // Create the SessionFactory from standard (hibernate.cfg.xml)
            // config file.
            Configuration configuration = new Configuration();
            
            // Add all annotated classes for the main DB.
            configuration.addAnnotatedClass(janisl.musicdb.models.Artist.class);
            configuration.addAnnotatedClass(janisl.musicdb.models.Genre.class);
            configuration.addAnnotatedClass(janisl.musicdb.models.Key.class);
            configuration.addAnnotatedClass(janisl.musicdb.models.Label.class);
            configuration.addAnnotatedClass(janisl.musicdb.models.Release.class);
            configuration.addAnnotatedClass(janisl.musicdb.models.ReleaseArtist.class);
            configuration.addAnnotatedClass(janisl.musicdb.models.ReleaseDetails.class);
            configuration.addAnnotatedClass(janisl.musicdb.models.ReleaseDetailsArtist.class);
            configuration.addAnnotatedClass(janisl.musicdb.models.ReleaseImportStatus.class);
            configuration.addAnnotatedClass(janisl.musicdb.models.Track.class);
            configuration.addAnnotatedClass(janisl.musicdb.models.TrackArtist.class);
            configuration.addAnnotatedClass(janisl.musicdb.models.TrackImportStatus.class);
            configuration.addAnnotatedClass(janisl.musicdb.models.BeatportArtist.class);
            configuration.addAnnotatedClass(janisl.musicdb.models.BeatportGenre.class);
            configuration.addAnnotatedClass(janisl.musicdb.models.BeatportLabel.class);
            configuration.addAnnotatedClass(janisl.musicdb.models.BeatportRelease.class);
            configuration.addAnnotatedClass(janisl.musicdb.models.BeatportTrack.class);
            
            configuration.configure();
            serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
                    configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            
            // Create the SessionFactory from hibernate-mixxx.cfg.xml config file.
            Configuration mixxxConfiguration = new Configuration();
            
            // Add all annotated classes for the main DB.
            mixxxConfiguration.addAnnotatedClass(janisl.musicdb.models.MixxxCrate.class);
            mixxxConfiguration.addAnnotatedClass(janisl.musicdb.models.MixxxPlaylist.class);
            mixxxConfiguration.addAnnotatedClass(janisl.musicdb.models.MixxxPlaylistTrack.class);
            mixxxConfiguration.addAnnotatedClass(janisl.musicdb.models.MixxxTrack.class);
            mixxxConfiguration.addAnnotatedClass(janisl.musicdb.models.MixxxTrackCue.class);
            mixxxConfiguration.addAnnotatedClass(janisl.musicdb.models.MixxxTrackLocation.class);
            
            mixxxConfiguration.configure("hibernate-mixxx.cfg.xml");
            mixxxServiceRegistry = new StandardServiceRegistryBuilder().applySettings(
                    mixxxConfiguration.getProperties()).build();
            mixxxSessionFactory = mixxxConfiguration.buildSessionFactory(mixxxServiceRegistry);
        } catch (Throwable ex) {
            // Log the exception.
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    private Session session;
    private Session mixxxSession;
    private Boolean transactionStarted = false;
    private Boolean mixxxTransactionStarted = false;
    
    private ArtistRepositoryImpl artistRepository;
    private GenreRepositoryImpl genreRepository;
    private KeyRepositoryImpl keyRepository;
    private LabelRepositoryImpl labelRepository;
    private ReleaseRepositoryImpl releaseRepository;
    private ReleaseArtistRepositoryImpl releaseArtistRepository;
    private ReleaseImportStatusRepositoryImpl releaseImportStatusRepository;
    private TrackRepositoryImpl trackRepository;
    private TrackArtistRepositoryImpl trackArtistRepository;
    private TrackImportStatusRepositoryImpl trackImportStatusRepository;
    private BeatportArtistRepositoryImpl beatportArtistRepository;
    private BeatportGenreRepositoryImpl beatportGenreRepository;
    private BeatportLabelRepositoryImpl beatportLabelRepository;
    private BeatportReleaseRepositoryImpl beatportReleaseRepository;
    private BeatportTrackRepositoryImpl beatportTrackRepository;
    private MixxxCrateRepositoryImpl mixxxCrateRepository;
    private MixxxPlaylistRepositoryImpl mixxxPlaylistRepository;
    private MixxxPlaylistTrackRepositoryImpl mixxxPlaylistTrackRepository;
    private MixxxTrackRepositoryImpl mixxxTrackRepository;
    private MixxxTrackCueRepositoryImpl mixxxTrackCueRepository;

    public Session getSession() {
        if (session == null) {
            session = sessionFactory.openSession();
        }
        if (!transactionStarted) {
            session.beginTransaction();
            transactionStarted = true;
        }
        return session;
    }

    public Session getMixxxSession() {
        if (mixxxSession == null) {
            mixxxSession = mixxxSessionFactory.openSession();
        }
        if (!mixxxTransactionStarted) {
            mixxxSession.beginTransaction();
            mixxxTransactionStarted = true;
        }
        return mixxxSession;
    }

    @Override
    public void commit() {
        if (session != null && transactionStarted) {
            session.getTransaction().commit();
            transactionStarted = false;
        }
        if (mixxxSession != null && mixxxTransactionStarted) {
            mixxxSession.getTransaction().commit();
            mixxxTransactionStarted = false;
        }
    }

    @Override
    public void close() throws Exception {
        if (session != null) {
            if (transactionStarted) {
                session.getTransaction().rollback();
                transactionStarted = false;
            }
            session.close();
        }
        if (mixxxSession != null) {
            if (mixxxTransactionStarted) {
                mixxxSession.getTransaction().rollback();
                mixxxTransactionStarted = false;
            }
            mixxxSession.close();
        }
    }

    @Override
    public ArtistRepository getArtistRepository() {
        if (artistRepository == null) {
            artistRepository = new ArtistRepositoryImpl(this);
        }
        return artistRepository;
    }

    @Override
    public GenreRepository getGenreRepository() {
        if (genreRepository == null) {
            genreRepository = new GenreRepositoryImpl(this);
        }
        return genreRepository;
    }

    @Override
    public KeyRepository getKeyRepository() {
        if (keyRepository == null) {
            keyRepository = new KeyRepositoryImpl(this);
        }
        return keyRepository;
    }

    @Override
    public LabelRepository getLabelRepository() {
        if (labelRepository == null) {
            labelRepository = new LabelRepositoryImpl(this);
        }
        return labelRepository;
    }

    @Override
    public ReleaseRepository getReleaseRepository() {
        if (releaseRepository == null) {
            releaseRepository = new ReleaseRepositoryImpl(this);
        }
        return releaseRepository;
    }

    @Override
    public ReleaseArtistRepository getReleaseArtistRepository() {
        if (releaseArtistRepository == null) {
            releaseArtistRepository = new ReleaseArtistRepositoryImpl(this);
        }
        return releaseArtistRepository;
    }

    @Override
    public ReleaseImportStatusRepository getReleaseImportStatusRepository() {
        if (releaseImportStatusRepository == null) {
            releaseImportStatusRepository = new ReleaseImportStatusRepositoryImpl(this);
        }
        return releaseImportStatusRepository;
    }

    @Override
    public TrackRepository getTrackRepository() {
        if (trackRepository == null) {
            trackRepository = new TrackRepositoryImpl(this);
        }
        return trackRepository;
    }

    @Override
    public TrackArtistRepository getTrackArtistRepository() {
        if (trackArtistRepository == null) {
            trackArtistRepository = new TrackArtistRepositoryImpl(this);
        }
        return trackArtistRepository;
    }

    @Override
    public TrackImportStatusRepository getTrackImportStatusRepository() {
        if (trackImportStatusRepository == null) {
            trackImportStatusRepository = new TrackImportStatusRepositoryImpl(this);
        }
        return trackImportStatusRepository;
    }

    @Override
    public BeatportArtistRepository getBeatportArtistRepository() {
        if (beatportArtistRepository == null) {
            beatportArtistRepository = new BeatportArtistRepositoryImpl(this);
        }
        return beatportArtistRepository;
    }

    @Override
    public BeatportGenreRepository getBeatportGenreRepository() {
        if (beatportGenreRepository == null) {
            beatportGenreRepository = new BeatportGenreRepositoryImpl(this);
        }
        return beatportGenreRepository;
    }

    @Override
    public BeatportLabelRepository getBeatportLabelRepository() {
        if (beatportLabelRepository == null) {
            beatportLabelRepository = new BeatportLabelRepositoryImpl(this);
        }
        return beatportLabelRepository;
    }

    @Override
    public BeatportReleaseRepository getBeatportReleaseRepository() {
        if (beatportReleaseRepository == null) {
            beatportReleaseRepository = new BeatportReleaseRepositoryImpl(this);
        }
        return beatportReleaseRepository;
    }

    @Override
    public BeatportTrackRepository getBeatportTrackRepository() {
        if (beatportTrackRepository == null) {
            beatportTrackRepository = new BeatportTrackRepositoryImpl(this);
        }
        return beatportTrackRepository;
    }

    @Override
    public MixxxCrateRepository getMixxxCrateRepository() {
        if (mixxxCrateRepository == null) {
            mixxxCrateRepository = new MixxxCrateRepositoryImpl(this);
        }
        return mixxxCrateRepository;
    }

    @Override
    public MixxxPlaylistRepository getMixxxPlaylistRepository() {
        if (mixxxPlaylistRepository == null) {
            mixxxPlaylistRepository = new MixxxPlaylistRepositoryImpl(this);
        }
        return mixxxPlaylistRepository;
    }

    @Override
    public MixxxPlaylistTrackRepository getMixxxPlaylistTrackRepository() {
        if (mixxxPlaylistTrackRepository == null) {
            mixxxPlaylistTrackRepository = new MixxxPlaylistTrackRepositoryImpl(this);
        }
        return mixxxPlaylistTrackRepository;
    }

    @Override
    public MixxxTrackRepository getMixxxTrackRepository() {
        if (mixxxTrackRepository == null) {
            mixxxTrackRepository = new MixxxTrackRepositoryImpl(this);
        }
        return mixxxTrackRepository;
    }

    @Override
    public MixxxTrackCueRepository getMixxxTrackCueRepository() {
        if (mixxxTrackCueRepository == null) {
            mixxxTrackCueRepository = new MixxxTrackCueRepositoryImpl(this);
        }
        return mixxxTrackCueRepository;
    }

}
