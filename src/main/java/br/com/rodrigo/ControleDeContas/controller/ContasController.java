package br.com.rodrigo.ControleDeContas.controller;

import br.com.rodrigo.ControleDeContas.controller.dto.ContaDto;
import br.com.rodrigo.ControleDeContas.controller.dto.ListaContasSomaResultadoDto;
import br.com.rodrigo.ControleDeContas.controller.form.ContaForm;
import br.com.rodrigo.ControleDeContas.controller.form.PagarContaForm;
import br.com.rodrigo.ControleDeContas.controller.service.ContaService;
import br.com.rodrigo.ControleDeContas.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/contas")
public class ContasController {
    @Autowired
    private ContaService contaService;
    @Autowired
    private ContaRepository contaRepository;

    @GetMapping
    public ResponseEntity<ListaContasSomaResultadoDto> lista(LocalDate inicio, LocalDate fim, String status) {
        return contaService.lista(inicio, fim, status);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContaDto> detalhar(@PathVariable Long id) {
        return contaService.detalhar(id);
    }

    @PostMapping
    @Transactional
    public List<ContaDto> cadastrar(@RequestBody ContaForm form, UriComponentsBuilder uriBuilder) {
        return ContaService.cadastrar(form, uriBuilder);
    }

    @PutMapping("/{id}/pagar")
    @Transactional
    public ResponseEntity<ContaDto> pagar(@PathVariable Long id, @RequestBody PagarContaForm form) {
        return ContaService.pagar(id, form);
    }

}
