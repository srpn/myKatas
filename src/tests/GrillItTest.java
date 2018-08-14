package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.train.GrillIt;

public class GrillItTest {

    @Test
    public void basicGrille() {
//    	assertEquals("d", GrillIt.grille("abcd",1 ));
    	assertEquals("b", GrillIt.grille("0abd", 2));
        assertEquals("df", GrillIt.grille("abcdef", 5));
        assertEquals("", GrillIt.grille("", 5 ));
        assertEquals("codewars", GrillIt.grille("tcddoadepwweasresd", 77098)); 
    }
    
}