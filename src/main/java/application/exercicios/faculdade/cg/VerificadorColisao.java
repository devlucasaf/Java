package application.exercicios.faculdade.cg;

public class VerificadorColisao {

    public static boolean colidem(Entidade a, Entidade b) {
        float[] aBox = a.getAABB();
        float[] bBox = b.getAABB();

        boolean sobrepoeX = (aBox[0] < bBox[1]) && (aBox[1] > bBox[0]);
        boolean sobrepoeY = (aBox[2] < bBox[3]) && (aBox[3] > bBox[2]);
        return sobrepoeX && sobrepoeY;
    }
}