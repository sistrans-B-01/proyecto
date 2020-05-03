package uniandes.isis2304.parranderos.persistencia;

import java.util.List; 

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Habitacion;


public class SQLHabitacion 
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
	public SQLHabitacion (PersistenciaAlohAndes pp)
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
	public long adicionarHbitacion (PersistenceManager pm, long idh, int capacidad, int con, int dias, int estrellas, int hab, double precio, String serv, String tam, String cat, long domId, String tipoId) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaHabitacion () + "(id, capacidad, compartidaconcuantos, diasusada, numeroestrellas, numerohab, precio, servicios, tamano, categoria, iddominio, tiporegdominio) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        q.setParameters(idh, capacidad, con, dias, estrellas, hab, precio, serv, tam, cat, domId, tipoId);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar UN BAR de la base de datos de Parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idBar - El identificador del bar
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarhabitacionPorId (PersistenceManager pm, long idh)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaHabitacion () + " WHERE id = ?");
        q.setParameters(idh);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UN BAR de la 
	 * base de datos de Parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idBar - El identificador del bar
	 * @return El objeto BAR que tiene el identificador dado
	 */
	public Habitacion darHabitacionPorId (PersistenceManager pm, long idh) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaHabitacion () + " WHERE id = ?");
		q.setResultClass(Habitacion.class);
		q.setParameters(idh);
		return (Habitacion) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS BARES de la 
	 * base de datos de Parranderos, por su nombre
	 * @param pm - El manejador de persistencia
	 * @param nombreBar - El nombre de bar buscado
	 * @return Una lista de objetos BAR que tienen el nombre dado
	 */
	public List<Habitacion> darHabitacionPorTipoDom (PersistenceManager pm, String tipoDom) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaHabitacion () + " WHERE tiporegdominio = ?");
		q.setResultClass(Habitacion.class);
		q.setParameters(tipoDom);
		return (List<Habitacion>) q.executeList();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS BARES de la 
	 * base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos BAR
	 */
	public List<Habitacion> darHabitaciones (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaHabitacion ());
		q.setResultClass(Habitacion.class);
		return (List<Habitacion>) q.executeList();
	}
	
	public List<Object[]> darAnalisisOperacion( PersistenceManager pm)
	{
		String sql= "SELECT 'HABITACION' AS ALOJAMIENTO, MAX(SUMAS) AS MAXIMO, MEJORPAGADA, MAX(OCU) AS MAXOCU, MEJOROCUPA";
		      sql+= " FROM( SELECT SUM(RES.COSTOPAGADO) AS SUMAS, RES.FECHADEPAGO AS MEJORPAGADA, COUNT(disponible) AS OCU, RES.FECHALLEGADA AS MEJOROCUPA";
		      sql+= " FROM " + pp.darTablaOfertaHabitacion() + " OFEHAB, " + pp.darTablaOferta() + " OFE, " + pp.darTablaReserva() + " RES";
		      sql+= " WHERE( OFEHAB.IDOFERTA= OFE.ID AND RES.IDOFERTA= OFE.ID AND ofe.disponible='N')";
		      sql+= " GROUP BY RES.FECHADEPAGO, res.fechallegada";
		      sql+= " HAVING(RES.FECHADEPAGO <= '10-ENE-2020' AND RES.FECHADEPAGO>= '28-DIC-2019'))";
		      sql+= " WHERE ROWNUM <=5";
		      sql+= " GROUP BY MEJORPAGADA, MEJOROCUPA";
		      sql+= " UNION";
		      sql+= " SELECT 'VIVIENDA' AS ALOJAMIENTO, MAX(SUMAS) AS MAXIMO, MEJORPAGADA, MAX(OCU) AS MAXOCU, MEJOROCUPA";
		      sql+= " FROM( SELECT SUM(RES.COSTOPAGADO) AS SUMAS, RES.FECHADEPAGO AS MEJORPAGADA, COUNT(disponible) AS OCU, res.fechallegada AS MEJOROCUPA";
		      sql+= " FROM " + pp.darTablaOfertaVivienda() + " OFEVIV, " + pp.darTablaOferta() + " OFE, " + pp.darTablaReserva() + " RES";
		      sql+= " WHERE (OFEVIV.IDOFERTA= OFE.ID AND RES.IDOFERTA= OFE.ID AND ofe.disponible='N')";
		      sql+= " GROUP BY RES.FECHADEPAGO, res.fechallegada";
		      sql+= " HAVING(RES.FECHADEPAGO <= '10-ENE-2020' AND RES.FECHADEPAGO>= '28-DIC-2019'))";
		      sql+= " WHERE ROWNUM<= 5";
		      sql+= " GROUP BY MEJORPAGADA, MEJOROCUPA";
		      sql+= " UNION";
		      sql+= " SELECT 'APARTAMENTO' AS ALOJAMIENTO, MAX(SUMAS) AS MAXIMO, MEJORPAGADA, MAX(OCU) AS MAXOCU, MEJOROCUPA";
		      sql+= " FROM( SELECT SUM(RES.COSTOPAGADO) AS SUMAS, RES.FECHADEPAGO AS MEJORPAGADA, COUNT(disponible) AS OCU, res.fechallegada AS MEJOROCUPA";
		      sql+= " FROM " + pp.darTablaOfertaApartamento() + " OFEAPT, " + pp.darTablaOferta() + " OFE, " + pp.darTablaReserva() + " RES";
		      sql+= " WHERE (OFEAPT.IDOFERTA= OFE.ID AND RES.IDOFERTA= OFE.ID AND ofe.disponible='N')";
		      sql+= " GROUP BY RES.FECHADEPAGO, res.fechallegada";
		      sql+= " HAVING(RES.FECHADEPAGO <= '10-ENE-2020' AND RES.FECHADEPAGO>= '28-DIC-2019'))";
		      sql+= " WHERE ROWNUM<= 5";
		      sql+= " GROUP BY MEJORPAGADA, MEJOROCUPA";
		      sql+= " ORDER BY MAXIMO DESC";
		Query q = pm.newQuery(SQL, sql);
	    return q.executeList();
	}
}
