package math.calculadora.completa.util;

public class Constantes {

    // Unidades e fatores para conversores genéricos
    public static final String[]    UNIDADES_VOLUME = {"Litros", "Mililitros", "Metros Cúbicos"};
    public static final double[]    FATORES_VOLUME = {1.0, 1000.0, 0.001};

    public static final String[]    UNIDADES_COMPRIMENTO = {"Metros", "Centímetros", "Milímetros", "Quilômetros"};
    public static final double[]    FATORES_COMPRIMENTO = {1.0, 100.0, 1000.0, 0.001};

    public static final String[]    UNIDADES_MASSA = {"Gramas", "Quilogramas", "Toneladas"};
    public static final double[]    FATORES_MASSA = {1.0, 0.001, 0.000001};

    public static final String[]    UNIDADES_AREA = {"Metros Quadrados", "Hectares", "Quilômetros Quadrados"};
    public static final double[]    FATORES_AREA = {1.0, 0.0001, 0.000001};

    public static final String[]    UNIDADES_VELOCIDADE = {"km/h", "m/s", "mph"};
    public static final double[]    FATORES_VELOCIDADE = {1.0, 0.2777778, 0.621371};

    public static final String[]    UNIDADES_TEMPO = {"Segundos", "Minutos", "Horas", "Dias"};
    public static final double[]    FATORES_TEMPO = {1.0, 1.0/60, 1.0/3600, 1.0/86400};

    public static final String[]    UNIDADES_PRESSAO = {"Pascal", "Bar", "atm"};
    public static final double[]    FATORES_PRESSAO = {1.0, 0.00001, 0.00000986923};

    public static final String[]    UNIDADES_DADOS = {"Bytes", "KB", "MB", "GB", "TB"};
    public static final double[]    FATORES_DADOS = {1.0, 1024.0, 1024.0*1024, 1024.0*1024*1024, 1024.0*1024*1024*1024};

    public static final String[]    MOEDAS = {"USD", "EUR", "BRL", "GBP", "JPY", "CAD", "AUD", "CHF", "CNY", "ARS"};
    public static final String      API_MOEDA = "https://api.frankfurter.app/latest?amount=%s&from=%s&to=%s";

    public static final String[]    UNIDADES_ANGULO = {"Graus", "Radianos"};
    public static final String[]    BASES_FORMATOS = {"Hexadecimal", "Decimal", "Octal", "Binário"};
    public static final int[]       BASES_VALORES = {16, 10, 8, 2};

    // Unidades de combustível
    public static final String[]    UNIDADES_COMBUSTIVEL = {"L/100km", "km/L", "MPG (EUA)"};

    // Constantes para conversão de combustível
    public static final double      KM_POR_MILHA = 1.60934;
    public static final double      LITROS_POR_GALAO = 3.78541;

    // Operações da tabela verdade
    public static final String[]    OPERACOES_TABELA_VERDADE = {
            "A AND B", "A OR B", "A XOR B", "NOT A",
            "A NAND B", "A NOR B", "A XNOR B",
            "(A AND B) OR C", "(A OR B) AND C", "A AND B AND C", "A OR B OR C"
    };

    // Operações bitwise
    public static final String[]    OPERACOES_BITWISE = {
            "AND", "OR", "XOR", "NOT (A)", "A << B", "A >> B"
    };
}
