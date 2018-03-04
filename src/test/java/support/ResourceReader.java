package support;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

/**
 * Created by MOHAMMADSHAH.ALAM on 3/3/2018.
 */
public class ResourceReader {
    public static String readFixture(String fileWhichExistsInResources) {

        URL url = ResourceReader.class.getResource("/" + fileWhichExistsInResources);
        return getFile(fileWhichExistsInResources, url);
    }

    private static String getFile(String fileWhichExistsInResources, URL url) {
        try {
            if (url == null) {
                throw new FileNotFoundException(fileWhichExistsInResources);
            }

            Path path = Paths.get(url.toURI());

            return Files.readAllLines(path).stream()
                    .collect(Collectors.joining("\n"));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }
}
