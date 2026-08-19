package udemy.projetos.xadrez.chess;

import udemy.projetos.xadrez.boardgame.BoardException;

public class ChessException extends BoardException{
  private static final long serialVersionUID = 1L;

  public ChessException(String msg) {
    super(msg);
  }
}
