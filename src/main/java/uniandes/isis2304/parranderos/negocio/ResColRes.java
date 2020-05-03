package uniandes.isis2304.parranderos.negocio;

public class ResColRes implements VOResColRes{
	
	private long idReserva;
	private long idReservaColectiva;
	
	public ResColRes() {
		this.idReserva = 0;
		this.idReservaColectiva = 0;
	}
	
	public ResColRes(long idReserva, long idReservaColectiva) {
		this.idReserva = idReserva;
		this.idReservaColectiva = idReservaColectiva;
	}

	public long getIdReserva() {
		return idReserva;
	}

	public void setIdReserva(long idReserva) {
		this.idReserva = idReserva;
	}

	public long getIdReservaColectiva() {
		return idReservaColectiva;
	}

	public void setIdReservaColectiva(long idReservaColectiva) {
		this.idReservaColectiva = idReservaColectiva;
	}

	

}
