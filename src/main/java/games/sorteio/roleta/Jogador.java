package games.sorteio.roleta;

public class Jogador {

    private final String    nome;
    private double          saldo;
    private int             rodadasJogadas;
    private int             rodadasGanhas;
    private int             rodadasPerdidas;
    private double          maiorGanho;
    private double          maiorPerda;

    public Jogador(String nome, double saldoInicial) {
        this.nome = nome;
        this.saldo = saldoInicial;
    }

    public String getNome() {
        return nome;
    }

    public double getSaldo() {
        return saldo;
    }

    public int getRodadasJogadas() {
        return rodadasJogadas;
    }

    public int getRodadasGanhas() {
        return rodadasGanhas;
    }

    public int getRodadasPerdidas() {
        return rodadasPerdidas;
    }

    public double getMaiorGanho() {
        return maiorGanho;
    }

    public double getMaiorPerda() {
        return maiorPerda;
    }

    public boolean podeApostar(double valor) {
        return valor > 0 && valor <= saldo;
    }

    public void debitar(double valor) {
        saldo -= valor;
    }

    public void creditar(double valor) {
        saldo += valor;
    }

    public void registrarVitoria(double lucro) {
        rodadasJogadas++;
        rodadasGanhas++;
        if (lucro > maiorGanho) {
            maiorGanho = lucro;
        }
    }

    public void registrarDerrota(double perda) {
        rodadasJogadas++;
        rodadasPerdidas++;
        if (perda > maiorPerda) {
            maiorPerda = perda;
        }
    }
}

