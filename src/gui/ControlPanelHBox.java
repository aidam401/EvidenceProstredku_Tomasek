package gui;

import java.util.Optional;

import java.util.function.Supplier;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionModel;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import perzistence.PerzistenceLib;
import prostredky.DopravniProstredek;
import prostredky.DopravniProstredekKlic;
import prostredky.ProstredekTyp;
import sprava.SpravaProstredku;

/**
 *
 * @author Adam_Tomasek
 */
public class ControlPanelHBox {

    private final HBox HBox = new HBox();
    private final SpravaProstredku OVLADAC;
    private final ProstredkyMapper MAPPER;
    private BorderPane root;
    private final String BIN_ZALOHA_CESTA = "zalohy/zaloha.bin";
    private final String TXT_ZALOHA_CESTA = "zalohy/prostredky.txt";

    private final Button GENERUJ_BUTTON = new Button("Generuj");
    private final Button ULOZ_BUTTON = new Button("Ulož");
    private final Button NACTI_BUTTON = new Button("Načti");
    private final ComboBox NOVY_COMBO = new ComboBox();
    private final ComboBox FILTER_COMBO = new ComboBox();
    private final ComboBox NAJDI_COMBO = new ComboBox();
    private final Button ZALOHUJ_BUTTON = new Button("Zálohuj");
    private final Button OBNOV_BUTTON = new Button("Obnov");
    private final Button ZRUS_BUTTON = new Button("Zruš");

    private final Supplier<ListView<String>> LIST_VIEW = () -> {
        return ((ListView<String>) root
                .getCenter());
    };
    private final Supplier<SelectionModel> SELECTION_MODEL = () -> {
        return LIST_VIEW.get().getSelectionModel();
    };

    public ControlPanelHBox(SpravaProstredku ovladac,
            ProstredkyMapper mapper,
            BorderPane root) {
        this.OVLADAC = ovladac;
        this.MAPPER = mapper;
        this.root = root;
        nactiMe();
    }

    private void nactiMe() {
        nastavKostru();
        nastavTlačítka();
        nastavComboboxy();

    }

    private void nastavKostru() {

        HBox.getChildren().addAll(
                new Node[]{
                    GENERUJ_BUTTON,
                    ULOZ_BUTTON,
                    NACTI_BUTTON,
                    new Label("Novy:"),
                    NOVY_COMBO,
                    new Label("Filtr:"),
                    FILTER_COMBO,
                    NAJDI_COMBO,
                    ZALOHUJ_BUTTON,
                    OBNOV_BUTTON,
                    ZRUS_BUTTON

                }
        );

    }

    private void nastavComboboxy() {
        // Najdi combo
        NAJDI_COMBO.getItems().addAll(
                "ID/SPZ",
                "Na základě id",
                "Na základě spz"
        );
        NAJDI_COMBO.getSelectionModel().selectFirst();
        NAJDI_COMBO.setOnAction((event) -> {
            String vybrane = (String) NAJDI_COMBO
                    .getSelectionModel()
                    .getSelectedItem();
            switch (vybrane) {
                case "Na základě id":
                    DialogTools.getNajdiId(OVLADAC.dejAktualniPocetPolozek())
                            .ifPresent((id) -> {
                                OVLADAC.nastavKomparator(
                                        ProstredkyMapper.ID_COMPARATOR);
                                prejdiNaPolozku(OVLADAC.najdiPolozku(
                                        new DopravniProstredekKlic(id)));
                            });

                    break;
                case "Na základě spz":
                    DialogTools.getNajdiSpz()
                            .ifPresent((spz) -> {
                                OVLADAC.nastavKomparator(
                                        ProstredkyMapper.SPZ_COMPARATOR);
                                prejdiNaPolozku(OVLADAC.najdiPolozku(
                                        new DopravniProstredekKlic(spz)));
                            });
                    break;
            }
            NAJDI_COMBO.getSelectionModel().selectFirst();
        });
        // Filtruj combo

        FILTER_COMBO.getItems().addAll(
                ProstredekTyp.getProstredkyFilter()
        );
        FILTER_COMBO.getSelectionModel().selectLast();
        FILTER_COMBO.setOnAction((event) -> {
            if (OVLADAC.dejAktualniPocetPolozek() == 0) {
                return;
            }
            ProstredekTyp vybrane = (ProstredekTyp) FILTER_COMBO
                    .getSelectionModel()
                    .getSelectedItem();
            MAPPER.nastavFiltr(vybrane);
            zobraz();
            if (!LIST_VIEW.get().getItems().isEmpty()) {
                SELECTION_MODEL.get().selectFirst();
            }

        });

        // Novy combo
        NOVY_COMBO.getItems().addAll(
                "Vyberte prostředek",
                "Dodávka",
                "Nákladní automobil",
                "Osobní Automobil",
                "Traktor"
        );
        NOVY_COMBO.getSelectionModel().selectFirst();
        NOVY_COMBO.setOnAction((event) -> {
            String vybrane = (String) NOVY_COMBO
                    .getSelectionModel()
                    .getSelectedItem();

            switch (vybrane) {
                case "Dodávka":
                    new DialogDodavka().vratNovouDodavku()
                            .ifPresent((prostredek) -> {
                                OVLADAC.vlozPolozku(prostredek);
                                zobraz();
                            });
                    break;
                case "Nákladní automobil":
                    new DialogNakladniAutomobil().vratNovyNakladniAutomobil()
                            .ifPresent((prostredek) -> {
                                OVLADAC.vlozPolozku(prostredek);
                                zobraz();
                            });
                    break;
                case "Osobní Automobil":
                    new DialogOsobniAuto().vratNoveOsobniAuto()
                            .ifPresent((prostredek) -> {
                                OVLADAC.vlozPolozku(prostredek);
                                zobraz();
                            });
                    break;
                case "Traktor":
                    new DialogTraktor().vratNovyTraktor()
                            .ifPresent((prostredek) -> {
                                OVLADAC.vlozPolozku(prostredek);
                                zobraz();
                            });
                    break;

            }

        });
    }

