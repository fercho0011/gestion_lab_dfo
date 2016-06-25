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

import co.edu.usbcali.demo.dao.ICuentasDAO;
import co.edu.usbcali.demo.dao.IRetirosDAO;
import co.edu.usbcali.demo.dao.IUsuariosDAO;
import co.edu.usbcali.demo.modelo.Cuentas;
import co.edu.usbcali.demo.modelo.Retiros;
import co.edu.usbcali.demo.modelo.RetirosId;
import co.edu.usbcali.demo.modelo.Usuarios;

@Service
@Scope("singleton")
public class RetirosLogica implements IRetirosLogica {
	
	private Logger log=LoggerFactory.getLogger(RetirosLogica.class);
	
	@Autowired
	private IRetirosDAO retirosDAO;
	
	@Autowired
	private ICuentasDAO cuentasDAO;
	
	@Autowired
	private IUsuariosDAO usuariosDAO;
	
	@Autowired
	private Validator validator;
	

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void grabar(Retiros retiros) throws Exception {
		Validador(retiros);
		
		Cuentas cuentas=cuentasDAO.consultarCuentaPorNumero(retiros.getCuentas().getCueNumero());
		if(cuentas==null){
			throw new Exception("La cuenta no existe");
		}
		retiros.setCuentas(cuentas);
		
		Usuarios usuarios=usuariosDAO.consultarUsuariosPorCedula(retiros.getUsuarios().getUsuCedula());
		if(usuarios==null){
			throw new Exception("El usuario no existe");
		}
		retiros.setUsuarios(usuarios);
		
		retirosDAO.grabar(retiros);
	}

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void modificar(Retiros retiros) throws Exception {
		
		Validador(retiros);
		
		Cuentas cuentas=cuentasDAO.consultarCuentaPorNumero(retiros.getCuentas().getCueNumero());
		if(cuentas==null){
			throw new Exception("La cuenta no existe");
		}
		retiros.setCuentas(cuentas);
		
		Usuarios usuarios=usuariosDAO.consultarUsuariosPorCedula(retiros.getUsuarios().getUsuCedula());
		if(usuarios==null){
			throw new Exception("El usuario no existe");
		}
		retiros.setUsuarios(usuarios);
		
		retirosDAO.modificar(retiros);

	}

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void borrar(Retiros retiros) throws Exception {
		if(retiros==null){
			throw new Exception("El retiro es null");
		}
		Retiros entity=retirosDAO.consultarRetirosPorId(retiros.getId());
		if(entity==null){
			throw new Exception("El retiro que desea eliminar no existe");
		}
		retirosDAO.borrar(entity);
	}

	@Override
	@Transactional(readOnly=true)
	public Retiros consultarRetirosPorId(RetirosId retirosId) throws Exception {
		return retirosDAO.consultarRetirosPorId(retirosId);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Retiros> consultarTodos() throws Exception {		
		return retirosDAO.consultarTodos();
	}
	
	private void Validador (Retiros retiros) throws Exception{
		StringBuilder stringBuilder=new StringBuilder();
		
		Set<ConstraintViolation<Retiros>> constraintViolations=validator.validate(retiros);
		if(constraintViolations.size()>0){
			for (ConstraintViolation<Retiros> constraintViolation : constraintViolations) {
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
