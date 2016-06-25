package co.edu.usbcali.demo.vista;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.primefaces.extensions.component.inputnumber.InputNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.usbcali.demo.delegado.IDelegadoDeNegocio;
import co.edu.usbcali.demo.dto.TransaccionesInfo;
import co.edu.usbcali.demo.modelo.Clientes;
import co.edu.usbcali.demo.modelo.Consignaciones;
import co.edu.usbcali.demo.modelo.ConsignacionesId;
import co.edu.usbcali.demo.modelo.Cuentas;
import co.edu.usbcali.demo.modelo.Retiros;
import co.edu.usbcali.demo.modelo.RetirosId;
import co.edu.usbcali.demo.modelo.Usuarios;

@ViewScoped
@ManagedBean
public class TransaccionalVista {

	private Logger log = LoggerFactory.getLogger(TransaccionalVista.class);

	@ManagedProperty(value = "#{delegadoDeNegocio}")
	private IDelegadoDeNegocio delegadoDeNegocio;

	private List<SelectItem> vLstOpUsuarios;
	private List<SelectItem> vLstOpCuenta;
	private List<TransaccionesInfo> vLstInfoTransacciones;
	
	private InputText txtCliente;
	private InputNumber txtValor;

	private SelectOneMenu somUsuario;
	private SelectOneMenu somCuenta;

	private CommandButton btnConsignar;
	private CommandButton btnRetirar;
	private BigDecimal bDValorCuenta;

	
	public List<TransaccionesInfo> getvLstInfoTransacciones() {
		return vLstInfoTransacciones;
	}

	public void setvLstInfoTransacciones(List<TransaccionesInfo> vLstInfoTransacciones) {
		this.vLstInfoTransacciones = vLstInfoTransacciones;
	}

	public BigDecimal getbDValorCuenta() {
		return bDValorCuenta;
	}

	public void setbDValorCuenta(BigDecimal bDValorCuenta) {
		this.bDValorCuenta = bDValorCuenta;
	}

	public List<SelectItem> getvLstOpCuenta() {
		return vLstOpCuenta;
	}

	public void setvLstOpCuenta(List<SelectItem> vLstOpCuenta) {
		this.vLstOpCuenta = vLstOpCuenta;
	}

	public SelectOneMenu getSomCuenta() {
		return somCuenta;
	}

	public void setSomCuenta(SelectOneMenu somCuenta) {
		this.somCuenta = somCuenta;
	}

	private CommandButton btnCancelar;

	public InputText getTxtCliente() {
		return txtCliente;
	}

	public void setTxtCliente(InputText txtCliente) {
		this.txtCliente = txtCliente;
	}

	public InputNumber getTxtValor() {
		return txtValor;
	}

	public void setTxtValor(InputNumber txtValor) {
		this.txtValor = txtValor;
	}

	public SelectOneMenu getSomUsuario() {
		return somUsuario;
	}

	public void setSomUsuario(SelectOneMenu somUsuario) {
		this.somUsuario = somUsuario;
	}

	public CommandButton getBtnConsignar() {
		return btnConsignar;
	}

	public void setBtnConsignar(CommandButton btnConsignar) {
		this.btnConsignar = btnConsignar;
	}

	public CommandButton getBtnRetirar() {
		return btnRetirar;
	}

	public void setBtnRetirar(CommandButton btnRetirar) {
		this.btnRetirar = btnRetirar;
	}

	public CommandButton getBtnCancelar() {
		return btnCancelar;
	}

	public void setBtnCancelar(CommandButton btnCancelar) {
		this.btnCancelar = btnCancelar;
	}

	public void setvLstOpUsuarios(List<SelectItem> vLstOpUsuarios) {
		this.vLstOpUsuarios = vLstOpUsuarios;
	}

	public IDelegadoDeNegocio getDelegadoDeNegocio() {
		return delegadoDeNegocio;
	}

	public void setDelegadoDeNegocio(IDelegadoDeNegocio delegadoDeNegocio) {
		this.delegadoDeNegocio = delegadoDeNegocio;
	}

