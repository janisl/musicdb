package janisl.musicdb.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BeatportArtist implements Serializable, BeatportEntity {

    private Integer id;
    private String slug;
    private String name;
    private String imageUrl;
    private Set<BeatportRelease> releases = new HashSet<>(0);

    public BeatportArtist() {
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

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @ManyToMany(mappedBy = "artists")
    @JsonIgnore
    public Set<BeatportRelease> getReleases() {
        return releases;
    }

    public void setReleases(Set<BeatportRelease> releases) {
        this.releases = releases;
    }

}
