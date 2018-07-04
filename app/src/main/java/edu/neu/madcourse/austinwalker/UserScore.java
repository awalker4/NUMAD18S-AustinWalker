package edu.neu.madcourse.austinwalker;

public class UserScore {
    public String imei;
    public String gameTime;
    public int finalScore;
    public String highestWord;
    public int highestWordScore;

    public UserScore(String id, String time, int score, String word, int wordScore) {
        imei = id;
        gameTime = time;
        finalScore = score;
        highestWord = word;
        highestWordScore = wordScore;
    }
}
