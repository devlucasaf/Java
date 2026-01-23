package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

// Classe responsável por gerar e armazenar senhas
class Senha {
    private String app;              // Nome do aplicativo/site associado à senha
    private String senhaFinal;       // Senha gerada
    private Random random = new Random(); // Gerador de números aleatórios

    // Conjuntos de caracteres usados para compor a senha
    private static final String LETRAS = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMEROS = "0123456789";
    private static final String PONTUACAO = "!#$%&'()*+,-./:;<=>?@[\\]^_`{|}~";

    // String que une todos os conjuntos de caracteres
    private static final String unirTodosAtributos = LETRAS + NUMEROS + PONTUACAO;

    // Define o nome do aplicativo/site
    public void setNomeAPP(String nome) {
        this.app = nome;
    }

    // Método para gerar uma senha aleatória com tamanho definido
    public String gerarSenha (int tamanho){
        // Verifica se o tamanho é válido
        if (tamanho <= 4) {
            System.out.print("A senha precisa conter no mínimo 5 caracteres!");
            return null;
        }

        // Lista que armazenará os caracteres da senha
        List<Character> listaSenha = new ArrayList<>();

        // Garante que a senha tenha pelo menos uma letra, um número e um símbolo
        listaSenha.add(LETRAS.charAt(random.nextInt(LETRAS.length())));
        listaSenha.add(NUMEROS.charAt(random.nextInt(NUMEROS.length())));
        listaSenha.add(PONTUACAO.charAt(random.nextInt(PONTUACAO.length())));

        // Preenche o restante da senha com caracteres aleatórios
        for (int i = 0; i < (tamanho - 3); i++) {
            listaSenha.add(unirTodosAtributos.charAt(random.nextInt(unirTodosAtributos.length())));
        }

        // Embaralha os caracteres para evitar padrão previsível
        Collections.shuffle(listaSenha);

        // Constrói a senha final a partir da lista de caracteres
        StringBuilder stringBuilder = new StringBuilder();
        for (char c : listaSenha) {
            stringBuilder.append(c);
        }
        this.senhaFinal = stringBuilder.toString();

        return this.senhaFinal;
    }

    // Método para gravar a senha em um arquivo de texto
    public void gravarSenha() {
        try (FileWriter fileWriter = new FileWriter("não entre.txt", true); // Abre arquivo em modo append
            PrintWriter printWriter = new PrintWriter(fileWriter)) {

            // Escreve informações formatadas no arquivo
            printWriter.println("       Senhas para login        \n");
            printWriter.println("+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=\n");
            printWriter.println("APP/Site: " + this.app + "\n");
            printWriter.println("Senha: " + this.senhaFinal + "\n");
            printWriter.println("+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=\n");
            printWriter.println("\n");
            printWriter.println();

        }
        catch (IOException ioException) {
            // Caso ocorra erro ao gravar no arquivo
            System.out.print("Erro ao gravar arquivo: " + ioException.getMessage());
        }
    }
}

// Classe principal que executa o programa
public class GeradorSenha {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Scanner para entrada de dados
        Senha s = new Senha(); // Instância da classe Senha

        // Pergunta quantos aplicativos/sites terão senhas geradas
        System.out.print("Digite o número de sites ou apps: ");
        int nomeAplicativos = Integer.parseInt(scanner.nextLine());

        // Loop para gerar senha para cada aplicativo/site informado
        for (int i = 0; i < nomeAplicativos; i++) {
            System.out.print("Digite o nome do app/site: ");
            s.setNomeAPP(scanner.nextLine());

            System.out.print("Digite o tamanho da senha: ");
            int comprimento = Integer.parseInt(scanner.nextLine());

            // Gera a senha e exibe na tela
            String senha = s.gerarSenha(comprimento);
            if (senha != null) {
                System.out.println("Senha gerada com sucesso! Sua senha: " + senha);
                s.gravarSenha(); // Grava a senha no arquivo
            }
        }
        scanner.close(); // Fecha o scanner
    }
}
