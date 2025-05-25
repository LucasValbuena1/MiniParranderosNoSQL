package uniandes.edu.co.demo.modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

@Document(collection = "CONTRIBUYENTES")
public class Contribuyente {
    @Id
    private String id;

    private ContribuyentePK pk;

    @Field("tipoDeDocumento")
    private String tipoDeDocumento;

    @Field("numeroDeDocumento")
    private String numeroDeDocumento;

    @Field("nombre")
    private String nombre;

    @Field("fechaDeNacimiento")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaDeNacimiento;

    @Field("direccionDeResidencia")
    private String direccionDeResidencia;

    @Field("telefono")
    private String telefono;

    @Field("beneficiarios")
    private List<Beneficiario> beneficiarios;

    public Contribuyente() {
        this.pk = new ContribuyentePK();
        this.beneficiarios = new ArrayList<>();
    }

    public Contribuyente(ContribuyentePK pk, String nombre, Date fechaDeNacimiento,
                         String direccionDeResidencia, String telefono, List<Beneficiario> beneficiarios) {
        this.pk = pk;
        this.nombre = nombre;
        this.fechaDeNacimiento = fechaDeNacimiento;
        this.direccionDeResidencia = direccionDeResidencia;
        this.telefono = telefono;
        this.beneficiarios = beneficiarios != null ? beneficiarios : new ArrayList<>();
        setId();
        if (pk != null) {
            this.tipoDeDocumento = pk.getTipoDocumento();
            this.numeroDeDocumento = pk.getNumeroDocumento();
        }
    }

    public String getId() { return id; }
    public void setId() {
        if (this.pk != null) {
            this.id = pk.getTipoDocumento() + "-" + pk.getNumeroDocumento();
        }
    }

    public ContribuyentePK getPk() { return pk; }
    public void setPk(ContribuyentePK pk) {
        this.pk = pk;
        setId();
        if (pk != null) {
            this.tipoDeDocumento = pk.getTipoDocumento();
            this.numeroDeDocumento = pk.getNumeroDocumento();
        }
    }

    public String getTipoDeDocumento() { return tipoDeDocumento; }
    public void setTipoDeDocumento(String tipoDeDocumento) { this.tipoDeDocumento = tipoDeDocumento; }

    public String getNumeroDeDocumento() { return numeroDeDocumento; }
    public void setNumeroDeDocumento(String numeroDeDocumento) { this.numeroDeDocumento = numeroDeDocumento; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Date getFechaDeNacimiento() { return fechaDeNacimiento; }
    public void setFechaDeNacimiento(Date fechaDeNacimiento) { this.fechaDeNacimiento = fechaDeNacimiento; }

    public String getDireccionDeResidencia() { return direccionDeResidencia; }
    public void setDireccionDeResidencia(String direccionDeResidencia) { this.direccionDeResidencia = direccionDeResidencia; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public List<Beneficiario> getBeneficiarios() { return beneficiarios; }
    public void setBeneficiarios(List<Beneficiario> beneficiarios) {
        this.beneficiarios = beneficiarios != null ? beneficiarios : new ArrayList<>();
    }
}
