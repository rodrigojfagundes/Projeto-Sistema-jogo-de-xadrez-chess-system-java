package boardgame;

public class Piece {

	//posicao da PEÇA na MATRIZ do jogo...
	protected Position position;
	private Board board;
	
	public Piece(Board board) {
		this.board = board;
		position = null;
	}

	protected Board getBoard() {
		return board;
	}	
}
