package net.johanneslink.scoreboard.datastore;

import java.io.*;

import net.johanneslink.scoreboard.core.Score;

public class DataStore {


	public Score getScoreFromStorage() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/score.txt"), "UTF-8"));

			String str;
			int teamA = 0;
			int teamB = 0;
			while ((str = br.readLine()) != null) {

				teamA = Integer.parseInt(str.substring(0, 3));
				teamB = Integer.parseInt(str.substring(5, 7));
			}
			br.close();
			return Score.ab(teamA, teamB);
		} catch (FileNotFoundException e) {
			return Score.ab(0, 0);
		} catch (IOException e) {
			return Score.ab(0, 0);
		}
	}

	public void saveScoreToStorage(Score score) {
		try {
			BufferedWriter br = new BufferedWriter(new FileWriter("/score.txt"));
			br.write(score.toString());
			br.close();
		} catch (IOException e) {
			System.out.println("FileNotFoundException: /score.txt (Permission denied)");
		}
	}
}
