package tests;

import static org.junit.Assert.*;
import org.junit.Test;

import com.train.Potatoes;

public class PotatoesTest {

    private static void dotest(int p0, int w0, int p1, int expected) {
        assertEquals(expected, Potatoes.potatoes(p0, w0, p1));
    }
    
    @Test
    public void test() {
    	dotest(99, 100, 98, 50);
    	dotest(99, 10, 98, 5);
    	dotest(82, 127, 80, 114);
        dotest(93, 129, 91, 100);
    }
}