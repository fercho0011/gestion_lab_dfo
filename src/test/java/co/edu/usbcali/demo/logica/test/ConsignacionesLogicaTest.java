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
import co.edu.usbcali.demo.logica.IConsignacionesLogica;
import co.edu.usbcali.demo.modelo.Consignaciones;
import co.edu.usbcali.demo.modelo.ConsignacionesId;
import co.edu.usbcali.demo.modelo.Cuentas;
import co.edu.usbcali.demo.modelo.Usuarios;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class ConsignacionesLogicaTest {
	
	private static final Logger log=LoggerFactory.getLogger(ConsignacionesLogicaTest.class);
	
	@Autowired
	private IConsignacionesLogica consignacionesLogic;
	
	@Autowired
	private IUsuariosDAO usuariosDAO;

	@Autowired
	private ICuentasDAO cuentasDAO;
	
	private ConsignacionesId cID = new ConsignacionesId(103L,"4008-5305-0030");
	
	
	@Test
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void atest() throws Exception {
		
		Consignaciones vConsignacion = new Consignaciones();
		vConsignacion.setId(cID);
		vConsignacion.setConDescripcion("Movimiento Caja");
		vConsignacion.setConValor(new BigDecimal(99800));
		vConsignacion.setConFecha(new Date());
		
		
		Cuentas cuentas=cuentasDAO.consultarCuentaPorNumero("4008-5305-0030");
		vConsignacion.setCuentas(cuentas);
		
		Usuarios usuario=usuariosDAO.consultarUsuariosPorCedula(15L);
		vConsignacion.setUsuarios(usuario);
		
		consignacionesLogic.grabar(vConsignacion);
		
	}
	
	@Test
	@Transactional(readOnly=true)
	public void btest() throws Exception {
		Consignaciones vConsignacion = consignacionesLogic.consultarConsignacionPorId(cID);
		assertNotNull("La consignacion no existe", vConsignacion);
		
		log.info(vConsignacion.getConDescripcion());
	}

	@Test
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void ctest() throws Exception {
		Consignaciones vConsignacion = consignacionesLogic.consultarConsignacionPorId(cID);
		assertNotNull("La consignacion no existe", vConsignacion);
		vConsignacion.setConDescripcion("Cambio de descripcion CONSIG");
		consignacionesLogic.modificar(vConsignacion);
	}
	
	@Test
	@Transactional(readOnly=true)
	public void etest() throws Exception {
		List<Consignaciones> vLstConsignaciones=consignacionesLogic.consultarTodos();
		for (Consignaciones consignacion : vLstConsignaciones) {
			log.info(consignacion.getConDescripcion()+" "+consignacion.getConValor());
		}
	}
	
	@Test
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void dtest() throws Exception {
		Consignaciones vConsignacion = consignacionesLogic.consultarConsignacionPorId(cID);
		assertNotNull("La consignacion no existe", vConsignacion);
		consignacionesLogic.borrar(vConsignacion);
	}

}
