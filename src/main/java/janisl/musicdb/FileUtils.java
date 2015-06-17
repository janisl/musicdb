package janisl.musicdb;

public class FileUtils {

    public static String fixName(String name) {
        // Directories ending with dots will cause problems for Windows utils.
        while (name.endsWith(".") || name.endsWith(" "))
            name = name.substring(0, name.length() - 1);
        return name.replace('/', '+').replace('\\', '+').replace('?', '_').replace('*', '_').replace(':', '-').replace('|', '+');
    }
    
}
