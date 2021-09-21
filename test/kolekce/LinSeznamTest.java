package kolekce;

import java.util.Iterator;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Adam_Tomasek
 */
public class LinSeznamTest {

    public LinSeznamTest() {
    }

    Object obj1 = new Object();
    Object obj2 = new Object();
    Object obj3 = new Object();
    Object obj4 = new Object();

    // ==Vloz testy==
    @Test
    public void testVlozPrvni() {

        LinSeznam list = new LinSeznam();

        list.vlozPrvni(obj1);
        list.vlozPrvni(obj2);
        list.vlozPrvni(obj3);

        Object[] testovaciPole = {obj3, obj2, obj1};
        assertArrayEquals(testovaciPole, list.vratDataJakoPole());
    }

    @Test
    public void testVlozNaKonec() {

        LinSeznam list = new LinSeznam();

        list.vlozNaKonec(obj1);
        list.vlozNaKonec(obj2);
        list.vlozNaKonec(obj3);
        Object[] testovaciPole = {obj1, obj2, obj3};
        assertArrayEquals(testovaciPole, list.vratDataJakoPole());
    }

    @Test(expected = KolekceException.class)
    public void testVlozZaAktualni01() {
        LinSeznam list = new LinSeznam();
        list.vlozZaAktualni(obj1);
        fail();
    }

    @Test
    public void testVlozZaAktualni02() {
        LinSeznam list = new LinSeznam();

        list.vlozPrvni(obj1);
        list.vlozNaKonec(obj3);
        list.vlozZaAktualni(obj2);

        Object[] testovaciPole = {obj1, obj2, obj3};
        assertArrayEquals(testovaciPole, list.vratDataJakoPole());
    }

    // ==jePrazdny test==
    @Test
    public void testJePrazdny() {
        LinSeznam list = new LinSeznam();
        assertEquals(list.jePrazdny(), true);
        list.vlozNaKonec(obj1);
        assertEquals(list.jePrazdny(), false);

    }

    // ==Dej testy==
    @Test(expected = KolekceException.class)
    public void testDejAktualni01() {
        LinSeznam list = new LinSeznam();
        list.dejAktualni();
        fail();
    }

    @Test
    public void testDejAktualni02() {
        LinSeznam list = new LinSeznam();
        list.vlozNaKonec(obj1);
        list.vlozNaKonec(obj2);
        assertEquals(list.dejAktualni(), obj1);
    }

    @Test(expected = KolekceException.class)
    public void testDejPrvni01() {
        LinSeznam list = new LinSeznam();
        list.dejPrvni();
        fail();
    }

    @Test
    public void testDejPrvni02() {
        LinSeznam list = new LinSeznam();
        list.vlozNaKonec(obj1);
        list.vlozNaKonec(obj2);
        assertEquals(obj1, list.dejPrvni());
    }

    @Test(expected = KolekceException.class)
    public void testDejPosledni01() {
        LinSeznam list = new LinSeznam();
        list.dejPosledni();
        fail();
    }

    @Test
    public void testDejPosledni02() {
        LinSeznam list = new LinSeznam();
        list.vlozNaKonec(obj1);
        list.vlozNaKonec(obj2);
        assertEquals(obj2, list.dejPosledni());
    }

    @Test(expected = KolekceException.class)
    public void testDejZaAktualnim01() {
        LinSeznam list = new LinSeznam();
        list.dejZaAktualnim();
        fail();
    }

    @Test(expected = KolekceException.class)
    public void testDejZaAktualnim02() {
        LinSeznam list = new LinSeznam();
        list.vlozNaKonec(obj1);
        list.dejZaAktualnim();
        fail();
    }

    @Test
    public void testDejZaAktualnim03() {
        LinSeznam list = new LinSeznam();
        list.vlozNaKonec(obj1);
        list.vlozNaKonec(obj2);
        list.vlozNaKonec(obj3);
        list.nastavPrvni();
        assertEquals(list.dejZaAktualnim(), obj2);
    }

    // ==Test dalsi==
    @Test(expected = KolekceException.class)
    public void testDalsi01() {
        LinSeznam list = new LinSeznam();
        list.dalsi();
        fail();
    }

    @Test
    public void testDalsi02() {
        LinSeznam list = new LinSeznam();
        list.vlozNaKonec(obj1);
        list.vlozNaKonec(obj2);
        list.vlozNaKonec(obj3);
        list.nastavPrvni();

        assertEquals(list.dalsi(), true);
        assertEquals(list.dejAktualni(), obj2);

        assertEquals(list.dalsi(), true);
        assertEquals(list.dejAktualni(), obj3);

        assertEquals(list.dalsi(), false);
        assertEquals(list.dejAktualni(), obj3);
    }

    // ==Nastav testy==
    @Test(expected = KolekceException.class)
    public void testNastavPrvni01() {
        LinSeznam list = new LinSeznam();
        list.nastavPrvni();
        fail();
    }

