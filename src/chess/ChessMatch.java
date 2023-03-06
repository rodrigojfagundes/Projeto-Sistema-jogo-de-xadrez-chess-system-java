package chess;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Rook;

public class ChessMatch {
	
	private int turn;
	private Color currentPlayer;
	private Board board;
	private boolean check;
	private boolean checkMate;
	private ChessPiece enPassantVulnerable;
	private ChessPiece promoted;
	
	private List<Piece> piecesOnTheBoard = new ArrayList<>();
	private List<Piece> capturedPieces = new ArrayList<>();
	
	public ChessMatch() {
		board = new Board(8,8);
		turn = 1;
		currentPlayer = Color.WHITE;
		initialSetup();
	}
	
	public int getTurn() {
		return turn;
	}
	
	public Color getCurrentPlayer(){
		return currentPlayer;
	}
	
	public boolean getCheck() {
		return check;
	}
	
	public boolean getCheckMate() {
		return check;
	}
	
	public ChessPiece getEnPassantVulnerable() {
		return enPassantVulnerable;
	}
	
	public ChessPiece getPromoted() {
		return promoted;
	}
	
	//
	//criando o metodo ChessPiece q vai retornar uma MATRIZ de pecas de XADREZ correspondente
	//aos valores q foram passados no BOARD ali de cima
	public ChessPiece[][] getPieces (){
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
		for (int i=0; i<board.getRows(); i++) {
			for (int j=0; j<board.getRows(); j++) {
				mat[i][j] = (ChessPiece) board.piece(i, j);
			}
		}
		return mat;
	}
	
	//metodo como indicador de para QUAIS lado a piece pode se mover
	//imprimir as possicoes possiveis a partir de uma posicao de origem
	public boolean[][] possibleMoves(ChessPosition sourcePosition){
		Position position = sourcePosition.toPosition();
		validateSourcePosition(position);
		return board.piece(position).possibleMoves();
	}
	
	
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		validateSourcePosition(source);
		validateTargetPosition(source, target);
		Piece capturedPiece = makeMove(source, target);
		
		if (testCheck(currentPlayer)) {
			undoMove(source, target, capturedPiece);
			throw new ChessException("you can't put yourself in check");
		}
		
		ChessPiece movedPiece = (ChessPiece)board.piece(target);
		
		
		// #specialmove PROMOTION... ocorre quando o PEAO atravessa todo o tabuleiro e chega no outro canto
		//do tabuleiro
		
		promoted = null;
		if(movedPiece instanceof Pawn) {
			if((movedPiece.getColor() == Color.WHITE && target.getRow() == 0) || (movedPiece.getColor() == Color.BLACK && target.getRow() == 7)) {
				promoted = (ChessPiece)board.piece(target);
				promoted = replacePromotedPiece("Q");
				
			}
		}
		
		check = (testCheck(opponent(currentPlayer))) ? true : false;
		if(testCheckMate(opponent(currentPlayer))) {
			checkMate = true;
		}
		else {
		nextTurn();
		}
		//testar se a peca movida foi um peao q moveu 2 casas... se FOR
		//ele e possivel de ENPASSANT
		//#specialmove enpassant
		if(movedPiece instanceof Pawn && (target.getRow() == source.getRow() - 2 || target.getRow() == source.getRow() + 2)) {
			enPassantVulnerable = movedPiece;
		} else {
			enPassantVulnerable = null;
		}

