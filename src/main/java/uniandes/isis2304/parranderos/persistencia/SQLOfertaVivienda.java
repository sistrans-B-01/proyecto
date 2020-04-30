package uniandes.isis2304.parranderos.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

public class SQLOfertaVivienda 
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
	public SQLOfertaVivienda (PersistenciaAlohAndes pp)
	{
		this.pp = pp;
	}
	
	public long adicionarOfertaVivienda (PersistenceManager pm, long ido, String idv) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaOfertaVivienda () + "(idoferta, idvivienda) values (?, ?)");
        q.setParameters(ido, idv);
        return (long) q.executeUnique();
	}
	
	public long eliminarOfertaVivienda (PersistenceManager pm, long ido, String idv)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaOfertaVivienda () + " WHERE idoferta = ? AND idvivienda = ?");
        q.setParameters(ido, idv);
        return (long) q.executeUnique();
	}
}
