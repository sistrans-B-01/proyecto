package uniandes.isis2304.parranderos.negocio;

import java.sql.Timestamp;

public interface VOReserva 
{

	//METODOS//
	
		public long getId();
		
		public double getCostoPagado();
		
		public double getCostoTotal();
		
		public Timestamp getFechaIda();
		
		public Timestamp getFechaLlegada();

		public long getCliente();

		public String getTipoIdCliente();

		public String getTiempoAlojamiento();
		
		public long getOferta();

		@Override
		public String toString();
}
