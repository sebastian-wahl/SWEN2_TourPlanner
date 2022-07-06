package at.fhtw.swen2_tourplanner.backend.tourlog.util;

import at.fhtw.swen2_tourplanner.backend.tour.dto.TourDTO;
import at.fhtw.swen2_tourplanner.backend.tourlog.dto.TourLogDTO;
import at.fhtw.swen2_tourplanner.backend.util.BusinessException;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.tagging.StandardRoles;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
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

    private static final String PDF_SUFFIX = ".pdf";
    private static final String SUMMARY_REPORT_NAME = "summary_report";
    public final String ABSOLUTE_IMAGE_PATH;
    private final String ABSOLUTE_PDF_PATH;

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

        boolean first = true;

        for (Map.Entry<TourDTO, List<TourLogDTO>> set : allToursAndLogs.entrySet()) {
            TourDTO tour = set.getKey();
            List<TourLogDTO> tourLogs = set.getValue();

            if (!first) {
                document.add(new AreaBreak());
            } else {
                first = false;
            }

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
        final double avgTime = tourLogs.stream().mapToInt(a -> a.getDateTime() != null ? a.getDateTime().getHour() * 3600 + a.getDateTime().getMinute() * 60 + a.getDateTime().getSecond() : 0).average().orElse(0);
        final double avgDistance = tourLogs.stream().mapToDouble(TourLogDTO::getDistance).average().orElse(0);
        final double avgRating = tourLogs.stream().mapToDouble(TourLogDTO::getRating).average().orElse(0);
        final String formattedTime = String.format("%02d", ((int) (avgTime / 3600))) + ":" + String.format("%02d", ((int) (avgTime / 60) % 60)) + ":" + String.format("%02d", ((int) avgTime % 60));

        tourSummary(document, tour);

        final float[] pointColumnWidths = {160F, 150F, 150F, 150F};
        Table table = new Table(pointColumnWidths);
        table.addCell(new Cell().add(new Paragraph("Amount of logged tours")));
        table.addCell(new Cell().add(new Paragraph("Average Time")));
        table.addCell(new Cell().add(new Paragraph("Average Distance")));
        table.addCell(new Cell().add(new Paragraph("Average Rating")));
        table.addCell(new Cell().add(new Paragraph("" + tourLogs.size())));
        table.addCell(new Cell().add(new Paragraph(formattedTime)));
        table.addCell(new Cell().add(new Paragraph("" + avgDistance)));
        table.addCell(new Cell().add(new Paragraph("" + avgRating)));

        document.add(table);
    }

    private void tourSummary(Document doc, TourDTO tour) throws MalformedURLException {
        final String name = tour.getName();
        final String start = tour.getStart();
        final String goal = tour.getGoal();
        final String desc = tour.getTourDescription();
        final String time = "" + tour.getEstimatedTime();
        final String type = getTransportType(tour.getTransportType());
        final String distance = "" + tour.getTourDistance();

        Paragraph title = new Paragraph("Tour: " + name).setFontColor(new DeviceRgb(8, 73, 117)).setFontSize(23f);
        title.getAccessibilityProperties().setRole(StandardRoles.H1);

        final float[] pointColumnWidths = {104F, 104F, 104F, 104F, 104F};
        Table table = new Table(pointColumnWidths);
        table.addCell(new Cell().add(new Paragraph("Start")));
        table.addCell(new Cell().add(new Paragraph("Goal")));
        table.addCell(new Cell().add(new Paragraph("Estimated Time")));
        table.addCell(new Cell().add(new Paragraph("Estimated Distance")));
        table.addCell(new Cell().add(new Paragraph("Transport Type")));
        table.addCell(new Cell().add(new Paragraph(start)));
        table.addCell(new Cell().add(new Paragraph(goal)));
        table.addCell(new Cell().add(new Paragraph(time)));
        table.addCell(new Cell().add(new Paragraph(distance)));
        table.addCell(new Cell().add(new Paragraph(type)));

        ImageData data = ImageDataFactory.create(ABSOLUTE_IMAGE_PATH + tour.getId() + "_image.jpg");
        final Image image = new Image(data);
        image.setAutoScale(true);

        doc.add(title);
        doc.add(table);
        if (desc != null && !desc.isBlank()) {
            doc.add(new Paragraph("Description").setFontColor(new DeviceRgb(8, 73, 117)).setFontSize(15f));
            doc.add(new Paragraph(desc));
        }
        doc.add(new Paragraph("Route").setFontColor(new DeviceRgb(8, 73, 117)).setFontSize(15f));
        doc.add(new Paragraph().add(image));
    }

    private boolean tourHasRequiredValues(TourDTO tour) {
        return !tour.getName().isEmpty() && !tour.getStart().isEmpty() && !tour.getGoal().isEmpty();
    }

    private String getTransportType(int type) {
        return switch (type) {
            case 0 -> "Foot";
            case 1 -> "Bike";
            case 2 -> "Car";
            default -> throw new IllegalStateException("Unexpected value: " + type);
        };
    }
}
