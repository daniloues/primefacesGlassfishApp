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
@Table(name = "tipo_reserva_secuencia")
@NamedQueries({
    @NamedQuery(name = "TipoReservaSecuencia.findHijosByPadre", query = "SELECT t FROM TipoReservaSecuencia t WHERE t.idTipoReservaSecuenciaPadre = :idTipoReservaSecuenciaPadre"),
    @NamedQuery(name = "TipoReservaSecuencia.findRaizByIdTipoReserva", query = "SELECT t FROM TipoReservaSecuencia t WHERE t.idTipoReserva.idTipoReserva = :idTipoReserva AND t.idTipoReservaSecuenciaPadre IS NULL"),
    @NamedQuery(name = "TipoReservaSecuencia.findAll", query = "SELECT t FROM TipoReservaSecuencia t"),
    @NamedQuery(name = "TipoReservaSecuencia.findByIdTipoReservaSecuencia", query = "SELECT t FROM TipoReservaSecuencia t WHERE t.idTipoReservaSecuencia = :idTipoReservaSecuencia"),
    @NamedQuery(name = "TipoReservaSecuencia.findByIndicaFin", query = "SELECT t FROM TipoReservaSecuencia t WHERE t.indicaFin = :indicaFin"),
    @NamedQuery(name = "TipoReservaSecuencia.findByNombre", query = "SELECT t FROM TipoReservaSecuencia t WHERE t.nombre = :nombre")})
public class TipoReservaSecuencia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tipo_reserva_secuencia")
    private Long idTipoReservaSecuencia;
    @Column(name = "indica_fin")
    private Boolean indicaFin;
    @Column(name = "nombre")
    private String nombre;
    @OneToMany(mappedBy = "idTipoReservaSecuencia")
    private Collection<ReservaHistorial> reservaHistorialCollection;
    @JoinColumn(name = "id_tipo_reserva", referencedColumnName = "id_tipo_reserva")
    @ManyToOne
    private TipoReserva idTipoReserva;
    @OneToMany(mappedBy = "idTipoReservaSecuenciaPadre")
    private Collection<TipoReservaSecuencia> tipoReservaSecuenciaCollection;
    @JoinColumn(name = "id_tipo_reserva_secuencia_padre", referencedColumnName = "id_tipo_reserva_secuencia")
    @ManyToOne
    private TipoReservaSecuencia idTipoReservaSecuenciaPadre;

    public TipoReservaSecuencia() {
    }

    public TipoReservaSecuencia(Long idTipoReservaSecuencia) {
        this.idTipoReservaSecuencia = idTipoReservaSecuencia;
    }

    public Long getIdTipoReservaSecuencia() {
        return idTipoReservaSecuencia;
    }

    public void setIdTipoReservaSecuencia(Long idTipoReservaSecuencia) {
        this.idTipoReservaSecuencia = idTipoReservaSecuencia;
    }

    public Boolean getIndicaFin() {
        return indicaFin;
    }

    public void setIndicaFin(Boolean indicaFin) {
        this.indicaFin = indicaFin;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Collection<ReservaHistorial> getReservaHistorialCollection() {
        return reservaHistorialCollection;
    }

    public void setReservaHistorialCollection(Collection<ReservaHistorial> reservaHistorialCollection) {
        this.reservaHistorialCollection = reservaHistorialCollection;
    }

    public TipoReserva getIdTipoReserva() {
        return idTipoReserva;
    }

    public void setIdTipoReserva(TipoReserva idTipoReserva) {
        this.idTipoReserva = idTipoReserva;
    }

    public Collection<TipoReservaSecuencia> getTipoReservaSecuenciaCollection() {
        return tipoReservaSecuenciaCollection;
    }

    public void setTipoReservaSecuenciaCollection(Collection<TipoReservaSecuencia> tipoReservaSecuenciaCollection) {
        this.tipoReservaSecuenciaCollection = tipoReservaSecuenciaCollection;
    }

    public TipoReservaSecuencia getIdTipoReservaSecuenciaPadre() {
        return idTipoReservaSecuenciaPadre;
    }

    public void setIdTipoReservaSecuenciaPadre(TipoReservaSecuencia idTipoReservaSecuenciaPadre) {
        this.idTipoReservaSecuenciaPadre = idTipoReservaSecuenciaPadre;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoReservaSecuencia != null ? idTipoReservaSecuencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoReservaSecuencia)) {
            return false;
        }
        TipoReservaSecuencia other = (TipoReservaSecuencia) object;
        if ((this.idTipoReservaSecuencia == null && other.idTipoReservaSecuencia != null) || (this.idTipoReservaSecuencia != null && !this.idTipoReservaSecuencia.equals(other.idTipoReservaSecuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.parqueowebapp.TipoReservaSecuencia[ idTipoReservaSecuencia=" + idTipoReservaSecuencia + " ]";
    }
    
}
