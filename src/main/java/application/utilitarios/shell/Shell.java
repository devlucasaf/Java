package application.utilitarios.shell;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Shell {

    private Path atual = Paths.get(System.getProperty("user.dir"));

    public static void main(String[] args) {
        new Shell().executar();
    }

    public void executar() {
        Scanner sc = new Scanner(System.in);
        System.out.println("=== SHELL CASEIRO ===");
        System.out.println("Comandos: pwd, cd <dir>, ls, cat <arquivo>, grep <padrao> <arquivo>, echo <texto>, help, exit");
        System.out.println("Pipes: comando1 | comando2");
        System.out.println();

        while (true) {
            System.out.print(atual + " > ");
            if (!sc.hasNextLine()) {
                break;
            }

            String linha = sc.nextLine().trim();
            if (linha.isEmpty()) {
                continue;
            }

            if (linha.equalsIgnoreCase("exit")) {
                break;
            }

            try {
                String saida = executarLinha(linha);
                if (!saida.isEmpty()) {
                    System.out.println(saida);
                }
            } catch (Exception e) {
                System.out.println("erro: " + e.getMessage());
            }
        }
    }

    private String executarLinha(String linha) throws IOException {
        String[] partes = linha.split("\\|");
        String entrada = null;
        for (String cmd : partes) {
            entrada = executar(cmd.trim(), entrada);
        }
        return entrada == null ? "" : entrada;
    }

    private String executar(String comando, String entradaAnterior) throws IOException {
        String[] tokens = comando.split("\\s+");
        String cmd = tokens[0];
        switch (cmd) {
            case "pwd":
                return atual.toString();
            case "cd":
                if (tokens.length < 2) {
                    return "";
                }

                Path novo = atual.resolve(tokens[1]).normalize();
                if (!Files.isDirectory(novo)) {
                    return "diretorio invalido: " + novo;
                }

                atual = novo;
                return "";
            case "ls":
                StringBuilder sb = new StringBuilder();
                try (DirectoryStream<Path> ds = Files.newDirectoryStream(atual)) {
                    for (Path p : ds) {
                        sb.append(p.getFileName()).append('\n');
                    }
                }
                return sb.toString().stripTrailing();
            case "cat":
                if (tokens.length < 2) {
                    return "";
                }

                Path arq = atual.resolve(tokens[1]);
                if (!Files.isRegularFile(arq)) {
                    return "arquivo nao encontrado: " + arq;
                }

                StringBuilder texto = new StringBuilder();
                try (BufferedReader br = Files.newBufferedReader(arq)) {
                    String l;
                    while ((l = br.readLine()) != null) {
                        texto.append(l).append('\n');
                    }
                }
                return texto.toString().stripTrailing();
            case "echo":
                return comando.substring(cmd.length()).trim();
            case "grep":
                if (tokens.length < 2) {
                    return "";
                }

                String padrao = tokens[1];
                String conteudo;
                if (entradaAnterior != null) {
                    conteudo = entradaAnterior;
                } else if (tokens.length >= 3) {
                    conteudo = executar("cat " + tokens[2], null);
                } else {
                    return "grep precisa de arquivo ou entrada de pipe";
                }

                StringBuilder filtrado = new StringBuilder();
                for (String l : conteudo.split("\n")) {
                    if (l.contains(padrao)) {
                        filtrado.append(l).append('\n');
                    }
                }
                return filtrado.toString().stripTrailing();
            case "wc":
                if (entradaAnterior == null) {
                    return "wc precisa de entrada via pipe";
                }
                return "linhas=" + entradaAnterior.split("\n").length
                        + " palavras=" + entradaAnterior.split("\\s+").length
                        + " chars=" + entradaAnterior.length();
            case "help":
                return "pwd, cd, ls, cat, grep, echo, wc, exit. Use | para encadear.";
            default:
                return "comando desconhecido: " + cmd;
        }
    }
}

