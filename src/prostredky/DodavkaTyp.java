package prostredky;

/**
 *
 * @author karel@simerda.cz
 */
public enum DodavkaTyp {
    DODAVKA("dodávka"), OSOBNI_AUTOMOBIL("osobní auto"), NAKLADNI_AUTOMOBIL("truck"), TRAKTOR("traktor");

    private final String nazev;

    private DodavkaTyp(String nazev) {
        this.nazev = nazev;
    }

    public String nazev() {
        return nazev;
    }

    @Override
    public String toString() {
        return nazev;
    }
    
}
