package application.system.hospital;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RegistroTriagem {

    private static long proximoIdentificador = 1;

    private final long          identificador;
    private final Paciente      paciente;
    private final LocalDateTime horarioEntrada;
    private final List<String>  sintomas;
    private final List<String>  historicoAlteracoes;
    private GravidadeTriagem    gravidadeInicial;
    private GravidadeTriagem    gravidadeAtual;
    private SituacaoTriagem     situacao;
    private LocalDateTime       horarioInicioAtendimento;
    private LocalDateTime       horarioFinalAtendimento;
    private LocalDateTime       horarioUltimaReclassificacao;
    private Medico              medicoResponsavel;
    private String              observacoes;
    private int                 quantidadeReclassificacoes;

    public RegistroTriagem(Paciente paciente, GravidadeTriagem gravidade, List<String> sintomas, String observacoes) {
        validarPaciente(paciente);
        validarGravidade(gravidade);

        this.identificador = proximoIdentificador++;
        this.paciente = paciente;
        this.gravidadeInicial = gravidade;
        this.gravidadeAtual = gravidade;
        this.sintomas = normalizarSintomas(sintomas);
        this.observacoes = observacoes == null ? "" : observacoes.trim();
        this.horarioEntrada = LocalDateTime.now();
        this.horarioUltimaReclassificacao = horarioEntrada;
        this.situacao = SituacaoTriagem.AGUARDANDO;
        this.historicoAlteracoes = new ArrayList<>();
        this.quantidadeReclassificacoes = 0;

        registrarHistorico("Paciente classificado como " + gravidade.getNomeFormatado() + ", cor " + gravidade.getCor() + ".");
    }

    // --- CALCULA O TEMPO DE ESPERA EM MINUTOS ---
    public long calcularTempoEsperaMinutos() {
        LocalDateTime horarioFinal = horarioInicioAtendimento == null ? LocalDateTime.now() : horarioInicioAtendimento;
        return Math.max(0, Duration.between(horarioEntrada, horarioFinal).toMinutes());
    }

    // --- CALCULA A PONTUAÇÃO DINÂMICA DE PRIORIDADE ---
    public long calcularPontuacaoPrioridade() {
        long pontuacaoGravidade = gravidadeAtual.getNivel() * 1000L;
        long pontuacaoEspera = calcularTempoEsperaMinutos() * 10L;
        long pontuacaoReclassificacao = quantidadeReclassificacoes * 100L;
        return pontuacaoGravidade + pontuacaoEspera + pontuacaoReclassificacao;
    }

    // --- VERIFICA SE O TEMPO MÁXIMO RECOMENDADO FOI ATINGIDO ---
    public boolean atingiuTempoMaximo() {
        if (gravidadeAtual == GravidadeTriagem.EMERGENCIA) {
            return true;
        }

        long minutosDesdeReclassificacao = Duration.between(horarioUltimaReclassificacao, LocalDateTime.now()).toMinutes();
        return minutosDesdeReclassificacao >= gravidadeAtual.getTempoMaximoEsperaMinutos();
    }

    // --- RECLASSIFICA AUTOMATICAMENTE A GRAVIDADE DO PACIENTE ---
    public boolean reclassificarAutomaticamente() {
        if (situacao != SituacaoTriagem.AGUARDANDO || gravidadeAtual == GravidadeTriagem.EMERGENCIA || !atingiuTempoMaximo()) {
            return false;
        }

        GravidadeTriagem gravidadeAnterior = gravidadeAtual;
        gravidadeAtual = gravidadeAtual.aumentarGravidade();
        horarioUltimaReclassificacao = LocalDateTime.now();
        quantidadeReclassificacoes++;

        registrarHistorico("Reclassificação automática de " + gravidadeAnterior.getNomeFormatado() + " para " + gravidadeAtual.getNomeFormatado() + ".");
        return true;
    }

    // --- RECLASSIFICA MANUALMENTE A GRAVIDADE DO PACIENTE ---
    public void reclassificarManualmente(GravidadeTriagem novaGravidade, String justificativa) {
        validarGravidade(novaGravidade);

        if (situacao != SituacaoTriagem.AGUARDANDO) {
            throw new IllegalStateException("Somente pacientes aguardando podem ser reclassificados.");
        }

        if (novaGravidade == gravidadeAtual) {
            return;
        }

        GravidadeTriagem gravidadeAnterior = gravidadeAtual;
        gravidadeAtual = novaGravidade;
        horarioUltimaReclassificacao = LocalDateTime.now();
        quantidadeReclassificacoes++;

        String motivo = justificativa == null || justificativa.trim().isEmpty() ? "Sem justificativa informada." : justificativa.trim();
        registrarHistorico("Reclassificação manual de " + gravidadeAnterior.getNomeFormatado() + " para " + novaGravidade.getNomeFormatado() + ". Motivo: " + motivo);
    }

    // --- INICIA O ATENDIMENTO DO PACIENTE ---
    public void iniciarAtendimento(Medico medico) {
        if (medico == null) {
            throw new IllegalArgumentException("O médico não pode ser nulo.");
        }

        if (situacao != SituacaoTriagem.AGUARDANDO) {
            throw new IllegalStateException("O paciente não está aguardando atendimento.");
        }

        this.medicoResponsavel = medico;
        this.horarioInicioAtendimento = LocalDateTime.now();
        this.situacao = SituacaoTriagem.EM_ATENDIMENTO;

        paciente.associarMedico(medico);
        registrarHistorico("Atendimento iniciado pelo médico " + medico.getNome() + ".");
    }

    // --- FINALIZA O ATENDIMENTO DO PACIENTE ---
    public void finalizarAtendimento() {
        if (situacao != SituacaoTriagem.EM_ATENDIMENTO) {
            throw new IllegalStateException("O atendimento ainda não foi iniciado.");
        }

        this.horarioFinalAtendimento = LocalDateTime.now();
        this.situacao = SituacaoTriagem.ATENDIDO;

        registrarHistorico("Atendimento finalizado.");
    }

    // --- RETORNA O PACIENTE PARA A FILA DE ESPERA ---
    public void retornarParaFila(String motivo) {
        if (situacao != SituacaoTriagem.EM_ATENDIMENTO) {
            throw new IllegalStateException("Somente pacientes em atendimento podem retornar para a fila.");
        }

        this.medicoResponsavel = null;
        this.horarioInicioAtendimento = null;
        this.situacao = SituacaoTriagem.AGUARDANDO;
        this.horarioUltimaReclassificacao = LocalDateTime.now();

        String motivoRetorno = motivo == null || motivo.trim().isEmpty() ? "Motivo não informado." : motivo.trim();
        registrarHistorico("Paciente retornou para a fila. Motivo: " + motivoRetorno);
    }

    // --- CANCELA A TRIAGEM DO PACIENTE ---
    public void cancelar(String motivo) {
        if (situacao == SituacaoTriagem.ATENDIDO || situacao == SituacaoTriagem.CANCELADO) {
            throw new IllegalStateException("Este registro não pode mais ser cancelado.");
        }

        this.situacao = SituacaoTriagem.CANCELADO;
        String motivoCancelamento = motivo == null || motivo.trim().isEmpty() ? "Motivo não informado." : motivo.trim();
        registrarHistorico("Triagem cancelada. Motivo: " + motivoCancelamento);
    }

    // --- ADICIONA UMA ALTERAÇÃO AO HISTÓRICO ---
    private void registrarHistorico(String descricao) {
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        historicoAlteracoes.add(LocalDateTime.now().format(formatador) + " - " + descricao);
    }

    // --- NORMALIZA OS SINTOMAS INFORMADOS ---
    private List<String> normalizarSintomas(List<String> sintomasRecebidos) {
        List<String> sintomasNormalizados = new ArrayList<>();

        if (sintomasRecebidos == null) {
            return sintomasNormalizados;
        }

        for (String sintoma : sintomasRecebidos) {
            if (sintoma != null && !sintoma.trim().isEmpty() && !sintomasNormalizados.contains(sintoma.trim())) {
                sintomasNormalizados.add(sintoma.trim());
            }
        }

        return sintomasNormalizados;
    }

    // --- VALIDA O PACIENTE ---
    private void validarPaciente(Paciente paciente) {
        if (paciente == null) {
            throw new IllegalArgumentException("O paciente não pode ser nulo.");
        }
    }

    // --- VALIDA A GRAVIDADE ---
    private void validarGravidade(GravidadeTriagem gravidade) {
        if (gravidade == null) {
            throw new IllegalArgumentException("A gravidade não pode ser nula.");
        }
    }

    public long getIdentificador() {
        return identificador;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public LocalDateTime getHorarioEntrada() {
        return horarioEntrada;
    }

    public GravidadeTriagem getGravidadeInicial() {
        return gravidadeInicial;
    }

    public GravidadeTriagem getGravidadeAtual() {
        return gravidadeAtual;
    }

    public SituacaoTriagem getSituacao() {
        return situacao;
    }

    public LocalDateTime getHorarioInicioAtendimento() {
        return horarioInicioAtendimento;
    }

    public LocalDateTime getHorarioFinalAtendimento() {
        return horarioFinalAtendimento;
    }

    public Medico getMedicoResponsavel() {
        return medicoResponsavel;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public List<String> getSintomas() {
        return Collections.unmodifiableList(sintomas);
    }

    public List<String> getHistoricoAlteracoes() {
        return Collections.unmodifiableList(historicoAlteracoes);
    }

    public int getQuantidadeReclassificacoes() {
        return quantidadeReclassificacoes;
    }
}

