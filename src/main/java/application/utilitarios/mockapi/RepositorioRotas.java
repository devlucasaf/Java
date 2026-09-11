package application.utilitarios.mockapi;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RepositorioRotas {

    private final List<ConfiguracaoRota>    rotas;
    private long                            proximoIdentificador;

    public RepositorioRotas() {
        this.rotas = new ArrayList<>();
        this.proximoIdentificador = 1;
    }

    // --- ADICIONA UMA NOVA ROTA ---
    public synchronized ConfiguracaoRota adicionar(ConfiguracaoRota rota) {
        validarRota(rota);

        if (existeRota(rota.getMetodo(), rota.getCaminho(), 0)) {
            throw new IllegalArgumentException("Já existe uma rota com o mesmo método e caminho.");
        }

        if (rota.getIdentificador() <= 0 || buscarPorIdentificador(rota.getIdentificador()) != null) {
            rota.setIdentificador(proximoIdentificador);
        }

        rotas.add(rota);
        atualizarProximoIdentificador();
        return rota;
    }

    // --- ATUALIZA UMA ROTA EXISTENTE ---
    public synchronized boolean atualizar(ConfiguracaoRota rotaAtualizada) {
        validarRota(rotaAtualizada);

        ConfiguracaoRota rotaExistente = buscarPorIdentificador(rotaAtualizada.getIdentificador());

        if (rotaExistente == null) {
            return false;
        }

        if (existeRota(rotaAtualizada.getMetodo(), rotaAtualizada.getCaminho(), rotaAtualizada.getIdentificador())) {
            throw new IllegalArgumentException("Já existe outra rota com o mesmo método e caminho.");
        }

        int indice = rotas.indexOf(rotaExistente);
        rotas.set(indice, rotaAtualizada);
        return true;
    }

    // --- REMOVE UMA ROTA ---
    public synchronized boolean remover(long identificador) {
        ConfiguracaoRota rota = buscarPorIdentificador(identificador);
        return rota != null && rotas.remove(rota);
    }

    // --- BUSCA UMA ROTA PELO IDENTIFICADOR ---
    public synchronized ConfiguracaoRota buscarPorIdentificador(long identificador) {
        for (ConfiguracaoRota rota : rotas) {
            if (rota.getIdentificador() == identificador) {
                return rota;
            }
        }

        return null;
    }

    // --- BUSCA UMA ROTA COMPATÍVEL COM A REQUISIÇÃO ---
    public synchronized ConfiguracaoRota buscarCorrespondente(String metodo, String caminho) {
        List<ConfiguracaoRota> candidatas = new ArrayList<>();

        for (ConfiguracaoRota rota : rotas) {
            if (rota.corresponde(metodo, caminho)) {
                candidatas.add(rota);
            }
        }

        candidatas.sort(Comparator.comparingInt(this::contarParametrosDaRota));
        return candidatas.isEmpty() ? null : candidatas.get(0);
    }

    // --- LISTA TODAS AS ROTAS ---
    public synchronized List<ConfiguracaoRota> listarTodas() {
        List<ConfiguracaoRota> copia = new ArrayList<>(rotas);
        copia.sort(Comparator.comparing(ConfiguracaoRota::getCaminho).thenComparing(ConfiguracaoRota::getMetodo));
        return copia;
    }

    // --- SUBSTITUI TODAS AS ROTAS ---
    public synchronized void substituirTodas(List<ConfiguracaoRota> novasRotas) {
        rotas.clear();
        proximoIdentificador = 1;

        if (novasRotas != null) {
            for (ConfiguracaoRota rota : novasRotas) {
                adicionar(rota);
            }
        }
    }

    // --- VERIFICA SE UMA ROTA JÁ EXISTE ---
    private boolean existeRota(String metodo, String caminho, long identificadorIgnorado) {
        for (ConfiguracaoRota rota : rotas) {
            if (rota.getIdentificador() != identificadorIgnorado && rota.getMetodo().equalsIgnoreCase(metodo) && rota.getCaminho().equals(caminho)) {
                return true;
            }
        }

        return false;
    }

    // --- CONTA OS PARÂMETROS EXISTENTES NO CAMINHO ---
    private int contarParametrosDaRota(ConfiguracaoRota rota) {
        int quantidade = 0;

        for (String parte : rota.getCaminho().split("/")) {
            if (parte.startsWith("{") && parte.endsWith("}")) {
                quantidade++;
            }
        }

        return quantidade;
    }

    // --- VALIDA UMA ROTA ---
    private void validarRota(ConfiguracaoRota rota) {
        if (rota == null) {
            throw new IllegalArgumentException("A rota não pode ser nula.");
        }
    }

    // --- ATUALIZA O PRÓXIMO IDENTIFICADOR ---
    private void atualizarProximoIdentificador() {
        long maiorIdentificador = 0;

        for (ConfiguracaoRota rota : rotas) {
            maiorIdentificador = Math.max(maiorIdentificador, rota.getIdentificador());
        }

        proximoIdentificador = maiorIdentificador + 1;
    }
}

