package co.edu.usbcali.demo.dao.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.demo.dao.ITiposUsuariosDAO;
import co.edu.usbcali.demo.dao.IUsuariosDAO;
import co.edu.usbcali.demo.modelo.TiposUsuarios;
import co.edu.usbcali.demo.modelo.Usuarios;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class UsuariosDAOTest {
	
	private static final Logger log=LoggerFactory.getLogger(UsuariosDAOTest.class);
	
	@Autowired
	private IUsuariosDAO usuariosDAO;
	
	@Autowired
	private ITiposUsuariosDAO tiposUsuariosDAO;
	
	private Long usuCedula = 1130626646L;
	
	
	@Test
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void atest() {
		
		Usuarios usuarios = new Usuarios();
		usuarios.setUsuCedula(usuCedula);
		usuarios.setUsuLogin("Diferote");
		usuarios.setUsuClave("MiClave123");
		usuarios.setUsuNombre("Diego Otero");
		TiposUsuarios tiposUsuarios=tiposUsuariosDAO.consultarTiposUsuariosPorID(20L);
		
		usuarios.setTiposUsuarios(tiposUsuarios);
		
		usuariosDAO.grabar(usuarios);
		
				
	}
	
	@Test
	@Transactional(readOnly=true)
	public void btest() {
		Usuarios vUsuario = usuariosDAO.consultarUsuariosPorCedula(usuCedula);
		assertNotNull("El cliente no existe", vUsuario);
		log.info(vUsuario.getUsuNombre());

	}

	@Test
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void ctest() {
		Usuarios vUsuario = usuariosDAO.consultarUsuariosPorCedula(usuCedula);
		assertNotNull("El cliente no existe", vUsuario);
		vUsuario.setUsuNombre("DIEGO F OTERO MODIFICADO");
		usuariosDAO.modificar(vUsuario);
	}
	
	
	@Test
	@Transactional(readOnly=true)
	public void etest() {
		List<Usuarios> vLstUsuarios=usuariosDAO.consultarTodos();
		for (Usuarios usuario : vLstUsuarios) {
			log.info(usuario.getUsuNombre());
		}
	}
	
	@Test
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void dtest() {
		Usuarios vUsuario = usuariosDAO.consultarUsuariosPorCedula(usuCedula);
		assertNotNull("El cliente no existe", vUsuario);
		usuariosDAO.borrar(vUsuario);
	}


}
