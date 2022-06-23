package at.fhtw.swen2_tourplanner.frontend.util;

import at.fhtw.swen2_tourplanner.frontend.util.exceptions.FileConvertException;
import at.fhtw.swen2_tourplanner.frontend.viewmodel.modelobjects.Tour;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileConverter {
    static Logger logger = LogManager.getLogger(FileConverter.class);

    public static List<Tour> convertFileToTour(List<File> files) throws FileConvertException {
        ObjectMapper o = new ObjectMapper();
        o.registerModule(new JavaTimeModule());

        List<Tour> out = new ArrayList<>();
        for (File file : files) {
            try {
                out.add(o.readValue(file, Tour.class));
            } catch (IOException e) {
                logger.warn("Exception while converting file \"{}\"", file.getName(), e);
                throw new FileConvertException("Exception while converting file " + file.getName() + "\n" + e.getMessage());
            }
        }

        return out;
    }
}
