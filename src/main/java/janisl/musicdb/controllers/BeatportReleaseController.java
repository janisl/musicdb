package janisl.musicdb.controllers;

import janisl.musicdb.beatport.ReleaseParser;
import janisl.musicdb.models.BeatportRelease;
import janisl.musicdb.models.BeatportTrack;
import janisl.musicdb.repositories.UnitOfWork;
import janisl.musicdb.repositories.UnitOfWorkFactory;
import java.util.List;
import java.util.Set;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/beatport/release")
public class BeatportReleaseController {
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<BeatportRelease> getList() throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            return unitOfWork.getBeatportReleaseRepository().getAll();
        }
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public BeatportRelease getByPath(@RequestParam(value = "url") String url) throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            final BeatportRelease release = new ReleaseParser(unitOfWork).parseUrl(url);
            unitOfWork.commit();
            return release;
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public BeatportRelease getById(@PathVariable("id") int id) throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            BeatportRelease release = unitOfWork.getBeatportReleaseRepository().get(id);
            if (release == null) {
                throw new ReleaseNotFoundException();
            }
            return release;
        }
    }

    @RequestMapping(value = "/{id}/reimport", method = RequestMethod.GET)
    public BeatportRelease reimportById(@PathVariable("id") int id) throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            BeatportRelease release = unitOfWork.getBeatportReleaseRepository().get(id);
            if (release == null) {
                throw new ReleaseNotFoundException();
            }
            new ReleaseParser(unitOfWork).reimport(release);
            unitOfWork.commit();
            return release;
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable("id") int id) throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            BeatportRelease release = unitOfWork.getBeatportReleaseRepository().get(id);
            if (release == null) {
                throw new ReleaseNotFoundException();
            }

            unitOfWork.getBeatportReleaseRepository().delete(release);
            unitOfWork.commit();
            return ResponseEntity.ok().build();
        }
    }

    @RequestMapping(value = "/{id}/tracks", method = RequestMethod.GET)
    public Set<BeatportTrack> getTracks(@PathVariable("id") int id) throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            BeatportRelease release = unitOfWork.getBeatportReleaseRepository().get(id);
            if (release == null) {
                throw new ReleaseNotFoundException();
            }
            release.getTracks().size();
            return release.getTracks();
        }
    }

}
