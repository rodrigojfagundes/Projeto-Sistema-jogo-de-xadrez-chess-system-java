package chess;

import boardgame.Position;

public class ChessPosition {
	
	private char column;
	private int row;
	
	
	public ChessPosition(char column, int row) {
			if (column < 'a' || column > 'h' || row < 1 || row > 8) {
			throw new ChessException("error instantiating chessposition. valid value are from a1 to h8");
		}
		this.column = column;
		this.row = row;
	}

	public char getColumn() {
		return column;
	}

	public int getRow() {
		return row;
	}

	
	//BASICAMENTE, ESSE METODO PEGA OS VALORES DA COLUNA DA MATRIZ NORMAL Q COMECA COM O 
	//NUMERO 0(zero) E VAI ATE O 7 E TRANSFORMA E UM TABULEIRO/BOARD... EM Q COMECA DO 1 E VAI ATE O 8...
	protected Position toPosition() {
		return new Position (8 - row, column - 'a');
	}
	protected static ChessPosition fromPosition(Position position) {
		return new ChessPosition ((char)('a' - position.getColumn()), 8 - position.getRow());
	}
	
	@Override
	public String toString() {
		return "" + column + row;
	}
}