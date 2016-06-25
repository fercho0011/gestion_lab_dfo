package co.edu.usbcali.demo.logica.test;

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
import co.edu.usbcali.demo.logica.IUsuariosLogica;
import co.edu.usbcali.demo.modelo.TiposUsuarios;
import co.edu.usbcali.demo.modelo.Usuarios;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class UsuariosLogicaTest {
	
	private static final Logger log=LoggerFactory.getLogger(UsuariosLogicaTest.class);
	
	@Autowired
	private IUsuariosLogica usuarioLogica;
	
	@Autowired
	private ITiposUsuariosDAO tiposUsuariosDAO;
	
	private Long usuCedula = 1130626646L;
	
	
	@Test
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void atest() throws Exception {
		
		Usuarios usuarios = new Usuarios();
		usuarios.setUsuCedula(usuCedula);
		usuarios.setUsuLogin("Diferote");
		usuarios.setUsuClave("MiClave123");
		usuarios.setUsuNombre("Diego Otero");
		TiposUsuarios tiposUsuarios=tiposUsuariosDAO.consultarTiposUsuariosPorID(20L);
		
		usuarios.setTiposUsuarios(tiposUsuarios);
		
		usuarioLogica.grabar(usuarios);
		
	}
	
	@Test
	@Transactional(readOnly=true)
	public void btest() throws Exception {
		Usuarios vUsuario = usuarioLogica.consultarUsuariosPorCedula(usuCedula);
		assertNotNull("El cliente no existe", vUsuario);
		
		log.info(vUsuario.getUsuNombre());
	}

	@Test
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void ctest() throws Exception {
		Usuarios vUsuario = usuarioLogica.consultarUsuariosPorCedula(usuCedula);
		assertNotNull("El cliente no existe", vUsuario);
		vUsuario.setUsuNombre("DIEGO F OTERO MODIFICADO");
		usuarioLogica.modificar(vUsuario);
	}
	
	@Test
	@Transactional(readOnly=true)
	public void etest() throws Exception {
		List<Usuarios> vLstUsuarios=usuarioLogica.consultarTodos();
		for (Usuarios usuario : vLstUsuarios) {
			log.info(usuario.getUsuNombre());
		}
	}
	
	@Test
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void dtest() throws Exception {
		Usuarios vUsuario = usuarioLogica.consultarUsuariosPorCedula(usuCedula);
		assertNotNull("El cliente no existe", vUsuario);
		usuarioLogica.borrar(vUsuario);
	}

}
