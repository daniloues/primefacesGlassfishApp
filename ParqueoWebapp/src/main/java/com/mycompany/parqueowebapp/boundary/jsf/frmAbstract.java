/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.parqueowebapp.boundary.jsf;

import com.mycompany.parqueowebapp.control.AbstractDataAccess;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Collections;
import java.io.Serializable;
import java.util.Map;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

public abstract class frmAbstract<T> implements Serializable {

    public abstract AbstractDataAccess<T> getDataAccess();

    LazyDataModel<T> modelo;

    EstadosCRUD estado = EstadosCRUD.NINGUNO;

    T registro = null;

    public abstract FacesContext getFacesContext();

    public int contar() {
        int resultado = 0;
        AbstractDataAccess<T> allBean = null;
        try {

            allBean = getDataAccess();
            resultado = allBean.count();

        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);

        }
        return resultado;
    }

    public List<T> cargarDatos(int primero, int tamanio) {
        List<T> resultado = null;
        try {

            AbstractDataAccess<T> allBean = getDataAccess();
            resultado = allBean.findRange(primero, tamanio);

        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);

        } finally {
            if (resultado == null) {
                resultado = Collections.EMPTY_LIST;
            }

        }
        return resultado;
    }

    @PostConstruct
    public void inicializar() {
        inicializarRegistros();
    }

    public void inicializarRegistros() {

        this.modelo = new LazyDataModel<T>() {
            @Override
            public int count(Map<String, FilterMeta> map) {
                return contar();
            }

            @Override
            public List<T> load(int primero, int tamanio, Map<String, SortMeta> map, Map<String, FilterMeta> map1) {
                return cargarDatos(primero, tamanio);
            }

            @Override
            public String getRowKey(T object) {
                if (object != null) {
                    return getIdPorObjeto(object);
                }
                return null;
            }

            @Override
            public T getRowData(String rowKey) {
                if (rowKey != null) {
                    return getObjetoPorId(rowKey);
                }
                return null;
            }

        };

    }

    public abstract String getIdPorObjeto(T object);

    public abstract T getObjetoPorId(String id);

    public abstract void instanciarRegistro();

    public void seleccionarRegistro() {
        this.estado = EstadosCRUD.MODIFICAR;
    }

    public void btnNuevoHandler(ActionEvent ae) {
        this.instanciarRegistro();
        this.estado = EstadosCRUD.NUEVO;
    }

    public void btnCancelarHandler(ActionEvent ae) {
        this.registro = null;
        this.estado = EstadosCRUD.NINGUNO;
    }

    public void btnGuardarHandler(ActionEvent ae) {
        FacesMessage mensaje;
        try {
            AbstractDataAccess<T> allBean = getDataAccess();
            allBean.create(registro);
            this.estado = EstadosCRUD.NINGUNO;
            this.registro = null;
            mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro guardado con exito", "Se creo el registro");
            getFacesContext().addMessage(null, mensaje);
        } catch (IllegalArgumentException | IllegalStateException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
            mensaje = new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se pudo guardar el registro", "No se pudo almacenar el registro");
            getFacesContext().addMessage(null, mensaje);
            this.registro = null;
        }
    }

    public void btnModificarHandler(ActionEvent ae) {
        T modify = null;
        try {
            AbstractDataAccess<T> allBean = getDataAccess();
            modify = allBean.modify(registro);
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);

        }
        if (modify != null) {
            this.estado = EstadosCRUD.NINGUNO;
            this.registro = null;
        }
    }

    public void btnEliminarHandler(ActionEvent ae) {
        try {
            AbstractDataAccess<T> allBean = getDataAccess();
            allBean.delete(registro);
            this.estado = EstadosCRUD.NINGUNO;
            this.registro = null;
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);

        }

    }

    public T getRegistro() {
        return registro;
    }

    public void setRegistro(T registro) {
        this.registro = registro;
    }

    public LazyDataModel<T> getModelo() {
        return this.modelo;
    }

    public EstadosCRUD getEstado() {
        return estado;
    }

}
