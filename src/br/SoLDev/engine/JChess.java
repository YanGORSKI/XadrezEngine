package br.SoLDev.engine;

import br.SoLDev.engine.board.Board;

public class JChess {

	public static void main(String[] args) {
		
		Board board = Board.createStandardBoard();
		System.out.println(board);

	}

}
