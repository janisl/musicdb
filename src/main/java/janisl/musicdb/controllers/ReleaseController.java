package janisl.musicdb.controllers;

import janisl.musicdb.models.Release;
import janisl.musicdb.models.ReleaseDetails;
import janisl.musicdb.models.Track;
import janisl.musicdb.models.TrackArtist;
import janisl.musicdb.repositories.UnitOfWork;
import janisl.musicdb.repositories.UnitOfWorkFactory;
import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/release")
public class ReleaseController {
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Release> getList() throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            return unitOfWork.getReleaseRepository().getAll();
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ReleaseDetails getById(@PathVariable("id") int id) throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            ReleaseDetails release = unitOfWork.getReleaseRepository().get(id);
            if (release == null) {
                throw new ReleaseNotFoundException();
            }
            return release;
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity add(@RequestBody ReleaseDetails release) throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            if (release.getLabel() != null && release.getLabel().getId() != null)
                release.setLabel(unitOfWork.getLabelRepository().get(release.getLabel().getId()));
            else
                release.setLabel(null);
            
            for (Track track : release.getTracks()) {
                track.setRelease(release);
                track.ResolveTrackReferences(unitOfWork, null);
            }
            
            unitOfWork.getReleaseRepository().add(release);
            for (Track track : release.getTracks()) {
                unitOfWork.getTrackRepository().add(track);
            }
            
            unitOfWork.commit();
            URI locationUri = new URI("/release/" + release.getId().toString());
            return ResponseEntity.ok().location(locationUri).build();
        }
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity update(@PathVariable("id") int id, @RequestBody ReleaseDetails newRelease) throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            ReleaseDetails release = unitOfWork.getReleaseRepository().get(id);
            if (release == null) {
                throw new ReleaseNotFoundException();
            }
            release.setName(newRelease.getName());
            release.setArtistId(newRelease.getArtistId());
            release.setBeatportId(newRelease.getBeatportId());
            release.setDiscogsId(newRelease.getDiscogsId());
            release.setCatalogNumber(newRelease.getCatalogNumber());
            release.setReleaseDate(newRelease.getReleaseDate());
            
            if (newRelease.getLabel() != null && newRelease.getLabel().getId() != null)
                release.setLabel(unitOfWork.getLabelRepository().get(newRelease.getLabel().getId()));
            else
                release.setLabel(null);

            Set<Track> remainingTracks = release.getTracks();
            Set<Track> tracks = new HashSet<>(0);
            for (Track newTrack : newRelease.getTracks()) {
                if (newTrack.getId() != null) {
                    Track track = unitOfWork.getTrackRepository().get(newTrack.getId());
                    if (track == null) {
                        throw new TrackNotFoundException();
                    }
                    newTrack.ResolveTrackReferences(unitOfWork, track.getArtists());
                    track.setName(newTrack.getName());
                    track.setBeatportId(newTrack.getBeatportId());
                    track.setVersion(newTrack.getVersion());
                    track.setBpm(newTrack.getBpm());
                    track.setKey(newTrack.getKey());
                    track.setDuration(newTrack.getDuration());
                    track.setGenre(newTrack.getGenre());
                    track.setArtists(newTrack.getArtists());
                    tracks.add(track);
                    remainingTracks.remove(track);
                } else {
                    newTrack.setRelease(release);
                    newTrack.ResolveTrackReferences(unitOfWork, null);

                    tracks.add(newTrack);
                    unitOfWork.getTrackRepository().add(newTrack);
                }
            }
            release.setTracks(tracks);
            
            unitOfWork.getReleaseRepository().update(release);
            for (Track track : remainingTracks) {
                unitOfWork.getTrackRepository().delete(track);
            }
            
            unitOfWork.commit();
            return ResponseEntity.ok().build();
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable("id") int id) throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            ReleaseDetails release = unitOfWork.getReleaseRepository().get(id);
            if (release == null) {
                throw new ReleaseNotFoundException();
            }

            unitOfWork.getReleaseRepository().delete(release);
            unitOfWork.commit();
            return ResponseEntity.ok().build();
        }
    }

}
