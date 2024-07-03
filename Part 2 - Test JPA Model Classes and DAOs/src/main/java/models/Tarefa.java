package models;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Tarefa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String titulo;

    private String descricao;

    @Temporal(TemporalType.DATE)
    private Date data;

    @ManyToOne(optional = false)
    private Pessoa pessoa;

    // Getters and Setters

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        if (titulo == null || titulo.isBlank())
            throw new IllegalArgumentException("O Título da tarefa não pode ser nulo ou vazio!");

        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        if (pessoa == null)
            throw new IllegalArgumentException("A pessoa da tarefa não pode ser nula!");

        this.pessoa = pessoa;
    }

    @Override
    public String toString() {
        return "Tarefa{id=" + id + ", titulo='" + titulo + "', descricao='" + descricao + "', data=" + data
                + ", pessoa=" + pessoa + "}";
    }
}
