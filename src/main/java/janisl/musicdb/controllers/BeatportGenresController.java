package janisl.musicdb.controllers;

import janisl.musicdb.models.BeatportGenre;
import janisl.musicdb.repositories.UnitOfWork;
import janisl.musicdb.repositories.UnitOfWorkFactory;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/beatport/genre")
public class BeatportGenresController {
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<BeatportGenre> getList() throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            return unitOfWork.getBeatportGenreRepository().getAll();
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public BeatportGenre getById(@PathVariable("id") int id) throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            BeatportGenre genre = unitOfWork.getBeatportGenreRepository().get(id);
            if (genre == null) {
                throw new GenreNotFoundException();
            }
            return genre;
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable("id") int id) throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            BeatportGenre genre = unitOfWork.getBeatportGenreRepository().get(id);
            if (genre == null) {
                throw new GenreNotFoundException();
            }

            unitOfWork.getBeatportGenreRepository().delete(genre);
            unitOfWork.commit();
            return ResponseEntity.ok().build();
        }
    }

}
