package at.fhtw.swen2_tourplanner.frontend.listener;

import at.fhtw.swen2_tourplanner.frontend.viewmodel.modelobjects.Tour;

import java.io.File;

public interface ExportTourListener {
    void exportTour(File file, Tour toExport);
}
