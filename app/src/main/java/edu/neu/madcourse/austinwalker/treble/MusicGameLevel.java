package edu.neu.madcourse.austinwalker.treble;

public class MusicGameLevel {
    private String levelName;
    private String levelDescription;
    private String introText;
    private String successText;
    private String failureText;

    private int alertIconId;

    public MusicGameLevel(String levelName, String levelDescription, String introText, String successText, String failureText) {
        this.levelName = levelName;
        this.levelDescription = levelDescription;
        this.introText = introText;
        this.successText = successText;
        this.failureText = failureText;
    }

    public String getIntroText() {
        return introText;
    }

    public String getSuccessText() {
        return successText;
    }

    public String getFailureText() {
        return failureText;
    }

    public int getAlertIconId() {
        return alertIconId;
    }

    public LevelTile getLevelTile() {
        return new LevelTile(levelName, levelDescription);
    }
}
