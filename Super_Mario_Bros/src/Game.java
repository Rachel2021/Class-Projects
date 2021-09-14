
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;

/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class Game implements Runnable {
    public void run() {

        // Top-level frame in which game components live
        final JFrame frame = new JFrame("Super Mario Bros");
        frame.setLocation(300, 300);
        
        String instructions = "Welcome to Rachel's primitive version of Super Mario Bros! "
        		+ "Here are the instructions. \nYour objective, as Mario, is to clear the level "
        		+ "without being killed by a Goomba, \nwhich are tiny mushrooms. If you run into "
        		+ "one, you will die. To avoid \nthe Goombas you can either jump over them, or jump "
        		+ "on top of them to kill them. You will \ngain one point for every Goomba you kill. "
        		+ "\nTo run forward or backward, use the right and left arrow keys. \nTo jump, press "
        		+ "the up arrow key. The highest possible score is 22. Good luck!";
        JOptionPane.showMessageDialog(frame, instructions, "INSTRUCTIONS:", 
        		JOptionPane.PLAIN_MESSAGE);
        
        String inputName = JOptionPane.showInputDialog(frame, "Please enter a nickname", "USER INPUT", 
                JOptionPane.PLAIN_MESSAGE);
        
		ScoreKeeper keeper = null;
		
		try {
			keeper = new ScoreKeeper("files/sampleScores.txt");
			while (keeper.isNameTaken(inputName)) {
		        inputName = JOptionPane.showInputDialog(frame, "That nickname is already taken. "
		        		+ "Please enter another", "USER INPUT", JOptionPane.PLAIN_MESSAGE);
			}  
		} catch (ScoreKeeper.FormatException e) {
			inputName = null;
		} catch (IOException e) {
			inputName = null;
		} 
		
        String inputAge = JOptionPane.showInputDialog(frame, "Please enter your age", "USER INPUT", 
                JOptionPane.PLAIN_MESSAGE);
        try { 
        	int numAge = Integer.parseInt(inputAge);
        } catch (NumberFormatException ex) {
            inputAge = JOptionPane.showInputDialog(frame, "Invalid number entered. "
            		+ "Please try again!", "USER INPUT", JOptionPane.PLAIN_MESSAGE);
        }
        
        

        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Running...");
        status_panel.add(status);

        // Main playing area
        final GameCourt court = new GameCourt(status);
        frame.add(court, BorderLayout.CENTER);
        court.setUserName(inputName);
        court.setUserAge(inputAge);

        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        // Note here that when we add an action listener to the reset button, we define it as an
        // anonymous inner class that is an instance of ActionListener with its actionPerformed()
        // method overridden. When the button is pressed, actionPerformed() will be called.
        final JButton reset = new JButton("Reset");
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                court.reset();
            }
        });
        control_panel.add(reset);

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
        // Start game
        court.reset();
    }

    /**
     * Main method run to start and run the game. 
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }
}