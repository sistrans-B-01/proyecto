package uniandes.isis2304.parranderos.negocio;

public interface VOServiciosAdicionales 
{

	public long getId();
	
	public double getCosto();

	public String getNombre();
	
	public long getOferta();

	@Override
	public String toString();
}
