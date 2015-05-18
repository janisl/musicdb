package janisl.musicdb.controllers;

import janisl.musicdb.models.Track;
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
            track.setName(newTrack.getName());
            track.setBeatportId(newTrack.getBeatportId());
            track.setBeatportUrl(newTrack.getBeatportUrl());
            track.setVersion(newTrack.getVersion());
            track.setBpm(newTrack.getBpm());
            track.setArtistId(newTrack.getArtistId());
            track.setReleaseId(newTrack.getReleaseId());
            track.setKeyId(newTrack.getKeyId());
            track.setDuration(newTrack.getDuration());
            track.setGenreId(newTrack.getGenreId());

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

}
