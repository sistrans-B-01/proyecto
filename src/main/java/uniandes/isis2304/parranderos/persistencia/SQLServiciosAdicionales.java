package uniandes.isis2304.parranderos.persistencia;

import java.util.List; 

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.ServiciosAdicionales;


public class SQLServiciosAdicionales 
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
	public SQLServiciosAdicionales (PersistenciaAlohAndes pp)
	{
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un BAR a la base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @param idBar - El identificador del bar
	 * @param nombre - El nombre del bar
	 * @param ciudad - La ciudad del bar
	 * @param presupuesto - El presupuesto del bar (ALTO, MEDIO, BAJO)
	 * @param sedes - El número de sedes del bar
	 * @return El número de tuplas insertadas
	 */
	public long adicionarServicio (PersistenceManager pm, long ids, double cos, String nombre, long ido) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaServiciosAdicionales () + "(id, costo, nombre, idoferta) values (?, ?, ?, ?)");
        q.setParameters(ids, cos, nombre, ido);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar BARES de la base de datos de Parranderos, por su nombre
	 * @param pm - El manejador de persistencia
	 * @param nombreBar - El nombre del bar
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarServiciosPorId (PersistenceManager pm, long ids)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaServiciosAdicionales () + " WHERE id = ?");
        q.setParameters(ids);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UN BAR de la 
	 * base de datos de Parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idBar - El identificador del bar
	 * @return El objeto BAR que tiene el identificador dado
	 */
	public ServiciosAdicionales darServicioPorId (PersistenceManager pm, long ids) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaServiciosAdicionales () + " WHERE id = ?");
		q.setResultClass(ServiciosAdicionales.class);
		q.setParameters(ids);
		return (ServiciosAdicionales) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS BARES de la 
	 * base de datos de Parranderos, por su nombre
	 * @param pm - El manejador de persistencia
	 * @param nombreBar - El nombre de bar buscado
	 * @return Una lista de objetos BAR que tienen el nombre dado
	 */
	public List<ServiciosAdicionales> darServiciosPorOferta (PersistenceManager pm, long ido) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaServiciosAdicionales () + " WHERE idoferta = ?");
		q.setResultClass(ServiciosAdicionales.class);
		q.setParameters(ido);
		return (List<ServiciosAdicionales>) q.executeList();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS BARES de la 
	 * base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos BAR
	 */
	public List<ServiciosAdicionales> darServicios (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaServiciosAdicionales ());
		q.setResultClass(ServiciosAdicionales.class);
		return (List<ServiciosAdicionales>) q.executeList();
	}
}
