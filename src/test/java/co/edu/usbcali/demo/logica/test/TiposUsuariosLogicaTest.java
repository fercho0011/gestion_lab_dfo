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

import co.edu.usbcali.demo.logica.ITiposUsuariosLogica;
import co.edu.usbcali.demo.modelo.TiposUsuarios;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class TiposUsuariosLogicaTest {
	
	private static final Logger log=LoggerFactory.getLogger(TiposUsuariosLogicaTest.class);
	
	@Autowired
	private ITiposUsuariosLogica tiposUsuariosLogic;
	
	
	private Long vLngTipoUsId = 99L;
	
	
	@Test
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void atest() throws Exception {
		
		TiposUsuarios tiposUsuarios = new TiposUsuarios();
		tiposUsuarios.setTusuCodigo(vLngTipoUsId);
		tiposUsuarios.setTusuNombre("Otro Usuario");
		
		
		tiposUsuariosLogic.grabar(tiposUsuarios);
		
	}
	
	@Test
	@Transactional(readOnly=true)
	public void btest() throws Exception {
		TiposUsuarios tiposUsuarios = tiposUsuariosLogic.consultarTiposUsuariosPorID(vLngTipoUsId);
		assertNotNull("Tipos de usuario no catalogado", tiposUsuarios);
		
		log.info(tiposUsuarios.getTusuNombre());
	}

	@Test
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void ctest() throws Exception {
		TiposUsuarios tiposUsuarios = tiposUsuariosLogic.consultarTiposUsuariosPorID(vLngTipoUsId);
		assertNotNull("Tipos de usuario no catalogado", tiposUsuarios);
		tiposUsuarios.setTusuNombre("INVITADO");
		tiposUsuariosLogic.modificar(tiposUsuarios);
	}
	
	@Test
	@Transactional(readOnly=true)
	public void etest() throws Exception {
		List<TiposUsuarios> vLstTiposUsr=tiposUsuariosLogic.consultarTodos();
		for (TiposUsuarios tiposUsuarios : vLstTiposUsr) {
			log.info(tiposUsuarios.getTusuNombre());
		}
	}

	@Test
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void dtest() throws Exception {
		TiposUsuarios tiposUsuarios = tiposUsuariosLogic.consultarTiposUsuariosPorID(vLngTipoUsId);
		assertNotNull("Tipos de usuario no catalogado", tiposUsuarios);
		tiposUsuariosLogic.borrar(tiposUsuarios);
	}
	
}
