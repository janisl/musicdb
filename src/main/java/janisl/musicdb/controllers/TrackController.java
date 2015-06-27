package janisl.musicdb.controllers;

import janisl.musicdb.models.Track;
import janisl.musicdb.models.TrackTags;
import janisl.musicdb.repositories.UnitOfWork;
import janisl.musicdb.repositories.UnitOfWorkFactory;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/track")
public class TrackController {
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Track> getList() throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            return unitOfWork.getTrackRepository().getAll();
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Track getById(@PathVariable("id") int id) throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            Track track = unitOfWork.getTrackRepository().get(id);
            if (track == null) {
                throw new TrackNotFoundException();
            }
            return track;
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity add(@RequestBody Track track) throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            if (track.getRelease() != null && track.getRelease().getId() != null)
                track.setRelease(unitOfWork.getReleaseRepository().get(track.getRelease().getId()));
            else
                track.setRelease(null);

            track.resolveReferences(unitOfWork, null);
            
            unitOfWork.getTrackRepository().add(track);
            unitOfWork.commit();
            URI locationUri = new URI("/track/" + track.getId().toString());
            return ResponseEntity.ok().location(locationUri).build();
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity update(@PathVariable("id") int id, @RequestBody Track newTrack) throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            Track track = unitOfWork.getTrackRepository().get(id);
            if (track == null) {
                throw new TrackNotFoundException();
            }

            newTrack.resolveReferences(unitOfWork, track.getArtists());
            if (newTrack.getRelease() != null && newTrack.getRelease().getId() != null)
                track.setRelease(unitOfWork.getReleaseRepository().get(newTrack.getRelease().getId()));
            else
                track.setRelease(null);
            track.copyForUpdate(newTrack);
            
            unitOfWork.getTrackRepository().update(track);
            unitOfWork.commit();
            return ResponseEntity.ok().build();
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable("id") int id) throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            Track track = unitOfWork.getTrackRepository().get(id);
            if (track == null) {
                throw new TrackNotFoundException();
            }

            unitOfWork.getTrackRepository().delete(track);
            unitOfWork.commit();
            return ResponseEntity.ok().build();
        }
    }

    @RequestMapping(value = "/{id}/tags", method = RequestMethod.GET)
    public TrackTags getTags(@PathVariable("id") int id) throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            Track track = unitOfWork.getTrackRepository().get(id);
            if (track == null) {
                throw new TrackNotFoundException();
            }
            TrackTags tags = new TrackTags(track);
            tags.read();
            return tags;
        }
    }

    @RequestMapping(value = "/{id}/setTags", method = RequestMethod.GET)
    public TrackTags setTags(@PathVariable("id") int id) throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            Track track = unitOfWork.getTrackRepository().get(id);
            if (track == null) {
                throw new TrackNotFoundException();
            }
            TrackTags tags = new TrackTags(track);
            tags.write();
            return tags;
        }
    }

}
