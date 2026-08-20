package cursos.udemy.aulas.unidade7.aula66;

public class FuncaoStrings {
    public static void main(String[] args) {
        String nomeOriginal = "Abcde FGHIJ ABC abc DEFG    ";

        String string1 = nomeOriginal.toLowerCase(); // converte para minúsculas
        String string2 = nomeOriginal.toUpperCase(); // converte para maiúsculas
        String string3 = nomeOriginal.trim(); // remove espaços em branco no início e no final
        String string4 = nomeOriginal.substring(2); // retorna a substring a partir do índice 2
        String string5 = nomeOriginal.substring(2, 9); // retorna a substring do índice 2 até o índice 9
        String string6 = nomeOriginal.replace('a', 'x'); // substitui todas as ocorrências de 'a' por 'x'
        String string7 = nomeOriginal.replace("abc", "xy"); // substitui todas as ocorrências de 'abc' por 'xy'
        int i = nomeOriginal.indexOf("bf"); // retorna o índice da primeira ocorrência de "bc"
        int j = nomeOriginal.lastIndexOf("bc"); // retorna o índice da última ocorrência de "bc"

        System.out.println("Original: -" + nomeOriginal + "-");
        System.out.println("toLowerCase: -" + string1 + "-");
        System.out.println("toUpperCase: -" + string2 + "-");
        System.out.println("trim: -" + string3 + "-");
        System.out.println("substring(2): -" + string4 + "-");
        System.out.println("substring(2, 9): -" + string5 + "-");
        System.out.println("replace('a', 'x'): -" + string6 + "-");
        System.out.println("replace('abc', 'xy'): -" + string7 + "-");
        System.out.println("indexOf('bf'): " + i);
        System.out.println("lastIndexOf('bc'): " + j);
    }
}
