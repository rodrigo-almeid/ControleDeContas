package br.com.rodrigo.ControleDeContas.controller.form;

import br.com.rodrigo.ControleDeContas.model.Conta;
import br.com.rodrigo.ControleDeContas.model.StatusConta;
import br.com.rodrigo.ControleDeContas.repository.ContaRepository;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagarContaForm {
    private StatusConta statusConta;

    public Conta pagar(Long id, ContaRepository contaRepository){
        Conta conta = contaRepository.getOne(id);
        conta.setStatus(StatusConta.PAGO);
        return conta;

    }

    public Conta ajuste(Long id, ContaRepository contaRepository) {
        Conta conta = contaRepository.getOne(id);
        return conta;
    }
}
