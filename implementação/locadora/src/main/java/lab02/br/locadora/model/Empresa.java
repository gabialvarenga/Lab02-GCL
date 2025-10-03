package lab02.br.locadora.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("EMPRESA")
public class Empresa extends Usuario {
    
    private String cnpj;
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
    public String getCnpj() {
        return cnpj;
    }
    
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
    
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
}