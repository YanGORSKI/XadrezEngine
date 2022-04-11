package br.SoLDev.engine.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.collect.ImmutableList;

import br.SoLDev.engine.Alliance;
import br.SoLDev.engine.board.Board;
import br.SoLDev.engine.board.BoardUtils;
import br.SoLDev.engine.board.Move;
import br.SoLDev.engine.board.Move.AttackMove;
import br.SoLDev.engine.board.Move.MajorMove;
import br.SoLDev.engine.pieces.Piece.PieceType;
import br.SoLDev.engine.board.Tile;

public class Knight extends Piece {

	private final static int[] CANDIDATE_MOVE_COORD = { -17, -15, -10, -6, 6, 10, 15, 17};
	
	public Knight(final int piecePosition, final Alliance pieceAlliance) {
		super(piecePosition, pieceAlliance);
	}

	@Override
	public Collection<Move> calculateLegalMoves(final Board board) {

		
		List<Move> legalMoves = new ArrayList<>();
		
		for(final int currentCandidateOffset : CANDIDATE_MOVE_COORD) {
			final int candidateDestinationCoord = this.piecePosition + currentCandidateOffset;
		
			if(BoardUtils.isValidTileCoord(candidateDestinationCoord)) {
				
				if(isFirstColumnExclusion(this.piecePosition, currentCandidateOffset) || 
						isSecondColumnExclusion(this.piecePosition, currentCandidateOffset) ||
						isSeventhColumnExclusion(this.piecePosition, currentCandidateOffset) ||
						isEightColumnExclusion(this.piecePosition, currentCandidateOffset)) {
					continue;
				}
				
				final Tile candidateDestinationTile = board.getTile(candidateDestinationCoord);
			
				if(!candidateDestinationTile.isTileOccupied()) {
					legalMoves.add(new MajorMove(board, this, candidateDestinationCoord));
				
				} else {
					final Piece pieceAtDestination = candidateDestinationTile.getPiece();
					final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
				
					if (this.pieceAlliance != pieceAlliance) {
						legalMoves.add(new AttackMove(board, this, candidateDestinationCoord, pieceAtDestination));
					}
				}
			}
		}
		
		return ImmutableList.copyOf(legalMoves);
	}
	
	@Override
	public String toString() {
		return PieceType.KNIGHT.toString();
	}
	
	private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.FIRST_COLUMN[currentPosition] && ((candidateOffset == -17) || (candidateOffset == -10) ||
				(candidateOffset == 6) || (candidateOffset == 15));
	}

	private static boolean isSecondColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.SECOND_COLUMN[currentPosition] && ((candidateOffset == -10) ||
				(candidateOffset == 6));
	}
	private static boolean isSeventhColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.SEVENTH_COLUMN[currentPosition] && ((candidateOffset == -6) ||
				(candidateOffset == 10));
	}
	
	private static boolean isEightColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.EIGHTH_COLUMN[currentPosition] && ((candidateOffset == -15) || (candidateOffset == -6) ||
				(candidateOffset == 10) || (candidateOffset == 17));
	}
	
	

}
