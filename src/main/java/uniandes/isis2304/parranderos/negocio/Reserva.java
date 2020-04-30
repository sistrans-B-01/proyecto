package uniandes.isis2304.parranderos.negocio;

import java.sql.Timestamp;

public class Reserva implements VOReserva
{

	//ATRIBUTOS//
	
	private long id;
	private double costoPagado;
	private double costoTotal;
	private Timestamp fechaIda;
	private Timestamp fechaLlegada;
	private String tiempoAlojamiento;
	private long cliente;
	private String tipoIdCliente;
	private long oferta;
	
	//METODOS//
	
	public Reserva()
	{
		this.id=0;
		this.costoPagado=0;
		this.costoTotal=0;
		this.fechaIda=new Timestamp (0);
		this.fechaLlegada=new Timestamp (0);
		this.tiempoAlojamiento="";
		this.cliente=0;
		this.setTipoIdCliente("");
		this.oferta=0;
	}
	
	public Reserva(long id, double cosp, double cost, Timestamp ida, Timestamp llega,
			String tiem, long cli, String tipc, long of)
	{
		this.id=id;
		this.costoPagado=cosp;
		this.costoTotal=cost;
		this.fechaIda=ida;
		this.fechaLlegada=llega;
		this.tiempoAlojamiento=tiem;
		this.cliente=cli;
		this.setTipoIdCliente(tipc);
		this.oferta=of;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getCostoPagado() {
		return costoPagado;
	}

	public void setCostoPagado(double costoPagado) {
		this.costoPagado = costoPagado;
	}

	public double getCostoTotal() {
		return costoTotal;
	}

	public void setCostoTotal(double costoTotal) {
		this.costoTotal = costoTotal;
	}

	public Timestamp getFechaIda() {
		return fechaIda;
	}

	public void setFechaIda(Timestamp fechaIda) {
		this.fechaIda = fechaIda;
	}

	public Timestamp getFechaLlegada() {
		return fechaLlegada;
	}

	public void setFechaLlegada(Timestamp fechaLlegada) {
		this.fechaLlegada = fechaLlegada;
	}

	public String getTiempoAlojamiento() {
		return tiempoAlojamiento;
	}

	public void setTiempoAlojamiento(String tiempoAlojamiento) {
		this.tiempoAlojamiento = tiempoAlojamiento;
	}

	public long getCliente() {
		return cliente;
	}

	public void setCliente(long cliente) {
		this.cliente = cliente;
	}

	public String getTipoIdCliente() {
		return tipoIdCliente;
	}

	public void setTipoIdCliente(String tipoIdCliente) {
		this.tipoIdCliente = tipoIdCliente;
	}

	public long getOferta() {
		return oferta;
	}

	public void setOferta(long oferta) {
		this.oferta = oferta;
	}
	
	/** 
	 * @return Una cadena con la información básica
	 */
	@Override
	public String toString() 
	{
		return "Reserva [id=" + id + ", idCliente=" + cliente + ", idOferta=" + oferta + 
				", costoPagado=" + costoPagado + ", costoTotal=" + costoTotal + 
				", fechaIda=" + fechaIda + ", fechaLlegada=" + fechaLlegada + 
				", tiempoAlojamiento=" + tiempoAlojamiento + "]";
	}
}
