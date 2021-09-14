import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Goomba extends GameObj implements Comparable<Goomba> {
	
    public static final String IMG_FILE = "files/goomba.png";
    public static final int WIDTH = 30;
    public static final int HEIGHT = 50;
    public static final int INIT_POS_X = 250;
    public static final int INIT_POS_Y = 115;
    public static final int INIT_VEL_X = 0;
    public static final int INIT_VEL_Y = 0;
    
    // the point at which to turn around 
    private int switchPx;
    
    private boolean alive;

    private static BufferedImage img;

    public Goomba(int px, int py) {
        super(INIT_VEL_X, INIT_VEL_Y, px, py, WIDTH, HEIGHT, GameCourt.COURT_WIDTH, GameCourt.COURT_HEIGHT);

        try {
            if (img == null) {
                img = ImageIO.read(new File(IMG_FILE));
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
        alive = true;
        switchPx = px + 25;
        this.setVx(1);
    }
    
    
    @Override
    public void draw(Graphics g, int marioPx) {
    	int goombaPx = this.getPx();
    	int goombaW = this.getWidth();
    	if (alive && (goombaPx >= marioPx - 285 || goombaPx + goombaW <= marioPx + 315)) {
    		int xToDrawAt = 285 - (marioPx - goombaPx);
    		g.drawImage(img, xToDrawAt, this.getPy(), this.getWidth(), this.getHeight(), null);
    	}
    }
    
    @Override
    public void move() {
    	
    	int px = this.getPx();
    	int vx = this.getVx();
    	if (switchPx != px) {
    		this.setPx(px + vx);
    	} else {
    		// random int from 30 to 40
    		int newPath = 30 + (int)(Math.random() * (11));
    		if (vx > 0) {
    			switchPx = px - newPath;
    		} else {
    			switchPx = px + newPath;
    		}
    		this.setVx(-1 * vx);
    		this.setPx(px + (-1 * vx));
    	}
    }

    
    @Override
    public int compareTo(Goomba c) {
    	return -1;
    }
    
    public boolean getAlive() {
    	return alive;
    }
    
    public void setAlive(boolean alive) {
    	this.alive = alive;
    }
    
    // if goomba is killed by mario
    @Override
    public boolean killed(Object c) {
    	
    	Mario mario = (Mario) c;
    	
    	int marioLowerEdge = mario.getPy() + mario.getHeight();
    	int marioRightEdge = mario.getPx() + mario.getWidth();
    	int marioLeftEdge = mario.getPx();
    	int goombaLeftEdge = this.getPx();
    	int goombaRightEdge = this.getPx() + this.getWidth();
    	int goombaUpperEdge = this.getPy();
    	
    	boolean ypos = (marioLowerEdge > goombaUpperEdge - 10) && (marioLowerEdge < goombaUpperEdge + 10);
    	boolean xpos = (marioRightEdge > goombaLeftEdge) && (marioLeftEdge < goombaRightEdge);
    	
    	return (mario.getComeDown() && ypos && xpos);


    }

}
