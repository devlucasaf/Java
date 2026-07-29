package application.utilitarios.motorbusca;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        MotorBusca motor = new MotorBusca();
        motor.adicionar(new Documento("d1", "Java Concorrencia",
                "Java oferece varias primitivas para concorrencia como Thread, Runnable e BlockingQueue. " +
                "A programacao concorrente exige atencao com condicoes de corrida e deadlocks."));
        motor.adicionar(new Documento("d2", "Spring Boot",
                "Spring Boot facilita a criacao de APIs REST em Java. Utiliza inversao de controle " +
                "e injecao de dependencia para reduzir codigo boilerplate."));
        motor.adicionar(new Documento("d3", "Algoritmos de Ordenacao",
                "Algoritmos classicos de ordenacao incluem BubbleSort, MergeSort e QuickSort. " +
                "Cada um possui caracteristicas de complexidade e estabilidade diferentes."));
        motor.adicionar(new Documento("d4", "Padroes de Projeto",
                "Padroes de projeto sao solucoes reutilizaveis. Singleton, Factory, Observer e " +
                "Strategy sao alguns dos mais usados em Java."));
        motor.adicionar(new Documento("d5", "JVM Interna",
                "A JVM gerencia memoria em heap e stack. O coletor de lixo remove objetos nao usados. " +
                "Otimizacoes JIT convertem bytecode em codigo nativo em tempo de execucao."));
        motor.adicionar(new Documento("d6", "Streams API",
                "A Streams API introduzida no Java 8 permite operacoes funcionais em colecoes. " +
                "Filter, map e reduce sao operacoes comuns em pipelines de dados."));

        System.out.println("Indexados " + motor.getTotal() + " documentos, "
                + motor.getTamanhoIndice() + " termos unicos.\n");

        String[] consultasExemplo = {"java concorrencia thread", "algoritmo ordenacao", "spring api rest"};
        for (String c : consultasExemplo) {
            executarBusca(motor, c);
        }

        System.out.println("\nDigite consultas (ou 'sair'):");
        try (Scanner sc = new Scanner(System.in)) {
            while (true) {
                System.out.print("> ");
                if (!sc.hasNextLine()) break;
                String consulta = sc.nextLine().trim();
                if (consulta.equalsIgnoreCase("sair") || consulta.isEmpty()) break;
                executarBusca(motor, consulta);
            }
        }
    }

    private static void executarBusca(MotorBusca motor, String consulta) {
        System.out.println("\nBuscando: \"" + consulta + "\"");
        List<MotorBusca.Resultado> res = motor.buscar(consulta, 5);
        if (res.isEmpty()) {
            System.out.println("  (sem resultados)");
            return;
        }
        for (MotorBusca.Resultado r : res) {
            System.out.printf("  [%.4f] %s: %s%n", r.score, r.doc.getId(), r.doc.getTitulo());
        }
    }
}

