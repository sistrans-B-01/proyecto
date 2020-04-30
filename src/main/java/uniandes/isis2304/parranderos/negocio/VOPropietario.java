package uniandes.isis2304.parranderos.negocio;

public interface VOPropietario 
{

	//METODOS//
	

		public long getNumeroIdentificacion();

		public String getTipoIdentificacion();

		public String getNombre();
		
		public String getTipoPropietario();

		@Override
		public String toString();
}
