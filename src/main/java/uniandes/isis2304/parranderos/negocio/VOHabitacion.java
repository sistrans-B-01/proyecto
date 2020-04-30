package uniandes.isis2304.parranderos.negocio;

public interface VOHabitacion 
{

	public long getId();

	public int getCapacidad();

	public int getCompartidaConCuantos();

	public int getDiasUsada();

	public int getNumeroEstrellas();

	public int getNumeroHab();

	public double getPrecio();

	public String getServicios();

	public String getTamano();

	public String getCategoria();
	
	public long getIdDominio() ;

	public String getTipoDominio();
	
	public String toString();

}
