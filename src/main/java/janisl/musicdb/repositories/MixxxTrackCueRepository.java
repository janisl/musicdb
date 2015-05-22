package janisl.musicdb.repositories;

import janisl.musicdb.models.MixxxTrackCue;
import java.util.List;

public interface MixxxTrackCueRepository {

    public List<MixxxTrackCue> getAll();

    public List<MixxxTrackCue> getByTrackId(Integer trackId);

    public MixxxTrackCue get(Integer id);

}
