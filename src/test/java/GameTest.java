import com.gvnn.trb.Game;
import com.gvnn.trb.exception.ToyRobotException;
import com.gvnn.trb.simulator.Direction;
import com.gvnn.trb.simulator.Position;
import com.gvnn.trb.simulator.SquareBoard;
import com.gvnn.trb.simulator.ToyRobot;
import com.gvnn.trb.simulator.Obstruction;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class GameTest {

    final int BOARD_ROWS = 5;
    final int BOARD_COLUMNS = 5;
    @Rule
    public org.junit.rules.ExpectedException thrown = ExpectedException.none();

    @Test
    public void testPlacing() throws ToyRobotException {

        SquareBoard board = new SquareBoard(BOARD_COLUMNS, BOARD_ROWS);
        ToyRobot toyRobot = new ToyRobot();
        Obstruction block = new Obstruction();
        Game game = new Game(board, toyRobot, block);

        Assert.assertTrue(game.placeToyRobot(new Position(0, 1)));
        Assert.assertTrue(game.placeToyRobot(new Position(2, 2)));
        Assert.assertFalse(game.placeToyRobot(new Position(6, 6)));
        Assert.assertFalse(game.placeToyRobot(new Position(-1, 5)));
    }

    @Test
    public void testPlacingExceptions() throws ToyRobotException {

        SquareBoard board = new SquareBoard(BOARD_COLUMNS, BOARD_ROWS);
        ToyRobot toyRobot = new ToyRobot();
        Obstruction block = new Obstruction ();
        Game game = new Game(board, toyRobot, block);

        thrown.expect(ToyRobotException.class);
        game.placeToyRobot(null);

        //thrown.expect(ToyRobotException.class);
        //game.placeToyRobot(new Position(0, 1, null));
    }

    @Test
    public void testEval() throws ToyRobotException {

        SquareBoard board = new SquareBoard(BOARD_COLUMNS, BOARD_ROWS);
        ToyRobot toyRobot = new ToyRobot();
        Obstruction block = new Obstruction();
        Game game = new Game(board, toyRobot, block);

        game.eval("PLACE 0,0");
        Assert.assertEquals("E: (0,0) B:", game.eval("REPORT"));


        // invalid commands
        thrown.expect(ToyRobotException.class);
        Assert.assertEquals("Invalid command", game.eval("PLACE12NORTH"));
        thrown.expect(ToyRobotException.class);
        Assert.assertEquals("Invalid command", game.eval("LEFFT"));
        thrown.expect(ToyRobotException.class);
        Assert.assertEquals("Invalid command", game.eval("RIGHTT"));
    }

    @Test
    public void testPath1() throws ToyRobotException {

        SquareBoard board = new SquareBoard(BOARD_COLUMNS, BOARD_ROWS);
        ToyRobot toyRobot = new ToyRobot();
        Obstruction block = new Obstruction();
        Game game = new Game(board, toyRobot, block);

        game.eval("PLACE 0,0");
        Assert.assertEquals("E: (0,0) B:", game.eval("REPORT"));

        game.eval("BLOCK 0,1");
        Assert.assertEquals("E: (0,0) B:(0,1)", game.eval("REPORT"));

        game.eval("BLOCK 0,2");
        Assert.assertEquals("E: (0,0) B:(0,1)(0,2)", game.eval("REPORT"));


        //game.eval("EXPLORER 0,3");
        Assert.assertEquals("PATH: (0,0)(1,0)(1,1)(1,2)(1,3)(0,3)", game.eval("EXPLORER 0,3"));

        game.eval("BLOCK 1,1");
        Assert.assertEquals("E: (0,0) B:(0,1)(0,2)(1,1)", game.eval("REPORT"));

        Assert.assertEquals("PATH: (0,0)(1,0)(2,0)(2,1)(2,2)(1,2)(1,3)(0,3)", game.eval("EXPLORER 0,3"));

        game.eval("PLACE 0,2");
        Assert.assertEquals("PATH: (0,2)(0,3)", game.eval("EXPLORER 0,3"));



        // invalid commands
        thrown.expect(ToyRobotException.class);
        Assert.assertEquals("Invalid command", game.eval("PLACE12NORTH"));
        thrown.expect(ToyRobotException.class);
        Assert.assertEquals("Invalid command", game.eval("LEFFT"));
        thrown.expect(ToyRobotException.class);
        Assert.assertEquals("Invalid command", game.eval("RIGHTT"));
    }

}
