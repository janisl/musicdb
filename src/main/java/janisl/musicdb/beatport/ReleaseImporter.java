package janisl.musicdb.beatport;

import janisl.musicdb.models.Artist;
import janisl.musicdb.models.BeatportArtist;
import janisl.musicdb.models.BeatportGenre;
import janisl.musicdb.models.BeatportLabel;
import janisl.musicdb.models.BeatportRelease;
import janisl.musicdb.models.BeatportTrack;
import janisl.musicdb.models.Genre;
import janisl.musicdb.models.Key;
import janisl.musicdb.models.Label;
import janisl.musicdb.models.ReleaseDetails;
import janisl.musicdb.models.ReleaseDetailsArtist;
import janisl.musicdb.models.Track;
import janisl.musicdb.models.TrackArtist;
import janisl.musicdb.repositories.UnitOfWork;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ReleaseImporter {
    private final UnitOfWork unitOfWork;
    
    public ReleaseImporter(UnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    public ReleaseDetails importToLibrary(BeatportRelease beatportRelease) {
        ReleaseDetails release = new ReleaseDetails();
        release.setName(beatportRelease.getTitle());
        release.setBeatportId(beatportRelease.getId());
        release.setCatalogNumber(beatportRelease.getCatalogNumber());
        release.setReleaseDate(beatportRelease.getReleaseDate());
        release.setLabel(resolveLabel(beatportRelease.getLabel()));

        Set<BeatportArtist> releaseArtists = new HashSet<>(0);
        releaseArtists.addAll(beatportRelease.getArtists());
        for (BeatportTrack beatportTrack : beatportRelease.getTracks()) {
            releaseArtists.retainAll(beatportTrack.getArtists());
        }
        resolveReleaseArtists(release, releaseArtists);
        
        Set<Track> tracks = new HashSet<>(0);
        for (BeatportTrack beatportTrack : beatportRelease.getTracks()) {
            Track track = new Track();
            track.setRelease(release);
            track.setBeatportId(beatportTrack.getId());
            track.setPosition(beatportTrack.getOrderNumber());
            track.setName(beatportTrack.getTitle());
            track.setVersion(beatportTrack.getRemix());
            track.setGenre(resolveGenre(beatportTrack.getGenre()));
            track.setBpm(beatportTrack.getBpm());
            track.setKey(resolveKey(beatportTrack.getKeytone()));
            track.setDuration(beatportTrack.getDuration());
            if (artistsAreDifferent(releaseArtists, beatportTrack.getArtists()))
                resolveTrackArtists(track, beatportTrack.getArtists());
            track.setRemixers(resolveArtists(beatportTrack.getRemixers()));
            tracks.add(track);
        }
        release.setTracks(tracks);
        
        unitOfWork.getReleaseRepository().add(release);
        for (Track track : release.getTracks()) {
            unitOfWork.getTrackRepository().add(track);
        }
        return release;
    }
    
    private Label resolveLabel(BeatportLabel beatportLabel) {
        List<Label> labels = unitOfWork.getLabelRepository().getByBeatportId(beatportLabel.getId());
        if (labels.size() > 0) {
            return labels.get(0);
        }
        Label label = new Label();
        label.setName(beatportLabel.getName());
        label.setBeatportId(beatportLabel.getId());
        unitOfWork.getLabelRepository().add(label);
        return label;
    }
    
    private Genre resolveGenre(BeatportGenre beatportGenre) {
        List<Genre> genres = unitOfWork.getGenreRepository().getByName(beatportGenre.getName());
        if (genres.size() > 0) {
            return genres.get(0);
        }
        Genre genre = new Genre();
        genre.setName(beatportGenre.getName());
        unitOfWork.getGenreRepository().add(genre);
        return genre;
    }
    
    private Key resolveKey(String beatportKey) {
        List<Key> keys = unitOfWork.getKeyRepository().getByBeatportName(beatportKey);
        return keys.size() > 0 ? keys.get(0) : null;
    }
    
    private Set<Artist> resolveArtists(Set<BeatportArtist> beatportArtists) {
        Set<Artist> artists = new HashSet<>(0);
        for (BeatportArtist beatportArtist : beatportArtists) {
            Artist artist = null;
            List<Artist> found = unitOfWork.getArtistRepository().getByBeatportId(beatportArtist.getId());
            if (found.size() > 0) {
                artist = found.get(0);
            } else {
                found = unitOfWork.getArtistRepository().getByName(beatportArtist.getName());
                if (found.size() > 0) {
                    artist = found.get(0);
                } else {
                    artist = new Artist();
                    artist.setName(beatportArtist.getName());
                    artist.setBeatportId(beatportArtist.getId());
                    unitOfWork.getArtistRepository().add(artist);
                }
            }
            artists.add(artist);
        }
        return artists;
    }

    private void resolveTrackArtists(Track track, Set<BeatportArtist> beatportArtists) {
        Set<Artist> artists = resolveArtists(beatportArtists);
        Set<TrackArtist> trackArtists = new HashSet<>(0);
        for (Artist artist : artists) {
            TrackArtist trackArtist = new TrackArtist();
            trackArtist.setArtist(artist);
            trackArtist.setOrderNumber(trackArtists.size() + 1);
            trackArtist.setTrack(track);
            trackArtists.add(trackArtist);
        }
        track.setArtists(trackArtists);
    }

    private void resolveReleaseArtists(ReleaseDetails release, Set<BeatportArtist> beatportArtists) {
        Set<Artist> artists = resolveArtists(beatportArtists);
        Set<ReleaseDetailsArtist> releaseArtists = new HashSet<>(0);
        for (Artist artist : artists) {
            ReleaseDetailsArtist releaseArtist = new ReleaseDetailsArtist();
            releaseArtist.setArtist(artist);
            releaseArtist.setOrderNumber(releaseArtists.size() + 1);
            releaseArtist.setRelease(release);
            releaseArtists.add(releaseArtist);
        }
        release.setArtists(releaseArtists);
    }

    private boolean artistsAreDifferent(Set<BeatportArtist> releaseArtists, Set<BeatportArtist> artists) {
        if (releaseArtists.size() != artists.size()) {
            return true;
        }
        return !releaseArtists.containsAll(artists);
    }
}
