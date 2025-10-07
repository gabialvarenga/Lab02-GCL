package lab02.br.locadora.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("ATENDENTE")
public class Atendente extends Usuario {
    
    private String matricula;
    private String setor;
    private Boolean ativo;
    
    @OneToMany(cascade = CascadeType.ALL)
    private List<Automovel> veiculosCadastrados = new ArrayList<>();
    
    // Construtores
    public Atendente() {
        super();
        this.ativo = true;
    }
    
    // Getters e Setters
    public String getMatricula() {
        return matricula;
    }
    
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
    
    public String getSetor() {
        return setor;
    }
    
    public void setSetor(String setor) {
        this.setor = setor;
    }
    
    public Boolean getAtivo() {
        return ativo;
    }
    
    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
    
    public List<Automovel> getVeiculosCadastrados() {
        return veiculosCadastrados;
    }
    
    public void setVeiculosCadastrados(List<Automovel> veiculosCadastrados) {
        this.veiculosCadastrados = veiculosCadastrados;
    }
    
    public Contrato aprovarPedido(Long pedidoId) {
        return null; 
    }
    
    public void rejeitarPedido(Long pedidoId, String motivo) {
    }
    
    public void encaminharParaBanco(Long pedidoId) {
    }
    
    public Automovel cadastrarVeiculo(String matricula, Integer ano, String marca, String modelo, String placa, BigDecimal valorDiaria) {
        Automovel automovel = new Automovel(matricula, ano, marca, modelo, placa, valorDiaria);
        this.veiculosCadastrados.add(automovel);
        return automovel;
    }
    
    public boolean atualizarInformacoesVeiculo(Automovel automovel, String marca, String modelo, Integer ano) {
        if (automovel != null) {
            automovel.setMarca(marca);
            automovel.setModelo(modelo);
            automovel.setAno(ano);
            return true;
        }
        return false;
    }
} 