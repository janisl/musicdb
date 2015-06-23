package janisl.musicdb.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import janisl.musicdb.MyFileUtils;
import janisl.musicdb.repositories.UnitOfWork;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
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
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.apache.commons.io.FileUtils;

@Entity
@Table(name = "Release_")
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
    private ReleaseImportStatus importStatus;
    private String path;
    private String coverLocation;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "importStatusId")
    public ReleaseImportStatus getImportStatus() {
        return importStatus;
    }

    public void setImportStatus(ReleaseImportStatus importStatus) {
        this.importStatus = importStatus;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "release")
    @OrderBy("position")
    public Set<Track> getTracks() {
        return tracks;
    }

    public void setTracks(Set<Track> tracks) {
        this.tracks = tracks;
    }

    @OneToMany(mappedBy = "release", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @OrderBy("orderNumber")
    public Set<ReleaseDetailsArtist> getArtists() {
        return artists;
    }

    public void setArtists(Set<ReleaseDetailsArtist> artists) {
        this.artists = artists;
    }

    public String getCoverLocation() {
        return coverLocation;
    }

    public void setCoverLocation(String coverLocation) {
        this.coverLocation = coverLocation;
    }

    @Transient
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void resolveReferences(UnitOfWork unitOfWork, Set<ReleaseDetailsArtist> existingArtists) {
        if (getLabel() != null && getLabel().getId() != null) {
            setLabel(unitOfWork.getLabelRepository().get(getLabel().getId()));
        } else {
            setLabel(null);
        }

        if (getImportStatus() != null && getImportStatus().getId() != null) {
            setImportStatus(unitOfWork.getReleaseImportStatusRepository().get(getImportStatus().getId()));
        } else {
            setImportStatus(null);
        }

        Set<ReleaseDetailsArtist> newArtists = new HashSet<>(0);
        int artistOrderNumber = 0;
        for (ReleaseDetailsArtist newArtist : getArtists()) {
            ReleaseDetailsArtist releaseArtist = null;
            if (newArtist.getId() != null && existingArtists != null) {
                for (ReleaseDetailsArtist check : existingArtists) {
                    if (Objects.equals(check.getId(), newArtist.getId())) {
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

    public String calculateFullArtistName() {
        List<ReleaseDetailsArtist> tmpArtists = new ArrayList<>(getArtists());
        if (tmpArtists.isEmpty()) {
            return "";
        }
        String artistString = tmpArtists.get(0).getArtist().getName();
        for (int i = 1; i < tmpArtists.size(); i++) {
            if (tmpArtists.get(i - 1).getJoinText() == null || "".equals(tmpArtists.get(i - 1).getJoinText())) {
                artistString += ", ";
            } else {
                artistString += " " + tmpArtists.get(i - 1).getJoinText() + " ";
            }
            artistString += tmpArtists.get(i).getArtist().getName();
        }
        return artistString;
    }

    public String calculatePath() {
        String tmpPath;
        if (getArtists().isEmpty()) {
            tmpPath = "/home/janis/music/various/";
        } else {
            tmpPath = ((ReleaseDetailsArtist) getArtists().toArray()[0]).calculatePath() + "/";
        }
        String releaseDirName = getReleaseDate().toString() + " ";
        if (getArtists().size() > 1) {
            releaseDirName += calculateFullArtistName() + " - ";
        }
        releaseDirName += name;
        return tmpPath + MyFileUtils.fixName(releaseDirName);
    }

    public void createDir() throws IOException {
        Files.createDirectories(Paths.get(calculatePath()));
    }

    public void setCover(String url) throws Exception {
        if (getCoverLocation() != null && !getCoverLocation().isEmpty()) {
            Files.deleteIfExists(Paths.get(getCoverLocation()));
        } else {
            setCoverLocation(calculatePath() + "/cover.jpg");
        }

        Files.createDirectories(Paths.get(getCoverLocation()).getParent());
        FileUtils.copyURLToFile(new URL(url), new File(getCoverLocation()));
    }
    
    public void moveCover() throws Exception {
        if (getCoverLocation() == null || getCoverLocation().isEmpty()) {
            return;
        }

        String newLocation = calculatePath() + "/cover.jpg";
        if (getCoverLocation().equals(newLocation)) {
            return;
        }

        Files.createDirectories(Paths.get(newLocation).getParent());
        FileUtils.moveFile(new File(getCoverLocation()), new File(newLocation));
        MyFileUtils.removeFileAndParentsIfEmpty(Paths.get(getCoverLocation()).getParent());

        setCoverLocation(newLocation);
    }
    
    public void moveTracks() throws Exception {
        String baseLocation = calculatePath();
        boolean artistPerTrack = false;
        for (Track track : getTracks()) {
            if (track.getArtists().size() > 0) {
                artistPerTrack = true;
                break;
            }
        }
        
        for (Track track : getTracks()) {
            track.move(baseLocation, artistPerTrack);
        }
    }
}
