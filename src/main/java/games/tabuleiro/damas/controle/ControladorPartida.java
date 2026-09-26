package games.tabuleiro.damas.controle;

import games.tabuleiro.damas.componentes.PainelInformacoes;
import games.tabuleiro.damas.componentes.TabuleiroVisual;
import games.tabuleiro.damas.componentes.TipoMensagem;
import games.tabuleiro.damas.ia.BotMinimax;
import games.tabuleiro.damas.ia.JogadorArtificial;
import games.tabuleiro.damas.modelo.ConfiguracaoPartida;
import games.tabuleiro.damas.modelo.EstadoPartida;
import games.tabuleiro.damas.modelo.Jogador;
import games.tabuleiro.damas.modelo.Movimento;
import games.tabuleiro.damas.modelo.Peca;
import games.tabuleiro.damas.modelo.Posicao;
import games.tabuleiro.damas.modelo.Tabuleiro;
import games.tabuleiro.damas.motor.Partida;
import games.tabuleiro.damas.motor.ResultadoJogada;
import games.tabuleiro.damas.telas.TelaPartida;
import games.tabuleiro.damas.util.Constantes;
import games.tabuleiro.damas.util.Dialogos;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.stage.Window;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Faz a ponte entre o motor da partida e a tela: interpreta os cliques do usuário,
 * controla a seleção e as capturas em sequência, aciona o BOT e atualiza o painel.
 * <p>
 * O campo {@code geracao} identifica a partida atual; callbacks de animações ou do BOT
 * pertencentes a uma partida anterior (reiniciada ou abandonada) são ignorados.
 */
public class ControladorPartida {

    private final ConfiguracaoPartida configuracao;
    private final TelaPartida tela;
    private final TabuleiroVisual tabuleiroVisual;
    private final PainelInformacoes painel;
    private final Partida partida = new Partida();
    private final JogadorArtificial bot;
    private final ExecutorService executorBot;
    private final Runnable aoVoltarMenu;
    private final Runnable aoEncerrarJogo;

    private Posicao posicaoSelecionada;
    private List<Movimento> candidatos = List.of();
    private final List<Posicao> caminhoParcial = new ArrayList<>();
    private boolean entradaBloqueada;
    private boolean finalizado;
    private long geracao;

    public ControladorPartida(ConfiguracaoPartida configuracao, TelaPartida tela,
                              Runnable aoVoltarMenu, Runnable aoEncerrarJogo) {
        this.configuracao = configuracao;
        this.tela = tela;
        this.tabuleiroVisual = tela.getTabuleiroVisual();
        this.painel = tela.getPainel();
        this.aoVoltarMenu = aoVoltarMenu;
        this.aoEncerrarJogo = aoEncerrarJogo;

        if (configuracao.isContraBot()) {
            bot = new BotMinimax(configuracao.nivel());
            executorBot = Executors.newSingleThreadExecutor(tarefa -> {
                Thread thread = new Thread(tarefa, "bot-damas");
                thread.setDaemon(true);
                return thread;
            });
        } else {
            bot = null;
            executorBot = null;
        }
        registrarEventos();
    }

    private void registrarEventos() {
        tabuleiroVisual.setAoClicarCasa(this::tratarCliqueCasa);
        tela.setAoSolicitarNovaPartida(this::solicitarNovaPartida);
        tela.setAoSolicitarMenu(this::solicitarVoltarMenu);
        tela.getModal().setAoNovaPartida(() -> {
            tela.ocultarFimDePartida();
            iniciarNovaPartida();
        });
        tela.getModal().setAoVoltarMenu(aoVoltarMenu);
        tela.getModal().setAoEncerrar(aoEncerrarJogo);
    }

    // ------------------------------------------------------------------ ciclo da partida

    public void iniciarNovaPartida() {
        geracao++;
        tabuleiroVisual.cancelarAnimacoes();
        partida.reiniciar();
        limparSelecao();
        entradaBloqueada = false;
        painel.mostrarPensando(false);

        tabuleiroVisual.reconstruir(partida.getTabuleiro());
        tabuleiroVisual.limparUltimoMovimento();
        painel.atualizarJogadas(0, null);
        painel.atualizarSituacao(partida.getEstado());
        prepararTurno("Nova partida iniciada!", TipoMensagem.SUCESSO);
    }

    /** Libera os recursos da partida ao sair da tela (thread do BOT e animações). */
    public void finalizar() {
        finalizado = true;
        geracao++;
        tabuleiroVisual.cancelarAnimacoes();
        if (executorBot != null) {
            executorBot.shutdownNow();
        }
    }

