package prostredky;

/**
 *
 * @author karel@simerda.cz
 */
public class Traktor extends DopravniProstredek {

    private int pocetNaprav;

    public Traktor(String spz, double hmotnost) {
        super(ProstredekTyp.TRAKTOR, hmotnost, spz);

    }

    public Traktor(String spz, int pocetNaprav, double hmotnost) {
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
    public Traktor clone() throws CloneNotSupportedException {
        Traktor kopie = new Traktor(this.spz, this.pocetNaprav, this.hmotnost);
        kopie.setId(super.getId());
        return kopie;
    }

}
