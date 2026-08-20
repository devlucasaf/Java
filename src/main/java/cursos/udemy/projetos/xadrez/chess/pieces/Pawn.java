package cursos.udemy.projetos.xadrez.chess.pieces;

import cursos.udemy.projetos.xadrez.boardgame.Board;
import cursos.udemy.projetos.xadrez.boardgame.Position;
import cursos.udemy.projetos.xadrez.chess.ChessMatch;
import cursos.udemy.projetos.xadrez.chess.ChessPiece;
import cursos.udemy.projetos.xadrez.chess.Color;

public class Pawn extends ChessPiece {

    private ChessMatch chessMatch;

    public Pawn(Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        this.chessMatch = chessMatch;
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
        Position p = new Position(0, 0);

        if (getColor() == Color.WHITE) {
            p.setValues(position.getRow() - 1, position.getColumn());

            if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
                mat[p.getRow()][p.getColumn()] = true;
            }

            p.setValues(position.getRow() - 2, position.getColumn());
            Position p2 = new Position(position.getRow() - 1, position.getColumn());

            if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getMoveCount() == 0
                    && getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2)) {
                mat[p.getRow()][p.getColumn()] = true;
            }

            p.setValues(position.getRow() - 1, position.getColumn() - 1);

            if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
                mat[p.getRow()][p.getColumn()] = true;
            }

            p.setValues(position.getRow() - 1, position.getColumn() + 1);

            if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
                mat[p.getRow()][p.getColumn()] = true;
            }

            // En Passant for White
            if (position.getRow() == 3) {
                Position left = new Position(position.getRow(), position.getColumn() - 1);

                if (getBoard().positionExists(left) && isThereOpponentPiece(left) && getBoard().piece(left) == chessMatch.getEnPassant()) {
                    mat[left.getRow() - 1][left.getColumn()] = true;  // Move up to capture en passant
                }

                Position right = new Position(position.getRow(), position.getColumn() + 1);

                if (getBoard().positionExists(right) && isThereOpponentPiece(right) && getBoard().piece(right) == chessMatch.getEnPassant()) {
                    mat[right.getRow() - 1][right.getColumn()] = true;  // Move up to capture en passant
                }
            }
        } else {
            p.setValues(position.getRow() + 1, position.getColumn());

            if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
                mat[p.getRow()][p.getColumn()] = true;
            }

            p.setValues(position.getRow() + 2, position.getColumn());
            Position p2 = new Position(position.getRow() + 1, position.getColumn());
            if (getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p) && getMoveCount() == 0
                    && getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2)) {
                mat[p.getRow()][p.getColumn()] = true;
            }

            p.setValues(position.getRow() + 1, position.getColumn() - 1);
            if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
                mat[p.getRow()][p.getColumn()] = true;
            }

            p.setValues(position.getRow() + 1, position.getColumn() + 1);
            if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
                mat[p.getRow()][p.getColumn()] = true;
            }

            // En Passant for Black
            if (position.getRow() == 4) {
                Position left = new Position(position.getRow(), position.getColumn() - 1);

                if (getBoard().positionExists(left) && isThereOpponentPiece(left) && getBoard().piece(left) == chessMatch.getEnPassant()) {
                    mat[left.getRow() + 1][left.getColumn()] = true;  // Move down to capture en passant
                }

                Position right = new Position(position.getRow(), position.getColumn() + 1);
                if (getBoard().positionExists(right) && isThereOpponentPiece(right) && getBoard().piece(right) == chessMatch.getEnPassant()) {
                    mat[right.getRow() + 1][right.getColumn()] = true;  // Move down to capture en passant
                }
            }
        }

        return mat;
    }

    @Override
    public String toString() {
        return "P";
    }
}
