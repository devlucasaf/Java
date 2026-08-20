package cursos.udemy.aulas.unidade5.aula41;

public class OperadoresLogicos {
    public static void main(String[] args) {
        boolean a = true;
        boolean b = false;

        // Operador AND (&&)
        System.out.println("a && b = " + (a && b));

        // Operador OR (||)
        System.out.println("a || b = " + (a || b));

        // Operador NOT (!)
        System.out.println("!a = " + (!a));
        System.out.println("!b = " + (!b));

        // Operadores de igualdade e desigualdade
        int x = 5, y = 10;
        System.out.println("x == y = " + (x == y));
        System.out.println("x != y = " + (x != y));

        // Combinação de operadores
        System.out.println("(x < y) && a = " + ((x < y) && a));
        System.out.println("(x > y) || b = " + ((x > y) || b));
    }
}
