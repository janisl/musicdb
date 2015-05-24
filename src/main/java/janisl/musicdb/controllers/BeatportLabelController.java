package janisl.musicdb.controllers;

import janisl.musicdb.beatport.LabelParser;
import janisl.musicdb.models.BeatportLabel;
import janisl.musicdb.models.BeatportRelease;
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
@RequestMapping(value = "/beatport/label")
public class BeatportLabelController {
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<BeatportLabel> getList() throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            return unitOfWork.getBeatportLabelRepository().getAll();
        }
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public BeatportLabel getByPath(@RequestParam(value = "url") String url) throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            final BeatportLabel label = new LabelParser(unitOfWork).parseUrl(url);
            unitOfWork.commit();
            return label;
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public BeatportLabel getById(@PathVariable("id") int id) throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            BeatportLabel label = unitOfWork.getBeatportLabelRepository().get(id);
            if (label == null) {
                throw new LabelNotFoundException();
            }
            return label;
        }
    }

    @RequestMapping(value = "/{id}/reimport", method = RequestMethod.GET)
    public BeatportLabel reimportById(@PathVariable("id") int id) throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            BeatportLabel label = unitOfWork.getBeatportLabelRepository().get(id);
            if (label == null) {
                throw new LabelNotFoundException();
            }
            new LabelParser(unitOfWork).reimport(label);
            unitOfWork.commit();
            return label;
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable("id") int id) throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            BeatportLabel label = unitOfWork.getBeatportLabelRepository().get(id);
            if (label == null) {
                throw new LabelNotFoundException();
            }

            unitOfWork.getBeatportLabelRepository().delete(label);
            unitOfWork.commit();
            return ResponseEntity.ok().build();
        }
    }

    @RequestMapping(value = "/{id}/releases", method = RequestMethod.GET)
    public Set<BeatportRelease> releasesById(@PathVariable("id") int id) throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            BeatportLabel label = unitOfWork.getBeatportLabelRepository().get(id);
            if (label == null) {
                throw new LabelNotFoundException();
            }
            System.out.println(label.getReleases().size());
            return label.getReleases();
        }
    }

}