    private void nastavTlačítka() {

        GENERUJ_BUTTON.setOnAction((event) -> {
            Optional<Integer> pocet = DialogTools.getGeneratorInt();
            pocet.ifPresent((poc) -> {
                OVLADAC.generujData(poc);
                zobraz();
                nastavListView();
                SELECTION_MODEL.get().selectFirst();

            });
        });
        ULOZ_BUTTON.setOnAction((event) -> {
            OVLADAC.ulozTextSoubor(TXT_ZALOHA_CESTA,
                    PerzistenceLib.mapperOutput);
        });

        NACTI_BUTTON.setOnAction((event) -> {
            OVLADAC.nactiTextSoubor(TXT_ZALOHA_CESTA,
                    PerzistenceLib.mapperInput);
            zobraz();
        });
        ZALOHUJ_BUTTON.setOnAction((event) -> {
            OVLADAC.ulozDoSouboru(BIN_ZALOHA_CESTA);
        });
        OBNOV_BUTTON.setOnAction((event) -> {
            OVLADAC.nactiZeSouboru(BIN_ZALOHA_CESTA);
            zobraz();
        });
        ZRUS_BUTTON.setOnAction((event) -> {
            OVLADAC.zrus();
            zobraz();
        });

    }

    public HBox getHBox() {
        return HBox;
    }

    private void zobraz() {
        ListView<String> listView = (ListView<String>) root.getCenter();
        listView.getItems().clear();
        OVLADAC.vypis(MAPPER.writer);

    }

    private void nastavListView() {
        LIST_VIEW.get().setOnMouseClicked((event) -> {
            if (OVLADAC.dejAktualniPocetPolozek() == 0) {
                return;
            }
            OVLADAC.prejdiNaPrvniPolozku();

            while (!OVLADAC.dejKopiiAktualniPolozky()
                    .toString()
                    .equals(SELECTION_MODEL.get().getSelectedItem()
                            .toString())) {
                OVLADAC.prejdiNaDalsiPolozku();
               
            }

        });

    }

    private void prejdiNaPolozku(DopravniProstredek hledanaPolozka) {
        ListView<String> listView = (ListView<String>) root.getCenter();
        ObservableList<String> seznam = listView.getItems();

        if (hledanaPolozka == null) {
            return;
        }
        OVLADAC.prejdiNaPrvniPolozku();
        int counter = 0;
        while (counter != seznam.size()) {
            System.out.println("aaa");
            counter++;
            if (ProstredkyMapper.filtrovaciTyp
                    != ProstredekTyp.NON_FILTER
                    && OVLADAC.dejKopiiAktualniPolozky().getTyp()
                    != ProstredkyMapper.filtrovaciTyp) {
                OVLADAC.prejdiNaDalsiPolozku();
                continue;
            }
            if (hledanaPolozka.toString().equals(seznam.get(counter))) {
                listView.getSelectionModel().select(counter);
                return;
            }

            OVLADAC.prejdiNaDalsiPolozku();
        }
    }

}
