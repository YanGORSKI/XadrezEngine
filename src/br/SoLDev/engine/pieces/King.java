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

public class King extends Piece {
	
	private final static int[] CANDIDATE_MOVE_COORD = {-9, -8, -7, -1, 1, 7, 8, 9};

	public King(int piecePosition, final Alliance pieceAlliance) {
		super(piecePosition, pieceAlliance);
	}

	@Override
	public Collection<Move> calculateLegalMoves(Board board) {
		
		final List<Move> legalMoves = new ArrayList<>();
		
		for(final int currentCandidateOffset : CANDIDATE_MOVE_COORD ) {
			final int candidateDestinationCoord = this.piecePosition + currentCandidateOffset;
			
			if(isFirstColumnExclusion(this.piecePosition, currentCandidateOffset) ||
			isEighthColumnExclusion(this.piecePosition, currentCandidateOffset)) {
				continue;
			}
			
			if(BoardUtils.isValidTileCoord(candidateDestinationCoord)) {
				final Tile candidateDestinationTile = board.getTile(candidateDestinationCoord);
				if(!candidateDestinationTile.isTileOccupied()) {
					legalMoves.add(new MajorMove(board, this, candidateDestinationCoord));
				} else {
					final Piece pieceAtDestination = candidateDestinationTile.getPiece();
					final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
					if(this.pieceAlliance != pieceAlliance) {
						legalMoves.add(new AttackMove(board, this, candidateDestinationCoord, pieceAtDestination));
					}
				}
			}
			
			
		}
		
		
		
		return ImmutableList.copyOf(legalMoves);
	}
	
	@Override
	public String toString() {
		return PieceType.KING.toString();
	}
	
	private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.FIRST_COLUMN[currentPosition] && ((candidateOffset == -9) || (candidateOffset == -1) ||
				(candidateOffset == 7));
	}

	private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.EIGHTH_COLUMN[currentPosition] && ((candidateOffset == -7) || (candidateOffset == 1) ||
				(candidateOffset == 9));
	}

}
