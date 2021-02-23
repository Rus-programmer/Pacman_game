package hr.kn.pacman.server.game;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ConfigParser {
    private static final Map<String, Integer> configMap = new HashMap<>();
    private static String filed;

    public static Map<String, Integer> getConfigMap() {
        return configMap;
    }

    public static String getFiled() {
        return filed;
    }

    public static void setFiled(String filed) {
        ConfigParser.filed = filed;
    }

    public void readGameConfig() throws FileNotFoundException, URISyntaxException {
        int countWidth = 0, countHeight = 0, i = 0;
        URL res = getClass().getClassLoader().getResource("config.txt");
        File file = Paths.get(res.toURI()).toFile();
        InputStream inputStream = new FileInputStream(file);
        String streamConfig = new BufferedReader(new InputStreamReader(inputStream))
                .lines().collect(Collectors.joining("\n"));
        StringBuilder config = new StringBuilder(streamConfig);
        if (config.indexOf("height") != -1) {
            configMap.put("height", Integer.parseInt(valueParser(config, "height")));
        }
        if (config.indexOf("width") != -1) {
            configMap.put("width", Integer.parseInt(valueParser(config, "width")));
        }
        if (config.indexOf("pacmanX") != -1) {
            configMap.put("pacmanX", Integer.parseInt(valueParser(config, "pacmanX")));
        }
        if (config.indexOf("pacmanY") != -1) {
            configMap.put("pacmanY", Integer.parseInt(valueParser(config, "pacmanY")));
        }
        if (config.indexOf("map") != -1) {
            filed = valueParser(config, "map");
        }
        if (config.indexOf("speed") != -1) {
            configMap.put("speed", Integer.parseInt(valueParser(config, "speed")));
        }
        if (config.indexOf("countWidth") != -1) {
            countWidth = Integer.parseInt(valueParser(config, "countWidth"));
            configMap.put("countWidth", countWidth);
        }
        if (config.indexOf("countHeight") != -1) {
            countHeight = Integer.parseInt(valueParser(config, "countHeight"));
            configMap.put("countHeight", countHeight);
        }
    }

    private String valueParser(StringBuilder config, String value) {
        String s = config.substring(config.indexOf(value) + value.length() + 1,
                config.indexOf("]", config.indexOf(value)));
        config.delete(config.indexOf(value),
                config.indexOf("]", config.indexOf(value)) + 2);
        return s;
    }
}
