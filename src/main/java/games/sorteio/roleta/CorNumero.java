package games.sorteio.roleta;

public enum CorNumero {
    VERMELHO("\u001B[31m", "Vermelho"),
    PRETO   ("\u001B[30;1m", "Preto"),
    VERDE   ("\u001B[32m", "Verde");

    public static final String RESET = "\u001B[0m";

    private final String codigoAnsi;
    private final String nome;

    CorNumero(String codigoAnsi, String nome) {
        this.codigoAnsi = codigoAnsi;
        this.nome = nome;
    }

    public String getCodigoAnsi() {
        return codigoAnsi;
    }

    public String getNome() {
        return nome;
    }

    public String pintar(String texto) {
        return codigoAnsi + texto + RESET;
    }

    public static CorNumero deNumero(int numero) {
        if (numero == 0) {
            return VERDE;
        }

        int[] vermelhos = {1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36};
        for (int v : vermelhos) {
            if (v == numero) {
                return VERMELHO;
            }
        }
        return PRETO;
    }
}

