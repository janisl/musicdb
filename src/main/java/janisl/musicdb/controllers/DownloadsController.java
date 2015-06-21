package janisl.musicdb.controllers;

import janisl.musicdb.models.Download;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DownloadsController {
    
    @Value("${downloadsDirectory}")
    private String downloadsDirectory;
    
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public List<Download> getList() throws Exception {
        ArrayList<Download> list = new ArrayList<>();
        File folder = new File(downloadsDirectory);
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            if (file.isFile()) {
                list.add(new Download(file));
            }
        }
        
        return list;
    }

}
