package uniandes.isis2304.parranderos.negocio;

public interface VOApartamento 
{

	//METODOS//
	
		public String getUbicacion();

		public int getNumeroHabitaciones();

		public String getServicios();

		public long getIdPropietario();
		
		public String getTipoIdentificacion();

		@Override
		public String toString();
}
