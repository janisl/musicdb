package janisl.musicdb.controllers;

import janisl.musicdb.beatport.ArtistParser;
import janisl.musicdb.models.BeatportArtist;
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
@RequestMapping(value = "/beatport/artist")
public class BeatportArtistController {
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<BeatportArtist> getList() throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            return unitOfWork.getBeatportArtistRepository().getAll();
        }
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public BeatportArtist getByPath(@RequestParam(value = "url") String url) throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            final BeatportArtist artist = new ArtistParser(unitOfWork).parseUrl(url);
            unitOfWork.commit();
            return artist;
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public BeatportArtist getById(@PathVariable("id") int id) throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            BeatportArtist artist = unitOfWork.getBeatportArtistRepository().get(id);
            if (artist == null) {
                throw new ArtistNotFoundException();
            }
            return artist;
        }
    }

    @RequestMapping(value = "/{id}/reimport", method = RequestMethod.GET)
    public BeatportArtist reimportById(@PathVariable("id") int id) throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            BeatportArtist artist = unitOfWork.getBeatportArtistRepository().get(id);
            if (artist == null) {
                throw new ArtistNotFoundException();
            }
            new ArtistParser(unitOfWork).reimport(artist);
            unitOfWork.commit();
            return artist;
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable("id") int id) throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            BeatportArtist artist = unitOfWork.getBeatportArtistRepository().get(id);
            if (artist == null) {
                throw new ArtistNotFoundException();
            }

            unitOfWork.getBeatportArtistRepository().delete(artist);
            unitOfWork.commit();
            return ResponseEntity.ok().build();
        }
    }

    @RequestMapping(value = "/{id}/releases", method = RequestMethod.GET)
    public Set<BeatportRelease> getReleases(@PathVariable("id") int id) throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            BeatportArtist artist = unitOfWork.getBeatportArtistRepository().get(id);
            if (artist == null) {
                throw new ArtistNotFoundException();
            }
            artist.getReleases().size();
            return artist.getReleases();
        }
    }

}
