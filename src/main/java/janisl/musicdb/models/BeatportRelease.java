package janisl.musicdb.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BeatportRelease implements Serializable, BeatportEntity {

    private Integer id;
    private String slug;
    private String title;
    private String imageUrl;
    private Date releaseDate;
    private BeatportLabel label;
    private String catalogNumber;
    private String description;
    private Boolean isExclusive;
    private Set<BeatportTrack> tracks = new HashSet<>(0);

    public BeatportRelease() {
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

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "labelId")
    public BeatportLabel getLabel() {
        return label;
    }

    public void setLabel(BeatportLabel label) {
        this.label = label;
    }

    public String getCatalogNumber() {
        return catalogNumber;
    }

    public void setCatalogNumber(String catalogNumber) {
        this.catalogNumber = catalogNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isIsExclusive() {
        return isExclusive;
    }

    public void setIsExclusive(Boolean isExclusive) {
        this.isExclusive = isExclusive;
    }

    @OneToMany(mappedBy = "release")
    @JsonIgnore
    public Set<BeatportTrack> getTracks() {
        return tracks;
    }

    public void setTracks(Set<BeatportTrack> tracks) {
        this.tracks = tracks;
    }
}
