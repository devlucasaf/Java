package application.system.condominio;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

public class Morador {

    private final long      identificador;
    private String          nome;
    private final String    cpf;
    private LocalDate       dataNascimento;
    private String          telefone;
    private String          email;
    private TipoMorador     tipo;
    private boolean         ativo;

    public Morador(long identificador, String nome, String cpf, LocalDate dataNascimento, String telefone, String email, TipoMorador tipo) {
        validarIdentificador(identificador);
        validarTexto(nome, "O nome");
        validarTexto(cpf, "O CPF");

        if (tipo == null) {
            throw new IllegalArgumentException("O tipo de morador não pode ser nulo.");
        }

        this.identificador = identificador;
        this.nome = nome.trim();
        this.cpf = cpf.trim();
        this.dataNascimento = dataNascimento;
        this.telefone = telefone == null ? "" : telefone.trim();
        this.email = email == null ? "" : email.trim();
        this.tipo = tipo;
        this.ativo = true;
    }

    // --- ATUALIZA OS DADOS DO MORADOR ---
    public void atualizar(String nome, LocalDate dataNascimento, String telefone, String email, TipoMorador tipo) {
        validarTexto(nome, "O nome");

        if (tipo == null) {
            throw new IllegalArgumentException("O tipo de morador não pode ser nulo.");
        }

        this.nome = nome.trim();
        this.dataNascimento = dataNascimento;
        this.telefone = telefone == null ? "" : telefone.trim();
        this.email = email == null ? "" : email.trim();
        this.tipo = tipo;
    }

    // --- CALCULA A IDADE DO MORADOR ---
    public int calcularIdade() {
        if (dataNascimento == null) {
            return 0;
        }

        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }

    // --- DESATIVA O MORADOR ---
    public void desativar() {
        ativo = false;
    }

    // --- REATIVA O MORADOR ---
    public void reativar() {
        ativo = true;
    }

    // --- VALIDA O IDENTIFICADOR ---
    private void validarIdentificador(long identificador) {
        if (identificador <= 0) {
            throw new IllegalArgumentException("O identificador deve ser maior que zero.");
        }
    }

    // --- VALIDA UM TEXTO OBRIGATÓRIO ---
    private void validarTexto(String texto, String campo) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new IllegalArgumentException(campo + " não pode estar vazio.");
        }
    }

    public long getIdentificador() {
        return identificador;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    public TipoMorador getTipo() {
        return tipo;
    }

    public boolean isAtivo() {
        return ativo;
    }

    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) {
            return true;
        }

        if (!(objeto instanceof Morador outroMorador)) {
            return false;
        }

        return cpf.equals(outroMorador.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpf);
    }

    @Override
    public String toString() {
        return identificador + " - " + nome + " - " + tipo.getNomeFormatado();
    }
}

