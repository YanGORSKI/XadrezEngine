package br.SoLDev.engine.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.collect.ImmutableList;

import br.SoLDev.engine.Alliance;
import br.SoLDev.engine.board.Board;
import br.SoLDev.engine.board.BoardUtils;
import br.SoLDev.engine.board.Move;
import br.SoLDev.engine.board.Tile;
import br.SoLDev.engine.board.Move.AttackMove;
import br.SoLDev.engine.board.Move.MajorMove;
import br.SoLDev.engine.pieces.Piece.PieceType;

public class Rook extends Piece {
	
	private final static int[] CANDIDATE_MOVE_VECTOR_COORD = {-8, -1, 1, 8};

	public Rook(final int piecePosition, final Alliance pieceAlliance) {
		super(piecePosition, pieceAlliance);
	}

	@Override
	public Collection<Move> calculateLegalMoves(final Board board) {
		
		final List<Move> legalMoves =  new ArrayList<>();
		
		for(final int candidateCoordOffset: CANDIDATE_MOVE_VECTOR_COORD) {
			int candidateDestinationCoord = this.piecePosition;
			while(BoardUtils.isValidTileCoord(candidateDestinationCoord)) {
				if (isFirstColumnExclusion(candidateDestinationCoord, candidateCoordOffset) ||
						isEighthColumnExclusion(candidateDestinationCoord, candidateCoordOffset)) {
					break;
				}
				candidateDestinationCoord += candidateCoordOffset;
				if(BoardUtils.isValidTileCoord(candidateDestinationCoord)) {
					final Tile candidateDestinationTile = board.getTile(candidateDestinationCoord);
					if(!candidateDestinationTile.isTileOccupied()) {
						legalMoves.add(new MajorMove(board, this, candidateDestinationCoord));
					} else {
						final Piece pieceAtDestination = candidateDestinationTile.getPiece();
						final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
					
						if (this.pieceAlliance != pieceAlliance) {
							legalMoves.add(new AttackMove(board, this, candidateDestinationCoord, pieceAtDestination));
						}
						break;
					}
				}
			}
		}
		
		return ImmutableList.copyOf(legalMoves);
	}
	
	@Override
	public String toString() {
		return PieceType.ROOK.toString();
	}
	
	private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -1);
	}

	private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == 1);
	}

}
