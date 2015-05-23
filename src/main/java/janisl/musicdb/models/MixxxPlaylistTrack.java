package janisl.musicdb.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "playlisttracks")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class MixxxPlaylistTrack implements Serializable {

    private Integer id;
    private Integer playlistId;
    private MixxxTrack track;
    private Integer position;

    public MixxxPlaylistTrack() {
    }

    @Id
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "playlist_id")
    public Integer getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(Integer playlistId) {
        this.playlistId = playlistId;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "track_id")
    public MixxxTrack getTrack() {
        return track;
    }

    public void setTrack(MixxxTrack track) {
        this.track = track;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

}