    private void prepararTurno(String mensagemAnterior, TipoMensagem tipoAnterior) {
        atualizarPainel();
        Jogador atual = partida.getJogadorAtual();
        String nome = configuracao.nomeExibicao(atual);

        if (isVezDoBot()) {
            tabuleiroVisual.limparDestaquesInterativos();
            tabuleiroVisual.definirCasasInterativas(Set.of());
            definirStatus(combinar(mensagemAnterior, "O BOT está pensando na próxima jogada..."), tipoAnterior);
            jogarBot();
            return;
        }

        if (partida.existeCapturaObrigatoria()) {
            definirStatus(combinar(mensagemAnterior,
                    "Captura obrigatória! " + nome + ", capture uma peça adversária."), TipoMensagem.ALERTA);
        } else {
            definirStatus(combinar(mensagemAnterior,
                    "Vez do " + nome + ". Selecione uma peça para mover."), tipoAnterior);
        }
        atualizarDestaques();
    }

    // ------------------------------------------------------------------ jogadas humanas

    private void tratarCliqueCasa(Posicao posicao) {
        if (entradaBloqueada || partida.isEncerrada() || finalizado) {
            return;
        }
        if (isVezDoBot()) {
            definirStatus("Aguarde! É a vez do BOT jogar.", TipoMensagem.ALERTA);
            return;
        }

        // Durante uma captura em sequência, só é permitido continuar com a mesma peça.
        if (!caminhoParcial.isEmpty()) {
            if (proximosDestinos().contains(posicao)) {
                executarPasso(posicao);
            } else {
                definirStatus("Captura em sequência! Continue capturando com a peça selecionada.",
                        TipoMensagem.ALERTA);
                tabuleiroVisual.sinalizarErro(posicao);
            }
            return;
        }

        if (posicaoSelecionada != null && proximosDestinos().contains(posicao)) {
            executarPasso(posicao);
            return;
        }

        Jogador atual = partida.getJogadorAtual();
        Peca peca = partida.getPeca(posicao);
        if (peca != null && peca.getJogador() == atual) {
            selecionarPeca(posicao);
        } else if (peca != null) {
            definirStatus("Esta peça pertence ao " + configuracao.nomeExibicao(peca.getJogador())
                    + ". Agora é a vez do " + configuracao.nomeExibicao(atual) + ".", TipoMensagem.ERRO);
            tabuleiroVisual.sinalizarErro(posicao);
        } else if (posicaoSelecionada != null) {
            definirStatus(posicao.isCasaEscura()
                    ? "Movimento inválido! Escolha uma das casas destacadas."
                    : "Movimento inválido! As peças só andam pelas casas pretas.", TipoMensagem.ERRO);
            tabuleiroVisual.sinalizarErro(posicao);
        } else {
            definirStatus("Selecione uma de suas peças para começar a jogada.", TipoMensagem.INFORMACAO);
        }
    }

    private void selecionarPeca(Posicao posicao) {
        if (posicao.equals(posicaoSelecionada)) {
            limparSelecao();
            atualizarDestaques();
            definirStatus("Peça desmarcada. Selecione uma peça para mover.", TipoMensagem.INFORMACAO);
            return;
        }

        List<Movimento> movimentos = partida.getMovimentosDaPeca(posicao);
        if (movimentos.isEmpty()) {
            definirStatus(partida.existeCapturaObrigatoria()
                    ? "Captura obrigatória! Selecione uma das peças destacadas em laranja."
                    : "Esta peça não possui movimentos válidos no momento.", TipoMensagem.ALERTA);
            tabuleiroVisual.sinalizarErro(posicao);
            return;
        }

        limparSelecao();
        posicaoSelecionada = posicao;
        candidatos = movimentos;
        tabuleiroVisual.definirPecaSelecionada(posicao);

        int capturas = movimentos.get(0).getQuantidadeCapturas();
        if (capturas > 0) {
            definirStatus("Peça selecionada. Capture " + capturas + (capturas > 1 ? " peças" : " peça")
                    + " seguindo as casas marcadas em vermelho.", TipoMensagem.ALERTA);
        } else {
            definirStatus("Peça selecionada. Escolha uma das casas destacadas.", TipoMensagem.INFORMACAO);
        }
        atualizarDestaques();
    }

