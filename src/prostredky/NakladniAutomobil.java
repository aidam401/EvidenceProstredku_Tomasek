package prostredky;

/**
 *
 * @author karel@simerda.cz
 */
public class NakladniAutomobil extends DopravniProstredek {

    private int pocetNaprav;
  

    public NakladniAutomobil(String spz, double hmotnost) {
        super(ProstredekTyp.NAKLADNI_AUTMOBIL, hmotnost, spz);

    }

    public NakladniAutomobil(String spz, int pocetNaprav, double hmotnost) {
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
    public NakladniAutomobil clone() throws CloneNotSupportedException {
        NakladniAutomobil kopie = new NakladniAutomobil(this.spz, 
                this.pocetNaprav, 
                this.hmotnost);
        kopie.setId(super.getId());
        return kopie;
    }

}
