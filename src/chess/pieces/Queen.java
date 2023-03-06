package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Queen extends ChessPiece{


	public Queen(Board board, Color color) {
		super(board, color);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		return "Q"; 
	}
	
	@Override
	public boolean[][] possibleMoves() {
		// TODO Auto-generated method stub
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		Position p = new Position(0,0);

		
		
		p.setValue(position.getRow() -1 , position.getColumn());
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;	
			p.setRow(p.getRow() -1);
		}
		//quando a repeticao acima terminar, vamos ver SE ainda existem CASAS(lugar para por PECA)
		//e TESTAR se nessa casa EXISTE uma peca adversaria, se SIM vamos marcar essa casa como verdadeiro
		if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
			
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
				
			 
				p.setValue(position.getRow() -1 , position.getColumn()-1);
				while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
					mat[p.getRow()][p.getColumn()] = true;
					p.setValue(p.getRow() -1, p.getColumn() -1);
				}

				if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
					mat[p.getRow()][p.getColumn()] = true;
				}
						p.setValue(position.getRow() -1, position.getColumn() +1);
						while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
							mat[p.getRow()][p.getColumn()] = true;
							p.setValue(p.getRow() -1, p.getColumn() + 1);
						}
						if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
							mat[p.getRow()][p.getColumn()] = true;
						}
				
						p.setValue(position.getRow() +1 , position.getColumn() +1);
						
						while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
							
							mat[p.getRow()][p.getColumn()] = true;

							p.setValue(p.getRow() +1, p.getColumn() +1);
						}
						if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
							mat[p.getRow()][p.getColumn()] = true;
						}
				
						p.setValue(position.getRow() +1 , position.getColumn() -1);
						
						while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
							
							mat[p.getRow()][p.getColumn()] = true;
							
							p.setValue(p.getRow() + 1, p.getColumn() -1);
						}
				
						if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
							mat[p.getRow()][p.getColumn()] = true;
						}

		return mat;
	}
}