    /** Move a peça selecionada um passo; ao completar a jogada, ela é registrada na partida. */
    private void executarPasso(Posicao destino) {
        int indice = caminhoParcial.size();
        List<Movimento> filtrados = new ArrayList<>();
        for (Movimento candidato : candidatos) {
            if (candidato.getCaminho().get(indice).equals(destino)) {
                filtrados.add(candidato);
            }
        }
        Posicao capturada = filtrados.get(0).getCapturaDoPasso(indice);
        Posicao origemPasso = posicaoSelecionada;

        candidatos = filtrados;
        caminhoParcial.add(destino);
        posicaoSelecionada = destino;
        entradaBloqueada = true;
        tabuleiroVisual.limparDestaquesInterativos();
        tabuleiroVisual.definirCasasInterativas(Set.of());

        long geracaoAtual = geracao;
        tabuleiroVisual.animarPasso(origemPasso, destino, capturada, () -> {
            if (geracaoAtual != geracao) {
                return;
            }
            entradaBloqueada = false;
            Movimento completo = null;
            for (Movimento candidato : candidatos) {
                if (candidato.getCaminho().size() == caminhoParcial.size()) {
                    completo = candidato;
                    break;
                }
            }
            if (completo != null) {
                concluirJogada(completo);
            } else {
                definirStatus("Captura em sequência! Continue capturando com a mesma peça.", TipoMensagem.ALERTA);
                atualizarDestaques();
            }
        });
    }

    // ------------------------------------------------------------------ BOT

    private boolean isVezDoBot() {
        return bot != null && configuracao.isControladoPeloBot(partida.getJogadorAtual());
    }

    private void jogarBot() {
        if (finalizado) {
            return;
        }
        entradaBloqueada = true;
        painel.mostrarPensando(true);

        Tabuleiro copia = partida.getTabuleiro();
        Jogador vez = partida.getJogadorAtual();
        long geracaoAtual = geracao;
        long inicio = System.currentTimeMillis();

        CompletableFuture.supplyAsync(() -> bot.escolherMovimento(copia, vez), executorBot)
                .whenComplete((movimento, erro) -> Platform.runLater(() -> {
                    if (geracaoAtual != geracao || finalizado) {
                        return;
                    }
                    if (erro != null || movimento == null) {
                        painel.mostrarPensando(false);
                        entradaBloqueada = false;
                        definirStatus("O BOT não conseguiu escolher uma jogada.", TipoMensagem.ERRO);
                        return;
                    }
                    // Pequena espera para que a jogada do BOT não pareça instantânea.
                    double decorrido = System.currentTimeMillis() - inicio;
                    PauseTransition espera = new PauseTransition(
                            Duration.millis(Math.max(0, Constantes.ATRASO_MINIMO_BOT_MS - decorrido)));
                    espera.setOnFinished(evento -> {
                        if (geracaoAtual == geracao) {
                            painel.mostrarPensando(false);
                            executarJogadaDoBot(movimento);
                        }
                    });
                    espera.play();
                }));
    }

    private void executarJogadaDoBot(Movimento movimento) {
        long geracaoAtual = geracao;
        tabuleiroVisual.definirPecaSelecionada(movimento.getOrigem());
        tabuleiroVisual.destacarSelecao(movimento.getOrigem());
        tabuleiroVisual.destacarDestinos(List.of(movimento.getCaminho().get(0)), movimento.isCaptura());

        PauseTransition pausa = new PauseTransition(Duration.millis(Constantes.PAUSA_ANTES_JOGADA_BOT_MS));
        pausa.setOnFinished(evento -> {
            if (geracaoAtual != geracao) {
                return;
            }
            tabuleiroVisual.limparDestaquesInterativos();
            tabuleiroVisual.animarMovimento(movimento, () -> {
                if (geracaoAtual == geracao) {
                    concluirJogada(movimento);
                }
            });
        });
        pausa.play();
    }

    // ------------------------------------------------------------------ conclusão da jogada

    private void concluirJogada(Movimento movimento) {
        limparSelecao();
        ResultadoJogada resultado = partida.executarMovimento(movimento);
        entradaBloqueada = true;
        tabuleiroVisual.limparDestaquesInterativos();
        tabuleiroVisual.definirCasasInterativas(Set.of());

        long geracaoAtual = geracao;
        tabuleiroVisual.concluirMovimento(movimento, resultado.promoveu(), () -> {
            if (geracaoAtual != geracao) {
                return;
            }
            entradaBloqueada = false;
            tabuleiroVisual.sincronizar(partida.getTabuleiro());
            tabuleiroVisual.destacarUltimoMovimento(movimento);
            painel.atualizarJogadas(partida.getJogadasRealizadas(), movimento);
            painel.atualizarSituacao(partida.getEstado());

            TipoMensagem tipo = movimento.isCaptura() || resultado.promoveu()
                    ? TipoMensagem.SUCESSO : TipoMensagem.INFORMACAO;
            if (partida.isEncerrada()) {
                atualizarPainel();
                exibirFimDePartida();
            } else {
                prepararTurno(descreverJogada(resultado), tipo);
            }
        });
    }

