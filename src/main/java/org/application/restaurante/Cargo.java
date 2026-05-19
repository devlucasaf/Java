package org.application.restaurante;

public enum Cargo {
    GARCOM("Garçom"),
    COZINHEIRO("Cozinheiro"),
    CHEF("Chef de Cozinha"),
    GERENTE("Gerente"),
    BARMAN("Barman"),
    AUXILIAR_COZINHA("Auxiliar de Cozinha"),
    RECEPCIONISTA("Recepcionista");

    private final String descricao;

    Cargo(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}

