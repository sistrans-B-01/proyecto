package uniandes.isis2304.parranderos.negocio;

public class OfertaVivienda implements VOOfertaVivienda
{

	//ATRIBUTOS//
	
    private long idOferta;
	private String idVivienda; //el id de vivienda es la ubicacion de esta
		
	//METODOS//
		
	public OfertaVivienda()
	{
		this.idOferta=0;
		this.idVivienda="";
	}
		
	public OfertaVivienda(long ido, String idv)
	{
		this.idOferta=ido;
		this.idVivienda=idv;
	}

	public long getIdOferta() {
		return idOferta;
	}

	public void setIdOferta(long idOferta) {
		this.idOferta = idOferta;
	}

	public String getIdVivienda() {
		return idVivienda;
	}

	public void setIdVivienda(String idVivienda) {
		this.idVivienda = idVivienda;
	}

	/** 
	* @return Una cadena con la información básica
	*/
	@Override
	public String toString() 
	{
		return "OfertaHabitacion [idOferta=" + idOferta + ", idVivienda=" + idVivienda + "]";
	}
}
