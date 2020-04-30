package uniandes.isis2304.parranderos.negocio;

public class Habitacion implements VOHabitacion
{

	//ATRIBUTOS//
	
	private long id;
	private int capacidad;
	private int compartidaConCuantos;
	private int diasUsada;
	private int numeroEstrellas;
	private int numeroHab;
	private double precio;
	private String servicios;
	private String tamano;
	private String categoria;
	private long idDominio;
	private String tipoDominio;
	
	//METODOS//
	
	public Habitacion()
	{
		this.setCapacidad(0);
		this.setCategoria("");
		this.setCompartidaConCuantos(0);
		this.setDiasUsada(0);
		this.setId(0);
		this.setNumeroEstrellas(0);
		this.setNumeroHab(0);
		this.setPrecio(0);
		this.setServicios("");
		this.setTamano("");
		this.setIdDominio(0);
		this.setTipoDominio("");
	}
	
	public Habitacion( long id, int cap, int com, int dia, int est, int numh, double prec, 
			String serv, String tam, String cat, long idd, String tip)
	{
		this.setId(id);
		this.setCapacidad(cap);
		this.setCompartidaConCuantos(com);
		this.setDiasUsada(dia);
		this.setNumeroEstrellas(est);
		this.setNumeroHab(numh);
		this.setPrecio(prec);
		this.setServicios(serv);
		this.setTamano(tam);
		this.setCategoria(cat);
		this.setIdDominio(idd);
		this.setTipoDominio(tip);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	public int getCompartidaConCuantos() {
		return compartidaConCuantos;
	}

	public void setCompartidaConCuantos(int compartidaConCuantos) {
		this.compartidaConCuantos = compartidaConCuantos;
	}

	public int getDiasUsada() {
		return diasUsada;
	}

	public void setDiasUsada(int diasUsada) {
		this.diasUsada = diasUsada;
	}

	public int getNumeroEstrellas() {
		return numeroEstrellas;
	}

	public void setNumeroEstrellas(int numeroEstrellas) {
		this.numeroEstrellas = numeroEstrellas;
	}

	public int getNumeroHab() {
		return numeroHab;
	}

	public void setNumeroHab(int numeroHab) {
		this.numeroHab = numeroHab;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public String getServicios() {
		return servicios;
	}

	public void setServicios(String servicios) {
		this.servicios = servicios;
	}

	public String getTamano() {
		return tamano;
	}

	public void setTamano(String tamano) {
		this.tamano = tamano;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
	public long getIdDominio() {
		return idDominio;
	}

	public void setIdDominio(long idDominio) {
		this.idDominio = idDominio;
	}

	public String getTipoDominio() {
		return tipoDominio;
	}

	public void setTipoDominio(String tipoDominio) {
		this.tipoDominio = tipoDominio;
	}
	
	/** 
	 * @return Una cadena con la información básica
	 */
	@Override
	public String toString() 
	{
		return "Habitacion [id=" + id + ", capacidad=" + capacidad + ", compartidaConCuantos=" + compartidaConCuantos + 
				", diasUsada=" + diasUsada + "NumeroEstrellas=" + numeroEstrellas + 
				"numeroHab=" + numeroHab + "precio=" + precio + "servicios=" + servicios +
				"tamano=" + tamano + "categoria=" + categoria + ", idDominio=" + idDominio +
				", tipoDominio= " + tipoDominio+"]";
	}

}
