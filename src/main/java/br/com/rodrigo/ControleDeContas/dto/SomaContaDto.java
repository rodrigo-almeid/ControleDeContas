package br.com.rodrigo.ControleDeContas.dto;

import br.com.rodrigo.ControleDeContas.model.StatusConta;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class SomaContaDto {
    private Double valor;
    private LocalDate vencimento;
    private StatusConta status;

    public SomaContaDto(Double valor, LocalDate vencimento, StatusConta status) {
        this.valor = valor;
        this.vencimento = vencimento;
        this.status = status;
    }
}
