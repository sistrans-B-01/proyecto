package uniandes.isis2304.parranderos.persistencia;

import java.util.List; 

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Vivienda;


public class SQLVivienda 
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
	public SQLVivienda (PersistenciaAlohAndes pp)
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
	public long adicionarVivienda (PersistenceManager pm, String cara, String menaje, int dias, int num, String serv, String ubi, long vecId, String tipoId) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaVivienda () + "(caracteristicasseguro, menaje, numerodiasusada, numerohabitaciones, servicios, ubicacion, idvecino, tipoidentificacion) values (?, ?, ?, ?, ?, ?, ?, ?)");
        q.setParameters(cara, menaje, dias, num, serv, ubi, vecId, tipoId);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar BARES de la base de datos de Parranderos, por su nombre
	 * @param pm - El manejador de persistencia
	 * @param nombreBar - El nombre del bar
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarViviendaPorUbicacion (PersistenceManager pm, String ubicacion)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaVivienda () + " WHERE ubicacion = ?");
        q.setParameters(ubicacion);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UN BAR de la 
	 * base de datos de Parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idBar - El identificador del bar
	 * @return El objeto BAR que tiene el identificador dado
	 */
	public Vivienda darViviendaPorUbicacion (PersistenceManager pm, String ubi) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaVivienda () + " WHERE ubicacion = ?");
		q.setResultClass(Vivienda.class);
		q.setParameters(ubi);
		return (Vivienda) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS BARES de la 
	 * base de datos de Parranderos, por su nombre
	 * @param pm - El manejador de persistencia
	 * @param nombreBar - El nombre de bar buscado
	 * @return Una lista de objetos BAR que tienen el nombre dado
	 */
	public List<Vivienda> darViviendasPorVecTipoId (PersistenceManager pm, String tip) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaVivienda () + " WHERE tipoidentificacion = ?");
		q.setResultClass(Vivienda.class);
		q.setParameters(tip);
		return (List<Vivienda>) q.executeList();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS BARES de la 
	 * base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos BAR
	 */
	public List<Vivienda> darViviendas (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaVivienda ());
		q.setResultClass(Vivienda.class);
		return (List<Vivienda>) q.executeList();
	}
}
