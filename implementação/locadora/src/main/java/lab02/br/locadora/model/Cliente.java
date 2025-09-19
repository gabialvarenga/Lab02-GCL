package lab02.br.locadora.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Cliente extends Usuario {
    private String cpf;
    private String rg;
    private String endereco;
    private String profissao;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Emprego> empregos;

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
}
