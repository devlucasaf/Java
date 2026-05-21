package org.application.biblioteca;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SistemaBiblioteca {
    public static void main(String[] args) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Criar biblioteca
        Biblioteca biblioteca = new Biblioteca("Biblioteca Central", "12.345.678/0001-90", "Rua da Educação, 123");

        // Criar publicações
        Livro livro1 = new Livro("Dom Casmurro", "Editora Globo", LocalDate.parse("15/03/1899", formatter), 3,
                "Machado de Assis", "978-85-1234-567-8", 256, CategoriaLivro.FICCAO);
        Livro livro2 = new Livro("O Guia do Mochileiro das Galáxias", "Arqueiro", LocalDate.parse("12/10/1979", formatter), 2,
                "Douglas Adams", "978-85-6543-210-9", 208, CategoriaLivro.FICCAO);
        Revista revista1 = new Revista("Superinteressante", "Abril", LocalDate.parse("01/12/2024", formatter), 5,
                325, "Mensal");
        DVD dvd1 = new DVD("Interestelar", "Warner", LocalDate.parse("05/11/2014", formatter), 2,
                "Christopher Nolan", 169, "Ficção Científica");

        biblioteca.adicionarPublicacao(livro1);
        biblioteca.adicionarPublicacao(livro2);
        biblioteca.adicionarPublicacao(revista1);
        biblioteca.adicionarPublicacao(dvd1);

        // Criar usuários
        AlunoBiblioteca aluno = new AlunoBiblioteca("João Silva", "123.456.789-00", "(11) 98765-4321",
                "joao@email.com", "Rua A, 123", "A1001", TipoUsuario.ALUNO, "Ciência da Computação", 5);
        ProfessorBiblioteca professor = new ProfessorBiblioteca("Maria Oliveira", "987.654.321-11", "(11) 91234-5678",
                "maria@email.com", "Rua B, 456", "P2001", TipoUsuario.PROFESSOR, "Física", "Doutor");
        FuncionarioBiblioteca funcionario = new FuncionarioBiblioteca("Carlos Santos", "555.444.333-22", "(11) 97777-8888",
                "carlos@email.com", "Rua C, 789", "F3001", TipoUsuario.FUNCIONARIO, "Auxiliar");

        biblioteca.cadastrarUsuario(aluno);
        biblioteca.cadastrarUsuario(professor);
        biblioteca.cadastrarUsuario(funcionario);

        // Realizar empréstimos
        System.out.println("\n--- REALIZANDO EMPRÉSTIMOS ---");
        biblioteca.realizarEmprestimo("A1001", "Dom Casmurro", LocalDate.now());
        biblioteca.realizarEmprestimo("P2001", "Interestelar", LocalDate.now());

        // Tentar empréstimo com publicação indisponível
        biblioteca.realizarEmprestimo("A1001", "Dom Casmurro", LocalDate.now()); // só tem 1 disponível? na verdade 3, mas só vai pegar outro exemplar

        // Realizar reserva
        biblioteca.realizarReserva("F3001", "Dom Casmurro", LocalDate.now());

        // Devolução com atraso (simular data passada)
        System.out.println("\n--- DEVOLUÇÃO COM ATRASO ---");
        LocalDate dataAtraso = LocalDate.now().plusDays(10);
        biblioteca.realizarDevolucao("A1001", "Dom Casmurro", dataAtraso);

        // Exibir relatórios
        biblioteca.exibirAcervoCompleto();
        biblioteca.exibirEmprestimosAtivos();
        biblioteca.exibirUsuariosComMulta();
        biblioteca.exibirReservasAtivas();

        // Pagamento de multa
        System.out.println("\n--- PAGAMENTO DE MULTA ---");
        aluno.pagarMulta(6.0); // 3 dias de atraso * 2 = 6
        biblioteca.exibirUsuariosComMulta();

        // Polimorfismo: todos os usuários são pessoas
        System.out.println("\n=== POLIMORFISMO: LISTA DE USUÁRIOS ===");
        List<UsuarioBiblioteca> usuarios = new ArrayList<>();
        usuarios.add(aluno);
        usuarios.add(professor);
        usuarios.add(funcionario);

        for (UsuarioBiblioteca u : usuarios) {
            u.exibirInformacoes();
            System.out.println("Prazo de empréstimo: " + u.getPrazoEmprestimo() + " dias");
            System.out.println("-----------------------------");
        }
    }
}