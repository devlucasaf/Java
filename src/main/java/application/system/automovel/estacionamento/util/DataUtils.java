package application.system.automovel.estacionamento.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DataUtils {
    public static String formatarData(LocalDateTime data) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return data.format(fmt);
    }

    public static String formatarMoeda(double valor) {
        return String.format("R$ %.2f", valor);
    }
}
