package application.system.automovel.locadora.service;

import application.system.automovel.locadora.enums.TipoSeguro;
import application.system.automovel.locadora.model.Locacao;

public class CalculadoraPadraoService implements ICalculadoraDiariaService {
    @Override
    public double calcular(Locacao locacao) {
        long dias = locacao.calcularDiasPrevistos();
        if (dias <= 0) {
            dias = 1;
        }

        double valorDiaria = locacao.getVeiculo().getValorDiaria();
        double valorBase = valorDiaria * dias;

        double adicionalSeguro = 0.0;
        if (locacao.getSeguro() == TipoSeguro.COMPLETO) {
            adicionalSeguro = 50.0 * dias;
        } else if (locacao.getSeguro() == TipoSeguro.APENAS_TERCEIROS) {
            adicionalSeguro = 20.0 * dias;
        }

        double desconto = 0.0;
        if (locacao.getCliente().getPontosFidelidade() >= 100) {
            desconto = 0.05;
        }

        double total = (valorBase + adicionalSeguro) * (1 - desconto);
        return Math.round(total * 100.0) / 100.0;
    }
}
