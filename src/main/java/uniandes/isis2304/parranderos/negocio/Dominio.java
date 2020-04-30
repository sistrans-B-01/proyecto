package uniandes.isis2304.parranderos.negocio;

public class Dominio implements VODominio
{

	//ATRIBUTOS//
	
	private long registro; //PK
	private String tipoRegistro;
	private String nombre;
	private String ubicacion;
	private String horario;
	
	//METODOS//
	
	public Dominio()
	{
		this.registro=0;
		this.tipoRegistro="";
		this.nombre="";
		this.ubicacion="";
		this.horario="";
	}
	
	public Dominio( long reg, String tipo, String nom, String ubi, String hor)
	{
		this.registro= reg;
		this.tipoRegistro=tipo;
		this.nombre=nom;
		this.ubicacion=ubi;
		this.horario=hor;
	}

	public long getRegistro() {
		return registro;
	}

	public void setRegistro(long registro) {
		this.registro = registro;
	}

	public String getTipoRegistro() {
		return tipoRegistro;
	}

	public void setTipoRegistro(String tipoIdentificacion) {
		this.tipoRegistro = tipoIdentificacion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacionRecinto) {
		this.ubicacion = ubicacionRecinto;
	}

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}
	
	/** 
	 * @return Una cadena con la información básica
	 */
	@Override
	public String toString() 
	{
		return "Dominio [registro=" + registro + " nombre=" + nombre + ", tipoIdentificacion="
				+ tipoRegistro + ", ubicacion=" + ubicacion + 
				", horario= " + horario + "]";
	}
}
