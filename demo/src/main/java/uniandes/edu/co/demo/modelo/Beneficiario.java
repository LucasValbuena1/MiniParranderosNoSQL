package uniandes.edu.co.demo.modelo;

import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

@Document(collection = "beneficiarios")
public class Beneficiario {
    @Id
    private String id;
    private String tipoDocumento;
    private String numeroDocumento;
    private String nombre;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaNacimiento;
    private String direccionResidencia;
    private String telefono;
    private String parentesco;
    private ContribuyenteEmbebido contribuyente;

    public Beneficiario() {
    }

    public Beneficiario(String tipoDocumento, String numeroDocumento, String nombre, Date fechaNacimiento,
            String direccionResidencia, String telefono, String parentesco, ContribuyenteEmbebido contribuyente) {
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.id = tipoDocumento + "-" + numeroDocumento;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.direccionResidencia = direccionResidencia;
        this.telefono = telefono;
        this.parentesco = parentesco;
        this.contribuyente = contribuyente;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
        this.id = tipoDocumento + "-" + this.numeroDocumento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
        this.id = this.tipoDocumento + "-" + numeroDocumento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getDireccionResidencia() {
        return direccionResidencia;
    }

    public void setDireccionResidencia(String direccionResidencia) {
        this.direccionResidencia = direccionResidencia;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

    public ContribuyenteEmbebido getContribuyente() {
        return contribuyente;
    }

    public void setContribuyente(ContribuyenteEmbebido contribuyente) {
        this.contribuyente = contribuyente;
    }
}