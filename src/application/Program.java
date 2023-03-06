package application;

import java.util.Scanner;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Program {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ChessMatch chessMatch = new ChessMatch();
		
		Scanner sc = new Scanner(System.in);
		while(true) {
		
		UI.printBoard(chessMatch.getPieces());
		System.out.println();
		//posicao de origem
		System.out.print("source");
		//lendo a posicao de origem
		ChessPosition source = UI.readChessPosition(sc);
		
		System.out.println();
		//posicao de destino
		System.out.println("target");
		ChessPosition target = UI.readChessPosition(sc);
		
		ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
		}
	}

}
