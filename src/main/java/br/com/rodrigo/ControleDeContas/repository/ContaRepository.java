package br.com.rodrigo.ControleDeContas.repository;

import br.com.rodrigo.ControleDeContas.model.Conta;
import br.com.rodrigo.ControleDeContas.model.StatusConta;
import jakarta.persistence.OrderBy;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ContaRepository extends JpaRepository<Conta, Long> {
    List<Conta> findByVencimentoBetween(LocalDate inicio, LocalDate fim,Sort sort);

    List<Conta> findByVencimentoGreaterThanEqual(LocalDate inicio);

    List<Conta> findByContaAndValor(String conta, Double valor);

    List<Conta> findByVencimentoBetweenAndStatus (LocalDate inicio, LocalDate fim, StatusConta statusConta , Sort sort);



}
