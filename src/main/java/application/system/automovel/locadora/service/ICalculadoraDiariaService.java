package application.system.automovel.locadora.service;

import application.system.automovel.locadora.model.Locacao;

public interface ICalculadoraDiariaService {
    double calcular(Locacao locacao);
}
