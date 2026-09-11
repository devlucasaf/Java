package application.system.condominio;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Ocorrencia {

    private final long              identificador;
    private final Unidade           unidade;
    private final Morador           registrador;
    private final TipoOcorrencia    tipo;
    private final String            descricao;
    private final LocalDateTime     dataRegistro;
    private SituacaoOcorrencia      situacao;
    private String                  respostaAdministracao;
    private LocalDateTime           dataInicioAnalise;
    private LocalDateTime           dataResolucao;
    private LocalDateTime           dataArquivamento;

    public Ocorrencia(long identificador, Unidade unidade, Morador registrador, TipoOcorrencia tipo, String descricao) {
        validarIdentificador(identificador);
        validarUnidade(unidade);
        validarRegistrador(registrador);
        validarTipo(tipo);
        validarDescricao(descricao);

        if (!unidade.possuiMorador(registrador)) {
            throw new IllegalArgumentException("O registrador não pertence à unidade informada.");
        }

        if (!registrador.isAtivo()) {
            throw new IllegalArgumentException("O registrador da ocorrência está inativo.");
        }

        this.identificador = identificador;
        this.unidade = unidade;
        this.registrador = registrador;
        this.tipo = tipo;
        this.descricao = descricao.trim();
        this.dataRegistro = LocalDateTime.now();
        this.situacao = SituacaoOcorrencia.ABERTA;
        this.respostaAdministracao = "";
        this.dataInicioAnalise = null;
        this.dataResolucao = null;
        this.dataArquivamento = null;
    }

    // --- COLOCA A OCORRÊNCIA EM ANÁLISE ---
    public void iniciarAnalise() {
        if (situacao != SituacaoOcorrencia.ABERTA) {
            throw new IllegalStateException("Somente ocorrências abertas podem entrar em análise.");
        }

        situacao = SituacaoOcorrencia.EM_ANALISE;
        dataInicioAnalise = LocalDateTime.now();
    }

    // --- RESOLVE A OCORRÊNCIA ---
    public void resolver(String respostaAdministracao) {
        if (situacao == SituacaoOcorrencia.RESOLVIDA) {
            throw new IllegalStateException("A ocorrência já foi resolvida.");
        }

        if (situacao == SituacaoOcorrencia.ARQUIVADA) {
            throw new IllegalStateException("Uma ocorrência arquivada não pode ser resolvida novamente.");
        }

        if (respostaAdministracao == null || respostaAdministracao.trim().isEmpty()) {
            throw new IllegalArgumentException("A resposta da administração não pode estar vazia.");
        }

        if (situacao == SituacaoOcorrencia.ABERTA) {
            dataInicioAnalise = LocalDateTime.now();
        }

        this.respostaAdministracao = respostaAdministracao.trim();
        this.dataResolucao = LocalDateTime.now();
        this.situacao = SituacaoOcorrencia.RESOLVIDA;
    }

    // --- ARQUIVA A OCORRÊNCIA ---
    public void arquivar() {
        if (situacao != SituacaoOcorrencia.RESOLVIDA) {
            throw new IllegalStateException("Somente ocorrências resolvidas podem ser arquivadas.");
        }

        situacao = SituacaoOcorrencia.ARQUIVADA;
        dataArquivamento = LocalDateTime.now();
    }

    // --- REABRE UMA OCORRÊNCIA RESOLVIDA OU ARQUIVADA ---
    public void reabrir(String justificativa) {
        if (situacao != SituacaoOcorrencia.RESOLVIDA && situacao != SituacaoOcorrencia.ARQUIVADA) {
            throw new IllegalStateException("Somente ocorrências resolvidas ou arquivadas podem ser reabertas.");
        }

        if (justificativa == null || justificativa.trim().isEmpty()) {
            throw new IllegalArgumentException("A justificativa da reabertura não pode estar vazia.");
        }

        respostaAdministracao = "Ocorrência reaberta. Justificativa: " + justificativa.trim();
        situacao = SituacaoOcorrencia.EM_ANALISE;
        dataInicioAnalise = LocalDateTime.now();
        dataResolucao = null;
        dataArquivamento = null;
    }

    // --- VERIFICA SE A OCORRÊNCIA ESTÁ EM ABERTO ---
    public boolean isAberta() {
        return situacao == SituacaoOcorrencia.ABERTA;
    }

    // --- VERIFICA SE A OCORRÊNCIA ESTÁ EM ANÁLISE ---
    public boolean isEmAnalise() {
        return situacao == SituacaoOcorrencia.EM_ANALISE;
    }

    // --- VERIFICA SE A OCORRÊNCIA FOI RESOLVIDA ---
    public boolean isResolvida() {
        return situacao == SituacaoOcorrencia.RESOLVIDA;
    }

    // --- VERIFICA SE A OCORRÊNCIA FOI ARQUIVADA ---
    public boolean isArquivada() {
        return situacao == SituacaoOcorrencia.ARQUIVADA;
    }

    // --- VERIFICA SE A OCORRÊNCIA ESTÁ ENCERRADA ---
    public boolean isEncerrada() {
        return situacao == SituacaoOcorrencia.RESOLVIDA || situacao == SituacaoOcorrencia.ARQUIVADA;
    }

    // --- RETORNA A DATA DE REGISTRO FORMATADA ---
    public String getDataRegistroFormatada() {
        return formatarData(dataRegistro);
    }

    // --- RETORNA A DATA DE INÍCIO DA ANÁLISE FORMATADA ---
    public String getDataInicioAnaliseFormatada() {
        return formatarData(dataInicioAnalise);
    }

    // --- RETORNA A DATA DE RESOLUÇÃO FORMATADA ---
    public String getDataResolucaoFormatada() {
        return formatarData(dataResolucao);
    }

    // --- RETORNA A DATA DE ARQUIVAMENTO FORMATADA ---
    public String getDataArquivamentoFormatada() {
        return formatarData(dataArquivamento);
    }

    // --- FORMATA UMA DATA E HORA ---
    private String formatarData(LocalDateTime data) {
        if (data == null) {
            return "Não informada";
        }

        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return data.format(formatador);
    }

    // --- VALIDA O IDENTIFICADOR DA OCORRÊNCIA ---
    private void validarIdentificador(long identificador) {
        if (identificador <= 0) {
            throw new IllegalArgumentException("O identificador deve ser maior que zero.");
        }
    }

    // --- VALIDA A UNIDADE DA OCORRÊNCIA ---
    private void validarUnidade(Unidade unidade) {
        if (unidade == null) {
            throw new IllegalArgumentException("A unidade não pode ser nula.");
        }
    }

    // --- VALIDA O MORADOR QUE REGISTROU A OCORRÊNCIA ---
    private void validarRegistrador(Morador registrador) {
        if (registrador == null) {
            throw new IllegalArgumentException("O registrador não pode ser nulo.");
        }
    }

    // --- VALIDA O TIPO DA OCORRÊNCIA ---
    private void validarTipo(TipoOcorrencia tipo) {
        if (tipo == null) {
            throw new IllegalArgumentException("O tipo da ocorrência não pode ser nulo.");
        }
    }

    // --- VALIDA A DESCRIÇÃO DA OCORRÊNCIA ---
    private void validarDescricao(String descricao) {
        if (descricao == null || descricao.trim().isEmpty()) {
            throw new IllegalArgumentException("A descrição não pode estar vazia.");
        }
    }

    public long getIdentificador() {
        return identificador;
    }

    public Unidade getUnidade() {
        return unidade;
    }

    public Morador getRegistrador() {
        return registrador;
    }

    public TipoOcorrencia getTipo() {
        return tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public LocalDateTime getDataRegistro() {
        return dataRegistro;
    }

    public SituacaoOcorrencia getSituacao() {
        return situacao;
    }

    public String getRespostaAdministracao() {
        return respostaAdministracao;
    }

    public LocalDateTime getDataInicioAnalise() {
        return dataInicioAnalise;
    }

    public LocalDateTime getDataResolucao() {
        return dataResolucao;
    }

    public LocalDateTime getDataArquivamento() {
        return dataArquivamento;
    }

    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) {
            return true;
        }

        if (!(objeto instanceof Ocorrencia outraOcorrencia)) {
            return false;
        }

        return identificador == outraOcorrencia.identificador;
    }

    @Override
    public int hashCode() {
        return Objects.hash(identificador);
    }

    @Override
    public String toString() {
        return identificador + " - " + tipo.getNomeFormatado() + " - " + unidade.getIdentificacaoCompleta() + " - " + situacao.getNomeFormatado();
    }
}

