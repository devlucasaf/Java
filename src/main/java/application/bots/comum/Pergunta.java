package application.bots.comum;

import java.util.List;

public record Pergunta(
        String          texto,
        List<String>    opcoes,
        int             correta
) {}