package application.utilitarios.logs;

import java.time.LocalDateTime;
import java.util.Map;

public class Resultado {

    public final long                   totalLinhas;
    public final long                   linhasValidas;
    public final long                   linhasCasadas;
    public final Map<String, Integer>   porNivel;
    public final Map<Integer, Integer>  porHora;
    public final LocalDateTime          primeira;
    public final LocalDateTime          ultima;

    public Resultado(long totalLinhas, long linhasValidas, long linhasCasadas,
                     Map<String, Integer> porNivel, Map<Integer, Integer> porHora,
                     LocalDateTime primeira, LocalDateTime ultima) {
        this.totalLinhas = totalLinhas;
        this.linhasValidas = linhasValidas;
        this.linhasCasadas = linhasCasadas;
        this.porNivel = porNivel;
        this.porHora = porHora;
        this.primeira = primeira;
        this.ultima = ultima;
    }
}

