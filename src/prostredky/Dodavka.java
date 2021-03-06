package prostredky;

/**
 *
 * @author karel@simerda.cz
 */
public class Dodavka extends DopravniProstredek {

    private int pocetNaprav;
    public Dodavka(String spz, double hmotnost) {
        super(ProstredekTyp.DODAVKA, hmotnost, spz);
    }

    public Dodavka(String spz, int pocetNaprav, double hmotnost) {
        this(spz, hmotnost);
        this.pocetNaprav = pocetNaprav;
    }

    public int getPocetNaprav() {
        return pocetNaprav;
    }

    public void setPocetNaprav(int pocetNaprav) {
        this.pocetNaprav = pocetNaprav;
    }

    @Override
    public String toString() {
        return super.toString() + ", pocetNaprav=" + pocetNaprav;
    }
    
    @Override
    public Dodavka clone() throws CloneNotSupportedException {
        Dodavka kopie = new Dodavka(this.spz, this.pocetNaprav, this.hmotnost);
        kopie.setId(super.getId());
        return kopie;
    }
}
