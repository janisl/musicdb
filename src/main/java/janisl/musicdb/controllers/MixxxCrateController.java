package janisl.musicdb.controllers;

import janisl.musicdb.models.MixxxCrate;
import janisl.musicdb.models.MixxxTrack;
import janisl.musicdb.repositories.UnitOfWork;
import janisl.musicdb.repositories.UnitOfWorkFactory;
import java.util.List;
import java.util.Set;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/mixxx/crate")
public class MixxxCrateController {
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<MixxxCrate> getList() throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            return unitOfWork.getMixxxCrateRepository().getAll();
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public MixxxCrate getById(@PathVariable("id") int id) throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            MixxxCrate crate = unitOfWork.getMixxxCrateRepository().get(id);
            if (crate == null) {
                throw new MixxxCrateNotFoundException();
            }
            return crate;
        }
    }

    @RequestMapping(value = "/{id}/track", method = RequestMethod.GET)
    public Set<MixxxTrack> getTracks(@PathVariable("id") int id) throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            MixxxCrate crate = unitOfWork.getMixxxCrateRepository().get(id);
            if (crate == null) {
                throw new MixxxCrateNotFoundException();
            }
            Integer size = crate.getTracks().size();
            return crate.getTracks();
        }
    }
    
}
