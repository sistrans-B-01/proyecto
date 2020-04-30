package uniandes.isis2304.parranderos.negocio;

import java.sql.Timestamp;

public interface VOOferta 
{

	public long getId();

	public Timestamp getFechaFin();

	public Timestamp getFechaInicio();

	public int getDiasActiva();
	
	public int getDiasUsada();

	public int getDescuento();
	
	public String getTiempoContraro();

	/** 
	 * @return Una cadena con la información básica
	 */
	@Override
	public String toString();
}
