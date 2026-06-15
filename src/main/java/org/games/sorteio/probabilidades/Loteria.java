package org.games.sorteio.probabilidades;

import java.math.BigInteger;

public enum Loteria {
    MEGA_SENA   ("Mega-Sena",  60, 6, 5.00),
    QUINA       ("Quina",      80, 5, 2.50),
    LOTOFACIL   ("Lotofácil",  25, 15, 3.00),
    DUPLA_SENA  ("Dupla Sena", 50, 6, 2.50),
    LOTOMANIA   ("Lotomania", 100, 20, 3.00),
    TIMEMANIA   ("Timemania",  80, 10, 3.50);

    private final String nome;
    private final int    universo;
    private final int    aposta;
    private final double valorAposta;

    Loteria(String nome, int universo, int aposta, double valorAposta) {
        this.nome = nome;
        this.universo = universo;
        this.aposta = aposta;
        this.valorAposta = valorAposta;
    }

    public String getNome() {
        return nome;
    }

    public int getUniverso() {
        return universo;
    }

    public int getAposta() {
        return aposta;
    }

    public double getValorAposta() {
        return valorAposta;
    }

    public BigInteger combinacoes() {
        return combinacao(universo, aposta);
    }

    public double probabilidade() {
        BigInteger c = combinacoes();
        return 1.0 / c.doubleValue();
    }

    public double custoMedioParaGanhar() {
        return combinacoes().doubleValue() * valorAposta;
    }

    public static BigInteger combinacao(int n, int k) {
        if (k < 0 || k > n) {
            return BigInteger.ZERO;
        }

        if (k > n - k) {
            k = n - k;
        }

        BigInteger resultado = BigInteger.ONE;
        for (int i = 0; i < k; i++) {
            resultado = resultado.multiply(BigInteger.valueOf(n - i));
            resultado = resultado.divide(BigInteger.valueOf(i + 1));
        }
        return resultado;
    }
}

