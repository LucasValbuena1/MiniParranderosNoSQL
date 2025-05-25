package uniandes.edu.co.demo.modelo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "MEDICO_EN_IPS")
public class MedicoEnIPS {
    @Id
    private String id; // "nit-medReg"
    private String nitIPS;
    private String numeroRegistroMedico;

    public MedicoEnIPS() {
    }

    public MedicoEnIPS(String nitIPS, String numeroRegistroMedico) {
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

    public String getNitIPS() {
        return nitIPS;
    }

    public void setNitIPS(String nitIPS) {
        this.nitIPS = nitIPS;
        this.id = nitIPS + "-" + this.numeroRegistroMedico;
    }

    public String getNumeroRegistroMedico() {
        return numeroRegistroMedico;
    }

    public void setNumeroRegistroMedico(String numeroRegistroMedico) {
        this.numeroRegistroMedico = numeroRegistroMedico;
        this.id = this.nitIPS + "-" + numeroRegistroMedico;
    }
}
