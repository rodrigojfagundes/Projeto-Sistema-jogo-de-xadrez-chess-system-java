package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece{

	private ChessMatch chessMatch;
	
	public King(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		return "K";
	}
	

	private boolean canMove(Position position) {
		ChessPiece p = (ChessPiece)getBoard().piece(position);
		return p == null || p.getColor() != getColor();
	}
	
	private boolean testRookCastling(Position position) {
		ChessPiece p = (ChessPiece)getBoard().piece(position);
		return p != null && p instanceof Rook && p.getColor() == getColor() && p.getMoveCount() == 0;	
	}


	@Override
	public boolean[][] possibleMoves() {
		// TODO Auto-generated method stub
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		Position p = new Position(0,0);
		
		//testando SE o REI pd se mover para cima
		p.setValue(position.getRow() -1, position.getColumn());
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		
				//testando SE o REI pd se mover para baixo
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
				
				//testando SE o REI pd se mover para SUDOESTE
				p.setValue(position.getRow() +1 , position.getColumn() -1);
				if (getBoard().positionExists(p) && canMove(p)) {
					mat[p.getRow()][p.getColumn()] = true;
				}
				
				p.setValue(position.getRow() +1 , position.getColumn() +1);
				if (getBoard().positionExists(p) && canMove(p)) {
					mat[p.getRow()][p.getColumn()] = true;
				}
				
				//#SpecialMove Castling (verificando se o REI pd se mover para DIREITA e ESQUERDA)

				if(getMoveCount() == 0 && !chessMatch.getCheck()) {
					//testar se as 2 posicoes DIREITA e ESQUERDA tao vaga e se a TORRE ainda nao se moveu
					
					//#specialmove castling kingside rook (rook do lado do rei) rook pequeno
					
					//posicao da torre do rei é  alinha e coluna do REI + 3 casas
					Position posT1 = new Position(position.getRow(), position.getColumn() + 3);
					if (testRookCastling(posT1)) {
						//vamos testar as 2 casas entre o rei e a torre se elas tao vazia
						//1 e 2 casas
						Position p1 = new Position(position.getRow(), position.getColumn() + 1);
						Position p2 = new Position(position.getRow(), position.getColumn() + 2);
						
						if (getBoard().piece(p1) == null && getBoard().piece(p2) == null) {
							mat[position.getRow()][position.getColumn() + 2] = true;
						}
					}
					
					//fazendo o rook grande
					//#specialmove castling queenside rook (rook do lado do rei) rook pequeno
					
					Position posT2 = new Position(position.getRow(), position.getColumn() - 4);
					
					if (testRookCastling(posT2)) {
						Position p1 = new Position(position.getRow(), position.getColumn() - 1);
						Position p2 = new Position(position.getRow(), position.getColumn() - 2);
						Position p3 = new Position(position.getRow(), position.getColumn() - 3);
						if (getBoard().piece(p1) == null && getBoard().piece(p2) == null && getBoard().piece(p3) == null) {

							mat[position.getRow()][position.getColumn() -2] = true;
						}
					}
					
					
				}
		
		return mat;
	}
	

}
