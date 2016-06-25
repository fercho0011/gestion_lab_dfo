package co.edu.usbcali.demo.logica.test;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Date;
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

import co.edu.usbcali.demo.dao.ICuentasDAO;
import co.edu.usbcali.demo.dao.IUsuariosDAO;
import co.edu.usbcali.demo.logica.IRetirosLogica;
import co.edu.usbcali.demo.modelo.Cuentas;
import co.edu.usbcali.demo.modelo.Retiros;
import co.edu.usbcali.demo.modelo.RetirosId;
import co.edu.usbcali.demo.modelo.Usuarios;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class RetirosLogicaTest {
	
	private static final Logger log=LoggerFactory.getLogger(RetirosLogicaTest.class);
	
	@Autowired
	private IRetirosLogica retirosLogic;
	
	@Autowired
	private IUsuariosDAO usuariosDAO;

	@Autowired
	private ICuentasDAO cuentasDAO;
	
	private RetirosId rID = new RetirosId(103L,"4008-5305-0030");
	
	
	@Test
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void atest() throws Exception {
		
		Retiros vRetiro = new Retiros();
		vRetiro.setId(rID);
		vRetiro.setRetDescripcion("Movimiento Caja");
		vRetiro.setRetValor(new BigDecimal(102300));
		vRetiro.setRetFecha(new Date());
		
		
		Cuentas cuentas=cuentasDAO.consultarCuentaPorNumero("4008-5305-0030");
		vRetiro.setCuentas(cuentas);
		
		Usuarios usuario=usuariosDAO.consultarUsuariosPorCedula(15L);
		vRetiro.setUsuarios(usuario);
		
		retirosLogic.grabar(vRetiro);
		
	}
	
	@Test
	@Transactional(readOnly=true)
	public void btest() throws Exception {
		Retiros vRetiro = retirosLogic.consultarRetirosPorId(rID);
		assertNotNull("El Retiro no existe", vRetiro);
		
		log.info(vRetiro.getRetDescripcion());
	}

	@Test
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void ctest() throws Exception {
		Retiros vRetiro = retirosLogic.consultarRetirosPorId(rID);
		assertNotNull("El Retiro no existe", vRetiro);
		vRetiro.setRetDescripcion("Cambio de descripcion");
		retirosLogic.modificar(vRetiro);
	}
	
	@Test
	@Transactional(readOnly=true)
	public void etest() throws Exception {
		List<Retiros> vLstRetiro=retirosLogic.consultarTodos();
		for (Retiros retiro : vLstRetiro) {
			log.info(retiro.getRetDescripcion()+" "+retiro.getRetValor());
		}
	}
	
	@Test
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void dtest() throws Exception {
		Retiros vRetiro = retirosLogic.consultarRetirosPorId(rID);
		assertNotNull("El Retiro no existe", vRetiro);
		retirosLogic.borrar(vRetiro);
	}

}
