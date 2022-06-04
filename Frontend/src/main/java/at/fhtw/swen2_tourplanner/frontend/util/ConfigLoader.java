package at.fhtw.swen2_tourplanner.frontend.util;

import at.fhtw.swen2_tourplanner.frontend.service.MapService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public class ConfigLoader {
    static Logger logger = LogManager.getLogger(MapService.class);

    public static String getValue(String name) {
        if (name == null || name.equals("")) {
            return "";
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(ConfigLoader.class.getResourceAsStream("/config.txt"))))) {
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                // comment
                if (sCurrentLine.startsWith("#")) {
                    continue;
                }
                if (sCurrentLine.contains(name)) {
                    return sCurrentLine.substring(sCurrentLine.indexOf("=") + 1);
                }
            }
        } catch (NullPointerException | IOException e) {
            logger.error("Config file not found: {}", e.getMessage());
        }
        return "";
    }
}
