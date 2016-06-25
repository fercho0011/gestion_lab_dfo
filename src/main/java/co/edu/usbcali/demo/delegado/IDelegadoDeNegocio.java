package co.edu.usbcali.demo.delegado;

import java.util.List;

import co.edu.usbcali.demo.modelo.Clientes;
import co.edu.usbcali.demo.modelo.Consignaciones;
import co.edu.usbcali.demo.modelo.Cuentas;
import co.edu.usbcali.demo.modelo.Retiros;
import co.edu.usbcali.demo.modelo.TiposDocumentos;
import co.edu.usbcali.demo.modelo.TiposUsuarios;
import co.edu.usbcali.demo.modelo.Usuarios;

public interface IDelegadoDeNegocio {


	public void grabarCliente(Clientes clientes)throws Exception;
	public void modificarCliente(Clientes clientes)throws Exception;
	public void borrarCliente(Clientes clientes)throws Exception;
	public Clientes consultarClinetePorId(long cliId)throws Exception;
	public List<Clientes> consultarTodosCliente()throws Exception;
	
	public void grabarTiposDocumentos(TiposDocumentos tiposDocumentos)throws Exception;;
	public void modificarTiposDocumentos(TiposDocumentos tiposDocumentos)throws Exception;;
	public void borrarTiposDocumentos(TiposDocumentos tiposDocumentos)throws Exception;;
	public TiposDocumentos consultarTiposDocumentosPorID(long tdocCodigo)throws Exception;;
	public List<TiposDocumentos> consultarTodosTiposDocumentos()throws Exception;;
	
	public void grabarUsuarios(Usuarios usuarios)throws Exception;
	public void modificarUsuarios(Usuarios usuarios)throws Exception;
	public void borrarUsuarios(Usuarios usuarios)throws Exception;
	public Usuarios consultarUsuariosPorCedula(long usuCedula)throws Exception;
	public List<Usuarios> consultarTodosUsuarios()throws Exception;
	
	
	public void grabarTiposUsuarios(TiposUsuarios tiposUsuarios)throws Exception;
	public void modificarTiposUsuarios(TiposUsuarios tiposUsuarios)throws Exception;
	public void borrarTiposUsuarios(TiposUsuarios tiposUsuarios)throws Exception;
	public TiposUsuarios consultarTiposUsuariosPorID(long tusuCodigo)throws Exception;
	public List<TiposUsuarios> consultarTodosTiposUsuarios()throws Exception;
	
	public void realizarConsignacion(Consignaciones consignaciones)throws Exception;
	public void realizarRetiro(Retiros retiros)throws Exception;
	
	public Cuentas consultarCuentaPorNumero(String cueNumero)throws Exception;
	public List<Cuentas> consultarTodosCuentas()throws Exception;
	
	public List<Consignaciones> consultarTodosConsignaciones()throws Exception;
	public List<Retiros> consultarTodosRetiros()throws Exception;;
	
}
