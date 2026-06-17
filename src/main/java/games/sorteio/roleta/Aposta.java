package games.sorteio.roleta;

public class Aposta {

    private final TipoAposta tipo;
    private final double     valor;
    private final int        numeroEscolhido;

    public Aposta(TipoAposta tipo, double valor) {
        this(tipo, valor, -1);
    }

    public Aposta(TipoAposta tipo, double valor, int numeroEscolhido) {
        this.tipo = tipo;
        this.valor = valor;
        this.numeroEscolhido = numeroEscolhido;
    }

    public TipoAposta getTipo() {
        return tipo;
    }

    public double getValor() {
        return valor;
    }

    public int getNumeroEscolhido() {
        return numeroEscolhido;
    }

    public boolean venceu(int numeroSorteado) {
        if (numeroSorteado == 0) {
            return tipo == TipoAposta.NUMERO_UNICO && numeroEscolhido == 0;
        }

        CorNumero cor = CorNumero.deNumero(numeroSorteado);

        return switch (tipo) {
            case NUMERO_UNICO   -> numeroSorteado == numeroEscolhido;
            case COR_VERMELHA   -> cor == CorNumero.VERMELHO;
            case COR_PRETA      -> cor == CorNumero.PRETO;
            case PAR            -> numeroSorteado % 2 == 0;
            case IMPAR          -> numeroSorteado % 2 != 0;
            case BAIXOS         -> numeroSorteado >= 1  && numeroSorteado <= 18;
            case ALTOS          -> numeroSorteado >= 19 && numeroSorteado <= 36;
            case PRIMEIRA_DUZIA -> numeroSorteado >= 1  && numeroSorteado <= 12;
            case SEGUNDA_DUZIA  -> numeroSorteado >= 13 && numeroSorteado <= 24;
            case TERCEIRA_DUZIA -> numeroSorteado >= 25 && numeroSorteado <= 36;
            case PRIMEIRA_COLUNA -> numeroSorteado % 3 == 1;
            case SEGUNDA_COLUNA  -> numeroSorteado % 3 == 2;
            case TERCEIRA_COLUNA -> numeroSorteado % 3 == 0;
        };
    }

    public double calcularGanho() {
        return valor + (valor * tipo.getMultiplicador());
    }

    @Override
    public String toString() {
        String detalhe = (tipo == TipoAposta.NUMERO_UNICO)
                ? tipo.getDescricao() + " [nº " + numeroEscolhido + "]"
                : tipo.getDescricao();
        return String.format("R$ %.2f em %s", valor, detalhe);
    }
}

