package application.utilitarios.analisadorlogs;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) throws IOException {
        Path arquivo = args.length > 0 ? Path.of(args[0]) : gerarLogExemplo();
        Pattern filtro = args.length > 1 ? Pattern.compile(args[1]) : null;
        String nivel = args.length > 2 ? args[2] : null;

        System.out.println("Analisando: " + arquivo.toAbsolutePath());
        if (filtro != null) {
            System.out.println("Filtro regex: " + filtro);
        }

        if (nivel != null) {
            System.out.println("Filtro nivel: " + nivel);
        }
        System.out.println();

        AnalisadorLogs analisador = new AnalisadorLogs(arquivo);
        Resultado resultado = analisador.analisar(filtro, nivel);

        System.out.println("=== ESTATISTICAS ===");
        System.out.println("Linhas totais: " + resultado.totalLinhas);
        System.out.println("Linhas validas: " + resultado.linhasValidas);
        System.out.println("Linhas casadas: " + resultado.linhasCasadas);
        System.out.println("Primeira: " + resultado.primeira);
        System.out.println("Ultima: " + resultado.ultima);
        System.out.println();

        System.out.println("=== POR NIVEL ===");
        resultado.porNivel.forEach((k, v) -> System.out.printf("%-8s %d%n", k, v));
        System.out.println();

        System.out.println("=== POR HORA ===");
        resultado.porHora.forEach((k, v) -> System.out.printf("%02dh  %s (%d)%n",
                k, "#".repeat(Math.min(60, v)), v));
    }

    private static Path gerarLogExemplo() throws IOException {
        Path arq = Files.createTempFile("exemplo-log-", ".log");
        String[] niveis = {"INFO", "INFO", "INFO", "WARN", "ERROR", "DEBUG"};
        String[] mensagens = {
                "Conexao estabelecida com banco de dados",
                "Usuario logado com sucesso",
                "Cache atualizado",
                "Timeout ao chamar servico externo",
                "Falha ao processar requisicao",
                "Iniciando job de indexacao",
                "Requisicao processada em 45ms",
                "Erro de autenticacao: token invalido"
        };
        Random rnd = new Random(42);
        List<String> linhas = new ArrayList<>();
        LocalDateTime dt = LocalDateTime.now().minusHours(24);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < 5000; i++) {
            dt = dt.plusSeconds(rnd.nextInt(20) + 1);
            String linha = "[" + dt.format(fmt) + "] "
                    + niveis[rnd.nextInt(niveis.length)] + " "
                    + mensagens[rnd.nextInt(mensagens.length)];
            linhas.add(linha);
        }
        Files.write(arq, linhas);
        System.out.println("Arquivo de exemplo criado com " + linhas.size() + " linhas");
        return arq;
    }
}

