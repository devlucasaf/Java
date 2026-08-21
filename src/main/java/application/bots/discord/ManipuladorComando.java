package application.bots.discord;

@FunctionalInterface
public interface ManipuladorComando {
    String responder(String usuario, String argumentos);
}
