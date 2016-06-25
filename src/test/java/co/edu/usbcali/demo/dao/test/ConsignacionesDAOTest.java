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

import co.edu.usbcali.demo.dao.IConsignacionesDAO;
import co.edu.usbcali.demo.dao.ICuentasDAO;
import co.edu.usbcali.demo.dao.IUsuariosDAO;
import co.edu.usbcali.demo.modelo.Consignaciones;
import co.edu.usbcali.demo.modelo.ConsignacionesId;
import co.edu.usbcali.demo.modelo.Cuentas;
import co.edu.usbcali.demo.modelo.Usuarios;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class ConsignacionesDAOTest {
	
	private static final Logger log=LoggerFactory.getLogger(ConsignacionesDAOTest.class);
	
	@Autowired
	private IConsignacionesDAO consignacionesDAO;
	
	@Autowired
	private IUsuariosDAO usuariosDAO;

	@Autowired
	private ICuentasDAO cuentasDAO;
	
	private ConsignacionesId cID = new ConsignacionesId(103L,"4008-5305-0030");
	
	
	
	@Test
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void atest() {
		
		Consignaciones vConsignacion = new Consignaciones();
		vConsignacion.setId(cID);
		vConsignacion.setConDescripcion("Movimiento Caja");
		vConsignacion.setConValor(new BigDecimal(99800));
		vConsignacion.setConFecha(new Date());
		
		
		Cuentas cuentas=cuentasDAO.consultarCuentaPorNumero("4008-5305-0030");
		vConsignacion.setCuentas(cuentas);
		
		Usuarios usuario=usuariosDAO.consultarUsuariosPorCedula(15L);
		vConsignacion.setUsuarios(usuario);
		
		consignacionesDAO.grabar(vConsignacion);
		
	}
	
	@Test
	@Transactional(readOnly=true)
	public void btest() {
		Consignaciones vConsignacion = consignacionesDAO.consultarConsignacionPorId(cID);
		assertNotNull("La consignacion no existe", vConsignacion);
		
		log.info(vConsignacion.getConDescripcion());
	}

	@Test
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void ctest() {
		Consignaciones vConsignacion = consignacionesDAO.consultarConsignacionPorId(cID);
		assertNotNull("La consignacion no existe", vConsignacion);
		vConsignacion.setConDescripcion("Cambio de descripcion CONSIG");
		consignacionesDAO.modificar(vConsignacion);
	}

	@Test
	@Transactional(readOnly=true)
	public void etest() {
		List<Consignaciones> vLstConsignaciones=consignacionesDAO.consultarTodos();
		for (Consignaciones consignacion : vLstConsignaciones) {
			log.info(consignacion.getConDescripcion()+" "+consignacion.getConValor());
		}
	}
	
	@Test
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void dtest() {
		Consignaciones vConsignacion = consignacionesDAO.consultarConsignacionPorId(cID);
		assertNotNull("La consignacion no existe", vConsignacion);
		consignacionesDAO.borrar(vConsignacion);
	}

	
	

}
