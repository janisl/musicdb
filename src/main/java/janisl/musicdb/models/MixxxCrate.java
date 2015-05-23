package janisl.musicdb.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "crates")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class MixxxCrate implements Serializable {

    private Integer id;
    private String name;
    private Integer count;
    private Integer show;
    private Integer locked;
    private Set<MixxxTrack> tracks = new HashSet<>(0);

    public MixxxCrate() {
    }

    @Id
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getShow() {
        return show;
    }

    public void setShow(Integer show) {
        this.show = show;
    }

    public Integer getLocked() {
        return locked;
    }

    public void setLocked(Integer locked) {
        this.locked = locked;
    }

    @ManyToMany
    @JoinTable(name = "crate_tracks",
            joinColumns = { @JoinColumn(name = "crate_id", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "track_id", nullable = false, updatable = false)})
    @JsonIgnore
    public Set<MixxxTrack> getTracks() {
        return tracks;
    }

    public void setTracks(Set<MixxxTrack> tracks) {
        this.tracks = tracks;
    }

}
