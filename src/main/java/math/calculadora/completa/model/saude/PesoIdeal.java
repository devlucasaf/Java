package math.calculadora.completa.model.saude;

public class PesoIdeal {

    public static double calcularPesoIdeal(double altura, char sexo) {
        if (altura <= 0) {
            throw new IllegalArgumentException("Altura deve ser positiva.");
        }

        double peso;
        if (sexo == 'M' || sexo == 'm') {
            peso = (altura * 100 - 100) - (altura * 100 - 150) / 4.0;
        } else if (sexo == 'F' || sexo == 'f') {
            peso = (altura * 100 - 100) - (altura * 100 - 150) / 2.5;
        } else {
            throw new IllegalArgumentException("Sexo deve ser 'M' ou 'F'");
        }
        return peso;
    }
}
