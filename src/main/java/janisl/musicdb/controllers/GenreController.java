package janisl.musicdb.controllers;

import janisl.musicdb.models.Genre;
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
@RequestMapping(value = "/genre")
public class GenreController {
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Genre> getList() throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            return unitOfWork.getGenreRepository().getAll();
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Genre getById(@PathVariable("id") int id) throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            Genre genre = unitOfWork.getGenreRepository().get(id);
            if (genre == null) {
                throw new GenreNotFoundException();
            }
            return genre;
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity add(@RequestBody Genre genre) throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            unitOfWork.getGenreRepository().add(genre);
            unitOfWork.commit();
            URI locationUri = new URI("/genre/" + genre.getId().toString());
            return ResponseEntity.ok().location(locationUri).build();
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity update(@PathVariable("id") int id, @RequestBody Genre newGenre) throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            Genre genre = unitOfWork.getGenreRepository().get(id);
            if (genre == null) {
                throw new GenreNotFoundException();
            }
            genre.setName(newGenre.getName());

            unitOfWork.getGenreRepository().update(genre);
            unitOfWork.commit();
            return ResponseEntity.ok().build();
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable("id") int id) throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            Genre genre = unitOfWork.getGenreRepository().get(id);
            if (genre == null) {
                throw new GenreNotFoundException();
            }

            unitOfWork.getGenreRepository().delete(genre);
            unitOfWork.commit();
            return ResponseEntity.ok().build();
        }
    }

}
