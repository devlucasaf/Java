package udemy.projetos.xadrez.application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import udemy.projetos.xadrez.chess.ChessException;
import udemy.projetos.xadrez.chess.ChessMatch;
import udemy.projetos.xadrez.chess.ChessPiece;
import udemy.projetos.xadrez.chess.ChessPosition;

public class Program {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ChessMatch chessMatch = new ChessMatch();
        List<ChessPiece> captured = new ArrayList<>();

        while (!chessMatch.getChekcMate()) {
            try {
                UI.clearScreen();
                UI.printMatch(chessMatch, captured);
                System.out.println();
                System.out.print("Source: ");
                ChessPosition source = UI.readChessPosition(scanner);
                boolean[][] possibleMoves = chessMatch.possibleMoves(source);
                UI.clearScreen();
                UI.printBoard(chessMatch.getPieces(), possibleMoves);

                System.out.println();
                System.out.print("Target: ");
                ChessPosition target = UI.readChessPosition(scanner);
                ChessPiece capturedPiece = chessMatch.performCheesMove(source, target);
                if(capturedPiece != null) {
                    captured.add(capturedPiece);
                }
            } catch (ChessException e) {
                System.out.println(e.getMessage());
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println(e.getMessage());
                scanner.nextLine();
            }
        }
        UI.clearScreen();
        UI.printMatch(chessMatch, captured);
    }
}
