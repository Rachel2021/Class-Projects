
public class Score implements Comparable<Score> {
	
	private int score;
	private String nickname;
	private String age;
	
	public Score(int score, String nickname, String age) {
		this.score = score;
		this.nickname = nickname;
		this.age = age;
	}
	
	public int getScore() {
		return score;
	}
	
	public String getNickname() {
		return nickname;
	}
	
	public String getAge() {
		return age;
	}
	
	public int compareTo(Score s) {
		return (score - s.getScore());
	}
	
	public boolean equals(Object s) {
		Score scoreToCompare = (Score) s;
		return (score == scoreToCompare.getScore() && nickname.equals(scoreToCompare.getNickname())
				&& age.equals(scoreToCompare.getAge()));
	}

}
