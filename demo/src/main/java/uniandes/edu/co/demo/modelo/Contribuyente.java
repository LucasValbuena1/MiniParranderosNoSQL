package uniandes.edu.co.demo.modelo;

import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

@Document(collection = "contribuyentes")
public class Contribuyente {
    @Id
    private String id;
    private ContribuyentePK pk;
    private String nombre;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaNacimiento;
    private String direccionResidencia;
    private String telefono;

    public Contribuyente() {
        this.pk = new ContribuyentePK();
        this.id = "";
    }

    public Contribuyente(ContribuyentePK pk, String nombre, Date fechaNacimiento, String direccionResidencia,
            String telefono) {
        this.pk = pk;
        this.id = pk.getTipoDocumento() + "-" + pk.getNumeroDocumento();
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.direccionResidencia = direccionResidencia;
        this.telefono = telefono;
    }

    public String getId() {
        return id;
    }

    public void setId() {
        this.id = pk.getTipoDocumento() + "-" + pk.getNumeroDocumento();
    }

    public ContribuyentePK getPk() {
        return pk;
    }

    public void setPk(ContribuyentePK pk) {
        this.pk = pk;
        setId();
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
}