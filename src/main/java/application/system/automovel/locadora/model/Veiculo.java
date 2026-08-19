package application.system.automovel.locadora.model;

import application.system.automovel.locadora.enums.CategoriaVeiculo;

public class Veiculo {
    private static int contadorId = 1;
    private int                 id;
    private String              placa;
    private String              modelo;
    private String              marca;
    private int                 ano;
    private CategoriaVeiculo    categoria;
    private double              valorDiaria;
    private boolean             disponivel;
    private double              quilometragem;

    public Veiculo(String placa, String modelo, String marca, int ano,
                   CategoriaVeiculo categoria, double valorDiaria, double quilometragem) {
        this.id = contadorId++;
        this.placa = placa;
        this.modelo = modelo;
        this.marca = marca;
        this.ano = ano;
        this.categoria = categoria;
        this.valorDiaria = valorDiaria;
        this.disponivel = true;
        this.quilometragem = quilometragem;
    }

    public void alugar() {
        this.disponivel = false;
    }

    public void devolver(double quilometragemAtual) {
        this.disponivel = true;
        this.quilometragem = quilometragemAtual;
    }

    public void exibirInformacoes() {
        System.out.println("--- VEÍCULO ---");
        System.out.println("ID: " + id);
        System.out.println("Placa: " + placa);
        System.out.println("Modelo: " + modelo + " (" + marca + ")");
        System.out.println("Ano: " + ano);
        System.out.println("Categoria: " + categoria);
        System.out.println("Diária: R$" + valorDiaria);
        System.out.println("Disponível: " + (disponivel ? "Sim" : "Não"));
        System.out.println("Quilometragem: " + quilometragem + " km");
    }

    public int getId() {
        return id;
    }

    public String getPlaca() {
        return placa;
    }

    public String getModelo() {
        return modelo;
    }

    public String getMarca() {
        return marca;
    }

    public int getAno() {
        return ano;
    }

    public CategoriaVeiculo getCategoria() {
        return categoria;
    }

    public double getValorDiaria() {
        return valorDiaria;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public double getQuilometragem() {
        return quilometragem;
    }

    public void setQuilometragem(double quilometragem) {
        this.quilometragem = quilometragem;
    }

}
