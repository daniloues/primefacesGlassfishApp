/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.parqueowebapp.app.entity;

import java.io.Serializable;
import java.util.Collection;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 *
 * @author daniloues
 */
@Entity
@Table(name = "tipo_reserva")
@NamedQueries({
    @NamedQuery(name = "TipoReserva.findAll", query = "SELECT t FROM TipoReserva t"),
    @NamedQuery(name = "TipoReserva.findByIdTipoReserva", query = "SELECT t FROM TipoReserva t WHERE t.idTipoReserva = :idTipoReserva"),
    @NamedQuery(name = "TipoReserva.findByNombre", query = "SELECT t FROM TipoReserva t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "TipoReserva.findByPublico", query = "SELECT t FROM TipoReserva t WHERE t.publico = :publico"),
    @NamedQuery(name = "TipoReserva.findByDescripcion", query = "SELECT t FROM TipoReserva t WHERE t.descripcion = :descripcion"),
    @NamedQuery(name = "TipoReserva.deteleById", query = "DELETE FROM TipoReserva t WHERE t.idTipoReserva = :idTipoReserva"),
    @NamedQuery(name = "TipoReserva.count", query = "SELECT COUNT(t) FROM TipoReserva t")})

public class TipoReserva implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tipo_reserva")
    private Integer idTipoReserva;
    @NotBlank(message = "El nombre no puede estar en blanco.")
    @Size(min = 3, max = 155, message = "El nombre debe poseer entre 3 y 155 caracteres.")
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "publico")
    private Boolean publico;
    @Column(name = "descripcion")
    private String descripcion;
    @OneToMany(mappedBy = "idTipoReserva")
    private Collection<TipoReservaSecuencia> tipoReservaSecuenciaCollection;
    @OneToMany(mappedBy = "idTipoReserva")
    private Collection<Reserva> reservaCollection;

    public TipoReserva() {
    }

    public TipoReserva(Integer idTipoReserva) {
        this.idTipoReserva = idTipoReserva;
    }

    public Integer getIdTipoReserva() {
        return idTipoReserva;
    }

    public void setIdTipoReserva(Integer idTipoReserva) {
        this.idTipoReserva = idTipoReserva;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getPublico() {
        return publico;
    }

    public void setPublico(Boolean publico) {
        this.publico = publico;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Collection<TipoReservaSecuencia> getTipoReservaSecuenciaCollection() {
        return tipoReservaSecuenciaCollection;
    }

    public void setTipoReservaSecuenciaCollection(Collection<TipoReservaSecuencia> tipoReservaSecuenciaCollection) {
        this.tipoReservaSecuenciaCollection = tipoReservaSecuenciaCollection;
    }

    public Collection<Reserva> getReservaCollection() {
        return reservaCollection;
    }

    public void setReservaCollection(Collection<Reserva> reservaCollection) {
        this.reservaCollection = reservaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoReserva != null ? idTipoReserva.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoReserva)) {
            return false;
        }
        TipoReserva other = (TipoReserva) object;
        if ((this.idTipoReserva == null && other.idTipoReserva != null) || (this.idTipoReserva != null && !this.idTipoReserva.equals(other.idTipoReserva))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.parqueowebapp.TipoReserva[ idTipoReserva=" + idTipoReserva + " ]";
    }

}