		return (ChessPiece) capturedPiece;
	}
	
	public ChessPiece replacePromotedPiece(String type) {
		if(promoted == null) {
			throw new IllegalStateException("there is no piece to be promoted");
		}
		if (!type.equals("B") && !type.equals("N") && !type.equals("R") & !type.equals("Q")) {
			throw new InvalidParameterException("invalid type for promotion");
		}
		Position pos = promoted.getChessPosition().toPosition();
		Piece p = board.removePiece(pos);
		piecesOnTheBoard.remove(p);
		
		ChessPiece newPiece = newPiece(type, promoted.getColor());
		board.placePiece(newPiece, pos);
		//vamos add a nova peca q foi escolhida no tabuleiro
		piecesOnTheBoard.add(newPiece);
		return newPiece;	
	}
	
	private ChessPiece newPiece(String type, Color color) {
		if (type.equals("B")) return new Bishop(board, color);
		if (type.equals("N")) return new Knight(board, color);
		if (type.equals("Q")) return new Queen(board, color);
		return new Bishop(board, color);
	}
	
	//criando o metodo MakeMove... Recebendo uma posicao LINHA e COLUNA de Origem e de Destino
	private Piece makeMove(Position source, Position target) {
		ChessPiece p = (ChessPiece) board.removePiece(source);
		p.increaseMoveCount();
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(p, target);
		if(capturedPiece != null) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}
		//metodo/funcao para testar se o jogador moveu 2 casas para direita é um rook pequeno
		//se o jogador moveu o rei 2 casas para a esquerda e um rook grande, entao tbm tem q mover a torre
		
		// #specialmove castrling kingside rook
		if (p instanceof King && target.getColumn() == source.getColumn() + 2) {
			Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
			Position targetT = new Position(source.getRow(), source.getColumn() +1);
			ChessPiece rook = (ChessPiece)board.removePiece(sourceT);
			board.placePiece(rook,  targetT);
			rook.increaseMoveCount();
		}
		
		// #specialmove castrling queenside rook
		//rook grande
		//se a piece movida for um rei e ele moveu 2 casas paa a ESQUERDA
		if (p instanceof King && target.getColumn() == source.getColumn() - 2) {
			Position sourceT = new Position(source.getRow(), source.getColumn() -4);
			Position targetT = new Position(source.getRow(), source.getColumn() -1 );
			ChessPiece rook = (ChessPiece)board.removePiece(sourceT);
			board.placePiece(rook,  targetT);
			rook.increaseMoveCount();
		}
		
		// # especialMove EN PASSANT
		//vamos testar se a peca q foi movida e uma INSTANCIA de PEAO PAWN
		if (p instanceof Pawn) {
			if(source.getColumn() != target.getColumn() && capturedPiece == null) {
				Position pawnPosition;
				if(p.getColor() == Color.WHITE) {
					pawnPosition = new Position(target.getRow() + 1, target.getColumn());
				} else {
					pawnPosition = new Position(target.getRow() - 1, target.getColumn());
				}
				capturedPiece = board.removePiece(pawnPosition);
				capturedPieces.add(capturedPiece);
				piecesOnTheBoard.remove(capturedPiece);
			}
		}
		
		
		
		
		return capturedPiece;
	}
	private void undoMove (Position source, Position target, Piece capturedPiece) {
		ChessPiece p = (ChessPiece)board.removePiece(target);
		p.decreaseMoveCount();
		board.placePiece(p, source);
	
		if (capturedPiece != null) {
			board.placePiece(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add(capturedPiece);
		}
		
		// DESFAZENDO O MOVIMENTO DE ROOK 
		//
		// #specialmove castrling kingside rook
		if (p instanceof King && target.getColumn() == source.getColumn() + 2) {
			Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
			Position targetT = new Position(source.getRow(), source.getColumn() +1);
			ChessPiece rook = (ChessPiece)board.removePiece(targetT);
			board.placePiece(rook,  sourceT);
			rook.decreaseMoveCount();
		}
		
		// #specialmove castrling queenside rook
		//rook grande
		//se a piece movida for um rei e ele moveu 2 casas paa a ESQUERDA
		if (p instanceof King && target.getColumn() == source.getColumn() - 2) {
			
			Position sourceT = new Position(source.getRow(), source.getColumn() -4);
			
			Position targetT = new Position(source.getRow(), source.getColumn() -1 );
			ChessPiece rook = (ChessPiece)board.removePiece(targetT);
			board.placePiece(rook,  sourceT);
			rook.decreaseMoveCount();
		}
		
		
		//
		//	DESFAZENDO O EN PASSANT
		//
		// # especialMove EN PASSANT
		if (p instanceof Pawn) {

			if(source.getColumn() != target.getColumn() && capturedPiece == enPassantVulnerable) {
				
				ChessPiece pawn = (ChessPiece)board.removePiece(target);
				Position pawnPosition;
				if(p.getColor() == Color.WHITE) {
					pawnPosition = new Position(3, target.getColumn());
				} else {
					pawnPosition = new Position(4, target.getColumn());
				}				
				board.placePiece(pawn, pawnPosition);
			}
		}
		
		
		
	}
	private void validateSourcePosition(Position position) {
		if (!board.thereIsAPiece(position)) {
			throw new ChessException("there is no piece on source position");
		}
		if(currentPlayer != ((ChessPiece)board.piece(position)).getColor()) {
			throw new ChessException("the chosen piece is not yours");
		}
		
		if(!board.piece(position).isThereAnyPossibleMove()) {
			throw new ChessException("there is no possible moves for the chosen piece");
			
		}
	}
	
	private void validateTargetPosition(Position source, Position target) {
		if(!board.piece(source).possibleMove(target)) {
			throw new ChessException("the chosen piece can't move to target position");
		}
	}
	
	private void nextTurn() {
		turn++;
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	
	private Color opponent(Color color) {
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}

	private ChessPiece king(Color color) {
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());

		for (Piece p : list) {
			if (p instanceof King) {
				return (ChessPiece)p;
			}
		}
		throw new IllegalStateException("There is no " + color + "king on the board");
	}
	
	private boolean testCheck(Color color) {
		Position kingPosition = king(color).getChessPosition().toPosition();
		List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == opponent(color)).collect(Collectors.toList());
		for (Piece p : opponentPieces) {
			boolean[][] mat = p.possibleMoves();
			if(mat[kingPosition.getRow()][kingPosition.getColumn()]) {
				return true;
			}
		}
	return false;
	}
	private boolean testCheckMate(Color color) {
		if(!testCheck(color)) {
			return false;
		}
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
		for (Piece p : list) {
			//pegando os movimentos possiveis
			boolean[][] mat = p.possibleMoves();
			//pegando as linhas da matriz
			for(int i=0; i<board.getRows(); i++) {
				for (int j=0; j<board.getColumns(); j++) {
					if(mat[i][j]) {
						Position source = ((ChessPiece)p).getChessPosition().toPosition();
						Position target = new Position(i, j);
						Piece capturedPiece = makeMove(source, target);
						boolean testCheck = testCheck(color);
						undoMove(source, target, capturedPiece);
						if(!testCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	
	//neste metodo nos vamos passar as coordenadas do xadrez
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
		piecesOnTheBoard.add(piece);
	}
	//criando um metodo que e responsavel por iniciar a partida de xadrez... Colocando as pecas
	//no tabuleiro
	private void initialSetup() {
		//instanciando as pecas...
		
		 	placeNewPiece('a', 1, new Rook(board, Color.WHITE));
		 	placeNewPiece('b', 1, new Knight(board, Color.WHITE));
		 	placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
		 	placeNewPiece('d', 1, new Queen(board, Color.WHITE));
	        placeNewPiece('e', 1, new King(board, Color.WHITE, this));
	        placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
	        placeNewPiece('g', 1, new Knight(board, Color.WHITE));
	        placeNewPiece('h', 1, new Rook(board, Color.WHITE));
	        placeNewPiece('a', 2, new Pawn(board, Color.WHITE, this));
	        placeNewPiece('b', 2, new Pawn(board, Color.WHITE, this));
	        placeNewPiece('c', 2, new Pawn(board, Color.WHITE, this));
	        placeNewPiece('d', 2, new Pawn(board, Color.WHITE, this));
	        placeNewPiece('e', 2, new Pawn(board, Color.WHITE, this));
	        placeNewPiece('f', 2, new Pawn(board, Color.WHITE, this));
	        placeNewPiece('g', 2, new Pawn(board, Color.WHITE, this));
	        placeNewPiece('h', 2, new Pawn(board, Color.WHITE, this));

	        placeNewPiece('a', 8, new Rook(board, Color.BLACK));
	        placeNewPiece('b', 8, new Knight(board, Color.BLACK));
	        placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
	        placeNewPiece('d', 8, new Queen(board, Color.BLACK));
	        placeNewPiece('e', 8, new King(board, Color.BLACK, this));
	        placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
	        placeNewPiece('g', 8, new Knight(board, Color.BLACK));
	        placeNewPiece('h', 8, new Rook(board, Color.BLACK));
	        placeNewPiece('a', 7, new Pawn(board, Color.BLACK, this));
	        placeNewPiece('b', 7, new Pawn(board, Color.BLACK, this));
	        placeNewPiece('c', 7, new Pawn(board, Color.BLACK, this));
	        placeNewPiece('d', 7, new Pawn(board, Color.BLACK, this));
	        placeNewPiece('e', 7, new Pawn(board, Color.BLACK, this));
	        placeNewPiece('f', 7, new Pawn(board, Color.BLACK, this));
	        placeNewPiece('g', 7, new Pawn(board, Color.BLACK, this));
	        placeNewPiece('h', 7, new Pawn(board, Color.BLACK, this));
	}
}
