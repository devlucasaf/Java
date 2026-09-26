package games.tabuleiro.damas.componentes;

/**
 * Categorias das mensagens de status exibidas ao usuário, cada uma com seu estilo visual.
 */
public enum TipoMensagem {

    INFORMACAO("status-informacao"),
    SUCESSO("status-sucesso"),
    ALERTA("status-alerta"),
    ERRO("status-erro");

    private final String classeCss;

    TipoMensagem(String classeCss) {
        this.classeCss = classeCss;
    }

    public String getClasseCss() {
        return classeCss;
    }
}
