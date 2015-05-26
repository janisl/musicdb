package janisl.musicdb.beatport;

import janisl.musicdb.models.BeatportLabel;
import janisl.musicdb.repositories.UnitOfWork;
import java.io.IOException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class LabelParser {
    
    private final UnitOfWork unitOfWork;
    private BeatportLabel label;
    private Boolean isExisting = false;

    public LabelParser(UnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    public BeatportLabel parseUrl(String url) throws IOException {
        createFromUrl(url);
        if (checkForExisting()) {
            downloadAndParseBeatportPage();
            save();
        }
        return label;
    }

    public void reimport(BeatportLabel label) throws IOException {
        this.label = label;
        downloadAndParseBeatportPage();
        save();
    }
    
    public void importReleases(BeatportLabel label) throws IOException {
        this.label = label;
        BeatportUtils.parseReleasesPage(BeatportUtils.downloadPage("label", label, "/releases"), unitOfWork);
    }
    
    private void createFromUrl(String url) throws BeatportInvalidPathException {
        label = new BeatportLabel();
        if (!"label".equals(BeatportUtils.parseBeatportUrl(url, label))) {
            throw new BeatportInvalidPathException();
        }
    }

    private Boolean checkForExisting() {
        BeatportLabel existingLabel = unitOfWork.getBeatportLabelRepository().get(label.getId());
        if (existingLabel == null) {
            return true;
        }
        Boolean needsRefresh = (existingLabel.getSlug() == null ? label.getSlug() != null : !existingLabel.getSlug().equals(label.getSlug()));
        existingLabel.setSlug(label.getSlug());
        label = existingLabel;
        isExisting = true;
        return needsRefresh;
    }

    private void downloadAndParseBeatportPage() throws IOException {
        Document doc = BeatportUtils.downloadPage("label", label);
        Element mainElement = doc.select("main").first();
        label.setName(mainElement.select("h1").first().text());
        label.setImageUrl(mainElement.select("img.interior-label-artwork").first().attr("src"));
    }

    private void save() {
        if (isExisting) {
            unitOfWork.getBeatportLabelRepository().update(label);
        } else {
            unitOfWork.getBeatportLabelRepository().add(label);
        }
    }
}
