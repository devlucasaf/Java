package application.simuladores.arduino;

public abstract class Sketch {

    protected final Arduino arduino;

    protected Sketch(Arduino arduino) {
        this.arduino = arduino;
    }

    public abstract void setup();

    public abstract void loop();

    public void executar(int iteracoes) {
        setup();
        for (int i = 0; i < iteracoes; i++) {
            System.out.println("--- loop " + (i + 1) + " ---");
            loop();
        }
    }
}

