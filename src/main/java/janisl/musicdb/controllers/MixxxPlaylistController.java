package janisl.musicdb.controllers;

import janisl.musicdb.models.MixxxPlaylist;
import janisl.musicdb.models.MixxxPlaylistTrack;
import janisl.musicdb.repositories.UnitOfWork;
import janisl.musicdb.repositories.UnitOfWorkFactory;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/mixxx/playlist")
public class MixxxPlaylistController {
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<MixxxPlaylist> getList() throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            return unitOfWork.getMixxxPlaylistRepository().getAll();
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public MixxxPlaylist getById(@PathVariable("id") int id) throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            MixxxPlaylist playlist = unitOfWork.getMixxxPlaylistRepository().get(id);
            if (playlist == null) {
                throw new MixxxPlaylistNotFoundException();
            }
            return playlist;
        }
    }

    @RequestMapping(value = "/{id}/track", method = RequestMethod.GET)
    public List<MixxxPlaylistTrack> getTracks(@PathVariable("id") int id) throws Exception {
        try (UnitOfWork unitOfWork = UnitOfWorkFactory.create()) {
            return unitOfWork.getMixxxPlaylistTrackRepository().getByPlaylistId(id);
        }
    }
    
}
