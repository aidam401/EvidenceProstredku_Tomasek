/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.util.Optional;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import prostredky.Dodavka;
import prostredky.DopravniProstredek;
import prostredky.Traktor;

/**
 *
 * @author Adam_Tomasek
 */
public class DialogTraktor extends DialogDopravniProstredek {

    private final byte MIN_NAPRAV = 2;
    private final byte MAX_NAPRAV = 10;

    private Slider napravy;

    public DialogTraktor() {
        super();

        nastavNapravy();
    }

    public Optional<DopravniProstredek> vratNovyTraktor() {

        super.DIALOG.setResultConverter((button) -> {
            if (button == ButtonType.OK) {
                return new Traktor(super.spz.getText(),
                        (int) napravy.getValue(),
                        super.hmotnost.getValue());
            }
            return null;

        });

        return DIALOG.showAndWait();
    }

    private void nastavNapravy() {
        napravy = new Slider(MIN_NAPRAV, MAX_NAPRAV, 1);
        Label labelNaprav = new Label("00");
        napravy.valueProperty().addListener((observable, oldValue, newValue) -> {
            int value = newValue.intValue();
            labelNaprav.setText(String.format("%02d", value));
        });

        super.dialogObsah.getChildren().add(new VBox(
                new Label("Napravy"),
                new HBox(labelNaprav,
                        napravy)));

    }

}
