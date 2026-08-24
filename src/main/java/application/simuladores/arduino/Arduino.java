package application.simuladores.arduino;

import java.util.HashMap;
import java.util.Map;

public class Arduino {
    private final int           numPinos;
    private final ModoPino[]    modos;
    private final int[]         valoresDigitais;
    private final int[]         valoresAnalogicos;
    private final Map<Integer, Runnable> interrupcoes = new HashMap<>();
    private long millisIniciais = System.currentTimeMillis();

    // --- INICIALIZA O ARDUINO COM 14 PINOS ---
    public Arduino() {
        this(14);
    }

    // --- INICIALIZA O ARDUINO COM A QUANTIDADE DE PINOS INFORMADA ---
    public Arduino(int numPinos) {
        this.numPinos = numPinos;
        this.modos = new ModoPino[numPinos];
        this.valoresDigitais = new int[numPinos];
        this.valoresAnalogicos = new int[numPinos];

        for (int i = 0; i < numPinos; i++) {
            modos[i] = ModoPino.ENTRADA;
        }
    }

    // --- DEFINE O MODO DE OPERACAO DE UM PINO ---
    public void pinMode(int pino, ModoPino modo) {
        checar(pino);
        modos[pino] = modo;
        System.out.println("[Arduino] pinMode(" + pino + ", " + modo + ")");
    }

    // --- ESCREVE UM VALOR DIGITAL EM UM PINO CONFIGURADO COMO SAIDA ---
    public void digitalWrite(int pino, int valor) {
        checar(pino);
        if (modos[pino] != ModoPino.SAIDA) {
            throw new IllegalStateException("Pino " + pino + " nao esta em modo SAIDA");
        }
        valoresDigitais[pino] = valor == 0 ? 0 : 1;
        System.out.println("[Arduino] digitalWrite(" + pino + ", " + (valoresDigitais[pino] == 1 ? "HIGH" : "LOW") + ")");
    }

    // --- LE O VALOR DIGITAL ARMAZENADO EM UM PINO ---
    public int digitalRead(int pino) {
        checar(pino);
        return valoresDigitais[pino];
    }

    // --- ESCREVE UM VALOR ANALOGICO ENTRE 0 E 255 EM UM PINO ---
    public void analogWrite(int pino, int valor) {
        checar(pino);
        int v = Math.max(0, Math.min(255, valor));
        valoresAnalogicos[pino] = v;
        System.out.println("[Arduino] analogWrite(" + pino + ", " + v + ") duty=" + String.format("%.1f%%", v * 100.0 / 255));
    }

    // --- LE O VALOR ANALOGICO ARMAZENADO EM UM PINO ---
    public int analogRead(int pino) {
        checar(pino);
        return valoresAnalogicos[pino];
    }

    // --- SIMULA UMA ENTRADA DIGITAL E EXECUTA A INTERRUPCAO ASSOCIADA ---
    public void simularEntradaDigital(int pino, int valor) {
        checar(pino);
        valoresDigitais[pino] = valor;
        Runnable runnable = interrupcoes.get(pino);

        if (runnable != null) {
            runnable.run();
        }
    }

    // --- SIMULA UMA ENTRADA ANALOGICA ENTRE 0 E 1023 ---
    public void simularEntradaAnalogica(int pino, int valor) {
        checar(pino);
        valoresAnalogicos[pino] = Math.max(0, Math.min(1023, valor));
    }

    // --- ASSOCIA UMA ACAO DE INTERRUPCAO A UM PINO ---
    public void attachInterrupt(int pino, Runnable acao) {
        checar(pino);
        interrupcoes.put(pino, acao);
        System.out.println("[Arduino] attachInterrupt(" + pino + ")");
    }

    // --- INTERROMPE A EXECUCAO PELO TEMPO INFORMADO EM MILISSEGUNDOS ---
    public void delay(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // --- RETORNA O TEMPO DECORRIDO DESDE A INICIALIZACAO DO ARDUINO ---
    public long millis() {
        return System.currentTimeMillis() - millisIniciais;
    }

    // --- VERIFICA SE O NUMERO DO PINO ESTA DENTRO DO INTERVALO VALIDO ---
    private void checar(int pino) {
        if (pino < 0 || pino >= numPinos) {
            throw new IllegalArgumentException("Pino invalido: " + pino);
        }
    }
}

