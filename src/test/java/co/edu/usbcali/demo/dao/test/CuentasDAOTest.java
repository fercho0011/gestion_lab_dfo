package co.edu.usbcali.demo.dao.test;

import static org.junit.Assert.*;

import java.math.BigDecimal;
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

import co.edu.usbcali.demo.dao.IClienteDAO;
import co.edu.usbcali.demo.dao.ICuentasDAO;
import co.edu.usbcali.demo.modelo.Clientes;
import co.edu.usbcali.demo.modelo.Cuentas;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class CuentasDAOTest {
	
	private static final Logger log=LoggerFactory.getLogger(CuentasDAOTest.class);
	
	@Autowired
	private ICuentasDAO cuentasDAO;
	
	@Autowired
	private IClienteDAO clienteDAO;
	
	private String vCueNumero = "5001-888-9999";
	
	
	@Test
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void atest() {
		
		Cuentas vCuentas = new Cuentas();
		vCuentas.setCueActiva("s");
		vCuentas.setCueClave("ClaveCuenta");
		vCuentas.setCueNumero(vCueNumero);
		vCuentas.setCueSaldo(new BigDecimal(1200000.3));
		Clientes cliente=clienteDAO.consultarClinetePorId(251234L);
		
		vCuentas.setClientes(cliente);
		
		cuentasDAO.grabar(vCuentas);
		
	}
	
	@Test
	@Transactional(readOnly=true)
	public void btest() {
		Cuentas vCuentas = cuentasDAO.consultarCuentaPorNumero(vCueNumero);
		assertNotNull("Cuenta no existe", vCuentas);
		
		log.info(vCuentas.getCueNumero());
	}

	@Test
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void ctest() {
		Cuentas vCuentas = cuentasDAO.consultarCuentaPorNumero(vCueNumero);
		assertNotNull("Cuenta no existe", vCuentas);
		vCuentas.setCueClave("NuevaClave999");
		cuentasDAO.modificar(vCuentas);
	}

	
	
	@Test
	@Transactional(readOnly=true)
	public void etest() {
		List<Cuentas> vLstCuentas=cuentasDAO.consultarTodos();
		for (Cuentas cuenta : vLstCuentas) {
			log.info(cuenta.getCueNumero()+" - "+cuenta.getCueSaldo());
		}
	}
	
	@Test
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void dtest() {
		Cuentas vCuentas = cuentasDAO.consultarCuentaPorNumero(vCueNumero);
		assertNotNull("Cuenta no existe", vCuentas);
		cuentasDAO.borrar(vCuentas);
	}

	

}
