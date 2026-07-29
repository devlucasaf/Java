package application.simuladores.arduino;

import java.util.HashMap;
import java.util.Map;

public class Arduino {

    public enum ModoPino { ENTRADA, SAIDA, ENTRADA_PULLUP }

    private final int numPinos;
    private final ModoPino[] modos;
    private final int[] valoresDigitais;
    private final int[] valoresAnalogicos;
    private final Map<Integer, Runnable> interrupcoes = new HashMap<>();
    private long millisIniciais = System.currentTimeMillis();

    public Arduino() {
        this(14);
    }

    public Arduino(int numPinos) {
        this.numPinos = numPinos;
        this.modos = new ModoPino[numPinos];
        this.valoresDigitais = new int[numPinos];
        this.valoresAnalogicos = new int[numPinos];
        for (int i = 0; i < numPinos; i++) modos[i] = ModoPino.ENTRADA;
    }

    public void pinMode(int pino, ModoPino modo) {
        checar(pino);
        modos[pino] = modo;
        System.out.println("[Arduino] pinMode(" + pino + ", " + modo + ")");
    }

    public void digitalWrite(int pino, int valor) {
        checar(pino);
        if (modos[pino] != ModoPino.SAIDA) {
            throw new IllegalStateException("Pino " + pino + " nao esta em modo SAIDA");
        }
        valoresDigitais[pino] = valor == 0 ? 0 : 1;
        System.out.println("[Arduino] digitalWrite(" + pino + ", " + (valoresDigitais[pino] == 1 ? "HIGH" : "LOW") + ")");
    }

    public int digitalRead(int pino) {
        checar(pino);
        return valoresDigitais[pino];
    }

    public void analogWrite(int pino, int valor) {
        checar(pino);
        int v = Math.max(0, Math.min(255, valor));
        valoresAnalogicos[pino] = v;
        System.out.println("[Arduino] analogWrite(" + pino + ", " + v + ") duty=" 
                + String.format("%.1f%%", v * 100.0 / 255));
    }

    public int analogRead(int pino) {
        checar(pino);
        return valoresAnalogicos[pino];
    }

    public void simularEntradaDigital(int pino, int valor) {
        checar(pino);
        valoresDigitais[pino] = valor;
        Runnable r = interrupcoes.get(pino);
        if (r != null) r.run();
    }

    public void simularEntradaAnalogica(int pino, int valor) {
        checar(pino);
        valoresAnalogicos[pino] = Math.max(0, Math.min(1023, valor));
    }

    public void attachInterrupt(int pino, Runnable acao) {
        checar(pino);
        interrupcoes.put(pino, acao);
        System.out.println("[Arduino] attachInterrupt(" + pino + ")");
    }

    public void delay(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public long millis() {
        return System.currentTimeMillis() - millisIniciais;
    }

    private void checar(int pino) {
        if (pino < 0 || pino >= numPinos) {
            throw new IllegalArgumentException("Pino invalido: " + pino);
        }
    }
}

