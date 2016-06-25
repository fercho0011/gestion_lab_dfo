package co.edu.usbcali.demo.dao;

import java.util.List;

import co.edu.usbcali.demo.modelo.TiposDocumentos;

public interface ITiposDocumentosDAO {
	
	public void grabar(TiposDocumentos tiposDocumentos);
	public void modificar(TiposDocumentos tiposDocumentos);
	public void borrar(TiposDocumentos tiposDocumentos);
	public TiposDocumentos consultarTiposDocumentosPorID(long tdocCodigo);
	public List<TiposDocumentos> consultarTodos();
	

}
