package uniandes.edu.co.demo.modelo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "SERVICIOS_DE_UNA_IPS")
public class ServiciosDeUnaIPS {
    @Id
    private String id; // compuesto "nit-ip"
    private String nitIPS;
    private String idServicio;

    public ServiciosDeUnaIPS() {
    }

    public ServiciosDeUnaIPS(String nitIPS, String idServicio) {
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

    public String getNitIPS() {
        return nitIPS;
    }

    public void setNitIPS(String nitIPS) {
        this.nitIPS = nitIPS;
        this.id = nitIPS + "-" + this.idServicio;
    }

    public String getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(String idServicio) {
        this.idServicio = idServicio;
        this.id = this.nitIPS + "-" + idServicio;
    }
}