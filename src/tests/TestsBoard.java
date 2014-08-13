package tests;

import static org.junit.Assert.*;

import java.io.IOException;

import game.Board;

import org.junit.Test;

public class TestsBoard {

	/**
	 * Roll 1000 pairs of dice. Check individual dice give a number in the range
	 * [1,6].

	@Test
	public void diceRoll_001() {
		try {
			Board board = new Board(null);
			for (int i = 0; i < 1000; i++) {
				int[] roll = board.rollDice();
				assertTrue(roll.length == 2);
				assertTrue(roll[0] >= 1 && roll[1] >= 1);
				assertTrue(roll[0] <= 6 && roll[1] <= 6);
				int moves = roll[0] + roll[1];
				assertTrue(moves >= 2 && moves <= 12);
			}
		} catch (IOException e) {
			fail("no board image");
		}

	}
	*/

}
