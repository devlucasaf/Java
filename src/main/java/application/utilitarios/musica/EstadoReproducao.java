package application.utilitarios.musica;

public enum EstadoReproducao {
    PRONTO("Pronto para reproduzir"),
    CARREGANDO("Carregando música"),
    REPRODUZINDO("Música em reprodução"),
    PAUSADO("Música pausada"),
    PARADO("Reprodução parada"),
    FINALIZADO("Música finalizada"),
    ERRO("Não foi possível reproduzir a música");

    private final String descricao;

    // --- INICIALIZA O ESTADO COM SUA DESCRIÇÃO VISUAL ---
    EstadoReproducao(String descricao) {
        this.descricao = descricao;
    }

    // --- RETORNA A DESCRIÇÃO DO ESTADO ---
    public String obterDescricao() {
        return descricao;
    }

    // --- VERIFICA SE O REPRODUTOR ESTÁ EXECUTANDO UMA MÚSICA ---
    public boolean estaReproduzindo() {
        return this == REPRODUZINDO;
    }

    // --- VERIFICA SE A REPRODUÇÃO ESTÁ PAUSADA ---
    public boolean estaPausado() {
        return this == PAUSADO;
    }

    // --- VERIFICA SE O REPRODUTOR ESTÁ CARREGANDO UMA MÚSICA ---
    public boolean estaCarregando() {
        return this == CARREGANDO;
    }

    // --- VERIFICA SE OCORREU ALGUM ERRO NA REPRODUÇÃO ---
    public boolean possuiErro() {
        return this == ERRO;
    }

    // --- VERIFICA SE A REPRODUÇÃO FOI ENCERRADA ---
    public boolean estaEncerrado() {
        return this == PARADO || this == FINALIZADO;
    }

    // --- RETORNA A DESCRIÇÃO DO ESTADO ---
    @Override
    public String toString() {
        return descricao;
    }
}

