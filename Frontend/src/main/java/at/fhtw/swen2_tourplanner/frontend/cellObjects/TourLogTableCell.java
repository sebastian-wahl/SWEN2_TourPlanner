package at.fhtw.swen2_tourplanner.frontend.cellObjects;

import at.fhtw.swen2_tourplanner.frontend.cellObjects.converter.Converter;
import at.fhtw.swen2_tourplanner.frontend.cellObjects.exception.ConverterException;
import at.fhtw.swen2_tourplanner.frontend.viewmodel.dtoObjects.TourLogDTO;
import javafx.event.Event;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.text.TextAlignment;

public class TourLogTableCell<T> extends TableCell<TourLogDTO, T> {
    private final Converter<T> converter;
    private final String hint;
    private TextField textField;

    private boolean escapePressed = false;
    private TablePosition<TourLogDTO, T> tablePos = null;

    public TourLogTableCell(Converter<T> converter) {
        this(converter, null);
    }

    public TourLogTableCell(Converter<T> converter, String hint) {
        this.converter = converter;
        this.hint = hint;
    }

    @Override
    public void startEdit() {
        if (!isEditable()
                || !getTableView().isEditable()
                || !getTableColumn().isEditable()) {
            return;
        }
        super.startEdit();

        if (isEditing()) {
            if (textField == null) {
                textField = getTextField();
            }
            escapePressed = false;
            startEdit(textField);
            final TableView<TourLogDTO> table = getTableView();
            tablePos = (TablePosition<TourLogDTO, T>) table.getEditingCell();
        }
    }

    @Override
    public void commitEdit(T newValue) {
        if (!isEditing())
            return;

        final TableView<TourLogDTO> table = getTableView();
        if (table != null) {
            // Inform the TableView of the edit being ready to be committed.
            TableColumn.CellEditEvent<TourLogDTO, T> editEvent = new TableColumn.CellEditEvent<TourLogDTO, T>(
                    table,
                    tablePos,
                    TableColumn.editCommitEvent(),
                    newValue
            );

            // fire event to model
            Event.fireEvent(getTableColumn(), editEvent);
        }

        // we need to setEditing(false):
        super.cancelEdit();

        // update the item within this cell, so that it represents the new value
        updateItem(newValue, false);

        if (table != null) {
            // reset the editing cell on the TableView
            table.edit(-1, null);
        }
    }

    @Override
    public void cancelEdit() {
        if (escapePressed) {
            // this is a cancel event after escape key
            super.cancelEdit();
            setText(getItemText()); // restore the original text in the view
        } else {
            // this is not a cancel event after escape key
            // we interpret it as commit.
            T converted = null;
            try {
                if (!textField.getText().isEmpty()) {
                    converted = converter.convertFromString(textField.getText());
                }
            } catch (ConverterException ex) {
                // ToDo maybe message
            }
            // commit the new text to the model
            this.commitEdit(converted);
        }
        setGraphic(null); // stop editing with TextField
    }

    @Override
    public void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        updateItem();
    }

    private TextField getTextField() {
        final TextField textField = new TextField(getItemText());
        // set hint (prompt text and tooltip
        if (this.hint != null && !this.hint.isEmpty()) {
            Tooltip hintToolTip = new Tooltip(this.hint);
            hintToolTip.setTextAlignment(TextAlignment.LEFT);
            textField.setTooltip(hintToolTip);
            textField.setPromptText(this.hint);
        }

        textField.setOnAction(event -> {
            T converted = null;
            try {
                if (!textField.getText().isEmpty()) {
                    converted = converter.convertFromString(textField.getText());
                }
            } catch (ConverterException e) {
                // ToDo maybe message
            }
            this.commitEdit(converted);
            event.consume();
        });

        textField.setOnKeyPressed(t -> {
            escapePressed = t.getCode() == KeyCode.ESCAPE;
        });
        return textField;
    }

    private String getItemText() {
        if (getItem() != null) {
            try {
                return converter.convertToString(getItem());
            } catch (ConverterException e) {
                return "";
            }
        }
        return "";
    }

    private void updateItem() {
        if (isEmpty()) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (textField != null) {
                    textField.setText(getItemText());
                }
                setText(null);
                setGraphic(textField);
            } else {
                setText(getItemText());
                setGraphic(null);
            }
        }
    }

    private void startEdit(final TextField textField) {
        if (textField != null) {
            textField.setText(getItemText());

        }
        setText(null);
        setGraphic(textField);

        // requesting focus so that key input can immediately go into the TextField
        if (textField != null) {
            textField.selectAll();
            textField.requestFocus();
        }
    }
}
