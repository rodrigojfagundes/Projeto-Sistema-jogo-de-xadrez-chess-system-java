package boardgame;

public abstract class Piece {

	protected Position position;
	private Board board;
	
	public Piece(Board board) {
		this.board = board;
		position = null;
	}

	protected Board getBoard() {
		return board;
	}

	public abstract boolean[][] possibleMoves();
	public boolean possibleMove(Position position) {

		return possibleMoves()[position.getRow()][position.getColumn()];
	}

	public boolean isThereAnyPossibleMove() {
		//chamando o metodo abstrato POSSIBLEMOVES q vai retornar uma matriz em booleano
		//e varre a matriz para verificar SE existe UMA possicao da MATRIZ q é VERDADEIRA...
		//SE NAO TIVER... Entao a peca nao pode se MOVER
		boolean[][] mat = possibleMoves();
		for(int i = 0; i<mat.length; i++) {
			for (int j=0; j<mat.length; j++) {
				if(mat[i][j]) {
					return true;
				}
			}
		}
		return false;
	}
}
