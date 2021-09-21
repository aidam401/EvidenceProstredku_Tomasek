package gui;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import prostredky.DopravniProstredek;

/**
 *
 * @author Adam_Tomasek
 */
//Zmena oproti zadani - predelano na abstract
public abstract class DialogDopravniProstredek {

    protected final Dialog<DopravniProstredek> DIALOG
            = new Dialog<DopravniProstredek>();

    final int MIN_HMOTNOST = 150;
    final int MAX_HMOTNOST = 10000;

    protected VBox dialogObsah;
    protected TextField spz;
    protected Slider hmotnost;

    public DialogDopravniProstredek() {
        dialogObsah = new VBox();
        DIALOG.setTitle("Generování");
        DIALOG.setHeaderText("Zadejte hodnoty");
        DIALOG.getDialogPane()
                .getButtonTypes()
                .addAll(ButtonType.OK, ButtonType.CANCEL);
        DIALOG.getDialogPane().setContent(dialogObsah);

        nastavSpz();
        nastavHmotnost();
    }

    protected void nastavSpz() {
        spz = new TextField("SPZ");
        dialogObsah.getChildren().add(spz);
    }

    protected void nastavHmotnost() {

        hmotnost = new Slider(MIN_HMOTNOST, MAX_HMOTNOST, 0.5);
        Label labelHmotnost = new Label("150,0");
        hmotnost.valueProperty().addListener((observable, oldValue, newValue) -> {
            double value = newValue.doubleValue();
            labelHmotnost.setText(String.format("%5.1f", value));
        });

        dialogObsah.getChildren().add(new VBox(
                new Label("Hmotnost"),
                new HBox(labelHmotnost,
                        hmotnost)));

    }

}
