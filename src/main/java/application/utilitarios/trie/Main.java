package application.utilitarios.trie;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Trie trie = new Trie();
        String[] palavras = {
            "casa", "casamento", "casaco", "carro", "carroca", "cartao", "cartola", "carta",
            "cachorro", "cabelo", "cabana", "cabecalho", "cair", "caixa", "calor", "calma",
            "programa", "programacao", "programador", "projeto", "professor", "profissao",
            "java", "javascript", "python", "javali", "javalina"
        };
        for (String p : palavras) {
            trie.inserir(p);
        }

        for (int i = 0; i < 5; i++) {
            trie.inserir("casa");
        }

        for (int i = 0; i < 3; i++) {
            trie.inserir("programa");
        }

        System.out.println("=== TRIE ===");
        System.out.println("Total de palavras unicas: " + trie.contarPalavras());
        System.out.println();

        System.out.println("Contem 'casa'? " + trie.contem("casa"));
        System.out.println("Contem 'ca'? " + trie.contem("ca"));
        System.out.println("Comeca com 'ca'? " + trie.comecaCom("ca"));
        System.out.println();

        for (String p : List.of("ca", "car", "casa", "pro", "jav", "xxx")) {
            List<String> r = trie.autocomplete(p, 10);
            System.out.println("Sugestoes para '" + p + "': " + r);
        }

        System.out.println("\n=== MODO INTERATIVO (digite prefixo, 'sair' para encerrar) ===");
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("prefixo> ");
            if (!sc.hasNextLine()) {
                break;
            }

            String s = sc.nextLine().trim();
            if (s.equalsIgnoreCase("sair")) {
                break;
            }

            if (s.isEmpty()) {
                continue;
            }
            System.out.println("  -> " + trie.autocomplete(s, 10));
        }
    }
}

