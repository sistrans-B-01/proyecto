package uniandes.isis2304.parranderos.persistencia;

import java.util.List; 

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Oferta;

import java.sql.Timestamp;



public class SQLOferta 
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
	public SQLOferta (PersistenciaAlohAndes pp)
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
	public long adicionarOferta (PersistenceManager pm, long ido, int des, int dias, int usada, Timestamp fin, Timestamp lle, String tiem, int numSem, String activa, String disponible) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaOferta () + "(id, descuento, diasactiva, diasusada, fechafin, fechainicio, tiempocontrato, numerosemana ,activa, disponible) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        q.setParameters(ido, des, dias, usada, fin, lle, tiem, numSem,activa, disponible);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar BARES de la base de datos de Parranderos, por su nombre
	 * @param pm - El manejador de persistencia
	 * @param nombreBar - El nombre del bar
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarOfertaPorId (PersistenceManager pm, long ido)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaOferta () + " WHERE id = ?");
        q.setParameters(ido);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UN BAR de la 
	 * base de datos de Parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idBar - El identificador del bar
	 * @return El objeto BAR que tiene el identificador dado
	 */
	public Oferta darOfertaPorId (PersistenceManager pm, long ido) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaOferta () + " WHERE id = ?");
		q.setResultClass(Oferta.class);
		q.setParameters(ido);
		return (Oferta) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS BARES de la 
	 * base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos BAR
	 */
	public List<Oferta> darOfertas (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaOferta ());
		q.setResultClass(Oferta.class);
		return (List<Oferta>) q.executeList();
	}
	
	public List<Object> dar20ofertasMasPopulares( PersistenceManager pm)
	{
		String sql= "SELECT *";
		sql += " FROM " + "( SELECT * ";
		sql +=           " FROM " + pp.darTablaOferta() + " OFE";
		sql +=                  " INNER JOIN " + pp.darTablaOfertaHabitacion() + " OFEHAB ON OFE.ID = OFEHAB.IDOFERTA";
		sql +=                  " INNER JOIN " + pp.darTablaOfertaApartamento() + " OFEAPA ON OFE.ID = OFEAPA.IDOFERTA";
		sql +=                  " INNER JOIN " + pp.darTablaOfertaVivienda() + " OFEVIV ON OFE.ID = OFEVIV.IDOFERTA";
		sql +=                  " INNER JOIN (";
		sql +=                      " SELECT OFE.ID, COUNT(RES.ID) AS VECESCOMPRADA";
		sql +=                        " FROM " + pp.darTablaReserva() + " RES, " + pp.darTablaOferta() + " OFE";
		sql +=                        " WHERE OFE.ID = RES.IDOFERTA";
		sql +=                        " GROUP BY OFE.ID)SEL1";
		sql +=                  " ON SEL1.ID = OFE.ID";
		sql +=           " ORDER BY SEL1.VECESCOMPRADA)";
		sql += " WHERE ROWNUM > 20";
		Query q = pm.newQuery(SQL, sql);
		return q.executeList();
	}
	
	public long actulizarOfertaActiva( PersistenceManager pm, long ido)
	{
		String sql= "update " + pp.darTablaOferta();
		      sql+= " set oferta.activa= '0'";
		      sql+= " where oferta.id= ?";
		Query q = pm.newQuery(SQL, sql);
		q.setParameters(ido);
		return (long) q.executeUnique();      
	}
	
	public List<Object> darIndiceDeOcupacion( PersistenceManager pm)
	{
		String sql = "SELECT ID, DIASACTIVA,DIASUSADA, ROUND(DIASUSADA/DIASACTIVA,2) AS INDICEUSO";
		sql +=       " FROM " + pp.darTablaOferta();
		sql +=       " WHERE DIASACTIVA !=0";
		Query q = pm.newQuery(SQL, sql);
		return q.executeList();
	}
	
	public List<Object[]> darReservasPorCambiar( PersistenceManager pm) 
	{
		String sql= "select distinct oferta.id as ofertas, reserva.id as cambiar";
		      sql+= " from " + pp.darTablaOferta() + ", " + pp.darTablaReserva();
		      sql+= " where(oferta.id in (select distinct oferta.id";
		      sql+= " from " + pp.darTablaOferta() + ", " + pp.darTablaReserva();
		      sql+= " where (oferta.id!= reserva.idoferta";
		      sql+= " and oferta.disponible= 'Y'";
		      sql+= " and reserva.id in( select reserva.id";
		      sql+= " from " + pp.darTablaOferta() + ", " + pp.darTablaReserva();
		      sql+= " where (reserva.idoferta=oferta.id and oferta.activa='0'))";
		      sql+= " and oferta.fechainicio= reserva.fechallegada))";
		      sql+= " and reserva.id in ( select distinct reserva.id";
		      sql+= " from " + pp.darTablaOferta() + ", " + pp.darTablaReserva();
		      sql+= " where (reserva.idoferta=oferta.id and oferta.activa='0')))";
		      Query q = pm.newQuery(SQL, sql);
				return q.executeList();      
	}
	
	public List<Object[]> darReservaPorCambiar( PersistenceManager pm) 
	{
		String sql= "select distinct oferta.id as ofertas, reserva.id as cambiar";
		      sql+= " from " + pp.darTablaOferta() + ", " + pp.darTablaReserva();
		      sql+= " where(oferta.id in (select distinct oferta.id";
		      sql+= " from " + pp.darTablaOferta() + ", " + pp.darTablaReserva();
		      sql+= " where (oferta.id!= reserva.idoferta";
		      sql+= " and oferta.disponible= 'Y'";
		      sql+= " and reserva.id in( select reserva.id";
		      sql+= " from " + pp.darTablaOferta() + ", " + pp.darTablaReserva();
		      sql+= " where (reserva.idoferta=oferta.id and oferta.activa='0'))";
		      sql+= " and oferta.fechainicio= reserva.fechallegada and rownum =1))";
		      sql+= " and reserva.id in ( select distinct reserva.id";
		      sql+= " from " + pp.darTablaOferta() + ", " + pp.darTablaReserva();
		      sql+= " where (reserva.idoferta=oferta.id and oferta.activa='0' and rownum =1)))";
		      Query q = pm.newQuery(SQL, sql);
				return q.executeList();      
	}
	
	public long actualizarReservas(PersistenceManager pm)
	{
		String sql= " update " + pp.darTablaReserva();
		      sql+= " set reserva.idoferta=( select distinct oferta.id";
		      sql+= " from "+ pp.darTablaOferta() +" , " + pp.darTablaReserva();
		      sql+= " where (oferta.id!= reserva.idoferta";
		      sql+= " and oferta.disponible= 'Y'";
		      sql+= " and reserva.id in( select reserva.id";
		      sql+= " from " + pp.darTablaOferta() + ", " + pp.darTablaReserva();
		      sql+= " where (reserva.idoferta=oferta.id and oferta.activa='0'))";
		      sql+= " and oferta.fechainicio= reserva.fechallegada and rownum =1))";
		      sql+= " where reserva.id in ( select reserva.id";
		      sql+= " from "+ pp.darTablaOferta() +" , " + pp.darTablaReserva();
		      sql+= " where (reserva.idoferta=oferta.id and oferta.activa='0' and rownum =1))";
		      Query q = pm.newQuery(SQL, sql);
		      return (long) q.executeUnique(); 
	}
	
	public long actualizarOfertaDisponible( PersistenceManager pm)
	{
		String sql= "update " + pp.darTablaOferta();
		      sql+= " set oferta.disponible = 'N'";
		      sql+= " where id in ( select distinct oferta.id";
		      sql+= " from " + pp.darTablaOferta() + ", " + pp.darTablaReserva();
		      sql+= " where (oferta.id= reserva.idoferta and oferta.disponible= 'Y'";
		      sql+= " and oferta.fechainicio= reserva.fechallegada and rownum =1))";
		      Query q = pm.newQuery(SQL, sql);
		      return (long) q.executeUnique(); 
	}
	
	public long eliminarReservaSinOferta( PersistenceManager pm)
	{
		String sql= "delete from " + pp.darTablaReserva();
		      sql+= " where id in ( select distinct reserva.id";
		      sql+= " from " + pp.darTablaOferta() + ", " + pp.darTablaReserva();
		      sql+= " where ( reserva.idoferta = oferta.id and oferta.activa='0' and rownum=1";
		      Query q = pm.newQuery(SQL, sql);
		      return (long) q.executeUnique();
	}
	
	public long actualizarOfertaDesactiva( PersistenceManager pm, long ido)
	{
		String sql= "update " + pp.darTablaOferta();
		      sql+= " set oferta.activa = '1'";
		      sql+= " where oferta.id = ?";
		Query q = pm.newQuery(SQL, sql);
	    q.setParameters(ido);
		return (long) q.executeUnique();
	}
	
	public List<Object> darOfertasMenorDemanda( PersistenceManager pm)
	{
		String sql= "select distinct oferta.id";
		      sql+= " from " + pp.darTablaOferta() + ", " + pp.darTablaReserva();
		      sql+= " where reserva.idoferta = oferta.id and reserva.fechaida <= '01-ENE-2020'";
		      sql+= " or oferta.id not in (select oferta.id from " + pp.darTablaOferta() + ", " + pp.darTablaReserva() + " where reserva.idoferta = oferta.id";
		Query q = pm.newQuery(SQL, sql);
	    return q.executeList();       
	}
	
	public List<Object> darRecordsSemanalesOfertas( PersistenceManager pm)
	{
		String sql= "SELECT OFE2.ID,OFE2.DESCUENTO,OFE2.DIASACTIVA,OFE2.DIASUSADA,OFE2.FECHAFIN,OFE2.FECHAINICIO,OFE2.TIEMPOCONTRATO,OFE2.NUMEROSEMANA,OFE2.ACTIVA,OFE2.DISPONIBLE\r\n" + 
				"FROM \r\n" + 
				"(SELECT OFE.ID, OFE.NUMEROSEMANA,SUM(OFE.DIASUSADA) AS TOTALDIASACTIVA\r\n" + 
				"FROM OFERTA OFE, RESERVA RES\r\n" + 
				"WHERE OFE.ID = RES.IDOFERTA\r\n" + 
				"GROUP BY OFE.ID, OFE.NUMEROSEMANA\r\n" + 
				") SEL1,\r\n" + 
				"OFERTA OFE2\r\n" + 
				"WHERE SEL1.ID = OFE2.ID\r\n" + 
				"ORDER BY SEL1.NUMEROSEMANA DESC, SEL1.TOTALDIASACTIVA DESC\r\n" + 
				";";
		Query q = pm.newQuery(SQL, sql);
	    return q.executeList();       
	}
	
	public List<Object> darRecordsSemanalesOperadores( PersistenceManager pm)
	{
		String sql= "SELECT DOM2.REGISTRO, DOM2.TIPOREGISTRO, DOM2.NOMBRE, DOM2.HORARIO, DOM2.UBICACION, SEL2.NUMEROSEMANA, SEL2.NUMSOLICITUDES\r\n" + 
				"FROM(\r\n" + 
				"    SELECT DOM.REGISTRO, DOM.TIPOREGISTRO, OFER.NUMEROSEMANA, COUNT(DOM.REGISTRO) AS NUMSOLICITUDES\r\n" + 
				"    FROM DOMINIO DOM, HABITACION HAB, OFERTAHABITACION OFEHAB, OFERTA OFER, RESERVA RES\r\n" + 
				"    WHERE DOM.REGISTRO = HAB.IDDOMINIO\r\n" + 
				"    AND DOM.TIPOREGISTRO = HAB.TIPOREGDOMINIO\r\n" + 
				"    AND HAB.ID = OFEHAB.IDHABITACION\r\n" + 
				"    AND OFEHAB.IDOFERTA = OFER.ID\r\n" + 
				"    AND OFER.ID = RES.IDOFERTA\r\n" + 
				"    GROUP BY DOM.REGISTRO, DOM.TIPOREGISTRO, OFER.NUMEROSEMANA) SEL2,\r\n" + 
				"    DOMINIO DOM2\r\n" + 
				"WHERE SEL2.REGISTRO = DOM2.REGISTRO\r\n" + 
				"AND SEL2.TIPOREGISTRO = DOM2.REGISTRO\r\n" + 
				"ORDER BY SEL2.NUMEROSEMANA, SEL2.NUMSOLICITUDES\r\n" + 
				";";
		Query q = pm.newQuery(SQL, sql);
	    return q.executeList();       
	}
	
	public List<Object> darBuenosClientes( PersistenceManager pm)
	{
		String sql= "-- SELECCIONAR LOS DE ALMENOS UNA VEZ POR MES\r\n" + 
				"SELECT RES.IDCLIENTE, RES.TIPOIDCLIENTE, COUNT (DISTINCT EXTRACT(MONTH FROM FECHALLEGADA)) NUMRESERVASDISTINTOSMESES,  0 AS TOTALCOST, 0 AS TOTALRES\r\n" + 
				"FROM RESERVA RES\r\n" + 
				"GROUP BY IDCLIENTE,TIPOIDCLIENTE, EXTRACT (YEAR FROM FECHALLEGADA)\r\n" + 
				"HAVING COUNT(DISTINCT EXTRACT(MONTH FROM FECHALLEGADA)) >12\r\n" + 
				"UNION\r\n" + 
				"--SELECCIONAR LOS DE MÁS DE 150\r\n" + 
				"SELECT RES.IDCLIENTE, RES.TIPOIDCLIENTE, 0, SUM(RES.COSTOTOTAL) AS TOTALPAGADO,  0\r\n" + 
				"FROM RESERVA RES \r\n" + 
				"WHERE COSTOTOTAL > 150\r\n" + 
				"GROUP BY IDCLIENTE,TIPOIDCLIENTE\r\n" + 
				"UNION\r\n" + 
				"--SELECCIONAR SELECCIONAR LOS DE SUITE\r\n" + 
				"SELECT RES.IDCLIENTE, RES.TIPOIDCLIENTE, 0, 0, COUNT(DISTINCT RES.ID) AS TOTALRESERVAS\r\n" + 
				"FROM RESERVA RES, OFERTAHABITACION OFHA, HABITACION HAB\r\n" + 
				"WHERE RES.IDOFERTA = OFHA.IDOFERTA\r\n" + 
				"AND OFHA.IDHABITACION = HAB.ID\r\n" + 
				"AND HAB.CATEGORIA = 'SUIT'\r\n" + 
				"GROUP BY IDCLIENTE,TIPOIDCLIENTE;";
		Query q = pm.newQuery(SQL, sql);
	    return q.executeList();       
	}
	
}
