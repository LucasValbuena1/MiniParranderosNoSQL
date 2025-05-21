package uniandes.edu.co.demo.modelo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "medicoEnIPS")
public class MedicoEnIPS {
    @Id
    private String id; // "nit-medReg"
    private Integer nitIPS;
    private Integer numeroRegistroMedico;

    public MedicoEnIPS() {
    }

    public MedicoEnIPS(Integer nitIPS, Integer numeroRegistroMedico) {
        this.nitIPS = nitIPS;
        this.numeroRegistroMedico = numeroRegistroMedico;
        this.id = nitIPS + "-" + numeroRegistroMedico;
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
        this.id = nitIPS + "-" + this.numeroRegistroMedico;
    }

    public Integer getNumeroRegistroMedico() {
        return numeroRegistroMedico;
    }

    public void setNumeroRegistroMedico(Integer numeroRegistroMedico) {
        this.numeroRegistroMedico = numeroRegistroMedico;
        this.id = this.nitIPS + "-" + numeroRegistroMedico;
    }
}
