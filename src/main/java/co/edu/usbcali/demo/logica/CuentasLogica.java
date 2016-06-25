package co.edu.usbcali.demo.logica;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.demo.dao.IClienteDAO;
import co.edu.usbcali.demo.dao.ICuentasDAO;
import co.edu.usbcali.demo.modelo.Clientes;
import co.edu.usbcali.demo.modelo.Cuentas;

@Service
@Scope("singleton")
public class CuentasLogica implements ICuentasLogica {

	private Logger log = LoggerFactory.getLogger(CuentasLogica.class);

	@Autowired
	private ICuentasDAO cuentasDAO;

	@Autowired
	private IClienteDAO clienteDAO;

	@Autowired
	private Validator validator;

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void grabar(Cuentas cuentas) throws Exception {
		Validador(cuentas);

		Clientes clientes = clienteDAO.consultarClinetePorId(cuentas.getClientes().getCliId());
		if (clientes == null) {
			throw new Exception("El Cliente no existe");
		}
		cuentas.setClientes(clientes);

		cuentasDAO.grabar(cuentas);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void modificar(Cuentas cuentas) throws Exception {

		Validador(cuentas);

		Clientes clientes = clienteDAO.consultarClinetePorId(cuentas.getClientes().getCliId());
		if (clientes == null) {
			throw new Exception("El Cliente no existe");
		}
		cuentas.setClientes(clientes);

		cuentasDAO.modificar(cuentas);

	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void borrar(Cuentas cuentas) throws Exception {
		if (cuentas == null) {
			throw new Exception("La Cuenta es null");
		}
		Cuentas entity = cuentasDAO.consultarCuentaPorNumero(cuentas.getCueNumero());
		if (entity == null) {
			throw new Exception("La cuenta que desea eliminar no existe");
		}
		cuentasDAO.borrar(entity);
	}

	@Override
	@Transactional(readOnly = true)
	public Cuentas consultarCuentaPorNumero(String cueNumero) throws Exception {
		Cuentas vCuenta;
		vCuenta = cuentasDAO.consultarCuentaPorNumero(cueNumero);
		Hibernate.initialize(vCuenta.getConsignacioneses());
		Hibernate.initialize(vCuenta.getConsignacioneses());

		vCuenta.getRetiroses();
		return vCuenta;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Cuentas> consultarTodos() throws Exception {
		return cuentasDAO.consultarTodos();
	}

	private void Validador(Cuentas cuentas) throws Exception {
		StringBuilder stringBuilder = new StringBuilder();

		Set<ConstraintViolation<Cuentas>> constraintViolations = validator.validate(cuentas);
		if (constraintViolations.size() > 0) {
			for (ConstraintViolation<Cuentas> constraintViolation : constraintViolations) {
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
