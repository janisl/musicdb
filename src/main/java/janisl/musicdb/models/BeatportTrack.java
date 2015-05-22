package janisl.musicdb.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.sql.Time;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BeatportTrack implements Serializable, BeatportEntity {

    private Integer id;
    private String slug;
    private BeatportRelease release;
    private Integer orderNumber;
    private String title;
    private String remix;
    private BeatportGenre genre;
    private Integer bpm;
    private String keytone;
    private Time duration;
    private Set<BeatportArtist> artists = new HashSet<>(0);
    private Set<BeatportArtist> remixers = new HashSet<>(0);

    public BeatportTrack() {
    }

    @Id
    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String getSlug() {
        return slug;
    }

    @Override
    public void setSlug(String slug) {
        this.slug = slug;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "releaseId")
    @JsonIgnore
    public BeatportRelease getRelease() {
        return release;
    }

    public void setRelease(BeatportRelease release) {
        this.release = release;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRemix() {
        return remix;
    }

    public void setRemix(String remix) {
        this.remix = remix;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "genreId")
    public BeatportGenre getGenre() {
        return genre;
    }

    public void setGenre(BeatportGenre genre) {
        this.genre = genre;
    }

    public Integer getBpm() {
        return bpm;
    }

    public void setBpm(Integer bpm) {
        this.bpm = bpm;
    }

    public String getKeytone() {
        return keytone;
    }

    public void setKeytone(String keytone) {
        this.keytone = keytone;
    }

    public Time getDuration() {
        return duration;
    }

    public void setDuration(Time duration) {
        this.duration = duration;
    }

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "BeatportTrackArtist",
            joinColumns = {
                @JoinColumn(name = "trackId", nullable = false, updatable = false)},
            inverseJoinColumns = {
                @JoinColumn(name = "artistId", nullable = false, updatable = false)})
    public Set<BeatportArtist> getArtists() {
        return artists;
    }

    public void setArtists(Set<BeatportArtist> artists) {
        this.artists = artists;
    }

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "BeatportTrackRemixer",
            joinColumns = {
                @JoinColumn(name = "trackId", nullable = false, updatable = false)},
            inverseJoinColumns = {
                @JoinColumn(name = "artistId", nullable = false, updatable = false)})
    public Set<BeatportArtist> getRemixers() {
        return remixers;
    }

    public void setRemixers(Set<BeatportArtist> remixers) {
        this.remixers = remixers;
    }

}
