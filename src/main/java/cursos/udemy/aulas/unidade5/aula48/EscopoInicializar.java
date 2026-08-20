package cursos.udemy.aulas.unidade5.aula48;

public class EscopoInicializar {
    public static void main(String[] args) {
        double preco = 400.00;
        double desconto;

        if (preco < 200.00) {
            desconto = preco * 0.1;
        } else {
            desconto = 0;
        }
        System.out.print("Desconto: " + desconto);
    }
}
