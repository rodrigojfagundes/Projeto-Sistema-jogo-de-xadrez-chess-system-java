package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Rook extends ChessPiece{

	
	public Rook(Board board, Color color) {
		super(board, color);
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	public String toString() {
		return "R"; 
	}
	
	@Override
	public boolean[][] possibleMoves() {
		// TODO Auto-generated method stub
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		Position p = new Position(0,0);
		
		//veerificando as posicoes ACIMA
		p.setValue(position.getRow() -1 , position.getColumn());
		
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
			p.setRow(p.getRow() -1);
		}
		if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		
				//veerificando as pecas nas posicoes a esquerda
				//left
				p.setValue(position.getRow() , position.getColumn() -1);
				while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
					mat[p.getRow()][p.getColumn()] = true;
					p.setColumn(p.getColumn() -1);
				}

				if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
					mat[p.getRow()][p.getColumn()] = true;
				}
		
				
				p.setValue(position.getRow() , position.getColumn() +1);
				while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
					mat[p.getRow()][p.getColumn()] = true;
					p.setColumn(p.getColumn() +1);
				}
				if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
					mat[p.getRow()][p.getColumn()] = true;
				}
				
				p.setValue(position.getRow() +1 , position.getColumn());
				while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
					mat[p.getRow()][p.getColumn()] = true;
					p.setRow(p.getRow() +1);
				}
				if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
					mat[p.getRow()][p.getColumn()] = true;
				}
		return mat;
	}
}
