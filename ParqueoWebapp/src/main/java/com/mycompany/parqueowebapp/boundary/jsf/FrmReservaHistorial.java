package com.mycompany.parqueowebapp.boundary.jsf;

import com.mycompany.parqueowebapp.app.entity.ReservaHistorial;
import com.mycompany.parqueowebapp.app.entity.TipoReserva;
import com.mycompany.parqueowebapp.app.entity.TipoReservaSecuencia;
import com.mycompany.parqueowebapp.control.AbstractDataAccess;
import jakarta.inject.Named;
import java.io.Serializable;
import com.mycompany.parqueowebapp.control.ReservaHistorialBean;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.Dependent;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.TreeNode;

/**
 *
 * @author daniloues
 */
@Named
@Dependent
public class FrmReservaHistorial extends frmAbstract<ReservaHistorial> implements Serializable {
    
    
    /*
    EN ESTE FRM SE DEBE REGRESAR EL REGISTRO ACTUAL QUE SE CREA CUANDO SE INICIALIZA, USAR NUEVAMENTE DE EJEMPLO EL FrmArea,
    MODIFICAR ACORDE
    */
    
    @Inject
    FrmTipoReservaSecuencia frmTRS;
    
    @Inject
    ReservaHistorialBean rhBean;

    @Inject
    FacesContext fc;

    TreeNode raiz;
    TreeNode nodoSeleccionado;
    TipoReserva idTipoReserva;
    
    @PostConstruct
    @Override
    public void inicializar() {
        super.inicializar();
        instanciarRegistro();
    }
    
    
    // EN ESTE PUNTO EL REGISTRO DE ESTE FORMULARIO TIENE EL IDRESERVA SETEADO Y EL IDTIPORESERVA SETEADO
    
    public void guardarHistorialReserva() {
        
        
        registro.setIdTipoReservaSecuencia(frmTRS.findRaizTipoReserva(idTipoReserva));
        registro.setActivo(true);
        registro.setFechaAlcanzado(new Date());
        rhBean.create(registro);
    }
    
    
        public void generarArbol(TreeNode padre, TipoReservaSecuencia actual) {
        DefaultTreeNode nuevoPadre = new DefaultTreeNode(actual, padre);
        List<TipoReservaSecuencia> hijos = this.frmTRS.findHijosByTipoReservaSecuencia(actual);
        for (TipoReservaSecuencia hijo : hijos) {
            generarArbol(nuevoPadre, hijo);
        }
    }
    
    /*
    ESTE METODO SE ENCARGA DE SELECCIONAR EL NODO DEL ARBOL SEGUN EL ELEMENTO SELECCIONADO
    */
    public void seleccionarNodoListener(NodeSelectEvent nse) {
        this.registro = (ReservaHistorial) nse.getTreeNode().getData();
        this.seleccionarRegistro();
        
    }
    
    
    
    
    @Override
    public AbstractDataAccess<ReservaHistorial> getDataAccess() {
        return this.rhBean;
    }

    @Override
    public FacesContext getFacesContext() {
        return this.fc;
    }

    @Override
    public String getIdPorObjeto(ReservaHistorial object) {
        if (object != null && object.getIdReservaHistorial() != null) {
            return object.getIdReservaHistorial().toString();
        }
        return null;
    }

    @Override
    public ReservaHistorial getObjetoPorId(String id) {
        if (id != null && this.modelo != null && this.modelo.getWrappedData() != null) {
            return this.modelo.getWrappedData().stream().filter(r -> r.getIdReservaHistorial().toString().equals(id)).collect(Collectors.toList()).get(0);
        }
        return null;
    }


    @Override
    public void instanciarRegistro() {
        this.registro = new ReservaHistorial();
    }

    @Override
    public LazyDataModel<ReservaHistorial> getModelo() {
        return super.getModelo();
    }
    
    public TreeNode getRaiz() {
        return raiz;
    }

    public void setRaiz(TreeNode raiz) {
        this.raiz = raiz;
    }
    
    public TreeNode getNodoSeleccionado() {
        return nodoSeleccionado;
    }

    public void setNodoSeleccionado(TreeNode nodoSeleccionado) {
        this.nodoSeleccionado = nodoSeleccionado;
    }

    @Override
    public ReservaHistorial getRegistro() {
        return registro;
    }

    @Override
    public void setRegistro(ReservaHistorial registro) {
        this.registro = registro;
    }
    
    
}
