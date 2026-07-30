package application.calculadoras.investimentos;

public class Investimentos {

    public static double montanteCompostoMensal(double capital, double taxaMensal, int meses) {
        return capital * Math.pow(1 + taxaMensal, meses);
    }

    public static double aliquotaIR(int dias) {
        if (dias <= 180) return 0.225;
        if (dias <= 360) return 0.20;
        if (dias <= 720) return 0.175;
        return 0.15;
    }

    public static double aliquotaIOF(int dias) {
        if (dias >= 30) return 0;
        double[] tabela = {
            96, 93, 90, 86, 83, 80, 76, 73, 70, 66,
            63, 60, 56, 53, 50, 46, 43, 40, 36, 33,
            30, 26, 23, 20, 16, 13, 10, 6, 3, 0
        };
        return tabela[dias - 1] / 100.0;
    }

    public static class Resultado {
        public final double montanteBruto;
        public final double lucro;
        public final double iof;
        public final double ir;
        public final double montanteLiquido;
        public final double rentabilidadeLiquida;

        public Resultado(double montanteBruto, double lucro, double iof, double ir,
                         double montanteLiquido, double rentabilidadeLiquida) {
            this.montanteBruto = montanteBruto;
            this.lucro = lucro;
            this.iof = iof;
            this.ir = ir;
            this.montanteLiquido = montanteLiquido;
            this.rentabilidadeLiquida = rentabilidadeLiquida;
        }
    }

    public static Resultado calcularCDB(double capital, double taxaAnualPercentualCDI, double cdiAnualPercentual, int dias) {
        double taxaCDBAnual = (cdiAnualPercentual / 100.0) * (taxaAnualPercentualCDI / 100.0);
        double taxaDiaria = Math.pow(1 + taxaCDBAnual, 1.0 / 252) - 1;
        double montante = capital * Math.pow(1 + taxaDiaria, dias * 252.0 / 365);
        double lucro = montante - capital;
        double iof = lucro * aliquotaIOF(dias);
        double ir = (lucro - iof) * aliquotaIR(dias);
        double liquido = montante - iof - ir;
        double rent = (liquido / capital - 1) * 100;
        return new Resultado(montante, lucro, iof, ir, liquido, rent);
    }

    public static Resultado calcularLCI(double capital, double taxaAnualPercentualCDI, double cdiAnualPercentual, int dias) {
        double taxaLCIAnual = (cdiAnualPercentual / 100.0) * (taxaAnualPercentualCDI / 100.0);
        double taxaDiaria = Math.pow(1 + taxaLCIAnual, 1.0 / 252) - 1;
        double montante = capital * Math.pow(1 + taxaDiaria, dias * 252.0 / 365);
        double lucro = montante - capital;
        double rent = (montante / capital - 1) * 100;
        return new Resultado(montante, lucro, 0, 0, montante, rent);
    }

    public static Resultado calcularTesouroSelic(double capital, double selicAnualPercentual, int dias) {
        double taxaDiaria = Math.pow(1 + selicAnualPercentual / 100.0, 1.0 / 252) - 1;
        double montante = capital * Math.pow(1 + taxaDiaria, dias * 252.0 / 365);
        double lucro = montante - capital;
        double iof = lucro * aliquotaIOF(dias);
        double ir = (lucro - iof) * aliquotaIR(dias);
        double liquido = montante - iof - ir;
        double rent = (liquido / capital - 1) * 100;
        return new Resultado(montante, lucro, iof, ir, liquido, rent);
    }
}

