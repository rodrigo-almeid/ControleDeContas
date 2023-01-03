package br.com.rodrigo.ControleDeContas.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Conta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String conta;
    private Double valor;
    private LocalDate vencimento;
    private Integer parcela;
    @Enumerated(EnumType.STRING)
    private StatusConta status = StatusConta.PENDENTE;
    private String descricao;

    public Conta(){

    }
    public Conta(Long id, String conta, Double valor, LocalDate vencimento, Integer parcela, StatusConta status, String descricao) {
        this.id = id;
        this.conta = conta;
        this.valor = valor;
        this.vencimento = vencimento;
        this.parcela = parcela;
        this.status = status;
        this.descricao = descricao;
    }

    public Conta(String conta, Double valor, LocalDate vencimento, String descricao) {
        this.conta = conta;
        this.valor = valor;
        this.vencimento = vencimento;
        this.descricao = descricao;
    }
    public Conta(String conta, Double valor, LocalDate vencimento, String descricao, Integer parcela) {
        this.conta = conta;
        this.valor = valor;
        this.vencimento = vencimento;
        this.descricao = descricao;
        this.parcela = parcela;
    }
}
