package application.exercicios.basico.funcao;

public class Funcoes {
    public static void main(String[] args) {
        soma(4,3,2);
        subtracao(4,3,2);
        multiplicacao(4,3,2);
        divisao(4,3,2);
        media(4,3,2);
        potencia(4,3);
        raizQuadrada(4);
        raizCubica(4);
        fatorial(4);
        logaritmo(4);
        logaritmoBase10(4);
        logaritmoBase2(4);
        seno(4);
        cosseno(4);
        tangente(4);
        arcoSeno(4);
        arcoCosseno(4);
        arcoTangente(4);
        arredondar(4);
        arredondarParaCima(4);
        arredondarParaBaixo(4);
        valorAbsoluto(4);
        maximo(4, 3);
        minimo(4, 3);
        numeroAleatorio();
        numeroAleatorioEntre(4, 3);
        numeroAleatorioInteiroEntre(4, 3);
    }

    private static void soma(int x, int y, int z) {
        System.out.println("Soma: " + (x + y + z));
    }

    private static void subtracao(int x, int y, int z) {
        System.out.println("Subtração: " + (x - y - z));
    }

    private static void multiplicacao(int x, int y, int z) {
        System.out.println("Multiplicação: " + (x * y * z));
    }

    private static void divisao(int x, int y, int z) {
        System.out.println("Divisão: " + (x / y / z));
    }

    private static void media(int x, int y, int z) {
        System.out.println("Média: " + ((x + y + z) / 3));
    }

    private static void potencia(int x, int y) {
        System.out.println("Potência: " + Math.pow(x, y));
    }

    private static void raizQuadrada(int x) {
        System.out.println("Raiz Quadrada: " + Math.sqrt(x));
    }

    private static void raizCubica(int x) {
        System.out.println("Raiz Cúbica: " + Math.cbrt(x));
    }

    private static void fatorial(int x) {
        int resultado = 1;
        for (int i = 1; i <= x; i++) {
            resultado *= i;
        }
        System.out.println("Fatorial: " + resultado);
    }

    private static void logaritmo(int x) {
        System.out.println("Logaritmo: " + Math.log(x));
    }

    private static void logaritmoBase10(int x) {
        System.out.println("Logaritmo Base 10: " + Math.log10(x));
    }

    private static void logaritmoBase2(int x) {
        System.out.println("Logaritmo Base 2: " + (Math.log(x) / Math.log(2)));
    }

    private static void seno(int x) {
        System.out.println("Seno: " + Math.sin(x));
    }

    private static void cosseno(int x) {
        System.out.println("Cosseno: " + Math.cos(x));
    }

    private static void tangente(int x) {
        System.out.println("Tangente: " + Math.tan(x));
    }

    private static void arcoSeno(int x) {
        System.out.println("Arco Seno: " + Math.asin(x));
    }

    private static void arcoCosseno(int x) {
        System.out.println("Arco Cosseno: " + Math.acos(x));
    }

    private static void arcoTangente(int x) {
        System.out.println("Arco Tangente: " + Math.atan(x));
    }

    private static void arredondar(int x) {
        System.out.println("Arredondar: " + Math.round(x));
    }

    private static void arredondarParaCima(int x) {
        System.out.println("Arredondar Para Cima: " + Math.ceil(x));
    }

    private static void arredondarParaBaixo(int x) {
        System.out.println("Arredondar Para Baixo: " + Math.floor(x));
    }

    private static void valorAbsoluto(int x) {
        System.out.println("Valor Absoluto: " + Math.abs(x));
    }

    private static void maximo(int x, int y) {
        System.out.println("Máximo: " + Math.max(x, y));
    }

    private static void minimo(int x, int y) {
        System.out.println("Mínimo: " + Math.min(x, y));
    }

    private static void numeroAleatorio() {
        System.out.println("Número Aleatório: " + Math.random());
    }

    private static void numeroAleatorioEntre(int x, int y) {
        System.out.println("Número Aleatório Entre " + x + " e " + y + ": " + (Math.random() * (y - x) + x));
    }

    private static void numeroAleatorioInteiroEntre(int x, int y) {
        System.out.println("Número Aleatório Inteiro Entre " + x + " e " + y + ": " + ((int) (Math.random() * (y - x + 1) + x)));
    }

}
