package uniandes.edu.co.demo.modelo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "SERVICIOS_DE_SALUD")
public class ServicioDeSalud {
    @Id
    private Integer idServicio;
    private String nombre;
    private String requiereOrden;
    private Integer idOrden;

    public ServicioDeSalud() {
    }

    public ServicioDeSalud(Integer idServicio, String nombre, String requiereOrden, Integer idOrden) {
        this.idServicio = idServicio;
        this.nombre = nombre;
        this.requiereOrden = requiereOrden;
        this.idOrden = idOrden;
    }

    public Integer getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Integer idServicio) {
        this.idServicio = idServicio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRequiereOrden() {
        return requiereOrden;
    }

    public void setRequiereOrden(String requiereOrden) {
        this.requiereOrden = requiereOrden;
    }

    public Integer getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(Integer idOrden) {
        this.idOrden = idOrden;
    }
}