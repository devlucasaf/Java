package games.puzzle.nonogram;

public class ValidadorNonogram {

    // --- VERIFICA SE UMA JOGADA ESPECÍFICA ESTÁ CORRETA ---
    public boolean isCelulaCorreta(TabuleiroNonogram tabuleiro, int linha, int coluna) {
        validarTabuleiro(tabuleiro);

        EstadoCelula estadoJogador = tabuleiro.getEstadoCelula(linha, coluna);

        if (estadoJogador == EstadoCelula.DESCONHECIDA) {
            return true;
        }

        boolean deveriaEstarPreenchida = tabuleiro.isCelulaSolucaoPreenchida(linha, coluna);

        if (estadoJogador == EstadoCelula.PREENCHIDA) {
            return deveriaEstarPreenchida;
        }

        return !deveriaEstarPreenchida;
    }

    // --- VERIFICA SE TODAS AS MARCAÇÕES ATUAIS ESTÃO CORRETAS ---
    public boolean isProgressoValido(TabuleiroNonogram tabuleiro) {
        validarTabuleiro(tabuleiro);

        for (int linha = 0; linha < tabuleiro.getQuantidadeLinhas(); linha++) {
            for (int coluna = 0; coluna < tabuleiro.getQuantidadeColunas(); coluna++) {
                if (!isCelulaCorreta(tabuleiro, linha, coluna)) {
                    return false;
                }
            }
        }

        return true;
    }

    // --- CONTA QUANTAS MARCAÇÕES DO JOGADOR ESTÃO INCORRETAS ---
    public int contarErros(TabuleiroNonogram tabuleiro) {
        validarTabuleiro(tabuleiro);

        int quantidadeErros = 0;

        for (int linha = 0; linha < tabuleiro.getQuantidadeLinhas(); linha++) {
            for (int coluna = 0; coluna < tabuleiro.getQuantidadeColunas(); coluna++) {
                EstadoCelula estado = tabuleiro.getEstadoCelula(linha, coluna);

                if (estado != EstadoCelula.DESCONHECIDA && !isCelulaCorreta(tabuleiro, linha, coluna)) {
                    quantidadeErros++;
                }
            }
        }

        return quantidadeErros;
    }

    // --- CALCULA A PORCENTAGEM DE CÉLULAS RESOLVIDAS CORRETAMENTE ---
    public double calcularProgresso(TabuleiroNonogram tabuleiro) {
        validarTabuleiro(tabuleiro);

        int quantidadeCelulasCorretas = 0;
        int quantidadeTotalCelulas = tabuleiro.getQuantidadeLinhas() * tabuleiro.getQuantidadeColunas();

        for (int linha = 0; linha < tabuleiro.getQuantidadeLinhas(); linha++) {
            for (int coluna = 0; coluna < tabuleiro.getQuantidadeColunas(); coluna++) {
                EstadoCelula estado = tabuleiro.getEstadoCelula(linha, coluna);

                if (estado != EstadoCelula.DESCONHECIDA && isCelulaCorreta(tabuleiro, linha, coluna)) {
                    quantidadeCelulasCorretas++;
                }
            }
        }

        return quantidadeCelulasCorretas * 100.0 / quantidadeTotalCelulas;
    }

    // --- VERIFICA SE O TABULEIRO FOI COMPLETAMENTE RESOLVIDO ---
    public boolean isConcluido(TabuleiroNonogram tabuleiro) {
        validarTabuleiro(tabuleiro);

        for (int linha = 0; linha < tabuleiro.getQuantidadeLinhas(); linha++) {
            for (int coluna = 0; coluna < tabuleiro.getQuantidadeColunas(); coluna++) {
                EstadoCelula estado = tabuleiro.getEstadoCelula(linha, coluna);

                if (estado == EstadoCelula.DESCONHECIDA || !isCelulaCorreta(tabuleiro, linha, coluna)) {
                    return false;
                }
            }
        }

        return true;
    }

    // --- VERIFICA SE UMA LINHA ESTÁ COMPLETA E CORRETA ---
    public boolean isLinhaConcluida(TabuleiroNonogram tabuleiro, int linha) {
        validarTabuleiro(tabuleiro);

        if (linha < 0 || linha >= tabuleiro.getQuantidadeLinhas()) {
            throw new IndexOutOfBoundsException("Linha inválida: " + linha);
        }

        for (int coluna = 0; coluna < tabuleiro.getQuantidadeColunas(); coluna++) {
            if (tabuleiro.getEstadoCelula(linha, coluna) == EstadoCelula.DESCONHECIDA || !isCelulaCorreta(tabuleiro, linha, coluna)) {
                return false;
            }
        }

        return true;
    }

    // --- VERIFICA SE UMA COLUNA ESTÁ COMPLETA E CORRETA ---
    public boolean isColunaConcluida(TabuleiroNonogram tabuleiro, int coluna) {
        validarTabuleiro(tabuleiro);

        if (coluna < 0 || coluna >= tabuleiro.getQuantidadeColunas()) {
            throw new IndexOutOfBoundsException("Coluna inválida: " + coluna);
        }

        for (int linha = 0; linha < tabuleiro.getQuantidadeLinhas(); linha++) {
            if (tabuleiro.getEstadoCelula(linha, coluna) == EstadoCelula.DESCONHECIDA || !isCelulaCorreta(tabuleiro, linha, coluna)) {
                return false;
            }
        }

        return true;
    }

    // --- VERIFICA SE O TABULEIRO FOI INFORMADO ---
    private void validarTabuleiro(TabuleiroNonogram tabuleiro) {
        if (tabuleiro == null) {
            throw new IllegalArgumentException("O tabuleiro não pode ser nulo.");
        }
    }
}

