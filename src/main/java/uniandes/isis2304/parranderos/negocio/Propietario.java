package uniandes.isis2304.parranderos.negocio;

public class Propietario implements VOPropietario
{

	//ATRIBUTOS//
	
	private String nombre;
	private long numeroIdentificacion; //PK
	private String tipoIdentificacion; //PK
	private String tipoPropietario;
	
	//METODOS//
	
	public Propietario()
	{
		this.nombre="";
		this.numeroIdentificacion=0;
		this.tipoIdentificacion="";
		this.tipoPropietario="";
	}
	
	public Propietario(String nom, long iden, String tipo, String tipop)
	{
		this.nombre=nom;
		this.numeroIdentificacion=iden;
		this.tipoIdentificacion=tipo;
		this.tipoPropietario=tipop;
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
	
	public String getTipoPropietario() {
		return tipoPropietario;
	}

	public void setTipoPropietario(String tipoPropietario) {
		this.tipoPropietario = tipoPropietario;
	}

	/** 
	 * @return Una cadena con la información básica
	 */
	@Override
	public String toString() 
	{
		return "Propietario [nombre=" + nombre + ", identificacion=" + numeroIdentificacion + ", tipoIdentificacion="
				+ tipoIdentificacion + ", tipoPropietario=" + tipoPropietario +"]";
	}	
}
