package janisl.musicdb.repositories;

import janisl.musicdb.models.Release;
import janisl.musicdb.models.ReleaseDetails;
import janisl.musicdb.models.ReleaseImportStatus;
import java.util.List;

public interface ReleaseRepository {

    public List<Release> getAll();

    public ReleaseDetails get(Integer id);

    public List<Release> getByImportStatus(ReleaseImportStatus importStatus);

    public void add(ReleaseDetails release);

    public void update(ReleaseDetails release);

    public void delete(ReleaseDetails release);

}
