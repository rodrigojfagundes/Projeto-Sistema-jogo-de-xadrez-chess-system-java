package chess;

import boardgame.Board;
import boardgame.Piece;

//classe das PECAS de XADREZ... ela HERDA/Extends a classe PIECE
public class ChessPiece extends Piece{
	private Color color;

	public ChessPiece(Board board, Color color) {
		super(board);
		this.color = color;
	}

	public Color getColor() {
		return color;
	}	
}
