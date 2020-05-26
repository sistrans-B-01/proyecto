package uniandes.isis2304.parranderos.persistencia;

import java.util.List; 

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Cliente;

public class SQLCliente 
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
	public SQLCliente (PersistenciaAlohAndes pp)
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
	public long adicionarCliente (PersistenceManager pm, String nombre, long numId, String tipoId, String tipoc) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaCliente () + "(nombre, numeroidentificacion, tipoidentificacion, tipocliente) values (?, ?, ?, ?)");
        q.setParameters(nombre, numId, tipoId, tipoc);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar BARES de la base de datos de Parranderos, por su nombre
	 * @param pm - El manejador de persistencia
	 * @param nombreBar - El nombre del bar
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarClientePorNumeroId (PersistenceManager pm, long numId)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaCliente () + " WHERE numeroidentificacion = ?");
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
	public Cliente darClientePorNumeroId (PersistenceManager pm, long numId) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaCliente () + " WHERE numeroidentificacion = ?");
		q.setResultClass(Cliente.class);
		q.setParameters(numId);
		return (Cliente) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS BARES de la 
	 * base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos BAR
	 */
	public List<Cliente> darCliente (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaCliente ());
		q.setResultClass(Cliente.class);
		return (List<Cliente>) q.executeList();
	}
	
	public List<Object> darInfoGeneral ( PersistenceManager pm)
	{
		String sql= "SELECT CLI.NOMBRE, CLI.TIPOCLIENTE, SUM(RES.COSTOPAGADO) AS DINEROPAGADO, (RES.FECHAIDA - RES.FECHALLEGADA) AS DIAS, COUNT(RES.ID) AS RESERVAS";
		      sql+= " FROM " + pp.darTablaCliente() + " CLI, " + pp.darTablaReserva() + " RES";
		      sql+= " WHERE( CLI.NUMEROIDENTIFICACION = RES.IDCLIENTE)";
		      sql+= " GROUP BY CLI.NOMBRE, CLI.TIPOCLIENTE, RES.FECHALLEGADA, RES.FECHAIDA";
		Query q = pm.newQuery(SQL, sql);
	    return q.executeList();
	}
	
	public List<Object> darConsumoAdministrador( PersistenceManager pm)
	{
		String sql= "select 'HABITACION' as ALOJAMIENTO, cliente.*, oferta.id as idOferta, count(reserva.id) as reservas";
		      sql+= " from " + pp.darTablaCliente()+ ", " + pp.darTablaOferta()+ ", " + pp.darTablaReserva()+ ", " + pp.darTablaOfertaHabitacion();
		      sql+= " where oferta.id= reserva.idoferta and cliente.numeroidentificacion= reserva.idcliente";
		      sql+= " and reserva.fechallegada BETWEEN '01-ENE-2019' and '20-ENE-2019'";
		      sql+= " and ofertahabitacion.idoferta= oferta.id";
		      sql+= " group by oferta.id, cliente.numeroidentificacion, cliente.tipocliente, cliente.tipoidentificacion, cliente.nombre";
		      sql+= " UNION";
		      sql+= " select 'VIVIENDA' as ALOJAMIENTO, cliente.*, oferta.id as idOferta, count(reserva.id) as reservas";
		      sql+= " from "+ pp.darTablaCliente()+ ", " + pp.darTablaOferta()+ ", " + pp.darTablaReserva()+ ", " + pp.darTablaOfertaVivienda();
		      sql+= " where oferta.id= reserva.idoferta and cliente.numeroidentificacion= reserva.idcliente";
		      sql+= " and reserva.fechallegada BETWEEN '01-ENE-2019' and '20-ENE-2019'";
		      sql+= " and ofertavivienda.idoferta= oferta.id";
		      sql+= " group by oferta.id, cliente.numeroidentificacion, cliente.tipocliente, cliente.tipoidentificacion, cliente.nombre";
		      sql+= " UNION";
		      sql+= " select 'APARTAMENTO' as ALOJAMIENTO, cliente.*, oferta.id as idOferta, count(reserva.id) as reservas";
		      sql+= " from "+ pp.darTablaCliente()+ ", " + pp.darTablaOferta()+ ", " + pp.darTablaReserva()+ ", " + pp.darTablaOfertaApartamento();
		      sql+= " where oferta.id= reserva.idoferta and cliente.numeroidentificacion= reserva.idcliente";
		      sql+= " and reserva.fechallegada BETWEEN '01-ENE-2019' and '20-ENE-2019'";
		      sql+= " and ofertaapartamento.idoferta= oferta.id";
		      sql+= " group by oferta.id, cliente.numeroidentificacion, cliente.tipocliente, cliente.tipoidentificacion, cliente.nombre";
		Query q = pm.newQuery(SQL, sql);
		return q.executeList(); 
	}
	
	public List<Object> darConsumoCliente( PersistenceManager pm, long numClien)
	{
		String sql= "select 'HABITACION' as ALOJAMIENTO, cliente.*, oferta.id as idOferta, count(reserva.id) as reservas";
		      sql+= " from " + pp.darTablaCliente()+ ", " + pp.darTablaOferta()+ ", " + pp.darTablaReserva()+ ", " + pp.darTablaOfertaHabitacion();
		      sql+= " where oferta.id= reserva.idoferta and cliente.numeroidentificacion= ? and cliente.numeroidentificacion= reserva.idcliente";
		      sql+= " and reserva.fechallegada BETWEEN '01-ENE-2019' and '20-ENE-2019'";
		      sql+= " and ofertahabitacion.idoferta= oferta.id";
		      sql+= " group by oferta.id, cliente.numeroidentificacion, cliente.tipocliente, cliente.tipoidentificacion, cliente.nombre";
		      sql+= " UNION";
		      sql+= " select 'VIVIENDA' as ALOJAMIENTO, cliente.*, oferta.id as idOferta, count(reserva.id) as reservas";
		      sql+= " from "+ pp.darTablaCliente()+ ", " + pp.darTablaOferta()+ ", " + pp.darTablaReserva()+ ", " + pp.darTablaOfertaVivienda();
		      sql+= " where oferta.id= reserva.idoferta and cliente.numeroidentificacion= ? and cliente.numeroidentificacion= reserva.idcliente";
		      sql+= " and reserva.fechallegada BETWEEN '01-ENE-2019' and '20-ENE-2019'";
		      sql+= " and ofertavivienda.idoferta= oferta.id";
		      sql+= " group by oferta.id, cliente.numeroidentificacion, cliente.tipocliente, cliente.tipoidentificacion, cliente.nombre";
		      sql+= " UNION";
		      sql+= " select 'APARTAMENTO' as ALOJAMIENTO, cliente.*, oferta.id as idOferta, count(reserva.id) as reservas";
		      sql+= " from "+ pp.darTablaCliente()+ ", " + pp.darTablaOferta()+ ", " + pp.darTablaReserva()+ ", " + pp.darTablaOfertaApartamento();
		      sql+= " where oferta.id= reserva.idoferta and cliente.numeroidentificacion= ? and cliente.numeroidentificacion= reserva.idcliente";
		      sql+= " and reserva.fechallegada BETWEEN '01-ENE-2019' and '20-ENE-2019'";
		      sql+= " and ofertaapartamento.idoferta= oferta.id";
		      sql+= " group by oferta.id, cliente.numeroidentificacion, cliente.tipocliente, cliente.tipoidentificacion, cliente.nombre";
		Query q = pm.newQuery(SQL, sql);
		q.setParameters(numClien, numClien, numClien);
		return q.executeList(); 
	}
	
	public List<Object> darNoConsumoAdministrador( PersistenceManager pm)
	{
		String sql= "select 'HABITACION' as ALOJAMIENTO, cliente.*, oferta.id as idOferta, count(reserva.id) as reservas";
		      sql+= " from " + pp.darTablaCliente()+ ", " + pp.darTablaOferta()+ ", " + pp.darTablaReserva()+ ", " + pp.darTablaOfertaHabitacion();
		      sql+= " where oferta.id= reserva.idoferta and cliente.numeroidentificacion= reserva.idcliente";
		      sql+= " and reserva.fechallegada NOT BETWEEN '11-ENE-2020' and '20-ENE-2020'";
		      sql+= " and ofertahabitacion.idoferta= oferta.id";
		      sql+= " group by oferta.id, cliente.numeroidentificacion, cliente.tipocliente, cliente.tipoidentificacion, cliente.nombre";
		      sql+= " UNION";
		      sql+= " select 'VIVIENDA' as ALOJAMIENTO, cliente.*, oferta.id as idOferta, count(reserva.id) as reservas";
		      sql+= " from "+ pp.darTablaCliente()+ ", " + pp.darTablaOferta()+ ", " + pp.darTablaReserva()+ ", " + pp.darTablaOfertaVivienda();
		      sql+= " where oferta.id= reserva.idoferta and cliente.numeroidentificacion= reserva.idcliente";
		      sql+= " and reserva.fechallegada NOT BETWEEN '11-ENE-2020' and '20-ENE-2020'";
		      sql+= " and ofertavivienda.idoferta= oferta.id";
		      sql+= " group by oferta.id, cliente.numeroidentificacion, cliente.tipocliente, cliente.tipoidentificacion, cliente.nombre";
		      sql+= " UNION";
		      sql+= " select 'APARTAMENTO' as ALOJAMIENTO, cliente.*, oferta.id as idOferta, count(reserva.id) as reservas";
		      sql+= " from "+ pp.darTablaCliente()+ ", " + pp.darTablaOferta()+ ", " + pp.darTablaReserva()+ ", " + pp.darTablaOfertaApartamento();
		      sql+= " where oferta.id= reserva.idoferta and cliente.numeroidentificacion= reserva.idcliente";
		      sql+= " and reserva.fechallegada NOT BETWEEN '11-ENE-2020' and '20-ENE-2020'";
		      sql+= " and ofertaapartamento.idoferta= oferta.id";
		      sql+= " group by oferta.id, cliente.numeroidentificacion, cliente.tipocliente, cliente.tipoidentificacion, cliente.nombre";
		Query q = pm.newQuery(SQL, sql);
		return q.executeList(); 
	}
	
	public List<Object> darNoConsumoCliente( PersistenceManager pm, long numClien)
	{
		String sql= "select 'HABITACION' as ALOJAMIENTO, cliente.*, oferta.id as idOferta, count(reserva.id) as reservas";
		      sql+= " from " + pp.darTablaCliente()+ ", " + pp.darTablaOferta()+ ", " + pp.darTablaReserva()+ ", " + pp.darTablaOfertaHabitacion();
		      sql+= " where oferta.id= reserva.idoferta and cliente.numeroidentificacion= ? and cliente.numeroidentificacion= reserva.idcliente";
		      sql+= " and reserva.fechallegada NOT BETWEEN '11-ENE-2020' and '20-ENE-2020'";
		      sql+= " and ofertahabitacion.idoferta= oferta.id";
		      sql+= " group by oferta.id, cliente.numeroidentificacion, cliente.tipocliente, cliente.tipoidentificacion, cliente.nombre";
		      sql+= " UNION";
		      sql+= " select 'VIVIENDA' as ALOJAMIENTO, cliente.*, oferta.id as idOferta, count(reserva.id) as reservas";
		      sql+= " from "+ pp.darTablaCliente()+ ", " + pp.darTablaOferta()+ ", " + pp.darTablaReserva()+ ", " + pp.darTablaOfertaVivienda();
		      sql+= " where oferta.id= reserva.idoferta and cliente.numeroidentificacion= ? and cliente.numeroidentificacion= reserva.idcliente";
		      sql+= " and reserva.fechallegada NOT BETWEEN '11-ENE-2020' and '20-ENE-2020'";
		      sql+= " and ofertavivienda.idoferta= oferta.id";
		      sql+= " group by oferta.id, cliente.numeroidentificacion, cliente.tipocliente, cliente.tipoidentificacion, cliente.nombre";
		      sql+= " UNION";
		      sql+= " select 'APARTAMENTO' as ALOJAMIENTO, cliente.*, oferta.id as idOferta, count(reserva.id) as reservas";
		      sql+= " from "+ pp.darTablaCliente()+ ", " + pp.darTablaOferta()+ ", " + pp.darTablaReserva()+ ", " + pp.darTablaOfertaApartamento();
		      sql+= " where oferta.id= reserva.idoferta and cliente.numeroidentificacion= ? and cliente.numeroidentificacion= reserva.idcliente";
		      sql+= " and reserva.fechallegada NOT BETWEEN '11-ENE-2020' and '20-ENE-2020'";
		      sql+= " and ofertaapartamento.idoferta= oferta.id";
		      sql+= " group by oferta.id, cliente.numeroidentificacion, cliente.tipocliente, cliente.tipoidentificacion, cliente.nombre";
		Query q = pm.newQuery(SQL, sql);
		q.setParameters(numClien, numClien, numClien);
		return q.executeList(); 
	}
}
