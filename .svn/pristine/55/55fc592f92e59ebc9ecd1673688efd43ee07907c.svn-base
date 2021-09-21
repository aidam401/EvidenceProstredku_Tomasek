package gui;


import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;

import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import kolekce.LinSeznam;
import prostredky.DopravniProstredek;

import sprava.SpravaProstredku;

public class MainFX extends javafx.application.Application {

    private final SpravaProstredku ovladac = new SpravaProstredku();

    private final BorderPane ROOT = new BorderPane();

    private final ProstredkyMapper MAPPER = new ProstredkyMapper(ROOT);
    private final ListView<String> LIST_VIEW = new ListView<>();
    private final ControlPanelVBox VBOX = new ControlPanelVBox(ovladac, MAPPER, ROOT);
    private final ControlPanelHBox HBOX = new ControlPanelHBox(ovladac, MAPPER, ROOT);

   
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Správa dopravních prostředků");
        primaryStage.setMinWidth(500);
        primaryStage.setMinHeight(350);
        primaryStage.setScene(new Scene(ROOT));
        primaryStage.show();

        ovladac.vytvorSeznam(LinSeznam<DopravniProstredek>::new);

        ovladac.nastavErrorLog((t) -> {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR, t, null);
            errorAlert.showAndWait();
        });

        nastavKostru();

    }

    private void nastavKostru() {

        LIST_VIEW.setId("listView");
        ROOT.setCenter(LIST_VIEW);
        ROOT.setRight(VBOX.getVBox());
        ROOT.setBottom(HBOX.getHBox());

    }

    

}
