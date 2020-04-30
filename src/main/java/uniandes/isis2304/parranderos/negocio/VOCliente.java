package uniandes.isis2304.parranderos.negocio;

public interface VOCliente 
{

	//METODOS//
	
	public long getNumeroIdentificacion();

	public String getTipoIdentificacion();

	public String getNombre();
	
	public String getTipoCliente();

	@Override
	public String toString();
}
