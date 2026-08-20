package cursos.udemy.aulas.unidade7.aula66;

public class FuncaoSplit {
    public static void main(String[] args) {
        String s = "potato apple lemon";

        String[] vect = s.split(" "); // split divide a string em um array de strings usando o espaço como delimitador
        String word1 = vect[0];
        String word2 = vect[1];
        String word3 = vect[2];

        System.out.println(word1);
        System.out.println(word2);
        System.out.println(word3);
    }
}
