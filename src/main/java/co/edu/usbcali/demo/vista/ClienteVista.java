package co.edu.usbcali.demo.vista;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.usbcali.demo.delegado.IDelegadoDeNegocio;
import co.edu.usbcali.demo.modelo.Clientes;
import co.edu.usbcali.demo.modelo.TiposDocumentos;

@ViewScoped
@ManagedBean
public class ClienteVista {

	private Logger log = LoggerFactory.getLogger(ClienteVista.class);
	
	@ManagedProperty(value = "#{delegadoDeNegocio}")
	private IDelegadoDeNegocio delegadoDeNegocio;

	private List<SelectItem> vLstOpTiposDocto;
	private List<Clientes> vLstListaCliente;
	
	private InputText txtID;
	private InputText txtNombre;
	private InputText txtTelefono;
	private InputText txtDireccion;
	private InputText txtMail;

	private SelectOneMenu somTipoDocumento;
	
	private CommandButton btnCrear;
	private CommandButton btnModificar;
	private CommandButton btnBorrar;
	private CommandButton btnLimpiar;
	/*private DataTable dttListaClientes;
	
	public DataTable getDttListaClientes() {
		return dttListaClientes;
	}

	public void setDttListaClientes(DataTable dttListaClientes) {
		this.dttListaClientes = dttListaClientes;
	}
	*/
	
	private Clientes clienteSeleccionado;
	
	public Clientes getClienteSeleccionado() {
		return clienteSeleccionado;
	}

	public void setClienteSeleccionado(Clientes clienteSeleccionado) {
		this.clienteSeleccionado = clienteSeleccionado;
	}

	
	
	public InputText getTxtID() {
		return txtID;
	}

	public void setTxtID(InputText txtID) {
		this.txtID = txtID;
	}

	public InputText getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(InputText txtNombre) {
		this.txtNombre = txtNombre;
	}

	public InputText getTxtTelefono() {
		return txtTelefono;
	}

	public void setTxtTelefono(InputText txtTelefono) {
		this.txtTelefono = txtTelefono;
	}

	public InputText getTxtDireccion() {
		return txtDireccion;
	}

	public void setTxtDireccion(InputText txtDireccion) {
		this.txtDireccion = txtDireccion;
	}

	public InputText getTxtMail() {
		return txtMail;
	}

	public void setTxtMail(InputText txtMail) {
		this.txtMail = txtMail;
	}

	public SelectOneMenu getSomTipoDocumento() {
		return somTipoDocumento;
	}

	public void setSomTipoDocumento(SelectOneMenu somTipoDocumento) {
		this.somTipoDocumento = somTipoDocumento;
	}

	public CommandButton getBtnCrear() {
		return btnCrear;
	}

	public void setBtnCrear(CommandButton btnCrear) {
		this.btnCrear = btnCrear;
	}

	public CommandButton getBtnModificar() {
		return btnModificar;
	}

	public void setBtnModificar(CommandButton btnModificar) {
		this.btnModificar = btnModificar;
	}

	public CommandButton getBtnBorrar() {
		return btnBorrar;
	}

	public void setBtnBorrar(CommandButton btnBorrar) {
		this.btnBorrar = btnBorrar;
	}

	public CommandButton getBtnLimpiar() {
		return btnLimpiar;
	}

	public void setBtnLimpiar(CommandButton btnLimpiar) {
		this.btnLimpiar = btnLimpiar;
	}
	
	public void setvLstOpTiposDocto(List<SelectItem> vLstOpTiposDocto) {
		this.vLstOpTiposDocto = vLstOpTiposDocto;
	}

	public List<Clientes> getvLstListaCliente() {
		if (vLstListaCliente == null) {
			leerClientesGrid();
		}

		return vLstListaCliente;
	}

	public void setvLstListaCliente(List<Clientes> vLstLista) {
		this.vLstListaCliente = vLstLista;
	}

	public IDelegadoDeNegocio getDelegadoDeNegocio() {
		return delegadoDeNegocio;
	}

	public void setDelegadoDeNegocio(IDelegadoDeNegocio delegadoDeNegocio) {
		this.delegadoDeNegocio = delegadoDeNegocio;
	}
	

	public void txtIdListener(){
		Clientes vObjCliente = null;
		try {
			vObjCliente = delegadoDeNegocio.consultarClinetePorId(Long.parseLong(txtID.getValue().toString().trim()));
		} catch (Exception e) {
			
		}
		if(vObjCliente== null){
			
			LimpiarDatosForm(false);
		}else{
			CargarCamposForm(vObjCliente, false);
		}
		
	}
	
