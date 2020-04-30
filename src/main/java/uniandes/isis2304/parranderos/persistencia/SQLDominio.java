package uniandes.isis2304.parranderos.persistencia;

import java.util.List;  

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Dominio;

public class SQLDominio 
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
	public SQLDominio (PersistenciaAlohAndes pp)
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
	public long adicionarDominio (PersistenceManager pm, long reg, String nombre, String tipo, String horario, String ubi) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaDominio () + "(registro, tiporegistro, nombre, horario, ubicacion) values (?, ?, ?, ?, ?)");
        q.setParameters(reg, tipo, nombre, horario, ubi);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar BARES de la base de datos de Parranderos, por su nombre
	 * @param pm - El manejador de persistencia
	 * @param nombreBar - El nombre del bar
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarDominioPorRegistro (PersistenceManager pm, long reg)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaDominio () + " WHERE registro = ?");
        q.setParameters(reg);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UN BAR de la 
	 * base de datos de Parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idBar - El identificador del bar
	 * @return El objeto BAR que tiene el identificador dado
	 */
	public Dominio darDominioPorRegistro (PersistenceManager pm, long reg) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaDominio () + " WHERE id = ?");
		q.setResultClass(Dominio.class);
		q.setParameters(reg);
		return (Dominio) q.executeUnique();
	}
	
	public List<Dominio> darDominiosPorTipoId(PersistenceManager pm, String tipoId)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaDominio () + " WHERE tiporegistro = ?");
		q.setResultClass(Dominio.class);
		q.setParameters(tipoId);
		return (List<Dominio>) q.executeList();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS BARES de la 
	 * base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos BAR
	 */
	public List<Dominio> darDominios (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaDominio ());
		q.setResultClass(Dominio.class);
		return (List<Dominio>) q.executeList();
	}
	
	public List<Object> darDineroRecibidoPorProveedor(PersistenceManager pm)
	{
		String sql = "SELECT DO.NOMBRE, PRO.NOMBRE, VEC.NOMBRE, SUM(RES.COSTOPAGADO";
		sql +=       " FROM " + pp.darTablaDominio() + " DO, " + pp.darTablaPropietario() + " PRO, " +pp.darTablaVecinos() + " VEC, " + pp.darTablaHabitacion() + " HAB, "+ pp.darTablaVivienda() + " VIV, " + pp.darTablaApartamento()+ " APA, " + pp.darTablaOferta() + " OFE, " + pp.darTablaOfertaHabitacion() + " OFEHAB, "+ pp.darTablaOfertaVivienda()+ " OFEVIV, "+ pp.darTablaOfertaApartamento()+ " OFEAPA, "+ pp.darTablaReserva() + " RES";
		sql +=       " WHERE ";
		sql +=            " ((DO.REGISTRO = HAB.IDDOMINIO AND DO.TIPOREGISTRO = = HAB.TIPOREGDOMINIO)";
		sql +=             " OR (PRO.NUMEROIDENTIFICACION = APA.IDPROPIETARIO AND PRO.TIPOIDENTIFICACION = APA.TIPOIDENTIFICACION)";
		sql +=             " OR (VEC.NUMEROIDENTIFICACION = VIV.IDVECINO AND VEC.TIPOIDENTIFICACION = VIV.TIPOIDENTIFICACION))";
		sql +=            " AND";
		sql +=            " ((HAB.ID= OFEHAB.IDHABITACION AND OFEHAB.IDHABITACION = OFE.ID)";
		sql +=             " OR(APA.UBICACION = OFEAPA.IDAPARTAMENTO AND OFEAPA.IDAPARTAMENTO = OFE.ID)";
		sql +=             " OR(VIV.UBICACION = OFEVIV.IDVIVIENDA AND OFEVIV.IDVIVIENDA = OFE.ID))";
		sql +=            " AND";
		sql +=            " OFE.ID= RES.IDOFERTA";
		sql +=       " GROUP BY DO.NOMBRE, PRO.NOMBRE, VEC.NOMBRE";
		Query q = pm.newQuery(SQL, sql);
		return q.executeList();
	}
}
