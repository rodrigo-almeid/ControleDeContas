package br.com.rodrigo.ControleDeContas.service;

import br.com.rodrigo.ControleDeContas.dto.ContaDto;
import br.com.rodrigo.ControleDeContas.dto.ListaContasSomaResultadoDto;
import br.com.rodrigo.ControleDeContas.controller.form.ContaForm;
import br.com.rodrigo.ControleDeContas.controller.form.PagarContaForm;
import br.com.rodrigo.ControleDeContas.model.Conta;
import br.com.rodrigo.ControleDeContas.model.StatusConta;
import br.com.rodrigo.ControleDeContas.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ContaService {

    @Autowired
    private ContaRepository contaRepository;



    public ResponseEntity<ListaContasSomaResultadoDto> lista(LocalDate inicio, LocalDate fim, String status) {
        List<Conta> contas;
        List<ContaDto> contasDto;
        ListaContasSomaResultadoDto resultado;

        if (status != null && inicio != null && fim != null) {
            contas = contaRepository.findByVencimentoBetweenAndStatus(inicio, fim, StatusConta.valueOf(status), Sort.by(Sort.Direction.ASC, "vencimento"));
        } else {
            contas = verificarDatas(inicio, fim);
        }

        contasDto = ContaDto.converter(contas);
        resultado = new ListaContasSomaResultadoDto(
                contas.stream().mapToDouble(Conta::getValor).sum(),
                contasDto
        );
        return ResponseEntity.ok(resultado);
    }

    public ResponseEntity<ContaDto> detalhar(@PathVariable Long id) {

        Optional<Conta> conta = contaRepository.findById(id);
        if (conta.isPresent()) {
            return ResponseEntity.ok(new ContaDto(conta.get()));
        }
        return ResponseEntity.notFound().build();
    }
    public List<ContaDto> cadastrar(ContaForm contaForm, UriComponentsBuilder uriBuilder){
        if (contaForm.getParcela() == null) {
            contaForm.setParcela(1);
        }
        Integer i = contaForm.getParcela();
        List<Conta> contaList = new ArrayList<>();
        while (i != 0) {
            Conta conta = contaForm.converter(contaForm);
            conta.setVencimento(conta.getVencimento().plusMonths(i - 1));
            conta.setStatus(StatusConta.PENDENTE);
            contaList.add(conta);
            i--;
        }
        List<Conta> contas = contaRepository.saveAll(contaList);
        return ContaDto.converter(contas);

    }
    public ContaDto pagar(Long id) {
    Conta conta = contaRepository.getOne(id);
    conta.setStatus(StatusConta.PAGO);
    contaRepository.save(conta);
    ContaDto contaDto = ContaDto.converterum(conta);
    return  contaDto;

    }

    public ContaDto ajustar(Long id, double valor){
        Conta conta = contaRepository.getOne(id);
        conta.setValor(valor);
        contaRepository.save(conta);
        ContaDto contaDto = ContaDto.converterum(conta);
        return  contaDto;
    }
    private List<Conta> verificarDatas(LocalDate inicio, LocalDate fim) {
        List<Conta> contaList = new ArrayList<>();
        if (inicio != null && fim != null) {
            contaList = contaRepository.findByVencimentoBetween(inicio, fim, Sort.by(Sort.Direction.ASC, "vencimento"));
        }
        if (inicio != null && fim == null) {
            contaList = contaRepository.findByVencimentoGreaterThanEqual(inicio);
        }
        if (inicio == null && fim == null) {
            contaList = contaRepository.findAll();
        }
        return contaList;
    }
}

