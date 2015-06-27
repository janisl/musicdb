package janisl.musicdb.controllers;

import janisl.musicdb.models.Release;
import janisl.musicdb.models.ReleaseDetails;
import janisl.musicdb.models.Track;
import janisl.musicdb.repositories.UnitOfWork;
import janisl.musicdb.repositories.UnitOfWorkFactory;
import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
            release.setPath(release.calculatePath());
            return release;
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity add(@RequestBody ReleaseDetails release) throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {

            release.resolveReferences(unitOfWork, null);
            for (Track track : release.getTracks()) {
                track.setRelease(release);
                track.resolveReferences(unitOfWork, null);
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

            newRelease.resolveReferences(unitOfWork, release.getArtists());

            release.setName(newRelease.getName());
            release.setBeatportId(newRelease.getBeatportId());
            release.setDiscogsId(newRelease.getDiscogsId());
            release.setCatalogNumber(newRelease.getCatalogNumber());
            release.setReleaseDate(newRelease.getReleaseDate());
            release.setLabel(newRelease.getLabel());
            release.setArtists(newRelease.getArtists());
            release.setImportStatus(newRelease.getImportStatus());
            release.setCoverLocation(newRelease.getCoverLocation());
            
            Set<Track> remainingTracks = release.getTracks();
            Set<Track> tracks = new HashSet<>(0);
            for (Track newTrack : newRelease.getTracks()) {
                if (newTrack.getId() != null) {
                    Track track = unitOfWork.getTrackRepository().get(newTrack.getId());
                    if (track == null) {
                        throw new TrackNotFoundException();
                    }
                    newTrack.resolveReferences(unitOfWork, track.getArtists());
                    track.copyForUpdate(newTrack);
                    tracks.add(track);
                    remainingTracks.remove(track);
                } else {
                    newTrack.setRelease(release);
                    newTrack.resolveReferences(unitOfWork, null);

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

    @RequestMapping(value = "/{id}/createDir", method = RequestMethod.GET)
    public void createDir(@PathVariable("id") int id) throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            ReleaseDetails release = unitOfWork.getReleaseRepository().get(id);
            if (release == null) {
                throw new ReleaseNotFoundException();
            }
            release.createDir();
        }
    }

    @RequestMapping(value = "{id}/cover", method = RequestMethod.GET)
    @ResponseBody
    public FileSystemResource getCover(@PathVariable("id") int id) throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            ReleaseDetails release = unitOfWork.getReleaseRepository().get(id);
            if (release == null) {
                throw new ReleaseNotFoundException();
            }
            if (release.getCoverLocation() == null || release.getCoverLocation().isEmpty()) {
                return new FileSystemResource("/home/janis/music/blank_cover.jpg"); 
            }
            return new FileSystemResource(release.getCoverLocation()); 
        }
    }

    @RequestMapping(value = "/{id}/setCover", method = RequestMethod.GET)
    public void setCover(@PathVariable("id") int id, @RequestParam(value = "url") String url) throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            ReleaseDetails release = unitOfWork.getReleaseRepository().get(id);
            if (release == null) {
                throw new ReleaseNotFoundException();
            }
            
            release.setCover(url);
            unitOfWork.getReleaseRepository().update(release);
            unitOfWork.commit();
        }
    }

    @RequestMapping(value = "/{id}/moveCover", method = RequestMethod.GET)
    public void moveCover(@PathVariable("id") int id) throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            ReleaseDetails release = unitOfWork.getReleaseRepository().get(id);
            if (release == null) {
                throw new ReleaseNotFoundException();
            }
            
            release.moveCover();
            unitOfWork.getReleaseRepository().update(release);
            unitOfWork.commit();
        }
    }

    @RequestMapping(value = "/{id}/moveTracks", method = RequestMethod.GET)
    public void moveTracks(@PathVariable("id") int id) throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            ReleaseDetails release = unitOfWork.getReleaseRepository().get(id);
            if (release == null) {
                throw new ReleaseNotFoundException();
            }
            
            release.moveTracks();
            unitOfWork.getReleaseRepository().update(release);
            unitOfWork.commit();
        }
    }

    @RequestMapping(value = "/{id}/moveTracksAndSetTags", method = RequestMethod.GET)
    public void moveTracksAndSetTags(@PathVariable("id") int id) throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            ReleaseDetails release = unitOfWork.getReleaseRepository().get(id);
            if (release == null) {
                throw new ReleaseNotFoundException();
            }
            
            release.moveTracksAndSetTags();
            unitOfWork.getReleaseRepository().update(release);
            unitOfWork.commit();
        }
    }
}
