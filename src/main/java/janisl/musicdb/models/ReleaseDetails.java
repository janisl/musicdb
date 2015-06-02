package janisl.musicdb.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import janisl.musicdb.repositories.UnitOfWork;
import java.io.Serializable;
import java.sql.Date;
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
import javax.persistence.Table;

@Entity
@Table(name="Release_")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ReleaseDetails implements Serializable {

    private Integer id;
    private String name;
    private Integer beatportId;
    private Integer discogsId;
    private Label label;
    private String catalogNumber;
    private Date releaseDate;
    private Set<ReleaseDetailsArtist> artists = new HashSet<>(0);
    private Set<Track> tracks = new HashSet<>(0);

    public ReleaseDetails() {
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

    public Integer getDiscogsId() {
        return discogsId;
    }

    public void setDiscogsId(Integer discogsId) {
        this.discogsId = discogsId;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "labelId")
    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public String getCatalogNumber() {
        return catalogNumber;
    }

    public void setCatalogNumber(String catalogNumber) {
        this.catalogNumber = catalogNumber;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "release")
    public Set<Track> getTracks() {
        return tracks;
    }

    public void setTracks(Set<Track> tracks) {
        this.tracks = tracks;
    }

    @OneToMany(mappedBy = "release", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Set<ReleaseDetailsArtist> getArtists() {
        return artists;
    }

    public void setArtists(Set<ReleaseDetailsArtist> artists) {
        this.artists = artists;
    }

    public void resolveReferences(UnitOfWork unitOfWork, Set<ReleaseDetailsArtist> existingArtists) {
        if (getLabel() != null && getLabel().getId() != null)
            setLabel(unitOfWork.getLabelRepository().get(getLabel().getId()));
        else
            setLabel(null);
        
        Set<ReleaseDetailsArtist> newArtists = new HashSet<>(0);
        for (ReleaseDetailsArtist newArtist : getArtists()) {
            ReleaseDetailsArtist releaseArtist = null;
            if (newArtist.getId() != null && existingArtists != null) {
                for (ReleaseDetailsArtist check : existingArtists) {
                    if (check.getId() == newArtist.getId()) {
                        releaseArtist = check;
                        releaseArtist.setArtist(newArtist.getArtist());
                        releaseArtist.setOrderNumber(newArtist.getOrderNumber());
                        releaseArtist.setJoinText(newArtist.getJoinText());
                        existingArtists.remove(check);
                        break;
                    }
                }
            }
            if (releaseArtist == null) {
                releaseArtist = newArtist;
                releaseArtist.setRelease(this);
            }
            releaseArtist.setArtist(unitOfWork.getArtistRepository().get(releaseArtist.getArtist().getId()));
            newArtists.add(releaseArtist);
        }
        setArtists(newArtists);
        
        if (existingArtists != null) {
            for (ReleaseDetailsArtist releaseArtist : existingArtists) {
                unitOfWork.getReleaseArtistRepository().delete(releaseArtist);
            }
        }
    }
}
