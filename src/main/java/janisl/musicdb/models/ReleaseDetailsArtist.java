package janisl.musicdb.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import janisl.musicdb.MyFileUtils;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "ReleaseArtist")
public class ReleaseDetailsArtist implements Serializable {

    private Integer id;
    private ReleaseDetails release;
    private Artist artist;
    private Integer orderNumber;
    private String joinText;

    public ReleaseDetailsArtist() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne()
    @JoinColumn(name = "releaseId", nullable = false, updatable = false)
    @JsonIgnore
    public ReleaseDetails getRelease() {
        return release;
    }

    public void setRelease(ReleaseDetails release) {
        this.release = release;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "artistId")
    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getJoinText() {
        return this.joinText;
    }

    public void setJoinText(String joinText) {
        this.joinText = joinText;
    }
    
    public String calculatePath() {
        return "/home/janis/music/artists/" + MyFileUtils.fixName(artist.getName());
    }

}