    private String descreverJogada(ResultadoJogada resultado) {
        Movimento movimento = resultado.movimento();
        String autor = configuracao.nomeExibicao(resultado.autor());
        StringBuilder texto = new StringBuilder();
        if (movimento.isCaptura()) {
            int total = movimento.getQuantidadeCapturas();
            texto.append(autor).append(" capturou ").append(total).append(total > 1 ? " peças!" : " peça!");
        } else {
            texto.append(autor).append(" moveu ").append(movimento.getDescricao()).append('.');
        }
        if (resultado.promoveu()) {
            texto.append(" A peça foi promovida a dama!");
        }
        return texto.toString();
    }

    private void exibirFimDePartida() {
        EstadoPartida estado = partida.getEstado();
        Jogador vencedor = partida.getVencedor();
        tabuleiroVisual.definirCasasInterativas(Set.of());

        String resultado = vencedor != null ? vencedor.getNome() + " venceu!" : "Partida empatada!";
        String detalhe = partida.getMotivoFim();
        if (configuracao.isContraBot() && vencedor != null) {
            detalhe += configuracao.isControladoPeloBot(vencedor)
                    ? " O BOT venceu desta vez. Que tal uma revanche?"
                    : " Parabéns, você derrotou o BOT!";
        }
        definirStatus("Partida encerrada. " + partida.getMotivoFim(),
                estado == EstadoPartida.EMPATE ? TipoMensagem.ALERTA : TipoMensagem.SUCESSO);

        long geracaoAtual = geracao;
        String detalheFinal = detalhe;
        PauseTransition espera = new PauseTransition(Duration.millis(Constantes.ATRASO_MODAL_FIM_MS));
        espera.setOnFinished(evento -> {
            if (geracaoAtual == geracao) {
                tela.exibirFimDePartida(resultado, detalheFinal, vencedor);
            }
        });
        espera.play();
    }

    // ------------------------------------------------------------------ botões da barra superior

    private void solicitarNovaPartida() {
        if (partidaEmAndamentoComJogadas() && !Dialogos.confirmar(janela(), "Nova Partida",
                "Deseja reiniciar? O progresso da partida atual será perdido.")) {
            return;
        }
        tela.ocultarFimDePartida();
        iniciarNovaPartida();
    }

    private void solicitarVoltarMenu() {
        if (partidaEmAndamentoComJogadas() && !Dialogos.confirmar(janela(), "Voltar ao Menu Principal",
                "Deseja abandonar a partida atual e voltar ao menu principal?")) {
            return;
        }
        aoVoltarMenu.run();
    }

    private boolean partidaEmAndamentoComJogadas() {
        return !partida.isEncerrada() && partida.getJogadasRealizadas() > 0;
    }

    private Window janela() {
        return tela.getScene() == null ? null : tela.getScene().getWindow();
    }

    // ------------------------------------------------------------------ auxiliares de interface

    private void atualizarDestaques() {
        tabuleiroVisual.limparDestaquesInterativos();
        if (partida.isEncerrada() || isVezDoBot()) {
            tabuleiroVisual.definirCasasInterativas(Set.of());
            return;
        }

        Set<Posicao> interativas = new LinkedHashSet<>();
        if (posicaoSelecionada != null) {
            Set<Posicao> destinos = proximosDestinos();
            tabuleiroVisual.destacarSelecao(posicaoSelecionada);
            tabuleiroVisual.destacarDestinos(destinos, candidatos.get(0).isCaptura());
            interativas.addAll(destinos);
        }
        if (caminhoParcial.isEmpty()) {
            Set<Posicao> origens = partida.getOrigensJogaveis();
            interativas.addAll(origens);
            if (partida.existeCapturaObrigatoria()) {
                tabuleiroVisual.destacarCapturasObrigatorias(origens);
            }
        }
        tabuleiroVisual.definirCasasInterativas(interativas);
    }

    private Set<Posicao> proximosDestinos() {
        Set<Posicao> destinos = new LinkedHashSet<>();
        int indice = caminhoParcial.size();
        for (Movimento candidato : candidatos) {
            if (candidato.getCaminho().size() > indice) {
                destinos.add(candidato.getCaminho().get(indice));
            }
        }
        return destinos;
    }

    private void limparSelecao() {
        posicaoSelecionada = null;
        candidatos = List.of();
        caminhoParcial.clear();
        tabuleiroVisual.definirPecaSelecionada(null);
    }

    private void atualizarPainel() {
        painel.atualizarVez(partida.getJogadorAtual());
        painel.atualizarPlacar(
                partida.contarPecas(Jogador.JOGADOR_1), partida.contarDamas(Jogador.JOGADOR_1),
                partida.contarPecas(Jogador.JOGADOR_2), partida.contarDamas(Jogador.JOGADOR_2));
    }

    private void definirStatus(String mensagem, TipoMensagem tipo) {
        painel.definirStatus(mensagem, tipo);
    }

    private static String combinar(String anterior, String atual) {
        return anterior == null || anterior.isBlank() ? atual : anterior + " " + atual;
    }
}
