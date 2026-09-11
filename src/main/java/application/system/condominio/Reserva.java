package application.system.condominio;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Reserva {

    private final long identificador;
    private final AreaComum areaComum;
    private final Unidade unidade;
    private final Morador responsavel;
    private final LocalDate data;
    private final LocalTime horarioInicio;
    private final LocalTime horarioFim;
    private final LocalDateTime dataSolicitacao;
    private boolean cancelada;
    private LocalDateTime dataCancelamento;
    private String motivoCancelamento;

    public Reserva(long identificador, AreaComum areaComum, Unidade unidade, Morador responsavel, LocalDate data, LocalTime horarioInicio, LocalTime horarioFim) {
        validarIdentificador(identificador);
        validarDadosObrigatorios(areaComum, unidade, responsavel);
        validarResponsavel(unidade, responsavel);
        validarData(data);
        validarHorario(areaComum, horarioInicio, horarioFim);

        this.identificador = identificador;
        this.areaComum = areaComum;
        this.unidade = unidade;
        this.responsavel = responsavel;
        this.data = data;
        this.horarioInicio = horarioInicio;
        this.horarioFim = horarioFim;
        this.dataSolicitacao = LocalDateTime.now();
        this.cancelada = false;
        this.dataCancelamento = null;
        this.motivoCancelamento = "";
    }

    // --- VERIFICA SE EXISTE CONFLITO COM OUTRA RESERVA ---
    public boolean conflitaCom(Reserva outraReserva) {
        if (outraReserva == null || cancelada || outraReserva.cancelada) {
            return false;
        }

        if (!areaComum.equals(outraReserva.areaComum) || !data.equals(outraReserva.data)) {
            return false;
        }

        return horarioInicio.isBefore(outraReserva.horarioFim) && horarioFim.isAfter(outraReserva.horarioInicio);
    }

    // --- VERIFICA SE A RESERVA PERTENCE AO PERÍODO INFORMADO ---
    public boolean pertenceAoPeriodo(LocalDate dataInicial, LocalDate dataFinal) {
        if (dataInicial == null || dataFinal == null || dataInicial.isAfter(dataFinal)) {
            throw new IllegalArgumentException("O período informado é inválido.");
        }

        return !data.isBefore(dataInicial) && !data.isAfter(dataFinal);
    }

    // --- VERIFICA SE A RESERVA ESTÁ ATIVA ---
    public boolean isAtiva() {
        return !cancelada;
    }

    // --- VERIFICA SE A RESERVA JÁ ACONTECEU ---
    public boolean isFinalizada() {
        LocalDateTime terminoReserva = LocalDateTime.of(data, horarioFim);
        return !cancelada && terminoReserva.isBefore(LocalDateTime.now());
    }

    // --- VERIFICA SE A RESERVA ESTÁ ACONTECENDO NESTE MOMENTO ---
    public boolean isEmAndamento() {
        if (cancelada) {
            return false;
        }

        LocalDateTime agora = LocalDateTime.now();
        LocalDateTime inicioReserva = LocalDateTime.of(data, horarioInicio);
        LocalDateTime fimReserva = LocalDateTime.of(data, horarioFim);

        return !agora.isBefore(inicioReserva) && agora.isBefore(fimReserva);
    }

    // --- VERIFICA SE A RESERVA AINDA ACONTECERÁ ---
    public boolean isFutura() {
        LocalDateTime inicioReserva = LocalDateTime.of(data, horarioInicio);
        return !cancelada && inicioReserva.isAfter(LocalDateTime.now());
    }

    // --- CANCELA A RESERVA SEM INFORMAR MOTIVO ---
    public void cancelar() {
        cancelar("Motivo não informado.");
    }

    // --- CANCELA A RESERVA E REGISTRA O MOTIVO ---
    public void cancelar(String motivo) {
        if (cancelada) {
            throw new IllegalStateException("A reserva já está cancelada.");
        }

        if (isFinalizada()) {
            throw new IllegalStateException("Uma reserva finalizada não pode ser cancelada.");
        }

        this.cancelada = true;
        this.dataCancelamento = LocalDateTime.now();
        this.motivoCancelamento = motivo == null || motivo.trim().isEmpty() ? "Motivo não informado." : motivo.trim();
    }

    // --- CALCULA A DURAÇÃO DA RESERVA EM MINUTOS ---
    public long calcularDuracaoMinutos() {
        return java.time.Duration.between(horarioInicio, horarioFim).toMinutes();
    }

    // --- RETORNA A SITUAÇÃO FORMATADA DA RESERVA ---
    public String getSituacaoFormatada() {
        if (cancelada) {
            return "Cancelada";
        }

        if (isEmAndamento()) {
            return "Em andamento";
        }

        if (isFinalizada()) {
            return "Finalizada";
        }

        return "Agendada";
    }

    // --- RETORNA A DATA E O HORÁRIO FORMATADOS ---
    public String getPeriodoFormatado() {
        DateTimeFormatter formatadorData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatadorHorario = DateTimeFormatter.ofPattern("HH:mm");
        return data.format(formatadorData) + " das " + horarioInicio.format(formatadorHorario) + " às " + horarioFim.format(formatadorHorario);
    }

    // --- VALIDA O IDENTIFICADOR DA RESERVA ---
    private void validarIdentificador(long identificador) {
        if (identificador <= 0) {
            throw new IllegalArgumentException("O identificador deve ser maior que zero.");
        }
    }

    // --- VALIDA OS OBJETOS OBRIGATÓRIOS DA RESERVA ---
    private void validarDadosObrigatorios(AreaComum areaComum, Unidade unidade, Morador responsavel) {
        if (areaComum == null) {
            throw new IllegalArgumentException("A área comum deve ser informada.");
        }

        if (unidade == null) {
            throw new IllegalArgumentException("A unidade deve ser informada.");
        }

        if (responsavel == null) {
            throw new IllegalArgumentException("O responsável deve ser informado.");
        }
    }

    // --- VALIDA O RESPONSÁVEL PELA RESERVA ---
    private void validarResponsavel(Unidade unidade, Morador responsavel) {
        if (!responsavel.isAtivo()) {
            throw new IllegalArgumentException("O responsável pela reserva está inativo.");
        }

        if (!unidade.possuiMorador(responsavel)) {
            throw new IllegalArgumentException("O responsável não pertence à unidade informada.");
        }
    }

    // --- VALIDA A DATA DA RESERVA ---
    private void validarData(LocalDate data) {
        if (data == null) {
            throw new IllegalArgumentException("A data da reserva deve ser informada.");
        }

        if (data.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("A data da reserva não pode estar no passado.");
        }
    }

    // --- VALIDA O HORÁRIO DA RESERVA ---
    private void validarHorario(AreaComum areaComum, LocalTime horarioInicio, LocalTime horarioFim) {
        if (horarioInicio == null || horarioFim == null) {
            throw new IllegalArgumentException("Os horários de início e término devem ser informados.");
        }

        if (!horarioInicio.isBefore(horarioFim)) {
            throw new IllegalArgumentException("O horário inicial deve ser anterior ao horário final.");
        }

        if (!areaComum.aceitaHorario(horarioInicio, horarioFim)) {
            throw new IllegalArgumentException("O horário está fora do funcionamento da área.");
        }
    }

    public long getIdentificador() {
        return identificador;
    }

    public AreaComum getAreaComum() {
        return areaComum;
    }

    public Unidade getUnidade() {
        return unidade;
    }

    public Morador getResponsavel() {
        return responsavel;
    }

    public LocalDate getData() {
        return data;
    }

    public LocalTime getHorarioInicio() {
        return horarioInicio;
    }

    public LocalTime getHorarioFim() {
        return horarioFim;
    }

    public LocalDateTime getDataSolicitacao() {
        return dataSolicitacao;
    }

    public boolean isCancelada() {
        return cancelada;
    }

    public LocalDateTime getDataCancelamento() {
        return dataCancelamento;
    }

    public String getMotivoCancelamento() {
        return motivoCancelamento;
    }

    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) {
            return true;
        }

        if (!(objeto instanceof Reserva outraReserva)) {
            return false;
        }

        return identificador == outraReserva.identificador;
    }

    @Override
    public int hashCode() {
        return Objects.hash(identificador);
    }

    @Override
    public String toString() {
        return identificador + " - " + areaComum.getNome() + " - " + unidade.getIdentificacaoCompleta() + " - " + getPeriodoFormatado() + " - " + getSituacaoFormatada();
    }
}

