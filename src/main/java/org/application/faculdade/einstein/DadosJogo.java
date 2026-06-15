package org.application.faculdade.einstein;

public class DadosJogo {
    public static final String[] NACIONALIDADES = {
            "Norueguês",
            "Inglês",
            "Sueco",
            "Dinamarquês",
            "Alemão"
    };

    public static final String[] CORES = {
            "Amarela",
            "Azul",
            "Vermelha",
            "Verde",
            "Branca"
    };

    public static final String[] BEBIDAS = {
            "Água",
            "Chá",
            "Leite",
            "Café",
            "Cerveja"
    };

    public static final String[] CIGARROS = {
            "Dunhill",
            "Blends",
            "Pall Mall",
            "Prince",
            "Blue Master"
    };

    public static final String[] ANIMAIS = {
            "Gatos",
            "Cavalos",
            "Pássaros",
            "Peixe",
            "Cachorros"
    };

    public static final String[][] SOLUCAO = {
            // Nacionalidade
            {"Norueguês", "Dinamarquês", "Inglês", "Alemão", "Sueco"},
            // Cor
            {"Amarela", "Azul", "Vermelha", "Verde", "Branca"},
            // Bebida
            {"Água", "Chá", "Leite", "Café", "Cerveja"},
            // Cigarro
            {"Dunhill", "Blends", "Pall Mall", "Prince", "Blue Master"},
            // Animal
            {"Gatos", "Cavalos", "Pássaros", "Peixe", "Cachorros"}
    };

    public static String[] valoresDe(Categoria cat) {
        switch (cat) {
            case NACIONALIDADE:
                return NACIONALIDADES;
            case COR:
                return CORES;
            case BEBIDA:
                return BEBIDAS;
            case CIGARRO:
                return CIGARROS;
            case ANIMAL:
                return ANIMAIS;
            default:
                return new String[0];
        }
    }
}