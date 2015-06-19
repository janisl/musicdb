package janisl.musicdb;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class MyFileUtils {

    public static String fixName(String name) {
        // Directories ending with dots will cause problems for Windows utils.
        while (name.endsWith(".") || name.endsWith(" ")) {
            name = name.substring(0, name.length() - 1);
        }
        return name.replace('/', '+').replace('\\', '+').replace('?', '_').replace('*', '_').replace(':', '-').replace('|', '+');
    }

    public static void removeFileAndParentsIfEmpty(Path path) throws IOException {
        if (path == null) {
            return;
        }
        if (Files.isRegularFile(path)) {
            Files.deleteIfExists(path);
        } else if (Files.isDirectory(path)) {
            if (path.toFile().list().length == 0) {
                Files.delete(path);
                while (Files.exists(path));
            } else {
                return;
            }
        } else {
            return;
        }
        removeFileAndParentsIfEmpty(path.getParent());
    }
}
