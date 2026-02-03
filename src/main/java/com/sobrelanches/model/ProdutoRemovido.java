package com.sobrelanches.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ProdutoRemovido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long produtoId;
    private String nomeProduto;
    private String motivo;
    private LocalDateTime dataRemocao;

    public ProdutoRemovido() {}

    public ProdutoRemovido(Long produtoId, String nomeProduto, String motivo) {
        this.produtoId = produtoId;
        this.nomeProduto = nomeProduto;
        this.motivo = motivo;
        this.dataRemocao = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public Long getProdutoId() { return produtoId; }
    public void setProdutoId(Long produtoId) { this.produtoId = produtoId; }
    public String getNomeProduto() { return nomeProduto; }
    public void setNomeProduto(String nomeProduto) { this.nomeProduto = nomeProduto; }
    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
    public LocalDateTime getDataRemocao() { return dataRemocao; }
    public void setDataRemocao(LocalDateTime dataRemocao) { this.dataRemocao = dataRemocao; }
}
