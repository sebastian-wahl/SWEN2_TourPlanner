package at.fhtw.swen2_tourplanner.backend.tour.util;

import at.fhtw.swen2_tourplanner.backend.tour.dto.TourDTO;
import at.fhtw.swen2_tourplanner.backend.tourlog.dto.TourLogDTO;
import at.fhtw.swen2_tourplanner.backend.tourlog.service.TourLogService;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

@Component
public class TourPdfHelper {
    private final TourLogService tourLogService;

    @Autowired
    public TourPdfHelper(TourLogService tourLogService) {
        this.tourLogService = tourLogService;
    }

    public void createTourReport(TourDTO tour) throws FileNotFoundException, DocumentException {
        final List<TourLogDTO> tourLogs = tourLogService.getAllByTourId(tour.getId());

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(tour.getName() + ".pdf"));

        document.open();
        Font font = FontFactory.getFont(FontFactory.COURIER, 12, BaseColor.BLACK);
        Chunk chunk = new Chunk("Hello World", font);

        document.add(chunk);
        document.close();
    }

    public void createSummaryReport(List<TourDTO> tours) throws FileNotFoundException, DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("summary.pdf"));
        Font font = FontFactory.getFont(FontFactory.COURIER, 12, BaseColor.BLACK);

        document.open();

        for (TourDTO tour : tours) {
            final List<TourLogDTO> tourLogs = tourLogService.getAllByTourId(tour.getId());
            //do math
            Chunk chunk = new Chunk("Hello World", font);

            document.add(chunk);
        }
        document.close();
    }
}
