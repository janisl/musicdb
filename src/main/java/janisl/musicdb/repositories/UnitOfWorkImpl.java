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

}
