package co.edu.usbcali.demo.dao;


import java.util.List;

import co.edu.usbcali.demo.modelo.Usuarios;;

public interface IUsuariosDAO {
	
	public void grabar(Usuarios usuarios);
	public void modificar(Usuarios usuarios);
	public void borrar(Usuarios usuarios);
	public Usuarios consultarUsuariosPorCedula(long usuCedula);
	public List<Usuarios> consultarTodos();
	

}
