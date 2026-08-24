package application.simuladores.arduino;

import java.util.concurrent.ThreadLocalRandom;

public class Main {

    private static final int LED = 13;
    private static final int BOTAO = 2;
    private static final int SENSOR_TEMP = 0;
    private static final int LED_PWM = 9;

    // --- EXECUTA OS SKETCHES DE SIMULACAO DO ARDUINO ---
    public static void main(String[] args) {
        Arduino arduino = new Arduino();

        // --- SIMULA UM LED PISCANDO EM INTERVALOS DE 200 MILISSEGUNDOS ---
        System.out.println("=== SKETCH 1: LED piscando ===\n");
        new Sketch(arduino) {
            @Override
            public void setup() {
                arduino.pinMode(LED, ModoPino.SAIDA);
            }

            @Override
            public void loop() {
                arduino.digitalWrite(LED, 1);
                arduino.delay(200);
                arduino.digitalWrite(LED, 0);
                arduino.delay(200);
            }
        }.executar(3);

        // --- SIMULA UM BOTAO QUE CONTROLA O ESTADO DO LED ---
        System.out.println("\n=== SKETCH 2: Botao + LED ===\n");
        new Sketch(arduino) {
            @Override
            public void setup() {
                arduino.pinMode(BOTAO, ModoPino.ENTRADA_PULLUP);
                arduino.pinMode(LED, ModoPino.SAIDA);
            }

            @Override
            public void loop() {
                arduino.simularEntradaDigital(BOTAO, ThreadLocalRandom.current().nextInt(2));
                int estado = arduino.digitalRead(BOTAO);
                System.out.println("  Botao = " + (estado == 1 ? "HIGH" : "LOW"));
                arduino.digitalWrite(LED, estado);
            }
        }.executar(4);

        // --- SIMULA UM SENSOR DE TEMPERATURA QUE CONTROLA O BRILHO DO LED ---
        System.out.println("\n=== SKETCH 3: Termometro com PWM ===\n");
        new Sketch(arduino) {
            @Override
            public void setup() {
                arduino.pinMode(LED_PWM, ModoPino.SAIDA);
            }

            @Override
            public void loop() {
                int leitura = ThreadLocalRandom.current().nextInt(300, 800);
                arduino.simularEntradaAnalogica(SENSOR_TEMP, leitura);
                int valor = arduino.analogRead(SENSOR_TEMP);
                double tempC = valor * 500.0 / 1023.0;
                System.out.println("  Sensor=" + valor + " -> " + String.format("%.1f°C", tempC));
                int brilho = (int) (tempC / 50.0 * 255);
                arduino.analogWrite(LED_PWM, brilho);
                arduino.delay(300);
            }
        }.executar(4);

        System.out.println("\nExecucao total: " + arduino.millis() + "ms");
    }
}

