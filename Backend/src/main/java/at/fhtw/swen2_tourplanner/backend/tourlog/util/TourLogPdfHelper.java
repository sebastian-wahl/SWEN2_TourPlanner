package at.fhtw.swen2_tourplanner.backend.tourlog.util;

import at.fhtw.swen2_tourplanner.backend.tour.dto.TourDTO;
import at.fhtw.swen2_tourplanner.backend.tourlog.dto.TourLogDTO;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TourLogPdfHelper {

    private final String ABSOLUTE_PDF_PATH;
    private final String PDF_SUFFIX = ".pdf";
    private final String SUMMARY_REPORT_NAME = "summary_report";

    public TourLogPdfHelper(@Value("${pdf.path.prefix}") String[] pathValues) {
        ABSOLUTE_PDF_PATH = Paths.get(pathValues[0], pathValues[1], pathValues[2], pathValues[3], pathValues[4]).toFile().getAbsolutePath() + "\\";
    }

    public void createTourReport(TourDTO tour, List<TourLogDTO> tourLogs) throws FileNotFoundException {
        PdfWriter writer = new PdfWriter(ABSOLUTE_PDF_PATH + tour.getName() + PDF_SUFFIX);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);
        document.add(new Paragraph("Hello World!"));
        document.close();
    }

    public void createSummaryReport(HashMap<TourDTO, List<TourLogDTO>> allToursAndLogs) throws IOException {
        PdfWriter writer = new PdfWriter(ABSOLUTE_PDF_PATH + SUMMARY_REPORT_NAME + PDF_SUFFIX);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        for (Map.Entry<TourDTO, List<TourLogDTO>> set : allToursAndLogs.entrySet()) {
            TourDTO tour = set.getKey();
            List<TourLogDTO> tourLogs = set.getValue();

            final double avgTime = tourLogs.stream().mapToInt(a -> a.getDateTime().getHour() * 60 + a.getDateTime().getMinute()).average().orElse(0);
            final double avgDistance = tourLogs.stream().mapToLong(a -> a.getDistance()).average().orElse(0);
            final double avgRating = tourLogs.stream().mapToDouble(a -> a.getRating()).average().orElse(0);

            Paragraph tourInfo = new Paragraph("Average Time: " + avgTime / 60.0 + "\nAverage Distance: " + avgDistance + "\nAverage Rating" + avgRating);

            Paragraph average = new Paragraph("Average Time: " + avgTime / 60.0 + "\nAverage Distance: " + avgDistance + "\nAverage Rating" + avgRating);
            document.add(average);
        }

        document.close();
    }

    public void addTourContent(Document document, TourDTO tour, List<TourLogDTO> tourLogs) throws IOException {
        document.add(new Paragraph("Hello World!"));
    }
}
