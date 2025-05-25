package uniandes.edu.co.demo.modelo;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

public class Beneficiario {
    private String id;
    private String tipoDocumento;
    private String numeroDocumento;
    private String nombre;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaDeNacimiento;
    private String direccionDeResidencia;
    private String telefono;
    private String parentesco;

    public Beneficiario() {}

    public Beneficiario(String id, String tipoDocumento, String numeroDocumento, String nombre, Date fechaDeNacimiento,
                        String direccionDeResidencia, String telefono, String parentesco) {
        this.id = id;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.nombre = nombre;
        this.fechaDeNacimiento = fechaDeNacimiento;
        this.direccionDeResidencia = direccionDeResidencia;
        this.telefono = telefono;
        this.parentesco = parentesco;
    }

    // GETTERS Y SETTERS

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTipoDocumento() { return tipoDocumento; }
    public void setTipoDocumento(String tipoDocumento) { this.tipoDocumento = tipoDocumento; }

    public String getNumeroDocumento() { return numeroDocumento; }
    public void setNumeroDocumento(String numeroDocumento) { this.numeroDocumento = numeroDocumento; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Date getFechaDeNacimiento() { return fechaDeNacimiento; }
    public void setFechaDeNacimiento(Date fechaDeNacimiento) { this.fechaDeNacimiento = fechaDeNacimiento; }

    public String getDireccionDeResidencia() { return direccionDeResidencia; }
    public void setDireccionDeResidencia(String direccionDeResidencia) { this.direccionDeResidencia = direccionDeResidencia; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getParentesco() { return parentesco; }
    public void setParentesco(String parentesco) { this.parentesco = parentesco; }
}
