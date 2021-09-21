package perzistence;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.function.Function;
import kolekce.LinSeznam;
import kolekce.Seznam;
import prostredky.Barva;
import prostredky.Dodavka;
import prostredky.DopravniProstredek;
import prostredky.NakladniAutomobil;
import prostredky.OsobniAutomobil;
import prostredky.Traktor;

/**
 *
 * @author Adam_Tomasek
 */
public final class PerzistenceLib {

    public static final Function<DopravniProstredek, String> mapperOutput
            = (prostredek) -> {
                String text = prostredek.toString();
                String output = "";
                
                for (String elem : text.split(",")) {
                    output += elem.split("=")[1] + ",";
                }
                return output + ";";

            };
    public static final Function<String, DopravniProstredek> mapperInput
            = (line) -> {
                DopravniProstredek prostredek = null;
                if (line.length() == 0) {
                    return prostredek;
                }
                String radekArr[] = line.split(",");

                switch (radekArr[1]) {
                    case "dodávka":
                        return new Dodavka(radekArr[2],
                                Integer.parseInt(radekArr[4]),
                                Double.parseDouble(radekArr[3]));

                    case "traktor":
                        return new Traktor(radekArr[2],
                                Integer.parseInt(radekArr[4]),
                                Double.parseDouble(radekArr[3]));

                    case "nákladní automobil":
                        return new NakladniAutomobil(radekArr[2],
                                Integer.parseInt(radekArr[4]),
                                Double.parseDouble(radekArr[3]));

                    case "osobní automobil":

                        Barva barva = null;
                        for (Barva br : Barva.values()) {
                            if (br.toString().equals(radekArr[3])) {
                                barva = br;
                            }

                        }
                        return new OsobniAutomobil(radekArr[2], barva,
                                Integer.parseInt(radekArr[5]),
                                Double.parseDouble(radekArr[3]));
                }
                return null;
            };
    public static void ulozBin(String cesta, Seznam<DopravniProstredek> seznam) 
            throws IOException {
//        vygenerujSoubor(cesta);

        Iterator iterator = seznam.iterator();

        ObjectOutputStream stream = new ObjectOutputStream(
                new FileOutputStream(cesta));
        stream.writeInt(seznam.size());
        while (iterator.hasNext()) {
            stream.writeObject(iterator.next());
        }
        stream.close();

    }

    public static LinSeznam<DopravniProstredek> nactiBin(String cesta) 
            throws IOException, ClassNotFoundException {
        LinSeznam<DopravniProstredek> seznam = new LinSeznam();

        ObjectInputStream stream = new ObjectInputStream(
                new FileInputStream(cesta));
        int pocet = stream.readInt();
        for (int i = 0; i < pocet; i++) {
            seznam.vlozNaKonec((DopravniProstredek) stream.readObject());
        }
        stream.close();

        return seznam;
    }
}
