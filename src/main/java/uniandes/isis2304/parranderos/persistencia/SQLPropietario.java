package uniandes.isis2304.parranderos.persistencia;

import java.util.List; 

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Propietario;

public class SQLPropietario 
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
	public SQLPropietario (PersistenciaAlohAndes pp)
	{
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un PROPIETARIO a la base de datos de AlohAndes
	 * @return El número de tuplas insertadas
	 */
	public long adicionarPropietario (PersistenceManager pm, long numId, String nombre, String tipoId, String tipop) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaPropietario () + "(nombre, numeroidentificacion, tipoidentificacion, tipopropietario) values (?, ?, ?, ?)");
        q.setParameters(nombre, numId, tipoId, tipop);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar BARES de la base de datos de Parranderos, por su nombre
	 * @param pm - El manejador de persistencia
	 * @param nombreBar - El nombre del bar
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarPropietarioPorNumeroId (PersistenceManager pm, long numId)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaPropietario () + " WHERE numeroidentificacion = ?");
        q.setParameters(numId);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UN BAR de la 
	 * base de datos de Parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idBar - El identificador del bar
	 * @return El objeto BAR que tiene el identificador dado
	 */
	public Propietario darPropietarioPorNumId (PersistenceManager pm, long numId) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaPropietario () + " WHERE numeroidentificacion = ?");
		q.setResultClass(Propietario.class);
		q.setParameters(numId);
		return (Propietario) q.executeUnique();
	}
	
	public List<Propietario> darPropietariosPorTipoId (PersistenceManager pm, String tipoId) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaPropietario () + " WHERE tipoidentificacion = ?");
		q.setResultClass(Propietario.class);
		q.setParameters(tipoId);
		return (List<Propietario>) q.executeList();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS BARES de la 
	 * base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos BAR
	 */
	public List<Propietario> darPropietarios (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaPropietario ());
		q.setResultClass(Propietario.class);
		return (List<Propietario>) q.executeList();
	}
}
