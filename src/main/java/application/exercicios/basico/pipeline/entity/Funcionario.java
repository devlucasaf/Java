package application.exercicios.basico.pipeline.entity;

public class Funcionario {
    private String nome;
    private String email;
    private Double salario;

    public Funcionario(String nome, String email, Double salario) {
        this.nome = nome;
        this.email = email;
        this.salario = salario;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public Double getSalario() {
        return salario;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }
}
