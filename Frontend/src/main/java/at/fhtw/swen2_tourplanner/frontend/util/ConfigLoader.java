package at.fhtw.swen2_tourplanner.frontend.util;

import lombok.extern.log4j.Log4j2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

@Log4j2
public class ConfigLoader {
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
            log.error("Config file not found: {}", e.getMessage());
        }
        return "";
    }
}
