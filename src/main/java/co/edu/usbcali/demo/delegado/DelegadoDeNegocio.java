package co.edu.usbcali.demo.delegado;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import co.edu.usbcali.demo.logica.IClienteLogica;
import co.edu.usbcali.demo.logica.IConsignacionesLogica;
import co.edu.usbcali.demo.logica.ICuentasLogica;
import co.edu.usbcali.demo.logica.IRetirosLogica;
import co.edu.usbcali.demo.logica.ITiposDocumentosLogica;
import co.edu.usbcali.demo.logica.ITiposUsuariosLogica;
import co.edu.usbcali.demo.logica.ITransaccionalLogica;
import co.edu.usbcali.demo.logica.IUsuariosLogica;
import co.edu.usbcali.demo.modelo.Clientes;
import co.edu.usbcali.demo.modelo.Consignaciones;
import co.edu.usbcali.demo.modelo.Cuentas;
import co.edu.usbcali.demo.modelo.Retiros;
import co.edu.usbcali.demo.modelo.TiposDocumentos;
import co.edu.usbcali.demo.modelo.TiposUsuarios;
import co.edu.usbcali.demo.modelo.Usuarios;

@Scope("singleton")
@Component("delegadoDeNegocio")
public class DelegadoDeNegocio implements IDelegadoDeNegocio {

	@Autowired
	IClienteLogica clienteLogica;
	@Autowired
	ITiposDocumentosLogica tiposDocumentosLogica;
	@Autowired
	IUsuariosLogica usuariosLogica;
	@Autowired
	ITiposUsuariosLogica tiposUsuariosLogica;
	@Autowired
	ICuentasLogica cuentasLogica;
	@Autowired
	ITransaccionalLogica transaccionalLogica;
	@Autowired
	IConsignacionesLogica  consignacionesLogica;
	@Autowired
	IRetirosLogica retirosLogica;
	
	@Override
	public void grabarCliente(Clientes clientes) throws Exception {
		clienteLogica.grabar(clientes);

	}

	@Override
	public void modificarCliente(Clientes clientes) throws Exception {
		clienteLogica.modificar(clientes);

	}

	@Override
	public void borrarCliente(Clientes clientes) throws Exception {
		clienteLogica.borrar(clientes);

	}

	@Override
	public Clientes consultarClinetePorId(long cliId) throws Exception {

		return clienteLogica.consultarClinetePorId(cliId);
	}

	@Override
	public List<Clientes> consultarTodosCliente() throws Exception {
		return clienteLogica.consultarTodos();
	}

	@Override
	public void grabarTiposDocumentos(TiposDocumentos tiposDocumentos) throws Exception {
		tiposDocumentosLogica.grabar(tiposDocumentos);

	}

	@Override
	public void modificarTiposDocumentos(TiposDocumentos tiposDocumentos) throws Exception {
		tiposDocumentosLogica.modificar(tiposDocumentos);

	}

	@Override
	public void borrarTiposDocumentos(TiposDocumentos tiposDocumentos) throws Exception {
		tiposDocumentosLogica.borrar(tiposDocumentos);

	}

	@Override
	public TiposDocumentos consultarTiposDocumentosPorID(long tdocCodigo) throws Exception {

		return tiposDocumentosLogica.consultarTiposDocumentosPorID(tdocCodigo);
	}

	@Override
	public List<TiposDocumentos> consultarTodosTiposDocumentos() throws Exception {
		return tiposDocumentosLogica.consultarTodos();
	}

	@Override
	public void grabarUsuarios(Usuarios usuarios) throws Exception {
		usuariosLogica.grabar(usuarios);

	}

	@Override
	public void modificarUsuarios(Usuarios usuarios) throws Exception {
		usuariosLogica.modificar(usuarios);

	}

	@Override
	public void borrarUsuarios(Usuarios usuarios) throws Exception {
		usuariosLogica.borrar(usuarios);

	}

	@Override
	public Usuarios consultarUsuariosPorCedula(long usuCedula) throws Exception {
		return usuariosLogica.consultarUsuariosPorCedula(usuCedula);

	}

	@Override
	public List<Usuarios> consultarTodosUsuarios() throws Exception {
		return usuariosLogica.consultarTodos();
	}

	@Override
	public void grabarTiposUsuarios(TiposUsuarios tiposUsuarios) throws Exception {
		tiposUsuariosLogica.grabar(tiposUsuarios);

	}

	@Override
	public void modificarTiposUsuarios(TiposUsuarios tiposUsuarios) throws Exception {
		tiposUsuariosLogica.modificar(tiposUsuarios);

	}

	@Override
	public void borrarTiposUsuarios(TiposUsuarios tiposUsuarios) throws Exception {
		tiposUsuariosLogica.borrar(tiposUsuarios);

	}

	@Override
	public TiposUsuarios consultarTiposUsuariosPorID(long tusuCodigo) throws Exception {
		return tiposUsuariosLogica.consultarTiposUsuariosPorID(tusuCodigo);
	}

	@Override
	public List<TiposUsuarios> consultarTodosTiposUsuarios() throws Exception {
		return tiposUsuariosLogica.consultarTodos();
	}

	@Override
	public void realizarConsignacion(Consignaciones consignaciones) throws Exception {
		transaccionalLogica.realizarConsignacion(consignaciones);
		
	}

	@Override
	public void realizarRetiro(Retiros retiros) throws Exception {
		transaccionalLogica.realizarRetiro(retiros);
		
	}

	@Override
	public Cuentas consultarCuentaPorNumero(String cueNumero) throws Exception {
		return cuentasLogica.consultarCuentaPorNumero(cueNumero);
	}

	@Override
	public List<Cuentas> consultarTodosCuentas() throws Exception {
		return cuentasLogica.consultarTodos();
	}

	@Override
	public List<Consignaciones> consultarTodosConsignaciones() throws Exception {
		return consignacionesLogica.consultarTodos();
	}

	@Override
	public List<Retiros> consultarTodosRetiros() throws Exception {
		return retirosLogica.consultarTodos();
	}

}
