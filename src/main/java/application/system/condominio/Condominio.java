package application.system.condominio;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Condominio {

    private final String nome;
    private final String endereco;
    private final List<Morador> moradores;
    private final List<Unidade> unidades;
    private final List<AreaComum> areasComuns;
    private final List<Reserva> reservas;
    private final List<Boleto> boletos;
    private final List<Ocorrencia> ocorrencias;
    private final List<Multa> multas;
    private long proximoMorador;
    private long proximaUnidade;
    private long proximaArea;
    private long proximaReserva;
    private long proximoBoleto;
    private long proximaOcorrencia;
    private long proximaMulta;

    public Condominio(String nome, String endereco) {
        if (nome == null || nome.trim().isEmpty() || endereco == null || endereco.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome e o endereço devem ser informados.");
        }

        this.nome = nome.trim();
        this.endereco = endereco.trim();
        this.moradores = new ArrayList<>();
        this.unidades = new ArrayList<>();
        this.areasComuns = new ArrayList<>();
        this.reservas = new ArrayList<>();
        this.boletos = new ArrayList<>();
        this.ocorrencias = new ArrayList<>();
        this.multas = new ArrayList<>();
        this.proximoMorador = 1;
        this.proximaUnidade = 1;
        this.proximaArea = 1;
        this.proximaReserva = 1;
        this.proximoBoleto = 1;
        this.proximaOcorrencia = 1;
        this.proximaMulta = 1;
    }

    // --- CADASTRA UMA UNIDADE ---
    public Unidade cadastrarUnidade(String bloco, String numero, int quantidadeVagas) {
        if (buscarUnidade(bloco, numero) != null) {
            throw new IllegalArgumentException("A unidade já está cadastrada.");
        }

        Unidade unidade = new Unidade(proximaUnidade++, bloco, numero, quantidadeVagas);
        unidades.add(unidade);
        return unidade;
    }

    // --- CADASTRA UM MORADOR ---
    public Morador cadastrarMorador(Unidade unidade, String nome, String cpf, LocalDate dataNascimento, String telefone, String email, TipoMorador tipo) {
        validarUnidade(unidade);

        if (buscarMoradorPorCpf(cpf) != null) {
            throw new IllegalArgumentException("Já existe um morador com esse CPF.");
        }

        Morador morador = new Morador(proximoMorador++, nome, cpf, dataNascimento, telefone, email, tipo);
        moradores.add(morador);
        unidade.adicionarMorador(morador);
        return morador;
    }

    // --- CADASTRA UMA ÁREA COMUM ---
    public AreaComum cadastrarAreaComum(String nome, int capacidade, BigDecimal valorReserva, LocalTime abertura, LocalTime fechamento) {
        AreaComum area = new AreaComum(proximaArea++, nome, capacidade, valorReserva, abertura, fechamento);
        areasComuns.add(area);
        return area;
    }

    // --- REALIZA UMA RESERVA ---
    public Reserva reservarArea(AreaComum area, Unidade unidade, Morador responsavel, LocalDate data, LocalTime inicio, LocalTime fim) {
        validarArea(area);
        validarUnidade(unidade);

        if (!area.isAtiva()) {
            throw new IllegalStateException("A área comum está inativa.");
        }

        if (possuiDebitoVencido(unidade)) {
            throw new IllegalStateException("A unidade possui débitos vencidos e não pode realizar reservas.");
        }

        Reserva novaReserva = new Reserva(proximaReserva, area, unidade, responsavel, data, inicio, fim);

        for (Reserva reserva : reservas) {
            if (novaReserva.conflitaCom(reserva)) {
                throw new IllegalStateException("A área já está reservada no período informado.");
            }
        }

        proximaReserva++;
        reservas.add(novaReserva);

        if (area.getValorReserva().signum() > 0) {
            emitirBoleto(unidade, "Reserva da área " + area.getNome(), area.getValorReserva(), LocalDate.now().plusDays(5));
        }

        return novaReserva;
    }

    // --- EMITE UM BOLETO ---
    public Boleto emitirBoleto(Unidade unidade, String descricao, BigDecimal valor, LocalDate vencimento) {
        validarUnidade(unidade);

        Boleto boleto = new Boleto(proximoBoleto++, unidade, descricao, valor, vencimento);
        boletos.add(boleto);
        return boleto;
    }

    // --- REGISTRA UMA OCORRÊNCIA ---
    public Ocorrencia registrarOcorrencia(Unidade unidade, Morador registrador, TipoOcorrencia tipo, String descricao) {
        validarUnidade(unidade);

        Ocorrencia ocorrencia = new Ocorrencia(proximaOcorrencia++, unidade, registrador, tipo, descricao);
        ocorrencias.add(ocorrencia);
        return ocorrencia;
    }

    // --- APLICA UMA MULTA E EMITE O BOLETO CORRESPONDENTE ---
    public Multa aplicarMulta(Unidade unidade, Ocorrencia ocorrencia, String motivo, BigDecimal valor, LocalDate vencimento) {
        validarUnidade(unidade);

        if (ocorrencia != null && ocorrencia.getUnidade() != unidade) {
            throw new IllegalArgumentException("A ocorrência não pertence à unidade informada.");
        }

        Multa multa = new Multa(proximaMulta++, unidade, ocorrencia, motivo, valor, vencimento);
        multas.add(multa);
        emitirBoleto(unidade, "Multa: " + motivo, valor, vencimento);
        return multa;
    }

    // --- VERIFICA SE A UNIDADE POSSUI DÉBITOS VENCIDOS ---
    public boolean possuiDebitoVencido(Unidade unidade) {
        validarUnidade(unidade);

        for (Boleto boleto : boletos) {
            if (boleto.getUnidade().equals(unidade) && boleto.getSituacao() == SituacaoBoleto.VENCIDO) {
                return true;
            }
        }

        return false;
    }

    // --- CALCULA O TOTAL EM ABERTO DE UMA UNIDADE ---
    public BigDecimal calcularTotalEmAberto(Unidade unidade) {
        validarUnidade(unidade);

        BigDecimal total = BigDecimal.ZERO;

        for (Boleto boleto : boletos) {
            if (boleto.getUnidade().equals(unidade) && boleto.getSituacao() != SituacaoBoleto.PAGO && boleto.getSituacao() != SituacaoBoleto.CANCELADO) {
                total = total.add(boleto.calcularValorAtualizado(LocalDate.now()));
            }
        }

        return total;
    }

    // --- BUSCA UMA UNIDADE ---
    public Unidade buscarUnidade(String bloco, String numero) {
        if (bloco == null || numero == null) {
            return null;
        }

        for (Unidade unidade : unidades) {
            if (unidade.getBloco().equalsIgnoreCase(bloco.trim()) && unidade.getNumero().equalsIgnoreCase(numero.trim())) {
                return unidade;
            }
        }

        return null;
    }

    // --- BUSCA UM MORADOR PELO CPF ---
    public Morador buscarMoradorPorCpf(String cpf) {
        if (cpf == null) {
            return null;
        }

        for (Morador morador : moradores) {
            if (morador.getCpf().equals(cpf.trim())) {
                return morador;
            }
        }

        return null;
    }

    // --- LISTA AS RESERVAS ATIVAS EM ORDEM DE DATA ---
    public List<Reserva> listarReservasAtivas() {
        return reservas.stream().filter(reserva -> !reserva.isCancelada()).sorted(Comparator.comparing(Reserva::getData).thenComparing(Reserva::getHorarioInicio)).toList();
    }

    // --- LISTA OS BOLETOS DE UMA UNIDADE ---
    public List<Boleto> listarBoletos(Unidade unidade) {
        validarUnidade(unidade);
        return boletos.stream().filter(boleto -> boleto.getUnidade().equals(unidade)).toList();
    }

    // --- LISTA AS OCORRÊNCIAS DE UMA UNIDADE ---
    public List<Ocorrencia> listarOcorrencias(Unidade unidade) {
        validarUnidade(unidade);
        return ocorrencias.stream().filter(ocorrencia -> ocorrencia.getUnidade().equals(unidade)).toList();
    }

    // --- VALIDA UMA UNIDADE ---
    private void validarUnidade(Unidade unidade) {
        if (unidade == null || !unidades.contains(unidade)) {
            throw new IllegalArgumentException("A unidade não pertence ao condomínio.");
        }
    }

    // --- VALIDA UMA ÁREA COMUM ---
    private void validarArea(AreaComum area) {
        if (area == null || !areasComuns.contains(area)) {
            throw new IllegalArgumentException("A área comum não pertence ao condomínio.");
        }
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public List<Morador> getMoradores() {
        return List.copyOf(moradores);
    }

    public List<Unidade> getUnidades() {
        return List.copyOf(unidades);
    }

    public List<AreaComum> getAreasComuns() {
        return List.copyOf(areasComuns);
    }

    public List<Reserva> getReservas() {
        return List.copyOf(reservas);
    }

    public List<Boleto> getBoletos() {
        return List.copyOf(boletos);
    }

    public List<Ocorrencia> getOcorrencias() {
        return List.copyOf(ocorrencias);
    }

    public List<Multa> getMultas() {
        return List.copyOf(multas);
    }
}

