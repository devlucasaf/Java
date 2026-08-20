package cursos.udemy.projetos.xadrez.chess;

import cursos.udemy.projetos.xadrez.boardgame.BoardException;

public class ChessException extends BoardException{
  private static final long serialVersionUID = 1L;

  public ChessException(String msg) {
    super(msg);
  }
}
