package lab02.br.locadora.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("CLIENTE")
public class Cliente extends Usuario {
    private String cpf;
    private String rg;
    private String endereco;
    private String profissao;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Emprego> empregos = new ArrayList<>();
    
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Pedido> pedidos = new ArrayList<>();

    // Getters e setters
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getRg() { return rg; }
    public void setRg(String rg) { this.rg = rg; }
    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
    public String getProfissao() { return profissao; }
    public void setProfissao(String profissao) { this.profissao = profissao; }
    public List<Emprego> getEmpregos() { return empregos; }
    public void setEmpregos(List<Emprego> empregos) { this.empregos = empregos; }
    
    public List<Pedido> getPedidos() { 
        return pedidos; 
    }
    
    public void setPedidos(List<Pedido> pedidos) { 
        this.pedidos = pedidos; 
    }
    
    // Métodos de negócio conforme diagrama
    public Pedido criarPedido(Pedido pedido) {
        pedido.setCliente(this);
        this.pedidos.add(pedido);
        return pedido;
    }
    
    public Pedido modificarPedido(Long id, Pedido pedidoAtualizado) {
        Pedido pedido = pedidos.stream()
            .filter(p -> p.getId().equals(id))
            .findFirst()
            .orElse(null);
            
        if (pedido != null && pedido.getStatus() == StatusPedido.PENDENTE) {
            pedido.setDataInicio(pedidoAtualizado.getDataInicio());
            pedido.setDataFim(pedidoAtualizado.getDataFim());
            pedido.atualizarValorTotal();
            return pedido;
        }
        return null;
    }
    
    public Pedido consultarPedido(Long id) {
        return pedidos.stream()
            .filter(p -> p.getId().equals(id))
            .findFirst()
            .orElse(null);
    }
    
    public void cancelarPedido(Long id) {
        Pedido pedido = consultarPedido(id);
        if (pedido != null) {
            pedido.cancelar();
        }
    }
    
    // Métodos de conveniência
    public void adicionarEmprego(Emprego emprego) {
        emprego.setCliente(this);
        this.empregos.add(emprego);
    }
}
