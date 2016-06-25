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
import org.primefaces.component.password.Password;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.usbcali.demo.delegado.IDelegadoDeNegocio;
import co.edu.usbcali.demo.modelo.TiposUsuarios;
import co.edu.usbcali.demo.modelo.Usuarios;

@ViewScoped
@ManagedBean
public class UsuarioVista {

	private Logger log = LoggerFactory.getLogger(UsuarioVista.class);
	
	@ManagedProperty(value = "#{delegadoDeNegocio}")
	private IDelegadoDeNegocio delegadoDeNegocio;

	private List<SelectItem> vLstOpTiposUsr;
	private List<Usuarios> vLstListaUsuario;
	
	private InputText txtCedula;
	private InputText txtNombre;
	private InputText txtLogin;
	private Password txtClave;

	private SelectOneMenu somTipoUsuario;
	
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
	
	private Usuarios usuarioSeleccionado;
	
	public Usuarios getUsuarioSeleccionado() {
		return usuarioSeleccionado;
	}

	public void setUsuarioSeleccionado(Usuarios usuarioSeleccionado) {
		this.usuarioSeleccionado = usuarioSeleccionado;
	}

	public InputText getTxtCedula() {
		return txtCedula;
	}

	public void setTxtCedula(InputText txtCedula) {
		this.txtCedula = txtCedula;
	}

	public InputText getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(InputText txtNombre) {
		this.txtNombre = txtNombre;
	}

	public InputText getTxtLogin() {
		return txtLogin;
	}

	public void setTxtLogin(InputText txtLogin) {
		this.txtLogin = txtLogin;
	}

	public Password getTxtClave() {
		return txtClave;
	}

	public void setTxtClave(Password txtClave) {
		this.txtClave = txtClave;
	}

	public SelectOneMenu getSomTipoUsuario() {
		return somTipoUsuario;
	}

	public void setSomTipoUsuario(SelectOneMenu somTipoUsuario) {
		this.somTipoUsuario = somTipoUsuario;
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
	
	public void setvLstOpTiposUsr(List<SelectItem> vLstOpTiposUsr) {
		this.vLstOpTiposUsr = vLstOpTiposUsr;
	}

	public List<Usuarios> getvLstListaUsuario() {
		if (vLstListaUsuario == null) {
			leerUsuariosGrid();
		}
		return vLstListaUsuario;
	}

	public void setvLstListaUsuario(List<Usuarios> vLstLista) {
		this.vLstListaUsuario = vLstLista;
	}
	

	public IDelegadoDeNegocio getDelegadoDeNegocio() {
		return delegadoDeNegocio;
	}

	public void setDelegadoDeNegocio(IDelegadoDeNegocio delegadoDeNegocio) {
		this.delegadoDeNegocio = delegadoDeNegocio;
	}
	

	public void txtCedListender(){
		Usuarios vObjUsuario = null;
		try {
			vObjUsuario = delegadoDeNegocio.consultarUsuariosPorCedula(Long.parseLong(txtCedula.getValue().toString().trim()));
		} catch (Exception e) {
			
		}
		if(vObjUsuario== null){
			
			LimpiarDatosForm(false);
		}else{
			CargarCamposForm(vObjUsuario, false);
		}
		
	}
	
	private void CargarCamposForm(Usuarios pvUsuario, boolean pvBlnCargarCedula){
		if (pvBlnCargarCedula==true){
			txtCedula.setValue(pvUsuario.getUsuCedula());
		}
		txtLogin.setValue(pvUsuario.getUsuLogin());
		txtNombre.setValue(pvUsuario.getUsuNombre());
		txtClave.setValue(pvUsuario.getUsuClave());
		
		somTipoUsuario.setValue(pvUsuario.getTiposUsuarios().getTusuCodigo());
		
		btnBorrar.setDisabled(false);
		btnCrear.setDisabled(true);
		btnModificar.setDisabled(false);
	}
	private void LimpiarDatosForm(boolean pvBlnLimpiarId){
		
		txtLogin.resetValue();
		txtNombre.resetValue();
		txtClave.resetValue();
		somTipoUsuario.setValue("-1");
		
		btnBorrar.setDisabled(true);
		btnModificar.setDisabled(true);
		
		if (pvBlnLimpiarId == true){
			txtCedula.resetValue();
			btnCrear.setDisabled(true);
		}
		else{
			btnCrear.setDisabled(false);			
		}
	}
	
	private Usuarios HidratarUsuario() throws Exception{
		
		try {
			Usuarios vObjUsuario=new Usuarios();
			vObjUsuario.setUsuLogin(txtLogin.getValue().toString().trim());
			vObjUsuario.setUsuCedula(Long.parseLong(txtCedula.getValue().toString().trim()));
			vObjUsuario.setUsuNombre(txtNombre.getValue().toString().trim());
			vObjUsuario.setUsuClave(txtClave.getValue().toString().trim());
			
			TiposUsuarios vObjtiposUsuarios;
			vObjtiposUsuarios = delegadoDeNegocio.consultarTiposUsuariosPorID(Long.parseLong(somTipoUsuario.getValue().toString()));
			vObjUsuario.setTiposUsuarios(vObjtiposUsuarios);
			return vObjUsuario;
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	public String crearAction(){
		log.info("Ingreso a crear");
		try {
			Usuarios vObjUsuario  =HidratarUsuario();
			delegadoDeNegocio.grabarUsuarios(vObjUsuario);
			leerUsuariosGrid();
			btnBorrar.setDisabled(false);
			btnCrear.setDisabled(true);
			btnModificar.setDisabled(false);
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO, "El usuario se creo con exito", ""));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}
		return "";
	}
	public String modificarAction(){
		log.info("Ingreso a modificar");
		try {
			Usuarios vObjUsuario =HidratarUsuario();
			delegadoDeNegocio.modificarUsuarios(vObjUsuario);
			leerUsuariosGrid();			
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO, "El usuario se modificó con exito", ""));

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}
		return "";
	}
	public String borrarAction(){
		log.info("Ingreso a borrar");
		try {
			Usuarios vObjUsuario =new Usuarios();
			vObjUsuario.setUsuCedula(Long.parseLong(txtCedula.getValue().toString().trim()));
			delegadoDeNegocio.borrarUsuarios(vObjUsuario);
			LimpiarDatosForm(true);
			leerUsuariosGrid();
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO, "El usuario se borró con exito", ""));
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
	
	public List<SelectItem> getvLstOpTiposUsr() {
		if (vLstOpTiposUsr== null){
			try {
				vLstOpTiposUsr = new ArrayList<SelectItem>();
				List<TiposUsuarios>vLstTiposUsr = delegadoDeNegocio.consultarTodosTiposUsuarios();
				for (TiposUsuarios tiposUsuario : vLstTiposUsr) {
					vLstOpTiposUsr.add(new SelectItem(tiposUsuario.getTusuCodigo(), tiposUsuario.getTusuNombre()));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return vLstOpTiposUsr;
	}

	
	private void leerUsuariosGrid(){
		try {
			vLstListaUsuario = delegadoDeNegocio.consultarTodosUsuarios();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	  public void filaUsuarioSelect(SelectEvent event) {
		 CargarCamposForm((Usuarios) event.getObject(), true);
		
	  }
	
	

}
