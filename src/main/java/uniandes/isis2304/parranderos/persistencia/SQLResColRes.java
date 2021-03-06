package uniandes.isis2304.parranderos.persistencia;

import java.sql.Timestamp;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Reserva;

public class SQLResColRes {
	
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
	public SQLResColRes (PersistenciaAlohAndes pp)
	{
		this.pp = pp;
	}
	
	public long adicionarResColRes (PersistenceManager pm, long idRes, long idResCol) 
	{
		
		System.out.println( pp.darTablaResColRes() );
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaResColRes () + "(IDRESERVA, IDRESERVACOLECTIVA) values (?, ?)");
        q.setParameters(idRes, idResCol);
        System.out.println("preexecute");
        return (long) q.executeUnique();
	}
	
	public long eliminarResColResId (PersistenceManager pm, long idResCol)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaResColRes() + " WHERE IDRESERVACOLECTIVA = ?");
		q.setParameters(idResCol);
		return (long) q.executeUnique();
	}
	
	public List<Object> darReservaPorId (PersistenceManager pm, long idResCol) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaResColRes() + " WHERE IDRESERVACOLECTIVA = ?");
		System.out.println("CORE01");
		q.setParameters(idResCol);
		return  q.executeList();
	}

	public List<Object> darClientesHabituales (PersistenceManager pm) 
	{
		String sql=	"";
		sql+=" SELECT SEL1.IDCLIENTE, SEL1.TIPOIDCLIENTE, CLI.NOMBRE, CLI.TIPOCLIENTE ";
		sql+=" FROM (";
		sql+=" SELECT IDCLIENTE, TIPOIDCLIENTE, COUNT(IDCLIENTE) ";
		sql+=" FROM RESERVA ";
		sql+=" GROUP BY IDCLIENTE, TIPOIDCLIENTE, IDOFERTA ";
		sql+=" HAVING COUNT(IDCLIENTE)>=3)SEL1, ";
		sql+=" (SELECT IDCLIENTE, TIPOIDCLIENTE ";
		sql+=" FROM RESERVA";
		sql+=" WHERE FECHAIDA-FECHALLEGADA <= 15) SEL2, CLIENTE CLI ";
		sql+=" WHERE SEL1.IDCLIENTE = SEL2.IDCLIENTE ";
		sql+=" AND SEL1.TIPOIDCLIENTE = SEL2.TIPOIDCLIENTE ";
		sql+=" AND SEL1.IDCLIENTE = CLI.NUMEROIDENTIFICACION ";
		sql+=" AND SEL1.TIPOIDCLIENTE= CLI.TIPOIDENTIFICACION ";
		sql+=" GROUP BY SEL1.IDCLIENTE, SEL1.TIPOIDCLIENTE, CLI.NOMBRE, CLI.TIPOCLIENTE ";

		Query q = pm.newQuery(SQL, sql);
		System.out.println("CORE01");
		return  q.executeList();
	}


}
