package co.edu.usbcali.demo.logica;



import co.edu.usbcali.demo.modelo.Consignaciones;
import co.edu.usbcali.demo.modelo.Retiros;

public interface ITransaccionalLogica {
	
	public void realizarConsignacion(Consignaciones consignaciones)throws Exception;
	public void realizarRetiro(Retiros retiros)throws Exception;
	

}
