package janisl.musicdb.controllers;

import janisl.musicdb.models.MixxxTrack;
import janisl.musicdb.models.MixxxTrackCue;
import janisl.musicdb.repositories.UnitOfWork;
import janisl.musicdb.repositories.UnitOfWorkFactory;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/mixxx/track")
public class MixxxTrackController {
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<MixxxTrack> getList(@RequestParam(value = "mixxxDeleted") Integer mixxxDeleted) throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            return unitOfWork.getMixxxTrackRepository().getAll(mixxxDeleted);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public MixxxTrack getById(@PathVariable("id") int id) throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            MixxxTrack track = unitOfWork.getMixxxTrackRepository().get(id);
            if (track == null) {
                throw new MixxxTrackNotFoundException();
            }
            return track;
        }
    }
    
    @RequestMapping(value = "/{id}/cues", method = RequestMethod.GET)
    public List<MixxxTrackCue> getCues(@PathVariable("id") Integer trackId) throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            return unitOfWork.getMixxxTrackCueRepository().getByTrackId(trackId);
        }
    }

}
