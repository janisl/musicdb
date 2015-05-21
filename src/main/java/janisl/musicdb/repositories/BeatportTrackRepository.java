package janisl.musicdb.repositories;

import janisl.musicdb.models.BeatportTrack;
import java.util.List;

public interface BeatportTrackRepository {

    public List<BeatportTrack> getAll();

    public BeatportTrack get(Integer id);

    public void add(BeatportTrack track);

    public void update(BeatportTrack track);

    public void delete(BeatportTrack track);

}
