package sprava;

import static generator.GenerujLib.getNahodnyProstredek;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import kolekce.KolekceException;
import kolekce.Seznam;
import perzistence.PerzistenceLib;
import prostredky.DopravniProstredek;
import prostredky.DopravniProstredekKlic;

/**
 *
 * @author Adam_Tomasek
 */
public class SpravaProstredku implements Ovladani {

    private Seznam<DopravniProstredek> seznam;
    private Comparator<? super DopravniProstredek> comparator;
    private Consumer<String> errorLog;

    @Override
    public void vytvorSeznam(Supplier<Seznam<DopravniProstredek>> supplier) {
        this.seznam = supplier.get();
    }

    @Override
    public void vytvorSeznam(Function<Integer, Seznam<DopravniProstredek>> function, int size)
            throws KolekceException {
        this.seznam = function.apply(size);
    }

    @Override
    public void nastavKomparator(
            Comparator<? super DopravniProstredek> comparator) {
        this.comparator = comparator;
    }

    @Override
    public void vlozPolozku(DopravniProstredek data) {
        try {
            this.seznam.vlozNaKonec(data);
        } catch (NullPointerException ex) {
            error(ex);
        }
    }

    @Override
    public DopravniProstredek najdiPolozku(DopravniProstredekKlic klic) {
        try {
            return stream()
                    .filter((pol) -> comparator.compare(pol, klic) == 0)
                    .findFirst()
                    .get();
        } catch (NoSuchElementException ex) {
            return null;
        }

    }

    @Override
    public void prejdiNaPrvniPolozku() {
        try {
            seznam.nastavPrvni();
        } catch (KolekceException|NullPointerException ex) {
            error(ex);
        }
    }

    @Override
    public void prejdiNaPosledniPolozku() {
        try {
            seznam.nastavPosledni();
        } catch (KolekceException|NullPointerException ex) {
            error(ex);
        }
    }

    @Override
    public void prejdiNaDalsiPolozku() {
        try {
            seznam.dalsi();
        } catch (KolekceException | NullPointerException ex) {
            error(ex);
        }
    }

    @Override
    public void prejdiNaPredchoziPolozku() {
        try {
            seznam.nastavPredaktualni();
        } catch (KolekceException | NullPointerException ex) {
            error(ex);
        }
    }

    @Override
    public DopravniProstredek nastavAktualniPolozku(DopravniProstredekKlic klic) {
        try {
            this.seznam.nastavPrvni();
            while (seznam.dalsi()) {
                DopravniProstredek prvek = seznam.dejAktualni();
                if (comparator.compare(klic, prvek) == 0) {
                    return prvek;
                }
            }
            return null;
        } catch (KolekceException | NullPointerException ex) {
            error(ex);
        }
        return null;
    }

    @Override
    public DopravniProstredek vyjmiAktualniPolozku() {
        try {
            return seznam.odeberAktualni();
        } catch (KolekceException | NullPointerException ex) {
            error(ex);
        }
        return null;
    }

    @Override
    public DopravniProstredek dejKopiiAktualniPolozky() {
        try {

            return (DopravniProstredek) this.seznam.dejAktualni().clone();

        } catch (KolekceException | CloneNotSupportedException
                | NullPointerException ex) {
            error(ex);
        }
        return null;
    }

    @Override
    public void editujAktualniPolozku(Function<DopravniProstredek, DopravniProstredek> editor) {
        try {
            DopravniProstredek novy = editor.apply(seznam.dejAktualni());
            this.seznam.vlozZaAktualni(novy);
            this.seznam.odeberAktualni();
        } catch (KolekceException|NullPointerException ex) {
            error(ex);
        }

    }

    @Override
    public void ulozDoSouboru(String soubor) {
        try {
            PerzistenceLib.ulozBin(soubor, seznam);
        } catch (IOException ex) {
            error(ex);
        }

    }

    @Override
    public void nactiZeSouboru(String soubor) {
        try {
            this.seznam = PerzistenceLib.nactiBin(soubor);
            DopravniProstredek.nastavCounter(this.dejAktualniPocetPolozek()+1);
        } catch (IOException | ClassNotFoundException ex) {
            error(ex);
        }
    }

    @Override
    public void vypis(Consumer<DopravniProstredek> writer) {
        stream().forEach(writer);
    }

    @Override
    public void nactiTextSoubor(String soubor, Function<String, DopravniProstredek> mapper) {
        try {
            zrus();
            Files.lines(Paths.get(soubor), StandardCharsets.UTF_8)
                    .filter(t -> t != null)
                    .map(mapper)
                    .forEach((t) -> seznam.vlozNaKonec(t));
        } catch (IOException ex) {
            error(ex);
        }
    }

    @Override
    public void ulozTextSoubor(String soubor, Function<DopravniProstredek, String> mapper) {
        try (PrintWriter pw = new PrintWriter(soubor, "UTF-8")) {
            stream()
                    .map(mapper)
                    .forEachOrdered(pw::println);

        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            error(ex);
        }
    }

    @Override
    public void generujData(int pocetProstredku) {
        for (int i = 0; i < pocetProstredku; i++) {
            this.seznam.vlozNaKonec(getNahodnyProstredek());
        }
    }

    @Override
    public int dejAktualniPocetPolozek() {
        return this.seznam.size();
    }

    @Override
    public void zrus() {
        DopravniProstredek.resetujCounter();
        this.seznam.zrus();
    }

    @Override
    public Iterator<DopravniProstredek> iterator() {
        return this.seznam.iterator();
    }

    private void error(Exception ex) {
        errorLog.accept("Error - " + ex.getClass()
                + " " + ex.getLocalizedMessage());
    }

    @Override
    public void nastavErrorLog(Consumer<String> errorLog) {
        this.errorLog = errorLog;
    }

}
