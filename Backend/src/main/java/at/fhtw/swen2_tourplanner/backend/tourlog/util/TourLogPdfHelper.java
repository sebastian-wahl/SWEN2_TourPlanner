package at.fhtw.swen2_tourplanner.backend.tourlog.util;

import at.fhtw.swen2_tourplanner.backend.tour.dto.TourDTO;
import at.fhtw.swen2_tourplanner.backend.tourlog.dto.TourLogDTO;
import at.fhtw.swen2_tourplanner.backend.util.BusinessException;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;


@Component
public class TourLogPdfHelper {

    private final String ABSOLUTE_PDF_PATH;
    public final String ABSOLUTE_IMAGE_PATH;
    private static final String PDF_SUFFIX = ".pdf";
    private static final String SUMMARY_REPORT_NAME = "summary_report";

    public TourLogPdfHelper(@Value("${pdf.path.prefix}") String[] pathValues, @Value("${image.path.prefix}") String[] imagePathValues) {
        ABSOLUTE_PDF_PATH = Paths.get(pathValues[0], pathValues[1], pathValues[2], pathValues[3], pathValues[4]).toFile().getAbsolutePath() + '\\';
        ABSOLUTE_IMAGE_PATH = Paths.get(imagePathValues[0], imagePathValues[1], imagePathValues[2], imagePathValues[3], imagePathValues[4]).toFile().getAbsolutePath() + '\\';
    }

    public void createTourReport(TourDTO tour, List<TourLogDTO> tourLogs) throws FileNotFoundException, MalformedURLException {
        if (tourHasRequiredValues(tour)) {
            PdfWriter writer = new PdfWriter(ABSOLUTE_PDF_PATH + tour.getId() + PDF_SUFFIX);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            generateTourReport(tour, tourLogs, document);
            document.close();
        } else {
            throw new BusinessException("Document could not be created. Tour is incomplete");
        }
    }

    public void createSummaryReport(Map<TourDTO, List<TourLogDTO>> allToursAndLogs) throws IOException {
        PdfWriter writer = new PdfWriter(ABSOLUTE_PDF_PATH + SUMMARY_REPORT_NAME + PDF_SUFFIX);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        for (Map.Entry<TourDTO, List<TourLogDTO>> set : allToursAndLogs.entrySet()) {
            TourDTO tour = set.getKey();
            List<TourLogDTO> tourLogs = set.getValue();

            if (tourHasRequiredValues(tour)) {
                generateTourReport(tour, tourLogs, document);
            }
        }

        document.close();
    }

    public byte[] getPdfFile(String filename) throws FileNotFoundException {
        if (filename != null) {
            try {
                File file = new File(ABSOLUTE_PDF_PATH + '\\' + filename + PDF_SUFFIX);
                return Files.readAllBytes(file.toPath());
            } catch (IOException e) {
                throw new FileNotFoundException("PDF File not found");
            }
        }
        throw new BusinessException("No filename supplied");
    }

    private void generateTourReport(TourDTO tour, List<TourLogDTO> tourLogs, Document document) throws MalformedURLException {
        final double avgTime = tourLogs.stream().mapToInt(a -> a.getDateTime().getHour() * 60 + a.getDateTime().getMinute()).average().orElse(0);
        final double avgDistance = tourLogs.stream().mapToDouble(TourLogDTO::getDistance).average().orElse(0);
        final double avgRating = tourLogs.stream().mapToDouble(TourLogDTO::getRating).average().orElse(0);

        tourSummary(document, tour);

        Paragraph average = new Paragraph("Amount of tours: " + tourLogs.size() + "\nAverage Time: " + avgTime / 60.0 + "\nAverage Distance: " + avgDistance + "\nAverage Rating: " + avgRating);
        document.add(average);
    }

    private void tourSummary(Document doc, TourDTO tour) throws MalformedURLException {
        final String name = "Name: " + tour.getName() + "\n";
        final String start = "Start: " + tour.getStart() + "\n";
        final String goal = "Goal: " + tour.getGoal() + "\n";
        final String desc = "Description: " + tour.getTourDescription() + "\n";
        final String time = "Estimated Time: " + tour.getEstimatedTime() + "\n";
        final String type = "Transport Type: " + getTransportType(tour.getTransportType()) + "\n";
        final String distance = "Distance: " + tour.getTourDistance();
        ImageData data = ImageDataFactory.create(ABSOLUTE_IMAGE_PATH + tour.getId() + "_image.jpg");
        final Image image = new Image(data);
        Paragraph tourSummary = new Paragraph(name + start + goal + desc + time + type + distance);
        doc.add(tourSummary);
        doc.add(image);
    }

    private boolean tourHasRequiredValues(TourDTO tour) {
        return !tour.getName().isEmpty() && !tour.getStart().isEmpty() && !tour.getGoal().isEmpty();
    }

    private String getTransportType(int type) {
        return switch (type) {
            case 0 -> "Foot";
            case 1 -> "Car";
            case 2 -> "Skateboard";
            default -> throw new IllegalStateException("Unexpected value: " + type);
        };
    }
}
