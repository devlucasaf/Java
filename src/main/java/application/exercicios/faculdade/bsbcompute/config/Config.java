package application.exercicios.faculdade.bsbcompute.config;

import java.util.List;

public class Config {
    public List<ServidorConfig>     servidores;
    public List<RequisicaoConfig>   requisicoes;

    public Config(List<ServidorConfig> servidores, List<RequisicaoConfig> requisicoes) {
        this.servidores = servidores;
        this.requisicoes = requisicoes;
    }
}
