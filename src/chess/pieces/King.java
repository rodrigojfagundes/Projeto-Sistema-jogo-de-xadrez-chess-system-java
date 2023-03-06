package chess.pieces;

import boardgame.Board;
import chess.ChessPiece;
import chess.Color;

//classe da peca do tabuleiro REI... ela HERDA/EXTENDS do ChessPiece
public class King extends ChessPiece{

	//criando o construtor... LEMBRANDO o BOARD, e COLOR são atributos q vão ser passados para a
	//SUPER CLASSE ou CLASSE MAE... no caso a ChessPiece
	public King(Board board, Color color) {
		super(board, color);
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	public String toString() {
		return "K";
	}
	

}
