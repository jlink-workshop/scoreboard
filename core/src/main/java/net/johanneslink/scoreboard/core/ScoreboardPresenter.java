package net.johanneslink.scoreboard.core;

public class ScoreboardPresenter {

    private ScoreboardView view;
    private Score currentScore = Score.create(0, 0);
    private Team currentSelectedTeam = Team.NONE;

    public void register(final ScoreboardView view) {
        this.view = view;
        displayCurrentScore();
        displaySelectedTeam();
    }

    public void select(final Team team) {
        currentSelectedTeam = team;
        displaySelectedTeam();
    }

    public void score(final Points points) {
        setScore(currentSelectedTeam.calculateScore(points, currentScore));
        select(Team.NONE);
    }

    public void setScore(final Score newScore) {
        if (!currentScore.equals(newScore)) {
            currentScore = newScore;
            displayCurrentScore();
        }
    }

    private void displayCurrentScore() {
        view.displayScore(currentScore);
    }

    private void displaySelectedTeam() {
        view.displaySelectedTeam(currentSelectedTeam);
    }

}
