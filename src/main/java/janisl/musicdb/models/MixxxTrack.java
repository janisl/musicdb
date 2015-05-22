package janisl.musicdb.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "library")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class MixxxTrack implements Serializable {

    private Integer id;
    private String artist;
    private String title;
    private String album;
    private String year;
    private String genre;
    private String trackNumber;
    private MixxxTrackLocation location;
    private String comment;
    private Integer duration;
    private Integer bitRate;
    private Integer sampleRate;
    private Integer cuePoint;
    private Float bpm;
    private Integer channels;
    //private Timestamp dateTimeAdded;
    private Integer mixxxDeleted;
    private String fileType;
    private Integer timesPlayed;
    private Integer rating;
    private String key;
    private String composer;
    private Integer bpmLock;

    public MixxxTrack() {
    }

    @Id
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(String trackNumber) {
        this.trackNumber = trackNumber;
    }

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "location")
    public MixxxTrackLocation getLocation() {
        return location;
    }

    public void setLocation(MixxxTrackLocation location) {
        this.location = location;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getBitRate() {
        return bitRate;
    }

    public void setBitRate(Integer bitRate) {
        this.bitRate = bitRate;
    }

    public Integer getSampleRate() {
        return sampleRate;
    }

    public void setSampleRate(Integer sampleRate) {
        this.sampleRate = sampleRate;
    }

    public Integer getCuePoint() {
        return cuePoint;
    }

    public void setCuePoint(Integer cuePoint) {
        this.cuePoint = cuePoint;
    }

    public Float getBpm() {
        return bpm;
    }

    public void setBpm(Float bpm) {
        this.bpm = bpm;
    }

    public Integer getChannels() {
        return channels;
    }

    public void setChannels(Integer channels) {
        this.channels = channels;
    }

    /*@Column(name = "datetime_added")
    public Timestamp getDateTimeAdded() {
        return dateTimeAdded;
    }

    public void setDateTimeAdded(Timestamp dateTimeAdded) {
        this.dateTimeAdded = dateTimeAdded;
    }*/

    @Column(name = "mixxx_deleted")
    public Integer getMixxxDeleted() {
        return mixxxDeleted;
    }

    public void setMixxxDeleted(Integer mixxxDeleted) {
        this.mixxxDeleted = mixxxDeleted;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Integer getTimesPlayed() {
        return timesPlayed;
    }

    public void setTimesPlayed(Integer timesPlayed) {
        this.timesPlayed = timesPlayed;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getComposer() {
        return composer;
    }

    public void setComposer(String composer) {
        this.composer = composer;
    }

    @Column(name = "bpm_lock")
    public Integer getBpmLock() {
        return bpmLock;
    }

    public void setBpmLock(Integer bpmLock) {
        this.bpmLock = bpmLock;
    }

}
