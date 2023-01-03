package br.com.rodrigo.ControleDeContas.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ListaContasSomaResultadoDto {
    private Double soma;
    private List<ContaDto> contas;


    public ListaContasSomaResultadoDto(Double soma, List<ContaDto> contas) {
        this.soma = soma;
        this.contas = contas;
    }
}
