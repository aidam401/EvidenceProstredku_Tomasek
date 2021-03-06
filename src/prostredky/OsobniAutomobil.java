package prostredky;

/**
 *
 * @author karel@simerda.cz
 */
public class OsobniAutomobil extends DopravniProstredek {

    private Barva barva;
    private int pocetSedadel;


    public OsobniAutomobil(double hmotnost, String spz) {
        super(ProstredekTyp.OSOBNI_AUTOMOBIL, hmotnost, spz);

    }

    public OsobniAutomobil(String spz, Barva barva, int pocetSedadel, double hmotnost) {
        this(hmotnost, spz);
        this.barva = barva;
        this.pocetSedadel = pocetSedadel;
        
    }

    public Barva getBarva() {
        return barva;
    }

    public void setBarva(Barva barva) {
        this.barva = barva;
    }

    public int getPocetSedadel() {
        return pocetSedadel;
    }

    public void setPocetSedadel(int pocetSedadel) {
        this.pocetSedadel = pocetSedadel;
    }

    @Override
    public String toString() {
        return super.toString() + ",  barva=" + barva + ", pocetSedadel=" + pocetSedadel;
    }

    @Override
    public OsobniAutomobil clone() throws CloneNotSupportedException {
        OsobniAutomobil kopie = new OsobniAutomobil(this.spz, this.barva,
                    this.pocetSedadel, this.hmotnost);
        kopie.setId(super.getId());
        return kopie;
    
       
    }

}
