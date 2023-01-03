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
    public static List<ContaDto> cadastrar(ContaForm form, UriComponentsBuilder uriBuilder) {
        if (form.getParcela() == null) {
            form.setParcela(1);
        }
        Integer i = form.getParcela();
        while (i != 0) {
            Conta conta = form.converter(contaRepository);
            contaRepository.save(conta);
            conta.setVencimento(conta.getVencimento().plusMonths(i - 1));
            i--;
        }
        List<ContaDto> contasDto = ContaDto.converter(contaRepository.findByContaAndValor(form.getConta(), form.getValor()));
        return contasDto;
    }
//    public static ResponseEntity<ContaDto> pagar(Long id, PagarContaForm form) {
//        Optional<Conta> optional = contaRepository.findById(id);
//        if (optional.isPresent()) {
//            Conta conta = form.pagar(id, contaRepository);
//            return ResponseEntity.ok(new ContaDto(conta));
//        }
//        return ResponseEntity.notFound().build();
//    }
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

