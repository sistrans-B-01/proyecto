package uniandes.isis2304.parranderos.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;


public class SQLUtil 
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
	public SQLUtil (PersistenciaAlohAndes pp)
	{
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para obtener un nuevo número de secuencia
	 * @param pm - El manejador de persistencia
	 * @return El número de secuencia generado
	 */
	public long nextval (PersistenceManager pm)
	{
        Query q = pm.newQuery(SQL, "SELECT "+ pp.darSeqAlohandes () + ".nextval FROM DUAL");
        q.setResultClass(Long.class);
        long resp = (long) q.executeUnique();
        return resp;
	}

	/**
	 * Crea y ejecuta las sentencias SQL para cada tabla de la base de datos - EL ORDEN ES IMPORTANTE 
	 * @param pm - El manejador de persistencia
	 * @return Un arreglo con 7 números que indican el número de tuplas borradas en las tablas GUSTAN, SIRVEN, VISITAN, BEBIDA,
	 * TIPOBEBIDA, BEBEDOR y BAR, respectivamente
	 */
	public long [] limpiaralohAndes (PersistenceManager pm)
	{
        Query qOfertaApartamento = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaOfertaApartamento());          
        Query qOfertaVivienda = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaOfertaVivienda());
        Query qOfertaHabitacion = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaOfertaHabitacion());
        Query qServiciosAdicionales = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaServiciosAdicionales());
        Query qReserva = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaReserva());
        Query qOferta = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaOferta ());
        Query qHabitacion = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaHabitacion ());
        Query qApartamento = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaApartamento ());
        Query qVivienda = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaVivienda ());
        Query qCliente = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaCliente ());
        Query qVecinos = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaVecinos ());
        Query qDominio = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaDominio ());
        Query qPropietario = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaPropietario ());

        
        long ofertaAptoEliminadas = (long) qOfertaApartamento.executeUnique ();
        long ofertaVivEliminadas = (long) qOfertaVivienda.executeUnique ();
        long ofertaHabiEliminadas = (long) qOfertaHabitacion.executeUnique ();
        long serviciosAdEliminadas = (long) qServiciosAdicionales.executeUnique ();
        long reservaEliminados = (long) qReserva.executeUnique ();
        long ofertaEliminados = (long) qOferta.executeUnique ();
        long habitacionEliminados = (long) qHabitacion.executeUnique ();
        long apartamentoEliminados = (long) qApartamento.executeUnique ();
        long viviendaEliminados = (long) qVivienda.executeUnique ();
        long clienteEliminados = (long) qCliente.executeUnique ();
        long vecinosEliminados = (long) qVecinos.executeUnique ();
        long dominioEliminados = (long) qDominio.executeUnique ();
        long propietarioEliminados = (long) qPropietario.executeUnique ();

        return new long[] {ofertaAptoEliminadas, ofertaVivEliminadas, ofertaHabiEliminadas, serviciosAdEliminadas, reservaEliminados,
        		ofertaEliminados, habitacionEliminados, apartamentoEliminados, viviendaEliminados, clienteEliminados, vecinosEliminados,
        		dominioEliminados, propietarioEliminados};
	}
}
