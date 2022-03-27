package br.SoLDev.engine.board;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

import br.SoLDev.engine.pieces.Piece;

public abstract class Tile {
	
	protected final int tileCoord;
	
	protected static final Map<Integer, EmptyTile> EMPTY_TILES_CACHE = createAllPossibleEmptyTiles();
	
	private static Map<Integer, EmptyTile> createAllPossibleEmptyTiles(){
		final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();
		for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
			emptyTileMap.put(i, new EmptyTile(i));
		}
		
		return ImmutableMap.copyOf(emptyTileMap);
	}
	
	public static Tile createTile(final int tileCoord, final Piece piece) {
		return piece != null ? new OccupiedTile(tileCoord, piece) : EMPTY_TILES_CACHE.get(tileCoord);
	}

	//builder
	private Tile(final int tileCoord) {
		this.tileCoord = tileCoord;
	}
	

	//métodos
	public abstract boolean isTileOccupied();
	
	public abstract Piece getPiece();
	
	//subclasse de Tile Vazio
	public static final class EmptyTile extends Tile {
		private EmptyTile(final int coord) {
			super(coord);
		}
		
		@Override
		public boolean isTileOccupied() {
			return false;
		}
		
		@Override
		public Piece getPiece() {
			return null;
		}
	}
	
	
	//subclasse de Tile com Peça
	public static final class OccupiedTile extends Tile {
		private final Piece pieceOnTile;
	
		private OccupiedTile(final int tileCoord, final Piece pieceOnTile) {
			super(tileCoord);
			this.pieceOnTile = pieceOnTile;
		}
		
		@Override
		public boolean isTileOccupied() {
			return true;
		}
		
		@Override
		public Piece getPiece() {
			return this.pieceOnTile;
		}
	
	}

}
