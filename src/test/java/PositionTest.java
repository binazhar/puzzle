import com.gvnn.trb.simulator.Direction;
import com.gvnn.trb.simulator.Position;
import org.junit.Assert;
import org.junit.Test;

public class PositionTest {

    @Test
    public void testGetNextPosition() throws Exception {

        Position position = new Position(0, 0);

        Assert.assertEquals(position.getX(), 0);
        Assert.assertEquals(position.getY(), 0);


        position = new Position(1, 2);
        Assert.assertNotEquals(position.getX(), 2);
        Assert.assertEquals(position.getY(), 2);


        position = new Position (6,8);
        Assert.assertEquals(position.getX(), 6);
        Assert.assertEquals(position.getY(), 8);

    }
}
