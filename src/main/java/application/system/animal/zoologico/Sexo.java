package application.system.animal.zoologico;

public enum Sexo {
    MACHO("Macho"),
    FEMEA("Femea");

    private final String descricao;

    Sexo(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}

