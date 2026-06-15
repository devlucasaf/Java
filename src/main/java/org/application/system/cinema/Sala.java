package org.application.system.cinema;

import java.util.ArrayList;
import java.util.List;

public class Sala {
    private static int contadorId = 1;
    private int             id;
    private int             numero;
    private TipoSala        tipo;
    private int             capacidade;
    private List<Assento>   assentos;
    private boolean         ativa;

    public Sala(int numero, TipoSala tipo, int capacidade) {
        this.id = contadorId++;
        this.numero = numero;
        this.tipo = tipo;
        this.capacidade = capacidade;
        this.assentos = new ArrayList<>();
        this.ativa = true;
        inicializarAssentos();
    }

    private void inicializarAssentos() {
        char[] letras = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
        int fileiras = (int) Math.ceil(capacidade / 10.0);
        for (int i = 0; i < fileiras && i < letras.length; i++) {
            for (int j = 1; j <= 10 && ((i * 10) + j) <= capacidade; j++) {
                assentos.add(new Assento(letras[i], j));
            }
        }
    }

    public Assento buscarAssento(String codigo) {
        for (Assento a : assentos) {
            if (a.getCodigo().equalsIgnoreCase(codigo)) {
                return a;
            }
        }
        return null;
    }

    public void exibirMapaAssentos() {
        System.out.println("Sala " + numero + " (" + tipo + ") - Mapa de assentos:");
        for (Assento a : assentos) {
            System.out.print(a.getCodigo() + "[" + (a.isDisponivel() ? " " : "X") + "] ");
            if (a.getNumero() % 10 == 0) {
                System.out.println();
            }
        }
        System.out.println();
    }

    public int getId() {
        return id;
    }

    public int getNumero() {
        return numero;
    }

    public TipoSala getTipo() {
        return tipo;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public List<Assento> getAssentos() {
        return assentos;
    }

    public boolean isAtiva() {
        return ativa;
    }
}