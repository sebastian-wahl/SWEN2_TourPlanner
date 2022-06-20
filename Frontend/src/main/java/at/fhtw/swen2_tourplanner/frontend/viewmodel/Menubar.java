package at.fhtw.swen2_tourplanner.frontend.viewmodel;

import at.fhtw.swen2_tourplanner.frontend.listener.ExportTourSummaryListener;
import at.fhtw.swen2_tourplanner.frontend.listener.ImportTourListener;
import lombok.Setter;

import java.io.File;
import java.util.List;

public class Menubar implements ViewModel {

    @Setter
    private ImportTourListener importTourListener;
    @Setter
    private ExportTourSummaryListener exportTourSummaryListener;

    public void exportTourSummary(File file) {
        this.exportTourSummaryListener.exportTourSummary(file);
    }

    public void importTour(List<File> fileList) {
        this.importTourListener.importTour(fileList);
    }
}
