package com.desarrollo.miprimeraapp.models;

public class Clientes {
    //atributos
    private Long id;
    private String nombres;
    private String apellidos;
    private String direccion;
    private String ciudad;

    //constructores
    public Clientes(){
    }

    public Clientes(Long id){
        this.id = id;
    }

    public Clientes(Long id, String nombres, String apellidos, String direccion, String ciudad){
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.ciudad = ciudad;
    }

    //metodos
    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getNombres(){
        return this.nombres;
    }

    public void setNombres(String nombres){
        this.nombres = nombres;
    }

    public String getApellidos(){
        return this.apellidos;
    }

    public void setApellidos(String apellidos){
        this.apellidos = apellidos;
    }

    public String getDireccion(){
        return this.direccion;
    }

    public void setDireccion(String direccion){
        this.direccion = direccion;
    }

    public String getCiudad(){
        return this.ciudad;
    }

    public void setCiudad(String ciudad){
        this.ciudad = ciudad;
    }
}
