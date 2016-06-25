package co.edu.usbcali.demo.dto;

import java.util.Date;

public class TransaccionesInfo {
private String prvStrTipo;
private Long prvLngValor;
private Date prvDtmFecha;

public String getPrvStrTipo() {
	return prvStrTipo;
}
public void setPrvStrTipo(String prvStrTipo) {
	this.prvStrTipo = prvStrTipo;
}
public Long getPrvLngValor() {
	return prvLngValor;
}
public void setPrvLngValor(Long prvLngValor) {
	this.prvLngValor = prvLngValor;
}
public Date getPrvDtmFecha() {
	return prvDtmFecha;
}
public void setPrvDtmFecha(Date prvDtmFecha) {
	this.prvDtmFecha = prvDtmFecha;
}

public TransaccionesInfo(String pvStrTipo, Long pvLngValor, Date pvDtmFecha) {
	prvStrTipo = pvStrTipo;
	prvLngValor = pvLngValor;
	prvDtmFecha = pvDtmFecha;
}

}
