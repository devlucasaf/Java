package org.application.system.colegio.biblioteca;

public class AlunoBiblioteca extends UsuarioBiblioteca {
    private String  curso;
    private int     periodo;

    public AlunoBiblioteca(String nome, String cpf, String telefone, String email, String endereco,
                           String matricula, TipoUsuario tipo, String curso, int periodo) {
        super(nome, cpf, telefone, email, endereco, matricula, tipo);
        this.curso = curso;
        this.periodo = periodo;
    }

    @Override
    public int getPrazoEmprestimo() {
        return 7;
    }

    @Override
    public void exibirInformacoes() {
        super.exibirInformacoes();
        System.out.println("Curso: " + curso);
        System.out.println("Período: " + periodo);
    }
}