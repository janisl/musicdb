package janisl.musicdb.repositories;

import janisl.musicdb.models.TrackImportStatus;
import java.util.List;

public interface TrackImportStatusRepository {
    
    public List<TrackImportStatus> getAll();

    public TrackImportStatus get( Integer id );

    public void add(TrackImportStatus status);
    
    public void update(TrackImportStatus status);
    
    public void delete(TrackImportStatus status);
    
}