    @Test
    public void testNastavPrvni02() {
        LinSeznam list = new LinSeznam();
        list.vlozNaKonec(obj1);
        list.vlozNaKonec(obj2);
        list.nastavPrvni();
        assertEquals(obj1, list.dejAktualni());
    }

    @Test(expected = KolekceException.class)
    public void testNastavPosledni01() {
        LinSeznam list = new LinSeznam();
        list.nastavPosledni();
    }

    @Test
    public void testNastavPosledni02() {
        LinSeznam list = new LinSeznam();
        list.vlozNaKonec(obj1);
        list.vlozNaKonec(obj2);
        list.nastavPosledni();
        assertEquals(obj2, list.dejAktualni());
        assertEquals(obj2, list.dejPosledni());
    }

    // ==Test odeber==
    @Test(expected = KolekceException.class)
    public void testOdeberAktualni01() {
        LinSeznam list = new LinSeznam();
        list.odeberAktualni();
        fail();

    }

    @Test
    public void testOdeberAktualni02() {

        LinSeznam list = new LinSeznam();
        list.vlozNaKonec(obj1);
        list.vlozNaKonec(obj2);
        list.vlozNaKonec(obj3);

        list.nastavPrvni();
        list.dalsi();

        assertEquals(obj2, list.odeberAktualni());

        Object[] testovaciPole = {obj1, obj3};
        assertArrayEquals(testovaciPole, list.vratDataJakoPole());

        assertEquals(obj1, list.odeberAktualni());

        Object[] testovaciPole2 = {obj3};
        assertArrayEquals(testovaciPole2, list.vratDataJakoPole());
        
        assertEquals(list.dejPosledni(), obj3);
        assertEquals(obj3, list.odeberAktualni());

        Object[] testovaciPole3 = {};
        assertArrayEquals(testovaciPole3, list.vratDataJakoPole());

    }

    @Test(expected = KolekceException.class)
    public void odeberZaAktualnim01() {
        LinSeznam list = new LinSeznam();
        list.odeberZaAktualnim();
        fail();
    }

    @Test(expected = KolekceException.class)
    public void odeberZaAktualnim02() {
        LinSeznam list = new LinSeznam();
        list.vlozNaKonec(obj1);
        list.odeberZaAktualnim();
        fail();
    }

    @Test
    public void odeberZaAktualnim03() {
        LinSeznam list = new LinSeznam();
        list.vlozNaKonec(obj1);
        list.vlozNaKonec(obj2);
        list.vlozNaKonec(obj3);

        list.nastavPrvni();
        assertEquals(obj2, list.odeberZaAktualnim());

        Object[] testovaciPole = {obj1, obj3};
        assertArrayEquals(testovaciPole, list.vratDataJakoPole());

        list.nastavPrvni();
        assertEquals(obj3, list.odeberZaAktualnim());
        
        assertEquals(list.dejPosledni(), obj1);
        Object[] testovaciPole2 = {obj1};
        assertArrayEquals(testovaciPole2, list.vratDataJakoPole());

    }

    // ==test zrus==
    @Test
    public void testZrus() {
        LinSeznam list = new LinSeznam();
        list.vlozNaKonec(obj1);
        list.vlozNaKonec(obj2);
        list.vlozNaKonec(obj3);

        list.zrus();

        Object[] testovaciPole = {};
        assertArrayEquals(testovaciPole, list.vratDataJakoPole());

    }
    
    // ==testy iterator
    
    @Test
    public void testIterator() {
        LinSeznam list = new LinSeznam();
        list.vlozNaKonec(obj2);
        list.vlozNaKonec(obj1);
        Iterator<Object> iterator = list.iterator();
        assertEquals(obj2, iterator.next());
        assertEquals(obj1, iterator.next());
    }

    @Test
    public void testIterator2() {
        LinSeznam list = new LinSeznam();
        list.vlozNaKonec(obj2);
        list.vlozNaKonec(obj1);
        list.vlozNaKonec(obj3);
        Iterator<Object> iterator = list.iterator();
        assertEquals(obj2, iterator.next());
        assertEquals(obj1, iterator.next());
        assertEquals(obj3, iterator.next());
    }

    @Test
    public void testIterator3() {
        LinSeznam list = new LinSeznam();
        list.vlozNaKonec(obj2);
        list.vlozNaKonec(obj1);
        list.vlozNaKonec(obj3);
        Iterator<Object> iterator = list.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            iterator.next();
            i++;
        }
        assertEquals(3, i);
    }
    @Test
    public void testIterator4(){
        LinSeznam list = new LinSeznam();
        list.vlozNaKonec(obj1);
        list.vlozNaKonec(obj2);
        list.vlozNaKonec(obj3);
        list.nastavPrvni();
        Iterator<Object> iterator = list.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            iterator.next();
            i++;
        }
        Object result = list.dejAktualni();
        assertEquals(obj1, result);
    }
    
    @Test(timeout=20L)
    public void testSize10(){
        LinSeznam list = new LinSeznam();
        for (int i = 0; i < 1000000; i++){
            list.vlozNaKonec(obj1);
        }
        int p = list.size();
    }
}
