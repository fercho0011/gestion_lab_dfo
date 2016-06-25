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
import co.edu.usbcali.demo.modelo.TiposUsuarios;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class TiposUsuariosDAOTest {
	
	private static final Logger log=LoggerFactory.getLogger(TiposUsuariosDAOTest.class);
	
	@Autowired
	private ITiposUsuariosDAO tiposUsuariosDAO;
	
	private Long vLngTipoUsId = 99L;
	
	
	@Test
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void atest() {
		
		TiposUsuarios tiposUsuarios = new TiposUsuarios();
		tiposUsuarios.setTusuCodigo(vLngTipoUsId);
		tiposUsuarios.setTusuNombre("Otro Usuario");
		
		
		tiposUsuariosDAO.grabar(tiposUsuarios);
		
	}
	
	@Test
	@Transactional(readOnly=true)
	public void btest() {
		TiposUsuarios tiposUsuarios = tiposUsuariosDAO.consultarTiposUsuariosPorID(vLngTipoUsId);
		assertNotNull("Tipos de usuario no catalogado", tiposUsuarios);
		
		log.info(tiposUsuarios.getTusuNombre());
	}

	@Test
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void ctest() {
		TiposUsuarios tiposUsuarios = tiposUsuariosDAO.consultarTiposUsuariosPorID(vLngTipoUsId);
		assertNotNull("Tipos de usuario no catalogado", tiposUsuarios);
		tiposUsuarios.setTusuNombre("INVITADO");
		tiposUsuariosDAO.modificar(tiposUsuarios);
	}

		
	@Test
	@Transactional(readOnly=true)
	public void etest() {
		List<TiposUsuarios> vLstTiposUsr=tiposUsuariosDAO.consultarTodos();
		for (TiposUsuarios tiposUsuarios : vLstTiposUsr) {
			log.info(tiposUsuarios.getTusuNombre());
		}
	}
	
	@Test
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void dtest() {
		TiposUsuarios tiposUsuarios = tiposUsuariosDAO.consultarTiposUsuariosPorID(vLngTipoUsId);
		assertNotNull("Tipos de usuario no catalogado", tiposUsuarios);
		tiposUsuariosDAO.borrar(tiposUsuarios);
	}

	

}
