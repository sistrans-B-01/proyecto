package uniandes.isis2304.parranderos.negocio;

public class ServiciosAdicionales implements VOServiciosAdicionales
{

	//ATRIBUTOS//
	
	private long id; //PK
	private double costo;
	private String nombre;
	private long oferta;
	
	//METODOS//
	public ServiciosAdicionales()
	{
		this.id=0;
		this.costo=0;
		this.nombre="";
		this.oferta=0;
	}
	
	public ServiciosAdicionales( long id, double cos, String nom, long of)
	{
		this.id=id;
		this.costo=cos;
		this.nombre=nom;
		this.oferta=of;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public double getCosto() {
		return costo;
	}
	public void setCosto(double costo) {
		this.costo = costo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
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
		return "ServiciosAdicionales [id=" + id + ", nombre=" + nombre + ", costo=" + costo + ", oferta="
				+ oferta + "]";
	}
}
