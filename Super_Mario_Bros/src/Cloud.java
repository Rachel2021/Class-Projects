import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

import javax.imageio.ImageIO;

public class Cloud implements Comparable<Cloud> {
	
    
   private int px; // current x position of the object
   private int py; // current y position of the object

   private int width; //  width of the object 
   private int height; // height of the object

   /* 
    * Upper bounds of the area in which the object can be positioned. Maximum permissible x, y
    * positions for the upper-left hand corner of the object.
    */
   private int maxX;
   private int maxY;
   private static BufferedImage img;
   public static final String IMG_FILE = "files/cloud.png";

   /**
    * Constructor
    */
   public Cloud(int px, int py, int width, int height) {
       this.px = px;
       this.py = py;
       this.width  = width;
       this.height = height;

       // take the width and height into account when setting the bounds for the upper left corner
       // of the object.
      // maxX = 600;
       maxY = 0;
       
       try {
           if (img == null) {
               img = ImageIO.read(new File(IMG_FILE));
           }
       } catch (IOException e) {
           System.out.println("Internal Error:" + e.getMessage());
       }
   }
   
   public int getPx() {
	   return px;
   }
   
   public int getPy() {
	   return py;
   }
   
   public int getWidth() {
	   return width;
   }
   
   public int getHeight() {
	   return height;
   }
   
   public void setPx(int px) {
	   this.px = px;
   }
   
   public void setPy(int py) {
	   this.py = py;
   }
   
   public void setWidth(int width) {
	   this.width = width;
   }

   @Override
   public boolean equals(Object c) {
	   Cloud cloud = (Cloud) c;
	   return (px == cloud.getPx() && py == cloud.getPy() && 
			   width == cloud.getWidth() && height == cloud.getHeight());
   }
   
   @Override
   public int compareTo(Cloud c) {
	   if (this.equals(c)) {
		   return 0;
	   } else {
		   if (px < c.getPx()) {
			   return -1;
		   } else {
			   return 1;
		   }
	   }
   }
   
   public void draw(Graphics g) {
	   
	   int px = this.getPx();
	   
	   if (px == 0) { // leaving on left
		   int subPx = CloudsSet.CLOUD_WIDTH - this.getWidth();
		   int width = CloudsSet.CLOUD_WIDTH - subPx;
		   BufferedImage subImg = img.getSubimage(subPx, 0, width, this.getHeight());
		   g.drawImage(subImg, this.getPx(), this.getPy(), this.getWidth(), this.getHeight(), null);
	   } else if (px > (GameCourt.COURT_WIDTH - CloudsSet.CLOUD_WIDTH)) { // entering on right
		   BufferedImage subImg = img.getSubimage(0, 0, this.getWidth(), this.getHeight());
		   g.drawImage(subImg, this.getPx(), this.getPy(), this.getWidth(), this.getHeight(), null);
	   } else { // normal cloud
		   g.drawImage(img, this.getPx(), this.getPy(), this.getWidth(), this.getHeight(), null);
	   }

   }
   
   

}
