package generator;

import java.util.Random;
import prostredky.*;

public class GenerujLib {

    public static DopravniProstredek getNahodnyProstredek() {
        Random rand = new Random();

        String spz = getNahodnyString(5, 30);
        double hmotnost = getNahodnyDouble(150, 10000);
        int pocetNaprav = 1 + 2 + rand.nextInt(10 - 2);

        switch (rand.nextInt(4)) {
            //Dodavka
            case 0:
                return new Dodavka(spz, pocetNaprav, hmotnost);
            //Nakladni automobil
            case 1:
                return new NakladniAutomobil(spz, pocetNaprav, hmotnost);
            //Osobni automobil
            case 2:
                Barva barva = getNahodnaBarva();
                return new OsobniAutomobil(spz, barva, pocetNaprav, hmotnost);
            //Traktor
            case 3:
                return new Traktor(spz, pocetNaprav, hmotnost);
        }
        return null;
    }

    private static double getNahodnyDouble(int min, int max) {
        double randInt = 1 + (min * 100) + new Random().nextInt(max * 100
                - min * 100);
        return ((double) randInt) / 100;
    }

    private static String getNahodnyString(int min, int max) {
        Random rand = new Random();

        int delka = 1 + min + rand.nextInt(max - min);

        String output = "";

        String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        char[] ALPHA_NUMERIC_CHARS = ALPHA_NUMERIC_STRING.toCharArray();

        for (int i = 0; i < delka; i++) {
            char AlNumChar = ALPHA_NUMERIC_CHARS[rand.nextInt(
                    ALPHA_NUMERIC_CHARS.length)];

            output += (rand.nextBoolean()) ? Character.toLowerCase(AlNumChar)
                    : AlNumChar;
        }
        return output;

    }

    private static Barva getNahodnaBarva() {
        Random rand = new Random();
        Barva barvy[] = Barva.values();

        return barvy[rand.nextInt(barvy.length)];
    }

}
