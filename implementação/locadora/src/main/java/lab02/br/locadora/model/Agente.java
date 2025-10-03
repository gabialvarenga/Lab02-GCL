package lab02.br.locadora.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("AGENTE")
public abstract class Agente extends Usuario {
    
    protected String cnpj;
    protected String departamento;
    
    @OneToMany(mappedBy = "agenteResponsavel", cascade = CascadeType.ALL)
    private List<Pedido> pedidosGerenciados = new ArrayList<>();
    
    // Construtores
    public Agente() {
        super();
    }
    
    // Getters e Setters
    public String getCnpj() {
        return cnpj;
    }
    
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
    
    public String getDepartamento() {
        return departamento;
    }
    
    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }
    
    public List<Pedido> getPedidosGerenciados() {
        return pedidosGerenciados;
    }
    
    public void setPedidosGerenciados(List<Pedido> pedidosGerenciados) {
        this.pedidosGerenciados = pedidosGerenciados;
    }
    
    // Métodos de negócio
    public void adicionarPedidoGerenciado(Pedido pedido) {
        pedido.setAgenteResponsavel(this);
        this.pedidosGerenciados.add(pedido);
    }
    
    // Métodos abstratos que devem ser implementados pelas subclasses
    public abstract boolean avaliarPedido(Pedido pedido);
    
    public void modificarPedido(Pedido pedido) {
        // Lógica comum de modificação de pedido
        if (pedido.getStatus() == StatusPedido.PENDENTE || 
            pedido.getStatus() == StatusPedido.EM_ANALISE) {
            pedido.setAgenteResponsavel(this);
        }
    }
}