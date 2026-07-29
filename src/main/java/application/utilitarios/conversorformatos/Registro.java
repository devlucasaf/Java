package application.utilitarios.conversorformatos;

import java.util.LinkedHashMap;
import java.util.Map;

public class Registro {

    private final Map<String, String> campos = new LinkedHashMap<>();

    public Registro set(String chave, String valor) {
        campos.put(chave, valor);
        return this;
    }

    public String get(String chave) {
        return campos.get(chave);
    }

    public Map<String, String> getCampos() {
        return campos;
    }

    @Override
    public String toString() {
        return campos.toString();
    }
}

