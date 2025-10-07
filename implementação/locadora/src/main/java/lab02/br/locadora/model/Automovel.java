package lab02.br.locadora.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class Automovel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String matricula;
    private String placa;
    private String marca;
    private String modelo;
    private Integer ano;
    private Boolean disponivel;
    private BigDecimal valorDiaria;
    
    @ManyToOne
    @JoinColumn(name = "proprietario_id")
    private Usuario proprietario;
    
    // Construtores
    public Automovel() {
        this.disponivel = true;
    }
    
    public Automovel(String matricula, Integer ano, String marca, String modelo, String placa, BigDecimal valorDiaria) {
        this.matricula = matricula;
        this.ano = ano;
        this.marca = marca;
        this.modelo = modelo;
        this.placa = placa;
        this.disponivel = true;
        this.valorDiaria = valorDiaria;
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getMatricula() {
        return matricula;
    }
    
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
    
    public Integer getAno() {
        return ano;
    }
    
    public void setAno(Integer ano) {
        this.ano = ano;
    }
    
    public String getMarca() {
        return marca;
    }
    
    public void setMarca(String marca) {
        this.marca = marca;
    }
    
    public String getModelo() {
        return modelo;
    }
    
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    
    public String getPlaca() {
        return placa;
    }
    
    public void setPlaca(String placa) {
        this.placa = placa;
    }
    
    public Boolean getDisponivel() {
        return disponivel;
    }
    
    public void setDisponivel(Boolean disponivel) {
        this.disponivel = disponivel;
    }
    
    public Usuario getProprietario() {
        return proprietario;
    }
    
    public void setProprietario(Usuario proprietario) {
        this.proprietario = proprietario;
    }
    
    public BigDecimal getValorDiaria() {
        return valorDiaria;
    }
    
    public void setValorDiaria(BigDecimal valorDiaria) {
        this.valorDiaria = valorDiaria;
    }
    
    // Método de negócio conforme diagrama
    public void marcarDisponivel(boolean flag) {
        this.disponivel = flag;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Automovel automovel = (Automovel) o;
        return Objects.equals(id, automovel.id) || 
               Objects.equals(placa, automovel.placa);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, placa);
    }
    
    @Override
    public String toString() {
        return "Automovel{" +
                "id=" + id +
                ", matricula='" + matricula + '\'' +
                ", ano=" + ano +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", placa='" + placa + '\'' +
                ", disponivel=" + disponivel +
                '}';
    }
}