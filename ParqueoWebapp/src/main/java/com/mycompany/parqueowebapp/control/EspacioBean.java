/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.parqueowebapp.control;

import com.mycompany.parqueowebapp.app.entity.Area;
import com.mycompany.parqueowebapp.app.entity.Espacio;
import com.mycompany.parqueowebapp.app.entity.Reserva;
import com.mycompany.parqueowebapp.app.entity.TipoEspacio;
import jakarta.ejb.Local;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
/**
 *
 * @author daniloues
 */
@Stateless
@Local
public class EspacioBean extends AbstractDataAccess<Espacio> implements Serializable {

    @PersistenceContext(unitName = "ParqueoPU")
    EntityManager em;

    public int countByIdPadre(final Integer idAreaPadre) {
        if (idAreaPadre != null) {
            if (em != null) {
                Query q = em.createNamedQuery("Area.countByIdPadre");
                q.setParameter("idAreaPadre", idAreaPadre);
                return ((Long) (q.getSingleResult())).intValue();
            }
        }
        return 0;
    }

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

    public List<Espacio> filtrarLiberados(List<Espacio> listaOcupados){
        
        Query q = em.createNamedQuery("ReservaHistorial.findByActivo");
        q.setParameter("activo", false);
        List<Espacio> listaReservasFinalizadas = q.getResultList();
        filtrarLista(listaOcupados, listaReservasFinalizadas);
        
        return listaOcupados;
    }
    
    
       private void filtrarLista(List<Espacio> listaConservar, List<Espacio> listaEliminar) {
        Iterator<Espacio> iterator = listaConservar.iterator();
        while (iterator.hasNext()) {
            Espacio espacioActivo = iterator.next();
            for (Espacio espacioOcupado : listaEliminar) {
                if (espacioActivo.equals(espacioOcupado)) {
                    iterator.remove();
                    break;
                }
            }
        }
    }
    
    public List<Espacio> filtrarOcupados(List<Espacio> listaTentativa, Date desde, Date hasta) {

        Query q = em.createNamedQuery("Espacio.findOcupados");
        q.setParameter("desde", desde);
        q.setParameter("hasta", hasta);
        List<Espacio> listaOcupados = q.getResultList();
        listaOcupados = filtrarLiberados(listaOcupados);
        filtrarLista(listaTentativa, listaOcupados);
        return listaTentativa;
    }

    public List<Espacio> findEspaciosByIdAreaPadre(final Integer idAreaPadre, int primero, int tamanio) {
        if (idAreaPadre != null && primero >= 0 && tamanio > 0) {
            if (em != null) {
                List<Espacio> espaciosTotales = null;
                if (countByIdPadre(idAreaPadre) > 0) {

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

    public List<Espacio> filtrarPorCaracteristicas(List<Espacio> espacios, List<TipoEspacio> idsCaracteristicas) {
        if (espacios == null || espacios.isEmpty() || idsCaracteristicas == null || idsCaracteristicas.isEmpty()) {
            return espacios; // No hay espacios o características para filtrar
        }
        boolean valido;
        // Crear una lista de IDs de espacios
        List<Long> idsEspacios = new ArrayList<>();
        for (Espacio OEspacio : espacios) {
            idsEspacios.add(OEspacio.getIdEspacio());
            System.out.println(OEspacio.getNombre());
        }

        List<Long> idCaracteristicasLong = new ArrayList<>();
        for (TipoEspacio caracteristica : idsCaracteristicas) {
            idCaracteristicasLong.add(caracteristica.getIdTipoEspacio().longValue());
            System.out.println(caracteristica.toString());
        }

        Query q = em.createNamedQuery("Espacio.filtrarCaracteristicas");
        Query q2 = em.createNamedQuery("Espacio.findCaracteristicaMatch");
        q.setParameter("idsEspacios", idsEspacios);
        q.setParameter("idsCaracteristicas", idCaracteristicasLong);
        List<Espacio> listaEspacioTentativa = q.getResultList();
        List<Espacio> listaEspacioFinal = new ArrayList<>();

        // LA LISTA QUE TIENE AL MENOS 1 CARACTERISTICA, AHORA SE CHEQUEARA QUE TODAS MATCHEEN
        for (Espacio listaEspacio : listaEspacioTentativa) {
            valido = true;
            q2.setParameter("idEspacio", listaEspacio.getIdEspacio());
            for (long idCaracteristica : idCaracteristicasLong) {
                q2.setParameter("idCaracteristica", idCaracteristica);
                if (q2.getResultList().isEmpty()) {
                    valido = false;
                }
            }
            if (valido) {
                listaEspacioFinal.add(listaEspacio);
            }

        }

        // Obtener el resultado de la consulta
        return listaEspacioFinal;
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
