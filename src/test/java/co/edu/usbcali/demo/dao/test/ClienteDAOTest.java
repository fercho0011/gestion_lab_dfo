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

import co.edu.usbcali.demo.dao.IClienteDAO;
import co.edu.usbcali.demo.dao.ITiposDocumentosDAO;
import co.edu.usbcali.demo.modelo.Clientes;
import co.edu.usbcali.demo.modelo.TiposDocumentos;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class ClienteDAOTest {
	
	private static final Logger log=LoggerFactory.getLogger(ClienteDAOTest.class);
	
	@Autowired
	private IClienteDAO clienteDAO;
	
	@Autowired
	private ITiposDocumentosDAO tiposDocumentosDAO;
	
	private Long prvLngId = 1130626646L;
	
	
	@Test
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void atest() {
		
		Clientes clientes = new Clientes();
		clientes.setCliDireccion("AV 11111 # 222222");
		clientes.setCliId(prvLngId);
		clientes.setCliMail("Corrego@micorreo.edu.co");
		clientes.setCliNombre("NERON NAVARRETE");
		clientes.setCliTelefono("99999999");
		TiposDocumentos tiposDocumentos=tiposDocumentosDAO.consultarTiposDocumentosPorID(10L);
		
		clientes.setTiposDocumentos(tiposDocumentos);
		
		clienteDAO.grabar(clientes);
		
	}
	
	@Test
	@Transactional(readOnly=true)
	public void btest() {
		Clientes vCliente = clienteDAO.consultarClinetePorId(prvLngId);
		assertNotNull("El cliente no existe", vCliente);
		
		log.info(vCliente.getCliNombre());
	}

	@Test
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void ctest() {
		Clientes vCliente = clienteDAO.consultarClinetePorId(prvLngId);
		assertNotNull("El cliente no existe", vCliente);
		vCliente.setCliNombre("PEDRITO PEREZ");
		clienteDAO.modificar(vCliente);
	}

	@Test
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void dtest() {
		Clientes vCliente = clienteDAO.consultarClinetePorId(prvLngId);
		assertNotNull("El cliente no existe", vCliente);
		clienteDAO.borrar(vCliente);
	}

	
	@Test
	@Transactional(readOnly=true)
	public void etest() {
		List<Clientes> losClientes=clienteDAO.consultarTodos();
		for (Clientes clientes : losClientes) {
			log.info(clientes.getCliNombre());
		}
	}

}
