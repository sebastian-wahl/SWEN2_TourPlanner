package at.fhtw.swen2_tourplanner.frontend.listener;

import at.fhtw.swen2_tourplanner.frontend.viewmodel.modelobjects.Tour;

import java.io.File;

public interface ExportTourReportListener {
    void exportTourReport(File file, Tour toExport);
}
