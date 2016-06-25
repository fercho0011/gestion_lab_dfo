package co.edu.usbcali.demo.logica;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.demo.dao.IClienteDAO;
import co.edu.usbcali.demo.dao.ITiposDocumentosDAO;
import co.edu.usbcali.demo.modelo.Clientes;
import co.edu.usbcali.demo.modelo.TiposDocumentos;

@Service
@Scope("singleton")
public class ClienteLogica implements IClienteLogica {
	
	private Logger log=LoggerFactory.getLogger(ClienteLogica.class);
	
	@Autowired
	private IClienteDAO clienteDAO;
	
	@Autowired
	private ITiposDocumentosDAO tipoDocumentoDAO;
	
	@Autowired
	private Validator validator;
	

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void grabar(Clientes clientes) throws Exception {
		Validador(clientes);
		
		TiposDocumentos tiposDocumentos=tipoDocumentoDAO.consultarTiposDocumentosPorID(clientes.getTiposDocumentos().getTdocCodigo());
		if(tiposDocumentos==null){
			throw new Exception("El tipo de documento no existe");
		}
		clientes.setTiposDocumentos(tiposDocumentos);
		
		clienteDAO.grabar(clientes);
		
	}

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void modificar(Clientes clientes) throws Exception {
		
		Validador(clientes);
		TiposDocumentos tiposDocumentos=tipoDocumentoDAO.consultarTiposDocumentosPorID(clientes.getTiposDocumentos().getTdocCodigo());
		if(tiposDocumentos==null){
			throw new Exception("El tipo de documento no existe");
		}
		clientes.setTiposDocumentos(tiposDocumentos);
		
		clienteDAO.modificar(clientes);

	}

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void borrar(Clientes clientes) throws Exception {
		if(clientes==null){
			throw new Exception("El clientes es null");
		}
		Clientes entity=clienteDAO.consultarClinetePorId(clientes.getCliId());
		if(entity==null){
			throw new Exception("El cliente que desea eliminar no existe");
		}
		
		clienteDAO.borrar(entity);

	}

	@Override
	@Transactional(readOnly=true)
	public Clientes consultarClinetePorId(long cliId) throws Exception {
		return clienteDAO.consultarClinetePorId(cliId);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Clientes> consultarTodos() throws Exception {		
		return clienteDAO.consultarTodos();
	}
	
	private void Validador (Clientes clientes) throws Exception{
		StringBuilder stringBuilder=new StringBuilder();
		
		Set<ConstraintViolation<Clientes>> constraintViolations=validator.validate(clientes);
		if(constraintViolations.size()>0){
			for (ConstraintViolation<Clientes> constraintViolation : constraintViolations) {
				log.error(constraintViolation.getPropertyPath().toString());
				log.error(constraintViolation.getMessage());
				stringBuilder.append(constraintViolation.getPropertyPath().toString());
				stringBuilder.append("-");
				stringBuilder.append(constraintViolation.getMessage());
				stringBuilder.append(",");
			}
			throw new Exception(stringBuilder.toString());
		}
		
		
	}
	

}
