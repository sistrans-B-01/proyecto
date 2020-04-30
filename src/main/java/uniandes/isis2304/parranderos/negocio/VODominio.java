package uniandes.isis2304.parranderos.negocio;

public interface VODominio 
{

	//METODOS//
	
		public long getRegistro();

		public String getTipoRegistro();

		public String getUbicacion();

		public String getNombre();
		
		public String getHorario();

		@Override
		public String toString();
}
