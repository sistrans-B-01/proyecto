package uniandes.isis2304.parranderos.negocio;

import java.sql.Timestamp;

public class ReservaColectiva implements VOReservaColectiva{
	
	//ATRIBUTOS//
	
		private long id; //PK
		private String tipoIdCliente; 
		private long idCliente;
		private Timestamp fechaLLegada;
		private Timestamp fechaIda;
		private Timestamp fechaPago;
		private int cantidad;
		private String tipoAlojamiento;
		private double costo;
		
		
		//METODOS//
		
		public ReservaColectiva() {
			
			this.id = 0;
			this.tipoIdCliente = "";
			this.idCliente = 0;
			this.fechaLLegada = new Timestamp (0);
			this.fechaIda = new Timestamp (0);
			this.fechaPago = new Timestamp (0);
			this.cantidad = 0;
			this.tipoAlojamiento = "";
			this.costo = 0;
		}
		
		public ReservaColectiva(long id, String tipoIdCliente, long idCliente, Timestamp fechaLLegada, Timestamp fechaIda,
				Timestamp fechaPago, int cantidad, String tipoAlojamiento, double costo) {
			
			this.id = id;
			this.tipoIdCliente = tipoIdCliente;
			this.idCliente = idCliente;
			this.fechaLLegada = fechaLLegada;
			this.fechaIda = fechaIda;
			this.fechaPago = fechaPago;
			this.cantidad = cantidad;
			this.tipoAlojamiento = tipoAlojamiento;
			this.costo = costo;
		}

		
		//setters && getters
		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public String getTipoIdCliente() {
			return tipoIdCliente;
		}

		public void setTipoIdCliente(String tipoIdCliente) {
			this.tipoIdCliente = tipoIdCliente;
		}

		public long getIdCliente() {
			return idCliente;
		}

		public void setIdCliente(long idCliente) {
			this.idCliente = idCliente;
		}

		public Timestamp getFechaLLegada() {
			return fechaLLegada;
		}

		public void setFechaLLegada(Timestamp fechaLLegada) {
			this.fechaLLegada = fechaLLegada;
		}

		public Timestamp getFechaIda() {
			return fechaIda;
		}

		public void setFechaIda(Timestamp fechaIda) {
			this.fechaIda = fechaIda;
		}

		public Timestamp getFechaPago() {
			return fechaPago;
		}

		public void setFechaPago(Timestamp fechaPago) {
			this.fechaPago = fechaPago;
		}

		public int getCantidad() {
			return cantidad;
		}

		public void setCantidad(int cantidad) {
			this.cantidad = cantidad;
		}

		public String getTipoAlojamiento() {
			return tipoAlojamiento;
		}

		public void setTipoAlojamiento(String tipoAlojamiento) {
			this.tipoAlojamiento = tipoAlojamiento;
		}

		public double getCosto() {
			return costo;
		}

		public void setCosto(double costo) {
			this.costo = costo;
		}

		@Override
		public String toString() {
			return "ReservaColectiva [id=" + id + ", tipoIdCliente=" + tipoIdCliente + ", idCliente=" + idCliente
					+ ", fechaLLegada=" + fechaLLegada + ", fechaIda=" + fechaIda + ", fechaPago=" + fechaPago
					+ ", cantidad=" + cantidad + ", tipoAlojamiento=" + tipoAlojamiento + ", costo=" + costo + "]";
		}	
		
		

}

		
		
	
		