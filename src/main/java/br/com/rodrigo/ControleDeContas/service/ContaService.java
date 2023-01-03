package br.com.rodrigo.ControleDeContas.service;

import br.com.rodrigo.ControleDeContas.controller.dto.ContaDto;
import br.com.rodrigo.ControleDeContas.controller.dto.ListaContasSomaResultadoDto;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ContaService {

    @Autowired
    private static ContaRepository contaRepository;

    public ResponseEntity<ListaContasSomaResultadoDto> lista(LocalDate inicio, LocalDate fim, String status) {
        if (status == null) {
            if (inicio != null && fim != null) {
                List<Conta> contas = contaRepository.findByVencimentoBetween(inicio, fim, Sort.by(Sort.Direction.ASC, "vencimento"));
                Double valor = contas.stream().mapToDouble(Conta::getValor).sum();
                List<ContaDto> contasDto = ContaDto.converter(contas);
                ListaContasSomaResultadoDto resultado = new ListaContasSomaResultadoDto(valor, contasDto);
                return ResponseEntity.ok(resultado);
            }
            if (inicio != null && fim == null) {
                List<Conta> contas = contaRepository.findByVencimentoGreaterThanEqual(inicio);
                Double valor = contas.stream().mapToDouble(Conta::getValor).sum();
                List<ContaDto> contasDto = ContaDto.converter(contas);
                ListaContasSomaResultadoDto resultado = new ListaContasSomaResultadoDto(valor, contasDto);
                return ResponseEntity.ok(resultado);
            }
            List<Conta> contas = contaRepository.findAll();
            Double valor = contas.stream().mapToDouble(Conta::getValor).sum();
            List<ContaDto> contasDto = ContaDto.converter(contas);
            ListaContasSomaResultadoDto resultado = new ListaContasSomaResultadoDto(valor, contasDto);
            return ResponseEntity.ok(resultado);

        } else {
            if (inicio != null && fim != null) {
                List<Conta> contas = contaRepository.findByVencimentoBetweenAndStatus(inicio, fim, StatusConta.valueOf(status), Sort.by(Sort.Direction.ASC, "vencimento"));
                Double valor = contas.stream().mapToDouble(Conta::getValor).sum();
                List<ContaDto> contasDto = ContaDto.converter(contas);
                ListaContasSomaResultadoDto resultado = new ListaContasSomaResultadoDto(valor, contasDto);
                return ResponseEntity.ok(resultado);
            } else if
            (inicio != null && fim == null) {
                List<Conta> contas = contaRepository.findByVencimentoGreaterThanEqual(inicio);
                Double valor = contas.stream().mapToDouble(Conta::getValor).sum();
                List<ContaDto> contasDto = ContaDto.converter(contas);
                ListaContasSomaResultadoDto resultado = new ListaContasSomaResultadoDto(valor, contasDto);
                return ResponseEntity.ok(resultado);
            }
            List<Conta> contas = contaRepository.findAll();
            Double valor = contas.stream().mapToDouble(Conta::getValor).sum();
            List<ContaDto> contasDto = ContaDto.converter(contas);
            ListaContasSomaResultadoDto resultado = new ListaContasSomaResultadoDto(valor, contasDto);
            return ResponseEntity.ok(resultado);

        }
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
        List<Conta> contas = new Array<>();
        while (i != 0) {
            Conta conta = form.converter(contaRepository);
            conta.setVencimento(conta.getVencimento().plusMonths(i - 1));
            contas.add(conta);
            i--;
        }
        contaRepository.save(contas);
        List<ContaDto> contasDto = ContaDto.converter(contaRepository.findByContaAndValor(form.getConta(), form.getValor()));
        return contasDto;
    }

    public static ResponseEntity<ContaDto> pagar(Long id, PagarContaForm form) {

        Optional<Conta> optional = contaRepository.findById(id);
        if (optional.isPresent()) {
            Conta conta = form.pagar(id, contaRepository);
            return ResponseEntity.ok(new ContaDto(conta));
        }
        return ResponseEntity.notFound().build();
    }
}

