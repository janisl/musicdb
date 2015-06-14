package janisl.musicdb.controllers;

import janisl.musicdb.models.Release;
import janisl.musicdb.models.ReleaseImportStatus;
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
@RequestMapping(value = "/releaseImportStatus")
public class ReleaseImportStatusController {
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<ReleaseImportStatus> getList() throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            return unitOfWork.getReleaseImportStatusRepository().getAll();
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ReleaseImportStatus getById(@PathVariable("id") int id) throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            ReleaseImportStatus status = unitOfWork.getReleaseImportStatusRepository().get(id);
            if (status == null) {
                throw new ReleaseImportStatusNotFoundException();
            }
            return status;
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity add(@RequestBody ReleaseImportStatus status) throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            unitOfWork.getReleaseImportStatusRepository().add(status);
            unitOfWork.commit();
            URI locationUri = new URI("/releaseImportStatus/" + status.getId().toString());
            return ResponseEntity.ok().location(locationUri).build();
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity update(@PathVariable("id") int id, @RequestBody ReleaseImportStatus newStatus) throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            ReleaseImportStatus status = unitOfWork.getReleaseImportStatusRepository().get(id);
            if (status == null) {
                throw new ReleaseImportStatusNotFoundException();
            }
            status.setName(newStatus.getName());

            unitOfWork.getReleaseImportStatusRepository().update(status);
            unitOfWork.commit();
            return ResponseEntity.ok().build();
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable("id") int id) throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            ReleaseImportStatus status = unitOfWork.getReleaseImportStatusRepository().get(id);
            if (status == null) {
                throw new ReleaseImportStatusNotFoundException();
            }

            unitOfWork.getReleaseImportStatusRepository().delete(status);
            unitOfWork.commit();
            return ResponseEntity.ok().build();
        }
    }
    
    @RequestMapping(value = "/{id}/releases", method = RequestMethod.GET)
    public List<Release> getReleases(@PathVariable("id") int id) throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            ReleaseImportStatus status = unitOfWork.getReleaseImportStatusRepository().get(id);
            if (status == null) {
                throw new ReleaseImportStatusNotFoundException();
            }

            return unitOfWork.getReleaseRepository().getByImportStatus(status);
        }
    }
    
    @RequestMapping(value = "/releases", method = RequestMethod.GET)
    public List<Release> getReleasesWithout() throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            return unitOfWork.getReleaseRepository().getByImportStatus(null);
        }
    }

}
