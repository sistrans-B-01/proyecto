package uniandes.isis2304.parranderos.negocio;

public class Vivienda implements VOVivienda
{

	//ATRIBUTOS//
	
	private String ubicacion; //PK
	private String caracteristicasSeguro;
	private String menaje;
	private int numeroDiasUsada;
	private int numeroHabitaciones;
	private String serviciosPublicos;
	private long idVecino;
	private String tipoIdVecino;
	
	//METODOS//
	
	public Vivienda()
	{
		this.ubicacion="";
		this.caracteristicasSeguro="";
		this.menaje="";
		this.numeroDiasUsada=0;
		this.numeroHabitaciones=0;
		this.serviciosPublicos="";
		this.idVecino=0;
		this.setTipoIdVecino("");
	}
	
	public Vivienda( String ubi, String cara, String men, int dias, int num,
			String serv, long prop, String tipo)
	{
		this.ubicacion=ubi;
		this.caracteristicasSeguro=cara;
		this.menaje=men;
		this.numeroDiasUsada=dias;
		this.numeroHabitaciones= num;
		this.serviciosPublicos=serv;
		this.idVecino=prop;
		this.setTipoIdVecino(tipo);
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public String getCaracteristicasSeguro() {
		return caracteristicasSeguro;
	}

	public void setCaracteristicasSeguro(String caracteristicasSeguro) {
		this.caracteristicasSeguro = caracteristicasSeguro;
	}

	public String getMenaje() {
		return menaje;
	}

	public void setMenaje(String menaje) {
		this.menaje = menaje;
	}

	public int getNumeroDiasUsada() {
		return numeroDiasUsada;
	}

	public void setNumeroDiasUsada(int numeroDiasUsada) {
		this.numeroDiasUsada = numeroDiasUsada;
	}

	public int getNumeroHabitaciones() {
		return numeroHabitaciones;
	}

	public void setNumeroHabitaciones(int numeroHabitaciones) {
		this.numeroHabitaciones = numeroHabitaciones;
	}

	public String getServiciosPublicos() {
		return serviciosPublicos;
	}

	public void setServiciosPublicos(String serviciosPublicos) {
		this.serviciosPublicos = serviciosPublicos;
	}

	public long getIdVecino() {
		return idVecino;
	}

	public void setIdVecino(long propietario) {
		this.idVecino = propietario;
	}
	
	public String getTipoIdVecino() {
		return tipoIdVecino;
	}

	public void setTipoIdVecino(String tipoIdVecino) {
		this.tipoIdVecino = tipoIdVecino;
	}

	/** 
	 * @return Una cadena con la información básica
	 */
	@Override
	public String toString() 
	{
		return "Vivienda [ubicacion=" + ubicacion + ", caracteristicasSeguro=" + caracteristicasSeguro + 
				", menaje=" + menaje + ", numeroDiasUsada=" + numeroDiasUsada + ", numeroHabitaciones" +
				numeroHabitaciones + ", serviciosPublicos=" + serviciosPublicos + ", idVecino="
				+ idVecino + ", tipoIdVecino=" + tipoIdVecino +"]";
	}
}
