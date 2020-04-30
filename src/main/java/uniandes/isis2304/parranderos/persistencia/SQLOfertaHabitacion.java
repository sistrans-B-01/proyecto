package uniandes.isis2304.parranderos.persistencia;


import javax.jdo.PersistenceManager;
import javax.jdo.Query;

public class SQLOfertaHabitacion 
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
	public SQLOfertaHabitacion (PersistenciaAlohAndes pp)
	{
		this.pp = pp;
	}
	
	public long adicionarOfertaHabitacion (PersistenceManager pm, long ido, long idh) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaOfertaHabitacion () + "(idoferta, idhabitacion) values (?, ?)");
        q.setParameters(ido, idh);
        return (long) q.executeUnique();
	}
	
	public long eliminarOfertaHabitacion (PersistenceManager pm, long ido, long idh)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaOfertaHabitacion () + " WHERE idoferta = ? AND idhabitacion = ?");
        q.setParameters(ido, idh);
        return (long) q.executeUnique();
	}
}
