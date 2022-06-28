package at.fhtw.swen2_tourplanner.frontend.util;

import at.fhtw.swen2_tourplanner.frontend.util.exceptions.FileConvertException;
import at.fhtw.swen2_tourplanner.frontend.viewmodel.modelobjects.Tour;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class FileConverter {

    public static List<Tour> convertFileToTour(List<File> files) throws FileConvertException {
        ObjectMapper o = new ObjectMapper();
        o.registerModule(new JavaTimeModule());

        List<Tour> out = new ArrayList<>();
        for (File file : files) {
            try {
                out.add(o.readValue(file, Tour.class));
            } catch (IOException e) {
                log.warn("Exception while converting file \"{}\"", file.getName(), e);
                throw new FileConvertException("Exception while converting file " + file.getName() + "\n" + e.getMessage());
            }
        }

        return out;
    }
}
