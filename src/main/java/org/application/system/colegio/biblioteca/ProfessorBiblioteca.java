package org.application.system.colegio.biblioteca;

public class ProfessorBiblioteca extends UsuarioBiblioteca {
    private String departamento;
    private String titulacao;

    public ProfessorBiblioteca(String nome, String cpf, String telefone, String email, String endereco,
                               String matricula, TipoUsuario tipo, String departamento, String titulacao) {
        super(nome, cpf, telefone, email, endereco, matricula, tipo);
        this.departamento = departamento;
        this.titulacao = titulacao;
    }

    @Override
    public int getPrazoEmprestimo() {
        return 21;
    }

    @Override
    public void exibirInformacoes() {
        super.exibirInformacoes();
        System.out.println("Departamento: " + departamento);
        System.out.println("Titulação: " + titulacao);
    }
}