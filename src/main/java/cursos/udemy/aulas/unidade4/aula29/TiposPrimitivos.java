package cursos.udemy.aulas.unidade4.aula29;

public class TiposPrimitivos {
    String  nome = "Leanderson";
    int     idade = 25;
    double  altura = 1.68;
    char    sexo = 'F';

    public static void main(String[] args) {
        TiposPrimitivos tiposPrimitivos = new TiposPrimitivos();
        System.out.println("Nome: " + tiposPrimitivos.nome);
        System.out.println("Idade: " + tiposPrimitivos.idade);
        System.out.println("Altura: " + tiposPrimitivos.altura);
        System.out.println("Sexo: " + tiposPrimitivos.sexo);
    }
}
