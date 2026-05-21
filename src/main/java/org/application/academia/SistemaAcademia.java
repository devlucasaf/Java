package org.application.academia;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SistemaAcademia {
    public static void main(String[] args) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Criar academia
        Academia academia = new Academia("Smart Fit", "12.345.678/0001-90", "Av. Paulista, 1000");

        // Criar planos
        Plano planoMensal = new Plano(TipoPlano.MENSAL, 120.0, 1);
        planoMensal.adicionarBeneficio("Acesso livre à academia");
        planoMensal.adicionarBeneficio("Avaliação física trimestral");

        Plano planoAnual = new Plano(TipoPlano.ANUAL, 100.0, 12);
        planoAnual.adicionarBeneficio("Acesso livre");
        planoAnual.adicionarBeneficio("Avaliação física mensal");
        planoAnual.adicionarBeneficio("Desconto em loja");

        academia.adicionarPlano(planoMensal);
        academia.adicionarPlano(planoAnual);

        // Criar exercícios
        Exercicio supino = new Exercicio("Supino Reto", GrupoMuscular.PEITO, 4, 12, 40.0,
                "Deite no banco, segure a barra e desça até o peito.");
        Exercicio agachamento = new Exercicio("Agachamento", GrupoMuscular.PERNAS, 4, 10, 60.0,
                "Com barra nos ombros, desça até 90 graus.");
        Exercicio corrida = new Exercicio("Corrida na Esteira", GrupoMuscular.CARDIO, 1, 0, 0.0,
                "Corra por 20 minutos.");

        // Criar treinos
        Treino treinoForca = new Treino("Força Total", NivelTreino.INTERMEDIARIO, 60);
        treinoForca.adicionarExercicio(supino);
        treinoForca.adicionarExercicio(agachamento);
        academia.adicionarTreino(treinoForca);

        Treino treinoCardio = new Treino("Cardio Leve", NivelTreino.INICIANTE, 30);
        treinoCardio.adicionarExercicio(corrida);
        academia.adicionarTreino(treinoCardio);

        // Criar instrutor
        Instrutor instrutor = new Instrutor("Carlos Mendes", "111.222.333-44", "(11) 98765-4321",
                "carlos@academia.com", "Rua X, 123", "I001", "Instrutor", 3500.0,
                LocalDate.parse("10/01/2020", formatter), "Musculação", "08:00 às 18:00");

        // Criar recepcionista
        Recepcionista recep = new Recepcionista("Ana Paula", "555.666.777-88", "(11) 91234-5678",
                "ana@academia.com", "Rua Y, 456", "R001", "Recepcionista", 2500.0,
                LocalDate.parse("15/03/2021", formatter), "Manhã", "101");

        academia.contratarFuncionario(instrutor);
        academia.contratarFuncionario(recep);

        // Criar alunos
        Aluno aluno1 = new Aluno("João Souza", "123.456.789-00", "(11) 98888-7777",
                "joao@email.com", "Rua A, 789", "A1001", LocalDate.now(), planoAnual);
        Aluno aluno2 = new Aluno("Maria Silva", "987.654.321-11", "(11) 97777-6666",
                "maria@email.com", "Rua B, 101", "A1002", LocalDate.now(), planoMensal);

        academia.matricularAluno(aluno1);
        academia.matricularAluno(aluno2);

        // Instrutor atribui treinos
        instrutor.atribuirTreino(aluno1, treinoForca);
        instrutor.atribuirTreino(aluno2, treinoCardio);

        // Registrar pagamentos
        Pagamento pag1 = new Pagamento(aluno1, LocalDate.now().plusDays(30), planoAnual.getValorMensal(),
                FormaPagamentoAcademia.PIX);
        pag1.efetuarPagamento(LocalDate.now());
        aluno1.registrarPagamento(pag1);
        academia.registrarPagamentoAcademia(pag1);

        Pagamento pag2 = new Pagamento(aluno2, LocalDate.now().minusDays(5), planoMensal.getValorMensal(),
                FormaPagamentoAcademia.CARTAO_CREDITO);
        pag2.marcarComoAtrasado();
        aluno2.registrarPagamento(pag2);
        academia.registrarPagamentoAcademia(pag2);

        // Realizar avaliação física
        AvaliacaoFisica avaliacao1 = new AvaliacaoFisica(aluno1, LocalDate.now(), 75.5, 1.78,
                18.5, 82.0, "Iniciante, boa postura.");
        aluno1.adicionarAvaliacao(avaliacao1);

        // Exibir treinos dos alunos
        System.out.println("\n=== TREINOS DOS ALUNOS ===");
        for (Treino t : aluno1.getTreinosAtribuidos()) {
            t.exibirTreino();
        }

        // Verificar situação financeira
        aluno1.verificarStatusFinanceiro();
        aluno2.verificarStatusFinanceiro();

        // Relatórios da academia
        academia.exibirRelatorioFinanceiro();
        academia.exibirAlunosInadimplentes();
        academia.exibirResumoGeral();

        // Exibir informações detalhadas de funcionários (polimorfismo)
        System.out.println("\n=== FUNCIONÁRIOS ===");
        for (Funcionario f : academia.getFuncionarios()) {
            f.exibirInformacoes();
            System.out.println("---------------------");
        }

        // Exibir planos
        System.out.println("\n=== PLANOS DISPONÍVEIS ===");
        planoMensal.exibirInformacoes();
        planoAnual.exibirInformacoes();

        // Aumento de salário (polimorfismo - método específico)
        instrutor.aumentarSalario(10.0);
    }
}