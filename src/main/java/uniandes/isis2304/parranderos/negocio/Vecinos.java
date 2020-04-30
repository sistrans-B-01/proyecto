package uniandes.isis2304.parranderos.negocio;

public class Vecinos implements VOVecinos
{

	//ATRIBUTOS//
	
	private String nombre;
	private long numeroIdentificacion; //PK
	private String tipoIdentificacion; //PK
	
	//METODOS//
	
	public Vecinos()
	{
		this.nombre="";
		this.numeroIdentificacion=0;
		this.tipoIdentificacion="";
	}
	
	public Vecinos(String nom, long iden, String tipo)
	{
		this.nombre=nom;
		this.numeroIdentificacion=iden;
		this.tipoIdentificacion=tipo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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
	
	/** 
	 * @return Una cadena con la información básica
	 */
	@Override
	public String toString() 
	{
		return "Vecinos [nombre=" + nombre + ", identificacion=" + numeroIdentificacion + ", tipoIdentificacion="
				+ tipoIdentificacion + "]";
	}
}
