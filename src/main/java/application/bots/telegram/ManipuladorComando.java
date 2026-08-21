package application.bots.telegram;

@FunctionalInterface
public interface ManipuladorComando {
    String responder(long chatId, String argumentos);
}
