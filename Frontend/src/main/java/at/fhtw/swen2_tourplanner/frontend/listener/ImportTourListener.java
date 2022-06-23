package at.fhtw.swen2_tourplanner.frontend.listener;

import java.io.File;
import java.util.List;

public interface ImportTourListener {
    void importTour(List<File> toImport);
}
