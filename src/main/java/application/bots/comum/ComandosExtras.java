package application.bots.comum;

import games.tabuleiro.velha.TabuleiroVelha;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class ComandosExtras {

    private static final List<Pergunta> PERGUNTAS = List.of(
            new Pergunta("Qual palavra-chave impede que uma classe seja herdada em Java?", List.of("static", "final", "volatile", "abstract"), 1),
            new Pergunta("Qual estrutura NAO permite elementos duplicados?", List.of("ArrayList", "LinkedList", "HashSet", "Vector"), 2),
            new Pergunta("Qual a complexidade de busca em uma arvore AVL?", List.of("O(n)", "O(log n)", "O(1)", "O(n log n)"), 1),
            new Pergunta("Qual interface representa um par chave/valor em Java?", List.of("Map", "List", "Queue", "Deque"), 0),
            new Pergunta("Quantos bits tem um int em Java?", List.of("8", "16", "32", "64"), 2),
            new Pergunta("Qual metodo inicia o programa Java?", List.of("start()", "run()", "main()", "init()"), 2),
            new Pergunta("Qual cor NAO existe em uma arvore rubro-negra?", List.of("Vermelho", "Preto", "Azul", "Nenhuma das anteriores"), 2),
            new Pergunta("Qual algoritmo acha o menor caminho com pesos nao negativos?", List.of("DFS", "Dijkstra", "Bubble Sort", "Huffman"), 1),
            new Pergunta("Qual recurso do Java 21 oferece threads leves?", List.of("Virtual Threads", "Fibers JNI", "Green Beans", "Daemon Pools"), 0),
            new Pergunta("Em Java, String e...", List.of("Mutavel", "Imutavel", "Primitiva", "Um enum"), 1)
    );

    private final Map<String, BiFunction<String, String, String>> comandos = new LinkedHashMap<>();
    private final Random rnd = new Random();
    private final BiConsumer<String, String> notificador;
    private final ScheduledExecutorService agendador = Executors.newScheduledThreadPool(1, r -> {
        Thread t = new Thread(r, "lembretes");
        t.setDaemon(true);
        return t;
    });

    // --- ESTADO ---
    private final Map<String, Pergunta> quizPendente = new ConcurrentHashMap<>();
    private final Map<String, Integer> pontos = new ConcurrentHashMap<>();
    private final Map<String, TabuleiroVelha> velhas = new ConcurrentHashMap<>();
    private volatile String enquetePergunta;
    private volatile List<String> enqueteOpcoes = List.of();
    private final Map<String, Integer> votos = new ConcurrentHashMap<>();

    public ComandosExtras(BiConsumer<String, String> notificador) {
        this.notificador = notificador;
        comandos.put("ajuda", (u, a) -> ajuda());
        comandos.put("moeda", (u, a) -> rnd.nextBoolean() ? "Cara!" : "Coroa!");
        comandos.put("sortear", (u, a) -> sortear(a));
        comandos.put("calc", (u, a) -> calcular(a));
        comandos.put("quiz", (u, a) -> quiz(u));
        comandos.put("resposta", (u, a) -> resposta(u, a));
        comandos.put("placar", (u, a) -> placar());
        comandos.put("enquete", (u, a) -> criarEnquete(a));
        comandos.put("votar", (u, a) -> votar(u, a));
        comandos.put("resultado", (u, a) -> resultado());
        comandos.put("lembrar", (u, a) -> lembrar(u, a));
        comandos.put("velha", (u, a) -> velha(u, a));
    }

    public Map<String, BiFunction<String, String, String>> todos() {
        return comandos;
    }

    public String executar(String nome, String usuario, String args) {
        BiFunction<String, String, String> fn = comandos.get(nome.toLowerCase());
        if (fn == null) {
            return null;
        }

        try {
            return fn.apply(usuario, args == null ? "" : args.trim());
        } catch (RuntimeException e) {
            return "Erro ao executar o comando: " + e.getMessage();
        }
    }

    // --- AJUDA ---
    private String ajuda() {
        return """
                Comandos:
                moeda - cara ou coroa
                sortear a, b, c - sorteia um item
                calc 2*(3+4) - calculadora
                quiz / resposta <A-D> / placar - perguntas de Java
                enquete Pergunta? | op1 | op2 - cria enquete
                votar <n> / resultado - vota e ve a apuracao
                lembrar 10m texto - lembrete (s, m ou h)
                velha / velha <1-9> - jogo da velha contra IA invencivel""";
    }

    // --- SORTEAR ---
    private String sortear(String args) {
        if (args.isBlank()) {
            return "Uso: sortear a, b, c";
        }
        String[] itens = args.split("[,;]");
        return "Sorteado: " + itens[rnd.nextInt(itens.length)].trim();
    }

    // --- CALC ---
    private String calcular(String expr) {
        if (expr.isBlank()) {
            return "Uso: calc 2*(3+4)";
        }
        double r = new Avaliador(expr).avaliar();
        return expr + " = " + (r == Math.rint(r) && Math.abs(r) < 1e15 ? String.valueOf((long) r) : String.valueOf(r));
    }

    // --- QUIZ ---
    private String quiz(String usuario) {
        Pergunta p = PERGUNTAS.get(rnd.nextInt(PERGUNTAS.size()));
        quizPendente.put(usuario, p);
        StringBuilder sb = new StringBuilder(p.texto()).append('\n');
        for (int i = 0; i < p.opcoes().size(); i++) {
            sb.append((char) ('A' + i))
                    .append(") ")
                    .append(p.opcoes().get(i))
                    .append('\n');
        }
        return sb.append("Responda com: resposta <letra>").toString();
    }

    private String resposta(String usuario, String args) {
        Pergunta pergunta = quizPendente.remove(usuario);
        if (pergunta == null) {
            return "Nenhuma pergunta pendente. Use: quiz";
        }

        if (args.isBlank()) {
            quizPendente.put(usuario, pergunta);
            return "Uso: resposta <A-D>";
        }

        int escolha = Character.toUpperCase(args.charAt(0)) - 'A';

        if (escolha == pergunta.correta()) {
            int total = pontos.merge(usuario, 1, Integer::sum);
            return "Correto! Voce tem " + total + " ponto(s).";
        }
        return "Errado. A resposta era " + (char) ('A' + pergunta.correta()) + ") " + pergunta.opcoes().get(pergunta.correta()) + ".";
    }

    private String placar() {
        if (pontos.isEmpty()) {
            return "Ninguem pontuou ainda. Use: quiz";
        }
        StringBuilder sb = new StringBuilder("Placar do quiz:\n");
        pontos.entrySet().stream()
                .sorted((a, b) -> b.getValue() - a.getValue())
                .limit(10)
                .forEach(e -> sb.append(e.getKey()).append(": ").append(e.getValue()).append('\n'));
        return sb.toString().trim();
    }

    // --- ENQUETE ---
    private String criarEnquete(String args) {
        String[] partes = args.split("\\|");
        if (partes.length < 3) {
            return "Uso: enquete Pergunta? | opcao 1 | opcao 2 | ...";
        }

        enquetePergunta = partes[0].trim();
        List<String> ops = new ArrayList<>();

        for (int i = 1; i < partes.length && i <= 8; i++) {
            ops.add(partes[i].trim());
        }
        enqueteOpcoes = ops;
        votos.clear();
        StringBuilder sb = new StringBuilder("Enquete: " + enquetePergunta + "\n");
        for (int i = 0; i < ops.size(); i++) {
            sb.append(i + 1)
                    .append(") ")
                    .append(ops.get(i))
                    .append('\n');
        }
        return sb.append("Vote com: votar <numero>").toString();
    }

    private String votar(String usuario, String args) {
        if (enquetePergunta == null) {
            return "Nao ha enquete ativa. Use: enquete Pergunta? | a | b";
        }

        int n;

        try {
            n = Integer.parseInt(args.trim());
        } catch (NumberFormatException e) {
            return "Uso: votar <numero>";
        }

        if (n < 1 || n > enqueteOpcoes.size()) {
            return "Opcao invalida (1 a " + enqueteOpcoes.size() + ").";
        }
        votos.put(usuario, n - 1);
        return "Voto registrado em \"" + enqueteOpcoes.get(n - 1) + "\".";
    }

    private String resultado() {
        if (enquetePergunta == null) {
            return "Nao ha enquete ativa.";
        }

        int[] cont = new int[enqueteOpcoes.size()];
        votos.values().forEach(i -> cont[i]++);
        int total = votos.size();
        StringBuilder sb = new StringBuilder("Resultado: " + enquetePergunta + "\n");

        for (int i = 0; i < cont.length; i++) {
            int pct;

            if (total == 0) {
                pct = 0;
            } else {
                pct = cont[i] * 100 / total;
            }

            sb.append(enqueteOpcoes.get(i))
                    .append(": ")
                    .append("#".repeat(pct / 10)).append(' ')
                    .append(cont[i])
                    .append(" (")
                    .append(pct)
                    .append("%)\n");
        }
        return sb.append("Total de votos: ").append(total).toString();
    }

    // --- LEMBRETE ---
    private String lembrar(String usuario, String args) {
        String[] p = args.split("\\s+", 2);
        if (p.length < 2 || !p[0].matches("\\d+[smh]")) {
            return "Uso: lembrar 10m texto (use s, m ou h)";
        }

        long n = Long.parseLong(p[0].substring(0, p[0].length() - 1));
        TimeUnit unidade = switch (p[0].charAt(p[0].length() - 1)) {
            case 's' -> TimeUnit.SECONDS;
            case 'm' -> TimeUnit.MINUTES;
            default -> TimeUnit.HOURS;
        };

        if (unidade.toSeconds(n) > 7 * 24 * 3600) {
            return "O maximo e 7 dias.";
        }
        agendador.schedule(() -> notificador.accept(usuario, "Lembrete: " + p[1]), n, unidade);
        return "Ok! Lembro voce em " + p[0] + ".";
    }

    // --- VELHA ---
    private String velha(String usuario, String args) {
        TabuleiroVelha tabuleiroVelha = velhas.get(usuario);
        if (args.isBlank() || tabuleiroVelha == null) {
            tabuleiroVelha = new TabuleiroVelha();
            velhas.put(usuario, tabuleiroVelha);
            return "Nova partida! Voce e X. Jogue com: velha <1-9>\n" + tabuleiroVelha;
        }

        if (!args.matches("[1-9]") || !tabuleiroVelha.jogar(Integer.parseInt(args) - 1, TabuleiroVelha.X)) {
            return "Jogada invalida.\n" + tabuleiroVelha;
        }

        if (!tabuleiroVelha.terminou()) {
            tabuleiroVelha.jogar(tabuleiroVelha.melhorJogada(TabuleiroVelha.O), TabuleiroVelha.O);
        }

        String fim = "";
        if (tabuleiroVelha.terminou()) {
            char v = tabuleiroVelha.vencedor();

            if (v == TabuleiroVelha.X) {
                fim = "\nVoce venceu!";
            } else if (v == TabuleiroVelha.O) {
                fim = "\nA IA venceu.";
            } else {
                fim = "\nEmpate.";
            }

            fim += " Use 'velha' para jogar de novo.";
            velhas.remove(usuario);
        }
        return tabuleiroVelha + fim;
    }

    // --- SEPARA COMANDO E ARGUMENTOS ---
    public static String[] separar(String texto, String... prefixos) {
        String t = texto.trim();
        for (String p : prefixos) {
            if (t.startsWith(p)) {
                t = t.substring(p.length());
                break;
            }
        }
        String[] partes = t.split("\\s+", 2);
        return new String[]{partes[0].toLowerCase(), partes.length > 1 ? partes[1] : ""};
    }
}
