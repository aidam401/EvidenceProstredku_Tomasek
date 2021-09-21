package gui;

import java.util.function.Supplier;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import sprava.SpravaProstredku;

/**
 *
 * @author Adam_Tomasek
 */
public class ControlPanelVBox {

    private final VBox VBOX = new VBox();
    private final SpravaProstredku OVLADAC;
    private final ProstredkyMapper MAPPER;
    private BorderPane root;

    private final Button PRVNI_BUTTON = new Button("První");
    private final Button MINULY_BUTTON = new Button("Předchozí");
    private final Button DALSI_BUTTON = new Button("Další");
    private final Button POSLEDNI_BUTTON = new Button("Poslední");
    private final Button EDITUJ_BUTTON = new Button("Edituj");
    private final Button VYJMI_BUTTON = new Button("Vyjmi");
    
    

    private final Supplier<ObservableList<String>> SEZNAM_SUPP = () -> {
        return ((ListView<String>) root.getCenter()).getItems();
    };

    private final Supplier<MultipleSelectionModel> SEZ_SELECTMOD_SUPP = () -> {
        return ((ListView<String>) root.getCenter())
                .getSelectionModel();
    };

    public ControlPanelVBox(SpravaProstredku ovladac,
            ProstredkyMapper mapper, BorderPane root) {
        this.OVLADAC = ovladac;
        this.MAPPER = mapper;
        this.root = root;
        nactiMe();
    }

    private void nactiMe() {
        nastavKostru();
        nastavTlacitka();

    }

    private void nastavKostru() {

        VBOX.getChildren().addAll(
                new Node[]{
                    new Label("Procházení"),
                    PRVNI_BUTTON,
                    MINULY_BUTTON,
                    DALSI_BUTTON,
                    POSLEDNI_BUTTON,
                    new Label("Příkazy"),
                    EDITUJ_BUTTON,
                    VYJMI_BUTTON,}
        );

    }

    private void nastavTlacitka() {

        PRVNI_BUTTON.setOnAction((event) -> {
            OVLADAC.prejdiNaPrvniPolozku();
            if (!SEZNAM_SUPP.get().isEmpty()) {
                SEZ_SELECTMOD_SUPP.get().selectFirst();
            }
        });

        MINULY_BUTTON.setOnAction((event) -> {
            OVLADAC.prejdiNaPredchoziPolozku();
            if (!SEZNAM_SUPP.get().isEmpty()) {
                SEZ_SELECTMOD_SUPP.get().selectPrevious();
            }
        });

        DALSI_BUTTON.setOnAction((event) -> {
            OVLADAC.prejdiNaDalsiPolozku();
            if (!SEZNAM_SUPP.get().isEmpty()) {
                SEZ_SELECTMOD_SUPP.get().selectNext();
            }
        });
        POSLEDNI_BUTTON.setOnAction((event) -> {
            OVLADAC.prejdiNaPosledniPolozku();
            if (!SEZNAM_SUPP.get().isEmpty()) {
                SEZ_SELECTMOD_SUPP.get().selectLast();
            }
        });
        EDITUJ_BUTTON.setOnAction((event) -> {
            OVLADAC.editujAktualniPolozku(ProstredkyMapper.EDITOR);
            zobraz();
        });
        VYJMI_BUTTON.setOnAction((event) -> {
            OVLADAC.vyjmiAktualniPolozku();
            zobraz();
        });

    }

    public VBox getVBox() {
        return VBOX;
    }

    private void zobraz() {
        ListView<String> listView = (ListView<String>) root.getCenter();
        listView.getItems().clear();
        OVLADAC.vypis(MAPPER.writer);
        PRVNI_BUTTON.fire();
    }

    
}
