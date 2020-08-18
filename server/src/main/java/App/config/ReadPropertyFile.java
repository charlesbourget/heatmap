package App.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReadPropertyFile {
    private static ReadPropertyFile readPropertyFile = null;

    private String gpxDir;

    private ReadPropertyFile() {
    }

    /**
     * Get instance of Reader and read config file if it's wasn't already done
     *
     * @return Current instance of reader
     * @throws IOException Error while reading properties file
     */
    public static ReadPropertyFile getInstance() throws IOException {
        if (readPropertyFile == null) {
            synchronized (ReadPropertyFile.class) {
                readPropertyFile = new ReadPropertyFile();
                readPropertyFile.readConfigFile();
            }
        }

        return readPropertyFile;
    }

    /**
     * Extract DB connection info from properties file
     *
     * @throws IOException Error while reading properties file
     */
    private void readConfigFile() throws IOException {
        Properties prop = new Properties();

        InputStream iStream = ReadPropertyFile.class.getResourceAsStream("/application.properties");

        prop.load(iStream);

        gpxDir = prop.getProperty("server.gpx.dir");
    }

    public String getGpxDir() {
        return gpxDir;
    }
}
