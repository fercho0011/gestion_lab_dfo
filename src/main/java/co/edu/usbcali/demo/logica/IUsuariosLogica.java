package co.edu.usbcali.demo.logica;


import java.util.List;

import co.edu.usbcali.demo.modelo.Usuarios;;

public interface IUsuariosLogica {
	
	public void grabar(Usuarios usuarios)throws Exception;
	public void modificar(Usuarios usuarios)throws Exception;
	public void borrar(Usuarios usuarios)throws Exception;
	public Usuarios consultarUsuariosPorCedula(long usuCedula)throws Exception;
	public List<Usuarios> consultarTodos()throws Exception;
	

}
