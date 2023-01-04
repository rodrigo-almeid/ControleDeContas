package br.com.rodrigo.ControleDeContas.controller;

import br.com.rodrigo.ControleDeContas.controller.form.ContaForm;
import br.com.rodrigo.ControleDeContas.controller.form.PagarContaForm;
import br.com.rodrigo.ControleDeContas.dto.ContaDto;
import br.com.rodrigo.ControleDeContas.dto.ListaContasSomaResultadoDto;
import br.com.rodrigo.ControleDeContas.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/contas")
public class ContasController {
    @Autowired
    private ContaService contaService;


    @GetMapping
    public ResponseEntity<ListaContasSomaResultadoDto> lista(LocalDate inicio, LocalDate fim, String status) {
        return contaService.lista(inicio, fim, status);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContaDto> detalhar(@PathVariable Long id) {
        return contaService.detalhar(id);
    }

    @PostMapping
    public List<ContaDto> cadastrar(@RequestBody ContaForm contaForm, UriComponentsBuilder uriBuilder) {
        return contaService.cadastrar(contaForm, uriBuilder);
    }

    @PutMapping("/{id}/pagar")
    public ContaDto pagar(@PathVariable Long id) {
        return contaService.pagar(id);
    }
    @PutMapping("/{id}/ajustar")
    public ContaDto ajustar(@PathVariable Long id,@RequestBody ContaForm contaForm) {
        Double valor = contaForm.getValor();
        return contaService.ajustar(id, valor);
    }
}

