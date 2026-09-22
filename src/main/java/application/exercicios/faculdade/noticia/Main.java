package application.exercicios.faculdade.noticia;

/**
 Paradigmas de Linguagens de Programação
 Atividades do Livro Base - Capítulo 5 - Programação Concorrente em Java
 Exercício do Capítulo 5

 Questão 2)
 Crie duas threads em Java, uma que fica enviando notícias a cada 5
 segundos (textos quaisquer, no total de 10), enquanto a outra thread
 fica enviando a hora, a cada 10 segundos. A hora deverá ser informada
 nomínimo 5 vezes para que o programa se encerre;

 *Atividade Desenvolvida com o auxílio da IA com autorização do professor*
 */

public class Main {
    public static void main(String[] args) {
        Thread threadNoticias = new Thread(new ThreadNoticias());
        Thread threadHora = new Thread(new ThreadHora());

        System.out.println("=== INICIANDO SISTEMA INFORMATIVO ===");

        threadNoticias.start();
        threadHora.start();

        try {
            threadNoticias.join();
            threadHora.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("=== PROGRAMA ENCERRADO ===");
    }
}

