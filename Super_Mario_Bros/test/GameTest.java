import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JLabel;

import org.junit.Test;

/** 
 *  You can use this file (and others) to test your
 *  implementation.
 */

public class GameTest {
	
	
	// Goomba & Mario Collision & Resulting Alive/Dead Status Tests

	// mario directly above goomba, but space in between them, so not touching
	// so goomba shouldn't die
	@Test
	public void testCollisionGoombaDeath1() {
		Goomba goomba = new Goomba(10, 100);
		// mario initial px = 200, inital py = 115
		// mario width = 30, mario height = 50
		Mario mario = new Mario(GameCourt.COURT_WIDTH, GameCourt.COURT_HEIGHT);
		mario.setPx(10);
		mario.setPy(180);
		assertFalse(goomba.killed(mario));
		assertTrue(goomba.getAlive());
	}
	
	// mario coming down on goomba from above, but is slightly to the left of goomba, but close 
	// enough that the goomba should still die
	@Test
	public void testCollisionGoombaDeath2() {
		Goomba goomba = new Goomba(20, 100);
		// mario initial px = 200, inital py = 115
		// mario width = 30, mario height = 50
		Mario mario = new Mario(GameCourt.COURT_WIDTH, GameCourt.COURT_HEIGHT);
		mario.setPx(5);
		mario.setPy(50);
		mario.setComeDown(true);
		assertTrue(goomba.killed(mario));	
	}
	
	// mario coming down on goomba from above, but is slightly to the right of goomba, but close 
	// enough that the goomba should still die
	@Test
	public void testCollisionGoombaDeath3() {
		Goomba goomba = new Goomba(20, 100);
		// mario initial px = 200, inital py = 115
		// mario width = 30, mario height = 50
		Mario mario = new Mario(GameCourt.COURT_WIDTH, GameCourt.COURT_HEIGHT);
		mario.setPx(35);
		mario.setPy(50);
		mario.setComeDown(true);
		assertTrue(goomba.killed(mario));	
	}
	
	// mario directly over goomba, goomba should die
	@Test
	public void testCollisionGoombaDeath4() {
		Goomba goomba = new Goomba(20, 100);
		// mario initial px = 200, inital py = 115
		// mario width = 30, mario height = 50
		Mario mario = new Mario(GameCourt.COURT_WIDTH, GameCourt.COURT_HEIGHT);
		mario.setPx(20);
		mario.setPy(50);
		mario.setComeDown(true);
		assertTrue(goomba.killed(mario));	
		
		// mario slightly in goomba already by the time it checks collision
		mario.setPx(20);
		mario.setPy(55);
		assertTrue(goomba.killed(mario));	
	}

	// mario is directly to the left of the goomba, touching, so should die
	@Test
	public void testCollisionMarioDeath1() {
		Goomba goomba = new Goomba(50, 100);
		// mario initial px = 200, inital py = 115
		// mario width = 30, mario height = 50
		Mario mario = new Mario(GameCourt.COURT_WIDTH, GameCourt.COURT_HEIGHT);
		mario.setPx(20);
		mario.setPy(100);

		assertTrue(mario.killed(goomba));	
		
		// mario dead irrespective of jump status
		mario.setComeDown(true);
		assertTrue(mario.killed(goomba));	
	}
	
	// mario is directly to the right of the goomba, touching, so should die
	@Test
	public void testCollisionMarioDeath2() {
		Goomba goomba = new Goomba(50, 100);
		// mario initial px = 200, inital py = 115
		// mario width = 30, mario height = 50
		Mario mario = new Mario(GameCourt.COURT_WIDTH, GameCourt.COURT_HEIGHT);
		mario.setPx(80);
		mario.setPy(100);

		assertTrue(mario.killed(goomba));	
		
		// mario dead irrespective of jump status
		mario.setComeDown(true);
		assertTrue(mario.killed(goomba));	
	}
	
	// mario and goomba are overlapping by next time step (if they weren't previously)
	// such that mario should die
	@Test
	public void testCollisionMarioDeath3() {
		Goomba goomba = new Goomba(50, 100);
		// mario initial px = 200, inital py = 115
		// mario width = 30, mario height = 50
		Mario mario = new Mario(GameCourt.COURT_WIDTH, GameCourt.COURT_HEIGHT);
		mario.setPx(78);
		mario.setPy(100);

		assertTrue(mario.killed(goomba));	
	}
	
