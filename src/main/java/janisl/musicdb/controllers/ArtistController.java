package janisl.musicdb.controllers;

import janisl.musicdb.models.Artist;
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
@RequestMapping(value = "/artist")
public class ArtistController {
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Artist> getList() throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            return unitOfWork.getArtistRepository().getAll();
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Artist getById(@PathVariable("id") int id) throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            Artist artist = unitOfWork.getArtistRepository().get(id);
            if (artist == null) {
                throw new ArtistNotFoundException();
            }
            return artist;
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity add(@RequestBody Artist artist) throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            unitOfWork.getArtistRepository().add(artist);
            unitOfWork.commit();
            URI locationUri = new URI("/artist/" + artist.getId().toString());
            return ResponseEntity.ok().location(locationUri).build();
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity update(@PathVariable("id") int id, @RequestBody Artist newArtist) throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            Artist artist = unitOfWork.getArtistRepository().get(id);
            if (artist == null) {
                throw new ArtistNotFoundException();
            }
            artist.setName(newArtist.getName());
            artist.setBeatportId(newArtist.getBeatportId());
            artist.setBeatportUrl(newArtist.getBeatportUrl());
            artist.setDiscogsId(newArtist.getDiscogsId());

            unitOfWork.getArtistRepository().update(artist);
            unitOfWork.commit();
            return ResponseEntity.ok().build();
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable("id") int id) throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            Artist artist = unitOfWork.getArtistRepository().get(id);
            if (artist == null) {
                throw new ArtistNotFoundException();
            }

            unitOfWork.getArtistRepository().delete(artist);
            unitOfWork.commit();
            return ResponseEntity.ok().build();
        }
    }

}
