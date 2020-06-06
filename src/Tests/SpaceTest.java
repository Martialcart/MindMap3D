package Tests;

import Tools.Point;
import Tools.Space;
import Tools.VP;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpaceTest {
    private Space world = new Space(3);
    private double amount = 2;
    private int dimensions = 2;
    private Point vpa2n1 = new Point(new VP(2.0,2.0,2.0));
    private Point vpa2n2 = new Point(new VP(2.0,2.0,2.0));
    private Point vpa1n1 = new Point(new VP(1.0,1.0,1.0));
    private Point a = new Point(new VP(5.0, 5.0, 5.0));
    private Point b = new Point(new VP(0.0,3.0,3.0));
    private Point zero = new Point(VP.zero(dimensions));

    @Test
    void addPointTest() {
        world.add(vpa2n1);
        world.add(vpa1n1);
        world.add(a);
        world.add(b);
        world.addFrom(zero, b);
    }

    @Test
    void getAllWithinTest() {
        world.add(vpa2n1);
        world.add(vpa1n1);
        world.add(a);
        world.add(b);
        world.addFrom(zero, b);
        assertEquals(4, world.getAllWithin(zero, 2.0).size());
    }
}
