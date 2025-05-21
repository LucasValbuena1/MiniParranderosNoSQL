package uniandes.edu.co.demo.modelo;

import java.io.Serializable;
import java.util.Objects;

public class ContribuyentePK implements Serializable {
    private String tipoDocumento;
    private String numeroDocumento;

    public ContribuyentePK() {
    }

    public ContribuyentePK(String tipoDocumento, String numeroDocumento) {
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof ContribuyentePK))
            return false;
        ContribuyentePK that = (ContribuyentePK) o;
        return Objects.equals(tipoDocumento, that.tipoDocumento)
                && Objects.equals(numeroDocumento, that.numeroDocumento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tipoDocumento, numeroDocumento);
    }
}