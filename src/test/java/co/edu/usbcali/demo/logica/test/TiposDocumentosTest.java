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

import co.edu.usbcali.demo.logica.ITiposDocumentosLogica;
import co.edu.usbcali.demo.modelo.TiposDocumentos;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class TiposDocumentosTest {
	
	private static final Logger log=LoggerFactory.getLogger(TiposDocumentosTest.class);
	
	@Autowired
	private ITiposDocumentosLogica tiposDocumentosLogic;
	
	
	private Long vTiposDocID = 90L;
	
	
	@Test
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void atest() throws Exception {
		
		TiposDocumentos tiposDocumentos = new TiposDocumentos();
		tiposDocumentos.setTdocCodigo(vTiposDocID);
		tiposDocumentos.setTdocNombre("NIUUP");
		
		tiposDocumentosLogic.grabar(tiposDocumentos);
		
	}
	
	@Test
	@Transactional(readOnly=true)
	public void btest() throws Exception {
		TiposDocumentos tiposDocumentos = tiposDocumentosLogic.consultarTiposDocumentosPorID(vTiposDocID);
		assertNotNull("Tipos de documento no catalogado", tiposDocumentos);
		
		log.info(tiposDocumentos.getTdocNombre());
	}

	@Test
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void ctest() throws Exception {
		TiposDocumentos tiposDocumentos = tiposDocumentosLogic.consultarTiposDocumentosPorID(vTiposDocID);
		assertNotNull("Tipos de documento no catalogado", tiposDocumentos);
		tiposDocumentos.setTdocNombre("OTRA ID");
		tiposDocumentosLogic.modificar(tiposDocumentos);
	}
	
	@Test
	@Transactional(readOnly=true)
	public void etest() throws Exception {
		List<TiposDocumentos> vLstTiposDocto=tiposDocumentosLogic.consultarTodos();
		for (TiposDocumentos tipoDocumento : vLstTiposDocto) {
			log.info(tipoDocumento.getTdocNombre());
		}
	}

	@Test
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void dtest() throws Exception {
		TiposDocumentos tiposDocumentos = tiposDocumentosLogic.consultarTiposDocumentosPorID(vTiposDocID);
		assertNotNull("Tipos de documento no catalogado", tiposDocumentos);
		tiposDocumentosLogic.borrar(tiposDocumentos);
	}
	
}
