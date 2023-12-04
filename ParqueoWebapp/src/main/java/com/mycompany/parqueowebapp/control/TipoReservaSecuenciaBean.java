/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.parqueowebapp.control;

import com.mycompany.parqueowebapp.app.entity.TipoReserva;
import com.mycompany.parqueowebapp.app.entity.TipoReservaSecuencia;
import jakarta.ejb.Local;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author daniloues
 */
@Stateless
@Local
public class TipoReservaSecuenciaBean extends AbstractDataAccess<TipoReservaSecuencia> implements Serializable {

    @PersistenceContext(unitName = "ParqueoPU")
    EntityManager em;

    public TipoReservaSecuencia findRaizByTipoReserva(Integer idTipoReserva) {

        Query q = em.createNamedQuery("TipoReservaSecuencia.findRaizByIdTipoReserva");
        q.setParameter("idTipoReserva", idTipoReserva);
        return (TipoReservaSecuencia) q.getSingleResult();

    }

    public List<TipoReservaSecuencia> findHijosByPadre(TipoReservaSecuencia idTipoReservaSecuenciaPadre) {
        
        Query q = em.createNamedQuery("TipoReservaSecuencia.findHijosByPadre");
        q.setParameter("idTipoReservaSecuenciaPadre", idTipoReservaSecuenciaPadre);
        return q.getResultList();

    }
    
    @Override
    public String entityQuery() {
        return ("TipoReservaSecuencia");
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public TipoReservaSecuenciaBean() {
        super(TipoReservaSecuencia.class);
    }

}
