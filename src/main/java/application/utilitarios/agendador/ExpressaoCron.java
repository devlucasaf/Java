package application.utilitarios.agendador;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class ExpressaoCron {

    private final Set<Integer>  minutos;
    private final Set<Integer>  horas;
    private final Set<Integer>  diasDoMes;
    private final Set<Integer>  meses;
    private final Set<Integer>  diasDaSemana;
    private final String        textoOriginal;

    private ExpressaoCron(Set<Integer> minutos, Set<Integer> horas, Set<Integer> diasDoMes,
                          Set<Integer> meses, Set<Integer> diasDaSemana, String texto) {
        this.minutos = minutos;
        this.horas = horas;
        this.diasDoMes = diasDoMes;
        this.meses = meses;
        this.diasDaSemana = diasDaSemana;
        this.textoOriginal = texto;
    }

    public boolean casaCom(LocalDateTime dt) {
        int diaSemana = dt.getDayOfWeek().getValue() % 7;
        return minutos.contains(dt.getMinute())
                && horas.contains(dt.getHour())
                && diasDoMes.contains(dt.getDayOfMonth())
                && meses.contains(dt.getMonthValue())
                && diasDaSemana.contains(diaSemana);
    }

    public String getTextoOriginal() {
        return textoOriginal;
    }

    public static ExpressaoCron parse(String expressao) {
        String[] partes = expressao.trim().split("\\s+");
        if (partes.length != 5) {
            throw new IllegalArgumentException("Cron precisa de 5 campos: min hora dia mes dow");
        }
        return new ExpressaoCron(
                parseCampo(partes[0], 0, 59),
                parseCampo(partes[1], 0, 23),
                parseCampo(partes[2], 1, 31),
                parseCampo(partes[3], 1, 12),
                parseCampo(partes[4], 0, 6),
                expressao
        );
    }

    private static Set<Integer> parseCampo(String campo, int min, int max) {
        Set<Integer> valores = new HashSet<>();
        for (String parte : campo.split(",")) {
            int passo = 1;
            String base = parte;
            if (parte.contains("/")) {
                String[] p = parte.split("/");
                base = p[0];
                passo = Integer.parseInt(p[1]);
            }
            int inicio;
            int fim;

            if (base.equals("*")) {
                inicio = min;
                fim = max;
            } else if (base.contains("-")) {
                String[] p = base.split("-");
                inicio = Integer.parseInt(p[0]);
                fim = Integer.parseInt(p[1]);
            } else {
                inicio = Integer.parseInt(base);
                fim = inicio;
            }

            for (int v = inicio; v <= fim; v += passo) {
                if (v >= min && v <= max) {
                    valores.add(v);
                }
            }
        }
        return valores;
    }
}

