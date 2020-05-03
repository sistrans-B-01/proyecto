package uniandes.isis2304.parranderos.persistencia;

import java.sql.Timestamp;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Reserva;

public class SQLReservaColectiva {


	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Cadena que representa el tipo de consulta que se va a realizar en las sentencias de acceso a la base de datos
	 * Se renombra acá para facilitar la escritura de las sentencias
	 */
	private final static String SQL = PersistenciaAlohAndes.SQL;

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El manejador de persistencia general de la aplicación
	 */
	private PersistenciaAlohAndes pp;

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/

	/**
	 * Constructor
	 * @param pp - El Manejador de persistencia de la aplicación
	 */
	public SQLReservaColectiva (PersistenciaAlohAndes pp)
	{
		this.pp = pp;
	}

	public long registrarReservaColectiva (PersistenceManager pm, long idresCol, String tipoc, long idc,  Timestamp lle, Timestamp feIda, Timestamp  fePago, int cantidadRes, String tipoAlojamiento, double costo) 
	{
			
		//		cerrar el trato
		Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaReservaColectiva() + "(id, TIPOIDCLIENTE, IDCLIENTE, FECHALLEGADA, FECHAIDA, FECHADEPAGO, CANTIDAD, TIPOALOJAMIENTO, COSTO) values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
		q.setParameters(idresCol, tipoc, idc,  lle, feIda, fePago, cantidadRes, tipoAlojamiento, costo);
		return (long) q.executeUnique();
	}

	public long eliminarReservaColectivaPorId (PersistenceManager pm, long idResCol)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaReservaColectiva () + " WHERE id = ?");
		q.setParameters(idResCol);
		return (long) q.executeUnique();
	}

	public Reserva darRservaPorId (PersistenceManager pm, long idr) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaReserva () + " WHERE id = ?");
		q.setResultClass(Reserva.class);
		q.setParameters(idr);
		return (Reserva) q.executeUnique();
	}

	public List<Reserva> darReservaPorIdClie (PersistenceManager pm, long idc) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaReserva () + " WHERE idcliente = ?");
		q.setResultClass(Reserva.class);
		q.setParameters(idc);
		return (List<Reserva>) q.executeList();
	}

	public List<Reserva> darReservas (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaReserva ());
		q.setResultClass(Reserva.class);
		return (List<Reserva>) q.executeList();
	}

}
