import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.TreeSet;


public class ScoreKeeper {
	
	private ArrayList<Score> scores = new ArrayList<>();
	private BufferedReader reader;
	private String curr = "";
	
    public static class FormatException extends Exception {
        public FormatException(String msg) {
            super(msg);
        }
    }
    
    /**
     * Construct a new ScoreKeeper.
     * @param fileScoresIn: the name of the file to read scores from
     * @throws IOException
     * @throws FormatException
     */
	public ScoreKeeper(String fileScoresIn) throws IOException, FormatException {
    	//reads the file and appends info to data structure
    	
		// try catches here
		// for invalid/wrong formatting of scores in file, null files, etc... 
		// ^^^^ make sure you have all edge cases
	
		reader = new BufferedReader(new FileReader(fileScoresIn));

    	curr = reader.readLine();
    	while (curr != null && curr.length() > 1) {
    		curr = curr.trim();
			
    		if (curr.startsWith(",") || curr.endsWith(",") || !curr.contains(",")) {
    			throw new FormatException("invalid input");
    		}
    		
    		int firstComma = curr.indexOf(",");
    		int secondComma = curr.indexOf(",", firstComma + 1);

    		String scoreString = curr.substring(0, firstComma);
    		scoreString = scoreString.trim();
    		
    		if (scoreString.length() < 1) {
    			throw new FormatException("invalid input: no score given");
    		}
    		
    		int score = Integer.parseInt(scoreString);

    		String name = curr.substring(firstComma + 1, secondComma);
    		name = name.trim();
    		
    		String age = curr.substring(secondComma + 1);
    		age = age.trim();
    		
    		Score newScore = new Score(score, name, age);
    		if (!scores.contains(newScore)) {
    			scores.add(newScore);
    		}

    		curr = reader.readLine();

    	}
    	//if (reader != null) {
    		reader.close();
    	//}
	}
	
	/**
	 * Get the top scores (up to 3). 
	 * @return
	 */
	public String getTopScores() {
		java.util.Collections.sort(scores);
		java.util.Collections.reverse(scores);
		
		int numScoresToShow = Math.min(3, scores.size());
		String ans = "";
		
		for (int i = 0; i < numScoresToShow; i++) {
			Score thisScore = scores.get(i);	
			ans = ans + thisScore.getNickname() + " age " + thisScore.getAge() + ": " + 
					thisScore.getScore() + "; ";
		}
		return ans;
	}
	
	/**
	 * Write the scores to a new file.
	 * @param fileScoresOut: the name of the file to write the scores to
	 * @throws IOException
	 */
	public void writeFile(String fileScoresOut) throws IOException {
		try {
			Writer out = new BufferedWriter(new FileWriter(fileScoresOut));
			for (Score score : scores) {
				out.write(Integer.toString(score.getScore()));
				out.write(", ");
				out.write(score.getNickname());
				out.write(", ");
				out.write(score.getAge());
				out.write("\n");
			}
			out.close();
		} catch (IOException e) {
			// don't do anything if problem with file
		}
	}
	
	/**
	 * Add a new score to this ScoreKeeper.
	 * @param newScore: the score to add
	 */
	public void addEntry(Score newScore) {
		scores.add(newScore);
	}
	
	/**
	 * Check if someone's score has the nickname being checked. 
	 * @param name: the name to check
	 * @return true if the name is taken, false if it isn't
	 */
	public boolean isNameTaken(String name) {
		boolean ans = false;
		for (Score score : scores) {
			if (name.equals(score.getNickname())) {
				ans = true;
			}
		}
		return ans;
	}

}
