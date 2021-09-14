import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Mario extends GameObj {
	
    public static final String IMG_FILE = "files/mario.png";
    public static final int WIDTH = 30;
    public static final int HEIGHT = 50;
    public static final int INIT_POS_X = 200; 
    public static final int INIT_POS_Y = 115;
    public static final int INIT_VEL_X = 0;
    public static final int INIT_VEL_Y = 0;
    public static final int JUMP_HEIGHT = 30;
    
    private boolean jumpUp; // true if the user pressed the up arrow key and mario is on his way up
    private boolean comeDown; // true if mario is on the descent down from a jump

    private static BufferedImage img;

    public Mario(int courtWidth, int courtHeight) {
        super(INIT_VEL_X, INIT_VEL_Y, INIT_POS_X, INIT_POS_Y, WIDTH, HEIGHT, courtWidth, courtHeight);

        try {
            if (img == null) {
                img = ImageIO.read(new File(IMG_FILE));
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
        
        jumpUp = false;
        comeDown = false;
    }
  

    @Override
    public void draw(Graphics g, int marioPx) {
		g.drawImage(img, 285, this.getPy(), this.getWidth(), this.getHeight(), null);
    }
    
    /**
     * Similar idea to move, but causes Mario to jump in the air. 
     */
    public void jump() {
    	if (this.getJumpUp()) {
    		int currY = this.getPy();
    		int newY = currY;
    		
    		if (currY > JUMP_HEIGHT) {
    			newY = currY - 4;
    			
    		}
    		if (newY <= JUMP_HEIGHT) {
    			this.setComeDown(true);
    			this.setJumpUp(false);
    			newY = JUMP_HEIGHT;
    		}
    		this.setPy(newY);
    	} 
    	
    	
    	else if (this.getComeDown()) {
    		int currY = this.getPy();
    		int newY = currY + 6;
    		
    		if (newY > INIT_POS_Y) {
    			this.setPy(INIT_POS_Y);
    			this.setComeDown(false);
    		} else {
    			this.setPy(newY);
    		}
    	}	
    }
    
    
    @Override
    public void move() {
    	int currX = this.getPx();
    	int newX = currX + this.getVx();
    	this.setPx(newX);
    }

    
    
    //if Mario is killed by a Goomba
    // true = Mario is killed by the Goomba
    @Override
    public boolean killed(Object c) {
    	Goomba goomba = (Goomba) c;
    	
    	int marioLowerEdge = this.getPy() + this.getHeight();
    	int marioRightEdge = this.getPx() + this.getWidth();
    	int marioLeftEdge = this.getPx();
    	int goombaLeftEdge = goomba.getPx();
    	int goombaRightEdge = goomba.getPx() + goomba.getWidth();
    	int goombaUpperEdge = goomba.getPy();
    	
    	boolean xpos = (marioRightEdge >= goombaLeftEdge && marioRightEdge < goombaLeftEdge + 5) || 
    			(marioLeftEdge <= goombaRightEdge && marioLeftEdge > goombaRightEdge - 5);
    	boolean ypos = (marioLowerEdge > goombaUpperEdge);
    	
    	return (xpos && ypos);
    }
    

    
    public boolean getJumpUp() {
    	return this.jumpUp;
    }
    public boolean getComeDown() {
    	return this.comeDown;
    }
    
    public void setJumpUp(boolean jump) {
    	jumpUp = jump;
    }
    
    public void setComeDown(boolean jump) {
    	comeDown = jump;
    }

}

