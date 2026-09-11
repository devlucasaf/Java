package application.system.condominio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Unidade {

    private final long identificador;
    private final String bloco;
    private final String numero;
    private final List<Morador> moradores;
    private int quantidadeVagas;
    private boolean ocupada;

    public Unidade(long identificador, String bloco, String numero, int quantidadeVagas) {
        validarIdentificador(identificador);
        validarTextoObrigatorio(bloco, "O bloco");
        validarTextoObrigatorio(numero, "O número da unidade");

        if (quantidadeVagas < 0) {
            throw new IllegalArgumentException("A quantidade de vagas não pode ser negativa.");
        }

        this.identificador = identificador;
        this.bloco = bloco.trim();
        this.numero = numero.trim();
        this.quantidadeVagas = quantidadeVagas;
        this.moradores = new ArrayList<>();
        this.ocupada = false;
    }

    // --- ADICIONA UM MORADOR À UNIDADE ---
    public boolean adicionarMorador(Morador morador) {
        if (morador == null) {
            throw new IllegalArgumentException("O morador não pode ser nulo.");
        }

        if (moradores.contains(morador) || buscarMoradorPorCpf(morador.getCpf()) != null) {
            return false;
        }

        moradores.add(morador);
        atualizarSituacaoOcupacao();
        return true;
    }

    // --- REMOVE UM MORADOR DA UNIDADE ---
    public boolean removerMorador(Morador morador) {
        if (morador == null) {
            return false;
        }

        boolean removido = moradores.remove(morador);
        atualizarSituacaoOcupacao();
        return removido;
    }

    // --- REMOVE UM MORADOR PELO CPF ---
    public Morador removerMoradorPorCpf(String cpf) {
        Morador morador = buscarMoradorPorCpf(cpf);

        if (morador == null) {
            return null;
        }

        moradores.remove(morador);
        atualizarSituacaoOcupacao();
        return morador;
    }

    // --- REMOVE UM MORADOR PELO IDENTIFICADOR ---
    public Morador removerMoradorPorIdentificador(long identificadorMorador) {
        Morador morador = buscarMoradorPorIdentificador(identificadorMorador);

        if (morador == null) {
            return null;
        }

        moradores.remove(morador);
        atualizarSituacaoOcupacao();
        return morador;
    }

    // --- BUSCA UM MORADOR PELO CPF ---
    public Morador buscarMoradorPorCpf(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            return null;
        }

        for (Morador morador : moradores) {
            if (morador.getCpf().equals(cpf.trim())) {
                return morador;
            }
        }

        return null;
    }

    // --- BUSCA UM MORADOR PELO IDENTIFICADOR ---
    public Morador buscarMoradorPorIdentificador(long identificadorMorador) {
        if (identificadorMorador <= 0) {
            return null;
        }

        for (Morador morador : moradores) {
            if (morador.getIdentificador() == identificadorMorador) {
                return morador;
            }
        }

        return null;
    }

    // --- VERIFICA SE A UNIDADE POSSUI O MORADOR INFORMADO ---
    public boolean possuiMorador(Morador morador) {
        return morador != null && moradores.contains(morador);
    }

    // --- VERIFICA SE A UNIDADE POSSUI UM MORADOR COM O CPF INFORMADO ---
    public boolean possuiMoradorPorCpf(String cpf) {
        return buscarMoradorPorCpf(cpf) != null;
    }

    // --- RETORNA O RESPONSÁVEL PRINCIPAL PELA UNIDADE ---
    public Morador getResponsavel() {
        for (Morador morador : moradores) {
            if (morador.isAtivo() && morador.getTipo() == TipoMorador.PROPRIETARIO) {
                return morador;
            }
        }

        for (Morador morador : moradores) {
            if (morador.isAtivo() && morador.getTipo() == TipoMorador.INQUILINO) {
                return morador;
            }
        }

        for (Morador morador : moradores) {
            if (morador.isAtivo()) {
                return morador;
            }
        }

        return null;
    }

    // --- RETORNA OS MORADORES ATIVOS DA UNIDADE ---
    public List<Morador> getMoradoresAtivos() {
        List<Morador> moradoresAtivos = new ArrayList<>();

        for (Morador morador : moradores) {
            if (morador.isAtivo()) {
                moradoresAtivos.add(morador);
            }
        }

        return Collections.unmodifiableList(moradoresAtivos);
    }

    // --- RETORNA OS PROPRIETÁRIOS DA UNIDADE ---
    public List<Morador> getProprietarios() {
        return buscarMoradoresPorTipo(TipoMorador.PROPRIETARIO);
    }

    // --- RETORNA OS INQUILINOS DA UNIDADE ---
    public List<Morador> getInquilinos() {
        return buscarMoradoresPorTipo(TipoMorador.INQUILINO);
    }

    // --- RETORNA OS DEPENDENTES DA UNIDADE ---
    public List<Morador> getDependentes() {
        return buscarMoradoresPorTipo(TipoMorador.DEPENDENTE);
    }

    // --- BUSCA MORADORES PELO TIPO ---
    public List<Morador> buscarMoradoresPorTipo(TipoMorador tipoMorador) {
        if (tipoMorador == null) {
            throw new IllegalArgumentException("O tipo de morador não pode ser nulo.");
        }

        List<Morador> moradoresEncontrados = new ArrayList<>();

        for (Morador morador : moradores) {
            if (morador.getTipo() == tipoMorador) {
                moradoresEncontrados.add(morador);
            }
        }

        return Collections.unmodifiableList(moradoresEncontrados);
    }

    // --- VERIFICA SE EXISTE UM PROPRIETÁRIO ATIVO NA UNIDADE ---
    public boolean possuiProprietarioAtivo() {
        for (Morador morador : moradores) {
            if (morador.isAtivo() && morador.getTipo() == TipoMorador.PROPRIETARIO) {
                return true;
            }
        }

        return false;
    }

    // --- VERIFICA SE EXISTE UM INQUILINO ATIVO NA UNIDADE ---
    public boolean possuiInquilinoAtivo() {
        for (Morador morador : moradores) {
            if (morador.isAtivo() && morador.getTipo() == TipoMorador.INQUILINO) {
                return true;
            }
        }

        return false;
    }

    // --- ATUALIZA A SITUAÇÃO DE OCUPAÇÃO DA UNIDADE ---
    private void atualizarSituacaoOcupacao() {
        ocupada = !moradores.isEmpty();
    }

    // --- VALIDA O IDENTIFICADOR DA UNIDADE ---
    private void validarIdentificador(long identificador) {
        if (identificador <= 0) {
            throw new IllegalArgumentException("O identificador deve ser maior que zero.");
        }
    }

    // --- VALIDA UM TEXTO OBRIGATÓRIO ---
    private void validarTextoObrigatorio(String texto, String campo) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new IllegalArgumentException(campo + " não pode estar vazio.");
        }
    }

    public long getIdentificador() {
        return identificador;
    }

    public String getBloco() {
        return bloco;
    }

    public String getNumero() {
        return numero;
    }

    public int getQuantidadeVagas() {
        return quantidadeVagas;
    }

    // --- ALTERA A QUANTIDADE DE VAGAS DA UNIDADE ---
    public void setQuantidadeVagas(int quantidadeVagas) {
        if (quantidadeVagas < 0) {
            throw new IllegalArgumentException("A quantidade de vagas não pode ser negativa.");
        }

        this.quantidadeVagas = quantidadeVagas;
    }

    // --- ADICIONA UMA VAGA À UNIDADE ---
    public void adicionarVaga() {
        quantidadeVagas++;
    }

    // --- REMOVE UMA VAGA DA UNIDADE ---
    public void removerVaga() {
        if (quantidadeVagas == 0) {
            throw new IllegalStateException("A unidade não possui vagas para remover.");
        }

        quantidadeVagas--;
    }

    public boolean isOcupada() {
        return ocupada;
    }

    public List<Morador> getMoradores() {
        return Collections.unmodifiableList(moradores);
    }

    public int getQuantidadeMoradores() {
        return moradores.size();
    }

    public int getQuantidadeMoradoresAtivos() {
        int quantidade = 0;

        for (Morador morador : moradores) {
            if (morador.isAtivo()) {
                quantidade++;
            }
        }

        return quantidade;
    }

    // --- RETORNA A IDENTIFICAÇÃO COMPLETA DA UNIDADE ---
    public String getIdentificacaoCompleta() {
        return "Bloco " + bloco + ", unidade " + numero;
    }

    // --- RETORNA A SITUAÇÃO FORMATADA DA UNIDADE ---
    public String getSituacaoFormatada() {
        return ocupada ? "Ocupada" : "Desocupada";
    }

    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) {
            return true;
        }

        if (!(objeto instanceof Unidade outraUnidade)) {
            return false;
        }

        return bloco.equalsIgnoreCase(outraUnidade.bloco) && numero.equalsIgnoreCase(outraUnidade.numero);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bloco.toLowerCase(), numero.toLowerCase());
    }

    @Override
    public String toString() {
        return identificador + " - " + getIdentificacaoCompleta() + " - " + getSituacaoFormatada() + " - moradores: " + moradores.size() + " - vagas: " + quantidadeVagas;
    }
}

