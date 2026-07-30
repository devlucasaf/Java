package application.utilitarios.trie;

import java.util.HashMap;
import java.util.Map;

public class No {

    Map<Character, No>  filhos = new HashMap<>();
    boolean             fimDePalavra;
    int                 frequencia;
}

