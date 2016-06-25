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

import co.edu.usbcali.demo.dao.ITiposDocumentosDAO;
import co.edu.usbcali.demo.modelo.TiposDocumentos;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class TiposDocumentosDAOTest {
	
	private static final Logger log=LoggerFactory.getLogger(TiposDocumentosDAOTest.class);
	
	@Autowired
	private ITiposDocumentosDAO tiposDocumentosDAO;
	
	private Long vTiposDocID = 90L;
	
	
	@Test
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void atest() {
		
		TiposDocumentos tiposDocumentos = new TiposDocumentos();
		tiposDocumentos.setTdocCodigo(vTiposDocID);
		tiposDocumentos.setTdocNombre("NIUUP");
		
		tiposDocumentosDAO.grabar(tiposDocumentos);
		
	}
	
	@Test
	@Transactional(readOnly=true)
	public void btest() {
		TiposDocumentos tiposDocumentos = tiposDocumentosDAO.consultarTiposDocumentosPorID(vTiposDocID);
		assertNotNull("Tipos de documento no catalogado", tiposDocumentos);
		
		log.info(tiposDocumentos.getTdocNombre());
	}

	@Test
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void ctest() {
		TiposDocumentos tiposDocumentos = tiposDocumentosDAO.consultarTiposDocumentosPorID(vTiposDocID);
		assertNotNull("Tipos de documento no catalogado", tiposDocumentos);
		tiposDocumentos.setTdocNombre("OTRA ID");
		tiposDocumentosDAO.modificar(tiposDocumentos);
	}

	@Test
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void dtest() {
		TiposDocumentos tiposDocumentos = tiposDocumentosDAO.consultarTiposDocumentosPorID(vTiposDocID);
		assertNotNull("Tipos de documento no catalogado", tiposDocumentos);
		tiposDocumentosDAO.borrar(tiposDocumentos);
	}

	
	@Test
	@Transactional(readOnly=true)
	public void etest() {
		List<TiposDocumentos> vLstTiposDocto=tiposDocumentosDAO.consultarTodos();
		for (TiposDocumentos tipoDocumento : vLstTiposDocto) {
			log.info(tipoDocumento.getTdocNombre());
		}
	}

}
