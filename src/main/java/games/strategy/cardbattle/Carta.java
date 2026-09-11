package games.strategy.cardbattle;

import java.util.Objects;

public class Carta {
    private final long          identificador;
    private final String        nome;
    private final String        descricao;
    private final TipoCarta     tipo;
    private final AlvoCarta     alvo;
    private final int           custoEnergia;
    private final int           dano;
    private final int           defesa;
    private final int           cura;
    private final EfeitoStatus  efeito;

    public Carta(long identificador, String nome, String descricao, TipoCarta tipo, AlvoCarta alvo, int custoEnergia,
                 int dano, int defesa, int cura, EfeitoStatus efeito) {
        validarIdentificador(identificador);
        validarTexto(nome, "O nome");
        validarTipoEAlvo(tipo, alvo);
        validarValores(custoEnergia, dano, defesa, cura);

        this.identificador = identificador;
        this.nome = nome.trim();
        this.descricao = descricao == null ? "" : descricao.trim();
        this.tipo = tipo;
        this.alvo = alvo;
        this.custoEnergia = custoEnergia;
        this.dano = dano;
        this.defesa = defesa;
        this.cura = cura;
        this.efeito = efeito;
    }

    // --- CRIA UMA CARTA DE ATAQUE ---
    public static Carta criarAtaque(long identificador, String nome, String descricao, int custoEnergia, int dano) {
        return new Carta(identificador, nome, descricao, TipoCarta.ATAQUE, AlvoCarta.ADVERSARIO, custoEnergia, dano, 0, 0, null);
    }

    // --- CRIA UMA CARTA DE DEFESA ---
    public static Carta criarDefesa(long identificador, String nome, String descricao, int custoEnergia, int defesa) {
        return new Carta(identificador, nome, descricao, TipoCarta.DEFESA, AlvoCarta.PROPRIO_JOGADOR, custoEnergia, 0, defesa, 0, null);
    }

    // --- CRIA UMA CARTA DE CURA ---
    public static Carta criarCura(long identificador, String nome, String descricao, int custoEnergia, int cura) {
        return new Carta(identificador, nome, descricao, TipoCarta.CURA, AlvoCarta.PROPRIO_JOGADOR, custoEnergia, 0, 0, cura, null);
    }

    // --- CRIA UMA CARTA DE SUPORTE ---
    public static Carta criarSuporte(long identificador, String nome, String descricao, int custoEnergia, AlvoCarta alvo, EfeitoStatus efeito) {
        if (efeito == null) {
            throw new IllegalArgumentException("A carta de suporte deve possuir um efeito.");
        }

        return new Carta(identificador, nome, descricao, TipoCarta.SUPORTE, alvo, custoEnergia, 0, 0, 0, efeito);
    }

    // --- VERIFICA SE A CARTA PODE SER UTILIZADA COM A ENERGIA INFORMADA ---
    public boolean podeSerJogada(int energiaDisponivel) {
        return energiaDisponivel >= custoEnergia;
    }

    // --- RETORNA UMA CÓPIA DO EFEITO DA CARTA ---
    public EfeitoStatus criarEfeito() {
        return efeito == null ? null : efeito.copiar();
    }

    // --- VALIDA O IDENTIFICADOR ---
    private void validarIdentificador(long identificador) {
        if (identificador <= 0) {
            throw new IllegalArgumentException("O identificador deve ser maior que zero.");
        }
    }

    // --- VALIDA UM TEXTO OBRIGATÓRIO ---
    private void validarTexto(String texto, String campo) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new IllegalArgumentException(campo + " não pode estar vazio.");
        }
    }

    // --- VALIDA O TIPO E O ALVO DA CARTA ---
    private void validarTipoEAlvo(TipoCarta tipo, AlvoCarta alvo) {
        if (tipo == null) {
            throw new IllegalArgumentException("O tipo da carta não pode ser nulo.");
        }

        if (alvo == null) {
            throw new IllegalArgumentException("O alvo da carta não pode ser nulo.");
        }
    }

    // --- VALIDA OS VALORES NUMÉRICOS DA CARTA ---
    private void validarValores(int custoEnergia, int dano, int defesa, int cura) {
        if (custoEnergia < 0) {
            throw new IllegalArgumentException("O custo de energia não pode ser negativo.");
        }

        if (dano < 0 || defesa < 0 || cura < 0) {
            throw new IllegalArgumentException("Os atributos da carta não podem ser negativos.");
        }
    }

    public long getIdentificador() {
        return identificador;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public TipoCarta getTipo() {
        return tipo;
    }

    public AlvoCarta getAlvo() {
        return alvo;
    }

    public int getCustoEnergia() {
        return custoEnergia;
    }

    public int getDano() {
        return dano;
    }

    public int getDefesa() {
        return defesa;
    }

    public int getCura() {
        return cura;
    }

    public EfeitoStatus getEfeito() {
        return efeito;
    }

    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) {
            return true;
        }

        if (!(objeto instanceof Carta outraCarta)) {
            return false;
        }

        return identificador == outraCarta.identificador;
    }

    @Override
    public int hashCode() {
        return Objects.hash(identificador);
    }

    @Override
    public String toString() {
        StringBuilder texto = new StringBuilder();
        texto.append(nome).append(" | ").append(tipo.getNomeFormatado()).append(" | Energia: ").append(custoEnergia);

        if (dano > 0) {
            texto.append(" | Dano: ").append(dano);
        }

        if (defesa > 0) {
            texto.append(" | Defesa: ").append(defesa);
        }

        if (cura > 0) {
            texto.append(" | Cura: ").append(cura);
        }

        if (efeito != null) {
            texto.append(" | ").append(efeito);
        }

        return texto.toString();
    }
}

