package org.application.system.colegio.escola;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SistemaEscolar {
    public static void main(String[] args) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Criar escola
        Escola escola = new Escola("Escola Municipal Dom Pedro", "12.345.678/0001-90", "Rua das Flores, 123, Centro");

        // Criar disciplinas
        Disciplina matematica = new Disciplina("Matemática", "MAT101", 80, 6.0);
        Disciplina portugues = new Disciplina("Português", "POR202", 80, 6.0);
        Disciplina historia = new Disciplina("História", "HIS303", 60, 5.0);
        escola.adicionarDisciplina(matematica);
        escola.adicionarDisciplina(portugues);
        escola.adicionarDisciplina(historia);

        // Criar professores
        Professor profJoao = new Professor("João Carlos", "111.222.333-44", LocalDate.parse("15/05/1975", formatter),
                "(11) 98765-4321", "joao@escola.com", "P001", "Matemática", 5500.0);
        Professor profMaria = new Professor("Maria Aparecida", "555.666.777-88", LocalDate.parse("22/08/1980", formatter),
                "(11) 91234-5678", "maria@escola.com", "P002", "Português", 5800.0);
        escola.contratarProfessor(profJoao);
        escola.contratarProfessor(profMaria);

        // Atribuir disciplinas aos professores
        profJoao.atribuirDisciplina(matematica);
        profMaria.atribuirDisciplina(portugues);

        // Criar turmas
        Turma turma6A = new Turma("6º Ano A", Turno.MANHA, NivelEnsino.ENSINO_FUNDAMENTAL, 2025);
        escola.adicionarTurma(turma6A);
        turma6A.adicionarDisciplina(matematica);
        turma6A.adicionarDisciplina(portugues);
        turma6A.adicionarDisciplina(historia);

        // Criar alunos
        Aluno aluno1 = new Aluno("Pedro Santos", "123.456.789-00", LocalDate.parse("10/03/2012", formatter),
                "(11) 99888-7777", "pedro@email.com", "A1001", LocalDate.now());
        Aluno aluno2 = new Aluno("Ana Souza", "987.654.321-11", LocalDate.parse("25/07/2011", formatter),
                "(11) 94444-5555", "ana@email.com", "A1002", LocalDate.now());
        Aluno aluno3 = new Aluno("Lucas Lima", "555.444.333-22", LocalDate.parse("03/12/2012", formatter),
                "(11) 97777-6666", "lucas@email.com", "A1003", LocalDate.now());

        // Matricular alunos na escola e na turma
        escola.matricularAluno(aluno1, turma6A);
        escola.matricularAluno(aluno2, turma6A);
        escola.matricularAluno(aluno3, turma6A);

        // Matricular alunos nas disciplinas da turma (simplificado)
        matematica.matricularAluno(aluno1);
        matematica.matricularAluno(aluno2);
        portugues.matricularAluno(aluno1);
        portugues.matricularAluno(aluno2);
        historia.matricularAluno(aluno1);
        historia.matricularAluno(aluno2);
        historia.matricularAluno(aluno3);

        // Definir frequência (exemplo)
        aluno1.setFrequenciaPercentual(85.0);
        aluno2.setFrequenciaPercentual(90.0);
        aluno3.setFrequenciaPercentual(60.0); // reprovado por falta

        // Professores lançam notas
        profJoao.lancarNota(aluno1, matematica, 7.5);
        profJoao.lancarNota(aluno1, matematica, 8.0);
        profJoao.lancarNota(aluno2, matematica, 5.0);
        profJoao.lancarNota(aluno2, matematica, 6.0);
        profMaria.lancarNota(aluno1, portugues, 9.0);
        profMaria.lancarNota(aluno1, portugues, 8.5);
        profMaria.lancarNota(aluno2, portugues, 4.0);
        profMaria.lancarNota(aluno2, portugues, 5.5);

        // Verificar aprovação
        System.out.println("\n--- VERIFICAÇÃO DE APROVAÇÃO ---");
        aluno1.verificarAprovacao(matematica, matematica.getMediaMinimaAprovacao());
        aluno1.verificarAprovacao(portugues, portugues.getMediaMinimaAprovacao());
        aluno2.verificarAprovacao(matematica, matematica.getMediaMinimaAprovacao());
        aluno2.verificarAprovacao(portugues, portugues.getMediaMinimaAprovacao());
        aluno3.verificarAprovacao(historia, historia.getMediaMinimaAprovacao()); // sem notas

        // Exibir boletins
        aluno1.exibirBoletim();
        aluno2.exibirBoletim();

        // Funcionário administrativo
        Funcionario secretaria = new Funcionario("Carla Oliveira", "222.333.444-55",
                LocalDate.parse("12/12/1985", formatter), "(11) 95555-8888", "carla@escola.com",
                "F001", "Secretária", 3200.0, LocalDate.now());
        escola.contratarFuncionario(secretaria);
        secretaria.promover("Coordenadora Administrativa", 10.0);

        // Exibir relatório geral da escola
        escola.exibirRelatorioGeral();
        escola.listarTodosAlunos();

        // Exibir informações da turma
        turma6A.listarAlunos();
        turma6A.exibirHorario();

        // Exibir informações de professores
        profJoao.exibirInformacoes();
        profMaria.exibirInformacoes();

        // Demonstração de polimorfismo (todos são Pessoas)
        System.out.println("\n--- POLIMORFISMO: LISTA DE PESSOAS ---");
        List<Pessoa> pessoas = new ArrayList<>();
        pessoas.add(aluno1);
        pessoas.add(aluno2);
        pessoas.add(profJoao);
        pessoas.add(secretaria);
        for (Pessoa p : pessoas) {
            System.out.println(p.getNome() + " - Idade: " + p.calcularIdade() + " anos");
            p.exibirInformacoes(); // polimorfismo em ação
            System.out.println("----------------------------");
        }
    }
}
