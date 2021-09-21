package gui;

import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import prostredky.Barva;
import prostredky.Dodavka;
import prostredky.DopravniProstredek;
import prostredky.OsobniAutomobil;

/**
 *
 * @author Adam_Tomasek
 */
public class DialogOsobniAuto extends DialogDopravniProstredek {

    private final byte MIN_SEDADEL = 1;
    private final byte MAX_SEDADEL = 15;

    private Slider sedadla;
    private ComboBox barva;

    public DialogOsobniAuto() {
        super();
        nastavSedadela();
        nastavBarvu();
    }

    public Optional<DopravniProstredek> vratNoveOsobniAuto() {

        super.DIALOG.setResultConverter((button) -> {
            if (button == ButtonType.OK) {
                return new OsobniAutomobil(super.spz.getText(),
                        (Barva) barva.getSelectionModel().getSelectedItem(),
                        (int) sedadla.getValue(),
                        super.hmotnost.getValue());
            }
            return null;

        });

        return DIALOG.showAndWait();
    }

    private void nastavSedadela() {
        sedadla = new Slider(MIN_SEDADEL, MAX_SEDADEL, 1);
        Label labelNaprav = new Label("01");
        sedadla.valueProperty().addListener((observable, oldValue, newValue) -> {
            int value = newValue.intValue();
            labelNaprav.setText(String.format("%02d", value));
        });

        super.dialogObsah.getChildren().add(new VBox(
                new Label("Sedadla"),
                new HBox(labelNaprav,
                        sedadla)));

    }

    private void nastavBarvu() {
        barva = new ComboBox(FXCollections.observableArrayList(Barva.values()));
        barva.getSelectionModel().selectFirst();
        super.dialogObsah.getChildren().add(
                new VBox(new Label("Barva"), barva)
        );
    }
}
