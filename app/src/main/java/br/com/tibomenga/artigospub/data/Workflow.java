package br.com.tibomenga.artigospub.data;

import java.util.Date;

/**
 * Created by menga on 26/09/17.
 */

public class Workflow {
    private long id;
    private int statusWorkflow;
    private long idArtigo;
    private Date dataStatus;
    private String versaoAtual;
    private Artigo artigo;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getStatusWorkflow() {
        return statusWorkflow;
    }

    public void setStatusWorkflow(int statusWorkflow) {
        this.statusWorkflow = statusWorkflow;
    }

    public long getIdArtigo() {
        return idArtigo;
    }

    public void setIdArtigo(long idArtigo) {
        this.idArtigo = idArtigo;
    }

    public Date getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(Date dataStatus) {
        this.dataStatus = dataStatus;
    }

    public String getVersaoAtual() {
        return versaoAtual;
    }

    public void setVersaoAtual(String versaoAtual) {
        this.versaoAtual = versaoAtual;
    }

    public Artigo getArtigo() {
        return artigo;
    }

    public void setArtigo(Artigo artigo) {
        this.artigo = artigo;
    }

    @Override
    public String toString() {
        return "Workflow{" +
                "id=" + id +
                ", statusWorkflow=" + statusWorkflow +
                ", idArtigo=" + idArtigo +
                ", dataStatus=" + dataStatus +
                ", versaoAtual='" + versaoAtual + '\'' +
                ", artigo=" + artigo +
                '}';
    }

    public String toStringSearch() {
        String s = DataUtil.getWorkflowDescription(statusWorkflow)
                + DataUtil.formatDateTime(dataStatus) + versaoAtual;
        if (artigo != null) {
            s += artigo.getNome() + artigo.getAutor();
        }
        return s;
    }
}
