package com.mycompany.parqueowebapp.boundary.jsf;

import com.mycompany.parqueowebapp.app.entity.TipoReserva;
import com.mycompany.parqueowebapp.app.entity.TipoReservaSecuencia;
import com.mycompany.parqueowebapp.control.AbstractDataAccess;
import jakarta.inject.Named;
import java.io.Serializable;
import com.mycompany.parqueowebapp.control.TipoReservaSecuenciaBean;
import jakarta.enterprise.context.Dependent;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;
/**
 *
 * @author daniloues
 */
import org.primefaces.model.LazyDataModel;

@Named
@Dependent
public class FrmTipoReservaSecuencia extends frmAbstract<TipoReservaSecuencia> implements Serializable {

    @Inject
    TipoReservaSecuenciaBean trsBean;

    @Inject
    FacesContext fc;
    
    
    public TipoReservaSecuencia findRaizTipoReserva (TipoReserva idTipoReserva){
        
    return trsBean.findRaizByTipoReserva(idTipoReserva.getIdTipoReserva());
    }
    
    
    public List<TipoReservaSecuencia> findHijosByTipoReservaSecuencia (TipoReservaSecuencia idTipoReservaSecuencia){
        
        return trsBean.findHijosByPadre(idTipoReservaSecuencia);
        
    }
    
    
//    public void setIdArea(Integer idArea) {
//        this.idArea = idArea;
//    }
//
//    public Integer getIdArea() {
//        return idArea;
//    }
//
//    @Override
//    public List<TipoReservaSecuencia> cargarDatos(int primero, int tamanio) {
//        if (this.idArea != null) {
//            return this.eBean.findByIdArea(this.idArea, primero, tamanio);
//        }
//        return Collections.EMPTY_LIST;
//    }

//    @Override
//    public int contar() {
//        if (this.idArea != null) {
//            return this.eBean.countByIdArea(this.idArea);
//        }
//        return 0;
//    }

    @Override
    public AbstractDataAccess<TipoReservaSecuencia> getDataAccess() {
        return trsBean;
    }

    @Override
    public FacesContext getFacesContext() {
        return fc;
    }

    @Override
    public String getIdPorObjeto(TipoReservaSecuencia object) {
        if (object != null && object.getIdTipoReservaSecuencia() != null) {
            return object.getIdTipoReservaSecuencia().toString();
        }
        return null;
    }

    @Override
    public TipoReservaSecuencia getObjetoPorId(String id) {
        if (id != null && this.modelo != null && this.modelo.getWrappedData() != null) {
            return this.modelo.getWrappedData().stream().filter(r -> r.getIdTipoReservaSecuencia().toString().equals(id)).collect(Collectors.toList()).get(0);
        }
        return null;
    }

    @Override
    public void instanciarRegistro() {
//        this.registro = new TipoReservaSecuencia();
//        if (this.idArea != null) {
//            this.registro.setIdArea(new Area(idArea));
//        }
//        this.registro.setActivo(true);
    }

    @Override
    public LazyDataModel<TipoReservaSecuencia> getModelo() {
        return super.getModelo();
    }
}
