package uniandes.isis2304.parranderos.negocio;

/**
 * Clase para modelar el concepto APARTAMENTO del negocio de AlohAndes
 */
public class Apartamento implements VOApartamento
{

	//ATRIBUTOS//
	
	private String ubicacion; //PK
	private int numeroHabitaciones;
	private String servicios;
	private long idPropietario;
	private String tipoIdentificacion;
	
	//METODOS//
	
	public Apartamento()
	{
		this.idPropietario=0;
		this.numeroHabitaciones=0;
		this.servicios="";
		this.ubicacion="";
		this.setTipoIdentificacion("");
	}
	
	public Apartamento( int numero, String servicio, String ubicacion, long idP,
			String tipo)
	{
		this.numeroHabitaciones= numero;
		this.servicios= servicio;
		this.ubicacion= ubicacion;
		this.idPropietario=idP;
		this.setTipoIdentificacion(tipo);
	}
	
	public int getNumeroHabitaciones() {
		return numeroHabitaciones;
	}

	public void setNumeroHabitaciones(int numeroHabitaciones) {
		this.numeroHabitaciones = numeroHabitaciones;
	}

	public String getServicios() {
		return servicios;
	}

	public void setServicios(String servicios) {
		this.servicios = servicios;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public long getIdPropietario() {
		return idPropietario;
	}

	public void setIdPropietario(long idPropietario) {
		this.idPropietario = idPropietario;
	}

	public String getTipoIdentificacion() {
		return tipoIdentificacion;
	}

	public void setTipoIdentificacion(String tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}

	/** 
	 * @return Una cadena con la información básica
	 */
	@Override
	public String toString() 
	{
		return "Apartemento [ubicacion=" + ubicacion + ", idPropietario=" + idPropietario + ", numeroHabitaciones=" + numeroHabitaciones + 
				", servicios="+ servicios + ", tipoIdetificacionPropiestario=" + tipoIdentificacion +"]";
	}
}
