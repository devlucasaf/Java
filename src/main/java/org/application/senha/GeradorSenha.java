package org.application.senha;

import java.util.*;

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
