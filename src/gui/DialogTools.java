package gui;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Dialog;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Adam_Tomasek
 */
public class DialogTools {

    public static Optional<Integer> getGeneratorInt() {
        Dialog<Integer> dialog = new Dialog<>();
        dialog.setTitle("Generování");
        dialog.setHeaderText("Zadejte počet ke generování");

        dialog.getDialogPane()
                .getButtonTypes()
                .addAll(ButtonType.OK, ButtonType.CANCEL);

        Spinner<Integer> spinner = new Spinner<>(1, Integer.MAX_VALUE, 1);
        dialog.getDialogPane().setContent(spinner);

        dialog.setResultConverter((button) -> {
            return button == ButtonType.OK ? spinner.getValue() : null;
        });

        return dialog.showAndWait();
    }

    public static Optional<Integer> getNajdiId(int velikostPole) {
        Dialog<Integer> dialog = new Dialog<>();
        dialog.setTitle("Hledání");
        dialog.setHeaderText("Zadejte hledané id");
        dialog.getDialogPane()
                .getButtonTypes()
                .addAll(ButtonType.OK, ButtonType.CANCEL);

        Spinner<Integer> spinner = new Spinner<>(0, Integer.MAX_VALUE,
                velikostPole - 1, 1);
        dialog.getDialogPane().setContent(spinner);

        dialog.setResultConverter((button) -> {
            return button == ButtonType.OK ? spinner.getValue() : null;
        });

        return dialog.showAndWait();
    }

    public static Optional<String> getNajdiSpz() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Hledání");
        dialog.setHeaderText("Zadejte hledané spz");
        dialog.getDialogPane()
                .getButtonTypes()
                .addAll(ButtonType.OK, ButtonType.CANCEL);

        TextField field = new TextField("SPZ");
        dialog.getDialogPane().setContent(field);

        dialog.setResultConverter(button -> {
            return button == ButtonType.OK ? field.getText() : null;
        });

        return dialog.showAndWait();
    }

}
