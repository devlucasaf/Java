package math.calculadora.completa.model.datas;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ContagemRegressiva {

    public static String calcularContagem(String dataHoraAlvo) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime alvo = LocalDateTime.parse(dataHoraAlvo, fmt);
        LocalDateTime agora = LocalDateTime.now();

        if (alvo.isBefore(agora)) {
            return "A data já passou!";
        }

        Duration duracao = Duration.between(agora, alvo);
        long dias = duracao.toDays();
        long horas = duracao.toHours() % 24;
        long minutos = duracao.toMinutes() % 60;
        long segundos = duracao.getSeconds() % 60;

        return String.format("%d dias, %d horas, %d minutos, %d segundos", dias, horas, minutos, segundos);
    }
}
