/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package command;

import static command.Keyboard.getBarva;
import static command.Keyboard.getDouble;
import static command.Keyboard.getInt;
import static command.Keyboard.getString;
import static command.Keyboard.getTyp;
import java.util.function.Function;
import prostredky.Barva;
import prostredky.Dodavka;
import prostredky.DopravniProstredek;
import prostredky.NakladniAutomobil;
import prostredky.OsobniAutomobil;
import prostredky.ProstredekTyp;
import prostredky.Traktor;

/**
 *
 * @author Adam_Tomasek
 */
public class Editor implements Function<DopravniProstredek, DopravniProstredek> {

    @Override
    public DopravniProstredek apply(DopravniProstredek prostredek) {
        ProstredekTyp typ = getTyp();

        String spz = getString("Vložte spz pro prostředek", 5, 30);
        double hmotnost = getDouble("Vložte hmotnost. (desetinné číslo)",
                150, 10000);
        int pocetNaprav = getInt("Vlozte počet náprav.", 2, 10);

        switch (typ) {
            case DODAVKA:
                prostredek = new Dodavka(spz, pocetNaprav, hmotnost);
                break;
            case OSOBNI_AUTOMOBIL:
                Barva barva = getBarva();
                prostredek = new OsobniAutomobil(spz, barva, pocetNaprav, hmotnost);
                break;
            case NAKLADNI_AUTMOBIL:
                prostredek = new NakladniAutomobil(spz, pocetNaprav, hmotnost);
                break;
            case TRAKTOR:
                prostredek = new Traktor(spz, pocetNaprav, hmotnost);
                break;

        }
        return prostredek;
    }

}
