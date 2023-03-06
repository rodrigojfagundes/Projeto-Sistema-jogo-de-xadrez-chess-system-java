package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece{

	private ChessMatch chessMatch;
	
	public Pawn(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
		// TODO Auto-generated constructor stub
	}
	
	
	//sobrescrita, de PosiblesMoves... Movimentos possiveis de um peao
	@Override
	public boolean[][] possibleMoves() {
		// TODO Auto-generated method stub

		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

		Position p = new Position(0,0);
		
		if(getColor() == Color.WHITE) {
			p.setValue(position.getRow() -1, position.getColumn());
			if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			p.setValue(position.getRow() -2, position.getColumn());
			
			Position p2 = new Position(position.getRow()-1, position.getColumn());

			if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0) {
				mat[p.getRow()][p.getColumn()] = true;
			}

			p.setValue(position.getRow() -1, position.getColumn()-1);

			if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}	

			p.setValue(position.getRow() -1, position.getColumn()+1);

			if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			//specialmove en passant white
			//verificando se tem piece branca na linha 3
			if(position.getRow() == 3) {
				Position left = new Position(position.getRow(), position.getColumn() -1);
				if(getBoard().positionExists(left) && isThereOpponentPiece(left) && getBoard().piece(left) == chessMatch.getEnPassantVulnerable()) {
					//movendo para a posicao da matriz
					mat[left.getRow() -1][left.getColumn()] = true;
				}
				//specialmove en passant white
				//verificando se tem piece adversaria na linha direita
				Position right = new Position(position.getRow(), position.getColumn() + 1);
				if(getBoard().positionExists(right) && isThereOpponentPiece(right) && getBoard().piece(right) == chessMatch.getEnPassantVulnerable()) {
					//movendo para a posicao da matriz
					mat[right.getRow() -1][right.getColumn()] = true;
				}
			}
			
		}

			else {
				//piece preta
				
				p.setValue(position.getRow() +1, position.getColumn());
				if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
					mat[p.getRow()][p.getColumn()] = true;
				}
				
				p.setValue(position.getRow() +2, position.getColumn());
				Position p2 = new Position(position.getRow()+1, position.getColumn());
				if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0) {
					mat[p.getRow()][p.getColumn()] = true;
				}
				
				p.setValue(position.getRow() +1, position.getColumn()-1);
				
				if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
					mat[p.getRow()][p.getColumn()] = true;
				}	
				
				p.setValue(position.getRow() +1, position.getColumn()-1);
				
				if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
					mat[p.getRow()][p.getColumn()] = true;
				}
				
				//specialmove en passant black
				//verificando se tem piece back na linha 4
				if(position.getRow() == 4) {
					Position left = new Position(position.getRow(), position.getColumn() -1);
					if(getBoard().positionExists(left) && isThereOpponentPiece(left) && getBoard().piece(left) == chessMatch.getEnPassantVulnerable()) {
						//movendo para a posicao da matriz
						mat[left.getRow() +1][left.getColumn()] = true;
					}
					//specialmove en passant white
					//verificando se tem piece adversaria na linha direita
					Position right = new Position(position.getRow(), position.getColumn() + 1);
					if(getBoard().positionExists(right) && isThereOpponentPiece(right) && getBoard().piece(right) == chessMatch.getEnPassantVulnerable()) {
						mat[right.getRow() +1][right.getColumn()] = true;
					}
				}
				
				
			}

		return mat;
	}
	
	//tostring
	@Override
	public String toString() {
		return "P";
	}
	
	
}
