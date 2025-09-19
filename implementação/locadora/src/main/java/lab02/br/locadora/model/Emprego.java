package lab02.br.locadora.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class Emprego {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String empregador;
    private BigDecimal rendimentoMensal;

    // Getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEmpregador() { return empregador; }
    public void setEmpregador(String empregador) { this.empregador = empregador; }
    public BigDecimal getRendimentoMensal() { return rendimentoMensal; }
    public void setRendimentoMensal(BigDecimal rendimentoMensal) { this.rendimentoMensal = rendimentoMensal; }
}
