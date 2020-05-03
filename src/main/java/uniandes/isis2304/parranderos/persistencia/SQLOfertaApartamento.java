package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Apartamento;
import uniandes.isis2304.parranderos.negocio.OfertaApartamento;


public class SQLOfertaApartamento 
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
	public SQLOfertaApartamento (PersistenciaAlohAndes pp)
	{
		this.pp = pp;
	}

	public long adicionarOfertaApartamento (PersistenceManager pm, long ido, String ida) 
	{
		Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaOfertaApartamento () + "(idoferta, idapartamento) values (?, ?)");
		q.setParameters(ido, ida);
		return (long) q.executeUnique();
	}

	public long eliminarOfertaApartamento (PersistenceManager pm, long ido, String ida)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaOfertaApartamento () + " WHERE idoferta = ? AND idapartamento = ?");
		q.setParameters(ido, ida);
		return (long) q.executeUnique();
	}

	public List<Object> consultarOfertasDisponiblesApartamento(PersistenceManager pm, int numeroOfertasPedidas, String serviciosSolicitados)
	{

		String sql= "SELECT *";
		sql += "FROM " + pp.darTablaOfertaApartamento () +" OFAP, "+ pp.darTablaOferta () +" OFE, "+ pp.darTablaApartamento () +" AP ";
		sql += "WHERE OFAP.IDOFERTA = OFE.ID ";
		sql += "AND OFAP.IDAPARTAMENTO = AP.UBICACION  ";
		sql += "AND AP.SERVICIOS = ? ";
		sql += "AND ROWNUM <= ?";
		
		
		Query q = pm.newQuery(SQL, sql);
		q.setParameters(serviciosSolicitados,numeroOfertasPedidas);
		return (List<Object>) q.executeList();

	}

	public List<Apartamento> darApartamentos (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaApartamento ());
		q.setResultClass(Apartamento.class);
		return (List<Apartamento>) q.executeList();
	}

}