	// mario and goomba are overlapping by next time step (if they weren't previously)
	// such that mario should die
	@Test
	public void testCollisionMarioDeath4() {
		Goomba goomba = new Goomba(50, 100);
		// mario initial px = 200, inital py = 115
		// mario width = 30, mario height = 50
		Mario mario = new Mario(GameCourt.COURT_WIDTH, GameCourt.COURT_HEIGHT);
		mario.setPx(22);
		mario.setPy(100);

		assertTrue(mario.killed(goomba));	
	}
	
	// mario is directly diagonal to the goomba, so that mario's bottom right corner touches 
	// goomba's upper left corner. Neither should die. 
	@Test
	public void testCollisionMarioDoesNotDie() {
		Goomba goomba = new Goomba(50, 100);
		// mario initial px = 200, inital py = 115
		// mario width = 30, mario height = 50
		Mario mario = new Mario(GameCourt.COURT_WIDTH, GameCourt.COURT_HEIGHT);
		mario.setPx(50 - mario.getWidth());
		mario.setPy(100 - mario.getHeight());

		assertFalse(mario.killed(goomba));	
		assertFalse(goomba.killed(mario));	
		
		mario.setComeDown(true);
		assertFalse(goomba.killed(mario));	
	}
	
	// Cloud tests:
	
	@Test
	public void testNumberOfCloudsDoesNotChange() {
		CloudsSet clouds = new CloudsSet();
		clouds.updateSet();
		assertEquals(clouds.getNumClouds(), 2);
	}
	
	
	@Test
	public void testNumberOfCloudsDoesNotChangeWithMove() {
		CloudsSet clouds = new CloudsSet();
		clouds.move();
		clouds.updateSet();
		assertEquals(clouds.getNumClouds(), 2);
	}
	
	@Test
	public void testNumberOfCloudsChanges() {
		CloudsSet clouds = new CloudsSet();
		for (int i = 0; i < 200; i++) {
			clouds.move();
		}
		assertEquals(clouds.getNumClouds(), 1);
		clouds.updateSet();
		assertEquals(clouds.getNumClouds(), 2);
	}
	
	// Game won/lose status tests:
	
	
	@Test
	public void testGoombasKilled() {
		JLabel status = new JLabel("tester");
		GameCourt court = new GameCourt(status);
		court.reset();
		court.killAllGoombas();
		assertEquals(court.numKilled(), 22);
		court = null;
	}
	

	// Score Keeping tests:
	
    /**
    * This is a helper method that compares two documents (used from hw08). 
    */
    public static void compareDocs(String out, String expected)
        throws IOException, FileNotFoundException {

        BufferedReader f1 = new BufferedReader(new FileReader(out));
        BufferedReader f2 = new BufferedReader(new FileReader(expected));

        try {
            String line1 = f1.readLine();
            String line2 = f2.readLine();
            while (line1 != null && line2 != null) {
                assertEquals("Output file did not match expected output.", line2, line1);
                line1 = f1.readLine();
                line2 = f2.readLine();
            }

            if (line1 != null) {
                fail("Expected end of file, but found extra lines in the output.");
            } else if (line2 != null) {
                fail("Expected more lines, but found end of file in the output. ");
            }
        } finally {
            f1.close();
            f2.close();
            
        }
    }

    @Test
    public void testWritingOut() throws IOException, ScoreKeeper.FormatException  {
    	ScoreKeeper keeper = new ScoreKeeper("files/testerScores.txt");
    	keeper.writeFile("files/newTestingScores.txt");
    	compareDocs("files/newTestingScores.txt", "files/testerScores.txt");
    }
    
    @Test(expected = ScoreKeeper.FormatException.class)
    public void testWrongFormatScores() throws IOException, ScoreKeeper.FormatException  {
    	ScoreKeeper keeper = new ScoreKeeper("files/badScores.txt");
    }
    
    @Test(expected = IOException.class)
    public void testFileDoesNotExist() throws IOException, ScoreKeeper.FormatException  {
    	ScoreKeeper keeper = new ScoreKeeper("files/fakeScores.txt");
    }
    
    @Test
    public void testWriteFileDoesNotExist() throws IOException, ScoreKeeper.FormatException  {
    	ScoreKeeper keeper = new ScoreKeeper("files/testerScores.txt");
    	keeper.writeFile("fakeScores.txt");
    	// should not throw an error
    }


}

