package Tests;

import Tools.VP;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VPTest {
    private VP vpa2n1 = new VP(2.0,2.0,2.0);
    private VP vpa2n2 = new VP(2.0,2.0,2.0);
    private VP vpa1n1 = new VP(1.0,1.0,1.0);
    private VP a = new VP(3.0,3.0,-3.0);
    private VP b = new VP(0.0,3.0,3.0);
    private int dimensions = 3;


    @Test
    void equalsTest() {
        assertTrue(vpa2n1.equals(vpa2n2));
    }
    @Test
    void equalsNotTest() {
        assertFalse(vpa2n1.equals(vpa1n1));
    }
    @Test
    void randomVectorNotZero() {
        assertTrue(0 < VP.random(3,10.0).length());
    }
    @Test
    void addTest() {
        assertEquals(new VP(3.0,3.0,3.0).length(), vpa2n2.add(vpa1n1).length());
    }
    @Test
    void addSelfTest() {
        vpa2n1 = vpa2n1.add(vpa1n1);
        assertFalse(vpa2n1.equals(vpa2n2));
    }

    @Test
    void angel2DPositiveTest() {
        assertEquals(45, VP.angle2D(2.0,0.0,2.0,2.0));
    }
    @Test
    void angel2DNegativeTest() {
        assertEquals(-45, VP.angle2D(2.0,0.0,2.0,-2.0));
    }
    @Test
    void xAngelTest() {
        assertEquals(90, a.xAngle(b));
    }
    @Test
    void yAngelTest() {
        assertEquals(135, a.yAngle(b));
    }
    @Test
    void divNumberZero() {
        assertEquals(0, b.div(2).get(0));
    }
    @Test
    void divZeroNumber() {
        assertEquals(0, VP.zero(dimensions).div(2).get(0));
        assertEquals(0, VP.zero(dimensions).div(2).get(1));
        assertEquals(0, VP.zero(dimensions).div(2).get(2));
    }
}