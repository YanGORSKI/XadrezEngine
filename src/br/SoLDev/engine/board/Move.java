package br.SoLDev.engine.board;

import br.SoLDev.engine.pieces.Piece;

public abstract class Move {
	
	 final Board board;
	 final Piece movedPiece;
	 final int destinationCoord;
	 
	 private Move(final Board board,
			 final Piece movedPiece,
			 final int destinationCoord) {
		 this.board = board;
		 this.movedPiece = movedPiece;
		 this.destinationCoord = destinationCoord;
	 }
	 
	 public static final class MajorMove extends Move {

		public MajorMove(final Board board,
				final Piece movedPiece,
				int destinationCoord) {
			super(board, movedPiece, destinationCoord);
		}
		 
	 }
	 
	 public static final class AttackMove extends Move {

		final Piece attackedPiece;
		 
		 public AttackMove(final Board board,
				 	final Piece movedPiece,
				 	int destinationCoord,
				 	final Piece attackedPiece) {
			super(board, movedPiece, destinationCoord);
			this.attackedPiece = attackedPiece;
		}
		 
	 }

}
