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
    
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Contrato> contratos = new ArrayList<>();
    
    @OneToMany(mappedBy = "proprietario", cascade = CascadeType.ALL)
    private List<Automovel> automoveis = new ArrayList<>();

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
    
    public List<Pedido> getPedidos() { return pedidos; }
    public void setPedidos(List<Pedido> pedidos) { this.pedidos = pedidos; }
    
    public List<Contrato> getContratos() { return contratos; }
    public void setContratos(List<Contrato> contratos) { this.contratos = contratos; }
    
    public List<Automovel> getAutomoveis() { return automoveis; }
    public void setAutomoveis(List<Automovel> automoveis) { this.automoveis = automoveis; }
    
    // Métodos de conveniência
    public void adicionarPedido(Pedido pedido) {
        pedido.setCliente(this);
        this.pedidos.add(pedido);
    }
    
    public void adicionarContrato(Contrato contrato) {
        contrato.setCliente(this);
        this.contratos.add(contrato);
    }
    
    public void adicionarAutomovel(Automovel automovel) {
        automovel.setProprietario(this);
        this.automoveis.add(automovel);
    }
    
    public void adicionarEmprego(Emprego emprego) {
        emprego.setCliente(this);
        this.empregos.add(emprego);
    }
}
