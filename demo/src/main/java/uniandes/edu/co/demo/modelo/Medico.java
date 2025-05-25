package uniandes.edu.co.demo.modelo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "MEDICOS")
public class Medico {
    @Id
    private Integer id;

    private String nombre;
    private String tipoDeDocumento;
    private String numeroDeDocumento;
    private String numeroRegistroMedico;
    private List<String> especialidades = new ArrayList<>();
    private List<Integer> IPSAsociada = new ArrayList<>();

    public Medico() {
        // Asegura que la lista nunca sea null
        this.especialidades = new ArrayList<>();
    }

    public Medico(Integer id, String nombre, String tipoDeDocumento, String numeroDeDocumento, String numeroRegistroMedico, List<String> especialidades, List<Integer> IPSAsociada) {
        this.id = id;
        this.nombre = nombre;
        this.tipoDeDocumento = tipoDeDocumento;
        this.numeroDeDocumento = numeroDeDocumento;
        this.numeroRegistroMedico = numeroRegistroMedico;
        this.especialidades = (especialidades != null) ? especialidades : new ArrayList<>();
        this.IPSAsociada = IPSAsociada;
    }

    // Getters y Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoDeDocumento() {
        return tipoDeDocumento;
    }

    public void setTipoDeDocumento(String tipoDeDocumento) {
        this.tipoDeDocumento = tipoDeDocumento;
    }

    public String getNumeroDeDocumento() {
        return numeroDeDocumento;
    }

    public void setNumeroDeDocumento(String numeroDeDocumento) {
        this.numeroDeDocumento = numeroDeDocumento;
    }

    public String getNumeroRegistroMedico() {
        return numeroRegistroMedico;
    }

    public void setNumeroRegistroMedico(String numeroRegistroMedico) {
        this.numeroRegistroMedico = numeroRegistroMedico;
    }

    public List<String> getEspecialidades() {
        return especialidades;
    }

    public void setEspecialidades(List<String> especialidades) {
        this.especialidades = (especialidades != null) ? especialidades : new ArrayList<>();
    }

    public List<Integer> getIPSAsociada() {
    return IPSAsociada;
}

public void setIPSAsociada(List<Integer> IPSAsociada) {
    this.IPSAsociada = (IPSAsociada != null) ? IPSAsociada : new ArrayList<>();
}

}
