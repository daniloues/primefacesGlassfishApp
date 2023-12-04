/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.parqueowebapp.control;

import com.mycompany.parqueowebapp.app.entity.Reserva;
import jakarta.ejb.Local;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author daniloues
 */
@Stateless
@Local
public class ReservaBean extends AbstractDataAccess<Reserva> implements Serializable {

    @PersistenceContext(unitName = "ParqueoPU")
    EntityManager em;

    public List<Reserva> mayor2() {
        TypedQuery q = em.createNamedQuery("Reserva.findLargerThan2", Reserva.class);
        return q.getResultList();
    }
    

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public ReservaBean() {
        super(Reserva.class);
    }

    @Override
    public String entityQuery() {
        return ("Reserva");
    }

}
