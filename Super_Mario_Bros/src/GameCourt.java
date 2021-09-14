
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.*;


/**
 * GameCourt
 * 
 * This class holds the primary game logic for how different objects interact with one another. Take
 * time to understand how the timer interacts with the different methods and how it repaints the GUI
 * on every tick().
 */


@SuppressWarnings("serial")
public class GameCourt extends JPanel {

    private Mario mario; // Mario, keyboard controlled
 
    public boolean playing = false; // whether the game is running 
    private JLabel status; // Current status text, i.e. "Running..." 
    private CloudsSet clouds = new CloudsSet();
    private Set<Goomba> goombas = new TreeSet<>();
    private boolean movingRight = false;
    
    private String userName = "";
    private String userAge = "";

    // Game constants
    public static final int COURT_WIDTH = 600;
    public static final int COURT_HEIGHT = 200;
    public static final int LEVEL_LENGTH = 6000;
    public static final int VELOCITY = 2;  

    // Update interval for timer, in milliseconds
    public static final int INTERVAL = 35;
    

    public GameCourt(JLabel status) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        Timer timer = new Timer(INTERVAL, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tick();
            }
        });
        timer.start(); 

        setFocusable(true);
        
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    mario.setVx(-VELOCITY);
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    mario.setVx(VELOCITY);
                    movingRight = true;
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    mario.setJumpUp(true);
                }
            }

            public void keyReleased(KeyEvent e) {
                mario.setVx(0);
                mario.setVy(0);
                movingRight = false;
            }
        });

        this.status = status;
        
    }

    /**
     * (Re-)set the game to its initial state.
     */
    public void reset() {
        mario = new Mario(COURT_WIDTH, COURT_HEIGHT);
    
        goombas.clear();
        for (int i = 0; i < 6000; i+=280) {
        	Goomba goomba = new Goomba(i, Goomba.INIT_POS_Y);
        	goombas.add(goomba);
        }
        
        clouds = new CloudsSet();
        
        playing = true;
        status.setText("Running...");

        requestFocusInWindow();
    }
    
    /**
     * Determine the number of Goombas the user has killed (public only for testing purposes).  
     * @return the number of goombas the user killed in the current round of playing
     */
    public int numKilled() {
    	int num = 0;
    	for (Goomba goomba : goombas) {
    		if (!goomba.getAlive()) {
    			num = num + 1;
    		}
    	
    	}
    	return num;
    }
    
    public void setUserName (String name) {
    	userName = name;
    }
    
    public void setUserAge (String age) {
    	userAge = age;
    }
    

    /**
     * This method is called every time the timer defined in the constructor triggers.
     */
    void tick() {
        if (playing) {
        	
        	int currentScore = numKilled();
        	status.setText("Your current score is " + currentScore);

            mario.move();
            mario.jump();
            
            for (Goomba goomba : goombas) {
            	goomba.move();
            }
             
            if (movingRight) {
            	clouds.move();
            	clouds.updateSet();
            }

        	
        	for (Goomba goomba : goombas) {
            	if (goomba.getAlive() && mario.killed(goomba)) {
            		playing = false;  		
            	} else if (goomba.killed(mario)) {
            		goomba.setAlive(false);
            	}
        	}
        	
        	if (mario.getPx() > 5900) {
        		playing = false;
        	}
        	
        	if (!playing) {
        		int goombasKilled = numKilled();
        		ScoreKeeper keeper = null;
        		try {
        			
        			keeper = new ScoreKeeper("files/sampleScores.txt");

            		if (userName != null) {
                        Score newScore = new Score(goombasKilled, userName, userAge);
                        keeper.addEntry(newScore);
            			String toShow = keeper.getTopScores();
            			if (mario.getPx() > 5900) {
            				status.setText("You Win! Your score: " + goombasKilled + 
            						". High scores: " + toShow);
        				} else {
            				status.setText("Game Over! Your score: " + goombasKilled + 
            						". High scores: " + toShow);
        				}
            			keeper.writeFile("files/sampleScores.txt");
            		} else {
            			String message = "Game Over! Your score was " + goombasKilled;
            			status.setText(message);
            		}
        		} catch (ScoreKeeper.FormatException e) {
        			String message = "Game Over! Your score was " + goombasKilled;
        			status.setText(message);
        		} catch (IOException e) {
        			String message = "Game Over! Your score was " + goombasKilled;
        			status.setText(message);
        		} 
            }

            // update the display
            repaint();
        }
    }
    

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        int topOfGround = Mario.INIT_POS_Y + Mario.HEIGHT;
        
        // draw the sky
        Color skyColor = new Color(90, 184, 255);
        g.setColor(skyColor);
        g.fillRect(0, 0, COURT_WIDTH, topOfGround);

		// draw the ground as plain green
		Color groundColor = new Color(153, 102, 51);
        g.setColor(groundColor);
        g.fillRect(0, topOfGround, COURT_WIDTH, COURT_HEIGHT - topOfGround);
	       
        // draw mario
        mario.draw(g, mario.getPx());
        
        // draw the clouds
        clouds.draw(g);
        
        // draw the goombas (mario's enemies)
        for (Goomba goomba : goombas) {
        	goomba.draw(g, mario.getPx());
        }  
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(COURT_WIDTH, COURT_HEIGHT);
    }
    
    /**
     * For testing purposes only. 
     */
    public void killAllGoombas() {
    	for (Goomba goomba : goombas) {
    		goomba.setAlive(false);
    	}
    }
    
    /**
     * For testing purposes only.
     * @param x
     */
    public void setMarioPx(int x) {
    	mario.setPx(x);
    }
    
    /**
     * For testing purposes only. 
     * @return
     */
    public boolean getPlayingStatus () {
    	return playing;
    }
    
    
}