package application.utilitarios.musica;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javafx.application.Platform;
import javafx.collections.MapChangeListener;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public final class ReprodutorMusica {

    private static final double VOLUME_INICIAL = 0.65;
    private static final double VOLUME_MINIMO = 0.0;
    private static final double VOLUME_MAXIMO = 1.0;

    private final GerenciadorPlaylist           gerenciadorPlaylist;
    private final List<ObservadorReprodutor>    observadores;

    private MediaPlayer                         tocadorMidia;
    private Musica                              musicaAtual;
    private EstadoReproducao                    estadoAtual;
    private double                              volumeAtual;
    private boolean                             reproducaoAutomaticaPendente;

    // --- INICIALIZA O REPRODUTOR DE MUSICA ---
    public ReprodutorMusica(GerenciadorPlaylist gerenciadorPlaylist) {
        this.gerenciadorPlaylist = Objects.requireNonNull(gerenciadorPlaylist, "O gerenciador da playlist nao pode ser nulo.");

        observadores = new ArrayList<>();
        estadoAtual = EstadoReproducao.PRONTO;
        volumeAtual = VOLUME_INICIAL;
        reproducaoAutomaticaPendente = false;
    }

    // --- ADICIONA UM OBSERVADOR AO REPRODUTOR ---
    public void adicionarObservador(ObservadorReprodutor observador) {
        Objects.requireNonNull(observador, "O observador nao pode ser nulo.");

        if (!observadores.contains(observador)) {
            observadores.add(observador);
        }
    }

    // --- REMOVE UM OBSERVADOR DO REPRODUTOR ---
    public void removerObservador(ObservadorReprodutor observador) {
        observadores.remove(observador);
    }

    // --- CARREGA UMA MUSICA NO REPRODUTOR ---
    public void carregarMusica(Musica musica) {
        carregarMusica(musica, false);
    }

    // --- CARREGA UMA MUSICA E DEFINE A REPRODUCAO AUTOMATICA ---
    public void carregarMusica(Musica musica, boolean reproduzirAutomaticamente) {
        if (musica == null) {
            alterarEstado(EstadoReproducao.ERRO, "Nenhuma musica foi selecionada.");
            return;
        }

        liberarTocadorAtual();

        musicaAtual = musica;
        reproducaoAutomaticaPendente = reproduzirAutomaticamente;

        gerenciadorPlaylist.selecionarMusica(musica);
        notificarTrocaMusica();
        alterarEstado(EstadoReproducao.CARREGANDO, musica.obterTitulo());

        try {
            Media midia = new Media(musica.obterEnderecoArquivo());

            configurarLeituraMetadados(midia, musica);

            tocadorMidia = new MediaPlayer(midia);
            tocadorMidia.setVolume(volumeAtual);

            configurarEventosTocador(tocadorMidia, musica);
        } catch (MediaException | IllegalArgumentException excecao) {
            alterarEstado(EstadoReproducao.ERRO, obterMensagemExcecao(excecao));
        }
    }

    // --- CONFIGURA A LEITURA DOS METADADOS DA MUSICA ---
    private void configurarLeituraMetadados(Media midia, Musica musica) {
        midia.getMetadata().addListener((MapChangeListener<String, Object>) alteracao -> {
                    if (!alteracao.wasAdded()) {
                        return;
                    }

                    atualizarMetadado(musica, alteracao.getKey(), alteracao.getValueAdded());
                }
        );
    }

    // --- ATUALIZA UM METADADO EXTRAIDO DO ARQUIVO ---
    private void atualizarMetadado(Musica musica, String chave, Object valor) {
        if (valor == null) {
            return;
        }

        executarNaInterface(() -> {
            switch (chave) {
                case "title" -> musica.definirTitulo(valor.toString());
                case "artist" -> musica.definirArtista(valor.toString());
                case "album" -> musica.definirAlbum(valor.toString());
                case "image" -> {
                    if (valor instanceof Image imagem) {
                        musica.definirCapa(imagem);
                    }
                }
                default -> {
                    return;
                }
            }

            if (musica.equals(musicaAtual)) {
                notificarTrocaMusica();
            }
        });
    }

    // --- CONFIGURA OS EVENTOS DO TOCADOR DE MIDIA ---
    private void configurarEventosTocador(MediaPlayer tocador, Musica musica) {
        tocador.currentTimeProperty().addListener((observavel, tempoAnterior, tempoAtual) ->
                        notificarAtualizacaoTempo(tempoAtual, tocador.getTotalDuration())
        );

        tocador.setOnReady(() -> aoPrepararMusica(tocador, musica));

        tocador.setOnPlaying(() -> alterarEstado(EstadoReproducao.REPRODUZINDO, musica.obterTitulo()));

        tocador.setOnPaused(() -> alterarEstado(EstadoReproducao.PAUSADO, musica.obterTitulo()));

        tocador.setOnStopped(() -> alterarEstado(EstadoReproducao.PARADO, musica.obterTitulo()));

        tocador.setOnEndOfMedia(this::aoFinalizarMusica);

        tocador.setOnError(() ->
                alterarEstado(
                        EstadoReproducao.ERRO,
                        obterMensagemErroTocador(tocador)
                )
        );
    }

    // --- TRATA A PREPARACAO COMPLETA DA MUSICA ---
    private void aoPrepararMusica(MediaPlayer tocador, Musica musica) {
        if (tocador != tocadorMidia) {
            return;
        }

        notificarAtualizacaoTempo(Duration.ZERO, tocador.getTotalDuration());

        alterarEstado(EstadoReproducao.PRONTO, musica.obterTitulo());

        if (reproducaoAutomaticaPendente) {
            reproducaoAutomaticaPendente = false;
            reproduzir();
        }
    }

    // --- TRATA A FINALIZACAO DA MUSICA ATUAL ---
    private void aoFinalizarMusica() {
        String titulo = musicaAtual == null
                ? "Musica desconhecida"
                : musicaAtual.obterTitulo();

        alterarEstado(EstadoReproducao.FINALIZADO, titulo);

        Musica proximaMusica = gerenciadorPlaylist.obterProximaMusica();

        if (proximaMusica != null) {
            carregarMusica(proximaMusica, true);
        }
    }

    // --- INICIA OU RETOMA A REPRODUCAO ---
    public void reproduzir() {
        if (tocadorMidia != null) {
            tocadorMidia.play();
            return;
        }

        Musica musicaSelecionada = gerenciadorPlaylist.obterMusicaAtual();

        if (musicaSelecionada == null) {
            alterarEstado(
                    EstadoReproducao.ERRO,
                    "A lista de reproducao esta vazia."
            );
            return;
        }

        carregarMusica(musicaSelecionada, true);
    }

    // --- PAUSA A REPRODUCAO ATUAL ---
    public void pausar() {
        if (tocadorMidia != null) {
            tocadorMidia.pause();
        }
    }

    // --- INTERROMPE A REPRODUCAO ATUAL ---
    public void parar() {
        if (tocadorMidia == null) {
            return;
        }

        tocadorMidia.stop();
        tocadorMidia.seek(Duration.ZERO);

        notificarAtualizacaoTempo(
                Duration.ZERO,
                tocadorMidia.getTotalDuration()
        );
    }

    // --- CARREGA E REPRODUZ A PROXIMA MUSICA ---
    public void reproduzirProximaMusica() {
        Musica proximaMusica = gerenciadorPlaylist.obterProximaMusica();

        if (proximaMusica == null) {
            alterarEstado(
                    EstadoReproducao.ERRO,
                    "A lista de reproducao esta vazia."
            );
            return;
        }

        carregarMusica(proximaMusica, true);
    }

    // --- CARREGA E REPRODUZ A MUSICA ANTERIOR ---
    public void reproduzirMusicaAnterior() {
        Musica musicaAnterior = gerenciadorPlaylist.obterMusicaAnterior();

        if (musicaAnterior == null) {
            alterarEstado(
                    EstadoReproducao.ERRO,
                    "A lista de reproducao esta vazia."
            );
            return;
        }

        carregarMusica(musicaAnterior, true);
    }

    // --- ALTERA A POSICAO DA REPRODUCAO EM SEGUNDOS ---
    public void alterarPosicao(double segundos) {
        if (tocadorMidia == null || Double.isNaN(segundos)) {
            return;
        }

        Duration duracaoTotal = tocadorMidia.getTotalDuration();

        if (duracaoTotal == null
                || duracaoTotal.isUnknown()
                || duracaoTotal.isIndefinite()) {
            return;
        }

        double posicaoLimitada = Math.max(
                0.0,
                Math.min(segundos, duracaoTotal.toSeconds())
        );

        tocadorMidia.seek(Duration.seconds(posicaoLimitada));
    }

    // --- ALTERA O VOLUME DO REPRODUTOR ---
    public void definirVolume(double volume) {
        volumeAtual = Math.max(
                VOLUME_MINIMO,
                Math.min(VOLUME_MAXIMO, volume)
        );

        if (tocadorMidia != null) {
            tocadorMidia.setVolume(volumeAtual);
        }
    }

    // --- RETORNA O VOLUME ATUAL ---
    public double obterVolume() {
        return volumeAtual;
    }

    // --- RETORNA A MUSICA CARREGADA ATUALMENTE ---
    public Musica obterMusicaAtual() {
        return musicaAtual;
    }

    // --- RETORNA O ESTADO ATUAL DO REPRODUTOR ---
    public EstadoReproducao obterEstadoAtual() {
        return estadoAtual;
    }

    // --- VERIFICA SE EXISTE UMA MUSICA CARREGADA ---
    public boolean possuiMusicaCarregada() {
        return musicaAtual != null && tocadorMidia != null;
    }

    // --- LIBERA OS RECURSOS DA MUSICA ATUAL ---
    private void liberarTocadorAtual() {
        if (tocadorMidia == null) {
            return;
        }

        tocadorMidia.setOnReady(null);
        tocadorMidia.setOnPlaying(null);
        tocadorMidia.setOnPaused(null);
        tocadorMidia.setOnStopped(null);
        tocadorMidia.setOnEndOfMedia(null);
        tocadorMidia.setOnError(null);
        tocadorMidia.stop();
        tocadorMidia.dispose();
        tocadorMidia = null;
    }

    // --- ENCERRA O REPRODUTOR E LIBERA SEUS RECURSOS ---
    public void encerrar() {
        reproducaoAutomaticaPendente = false;
        liberarTocadorAtual();
        musicaAtual = null;
        estadoAtual = EstadoReproducao.PARADO;
        observadores.clear();
    }

    // --- ALTERA O ESTADO E NOTIFICA OS OBSERVADORES ---
    private void alterarEstado(
            EstadoReproducao novoEstado,
            String detalhe
    ) {
        estadoAtual = Objects.requireNonNull(
                novoEstado,
                "O estado da reproducao nao pode ser nulo."
        );

        executarNaInterface(() -> {
            List<ObservadorReprodutor> copiaObservadores =
                    new ArrayList<>(observadores);

            for (ObservadorReprodutor observador : copiaObservadores) {
                observador.aoAlterarEstado(novoEstado, detalhe);
            }
        });
    }

    // --- NOTIFICA A TROCA DA MUSICA ATUAL ---
    private void notificarTrocaMusica() {
        executarNaInterface(() -> {
            List<ObservadorReprodutor> copiaObservadores =
                    new ArrayList<>(observadores);

            for (ObservadorReprodutor observador : copiaObservadores) {
                observador.aoTrocarMusica(musicaAtual);
            }
        });
    }

    // --- NOTIFICA A ATUALIZACAO DO TEMPO DA REPRODUCAO ---
    private void notificarAtualizacaoTempo(
            Duration tempoAtual,
            Duration duracaoTotal
    ) {
        executarNaInterface(() -> {
            List<ObservadorReprodutor> copiaObservadores =
                    new ArrayList<>(observadores);

            for (ObservadorReprodutor observador : copiaObservadores) {
                observador.aoAtualizarTempo(tempoAtual, duracaoTotal);
            }
        });
    }

    // --- EXECUTA UMA ACAO NA LINHA DE INTERFACE DO JAVAFX ---
    private void executarNaInterface(Runnable acao) {
        if (Platform.isFxApplicationThread()) {
            acao.run();
        } else {
            Platform.runLater(acao);
        }
    }

    // --- RETORNA A MENSAGEM DE ERRO DO TOCADOR ---
    private String obterMensagemErroTocador(MediaPlayer tocador) {
        MediaException erro = tocador.getError();

        if (erro == null || erro.getMessage() == null) {
            return "Formato de audio invalido ou arquivo indisponivel.";
        }

        return erro.getMessage();
    }

    // --- RETORNA UMA MENSAGEM SEGURA PARA A EXCECAO ---
    private String obterMensagemExcecao(Exception excecao) {
        String mensagem = excecao.getMessage();

        if (mensagem == null || mensagem.isBlank()) {
            return "Nao foi possivel carregar o arquivo de audio.";
        }

        return mensagem;
    }
}
