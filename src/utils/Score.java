package utils;

public class Score {
	private static int _score;
	
	public static void addScore(int score){
		_score = _score + score;
	}

	public static int getScore(){
		return _score;
	}
	
	public static void reset(){
		_score = 0;
	}
}

