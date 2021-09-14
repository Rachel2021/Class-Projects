import java.awt.Graphics;
import java.util.Set;
import java.util.TreeSet;

public class CloudsSet {
	
	private Set<Cloud> clouds;
	
	public static final int CLOUD_WIDTH = 50;
	
	public static final int CLOUD_VELOCITY = 1;
	
	public CloudsSet() {
		clouds = new TreeSet<>();
		Cloud cloud1 = new Cloud(100, 20, CLOUD_WIDTH, 30);
		Cloud cloud2 = new Cloud(400, 20, CLOUD_WIDTH, 30);
		clouds.add(cloud1);
		clouds.add(cloud2);
	}
	
	public void move() {
		Set<Cloud> cloudsToRemove = new TreeSet<>();
		for (Cloud cloud : clouds) {
			int currX = cloud.getPx();
			int newX = currX - CLOUD_VELOCITY;
			int width = cloud.getWidth();
			
			newX = Math.max(newX, 0);
			
			// decreasing/leaving cloud on left
			if (newX == 0) {
				cloud.setWidth(width - CLOUD_VELOCITY);
			}
			
			// cloud is gone to left
			if (cloud.getWidth() == 0) {
				cloudsToRemove.add(cloud);
			}
			
			// entering/new cloud on right
			if (currX + width >= GameCourt.COURT_WIDTH) {
				int newWidth = width + CLOUD_VELOCITY;
				newWidth = Math.min(newWidth, CLOUD_WIDTH);
				cloud.setWidth(newWidth);
			}
				
			cloud.setPx(newX);
		}
		
		for (Cloud cloud : cloudsToRemove) {
			clouds.remove(cloud);
		}
	}
	
	
	public void updateSet() {
		if (clouds.size() < 2) {
			Cloud newCloud = new Cloud(GameCourt.COURT_WIDTH - 1, 20, 1, 30);
			clouds.add(newCloud);
		}
	}
	
	public int getNumClouds() {
		return clouds.size();
	}
	

   public void draw(Graphics g) {
	   for (Cloud cloud : clouds) {
		   cloud.draw(g);
	   }
   }
	   
}
