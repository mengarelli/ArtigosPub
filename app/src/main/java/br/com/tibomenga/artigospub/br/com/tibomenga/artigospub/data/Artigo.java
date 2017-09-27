package br.com.tibomenga.artigospub.br.com.tibomenga.artigospub.data;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by menga on 25/09/17.
 */

public class Artigo implements Serializable {
    private long id;
    private String nome;
    private String autor;
    private Date dataInicial;
    private Date dataLimite;
    private String comentarios;
    private int statusWorkflow;
    private Integer statusWorkflowSombra = null;
    private String destinoPublicacao;
    private String versaoAtual;
    private String versaoAtualSombra = null;

    public Artigo() {
        setDataInicial(Calendar.getInstance().getTime());
        setDataLimite(Calendar.getInstance().getTime());
        versaoAtual = "";
        statusWorkflow = 0;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getVersaoAtual() {
        return versaoAtual;
    }

    public void setVersaoAtual(String versaoAtual) {
        if (versaoAtualSombra == null) {
            versaoAtualSombra = this.versaoAtual;
        }
        this.versaoAtual = versaoAtual;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Date getDataInicial() {
        return dataInicial;
    }

    public void setDataLimite(Date dataLimite) {
        this.dataLimite = dataLimite;
    }

    public Date getDataLimite() {
        return dataLimite;
    }

    public void setDataInicial(Date dataInicial) {
        this.dataInicial = dataInicial;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public int getStatusWorkflow() { return statusWorkflow; }

    public void setStatusWorkflow(int statusWorkflow) {
        if (statusWorkflowSombra == null) {
            this.statusWorkflowSombra = this.statusWorkflow;
        }
        this.statusWorkflow = statusWorkflow;
    }

    public String getDestinoPublicacao() {
        return destinoPublicacao;
    }

    public void setDestinoPublicacao(String destinoPublicacao) {
        this.destinoPublicacao = destinoPublicacao;
    }

    public boolean isVersaoWorkflowChanged() {
        boolean result = false;
        if ((statusWorkflowSombra != null) && (statusWorkflowSombra != statusWorkflow)) {
            result = true;
        }
        if ((versaoAtualSombra != null) && (!versaoAtualSombra.equals(versaoAtual))) {
            result = true;
        }
        return result;
    }

    @Override
    public String toString() {
        return "Artigo{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", autor='" + autor + '\'' +
                ", dataInicial=" + dataInicial +
                ", dataLimite=" + dataLimite +
                ", comentarios='" + comentarios + '\'' +
                ", statusWorkflow=" + statusWorkflow +
                ", destinoPublicacao='" + destinoPublicacao + '\'' +
                ", versaoAtual='" + versaoAtual + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Artigo artigo = (Artigo) o;

        return getId() == artigo.getId();

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
