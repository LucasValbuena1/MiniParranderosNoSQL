package uniandes.edu.co.demo.modelo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "serviciosDeUnaIPS")
public class ServiciosDeUnaIPS {
    @Id
    private String id; // compuesto "nit-ip"
    private Integer nitIPS;
    private Integer idServicio;

    public ServiciosDeUnaIPS() {
    }

    public ServiciosDeUnaIPS(Integer nitIPS, Integer idServicio) {
        this.nitIPS = nitIPS;
        this.idServicio = idServicio;
        this.id = nitIPS + "-" + idServicio;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getNitIPS() {
        return nitIPS;
    }

    public void setNitIPS(Integer nitIPS) {
        this.nitIPS = nitIPS;
        this.id = nitIPS + "-" + this.idServicio;
    }

    public Integer getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Integer idServicio) {
        this.idServicio = idServicio;
        this.id = this.nitIPS + "-" + idServicio;
    }
}