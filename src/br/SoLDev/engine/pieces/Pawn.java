package br.SoLDev.engine.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.SoLDev.engine.Alliance;
import br.SoLDev.engine.board.Board;
import br.SoLDev.engine.board.BoardUtils;
import br.SoLDev.engine.board.Move;
import br.SoLDev.engine.board.Move.MajorMove;
import br.SoLDev.engine.pieces.Piece.PieceType;

public class Pawn extends Piece{

	private final static int[] CANDIDATE_MOVE_COORD = {8, 16, 7, 9};

	public Pawn(final int piecePosition, final Alliance pieceAlliance) {
		super(piecePosition, pieceAlliance);
	}

	@Override
	public Collection<Move> calculateLegalMoves(final Board board) {

		final List<Move> legalMoves = new ArrayList<>();

		for(final int currentCandidateOffset : CANDIDATE_MOVE_COORD) {

			final int candidateDestinationCoord = this.piecePosition + (this.pieceAlliance.getDirection() * currentCandidateOffset);

			if(BoardUtils.isValidTileCoord(candidateDestinationCoord)) {
				continue;
			}

			if(currentCandidateOffset == 8 && !board.getTile(candidateDestinationCoord).isTileOccupied()) {
				//TODO more work
				legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoord));
			} else if(currentCandidateOffset == 16 && this.isFirstMove() &&
					(BoardUtils.SECOND_ROW[this.piecePosition] && this.getPieceAlliance().isBlack()) ||
					(BoardUtils.SEVENTH_ROW[this.piecePosition] && this.getPieceAlliance().isWhite())) {
				final int behindCandidateDestinationCoord = this.piecePosition + (this.pieceAlliance.getDirection() * 8);
				if (!board.getTile(behindCandidateDestinationCoord).isTileOccupied() &&
						!board.getTile(candidateDestinationCoord).isTileOccupied()) {
					legalMoves.add(new MajorMove(board, this, candidateDestinationCoord));
				}
			} else if(currentCandidateOffset == 7 &&
					!((BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite() ||
							(BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack())) ))  {
				if(board.getTile(candidateDestinationCoord).isTileOccupied()) {
					final Piece pieceOnCandidate = board.getTile(candidateDestinationCoord).getPiece();
					if(this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
						//TODO attack move
						legalMoves.add(new MajorMove(board, this, candidateDestinationCoord));
					}
				}

			} else if(currentCandidateOffset == 9 &&
					!((BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite() ||
							(BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack())) ))  {
				if(board.getTile(candidateDestinationCoord).isTileOccupied()) {
					final Piece pieceOnCandidate = board.getTile(candidateDestinationCoord).getPiece();
					if(this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
						//TODO attack move
						legalMoves.add(new MajorMove(board, this, candidateDestinationCoord));
					}

				}



			}

		}
		
		return legalMoves;
		
	}
	
	@Override
	public String toString() {
		return PieceType.PAWN.toString();
	}
}