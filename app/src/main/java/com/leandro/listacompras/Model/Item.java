package com.leandro.listacompras.Model;

import java.text.DecimalFormat;

public class Item {
    private String nome;
    private double preco;
    public Item(String nome, double preco) {
        this.nome = nome;
        this.preco = preco;
    }
    public double getPreco() {
        return preco;
    }
    public void setPreco(double preco) {
        this.preco = preco;
    }
    public Item(String nome) {
        this.nome = nome;
    }
    public String getNome() {
        return nome;
    }
    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("0.00");
        return nome + " - R$ " + df.format(preco);
    }
}
