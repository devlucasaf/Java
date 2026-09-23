package application.utilitarios.musica;

import javafx.util.Duration;

public interface ObservadorReprodutor {

    // --- NOTIFICA A ALTERAÇÃO DA MÚSICA ATUAL ---
    void aoTrocarMusica(Musica musica);

    // --- NOTIFICA A ATUALIZAÇÃO DO TEMPO DA REPRODUÇÃO ---
    void aoAtualizarTempo(Duration tempoAtual, Duration duracaoTotal);

    // --- NOTIFICA A ALTERAÇÃO DO ESTADO DA REPRODUÇÃO ---
    void aoAlterarEstado(EstadoReproducao estadoReproducao, String detalhe);
}

