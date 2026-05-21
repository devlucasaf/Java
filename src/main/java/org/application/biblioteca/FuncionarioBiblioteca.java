package org.application.biblioteca;

public class FuncionarioBiblioteca extends UsuarioBiblioteca {
    private String cargo;

    public FuncionarioBiblioteca(String nome, String cpf, String telefone, String email, String endereco,
                                 String matricula, TipoUsuario tipo, String cargo) {
        super(nome, cpf, telefone, email, endereco, matricula, tipo);
        this.cargo = cargo;
    }

    @Override
    public int getPrazoEmprestimo() {
        return 14;
    }

    @Override
    public void exibirInformacoes() {
        super.exibirInformacoes();
        System.out.println("Cargo: " + cargo);
    }
}