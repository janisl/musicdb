package janisl.musicdb.mixxx;

import janisl.musicdb.models.MixxxTrack;
import janisl.musicdb.models.Track;
import janisl.musicdb.repositories.UnitOfWork;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LinkHelper {
    private final UnitOfWork unitOfWork;

    public LinkHelper(UnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }
    
    public List<MixxxTrack> getMixxxNotLinked() {
        List<MixxxTrack> mixxxTracks = unitOfWork.getMixxxTrackRepository().getAll(0);
        List<Track> tracks = unitOfWork.getTrackRepository().getAll();
        List<MixxxTrack> unusedMixxxTracks = new ArrayList<>();
        for (MixxxTrack mixxxTrack : mixxxTracks) {
            boolean used = false;
            for (Track track : tracks) {
                if (Objects.equals(mixxxTrack.getId(), track.getMixxxId())) {
                    used = true;
                    break;
                }
            }
            if (!used) {
                unusedMixxxTracks.add(mixxxTrack);
            }
        }
        return unusedMixxxTracks;
    }
}
