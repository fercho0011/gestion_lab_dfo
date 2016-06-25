package co.edu.usbcali.demo.logica.test;

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
import co.edu.usbcali.demo.logica.ICuentasLogica;
import co.edu.usbcali.demo.modelo.Clientes;
import co.edu.usbcali.demo.modelo.Cuentas;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class CuentasLogicaTest {
	
	private static final Logger log=LoggerFactory.getLogger(CuentasLogicaTest.class);
	
	@Autowired
	private ICuentasLogica cuentasLogica;
	
	@Autowired
	private IClienteDAO clientesDAO;
	
	private String vCueNumero = "5001-888-9999";
	
	
	@Test
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void atest() throws Exception {
		
		Cuentas vCuentas = new Cuentas();
		vCuentas.setCueActiva("s");
		vCuentas.setCueClave("ClaveCuenta");
		vCuentas.setCueNumero(vCueNumero);
		vCuentas.setCueSaldo(new BigDecimal(1200000.3));
		Clientes cliente=clientesDAO.consultarClinetePorId(251234L);
		
		vCuentas.setClientes(cliente);
		
		cuentasLogica.grabar(vCuentas);
		
	}
	
	@Test
	@Transactional(readOnly=true)
	public void btest() throws Exception {
		Cuentas vCuentas = cuentasLogica.consultarCuentaPorNumero(vCueNumero);
		assertNotNull("Cuenta no existe", vCuentas);
		
		log.info(vCuentas.getCueNumero());
	}

	@Test
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void ctest() throws Exception {
		Cuentas vCuentas = cuentasLogica.consultarCuentaPorNumero(vCueNumero);
		assertNotNull("Cuenta no existe", vCuentas);
		vCuentas.setCueClave("NuevaClave999");
		cuentasLogica.modificar(vCuentas);
	}
	
	@Test
	@Transactional(readOnly=true)
	public void etest() throws Exception {
		List<Cuentas> vLstCuentas=cuentasLogica.consultarTodos();
		for (Cuentas cuenta : vLstCuentas) {
			log.info(cuenta.getCueNumero()+" - "+cuenta.getCueSaldo());
		}
	}
	
	@Test
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void dtest() throws Exception {
		Cuentas vCuentas = cuentasLogica.consultarCuentaPorNumero(vCueNumero);
		assertNotNull("Cuenta no existe", vCuentas);
		cuentasLogica.borrar(vCuentas);
	}

}
