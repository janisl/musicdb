package janisl.musicdb.repositories;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class UnitOfWorkImpl implements UnitOfWork {

    private static final SessionFactory sessionFactory;
    private static final ServiceRegistry serviceRegistry;

    static {
        try {
            // Create the SessionFactory from standard (hibernate.cfg.xml)
            // config file.
            Configuration configuration = new Configuration();
            
            // Add all annotated classes for the main DB.
            configuration.addAnnotatedClass(janisl.musicdb.models.Artist.class);
            configuration.addAnnotatedClass(janisl.musicdb.models.Genre.class);
            configuration.addAnnotatedClass(janisl.musicdb.models.Label.class);
            configuration.addAnnotatedClass(janisl.musicdb.models.Release.class);
            configuration.addAnnotatedClass(janisl.musicdb.models.Track.class);
            configuration.addAnnotatedClass(janisl.musicdb.models.BeatportArtist.class);
            configuration.addAnnotatedClass(janisl.musicdb.models.BeatportGenre.class);
            configuration.addAnnotatedClass(janisl.musicdb.models.BeatportLabel.class);
            configuration.addAnnotatedClass(janisl.musicdb.models.BeatportRelease.class);
            
            configuration.configure();
            serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
                    configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Throwable ex) {
            // Log the exception.
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    private Session session;
    private Boolean transactionStarted = false;
    private ArtistRepositoryImpl artistRepository;
    private GenreRepositoryImpl genreRepository;
    private LabelRepositoryImpl labelRepository;
    private ReleaseRepositoryImpl releaseRepository;
    private TrackRepositoryImpl trackRepository;
    private BeatportArtistRepositoryImpl beatportArtistRepository;
    private BeatportGenreRepositoryImpl beatportGenreRepository;
    private BeatportLabelRepositoryImpl beatportLabelRepository;
    private BeatportReleaseRepositoryImpl beatportReleaseRepository;

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
    public TrackRepository getTrackRepository() {
        if (trackRepository == null) {
            trackRepository = new TrackRepositoryImpl(this);
        }
        return trackRepository;
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

}
