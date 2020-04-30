package uniandes.isis2304.parranderos.negocio;

public class OfertaApartamento implements VOOfertaApartamento
{
	//ATRIBUTOS//
	
    private long idOferta;
	private String idApartamento; //el id de apartamento es la ubicacion de esta
		
	//METODOS//
		
	public OfertaApartamento()
	{
		this.idOferta=0;
		this.idApartamento="";
	}
		
	public OfertaApartamento(long ido, String ida)
	{
		this.idOferta=ido;
		this.idApartamento=ida;
	}

	public long getIdOferta() {
		return idOferta;
	}

	public void setIdOferta(long idOferta) {
		this.idOferta = idOferta;
	}

	public String getIdApartamento() {
		return idApartamento;
	}

	public void setIdApartamento(String idApartamento) {
		this.idApartamento = idApartamento;
	}

	/** 
	* @return Una cadena con la información básica
	*/
	@Override
	public String toString() 
	{
		return "OfertaHabitacion [idOferta=" + idOferta + ", idApartamento=" + idApartamento + "]";
	}
}
