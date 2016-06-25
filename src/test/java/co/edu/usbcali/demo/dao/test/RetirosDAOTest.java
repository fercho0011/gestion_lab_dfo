package co.edu.usbcali.demo.dao.test;

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
import co.edu.usbcali.demo.dao.IRetirosDAO;
import co.edu.usbcali.demo.dao.IUsuariosDAO;
import co.edu.usbcali.demo.modelo.Cuentas;
import co.edu.usbcali.demo.modelo.Retiros;
import co.edu.usbcali.demo.modelo.RetirosId;
import co.edu.usbcali.demo.modelo.Usuarios;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class RetirosDAOTest {
	
	private static final Logger log=LoggerFactory.getLogger(RetirosDAOTest.class);
	
	@Autowired
	private IRetirosDAO retirosDAO;
	
	@Autowired
	private IUsuariosDAO usuariosDAO;

	@Autowired
	private ICuentasDAO cuentasDAO;
	
	private RetirosId rID = new RetirosId(103L,"4008-5305-0030");
	
	
	@Test
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void atest() {
		
		Retiros vRetiro = new Retiros();
		vRetiro.setId(rID);
		vRetiro.setRetDescripcion("Movimiento Caja");
		vRetiro.setRetValor(new BigDecimal(102300));
		vRetiro.setRetFecha(new Date());
		
		
		Cuentas cuentas=cuentasDAO.consultarCuentaPorNumero("4008-5305-0030");
		vRetiro.setCuentas(cuentas);
		
		Usuarios usuario=usuariosDAO.consultarUsuariosPorCedula(15L);
		vRetiro.setUsuarios(usuario);
		
		retirosDAO.grabar(vRetiro);
		
	}
	
	@Test
	@Transactional(readOnly=true)
	public void btest() {
		Retiros vRetiro = retirosDAO.consultarRetirosPorId(rID);
		assertNotNull("El Retiro no existe", vRetiro);
		
		log.info(vRetiro.getRetDescripcion());
	}

	@Test
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void ctest() {
		Retiros vRetiro = retirosDAO.consultarRetirosPorId(rID);
		assertNotNull("El Retiro no existe", vRetiro);
		vRetiro.setRetDescripcion("Cambio de descripcion");
		retirosDAO.modificar(vRetiro);
	}

	@Test
	@Transactional(readOnly=true)
	public void etest() {
		List<Retiros> vLstRetiro=retirosDAO.consultarTodos();
		for (Retiros retiro : vLstRetiro) {
			log.info(retiro.getRetDescripcion()+" "+retiro.getRetValor());
		}
	}
	
	@Test
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void dtest() {
		Retiros vRetiro = retirosDAO.consultarRetirosPorId(rID);
		assertNotNull("El Retiro no existe", vRetiro);
		retirosDAO.borrar(vRetiro);
	}

}
