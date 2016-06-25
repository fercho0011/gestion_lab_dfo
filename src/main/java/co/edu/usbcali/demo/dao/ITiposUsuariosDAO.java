package co.edu.usbcali.demo.dao;

import java.util.List;

import co.edu.usbcali.demo.modelo.TiposUsuarios;;

public interface ITiposUsuariosDAO {
	
	public void grabar(TiposUsuarios tiposUsuarios);
	public void modificar(TiposUsuarios tiposUsuarios);
	public void borrar(TiposUsuarios tiposUsuarios);
	public TiposUsuarios consultarTiposUsuariosPorID(long tusuCodigo);
	public List<TiposUsuarios> consultarTodos();
	

}
