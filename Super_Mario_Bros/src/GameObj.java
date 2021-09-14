import java.awt.Graphics;


/** 
 * A character in the game. 
 */
public abstract class GameObj {

    /*
     * Current x and y position of the character (upper left hand coordinate)
     */
	private int px; 
    private int py; 

    /*
     * Dimensions (width and height) of the character
     */
    private int width;
    private int height; 

    /*
     * Horizontal and vertical velocity of the character. 
     */
    private int vx; 
    private int vy;

    /* 
     * Maximum positions for the upper-left hand corner of the object.
     */
    private int maxX; 
    private int maxY;

    
    /**
     * Constructor
     */
    public GameObj(int vx, int vy, int px, int py, int width, int height, int courtWidth,
        int courtHeight) {
    	
        this.vx = vx;
        this.vy = vy;
        this.px = px;
        this.py = py;
        this.width  = width;
        this.height = height;

        this.maxX = GameCourt.LEVEL_LENGTH - width;
        this.maxY = courtHeight - height;
    }

    /*** GETTERS **********************************************************************************/
    
    /**
     * Get the x position of the upper left coordinate
     * @return x position of the upper left coordinate 
     */
    public int getPx() {
        return this.px;
    }
    
    /**
     * Get the y position of the upper left coordinate
     * @return y position of the upper left coordinate
     */
    public int getPy() {
        return this.py;
    }
    
    /**
     * Get the horizontal velocity 
     * @return horizontal velocity
     */
    public int getVx() {
        return this.vx;
    }
    
    /**
     * Get the vertical velocity 
     * @return vertical velocity 
     */
    public int getVy() {
        return this.vy;
    }
    
    /**
     * Get the width 
     * @return width
     */
    public int getWidth() {
        return this.width;
    }
    
    /**
     * Get the height
     * @return height
     */
    public int getHeight() {
        return this.height;
    }
    


    /*** SETTERS **********************************************************************************/
    
    /**
     * Set the x coordinate of the upper left position
     * @param px: new x coordinate to set it to
     */
    public void setPx(int px) {
        this.px = Math.min(px, maxX);
    }

    /**
     * Set the y coordinate of the upper left position
     * @param py: new y coordinate to set it to
     */
    public void setPy(int py) {
        this.py = Math.min(py, maxY);
    }

    /**
     * Set the horizontal velocity
     * @param vx: the new horizontal velocity to set it to
     */
    public void setVx(int vx) {
        this.vx = vx;
    }
    
    /**
     * Set the vertical velocity
     * @param vy: the new vertical velocity to set it to
     */
    public void setVy(int vy) {
        this.vy = vy;
    }
    

    /*** ABSTRACT METHODS ****************************************************************/
    
    /**
     * Draw the character
     * @param g: the graphics context in which to draw the object
     * @param marioPx: the x position of mario 
     */
    public abstract void draw(Graphics g, int marioPx);

    /**
     * Move the character 
     */
    public abstract void move();
    
    /**
     * Determine if this character has been killed by another
     * @param c: the character which may have killed this character 
     * @return true if this character has been killed by the input character, false if not
     */
    public abstract boolean killed(Object c);
    
    
}