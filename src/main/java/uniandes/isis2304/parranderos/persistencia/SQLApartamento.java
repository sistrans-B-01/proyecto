package uniandes.isis2304.parranderos.persistencia;

import java.util.List;   

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Apartamento;


public class SQLApartamento {
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
	public SQLApartamento (PersistenciaAlohAndes pp)
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
	public long adicionarApartamento (PersistenceManager pm, String ubicacion, String servicios, long propietario, int numHabitaciones, String tipoId) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaApartamento() + "(numeroHabitaciones, servicios, ubicacion, idpropietario, tipoidentificacion) values (?, ?, ?, ?, ?)");
        q.setParameters(numHabitaciones, servicios, ubicacion, propietario, tipoId);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar BARES de la base de datos de Parranderos, por su nombre
	 * @param pm - El manejador de persistencia
	 * @param nombreBar - El nombre del bar
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarApartamentoPorUbicacion (PersistenceManager pm, String ubicacion)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaApartamento () + " WHERE ubicacion = ?");
        q.setParameters(ubicacion);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar UN BAR de la base de datos de Parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idBar - El identificador del bar
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarApartamentoPorIdPropietario (PersistenceManager pm, long idPropietario)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaApartamento () + " WHERE idpropietario = ?");
        q.setParameters(idPropietario);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UN BAR de la 
	 * base de datos de Parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idBar - El identificador del bar
	 * @return El objeto BAR que tiene el identificador dado
	 */
	public Apartamento darApartamentoPorUbicacion (PersistenceManager pm, String ubi) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaApartamento () + " WHERE ubicacion = ?");
		q.setResultClass(Apartamento.class);
		q.setParameters(ubi);
		return (Apartamento) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS BARES de la 
	 * base de datos de Parranderos, por su nombre
	 * @param pm - El manejador de persistencia
	 * @param nombreBar - El nombre de bar buscado
	 * @return Una lista de objetos BAR que tienen el nombre dado
	 */
	public List<Apartamento> darApartamentoPorTipoIdProp (PersistenceManager pm, String tipoId) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaApartamento () + " WHERE tipoidentificacion = ?");
		q.setResultClass(Apartamento.class);
		q.setParameters(tipoId);
		return (List<Apartamento>) q.executeList();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS BARES de la 
	 * base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos BAR
	 */
	public List<Apartamento> darApartamentos (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaApartamento ());
		q.setResultClass(Apartamento.class);
		return (List<Apartamento>) q.executeList();
	}
	
	public List<Object[]> darAlojamentoFiltrado( PersistenceManager pm)
	{
		String sql = "SELECT *";
		sql +=      " FROM " + pp.darTablaOferta() + " OFE, " + pp.darTablaOfertaHabitacion() + " OFEHAB, " + pp.darTablaOfertaVivienda() + " OFEVIV, "+ pp.darTablaApartamento()+ " OFEAPA, "+ pp.darTablaApartamento() + " APA, " + pp.darTablaVivienda() + " VIV, "+ pp.darTablaHabitacion() + "HAB";
		sql +=      " WHERE FECHAFIN <='20/03/2020' AND FECHAINICIO >='19/03/2020'";
		sql +=      " AND (OFE.ID = OFEHAB.IDOFERTA";
		sql +=           " OR OFE.ID = OFEVIV.IDOFERTA";
		sql +=           " OR OFE.ID = OFEAPA.IDOFERTA)";
		sql +=      " AND (OFEHAB.IDHABITACION = HAB.ID";
		sql +=           " OR OFEAPA.IDAPARTAMENTO = APA.UBICACION";
		sql +=           " OR OFEVIV.IDVIVIENDA = VIV.UBICACION)";
		sql +=      " AND (HAB.SERVICIOS = 'LUZ, AGUA'";
		sql +=           " OR VIV.SERVICIOS = 'LUZ, AGUA'";
		sql +=           " OR APA.SERVICIOS = 'LUZ, AGUA')";
		Query q = pm.newQuery(SQL, sql);
		return q.executeList();
	}
}

