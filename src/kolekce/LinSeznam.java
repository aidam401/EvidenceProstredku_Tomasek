package kolekce;

import java.util.Iterator;

/**
 *
 * @author Adam_Tomasek
 * @param <E>
 */
public class LinSeznam<E> implements Seznam<E> {

    // ==Privatni atributy==
    private int pozice;
    private int size;

    private Prvek<E> aktualniPrvek;
    private Prvek<E> prvniPrvek;
    private Prvek<E> posledniPrvek;

    // ==Vnorene tridy==
    private static class Prvek<E> {

        Prvek<E> dalsi;
        E data;

        Prvek(E data, Prvek<E> dalsi) {
            this.dalsi = dalsi;
            this.data = data;
        }

        Prvek(E data) {
            this.data = data;
        }

    }

    private class IteratorImpl implements Iterator {

        Prvek<E> iterAktualniPrvek;
        int iterPozice;

        public IteratorImpl() {

            iterAktualniPrvek = new Prvek<E>(null, prvniPrvek);
            iterPozice = 0;

        }

        @Override
        public boolean hasNext() {
            return this.iterPozice != size;
        }

        @Override
        public Object next() {
            if (hasNext()) {
                iterPozice++;
                iterAktualniPrvek = iterAktualniPrvek.dalsi;
                return iterAktualniPrvek.data;
            }
            throw new KolekceException("Seznam nemá další prvek");
        }
    }

    // ==Konstruktor==
    public LinSeznam() {
        this.size = 0;
        this.pozice = 0;
    }

    // ==Verejne metody==
    // ==Metoda pouzita pouze pri testovani==
    public E[] vratDataJakoPole() {
        Prvek<E> prvek = this.prvniPrvek;
        E[] output = (E[]) new Object[this.size];

        for (int i = 0; i < this.size; i++) {
            output[i] = prvek.data;
            prvek = prvek.dalsi;
        }
        return output;
    }

    @Override
    public void nastavPrvni() throws KolekceException {
        jePrazdnyTest();
        this.pozice = 1;
        this.aktualniPrvek = this.prvniPrvek;
    }

    @Override
    public void nastavPosledni() throws KolekceException {
        jePrazdnyTest();
        this.pozice = this.size;
        this.aktualniPrvek = this.posledniPrvek;
    }

    @Override
    public void nastavPredaktualni() throws KolekceException {
        jePrazdnyTest();
        if (this.size == 0 || this.prvniPrvek == this.aktualniPrvek) {
            throw new KolekceException("Není žádný předaktuální prvek");
        }
        if (this.pozice == 2) {
            nastavPrvni();
        } else {
            this.pozice--;
            this.aktualniPrvek = this.dejPrvekZPozice(this.pozice);     
        } 
    }

    @Override
    public boolean dalsi() throws KolekceException {
        jePrazdnyTest();
        if (this.pozice != this.size) {
            this.aktualniPrvek = this.aktualniPrvek.dalsi;
            this.pozice++;
            return true;
        }
        return false;
    }

    @Override
    public void vlozPrvni(E data) {
        jeVstupPrazdnyTest(data);
        this.prvniPrvek = new Prvek(data, this.prvniPrvek);

        if (this.size == 0) {
            this.posledniPrvek = this.aktualniPrvek = this.prvniPrvek;
            this.pozice = 1;
        }
        this.size++;
    }

    @Override
    public void vlozNaKonec(E data) {

        if (this.size == 0) {
            vlozPrvni(data);
            return;
        }
        jeVstupPrazdnyTest(data);

        this.posledniPrvek = this.posledniPrvek.dalsi = new Prvek(data);

        this.size++;
    }

    @Override
    public void vlozZaAktualni(E data) throws KolekceException {
        jeVstupPrazdnyTest(data);
        jePrazdnyTest();
        this.aktualniPrvek.dalsi = new Prvek(data, this.aktualniPrvek.dalsi);

        this.size++;

        if (this.pozice == this.size - 1) {
            this.posledniPrvek = this.aktualniPrvek.dalsi;
        }

    }

    @Override
    public boolean jePrazdny() {
        return this.size == 0;
    }

    @Override
    public E dejPrvni() throws KolekceException {
        jePrazdnyTest();
        return this.prvniPrvek.data;
    }

    @Override
    public E dejPosledni() throws KolekceException {
        jePrazdnyTest();
        return this.posledniPrvek.data;
    }

    @Override
    public E dejAktualni() throws KolekceException {
        jePrazdnyTest();
        return this.aktualniPrvek.data;
    }

    @Override
    public E dejZaAktualnim() throws KolekceException {
        jePrazdnyTest();
        maDalsiTest();

        // TODO: Zeptat se na duvod explicitniho preparsovani
        return this.aktualniPrvek.dalsi.data;
    }

    @Override
    public E odeberAktualni() throws KolekceException {
        jePrazdnyTest();

        E output = this.aktualniPrvek.data;
        // Osetreni seznamu o velikosti 1
        if (this.size == 1) {
            this.aktualniPrvek = null;
            this.pozice = 0;
            this.size = 0;
            return output;
        }
        // Osetreni prvni pozice
        if (this.pozice == 1) {
            this.aktualniPrvek = this.aktualniPrvek.dalsi;
            this.prvniPrvek = this.aktualniPrvek;
            this.size--;
            return output;
        }
        Prvek<E> predchoziPrvek = dejPrvekZPozice(this.pozice - 1);
        predchoziPrvek.dalsi = this.aktualniPrvek.dalsi;

        this.aktualniPrvek = predchoziPrvek;
        this.pozice--;
        this.size--;

        if (this.pozice == this.size) {
            this.posledniPrvek = this.aktualniPrvek;
        }

        return output;

    }

    @Override
    public E odeberZaAktualnim() throws KolekceException {

        jePrazdnyTest();

        maDalsiTest();

        E output = (E) this.aktualniPrvek.dalsi.data;

        this.aktualniPrvek.dalsi = this.aktualniPrvek.dalsi.dalsi;
        this.size--;

        if (this.pozice == this.size) {
            this.posledniPrvek = this.aktualniPrvek;
        }
        return output;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void zrus() {
        Prvek<E> node = this.prvniPrvek;
        for (int i = 0; i < this.size; i++) {
            Prvek<E> dalsiNode = node.dalsi;
            node.dalsi = null;

            node = dalsiNode;
        }
        this.size = 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new IteratorImpl();
    }

    // ==Privatni metody==
    private void jePrazdnyTest() throws KolekceException {
        if (jePrazdny()) {
            throw new KolekceException("Seznam je prázdný");
        }
    }

    private void jeVstupPrazdnyTest(E data) throws NullPointerException {
        if (data == null) {
            throw new NullPointerException();
        }

    }

    private void maDalsiTest() throws KolekceException {
        if (this.pozice == this.size) {
            throw new KolekceException("Za aktuálním prvkem další není");
        }
    }

    private Prvek<E> dejPrvekZPozice(int pozice) {
        Prvek<E> output = this.prvniPrvek;
        for (int i = 0; i < pozice - 1; i++) {
            output = output.dalsi;
        }
        return output;
    }

}
