package at.fhtw.swen2_tourplanner.frontend.controller;


import at.fhtw.swen2_tourplanner.frontend.viewmodel.Menubar;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.List;

public class MenubarController extends BaseController<Menubar> {

    @FXML
    public MenuItem importToursMenu;
    @FXML
    public MenuItem exportSummary;
    @FXML
    public MenuBar menu;

    private FileChooser fileChooser;

    public MenubarController(Menubar menubar) {
        super(menubar);
    }

    @FXML
    public void initialize() {
        fileChooser = new FileChooser();

        this.importToursMenu.setOnAction(event -> importTour());
        this.exportSummary.setOnAction(event -> exportSummary());
    }

    private void importTour() {
        fileChooser.setTitle("Select Tour File(s");
        fileChooser.getExtensionFilters().clear();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JSON Files (*.json)", "*.json", "*.JSON"));
        List<File> fileList = fileChooser.showOpenMultipleDialog(this.menu.getScene().getWindow());
        if (fileList != null && !fileList.isEmpty()) {
            this.getViewModel().importTour(fileList);
        }
    }

    private void exportSummary() {
        fileChooser.setTitle("Save Tour Summary Report");
        fileChooser.getExtensionFilters().clear();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.PDF", "*.pdf"));
        File saveToFile = fileChooser.showSaveDialog(this.menu.getScene().getWindow());
        if (saveToFile != null) {
            this.getViewModel().exportTourSummary(saveToFile);
        }
    }
}
