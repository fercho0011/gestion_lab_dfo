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


import co.edu.usbcali.demo.dao.ITiposUsuariosDAO;
import co.edu.usbcali.demo.modelo.TiposUsuarios;

@Service
@Scope("singleton")
public class TiposUsuariosLogica implements ITiposUsuariosLogica {
	
	private Logger log=LoggerFactory.getLogger(TiposUsuariosLogica.class);
	
	@Autowired
	private ITiposUsuariosDAO tiposUsuariosDAO;
	
	
	@Autowired
	private Validator validator;
	

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void grabar(TiposUsuarios tiposUsuarios) throws Exception {
		Validador(tiposUsuarios);
		tiposUsuariosDAO.grabar(tiposUsuarios);
	}

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void modificar(TiposUsuarios tiposUsuarios) throws Exception {
		
		Validador(tiposUsuarios);
		tiposUsuariosDAO.modificar(tiposUsuarios);

	}

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void borrar(TiposUsuarios tiposUsuarios) throws Exception {
		if(tiposUsuarios==null){
			throw new Exception("El tipos de usuario es null");
		}
		TiposUsuarios entity=tiposUsuariosDAO.consultarTiposUsuariosPorID(tiposUsuarios.getTusuCodigo());
		if(entity==null){
			throw new Exception("El tipo de usuario que desea eliminar no existe");
		}
		tiposUsuariosDAO.borrar(entity);
	}

	@Override
	@Transactional(readOnly=true)
	public TiposUsuarios consultarTiposUsuariosPorID(long tusuCodigo) throws Exception {
		return tiposUsuariosDAO.consultarTiposUsuariosPorID(tusuCodigo);
	}

	@Override
	@Transactional(readOnly=true)
	public List<TiposUsuarios> consultarTodos() throws Exception {		
		return tiposUsuariosDAO.consultarTodos();
	}
	
	private void Validador (TiposUsuarios tiposUsuarios) throws Exception{
		StringBuilder stringBuilder=new StringBuilder();
		
		Set<ConstraintViolation<TiposUsuarios>> constraintViolations=validator.validate(tiposUsuarios);
		if(constraintViolations.size()>0){
			for (ConstraintViolation<TiposUsuarios> constraintViolation : constraintViolations) {
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
