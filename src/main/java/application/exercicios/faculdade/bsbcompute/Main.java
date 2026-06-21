package application.exercicios.faculdade.bsbcompute;

import application.exercicios.faculdade.bsbcompute.config.*;
import application.exercicios.faculdade.bsbcompute.core.Master;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        Config config = new Config(
                Arrays.asList(
                        new ServidorConfig(1, 3),
                        new ServidorConfig(2, 2),
                        new ServidorConfig(3, 1)
                ),
                Arrays.asList(
                        new RequisicaoConfig(101, "visao", 1, 8.0, null),
                        new RequisicaoConfig(102, "nlp", 3, 3.0, null),
                        new RequisicaoConfig(103, "voz", 2, 5.0, null)
                )
        );

        Master.run(config, "rr");
    }
}