package lab02.br.locadora.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("EMPRESA")
public class Empresa extends Agente {
    
    private String razaoSocial;
    private String endereco;
    private String telefone;
    private String areaAtuacao;
    
    @OneToMany(mappedBy = "proprietario", cascade = CascadeType.ALL)
    private List<Automovel> automoveis = new ArrayList<>();
    
    // Construtores
    public Empresa() {
        super();
    }
    
    // Getters e Setters
    public String getRazaoSocial() {
        return razaoSocial;
    }
    
    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }
    
    public String getEndereco() {
        return endereco;
    }
    
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
    
    public String getTelefone() {
        return telefone;
    }
    
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    
    public String getAreaAtuacao() {
        return areaAtuacao;
    }
    
    public void setAreaAtuacao(String areaAtuacao) {
        this.areaAtuacao = areaAtuacao;
    }
    
    public List<Automovel> getAutomoveis() {
        return automoveis;
    }
    
    public void setAutomoveis(List<Automovel> automoveis) {
        this.automoveis = automoveis;
    }
    
    // Adicionar automóvel
    public void adicionarAutomovel(Automovel automovel) {
        automovel.setProprietario(this);
        this.automoveis.add(automovel);
    }
    
    // Implementação dos métodos abstratos de Agente
    @Override
    public boolean avaliarPedido(Pedido pedido) {
        // Lógica de avaliação da empresa
        // Por exemplo: verificar se há veículos disponíveis
        if (pedido.getAutomovel() != null && pedido.getAutomovel().getDisponivel()) {
            // Pode adicionar outras validações específicas da empresa
            return true;
        }
        return false;
    }
    
    // Método específico da Empresa
    public Automovel fornecerVeiculo(String matricula, String placa, String marca, String modelo, Integer ano) {
        Automovel automovel = new Automovel(matricula, ano, marca, modelo, placa);
        adicionarAutomovel(automovel);
        return automovel;
    }
}