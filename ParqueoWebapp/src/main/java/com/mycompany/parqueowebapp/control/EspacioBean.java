/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.parqueowebapp.control;

import com.mycompany.parqueowebapp.app.entity.Area;
import com.mycompany.parqueowebapp.app.entity.Espacio;
import jakarta.ejb.Local;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author daniloues
 */
@Stateless
@Local
public class EspacioBean extends AbstractDataAccess<Espacio> implements Serializable {

    @PersistenceContext(unitName = "ParqueoPU")
    EntityManager em;

    public List<Espacio> activosLista() {
        TypedQuery q = em.createNamedQuery("Espacio.findByActivo", Espacio.class);
        q.setParameter("activo", true);
        return q.getResultList();
    }

    public List<Espacio> findByIdArea(final Integer idArea, int primero, int tamanio) {
        if (idArea != null && primero >= 0 && tamanio > 0) {
            if (em != null) {
                Query q = em.createNamedQuery("Espacio.findByIdArea");
                q.setParameter("idArea", idArea);
                q.setFirstResult(primero);
                q.setMaxResults(tamanio);
                return q.getResultList();
            }
        }
        return Collections.EMPTY_LIST;
    }

    public List<Area> findByIdPadre(final Integer idAreaPadre, int primero, int tamanio) {
        if (idAreaPadre != null && primero >= 0 && tamanio > 0) {
            if (em != null) {
                Query q = em.createNamedQuery("Area.findByIdPadre");
                q.setParameter("idAreaPadre", idAreaPadre);
                q.setFirstResult(primero);
                q.setMaxResults(tamanio);
                return q.getResultList();
            }
        }
        return Collections.EMPTY_LIST;
    }

    public List<Espacio> findEspaciosByIdAreaPadre(final Integer idAreaPadre, int primero, int tamanio) {
        if (idAreaPadre != null && primero >= 0 && tamanio > 0) {
            if (em != null) {
                List<Espacio> espaciosTotales = null;
                if (countByIdArea(idAreaPadre) > 0) {

                    Query q = em.createNamedQuery("Espacio.findByEspaciosByListAreas");
                    q.setParameter("idAreaPadre", idAreaPadre);
                    q.setFirstResult(0);
                    q.setMaxResults(0);

                    if (countByIdArea(idAreaPadre) > 0) {

                        List<Area> listAreas = findByIdPadre(idAreaPadre, 0, 10000000);
                        for (Area areaHija : listAreas) {
                            espaciosTotales.addAll(findEspaciosByIdAreaPadre(areaHija.getIdArea(), 0, 100000));
                        }

                    } else {
                        return q.getResultList();
                    }

                    return espaciosTotales;
                } else {
                    espaciosTotales = findByIdArea(idAreaPadre, primero, tamanio);
                    return espaciosTotales;
                }

            }

            return Collections.EMPTY_LIST;
        }
        return Collections.EMPTY_LIST;
    }

    public List<Espacio> filtrarPorCaracteristicas(List<Espacio> espacios, List<Integer> idsCaracteristicas) {
        if (espacios == null || espacios.isEmpty() || idsCaracteristicas == null || idsCaracteristicas.isEmpty()) {
            return espacios; // No hay espacios o características para filtrar
        }

        // Crear una lista de IDs de espacios
        List<Long> idsEspacios=new ArrayList<>();
        for (Espacio OEspacio : espacios) {
            idsEspacios.add(OEspacio.getIdEspacio());
        }
       
        List<Long> idCaracteristicasLong = new ArrayList<>();
        for(Integer caracteristica : idsCaracteristicas){
            idCaracteristicasLong.add(caracteristica.longValue());
        }
       
       
        Query q = em.createNamedQuery("Espacio.filtrarCaracteristicas");
        q.setParameter("idsEspacios", idsEspacios);
        q.setParameter("idsCaracteristicas", idCaracteristicasLong);

        // Obtener el resultado de la consulta
        return q.getResultList();
    }

    private String construirClausaCaracteristicas(List<String> caracteristicas) {
        StringBuilder jpql = new StringBuilder();

        for (int i = 0; i < caracteristicas.size(); i++) {
            jpql.append(" AND EXISTS (SELECT 1 FROM EspacioCaracteristica ec")
                    .append(" WHERE ec.idEspacio = e.idEspacio")
                    .append(" AND ec.descripcion = :caracteristica").append(i).append(")");
        }

        return jpql.toString();
    }

    public int countByIdArea(final Integer idArea) {
        if (idArea != null) {
            if (em != null) {
                Query q = em.createNamedQuery("Espacio.countByIdArea");
                q.setParameter("idArea", idArea);
                return ((Long) (q.getSingleResult())).intValue();
            }
        }
        return 0;
    }

    public List<String> findNombresByIdArea(final Integer idArea) {

        if (idArea != null) {
            if (em != null) {

                Query q = em.createNamedQuery("Espacio.findNombresByIdArea");
                q.setParameter("idArea", idArea);
                return q.getResultList();

            }

        }

        return Collections.EMPTY_LIST;
    }

    @Override
    public String entityQuery() {
        return ("Espacio");
    }

    public EntityManager getEntityManager() {
        return em;
    }

    public EspacioBean() {
        super(Espacio.class);
    }

}
