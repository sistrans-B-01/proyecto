package uniandes.isis2304.parranderos.negocio;

public class Cliente implements VOCliente
{

	//ATRIBUTOS//
	
	private long numeroIdentificacion; //PK
	private String tipoIdentificacion;
	private String nombre;
	private String tipoCliente;
	
	//METODOS//
	
	public Cliente()
	{
		this.setNombre("");
		this.setNumeroIdentificacion(0);
		this.setTipoIdentificacion("");
		this.tipoCliente="";
	}
	
	public Cliente(long identificacion, String tipoid, String nom, String tipoc)
	{
		this.setNombre(nom);
		this.setNumeroIdentificacion(identificacion);
		this.setTipoIdentificacion(tipoid);
		this.tipoCliente=tipoc;
	}

	public long getNumeroIdentificacion() {
		return numeroIdentificacion;
	}

	public void setNumeroIdentificacion(long numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
	}

	public String getTipoIdentificacion() {
		return tipoIdentificacion;
	}

	public void setTipoIdentificacion(String tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getTipoCliente() {
		return tipoCliente;
	}

	public void setTipoCliente(String tipoCliente) {
		this.tipoCliente = tipoCliente;
	}

	/** 
	 * @return Una cadena con la información básica
	 */
	@Override
	public String toString() 
	{
		return "Cliente [numeroIdentificacion=" + numeroIdentificacion + " nombre=" + nombre + ", tipoIdentificacion="
				+ tipoIdentificacion + ", TipoCliente=" + tipoCliente + "]";
	}
}
