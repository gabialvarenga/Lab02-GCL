package lab02.br.locadora.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Emprego {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String empregador;
    private String cargo;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private BigDecimal rendimentoMensal;
    private Boolean atual;
    private String telefoneEmpresa;
    private String enderecoEmpresa;
    
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    // Construtores
    public Emprego() {
    }
    
    public Emprego(String empregador, String cargo, LocalDate dataInicio, BigDecimal rendimentoMensal, Boolean atual) {
        this.empregador = empregador;
        this.cargo = cargo;
        this.dataInicio = dataInicio;
        this.rendimentoMensal = rendimentoMensal;
        this.atual = atual;
    }

    // Getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEmpregador() { return empregador; }
    public void setEmpregador(String empregador) { this.empregador = empregador; }
    public BigDecimal getRendimentoMensal() { return rendimentoMensal; }
    public void setRendimentoMensal(BigDecimal rendimentoMensal) { this.rendimentoMensal = rendimentoMensal; }
    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }
    public LocalDate getDataInicio() { return dataInicio; }
    public void setDataInicio(LocalDate dataInicio) { this.dataInicio = dataInicio; }
    public LocalDate getDataFim() { return dataFim; }
    public void setDataFim(LocalDate dataFim) { this.dataFim = dataFim; }
    public Boolean getAtual() { return atual; }
    public void setAtual(Boolean atual) { 
        this.atual = atual; 
        if (atual) {
            this.dataFim = null;
        }
    }
    public String getTelefoneEmpresa() { return telefoneEmpresa; }
    public void setTelefoneEmpresa(String telefoneEmpresa) { this.telefoneEmpresa = telefoneEmpresa; }
    public String getEnderecoEmpresa() { return enderecoEmpresa; }
    public void setEnderecoEmpresa(String enderecoEmpresa) { this.enderecoEmpresa = enderecoEmpresa; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
}
