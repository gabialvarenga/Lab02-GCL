package lab02.br.locadora.model;

import jakarta.persistence.*;
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
    
    // Métodos de negócio
    public Contrato aprovarPedido(Pedido pedido) {
        if (pedido.aprovar()) {
            Contrato contrato = new Contrato(pedido);
            pedido.setContrato(contrato);
            pedido.setStatus(StatusPedido.CONTRATO_GERADO);
            return contrato;
        }
        return null;
    }
    
    public void rejeitarPedido(Pedido pedido, String motivo) {
        pedido.rejeitar();
        pedido.setObservacoes("Rejeitado por: " + this.getNome() + ". Motivo: " + motivo);
    }
    
    public void encaminharParaBanco(Pedido pedido, Banco banco) {
        pedido.iniciarAnalise();
        pedido.setAgenteResponsavel(banco);
        banco.adicionarPedidoGerenciado(pedido);
    }
    
    public Automovel cadastrarVeiculo(String matricula, Integer ano, String marca, String modelo, String placa) {
        Automovel automovel = new Automovel(matricula, ano, marca, modelo, placa);
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