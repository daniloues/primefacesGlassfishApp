package com.mycompany.parqueowebapp.boundary.jsf;

import com.mycompany.parqueowebapp.app.entity.TipoReserva;
import com.mycompany.parqueowebapp.control.AbstractDataAccess;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import com.mycompany.parqueowebapp.control.TipoReservaBean;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import java.util.stream.Collectors;
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author daniloues
 */
@Named
@ViewScoped
public class FrmTipoReserva extends frmAbstract<TipoReserva> implements Serializable {
    
    @Inject
    TipoReservaBean trBean;
    
    @Inject
    FacesContext fc;
    
    @Override
    public AbstractDataAccess<TipoReserva> getDataAccess() {
        return this.trBean;
    }

    public TipoReservaBean getTrBean() {
        return trBean;
    }

    @Override
    public FacesContext getFacesContext() {
        return this.fc;
    }

    @Override
    public String getIdPorObjeto(TipoReserva object) {
        if(object!=null && object.getIdTipoReserva()!=null){
            return object.getIdTipoReserva().toString();
        }
        return null;
    }

    @Override
    public TipoReserva getObjetoPorId(String id) {
        if(id!=null && this.modelo!= null && this.modelo.getWrappedData()!=null){
            return this.modelo.getWrappedData().stream().filter(r->r.getIdTipoReserva().toString().equals(id)).collect(Collectors.toList()).get(0);
        }
        return null;
    }

    @Override
    public void instanciarRegistro() {
        this.registro = new TipoReserva();
    }
    
    @Override
    public LazyDataModel<TipoReserva> getModelo() {
        return super.getModelo();
    }

}
