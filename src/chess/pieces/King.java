package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece{

	public King(Board board, Color color) {
		super(board, color);
		// TODO Auto-generated constructor stub
	}
	

	@Override
	public String toString() {
		//o K vai aparecer no tabuleiro... K pq REI é KING em INGLES
		return "K";
	}
	
	private boolean canMove(Position position) {
		ChessPiece p = (ChessPiece)getBoard().piece(position);
		return p == null || p.getColor() != getColor();
	}


	@Override
	public boolean[][] possibleMoves() {
		// TODO Auto-generated method stub
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		Position p = new Position(0,0);
		
		p.setValue(position.getRow() -1, position.getColumn());
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
				p.setValue(position.getRow() +1, position.getColumn());
				if (getBoard().positionExists(p) && canMove(p)) {
					mat[p.getRow()][p.getColumn()] = true;
				}		
		
				p.setValue(position.getRow(), position.getColumn() -1);
		
				if (getBoard().positionExists(p) && canMove(p)) {
					mat[p.getRow()][p.getColumn()] = true;
				}
		
				p.setValue(position.getRow(), position.getColumn() +1);
				if (getBoard().positionExists(p) && canMove(p)) {
					mat[p.getRow()][p.getColumn()] = true;
				}
				
				p.setValue(position.getRow() -1 , position.getColumn() -1);
				
				if (getBoard().positionExists(p) && canMove(p)) {
					mat[p.getRow()][p.getColumn()] = true;
				}
				
				p.setValue(position.getRow() -1 , position.getColumn() +1);
				if (getBoard().positionExists(p) && canMove(p)) {
					mat[p.getRow()][p.getColumn()] = true;
				}
				
				p.setValue(position.getRow() +1 , position.getColumn() -1);
				
				if (getBoard().positionExists(p) && canMove(p)) {
					mat[p.getRow()][p.getColumn()] = true;
				}
				p.setValue(position.getRow() +1 , position.getColumn() +1);
				if (getBoard().positionExists(p) && canMove(p)) {
					mat[p.getRow()][p.getColumn()] = true;
				}
				
				
		
		return mat;
	}
	

}
