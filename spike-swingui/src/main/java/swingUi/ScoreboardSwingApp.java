package swingUi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ScoreboardSwingApp extends JFrame implements ScoreboardView {

	private static final long serialVersionUID = 1L;

	private static class FakeTimeService implements GameTimeService {
		int secs = 60;

		@Override
		public int getRemainingSeconds() {
			if (secs > 0) secs--;
			else secs = 59;
			return secs;
		}

		@Override
		public int getRemainingMinutes() {
			return 9;
		}
	}

	private static Font SCORE_FONT = new Font("Courier New", Font.BOLD, 48);
	private static Font TIME_FONT = new Font("Courier New", Font.PLAIN, 24);
	private static javax.swing.Timer checkTimeTrigger;

	private JLabel scoreALabel;
	private JLabel scoreBLabel;
	private JLabel timeLabel;
	private JButton teamAButton;
	private JButton teamBButton;
	private JButton score1Button;
	private JButton score2Button;
	private JButton score3Button;
	private final ScoreboardPresenter scoreboard;

	public static void main(String[] args) {
		GameTimeService timeService = new FakeTimeService();
		ScoreboardPresenter sc = new ScoreboardPresenter(timeService);
		new ScoreboardSwingApp(sc).setVisible(true);
		startTimeCheckTriggerOn(sc);
	}

	public ScoreboardSwingApp(ScoreboardPresenter scoreboard) {
		super("Basketball Console");
		this.scoreboard = scoreboard;
		initComponents();
		scoreboard.registerViewer(this);
	}

	private static void startTimeCheckTriggerOn(final ScoreboardPresenter scoreboard) {
		checkTimeTrigger = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scoreboard.triggerTimeCheck();
			}
		});
		checkTimeTrigger.start();
	}

	private void initComponents() {
		setLayout(new BorderLayout(5, 5));
		add(createTimeScorePanel(), BorderLayout.PAGE_START);
		add(createTeamButtonsPanel(), BorderLayout.CENTER);
		add(createScoringButtonsPanel(), BorderLayout.PAGE_END);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(300, 220);
	}

	private Component createScoringButtonsPanel() {
		JPanel scoringButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		score1Button = new JButton("1");
		score1Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				scoreboard.score1Clicked();
			}
		});
		score2Button = new JButton("2");
		score2Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				scoreboard.score2Clicked();
			}
		});
		score3Button = new JButton("3");
		score3Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				scoreboard.score3Clicked();
			}
		});
		scoringButtonsPanel.add(score1Button);
		scoringButtonsPanel.add(score2Button);
		scoringButtonsPanel.add(score3Button);
		return scoringButtonsPanel;
	}

	private Component createTeamButtonsPanel() {
		JPanel teamButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		teamAButton = new JButton("Team A");
		teamAButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				scoreboard.teamAClicked();
			}
		});
		teamBButton = new JButton("Team B");
		teamBButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				scoreboard.teamBClicked();
			}
		});
		teamButtonsPanel.add(teamAButton);
		teamButtonsPanel.add(teamBButton);
		return teamButtonsPanel;
	}

	private JPanel createTimeScorePanel() {
		JPanel timeScorePanel = new JPanel(new BorderLayout(0, 0));
		timeScorePanel.add(createTimePanel(), BorderLayout.PAGE_START);
		timeScorePanel.add(createScorePanel(), BorderLayout.CENTER);
		return timeScorePanel;
	}

	private JPanel createScorePanel() {
		JPanel scorePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		scoreALabel = new JLabel();
		scoreALabel.setFont(SCORE_FONT);
		scoreBLabel = new JLabel();
		scoreBLabel.setFont(SCORE_FONT);
		scorePanel.add(scoreALabel);
		JLabel colon = new JLabel(":");
		colon.setFont(SCORE_FONT);
		scorePanel.add(colon);
		scorePanel.add(scoreBLabel);
		return scorePanel;
	}

	private JPanel createTimePanel() {
		timeLabel = new JLabel("10:00");
		timeLabel.setFont(TIME_FONT);
		JPanel timePanel = new JPanel(new FlowLayout());
		timePanel.add(timeLabel);
		return timePanel;
	}

	@Override
	public void dispose() {
		if (checkTimeTrigger != null) checkTimeTrigger.stop();
		super.dispose();
	}

	;

	@Override
	public void displayScore(Score score) {
		scoreALabel.setText(Integer.toString(score.getA()));
		scoreBLabel.setText(Integer.toString(score.getB()));
	}

	@Override
	public void displayGameTime(final String time) {
		// Invoke in UI thread since trigger might come from a different thread
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				timeLabel.setText(time);
			}
		});
	}

	;

}
