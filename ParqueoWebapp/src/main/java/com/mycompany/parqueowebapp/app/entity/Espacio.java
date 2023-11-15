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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 *
 * @author daniloues
 */
@Entity
@Table(name = "espacio", schema = "public")
@NamedQueries({
    @NamedQuery(name = "Espacio.filtrarCaracteristicas", query = "SELECT DISTINCT e FROM Espacio e JOIN e.espacioCaracteristicaCollection c\n" +
    "WHERE e.idEspacio IN :idsEspacios AND c.idTipoEspacio.idTipoEspacio IN :idsCaracteristicas"),
    @NamedQuery(name = "Espacio.findByEspaciosByListAreas", query = "SELECT e FROM Espacio e JOIN e.idArea a WHERE a.idAreaPadre.idArea = :idAreaPadre"),
    @NamedQuery(name = "Espacio.findAll", query = "SELECT e FROM Espacio e"),
    @NamedQuery(name = "Espacio.findByIdArea", query = "SELECT e FROM Espacio e WHERE e.idArea.idArea = :idArea ORDER BY e.nombre ASC"),
    @NamedQuery(name = "Espacio.countByIdArea", query = "SELECT COUNT (e.idEspacio) FROM Espacio e WHERE e.idArea.idArea = :idArea"),
    @NamedQuery(name = "Espacio.findByIdEspacio", query = "SELECT e FROM Espacio e WHERE e.idEspacio = :idEspacio"),
    @NamedQuery(name = "Espacio.findByActivo", query = "SELECT e FROM Espacio e WHERE e.activo = :activo"),
    @NamedQuery(name = "Espacio.findNombresByIdArea", query = "SELECT e.nombre FROM Espacio e WHERE e.idArea.idArea = :idArea ORDER BY e.nombre ASC")
})
public class Espacio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_espacio")
    private Long idEspacio;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "observaciones")
    private String observaciones;
    @Column(name = "activo")
    private Boolean activo;
    @JoinColumn(name = "id_area", referencedColumnName = "id_area")
    @ManyToOne
    private Area idArea;
    @OneToMany(mappedBy = "idEspacio")
    private Collection<Reserva> reservaCollection;
    @OneToMany(mappedBy = "idEspacio")
    private Collection<EspacioCaracteristica> espacioCaracteristicaCollection;

    public Espacio() {
    }

    public Espacio(Long idEspacio) {
        this.idEspacio = idEspacio;
    }

    public Long getIdEspacio() {
        return idEspacio;
    }

    public void setIdEspacio(Long idEspacio) {
        this.idEspacio = idEspacio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Area getIdArea() {
        return idArea;
    }

    public void setIdArea(Area idArea) {
        this.idArea = idArea;
    }

    public Collection<Reserva> getReservaCollection() {
        return reservaCollection;
    }

    public void setReservaCollection(Collection<Reserva> reservaCollection) {
        this.reservaCollection = reservaCollection;
    }

    public Collection<EspacioCaracteristica> getEspacioCaracteristicaCollection() {
        return espacioCaracteristicaCollection;
    }

    public void setEspacioCaracteristicaCollection(Collection<EspacioCaracteristica> espacioCaracteristicaCollection) {
        this.espacioCaracteristicaCollection = espacioCaracteristicaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEspacio != null ? idEspacio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Espacio)) {
            return false;
        }
        Espacio other = (Espacio) object;
        if ((this.idEspacio == null && other.idEspacio != null) || (this.idEspacio != null && !this.idEspacio.equals(other.idEspacio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.parqueowebapp.Espacio[ idEspacio=" + idEspacio + " ]";
    }

}
