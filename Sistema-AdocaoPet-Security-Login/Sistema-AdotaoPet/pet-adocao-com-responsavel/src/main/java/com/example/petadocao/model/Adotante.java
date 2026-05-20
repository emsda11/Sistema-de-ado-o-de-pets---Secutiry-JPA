package com.example.petadocao.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Adotante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;
    private String telefone;
    private String cidade;
    private Integer idPetDesejado;

    public Adotante() {
    }

    public Adotante(Integer id, String nome, String telefone, String cidade, Integer idPetDesejado) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.cidade = cidade;
        this.idPetDesejado = idPetDesejado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public Integer getIdPetDesejado() {
        return idPetDesejado;
    }

    public void setIdPetDesejado(Integer idPetDesejado) {
        this.idPetDesejado = idPetDesejado;
    }
}
