package janisl.musicdb.repositories;

import janisl.musicdb.models.ReleaseImportStatus;
import java.util.List;

public interface ReleaseImportStatusRepository {
    
    public List<ReleaseImportStatus> getAll();

    public ReleaseImportStatus get( Integer id );

    public void add(ReleaseImportStatus status);
    
    public void update(ReleaseImportStatus status);
    
    public void delete(ReleaseImportStatus status);
    
}
