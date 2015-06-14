package janisl.musicdb.controllers;

import janisl.musicdb.models.TrackImportStatus;
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
@RequestMapping(value = "/trackImportStatus")
public class TrackImportStatusController {
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<TrackImportStatus> getList() throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            return unitOfWork.getTrackImportStatusRepository().getAll();
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public TrackImportStatus getById(@PathVariable("id") int id) throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            TrackImportStatus status = unitOfWork.getTrackImportStatusRepository().get(id);
            if (status == null) {
                throw new TrackImportStatusNotFoundException();
            }
            return status;
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity add(@RequestBody TrackImportStatus status) throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            unitOfWork.getTrackImportStatusRepository().add(status);
            unitOfWork.commit();
            URI locationUri = new URI("/trackImportStatus/" + status.getId().toString());
            return ResponseEntity.ok().location(locationUri).build();
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity update(@PathVariable("id") int id, @RequestBody TrackImportStatus newStatus) throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            TrackImportStatus status = unitOfWork.getTrackImportStatusRepository().get(id);
            if (status == null) {
                throw new TrackImportStatusNotFoundException();
            }
            status.setName(newStatus.getName());

            unitOfWork.getTrackImportStatusRepository().update(status);
            unitOfWork.commit();
            return ResponseEntity.ok().build();
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable("id") int id) throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            TrackImportStatus status = unitOfWork.getTrackImportStatusRepository().get(id);
            if (status == null) {
                throw new TrackImportStatusNotFoundException();
            }

            unitOfWork.getTrackImportStatusRepository().delete(status);
            unitOfWork.commit();
            return ResponseEntity.ok().build();
        }
    }

}
