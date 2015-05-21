package janisl.musicdb.controllers;

import janisl.musicdb.models.BeatportTrack;
import janisl.musicdb.repositories.UnitOfWork;
import janisl.musicdb.repositories.UnitOfWorkFactory;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/beatport/track")
public class BeatportTrackController {
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<BeatportTrack> getList() throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            return unitOfWork.getBeatportTrackRepository().getAll();
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public BeatportTrack getById(@PathVariable("id") int id) throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            BeatportTrack track = unitOfWork.getBeatportTrackRepository().get(id);
            if (track == null) {
                throw new TrackNotFoundException();
            }
            return track;
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable("id") int id) throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            BeatportTrack track = unitOfWork.getBeatportTrackRepository().get(id);
            if (track == null) {
                throw new TrackNotFoundException();
            }

            unitOfWork.getBeatportTrackRepository().delete(track);
            unitOfWork.commit();
            return ResponseEntity.ok().build();
        }
    }

}
