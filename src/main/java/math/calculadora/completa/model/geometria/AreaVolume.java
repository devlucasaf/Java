package math.calculadora.completa.model.geometria;

public class AreaVolume {

    // Áreas
    public static double areaCirculo(double raio) {
        return Math.PI * raio * raio;
    }

    public static double areaTriangulo(double base, double altura) {
        return (base * altura) / 2;
    }

    public static double areaRetangulo(double largura, double altura) {
        return largura * altura;
    }

    public static double areaTrapezio(double baseMaior, double baseMenor, double altura) {
        return ((baseMaior + baseMenor) * altura) / 2;
    }

    // Volumes
    public static double volumeCubo(double lado) {
        return lado * lado * lado;
    }

    public static double volumeEsfera(double raio) {
        return (4.0 / 3.0) * Math.PI * raio * raio * raio;
    }

    public static double volumeCilindro(double raio, double altura) {
        return Math.PI * raio * raio * altura;
    }

    public static double volumeCone(double raio, double altura) {
        return (1.0 / 3.0) * Math.PI * raio * raio * altura;
    }
}
