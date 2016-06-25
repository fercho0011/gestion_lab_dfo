package co.edu.usbcali.demo.logica;

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
import co.edu.usbcali.demo.dao.IRetirosDAO;
import co.edu.usbcali.demo.dao.IUsuariosDAO;
import co.edu.usbcali.demo.modelo.Consignaciones;
import co.edu.usbcali.demo.modelo.Cuentas;
import co.edu.usbcali.demo.modelo.Retiros;
import co.edu.usbcali.demo.modelo.Usuarios;

@Service
@Scope("singleton")
public class TransaccionalLogica implements ITransaccionalLogica {
	
	private Logger log=LoggerFactory.getLogger(TransaccionalLogica.class);
	
	@Autowired
	private IConsignacionesDAO consignacionesDAO;
	
	@Autowired
	private IRetirosDAO retirosDAO;
	
	
	@Autowired
	private ICuentasDAO cuentasDAO;
	
	@Autowired
	private IUsuariosDAO usuariosDAO;
	
	@Autowired
	private Validator validator;
	private int vAlgoBonito = 0;
	

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void realizarConsignacion(Consignaciones consignaciones) throws Exception {
		ValidadorConsignacion(consignaciones);
		
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
		cuentas.setCueSaldo(cuentas.getCueSaldo().add( consignaciones.getConValor()));
		cuentasDAO.modificar(cuentas);
	}

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void realizarRetiro(Retiros retiros) throws Exception {
			ValidadorRetiro(retiros);
			int vIntVariableBOnita = 0;
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
			cuentas.setCueSaldo(cuentas.getCueSaldo().subtract( retiros.getRetValor()));
			cuentasDAO.modificar(cuentas);
		
	}
	
	
	private void ValidadorConsignacion (Consignaciones consignaciones) throws Exception{
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
	
	private void ValidadorRetiro (Retiros retiros) throws Exception{
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



	



	
