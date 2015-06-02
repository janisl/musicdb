package janisl.musicdb.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import janisl.musicdb.repositories.UnitOfWork;
import java.io.Serializable;
import java.sql.Time;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Track implements Serializable {

    private Integer id;
    private String name;
    private Integer beatportId;
    private String version;
    private Integer bpm;
    private ReleaseDetails release;
    private Key key;
    private Time duration;
    private Genre genre;
    private Set<TrackArtist> artists = new HashSet<>(0);

    public Track() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBeatportId() {
        return beatportId;
    }

    public void setBeatportId(Integer beatportId) {
        this.beatportId = beatportId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getBpm() {
        return bpm;
    }

    public void setBpm(Integer bpm) {
        this.bpm = bpm;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "releaseId")
    @JsonIgnore
    public ReleaseDetails getRelease() {
        return release;
    }

    public void setRelease(ReleaseDetails release) {
        this.release = release;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "keyId")
    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public Time getDuration() {
        return duration;
    }

    public void setDuration(Time duration) {
        this.duration = duration;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "genreId")
    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    @OneToMany(mappedBy = "track", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Set<TrackArtist> getArtists() {
        return artists;
    }

    public void setArtists(Set<TrackArtist> artists) {
        this.artists = artists;
    }

    public void resolveReferences(UnitOfWork unitOfWork, Set<TrackArtist> existingArtists) {
        if (getGenre() != null && getGenre().getId() != null)
            setGenre(unitOfWork.getGenreRepository().get(getGenre().getId()));
        else
            setGenre(null);
        
        if (getKey() != null && getKey().getId() != null)
            setKey(unitOfWork.getKeyRepository().get(getKey().getId()));
        else
            setKey(null);
        
        Set<TrackArtist> newArtists = new HashSet<>(0);
        for (TrackArtist newArtist : getArtists()) {
            TrackArtist trackArtist = null;
            if (newArtist.getId() != null && existingArtists != null) {
                for (TrackArtist check : existingArtists) {
                    if (check.getId() == newArtist.getId()) {
                        trackArtist = check;
                        trackArtist.setArtist(newArtist.getArtist());
                        trackArtist.setOrderNumber(newArtist.getOrderNumber());
                        trackArtist.setJoinText(newArtist.getJoinText());
                        existingArtists.remove(check);
                        break;
                    }
                }
            }
            if (trackArtist == null) {
                trackArtist = newArtist;
                trackArtist.setTrack(this);
            }
            trackArtist.setArtist(unitOfWork.getArtistRepository().get(trackArtist.getArtist().getId()));
            newArtists.add(trackArtist);
        }
        setArtists(newArtists);
        
        if (existingArtists != null) {
            for (TrackArtist trackArtist : existingArtists) {
                unitOfWork.getTrackArtistRepository().delete(trackArtist);
            }
        }
    }

}
