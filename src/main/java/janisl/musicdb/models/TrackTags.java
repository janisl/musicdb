package janisl.musicdb.models;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.ID3v24Tag;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.NotSupportedException;
import com.mpatric.mp3agic.UnsupportedTagException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

public class TrackTags {

    private final Track track;
    private long length;
    private int bitRate;
    private int sampleRate;
    private boolean constantBitRate;
    private ID3v1 id3v1;

    public TrackTags(Track track) {
        this.track = track;
    }

    public void read() throws Exception {
        if (track.getLocation() == null || track.getLocation().isEmpty() || !"mp3".equals(FilenameUtils.getExtension(track.getLocation()))) {
            return;
        }
        Mp3File mp3file = new Mp3File(track.getLocation());
        setLength(mp3file.getLengthInSeconds());
        setBitRate(mp3file.getBitrate());
        setConstantBitRate(!mp3file.isVbr());
        setSampleRate(mp3file.getSampleRate());
        if (mp3file.hasId3v1Tag()) {
            setId3v1(mp3file.getId3v1Tag());
        }
        if (mp3file.hasId3v2Tag()) {
            setId3v2(mp3file.getId3v2Tag());
        }

    }

    public void write() throws Exception {
        String oldFile = track.getLocation() + "old";
        String newFile = track.getLocation() + "new";
        if (!write(track.getLocation(), newFile))
            return;
        FileUtils.moveFile(new File(track.getLocation()), new File(oldFile));
        FileUtils.moveFile(new File(newFile), new File(track.getLocation()));
        Files.delete(Paths.get(oldFile));
    }

    public boolean write(String srcFile, String dstFile) throws IOException, InvalidDataException, NotSupportedException, UnsupportedTagException {
        if (srcFile == null || srcFile.isEmpty() || !"mp3".equals(FilenameUtils.getExtension(srcFile))) {
            return false;
        }
        Mp3File mp3file = new Mp3File(srcFile);
        String trackNo = track.getPosition() == null ? "" : track.getPosition().toString();
        String title = track.calculateFullTitle();
        String artist = track.calculateFullArtistName();
        String album = track.getRelease() != null ? track.getRelease().getName() : "";
        String albumArtist = track.getRelease() != null ? track.getRelease().calculateFullArtistName() : "";
        String year = track.getRelease() != null && track.getRelease().getReleaseDate() != null ? String.format("%d", 1900 + track.getRelease().getReleaseDate().getYear()) : "";
        String genre = track.getGenre() != null ? track.getGenre().getName() : "";
        String comment = "";
        if (track.getRelease() != null) {
            if (track.getRelease().getLabel() != null) {
                comment += track.getRelease().getLabel().getName();
            }
            if (track.getRelease().getCatalogNumber() != null) {
                comment += " - " + track.getRelease().getCatalogNumber();
            }
        }
        if (mp3file.hasId3v1Tag()) {
            ID3v1 id3v1Tag = mp3file.getId3v1Tag();
            id3v1Tag.setTrack(trackNo);
            id3v1Tag.setArtist(artist);
            id3v1Tag.setTitle(title);
            id3v1Tag.setAlbum(album);
            id3v1Tag.setYear(year);
            id3v1Tag.setGenre("Trance".equals(genre) ? 31 : 0);
            id3v1Tag.setComment(comment);
        }
        ID3v2 id3v2Tag;
        if (mp3file.hasId3v2Tag()) {
            id3v2Tag = mp3file.getId3v2Tag();
        } else {
            // mp3 does not have an ID3v2 tag, let's create one..
            id3v2Tag = new ID3v24Tag();
            mp3file.setId3v2Tag(id3v2Tag);
        }
        id3v2Tag.setTrack(trackNo);
        id3v2Tag.setTitle(title);
        id3v2Tag.setArtist(artist);
        id3v2Tag.setAlbum(album);
        id3v2Tag.setAlbumArtist(albumArtist);
        id3v2Tag.setYear(year);
        id3v2Tag.setComposer(track.getComposer());
        id3v2Tag.setGenreDescription(genre);
        id3v2Tag.setBPM(track.getBpm() != null ? track.getBpm() : -1);
        id3v2Tag.setKey(track.getKey() != null ? track.getKey().getName() : "");
        id3v2Tag.setComment(comment);
        id3v2Tag.setGrouping(null);
        mp3file.save(dstFile);
        return true;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public int getBitRate() {
        return bitRate;
    }

    public void setBitRate(int bitRate) {
        this.bitRate = bitRate;
    }

    public int getSampleRate() {
        return sampleRate;
    }

    public void setSampleRate(int sampleRate) {
        this.sampleRate = sampleRate;
    }

    public boolean isConstantBitRate() {
        return constantBitRate;
    }

    public void setConstantBitRate(boolean constantBitRate) {
        this.constantBitRate = constantBitRate;
    }

    public ID3v1 getId3v1() {
        return id3v1;
    }

    public void setId3v1(ID3v1 id3v1) {
        this.id3v1 = id3v1;
    }

    private ID3v2 id3v2;

    public ID3v2 getId3v2() {
        return id3v2;
    }

    public void setId3v2(ID3v2 id3v2) {
        this.id3v2 = id3v2;
    }
}
