package br.com.tibomenga.artigospub.br.com.tibomenga.artigospub.data;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by menga on 25/09/17.
 */

public class Artigo implements Serializable {
    private String nome;
    private String autor;
    private Date dataInicial;
    private String comentarios;
    private String statusWorkflow;
    private String destinoPublicacao;
    private String versaoAtual;

    public Artigo() {
        setDataInicial(Calendar.getInstance().getTime());
    }

    public String getVersaoAtual() {
        return versaoAtual;
    }

    public void setVersaoAtual(String versaoAtual) {
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

    public void setDataInicial(Date dataInicial) {
        this.dataInicial = dataInicial;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public String getStatusWorkflow() {
        return statusWorkflow;
    }

    public void setStatusWorkflow(String statusWorkflow) {
        this.statusWorkflow = statusWorkflow;
    }

    public String getDestinoPublicacao() {
        return destinoPublicacao;
    }

    public void setDestinoPublicacao(String destinoPublicacao) {
        this.destinoPublicacao = destinoPublicacao;
    }
}
