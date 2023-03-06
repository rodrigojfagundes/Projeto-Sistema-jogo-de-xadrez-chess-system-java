package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Program {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ChessMatch chessMatch = new ChessMatch();
		
		List<ChessPiece> captured = new ArrayList<>();
		
		Scanner sc = new Scanner(System.in);
		while(!chessMatch.getCheck()) {
		
		try {	
		
		UI.clearScreen();
		
		UI.printMatch(chessMatch, captured);
		System.out.println();
		
		System.out.print("source");
		
		ChessPosition source = UI.readChessPosition(sc);
		
		boolean[][] possibleMoves = chessMatch.possibleMoves(source);
		UI.clearScreen();
		
		UI.printBoard(chessMatch.getPieces(), possibleMoves);
		
		
		System.out.println();
		//posicao de destino
		System.out.println("target");
		ChessPosition target = UI.readChessPosition(sc);
		

		ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
		
		if (capturedPiece != null) {
		
			captured.add(capturedPiece);
		}
		
		}
		
		catch(ChessException e) {
			System.out.print(e.getMessage());
			sc.nextLine();
		}
		catch(InputMismatchException e) {
			System.out.print(e.getMessage());
			sc.nextLine();
		}
	}
		UI.clearScreen();
		UI.printMatch(chessMatch, captured);
}

}
