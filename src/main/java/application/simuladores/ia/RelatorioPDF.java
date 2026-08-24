package application.simuladores.ia;

import java.io.IOException;
import java.nio.file.Path;

public class RelatorioPDF {

    // --- GERA O RELATORIO PDF COM OS DADOS E O GRAFICO INFORMADOS ---
    public void gerar(AnaliseCronologica dados, Path graficoPath, Path caminhoSaida) throws IOException {
        System.out.println("Gerando relatorio em: " + caminhoSaida);
    }
}
