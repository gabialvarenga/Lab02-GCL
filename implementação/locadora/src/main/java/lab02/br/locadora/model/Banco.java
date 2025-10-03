package lab02.br.locadora.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("BANCO")
public class Banco extends Agente {
    
    private String razaoSocial;
    private String endereco;
    private String telefone;
    
    @OneToMany(mappedBy = "banco", cascade = CascadeType.ALL)
    private List<Credito> creditos = new ArrayList<>();
    
    @OneToMany(mappedBy = "proprietario", cascade = CascadeType.ALL)
    private List<Automovel> automoveis = new ArrayList<>();
    
    // Construtores
    public Banco() {
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
    
    public List<Credito> getCreditos() {
        return creditos;
    }
    
    public void setCreditos(List<Credito> creditos) {
        this.creditos = creditos;
    }
    
    public List<Automovel> getAutomoveis() {
        return automoveis;
    }
    
    public void setAutomoveis(List<Automovel> automoveis) {
        this.automoveis = automoveis;
    }
    
    // Adicionar crédito
    public void adicionarCredito(Credito credito) {
        credito.setBanco(this);
        this.creditos.add(credito);
    }
    
    // Adicionar automóvel
    public void adicionarAutomovel(Automovel automovel) {
        automovel.setProprietario(this);
        this.automoveis.add(automovel);
    }
    
    // Implementação dos métodos abstratos de Agente
    @Override
    public boolean avaliarPedido(Pedido pedido) {
        // Lógica de avaliação financeira do banco
        if (pedido.getCliente() != null && pedido.getCliente().getEmpregos() != null) {
            BigDecimal rendaTotalMensal = pedido.getCliente().getEmpregos().stream()
                .map(Emprego::getRendimentoMensal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
            
            // Exemplo: aprovar se renda mensal for maior que 3x o valor do aluguel
            // (essa lógica pode ser ajustada conforme necessário)
            return rendaTotalMensal.compareTo(BigDecimal.ZERO) > 0;
        }
        return false;
    }
    
    // Método específico do Banco
    public Credito concederCredito(Pedido pedido, BigDecimal valorCredito, Integer numeroParcelas, BigDecimal taxaJuros) {
        if (avaliarPedido(pedido)) {
            Credito credito = new Credito(valorCredito, numeroParcelas, taxaJuros, pedido.getContrato(), this);
            credito.aprovar();
            adicionarCredito(credito);
            return credito;
        }
        return null;
    }
}