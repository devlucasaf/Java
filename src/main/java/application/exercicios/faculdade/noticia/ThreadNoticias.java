package application.exercicios.faculdade.noticia;

public class ThreadNoticias implements Runnable {
    @Override
    public void run() {
        String[] noticias = {
                "Caesb tem a melhor água do país!",
                "Motoboy buzina para encontrar dono do pedido",
                "Van♡Maid anuncia o fim das atividades",
                "Flamengo é o time mais odiado do Brasil",
                "Pedro comeu o frango",
                "Casal VV: Vinicius JR e Virginia. O novo Pelé e Xuxa?",
                "Maria Flor anda de cavalo",
                "Centauro finaliza uma entrega!",
                "Encerrado a Segunda Guerra Mundial!",
                "Titanic afunda nas coordenadas de 41°43'57\"N, 49°56'49\"W. Teremos sobreviventes?"
        };

        try {
            for (int i = 0; i < 10; i++) {
                System.out.println("[NOTÍCIA " + (i + 1) + "/10]: " + noticias[i]);

                Thread.sleep(5000);
            }
        } catch (InterruptedException e) {
            System.out.println("Thread de notícias interrompida.");
        }
        System.out.println(">> Fim das Notícias.");
    }
}
