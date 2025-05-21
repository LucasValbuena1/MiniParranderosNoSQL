package uniandes.edu.co.demo.modelo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "medicos")
public class Medico {
    @Id
    private Integer numeroRegistroMedico;
    private Integer numeroDeDocumento;
    private String nombre;
    private String especialidad;
    private String tipoDocumento;

    public Medico() {
    }

    public Medico(Integer numeroRegistroMedico, Integer numeroDeDocumento, String nombre, String especialidad,
            String tipoDocumento) {
        this.numeroRegistroMedico = numeroRegistroMedico;
        this.numeroDeDocumento = numeroDeDocumento;
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.tipoDocumento = tipoDocumento;
    }

    public Integer getNumeroRegistroMedico() {
        return numeroRegistroMedico;
    }

    public void setNumeroRegistroMedico(Integer numeroRegistroMedico) {
        this.numeroRegistroMedico = numeroRegistroMedico;
    }

    public Integer getNumeroDeDocumento() {
        return numeroDeDocumento;
    }

    public void setNumeroDeDocumento(Integer numeroDeDocumento) {
        this.numeroDeDocumento = numeroDeDocumento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }
}