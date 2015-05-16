package janisl.musicdb.controllers;

import janisl.musicdb.models.Label;
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
@RequestMapping(value = "/label")
public class LabelController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Label> getList() throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            return unitOfWork.getLabelRepository().getAll();
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Label getById(@PathVariable("id") int id) throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            Label label = unitOfWork.getLabelRepository().get(id);
            if (label == null) {
                throw new LabelNotFoundException();
            }
            return label;
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity add(@RequestBody Label label) throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            unitOfWork.getLabelRepository().add(label);
            unitOfWork.commit();
            URI locationUri = new URI("/label/" + label.getId().toString());
            return ResponseEntity.ok().location(locationUri).build();
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity update(@PathVariable("id") int id, @RequestBody Label newLabel) throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            Label label = unitOfWork.getLabelRepository().get(id);
            if (label == null) {
                throw new LabelNotFoundException();
            }
            label.setName(newLabel.getName());
            label.setBeatportId(newLabel.getBeatportId());
            label.setBeatportUrl(newLabel.getBeatportUrl());
            label.setDiscogsId(newLabel.getDiscogsId());

            unitOfWork.getLabelRepository().update(label);
            unitOfWork.commit();
            return ResponseEntity.ok().build();
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable("id") int id) throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            Label label = unitOfWork.getLabelRepository().get(id);
            if (label == null) {
                throw new LabelNotFoundException();
            }

            unitOfWork.getLabelRepository().delete(label);
            unitOfWork.commit();
            return ResponseEntity.ok().build();
        }
    }

}
