package janisl.musicdb.controllers;

import janisl.musicdb.models.Release;
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
@RequestMapping(value = "/release")
public class ReleaseController {
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Release> getList() throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            return unitOfWork.getReleaseRepository().getAll();
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Release getById(@PathVariable("id") int id) throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            Release release = unitOfWork.getReleaseRepository().get(id);
            if (release == null) {
                throw new ReleaseNotFoundException();
            }
            return release;
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity add(@RequestBody Release release) throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            unitOfWork.getReleaseRepository().add(release);
            unitOfWork.commit();
            URI locationUri = new URI("/release/" + release.getId().toString());
            return ResponseEntity.ok().location(locationUri).build();
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity update(@PathVariable("id") int id, @RequestBody Release newRelease) throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            Release release = unitOfWork.getReleaseRepository().get(id);
            if (release == null) {
                throw new ReleaseNotFoundException();
            }
            release.setName(newRelease.getName());
            release.setArtistId(newRelease.getArtistId());
            release.setBeatportId(newRelease.getBeatportId());
            release.setBeatportUrl(newRelease.getBeatportUrl());
            release.setDiscogsId(newRelease.getDiscogsId());
            release.setLabelId(newRelease.getLabelId());
            release.setCatalogNumber(newRelease.getCatalogNumber());
            release.setReleaseDate(newRelease.getReleaseDate());

            unitOfWork.getReleaseRepository().update(release);
            unitOfWork.commit();
            return ResponseEntity.ok().build();
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable("id") int id) throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            Release release = unitOfWork.getReleaseRepository().get(id);
            if (release == null) {
                throw new ReleaseNotFoundException();
            }

            unitOfWork.getReleaseRepository().delete(release);
            unitOfWork.commit();
            return ResponseEntity.ok().build();
        }
    }

}
