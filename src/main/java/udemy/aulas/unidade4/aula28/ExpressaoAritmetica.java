package udemy.aulas.unidade4.aula28;

public class ExpressaoAritmetica {
    int num1;
    int num2;

    private void soma() {
        System.out.println("Soma: " + (num1 + num2));
    }

    private void subtracao() {
        System.out.println("Subtração: " + (num1 - num2));
    }

    private void multiplicacao() {
        System.out.println("Multiplicação: " + (num1 * num2));
    }

    private void divisao() {
        if (num2 != 0) {
            System.out.println("Divisão: " + (num1 / num2));
        } else {
            System.out.println("Divisão por zero não é permitida.");
        }
    }

    public static void main(String[] args) {
        ExpressaoAritmetica somaNumeros = new ExpressaoAritmetica();
        somaNumeros.num1 = 10;
        somaNumeros.num2 = 5;
        somaNumeros.soma();

        ExpressaoAritmetica subtracaoNumeros = new ExpressaoAritmetica();
        subtracaoNumeros.num1 = 10;
        subtracaoNumeros.num2 = 5;
        subtracaoNumeros.subtracao();

        ExpressaoAritmetica multiplicacaoNumeros = new ExpressaoAritmetica();
        multiplicacaoNumeros.num1 = 10;
        multiplicacaoNumeros.num2 = 5;
        multiplicacaoNumeros.multiplicacao();

        ExpressaoAritmetica divisaoNumeros = new ExpressaoAritmetica();
        divisaoNumeros.num1 = 10;
        divisaoNumeros.num2 = 5;
        divisaoNumeros.divisao();
    }
}
