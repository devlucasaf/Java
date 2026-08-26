package application.system.streaming;

import application.system.streaming.enums.Tipo;
import application.system.streaming.model.Plano;
import application.system.streaming.model.Titulo;
import application.system.streaming.model.Usuario;
import application.system.streaming.service.Streaming;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Streaming streaming = new Streaming();

        streaming.adicionarTitulo(new Titulo("T1", "Fuga das Estrelas", Tipo.FILME,
                new HashSet<>(Arrays.asList("Ficcao Cientifica", "Acao")), 12));
        streaming.adicionarTitulo(new Titulo("T2", "Codigo Secreto", Tipo.SERIE,
                new HashSet<>(Arrays.asList("Ficcao Cientifica", "Suspense")), 14));
        streaming.adicionarTitulo(new Titulo("T3", "Risadas da Vila", Tipo.SERIE,
                new HashSet<>(Arrays.asList("Comedia")), 0));
        streaming.adicionarTitulo(new Titulo("T4", "Alem do Horizonte", Tipo.FILME,
                new HashSet<>(Arrays.asList("Ficcao Cientifica", "Drama")), 10));
        streaming.adicionarTitulo(new Titulo("T5", "Noite de Terror", Tipo.FILME,
                new HashSet<>(Arrays.asList("Terror")), 18));

        streaming.avaliarTitulo("T1", 4.5);
        streaming.avaliarTitulo("T2", 4.8);
        streaming.avaliarTitulo("T3", 4.0);
        streaming.avaliarTitulo("T4", 4.2);
        streaming.avaliarTitulo("T5", 3.9);

        Usuario usuario = new Usuario("U1", "Rafael", 16);
        streaming.cadastrarUsuario(usuario);
        usuario.assinarPlano(new Plano("Premium", 39.90, 4, true, true));

        System.out.println("Plano de " + usuario.getNome() + ": " + usuario.getPlano());
        System.out.println();

        System.out.println("=== Historico de visualizacao ===");
        streaming.assistir(usuario, "T1");
        streaming.assistir(usuario, "T2");

        try {
            streaming.assistir(usuario, "T5");
        } catch (IllegalStateException e) {
            System.out.println("Bloqueado: " + e.getMessage());
        }

        System.out.println();
        System.out.println("Generos preferidos de " + usuario.getNome() + ": " + usuario.getGenerosPreferidos());

        System.out.println();
        System.out.println("=== Recomendacoes personalizadas ===");
        List<Titulo> recomendados = streaming.recomendarPara(usuario, 3);
        for (Titulo titulo : recomendados) {
            System.out.println(titulo);
        }
    }
}
