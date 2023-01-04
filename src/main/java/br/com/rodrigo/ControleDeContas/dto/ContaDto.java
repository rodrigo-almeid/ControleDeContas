package br.com.rodrigo.ControleDeContas.dto;

import br.com.rodrigo.ControleDeContas.model.Conta;
import br.com.rodrigo.ControleDeContas.model.StatusConta;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
@Getter
public class ContaDto {
    private Long id;
    private String conta;
    private Double valor;
    private LocalDate vencimento;
    private StatusConta status;
    private String descricao;

    public ContaDto (Conta conta){
        this.id = conta.getId();
        this.conta = conta.getConta();
        this.valor = conta.getValor();
        this.vencimento = conta.getVencimento();
        this.status = conta.getStatus();
        this.descricao = conta.getDescricao();
    }

    public static List<ContaDto> converter(List<Conta> contas) {
        return contas.stream().map(ContaDto::new).collect(Collectors.toList());
    }
    public static ContaDto converterum(Conta conta){
        return new ContaDto(conta);
    }
}
