package application.system.hospital;

public enum GravidadeTriagem {
    EMERGENCIA(5, "Vermelho", 0),
    MUITO_URGENTE(4, "Laranja", 10),
    URGENTE(3, "Amarelo", 60),
    POUCO_URGENTE(2, "Verde", 120),
    NAO_URGENTE(1, "Azul", 240);

    private final int       nivel;
    private final String    cor;
    private final long      tempoMaximoEsperaMinutos;

    GravidadeTriagem(int nivel, String cor, long tempoMaximoEsperaMinutos) {
        this.nivel = nivel;
        this.cor = cor;
        this.tempoMaximoEsperaMinutos = tempoMaximoEsperaMinutos;
    }

    public int getNivel() {
        return nivel;
    }

    public String getCor() {
        return cor;
    }

    public long getTempoMaximoEsperaMinutos() {
        return tempoMaximoEsperaMinutos;
    }

    // --- RETORNA A GRAVIDADE IMEDIATAMENTE SUPERIOR ---
    public GravidadeTriagem aumentarGravidade() {
        switch (this) {
            case NAO_URGENTE:
                return POUCO_URGENTE;
            case POUCO_URGENTE:
                return URGENTE;
            case URGENTE:
                return MUITO_URGENTE;
            case MUITO_URGENTE:
                return EMERGENCIA;
            default:
                return EMERGENCIA;
        }
    }

    // --- RETORNA O NOME FORMATADO DA GRAVIDADE ---
    public String getNomeFormatado() {
        switch (this) {
            case EMERGENCIA:
                return "Emergência";
            case MUITO_URGENTE:
                return "Muito urgente";
            case URGENTE:
                return "Urgente";
            case POUCO_URGENTE:
                return "Pouco urgente";
            case NAO_URGENTE:
                return "Não urgente";
            default:
                return name();
        }
    }
}