	public void somCuentaListaner() {

		if (somCuenta.getValue().toString().equals("-1")) {
			deshabilitarBotones(true);
			vLstInfoTransacciones = null;
			bDValorCuenta = new BigDecimal(0);
		} else {
			deshabilitarBotones(false);
			try {
				bDValorCuenta = delegadoDeNegocio.consultarCuentaPorNumero(somCuenta.getValue().toString()).getCueSaldo();
				LeerTransaccionesCuenta(somCuenta.getValue().toString());
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}
	}

	public void txtClienteListener() {
		Clientes vObjCliente = null;
		try {
			vObjCliente = delegadoDeNegocio
					.consultarClinetePorId(Long.parseLong(txtCliente.getValue().toString().trim()));

		} catch (Exception e) {

		}
		if (vObjCliente == null) {
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "El Cliente existe", ""));
			txtCliente.resetValue();
			vLstOpCuenta = null;
			limpiarDatosForm();
		} else {
			leerCuentasUsuario();

		}
	}

	private void limpiarDatosForm() {
		somCuenta.resetValue();
		txtCliente.resetValue();
		txtValor.resetValue();
		somUsuario.setValue("-1");
		btnConsignar.setDisabled(true);
		btnRetirar.setDisabled(true);
		vLstInfoTransacciones= null;
		bDValorCuenta = new BigDecimal(0);
	}

	private void deshabilitarBotones(boolean pvBlnActivar) {
		btnConsignar.setDisabled(pvBlnActivar);
		btnRetirar.setDisabled(pvBlnActivar);
	}

	public String consignarAction() {
		log.info("Ingreso a consignar");
		try {

			Consignaciones vConsignacion = new Consignaciones();

			vConsignacion.setConDescripcion("Consignacion DFO");
			vConsignacion.setConFecha(new Date());
			vConsignacion.setConValor(new BigDecimal(txtValor.getValue().toString().trim()));

			Cuentas vCuentaCons = new Cuentas();
			vCuentaCons.setCueNumero(somCuenta.getValue().toString().trim());
			vConsignacion.setCuentas(vCuentaCons);

			Usuarios vUsurioCons = new Usuarios();
			vUsurioCons.setUsuCedula(Long.parseLong(somUsuario.getValue().toString()));
			vConsignacion.setUsuarios(vUsurioCons);

			Long vLngConsecConsignacion = (long) delegadoDeNegocio.consultarTodosConsignaciones().size();
			ConsignacionesId vConsigID = new ConsignacionesId(vLngConsecConsignacion + 1, vCuentaCons.getCueNumero());
			vConsignacion.setId(vConsigID);

			delegadoDeNegocio.realizarConsignacion(vConsignacion);
			limpiarDatosForm();
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Consignacion realizada con exito", ""));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}
		return "";
	}

	public String retirarAction() {
		log.info("Ingreso a retirar");
		try {

			Retiros vRetiro = new Retiros();

			vRetiro.setRetDescripcion("RETIRO DFO TEST");
			vRetiro.setRetFecha(new Date());
			vRetiro.setRetValor(new BigDecimal(txtValor.getValue().toString().trim()));

			Cuentas vCuentaCons = new Cuentas();
			vCuentaCons.setCueNumero(somCuenta.getValue().toString().trim());
			vRetiro.setCuentas(vCuentaCons);

			Usuarios vUsurioCons = new Usuarios();
			vUsurioCons.setUsuCedula(Long.parseLong(somUsuario.getValue().toString()));
			vRetiro.setUsuarios(vUsurioCons);

			Long vLngConsecRetiro = (long) delegadoDeNegocio.consultarTodosRetiros().size();
			RetirosId vRetiroID = new RetirosId(vLngConsecRetiro + 1, vCuentaCons.getCueNumero());
			vRetiro.setId(vRetiroID);

			delegadoDeNegocio.realizarRetiro(vRetiro);
			limpiarDatosForm();
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Retiro realizado con exito", ""));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}
		return "";
	}

	public String cancelarAction() {
		log.info("Ingreso a cancelar");
		limpiarDatosForm();
		return "";
	}

	public List<SelectItem> getvLstOpUsuarios() {
		if (vLstOpUsuarios == null) {
			try {
				vLstOpUsuarios = new ArrayList<SelectItem>();
				List<Usuarios> vLstUsuarios = delegadoDeNegocio.consultarTodosUsuarios();
				for (Usuarios usuario : vLstUsuarios) {
					vLstOpUsuarios.add(new SelectItem(usuario.getUsuCedula(), usuario.getUsuNombre()));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return vLstOpUsuarios;
	}

	private void leerCuentasUsuario() {
		try {
			vLstOpCuenta = new ArrayList<SelectItem>();
			List<Cuentas> vLstCuentas = delegadoDeNegocio.consultarTodosCuentas();
			long vLngIdCliente = Long.parseLong(txtCliente.getValue().toString().trim());
			for (Cuentas cuenta : vLstCuentas) {
				if (cuenta.getClientes().getCliId() == vLngIdCliente) {
					vLstOpCuenta.add(new SelectItem(cuenta.getCueNumero(), cuenta.getCueNumero()));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void LeerTransaccionesCuenta(String pvStrIdCuenta){
		Cuentas vObjCUenta;
		Set<Consignaciones> vLstConsignaciones;
		Set<Retiros> vLstRetiros;
		
		try {
			vObjCUenta = delegadoDeNegocio.consultarCuentaPorNumero(pvStrIdCuenta);
			vLstConsignaciones = vObjCUenta.getConsignacioneses();
			vLstRetiros = vObjCUenta.getRetiroses();
			vLstInfoTransacciones = new ArrayList<TransaccionesInfo>();
			
			for (Consignaciones vConsignacion : vLstConsignaciones) {
				vLstInfoTransacciones.add(new TransaccionesInfo("Consignación", vConsignacion.getConValor().longValue(),vConsignacion.getConFecha()));
			}
			
			for (Retiros vRetiro : vLstRetiros) {
				vLstInfoTransacciones.add(new TransaccionesInfo("Retiro", vRetiro.getRetValor().longValue(),vRetiro.getRetFecha()));
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
