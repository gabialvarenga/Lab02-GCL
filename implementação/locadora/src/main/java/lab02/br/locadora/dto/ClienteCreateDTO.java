package lab02.br.locadora.dto;

import lab02.br.locadora.model.Role;
import java.util.ArrayList;
import java.util.List;

public class ClienteCreateDTO extends UsuarioCreateDTO {
    private String cpf;
    private String rg;
    private String endereco;
    private String profissao;
    
    // Getters e setters
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getRg() { return rg; }
    public void setRg(String rg) { this.rg = rg; }
    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
    public String getProfissao() { return profissao; }
    public void setProfissao(String profissao) { this.profissao = profissao; }
}