package uniandes.isis2304.parranderos.negocio;

public class OfertaHabitacion implements VOOfertaHabitacion
{

	//ATRIBUTOS//
	
	private long idOferta;
	private long idHabitacion;
	
	//METODOS//
	
	public OfertaHabitacion()
	{
		this.idOferta=0;
		this.idHabitacion=0;
	}
	
	public OfertaHabitacion(long ido, long idh)
	{
		this.idOferta=ido;
		this.idHabitacion=idh;
	}

	public long getIdOferta() {
		return idOferta;
	}

	public void setIdOferta(long idOferta) {
		this.idOferta = idOferta;
	}

	public long getIdHabitacion() {
		return idHabitacion;
	}

	public void setIdHabitacion(long idHabitacion) {
		this.idHabitacion = idHabitacion;
	}

	/** 
	 * @return Una cadena con la información básica
	 */
	@Override
	public String toString() 
	{
		return "OfertaHabitacion [idOferta=" + idOferta + ", idHabitacion=" + idHabitacion + "]";
	}
	
}
