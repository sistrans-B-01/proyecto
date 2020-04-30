package uniandes.isis2304.parranderos.negocio;

public interface VOVivienda 
{
	public String getUbicacion();
	
	public String getCaracteristicasSeguro();
	
	public String getMenaje();
	
	public int getNumeroDiasUsada();
	
	public int getNumeroHabitaciones();
	
	public String getServiciosPublicos();

	public long getIdVecino();
	
	public String getTipoIdVecino();

	@Override
	public String toString();
}
