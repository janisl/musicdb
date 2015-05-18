package janisl.musicdb.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.sql.Time;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Track implements Serializable {

    private Integer id;
    private String name;
    private Integer beatportId;
    private String beatportUrl;
    private String version;
    private Integer bpm;
    private Integer artistId;
    private Integer releaseId;
    private Integer keyId;
    private Time duration;
    private Integer genreId;

    public Track() {
    }

    public Track(String name) {
        this.name = name;
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

    public String getBeatportUrl() {
        return beatportUrl;
    }

    public void setBeatportUrl(String beatportUrl) {
        this.beatportUrl = beatportUrl;
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

    public Integer getArtistId() {
        return artistId;
    }

    public void setArtistId(Integer artistId) {
        this.artistId = artistId;
    }

    public Integer getReleaseId() {
        return releaseId;
    }

    public void setReleaseId(Integer releaseId) {
        this.releaseId = releaseId;
    }

    public Integer getKeyId() {
        return keyId;
    }

    public void setKeyId(Integer keyId) {
        this.keyId = keyId;
    }

    public Time getDuration() {
        return duration;
    }

    public void setDuration(Time duration) {
        this.duration = duration;
    }

    public Integer getGenreId() {
        return genreId;
    }

    public void setGenreId(Integer genreId) {
        this.genreId = genreId;
    }
}