	private void CargarCamposForm(Clientes pvCliente, boolean pvBlnCargarId){
		if (pvBlnCargarId==true){
			txtID.setValue(pvCliente.getCliId());
		}
		txtDireccion.setValue(pvCliente.getCliDireccion());
		txtMail.setValue(pvCliente.getCliMail());
		txtNombre.setValue(pvCliente.getCliNombre());
		txtTelefono.setValue(pvCliente.getCliTelefono());
		
		somTipoDocumento.setValue(pvCliente.getTiposDocumentos().getTdocCodigo());
		
		btnBorrar.setDisabled(false);
		btnCrear.setDisabled(true);
		btnModificar.setDisabled(false);
	}
	private void LimpiarDatosForm(boolean pvBlnLimpiarId){
		
		txtMail.resetValue();
		txtNombre.resetValue();
		txtTelefono.resetValue();
		txtDireccion.resetValue();
		somTipoDocumento.setValue("-1");
		
		btnBorrar.setDisabled(true);
		btnModificar.setDisabled(true);
		
		if (pvBlnLimpiarId == true){
			txtID.resetValue();
			btnCrear.setDisabled(true);
		}
		else{

			btnCrear.setDisabled(false);
			
		}
	}
	
	private Clientes HidratarCliente() throws Exception{
		
		try {
			Clientes vObjCliente=new Clientes();
			vObjCliente.setCliDireccion(txtDireccion.getValue().toString().trim());
			vObjCliente.setCliId(Long.parseLong(txtID.getValue().toString().trim()));
			vObjCliente.setCliMail(txtMail.getValue().toString().trim());
			vObjCliente.setCliNombre(txtNombre.getValue().toString().trim());
			vObjCliente.setCliTelefono(txtTelefono.getValue().toString().trim());
			TiposDocumentos vObjtiposDocumentos;
			vObjtiposDocumentos = delegadoDeNegocio.consultarTiposDocumentosPorID(Long.parseLong(somTipoDocumento.getValue().toString()));
			vObjCliente.setTiposDocumentos(vObjtiposDocumentos);
			return vObjCliente;
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	public String crearAction(){
		log.info("Ingreso a crear");
		try {
			Clientes vObjCliente =HidratarCliente();
			delegadoDeNegocio.grabarCliente(vObjCliente);
			leerClientesGrid();
			btnBorrar.setDisabled(false);
			btnCrear.setDisabled(true);
			btnModificar.setDisabled(false);
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO, "El cliente se creo con exito", ""));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}
		return "";
	}
	public String modificarAction(){
		log.info("Ingreso a modificar");
		try {
			Clientes vObjCliente =HidratarCliente();
			delegadoDeNegocio.modificarCliente(vObjCliente);
			leerClientesGrid();			
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO, "El cliente se modificó con exito", ""));

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}
		return "";
	}
	public String borrarAction(){
		log.info("Ingreso a borrar");
		try {
			Clientes vObjCliente =new Clientes();
			vObjCliente.setCliId(Long.parseLong(txtID.getValue().toString().trim()));
			delegadoDeNegocio.borrarCliente(vObjCliente);
			LimpiarDatosForm(true);
			leerClientesGrid();
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO, "El cliente se borró con exito", ""));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}
		return "";
	}
	public String limpiarAction(){
		log.info("Ingreso a limpiar");
		LimpiarDatosForm(true);
		return "";
	}
	
	public List<SelectItem> getvLstOpTiposDocto() {
		if (vLstOpTiposDocto== null){
			try {
				vLstOpTiposDocto = new ArrayList<SelectItem>();
				List<TiposDocumentos>vLstTiposDocto = delegadoDeNegocio.consultarTodosTiposDocumentos();
				for (TiposDocumentos tiposDocumentos : vLstTiposDocto) {
					vLstOpTiposDocto.add(new SelectItem(tiposDocumentos.getTdocCodigo(), tiposDocumentos.getTdocNombre()));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return vLstOpTiposDocto;
	}

	
	private void leerClientesGrid(){
		try {
			vLstListaCliente = delegadoDeNegocio.consultarTodosCliente();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	  public void filaClienteSelect(SelectEvent event) {
		 CargarCamposForm((Clientes) event.getObject(), true);
		
	  }
	
	

}
