package application.system.colegio.biblioteca.model;

public abstract class Pessoa {
    protected String nome;
    protected String cpf;
    protected String telefone;
    protected String email;
    protected String endereco;

    public Pessoa(String nome, String cpf, String telefone, String email, String endereco) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
        this.endereco = endereco;
    }

    public abstract void exibirInformacoes();

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }
}
