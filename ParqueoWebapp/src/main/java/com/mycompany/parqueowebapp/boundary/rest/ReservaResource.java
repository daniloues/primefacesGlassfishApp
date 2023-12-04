/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.parqueowebapp.boundary.rest;

import com.mycompany.parqueowebapp.app.entity.Espacio;
import com.mycompany.parqueowebapp.app.entity.Reserva;
import com.mycompany.parqueowebapp.control.ReservaBean;
import com.mycompany.parqueowebapp.websocket.wsNotificarCambios;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author daniloues
 */
@Path("reserva")
public class ReservaResource implements Serializable {

    List<reservaDTO> jsonBack = new ArrayList<>();
    
    reservaDTO dtoObjeto;
    
    @Inject
    ReservaBean rBean;
    
    @Inject
    wsNotificarCambios wsNC;
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<reservaDTO> findRange(){
            
        
        List<Reserva> listaReserva = rBean.findAll();
        
        for(Reserva reserva : listaReserva){
            System.out.println("IdReserva: "+reserva.getIdReserva());
            dtoObjeto = convertToDTO(reserva, reserva.getIdEspacio());
            
            System.out.println("IdReservaenDTO: "+dtoObjeto.getIdReserva());
            jsonBack.add(dtoObjeto);
            }
        
        
        
        return jsonBack;
        
        
    }
    
    private reservaDTO convertToDTO(Reserva reserva, Espacio espacio) {
        reservaDTO dto = new reservaDTO();
        dto.setIdReserva(reserva.getIdReserva().toString());
        dto.setDesde(reserva.getDesde().toString());
        dto.setHasta(reserva.getHasta().toString());
        dto.setEspacio(reserva.getIdEspacio().getNombre());

        return dto;
    }
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/{id}")
    public Response findById(
            @PathParam("id")
            final Integer idReserva) {
        if (idReserva != null) {
            Reserva findById = rBean.findById(idReserva);
            if (findById != null) {
                return Response.status(Response.Status.OK).entity(findById).build();
            }
            return Response.status(Response.Status.NOT_FOUND).header("not-found", idReserva).build();
        }
        return Response.status(422).
                header("missing-parameter", "id").
                build();
    }
    
}