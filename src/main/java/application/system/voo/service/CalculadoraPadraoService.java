package application.system.voo.service;

import application.system.voo.enums.CategoriaVoo;
import application.system.voo.enums.ClasseAssento;
import application.system.voo.model.Voo;

public class CalculadoraPadraoService implements ICalculadoraPrecoService {

    @Override
    public double calcular(Voo voo, ClasseAssento classe) {
        double preco = voo.getPrecoBase();

        if (voo.getCategoria() == CategoriaVoo.INTERNACIONAL) {
            preco *= 1.4;
        }

        switch (classe) {
            case EXECUTIVA:
                preco *= 1.6;
                break;
            case PRIMEIRA_CLASSE:
                preco *= 2.5;
                break;
            case ECONOMICA:
            default:
                break;
        }

        return Math.round(preco * 100.0) / 100.0;
    }
}
