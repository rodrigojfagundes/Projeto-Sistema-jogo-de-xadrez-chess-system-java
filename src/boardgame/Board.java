package boardgame;

public class Board {

	private int rows;
	private int columns;
	private Piece[][] pieces;
	
	
	public Board(int rows, int columns) {
	
		if(rows < 1 || columns < 1 ) {
			throw new BoardException("error creating board: there must be at least 1 row and 1 column");
		}
		this.rows = rows;
		this.columns = columns;
	
		pieces = new Piece[rows][columns];
	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}
	
	public Piece piece(int row, int column) {
		if (!positionExists(row, column)) {
			throw new BoardException("position not on the board");
		}
		return pieces[row][column];
	}
	public Piece piece(Position position) {
	
		if (!positionExists(position)) {
			throw new BoardException("position not on the board");
		}

		return pieces[position.getRow()][position.getColumn()];
	}

	public void placePiece(Piece piece, Position position) {

		if (thereIsAPiece(position)) {
			throw new BoardException("there is already a piece on position " + position);
		}
		
		pieces[position.getRow()][position.getColumn()] = piece;
		piece.position = position;
	}
	public Piece removePiece(Position position) {
		if (!positionExists(position)) {
			throw new BoardException("position not on the board");
		}
		if (piece(position) == null){
			return null;
		}
		Piece aux = piece(position);
		aux.position = null;
		pieces[position.getRow()][position.getColumn()] = null;
		return aux;
	}
	
	
	//criando um metodo auxiiar PRIVADO q recebe uma linha e coluna
	//e verifica se essa LINHA e COLUNA existe dentro do tabuleiro...
	private boolean positionExists(int row, int column) {
		return row >= 0 && row < rows && column >= 0 && column < columns;
	}
	//se o metodo/funcao ali de cima for TRUE o PositionExists... Entao o positionExists aqui de baixo
	//vai retornar TRUE
	public boolean positionExists(Position position) {
	return positionExists(position.getRow(), position.getColumn());
	}
	
	//criando metodo thereIsAPiece... Q serve para testar se tem PECA na posicao
	public boolean thereIsAPiece(Position position) {
		//testar se a posicao NAO EXISTE...
		if (!positionExists(position)) {
	    //se a position q foi testada no IF acima nao existir... dai vai exibir a mensagem de erro
		//a baixo
		throw new BoardException("position not on the board");
		}
		//CHAMANDO o metodo/funcao PIECE q esta NESSA CLASSE BOARD, e passando o valor de POSITION
		//para esse metodo... e verificando se o o resultado e diferente de nulo
		return piece(position) != null;
	}
	
}
