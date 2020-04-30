package uniandes.isis2304.parranderos.persistencia;

import java.util.List; 

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Reserva;

import java.sql.Timestamp;


public class SQLReserva 
{

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
	public SQLReserva (PersistenciaAlohAndes pp)
	{
		this.pp = pp;
	}
	
	public long adicionarReserva (PersistenceManager pm, long idr, double costoP, double costoT, Timestamp ida, Timestamp lle, String tiem, long idc, String tipoc, long ido) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaReserva () + "(id, costopagado, costototal, fechaida, fechallegada, tiempoalojamiento, idcliente, tipoidcliente, idoferta) values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
        q.setParameters(idr, costoP, costoT, ida, lle, tiem, idc, tipoc, ido);
        return (long) q.executeUnique();
	}

	public long eliminarReservaPorId (PersistenceManager pm, long idr)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaReserva () + " WHERE id = ?");
        q.setParameters(idr);
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
