package Models;


import java.io.Serializable;

public class contactos implements Serializable {

    private String pais;

    private Integer id;

    private String nombreCompleto;

    private String Telefono;

    private String nota;

    private String imagen;

    public contactos(String pais, Integer id, String nombreCompleto, String telefono, String nota, String imagen) {
        this.pais = pais;
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.Telefono = telefono;
        this.nota = nota;
        this.imagen = imagen;
    }

    public contactos() {
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {

        this.nombreCompleto = nombreCompleto;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        this.Telefono = telefono;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public String getImagen() {
        return imagen;
    }


    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}


