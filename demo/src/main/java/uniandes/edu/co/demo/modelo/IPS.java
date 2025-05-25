package uniandes.edu.co.demo.modelo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "IPS")
public class IPS {
    @Id
    private Integer id; // El identificador que exige tu validador en MongoDB

    private String nit;
    private String nombre;
    private String direccion;
    private String telefono;
    private String tipo;

    private List<Integer> servicios = new ArrayList<>(); // Inicializa vacío por defecto

    public IPS() {
        // Inicializa servicios vacío por si acaso
        this.servicios = new ArrayList<>();
    }

    public IPS(Integer id, String nit, String nombre, String direccion, String telefono, String tipo, List<Integer> servicios) {
        this.id = id;
        this.nit = nit;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.tipo = tipo;
        // Si servicios viene nulo, inicialízalo vacío
        this.servicios = (servicios != null) ? servicios : new ArrayList<>();
    }

    // Getters y setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<Integer> getServicios() {
        return servicios;
    }

    public void setServicios(List<Integer> servicios) {
        this.servicios = (servicios != null) ? servicios : new ArrayList<>();
    }
}
