package com.kryeit.votifier.config;


import com.kryeit.votifier.utils.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigReader {

    public static String HOST;
    public static int PORT;
    public static boolean DEBUG;
    public static String LISTENER_FOLDER;

    private ConfigReader() {

    }

    public static void readFile(Path path) throws IOException {
        String config = readOrCopyFile(path.resolve("votifier.json"), "/votifier.json");
        JSONObject configObject = new JSONObject(config);
        HOST = configObject.getString("host");
        PORT = Integer.parseInt(configObject.getString("port"));
        DEBUG = configObject.getBoolean("debug");
        LISTENER_FOLDER = configObject.getString("listener-folder");
    }

    public static String readOrCopyFile(Path path, String exampleFile) throws IOException {
        File file = path.toFile();
        if (!file.exists()) {
            InputStream stream = ConfigReader.class.getResourceAsStream(exampleFile);
            if (stream == null) throw new NullPointerException("Cannot load example file");

            //noinspection ResultOfMethodCallIgnored
            file.getParentFile().mkdirs();
            Files.copy(stream, path);
        }
        return Files.readString(path);
    }
}
