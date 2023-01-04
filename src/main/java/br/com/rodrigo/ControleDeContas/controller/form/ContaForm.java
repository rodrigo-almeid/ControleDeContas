package br.com.rodrigo.ControleDeContas.controller.form;

import br.com.rodrigo.ControleDeContas.model.Conta;
import br.com.rodrigo.ControleDeContas.repository.ContaRepository;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ContaForm {
    @Id    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String conta;
    private Double valor;
    private LocalDate vencimento;
    private String descricao;
    private Integer parcela;
    public Conta converter(ContaForm form) {
        return               Conta.builder()
                .id(form.getId())
                .conta(form.getConta())
                .valor(form.getValor())
                .vencimento(form.getVencimento())
                .parcela(form.getParcela())
                .descricao(form.getDescricao())
                .build();
    }

}