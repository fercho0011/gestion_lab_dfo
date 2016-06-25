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

import co.edu.usbcali.demo.dao.IConsignacionesDAO;
import co.edu.usbcali.demo.dao.ICuentasDAO;
import co.edu.usbcali.demo.dao.IUsuariosDAO;
import co.edu.usbcali.demo.modelo.Consignaciones;
import co.edu.usbcali.demo.modelo.ConsignacionesId;
import co.edu.usbcali.demo.modelo.Cuentas;
import co.edu.usbcali.demo.modelo.Usuarios;

@Service
@Scope("singleton")
public class ConsignacionesLogica implements IConsignacionesLogica {
	
	private Logger log=LoggerFactory.getLogger(ConsignacionesLogica.class);
	
	@Autowired
	private IConsignacionesDAO consignacionesDAO;
	
	@Autowired
	private ICuentasDAO cuentasDAO;
	
	@Autowired
	private IUsuariosDAO usuariosDAO;
	
	@Autowired
	private Validator validator;
	

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void grabar(Consignaciones consignaciones) throws Exception {
		Validador(consignaciones);
		
		Cuentas cuentas=cuentasDAO.consultarCuentaPorNumero(consignaciones.getCuentas().getCueNumero());
		if(cuentas==null){
			throw new Exception("La cuenta no existe");
		}
		consignaciones.setCuentas(cuentas);
		
		Usuarios usuarios=usuariosDAO.consultarUsuariosPorCedula(consignaciones.getUsuarios().getUsuCedula());
		if(usuarios==null){
			throw new Exception("El usuario no existe");
		}
		consignaciones.setUsuarios(usuarios);
		
		consignacionesDAO.grabar(consignaciones);
	}

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void modificar(Consignaciones consignaciones) throws Exception {
		
		Validador(consignaciones);
		
		Cuentas cuentas=cuentasDAO.consultarCuentaPorNumero(consignaciones.getCuentas().getCueNumero());
		if(cuentas==null){
			throw new Exception("La cuenta no existe");
		}
		consignaciones.setCuentas(cuentas);
		
		Usuarios usuarios=usuariosDAO.consultarUsuariosPorCedula(consignaciones.getUsuarios().getUsuCedula());
		if(usuarios==null){
			throw new Exception("El usuario no existe");
		}
		consignaciones.setUsuarios(usuarios);
		
		consignacionesDAO.modificar(consignaciones);

	}

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void borrar(Consignaciones consignaciones) throws Exception {
		if(consignaciones==null){
			throw new Exception("La consignación es null");
		}
		Consignaciones entity=consignacionesDAO.consultarConsignacionPorId(consignaciones.getId());
		if(entity==null){
			throw new Exception("La consignación que desea eliminar no existe");
		}
		consignacionesDAO.borrar(entity);
	}

	@Override
	@Transactional(readOnly=true)
	public Consignaciones consultarConsignacionPorId(ConsignacionesId consignacionesId) throws Exception {
		return consignacionesDAO.consultarConsignacionPorId(consignacionesId);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Consignaciones> consultarTodos() throws Exception {		
		return consignacionesDAO.consultarTodos();
	}
	
	private void Validador (Consignaciones consignaciones) throws Exception{
		StringBuilder stringBuilder=new StringBuilder();
		
		Set<ConstraintViolation<Consignaciones>> constraintViolations=validator.validate(consignaciones);
		if(constraintViolations.size()>0){
			for (ConstraintViolation<Consignaciones> constraintViolation : constraintViolations) {
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
