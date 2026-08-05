package math.calculadora.completa.model.conversoes;

import math.calculadora.completa.service.MoedaService;

public class ConversorMoeda {

    private final MoedaService service;

    public ConversorMoeda() {
        this.service = new MoedaService();
    }

    public double converter(double valor, String de, String para) throws Exception {
        if (de.equals(para)) {
            return valor;
        }
        return service.buscarConversao(valor, de, para);
    }
}
