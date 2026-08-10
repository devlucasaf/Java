package math.calculadora.completa.model.calculos;

public class Pitagoras {
    public static double calcularHipotenusa(double catetoA, double catetoB) {
        if (catetoA <= 0 || catetoB <= 0) {
            throw new IllegalArgumentException("Catetos devem ser positivos");
        }
        return Math.sqrt(catetoA * catetoA + catetoB * catetoB);
    }

    public static double calcularCateto(double hipotenusa, double catetoConhecido) {
        if (hipotenusa <= 0 || catetoConhecido <= 0) {
            throw new IllegalArgumentException("Valores devem ser positivos");
        }

        if (hipotenusa <= catetoConhecido) {
            throw new IllegalArgumentException("Hipotenusa deve ser maior que o cateto");
        }
        return Math.sqrt(hipotenusa * hipotenusa - catetoConhecido * catetoConhecido);
    }
}
