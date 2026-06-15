package org.application.outros.senha;

import java.util.*;

public class GeradorSenha {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Senha s = new Senha();

        System.out.print("Digite o número de sites ou apps: ");
        int nomeAplicativos = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < nomeAplicativos; i++) {
            System.out.print("Digite o nome do app/site: ");
            s.setNomeAPP(scanner.nextLine());

            System.out.print("Digite o tamanho da senha: ");
            int comprimento = Integer.parseInt(scanner.nextLine());

            String senha = s.gerarSenha(comprimento);
            if (senha != null) {
                System.out.println("Senha gerada com sucesso! Sua senha: " + senha);
                s.gravarSenha();
            }
        }
        scanner.close();
    }
}
