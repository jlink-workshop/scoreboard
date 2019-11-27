/*
 * Copyright © 2018 Daimler TSS. All Rights Reserved.
 *
 * Reproduction or transmission in whole or in part, in any form or by any
 * means, is prohibited without the prior written consent of the copyright
 * owner.
 *
 * Created on: Nov 27, 2019
 * Created by: EMINANC
 */
package net.johanneslink.scoreboard.core;

public interface ScoreSaver {

	ScoreSaver NULL = new ScoreSaver() {
		@Override
		public void save(Score score) {

		}

		@Override
		public Score load() {
			throw new RuntimeException("Not implemented");
		}
	};

	void save(Score score);

	Score load();
}
