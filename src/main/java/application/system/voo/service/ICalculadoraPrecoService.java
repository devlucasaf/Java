package application.system.voo.service;

import application.system.voo.enums.ClasseAssento;
import application.system.voo.model.Voo;

public interface ICalculadoraPrecoService {
    double calcular(Voo voo, ClasseAssento classe);
}
