package uniandes.isis2304.parranderos.persistencia;

import java.util.ArrayList;
import java.util.LinkedList;  
import java.util.List;

import javax.jdo.JDODataStoreException;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import java.math.BigDecimal;
import java.sql.Timestamp;


import org.apache.log4j.Logger;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sun.jdi.LongValue;

import uniandes.isis2304.parranderos.negocio.Apartamento;
import uniandes.isis2304.parranderos.negocio.Cliente;
import uniandes.isis2304.parranderos.negocio.Dominio;
import uniandes.isis2304.parranderos.negocio.Habitacion;
import uniandes.isis2304.parranderos.negocio.Oferta;
import uniandes.isis2304.parranderos.negocio.OfertaApartamento;
import uniandes.isis2304.parranderos.negocio.OfertaHabitacion;
import uniandes.isis2304.parranderos.negocio.OfertaVivienda;
import uniandes.isis2304.parranderos.negocio.Propietario;
import uniandes.isis2304.parranderos.negocio.Reserva;
import uniandes.isis2304.parranderos.negocio.ReservaColectiva;
import uniandes.isis2304.parranderos.negocio.ServiciosAdicionales;
import uniandes.isis2304.parranderos.negocio.Vecinos;
import uniandes.isis2304.parranderos.negocio.Vivienda;


public class PersistenciaAlohAndes 
{

	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(PersistenciaAlohAndes.class.getName());
	
	/**
	 * Cadena para indicar el tipo de sentencias que se va a utilizar en una consulta
	 */
	public final static String SQL = "javax.jdo.query.SQL";

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * Atributo privado que es el único objeto de la clase - Patrón SINGLETON
	 */
	private static PersistenciaAlohAndes instance;
	
	/**
	 * Fábrica de Manejadores de persistencia, para el manejo correcto de las transacciones
	 */
	private PersistenceManagerFactory pmf;
	
	/**
	 * Arreglo de cadenas con los nombres de las tablas de la base de datos, en su orden:
	 * Secuenciador, tipoBebida, bebida, bar, bebedor, gustan, sirven y visitan
	 */
	private List <String> tablas;
	
	/**
	 * Atributo para el acceso a las sentencias SQL propias a PersistenciaParranderos
	 */
	private SQLUtil sqlUtil;
	
	/**
	 * Atributo para el acceso a la tabla TIPOBEBIDA de la base de datos
	 */
	private SQLApartamento sqlApartamento;
	
	/**
	 * Atributo para el acceso a la tabla BEBIDA de la base de datos
	 */
	private SQLPropietario sqlPropietario;
	
	/**
	 * Atributo para el acceso a la tabla BAR de la base de datos
	 */
	private SQLCliente sqlCliente;
	
	/**
	 * Atributo para el acceso a la tabla BEBIDA de la base de datos
	 */
	private SQLVecinos sqlVecinos;
	
	/**
	 * Atributo para el acceso a la tabla GUSTAN de la base de datos
	 */
	private SQLVivienda sqlVivienda;
	
	/**
	 * Atributo para el acceso a la tabla SIRVEN de la base de datos
	 */
	private SQLHabitacion sqlHabitacion;
	
	/**
	 * Atributo para el acceso a la tabla VISITAN de la base de datos
	 */
	private SQLServiciosAdicionales sqlServiciosAdicionales;
	
	private SQLReserva sqlReserva;
	
	private SQLOferta sqlOferta;
	
	private SQLOfertaHabitacion sqlOfertaHabitacion;
	
	private SQLOfertaApartamento sqlOfertaApartamento;
	
	private SQLOfertaVivienda sqlOfertaVivienda;
	
	private SQLDominio sqlDominio;
	
	private SQLReservaColectiva sqlReservaColectiva;
	
	private SQLResColRes sqlResColRes;
	
	
	
	/* ****************************************************************
	 * 			Métodos del MANEJADOR DE PERSISTENCIA
	 *****************************************************************/

	/**
	 * Constructor privado con valores por defecto - Patrón SINGLETON
	 */
	private PersistenciaAlohAndes ()
	{
		pmf = JDOHelper.getPersistenceManagerFactory("AlohAndes");		
		crearClasesSQL ();
		
		// Define los nombres por defecto de las tablas de la base de datos
		tablas = new LinkedList<String> ();
		tablas.add ("Alohandes_sequence");
		tablas.add ("PROPIETARIO");
		tablas.add ("DOMINIO");
		tablas.add ("VECINOS");
		tablas.add ("CLIENTE");
		tablas.add ("APARTAMENTO");
		tablas.add ("VIVIENDA");
		tablas.add ("HABITACION");
		tablas.add ("OFERTA");
		tablas.add ("RESERVA");
		tablas.add ("SERVICIOSADICIONALES");
		tablas.add ("OFERTAHABITACION");
		tablas.add ("OFERTAVIVIENDA");
		tablas.add ("OFERTAAPARTAMENTO");
		tablas.add ("RESERVACOLECTIVA");
		tablas.add ("RESCOLRES");
		
}

	/**
	 * Constructor privado, que recibe los nombres de las tablas en un objeto Json - Patrón SINGLETON
	 * @param tableConfig - Objeto Json que contiene los nombres de las tablas y de la unidad de persistencia a manejar
	 */
	private PersistenciaAlohAndes (JsonObject tableConfig)
	{
		crearClasesSQL ();
		tablas = leerNombresTablas (tableConfig);
		
		String unidadPersistencia = tableConfig.get ("unidadPersistencia").getAsString ();
		log.trace ("Accediendo unidad de persistencia: " + unidadPersistencia);
		pmf = JDOHelper.getPersistenceManagerFactory (unidadPersistencia);
	}

	/**
	 * @return Retorna el único objeto PersistenciaParranderos existente - Patrón SINGLETON
	 */
	public static PersistenciaAlohAndes getInstance ()
	{
		if (instance == null)
		{
			instance = new PersistenciaAlohAndes ();
		}
		return instance;
	}
	
	/**
	 * Constructor que toma los nombres de las tablas de la base de datos del objeto tableConfig
	 * @param tableConfig - El objeto JSON con los nombres de las tablas
	 * @return Retorna el único objeto PersistenciaParranderos existente - Patrón SINGLETON
	 */
	public static PersistenciaAlohAndes getInstance (JsonObject tableConfig)
	{
		if (instance == null)
		{
			instance = new PersistenciaAlohAndes (tableConfig);
		}
		return instance;
	}

	/**
	 * Cierra la conexión con la base de datos
	 */
	public void cerrarUnidadPersistencia ()
	{
		pmf.close ();
		instance = null;
	}
	
	/**
	 * Genera una lista con los nombres de las tablas de la base de datos
	 * @param tableConfig - El objeto Json con los nombres de las tablas
	 * @return La lista con los nombres del secuenciador y de las tablas
	 */
	private List <String> leerNombresTablas (JsonObject tableConfig)
	{
		JsonArray nombres = tableConfig.getAsJsonArray("tablas") ;

		List <String> resp = new LinkedList <String> ();
		for (JsonElement nom : nombres)
		{
			resp.add (nom.getAsString ());
		}
		
		return resp;
	}
	
	/**
	 * Crea los atributos de clases de apoyo SQL
	 */
	private void crearClasesSQL ()
	{
		sqlPropietario = new SQLPropietario(this);
		sqlDominio = new SQLDominio(this);
		sqlVecinos = new SQLVecinos(this);
		sqlCliente = new SQLCliente(this);
		sqlApartamento = new SQLApartamento(this);
		sqlVivienda = new SQLVivienda (this);
		sqlHabitacion = new SQLHabitacion(this);	
		sqlOferta = new SQLOferta(this);		
		sqlReserva = new SQLReserva(this);		
		sqlServiciosAdicionales = new SQLServiciosAdicionales(this);		
		sqlOfertaHabitacion = new SQLOfertaHabitacion(this);		
		sqlOfertaVivienda = new SQLOfertaVivienda(this);		
		sqlOfertaApartamento = new SQLOfertaApartamento(this);		
		sqlReservaColectiva = new SQLReservaColectiva(this);		
		sqlResColRes = new SQLResColRes(this);		

		sqlUtil = new SQLUtil(this);
	}

	/**
	 * @return La cadena de caracteres con el nombre del secuenciador de parranderos
	 */
	public String darSeqAlohandes ()
	{
		return tablas.get (0);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de TipoBebida de parranderos
	 */
	public String darTablaPropietario ()
	{
		return tablas.get (1);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Bebida de parranderos
	 */
	public String darTablaDominio ()
	{
		return tablas.get (2);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Bar de parranderos
	 */
	public String darTablaVecinos ()
	{
		return tablas.get (3);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Bebedor de parranderos
	 */
	public String darTablaCliente ()
	{
		return tablas.get (4);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Gustan de parranderos
	 */
	public String darTablaApartamento ()
	{
		return tablas.get (5);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Sirven de parranderos
	 */
	public String darTablaVivienda ()
	{
		return tablas.get (6);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Visitan de parranderos
	 */
	public String darTablaHabitacion ()
	{
		return tablas.get (7);
	}
	
	public String darTablaOferta()
	{
		return tablas.get(8);
	}
	
	public String darTablaReserva()
	{
		return tablas.get(9);
	}
	
	public String darTablaServiciosAdicionales()
	{
		return tablas.get(10);
	}
	
	public String darTablaOfertaHabitacion()
	{
		return tablas.get(11);
	}
	
	public String darTablaOfertaVivienda()
	{
		return tablas.get(12);
	}
	
	public String darTablaOfertaApartamento()
	{
		return tablas.get(13);
	}
	public String darTablaReservaColectiva()
	{
		return tablas.get(14);
	}
	public String darTablaResColRes()
	{
		return tablas.get(15);
	}
	
	/**
	 * Transacción para el generador de secuencia de Parranderos
	 * Adiciona entradas al log de la aplicación
	 * @return El siguiente número del secuenciador de Parranderos
	 */
	private long nextval ()
	{
        long resp = sqlUtil.nextval (pmf.getPersistenceManager());
        log.trace ("Generando secuencia: " + resp);
        return resp;
    }
	
	/**
	 * Extrae el mensaje de la exception JDODataStoreException embebido en la Exception e, que da el detalle específico del problema encontrado
	 * @param e - La excepción que ocurrio
	 * @return El mensaje de la excepción JDO
	 */
	private String darDetalleException(Exception e) 
	{
		String resp = "";
		if (e.getClass().getName().equals("javax.jdo.JDODataStoreException"))
		{
			JDODataStoreException je = (javax.jdo.JDODataStoreException) e;
			return je.getNestedExceptions() [0].getMessage();
		}
		return resp;
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar los PROPIETARIO
	 *****************************************************************/

	public Propietario adicionarPropietario(String nombre, long numeroId, String tipoId, String tipop) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long tuplasInsertadas = sqlPropietario.adicionarPropietario(pm, numeroId, nombre, tipoId, tipop);
            tx.commit();

            log.trace ("Inserción de Propietario: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");

            return new Propietario(nombre, numeroId, tipoId, tipop);
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}

	public long eliminarPropietarioPorNumeroid (long numId) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlPropietario.eliminarPropietarioPorNumeroId(pm, numId);
            tx.commit();

            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}

	public List<Propietario> darPropietarios ()
	{
		return sqlPropietario.darPropietarios(pmf.getPersistenceManager());
	}

	public List<Propietario> darPropietariosPorTipoId (String tipoid)
	{
		return sqlPropietario.darPropietariosPorTipoId(pmf.getPersistenceManager(), tipoid);
	}

	public Propietario darPropietarioPorNumeroId (long idp)
	{
		return sqlPropietario.darPropietarioPorNumId(pmf.getPersistenceManager(), idp);
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar los Dominios
	 *****************************************************************/

	public Dominio adicionarDominio(long reg, String tipoReg, String nombre, String hor, String ubi) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long tuplasInsertadas = sqlDominio.adicionarDominio(pm, reg, nombre, tipoReg, hor, ubi);
            tx.commit();

            log.trace ("Inserción de Dominio: " + reg + ": " + tuplasInsertadas + " tuplas insertadas");

            return new Dominio(reg, tipoReg, nombre, ubi, hor);
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}

	public long eliminarDominioPorRegistro (long numId) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlDominio.eliminarDominioPorRegistro(pm, numId);
            tx.commit();

            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}

	public List<Dominio> darDominio ()
	{
		return sqlDominio.darDominios(pmf.getPersistenceManager());
	}

	public List<Dominio> darDominiosPorTipoId (String tipoid)
	{
		return sqlDominio.darDominiosPorTipoId(pmf.getPersistenceManager(), tipoid);
	}

	public Dominio darDominioPorRegistro (long reg)
	{
		return sqlDominio.darDominioPorRegistro(pmf.getPersistenceManager(),  reg);
	}
	
	public List<Object []> darDineroRecibidoPorProveedores ( )
	{
		List<Object []> dineros = new LinkedList <Object []> ();
		List<Object> tuplas= sqlDominio.darDineroRecibidoPorProveedor(pmf.getPersistenceManager());
		for (Object tupla : tuplas)
		{
			Object [] datos = (Object []) tupla;
			String nombre = (String) datos [0];
			double dineroP = ((BigDecimal) datos [1]).doubleValue();
			
			Object [] dinero = new Object [2];
			dinero[0] = nombre;
			dinero [1] = dineroP;

			dineros.add (dinero);
		}
		return dineros;
	}
 
	/* ****************************************************************
	 * 			Métodos para manejar los Vecinos
	 *****************************************************************/

	public Vecinos adicionarVecino(String nombre, long numeroId, String tipoId) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long tuplasInsertadas = sqlVecinos.adicionarVecio(pm, nombre, numeroId, tipoId);
            tx.commit();

            log.trace ("Inserción de Vecino: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");

            return new Vecinos(nombre, numeroId, tipoId);
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}

	public long eliminarVecinoPorNumeroid (long numId) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlVecinos.eliminarVecinoPorNumeroId(pm, numId);
            tx.commit();

            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}

	public List<Vecinos> darVecinos ()
	{
		return sqlVecinos.darVecinos(pmf.getPersistenceManager());
	}
 
	public Vecinos darVecinoPorNumeroId (long idv)
	{
		return sqlVecinos.darVecinoPorId(pmf.getPersistenceManager(), idv);
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar los cliente
	 *****************************************************************/

	public Cliente adicionarCliente(String nombre, long numeroId, String tipoId, String tipoc) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long tuplasInsertadas = sqlCliente.adicionarCliente(pm, nombre, numeroId, tipoId, tipoc);
            tx.commit();

            log.trace ("Inserción de Cliente: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");

            return new Cliente(numeroId, tipoId, nombre, tipoc);
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}

	public long eliminarClientePorNumeroid (long numId) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlCliente.eliminarClientePorNumeroId(pm, numId);
            tx.commit();

            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}

	public List<Cliente> darCliente ()
	{
		return sqlCliente.darCliente(pmf.getPersistenceManager());
	}

	public Cliente darClientePorNumeroId (long idc)
	{
		return sqlCliente.darClientePorNumeroId(pmf.getPersistenceManager(), idc);
	}
	
	public List<Object[ ]> darInfoGeneral()
	{
		List<Object []> info = new LinkedList <Object []> ();
		List<Object> tuplas= sqlCliente.darInfoGeneral(pmf.getPersistenceManager());
		for (Object tupla : tuplas)
		{
			Object [] datos = (Object []) tupla;
			String nombre = (String) datos [0];
			String tipoCliente= (String) datos[1];
			long dineroPagado = ((BigDecimal) datos[2]).longValue();
			int dias = ((BigDecimal) datos[3]).intValue();
			int reservas = ((BigDecimal) datos[4]).intValue();
			
			Object [] indiceO = new Object [5];
			indiceO[0] = nombre;
			indiceO [1] = tipoCliente;
			indiceO[2] = dineroPagado;
			indiceO[3] = dias;
			indiceO[4] = reservas;

			info.add (indiceO);
			
		}
		return info;
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar los APARTAMENTO
	 *****************************************************************/

	public Apartamento adicionarApartamento(int num, String serv, String ubi, long idp, String tipoId) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long tuplasInsertadas = sqlApartamento.adicionarApartamento(pm, ubi, serv, idp, num, tipoId);
            tx.commit();

            log.trace ("Inserción del Apartamento: " + ubi + ": " + tuplasInsertadas + " tuplas insertadas");

            return new Apartamento(num, serv, ubi, idp, tipoId);
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}

	public long eliminarApartamentoPorUbicacion (String ubi) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlApartamento.eliminarApartamentoPorUbicacion(pm, ubi);
            tx.commit();

            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}

	public List<Apartamento> darApartamento ()
	{
		return sqlApartamento.darApartamentos(pmf.getPersistenceManager());
	}

	public List<Apartamento> darApartamentoPorTipoId (String tip)
	{
		return sqlApartamento.darApartamentoPorTipoIdProp(pmf.getPersistenceManager(), tip);
	}

	public Apartamento darApartamentoPorUbicacion (String ubi)
	{
		return sqlApartamento.darApartamentoPorUbicacion(pmf.getPersistenceManager(), ubi);
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar las VIVIENDA
	 *****************************************************************/

	public Vivienda adicionarVivienda(String cara, String men, int dias, int num, String serv, String ubi, long idv, String tipoId) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long tuplasInsertadas = sqlVivienda.adicionarVivienda(pm, cara, men, dias, num, serv, ubi, idv, tipoId);
            tx.commit();

            log.trace ("Inserción de Vivienda: " + ubi + ": " + tuplasInsertadas + " tuplas insertadas");

            return new Vivienda(ubi, cara, men, dias, num, serv, idv, tipoId);
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}

	public long eliminarViviendaPorUbicacion (String ubi) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlVivienda.eliminarViviendaPorUbicacion(pm, ubi);
            tx.commit();

            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}

	public List<Vivienda> darViviendas ()
	{
		return sqlVivienda.darViviendas(pmf.getPersistenceManager());
	}
 
	public List<Vivienda> darViviendasPorTipoId (String tip)
	{
		return sqlVivienda.darViviendasPorVecTipoId(pmf.getPersistenceManager(), tip);
	}
 
	public Vivienda darViviendaPorUbicacion (String ubi)
	{
		return sqlVivienda.darViviendaPorUbicacion(pmf.getPersistenceManager(), ubi);
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar las HABITACION
	 *****************************************************************/

	public Habitacion adicionarHabitacion(int cap, int con, int dias, int est, int hab, double precio, String serv, String tam, String cat, long idd, String tipoId) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long idh = nextval ();
            long tuplasInsertadas = sqlHabitacion.adicionarHbitacion(pm, idh, cap, con, dias, est, hab, precio, serv, tam, cat, idd, tipoId);
            tx.commit();

            log.trace ("Inserción de Habitacion: " + idh + ": " + tuplasInsertadas + " tuplas insertadas");

            return new Habitacion(idh, cap, con, dias, est, hab, precio, serv, tam, cat, idd, tipoId);
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}

	public long eliminarHabitacionPorId (long idh) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlHabitacion.eliminarhabitacionPorId(pm, idh);
            tx.commit();

            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}

	public List<Habitacion> darHabitaciones ()
	{
		return sqlHabitacion.darHabitaciones(pmf.getPersistenceManager());
	}
 
	public List<Habitacion> darHabitacionesPorTipoDom (String tipoDom)
	{
		return sqlHabitacion.darHabitacionPorTipoDom(pmf.getPersistenceManager(), tipoDom);
	}
 
	public Habitacion darHabitacionPorId (long idh)
	{
		return sqlHabitacion.darHabitacionPorId(pmf.getPersistenceManager(), idh);
	}
	
	public List<Object[ ]> darAnalisisOperacion()
	{
		List<Object []> analisis = new LinkedList <Object []> ();
		List<Object[]> tuplas= sqlHabitacion.darAnalisisOperacion(pmf.getPersistenceManager());
		for (Object[] tupla : tuplas)
		{
			String alojamiento = (String) tupla [0];
			long maximo = ((BigDecimal) tupla[1]).longValue();
			Timestamp mejorPaga = (Timestamp) tupla [2];
			long maxocu = ((BigDecimal) tupla[3]).longValue();
			Timestamp mejorOcupa = (Timestamp) tupla [4];
						
			Object [] indiceO = new Object [5];
			indiceO[0] = alojamiento;
			indiceO [1] = maximo;
			indiceO[2] = mejorPaga;
			indiceO[3] = maxocu;
			indiceO[4] = mejorOcupa;

			analisis.add (indiceO);
			
		}
		return analisis;
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar las OFERTA
	 *****************************************************************/

	public Oferta adicionarOferta(int des, int dias, int usada, Timestamp fin, Timestamp lle, String tiem, String activa, String disponible) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long ido = nextval ();
            long tuplasInsertadas = sqlOferta.adicionarOferta(pm, ido, des, dias, usada, fin, lle, tiem, activa, disponible);
            tx.commit();

            log.trace ("Inserción de Oferta: " + ido + ": " + tuplasInsertadas + " tuplas insertadas");

            return new Oferta(ido, fin, lle, dias, usada, des, tiem, activa, disponible);
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}

	public long eliminarOfertaPorId (long ido) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlOferta.eliminarOfertaPorId(pm, ido);
            tx.commit();

            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}

	public List<Oferta> darOfertas ()
	{
		return sqlOferta.darOfertas(pmf.getPersistenceManager());
	}
 
	public Oferta darOfertaPorId (long ido)
	{
		return sqlOferta.darOfertaPorId(pmf.getPersistenceManager(), ido);
	}
	
	public List<Oferta> dar20ofertasMasPopulares ()
	{
		List<Oferta> respuesta = new LinkedList <Oferta> ();
		List<Object> tuplas = sqlOferta.dar20ofertasMasPopulares(pmf.getPersistenceManager());
        for ( Object tupla : tuplas)
        {
			Object [] datos = (Object []) tupla;
			long idOferta = ((BigDecimal) datos [0]).longValue ();
			int descuento = ((BigDecimal) datos[1]).intValue();
			int diasActiva = ((BigDecimal) datos[2]).intValue();
			int diasUsada = ((BigDecimal) datos[3]).intValue();
			Timestamp fechaFin = (Timestamp) datos [4];
			Timestamp fechaIni = (Timestamp) datos [5];
			String tiempoContrato = (String) datos [6];
			String activa = (String) datos[7];
			String disponible= (String) datos[8];

			Oferta resp = new Oferta(idOferta, fechaFin, fechaIni, diasActiva, diasUsada, descuento, tiempoContrato, activa, disponible);	
			
			respuesta.add(resp);
        }

		return respuesta;
	}
	
	public List<Object[]> darIndiceOcupacion( )
	{
		List<Object[]> respuesta = new LinkedList<Object[]>();
		List<Object> tuplas = sqlOferta.darIndiceDeOcupacion(pmf.getPersistenceManager());
		for( Object tupla : tuplas)
		{
			Object[] datos = (Object[]) tupla;
			long idOferta = ((BigDecimal) datos [0]).longValue ();
			int diasActiva = ((BigDecimal) datos[1]).intValue();
			int diasUsada = ((BigDecimal) datos[2]).intValue();
			double indice = ((BigDecimal) datos[3]).doubleValue();
			
			Object [] indiceO = new Object [4];
			indiceO[0] = idOferta;
			indiceO [1] = diasActiva;
			indiceO[2] = diasUsada;
			indiceO[3] = indice;

			respuesta.add (indiceO);
		}
		return respuesta;
		
	}
	
	public long actulizarOfertaActiva (long ido) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlOferta.actulizarOfertaActiva(pm, ido);
            tx.commit();

            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	} 
	
	public List<long[]> darReservasPorCambiar()
	{
		List<long[]> respuesta = new LinkedList<long[]>();
		List<Object[]> tuplas = sqlOferta.darReservasPorCambiar(pmf.getPersistenceManager());
		for( Object[] tupla : tuplas)
		{
			long[] datos = new long [2];
			datos[0]= ((BigDecimal) tupla [0]).longValue ();
			datos[1]= ((BigDecimal) tupla [1]).longValue ();
			respuesta.add(datos);	
		}
		return respuesta;
	}
	
	public long actualizarReservas()
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlOferta.actualizarReservas(pm);
            tx.commit();

            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	public long actualizarOfertaDesactiva(long ido) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlOferta.actualizarOfertaDesactiva(pm, ido);
            tx.commit();

            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	} 
	
	/* ****************************************************************
	 * 			Métodos para manejar las RESERVA
	 *****************************************************************/
	
	public Reserva adicionarReserva(double costoP, double costoT, Timestamp ida, Timestamp lle, String tiem, long idc, String tipoc, long ido) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long idr = nextval ();
            long tuplasInsertadas = sqlReserva.adicionarReserva(pm, idr, costoP, costoT, ida, lle, tiem, idc, tipoc, ido);
            tx.commit();

            log.trace ("Inserción de Reserva: " + idr + ": " + tuplasInsertadas + " tuplas insertadas");

            return new Reserva(idr, costoP, costoT, ida, lle, tiem, idc, tipoc, ido);
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	public long elimResSiOfeInac (long idr) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlReserva.elimResSiOfeInac(pm, idr);
            tx.commit();

            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	public long cambiarDisponibleOferta (long ido) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlReserva.cambiarDisponibleOferta(pm, ido);
            tx.commit();

            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}

	public long eliminarReservaPorId (long idr) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlReserva.eliminarReservaPorId(pm, idr);
            tx.commit();

            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}

	public List<Reserva> darReservas ()
	{
		return sqlReserva.darReservas(pmf.getPersistenceManager());
	}
 
	public List<Reserva> darReservasPorIdCli (long idc)
	{
		return sqlReserva.darReservaPorIdClie(pmf.getPersistenceManager(), idc);
	}
 
	public Reserva darReservaPorId (long idr)
	{
		return sqlReserva.darRservaPorId(pmf.getPersistenceManager(), idr);
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar los SERVICIOS_ADICIONALES
	 *****************************************************************/

	public ServiciosAdicionales adicionarServicio(double costo, String nombre, long ido) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long ids = nextval ();
            long tuplasInsertadas = sqlServiciosAdicionales.adicionarServicio(pm, ids, costo, nombre, ido);
            tx.commit();

            log.trace ("Inserción de Servicio Adicional: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");

            return new ServiciosAdicionales(ids, costo, nombre, ido);
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	public long eliminarServicioPorId (long ids) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlServiciosAdicionales.eliminarServiciosPorId(pm, ids);
            tx.commit();

            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}

	public List<ServiciosAdicionales> darServicios ()
	{
		return sqlServiciosAdicionales.darServicios(pmf.getPersistenceManager());
	}
 
	public List<ServiciosAdicionales> darServiciosPorOferta (long ido)
	{
		return sqlServiciosAdicionales.darServiciosPorOferta(pmf.getPersistenceManager(), ido);
	}
 
	public ServiciosAdicionales darServicioPorId (long ids)
	{
		return sqlServiciosAdicionales.darServicioPorId(pmf.getPersistenceManager(), ids);
	}
	
	
	public long [] limpiarAlohandes ()
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long [] resp = sqlUtil.limpiaralohAndes(pm);
            tx.commit ();
            log.info ("Borrada la base de datos");
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return new long[] {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
		
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar las OFERTA APARTAMENTO
	 *****************************************************************/

	public OfertaHabitacion adicionarOfertaHabitacion(long ido, long idh) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long tuplasInsertadas = sqlOfertaHabitacion.adicionarOfertaHabitacion(pm, ido, idh);
            tx.commit();

            log.trace ("Inserción de Oferta Habitacion: [" + ido + ", " + idh + "]: " + tuplasInsertadas + " tuplas insertadas");

            return new OfertaHabitacion(ido, idh);
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}


	public long eliminarOfertaHabitacionPorIds (long ido, long idh) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlOfertaHabitacion.eliminarOfertaHabitacion(pm, ido, idh);
            tx.commit();

            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar las OFERTA VIVIENDA
	 *****************************************************************/

	public OfertaVivienda adicionarOfertaVivienda(long ido, String idv) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long tuplasInsertadas = sqlOfertaVivienda.adicionarOfertaVivienda(pm, ido, idv);
            tx.commit();

            log.trace ("Inserción de Oferta Vivienda: [" + ido + ", " + idv + "]: " + tuplasInsertadas + " tuplas insertadas");

            return new OfertaVivienda(ido, idv);
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}


	public long eliminarOfertaViviendaPorIds (long ido, String idv) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlOfertaVivienda.eliminarOfertaVivienda(pm, ido, idv);
            tx.commit();

            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar las OFERTA APARTAMENTO
	 *****************************************************************/

	public OfertaApartamento adicionarOfertaApartamento(long ido, String ida) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long tuplasInsertadas = sqlOfertaApartamento.adicionarOfertaApartamento(pm, ido, ida);
            tx.commit();

            log.trace ("Inserción de Oferta Apartamento: [" + ido + ", " + ida + "]: " + tuplasInsertadas + " tuplas insertadas");

            return new OfertaApartamento(ido, ida);
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}


	public long eliminarOfertaApartamentoPorIds (long ido, String ida) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlOfertaApartamento.eliminarOfertaApartamento(pm, ido, ida);
            tx.commit();

            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	
	/* ****************************************************************
	 * 			Métodos para manejar las reservaColectiva
	 *****************************************************************/
	
	public ReservaColectiva registrarReservaColectiva (long idresCol, long idc, String tipoc, Timestamp lle,Timestamp ida, Timestamp  fePago,int cantidadRes, String tipoAlojamiento, double costo, String servicios) 
	{
		
		
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
        	System.out.println("corriendo");
            tx.begin();
            //    		revisar que se pueda cubrir la demanda
            ReservaColectiva resp = null;
            switch(tipoAlojamiento) {
    		case "APARTAMENTO":
    			System.out.println("CORR1");
    			List<Object> listaOfeApa = sqlOfertaApartamento.consultarOfertasDisponiblesApartamento(pm,  cantidadRes, servicios ) ;
    			//nunca listaOfeApa.size() nunca va aser mayor a cantidad res porque de ser mayor la recortaría
    			if(listaOfeApa.size() == cantidadRes)
    			{
    				System.out.println("CORR2");

    				//hay que ir guardando los ids de las reservas para luego poder hacer la relacion
    				List<Long> idsReservas = new ArrayList<Long>();
    				for( Object tupla : listaOfeApa)
    				{
    					
    					Object[] datos = (Object[]) tupla;
    					
    					Long tempId =   nextval(); 
    					double tempCosPa = 0;
    					double tempCosTot = 10000;
    					Long tempIdOfer =  ((BigDecimal) datos[0]).longValue();
    					idsReservas.add(tempId);
    					sqlReserva.adicionarReserva(pm, tempId, tempCosPa, tempCosTot, ida, lle, tipoAlojamiento, idc, tipoc, tempIdOfer);
    				
    					System.out.println("CORR2.1");
    				}
    				System.out.println("CORR3");
    				
    				sqlReservaColectiva.registrarReservaColectiva(pm, idresCol, tipoc, idc, lle, ida, fePago, cantidadRes, tipoAlojamiento, costo);
    				System.out.println("CORR3.01");
    				
    				//crear la reservaColectiva
    				resp = new ReservaColectiva(idresCol, tipoc, idc, lle, ida, fePago, cantidadRes, tipoAlojamiento, costo);
    				log.trace ("Inserción de reservaColectiva" + idresCol + " tuplas insertadas");
    				System.out.println("CORR3.1");
    				//relacionar las reservas con la reservaColectiva
    				for( Long idReservaRegistrada:  idsReservas)
    				{
    					System.out.println("CORR3.2");
    					sqlResColRes.adicionarResColRes(pm, idReservaRegistrada, idresCol);
    				}
    				System.out.println("CORR4");
    			}
    			break;
    		case "VIVIENDA":
    			System.out.println("CORR1");
    			List<Object> listaOfeViv = sqlOfertaVivienda.consultarOfertasDisponiblesVivienda(pm, cantidadRes, servicios) ;
    			//nunca listaOfeApa.size() nunca va aser mayor a cantidad res porque de ser mayor la recortaría
    			if(listaOfeViv.size() == cantidadRes)
    			{
    				System.out.println("CORR2");

    				//hay que ir guardando los ids de las reservas para luego poder hacer la relacion
    				List<Long> idsReservas = new ArrayList<Long>();
    				for( Object tupla : listaOfeViv)
    				{
    					
    					Object[] datos = (Object[]) tupla;
    					
    					Long tempId =   nextval(); 
    					double tempCosPa = 0;
    					double tempCosTot = 10000;
    					Long tempIdOfer =  ((BigDecimal) datos[0]).longValue();
    					idsReservas.add(tempId);
    					sqlReserva.adicionarReserva(pm, tempId, tempCosPa, tempCosTot, ida, lle, tipoAlojamiento, idc, tipoc, tempIdOfer);
    				
    					System.out.println("CORR2.1");
    				}
    				System.out.println("CORR3");
    				
    				sqlReservaColectiva.registrarReservaColectiva(pm, idresCol, tipoc, idc, lle, ida, fePago, cantidadRes, tipoAlojamiento, costo);
    				System.out.println("CORR3.01");
    				
    				//crear la reservaColectiva
    				resp = new ReservaColectiva(idresCol, tipoc, idc, lle, ida, fePago, cantidadRes, tipoAlojamiento, costo);
    				log.trace ("Inserción de reservaColectiva" + idresCol + " tuplas insertadas");
    				System.out.println("CORR3.1");
    				//relacionar las reservas con la reservaColectiva
    				for( Long idReservaRegistrada:  idsReservas)
    				{
    					System.out.println("CORR3.2");
    					sqlResColRes.adicionarResColRes(pm, idReservaRegistrada, idresCol);
    				}
    				System.out.println("CORR4");
    			}
    			break;
    		case "HABITACION":
    			System.out.println("CORR1");
    			List<Object> listaOfeHab = sqlOfertaHabitacion.consultarOfertasDisponiblesHabitacion(pm, cantidadRes, servicios);
    			//nunca listaOfeApa.size() nunca va aser mayor a cantidad res porque de ser mayor la recortaría
    			if(listaOfeHab.size() == cantidadRes)
    			{
    				System.out.println("CORR2");

    				//hay que ir guardando los ids de las reservas para luego poder hacer la relacion
    				List<Long> idsReservas = new ArrayList<Long>();
    				for( Object tupla : listaOfeHab)
    				{
    					
    					Object[] datos = (Object[]) tupla;
    					
    					Long tempId =   nextval(); 
    					double tempCosPa = 0;
    					double tempCosTot = 10000;
    					Long tempIdOfer =  ((BigDecimal) datos[0]).longValue();
    					idsReservas.add(tempId);
    					sqlReserva.adicionarReserva(pm, tempId, tempCosPa, tempCosTot, ida, lle, tipoAlojamiento, idc, tipoc, tempIdOfer);
    				
    					System.out.println("CORR2.1");
    				}
    				System.out.println("CORR3");
    				
    				sqlReservaColectiva.registrarReservaColectiva(pm, idresCol, tipoc, idc, lle, ida, fePago, cantidadRes, tipoAlojamiento, costo);
    				System.out.println("CORR3.01");
    				
    				//crear la reservaColectiva
    				resp = new ReservaColectiva(idresCol, tipoc, idc, lle, ida, fePago, cantidadRes, tipoAlojamiento, costo);
    				log.trace ("Inserción de reservaColectiva" + idresCol + " tuplas insertadas");
    				System.out.println("CORR3.1");
    				//relacionar las reservas con la reservaColectiva
    				for( Long idReservaRegistrada:  idsReservas)
    				{
    					System.out.println("CORR3.2");
    					sqlResColRes.adicionarResColRes(pm, idReservaRegistrada, idresCol);
    				}
    				System.out.println("CORR4");
    			}
    			break;
    		default:;
    		}	
            
            tx.commit();

            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	
	public long eliminarReservaColectivaPorId (long idResCol) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            System.out.println("CORE0 " );
            List<Object> listaResColRes = sqlResColRes.darReservaPorId(pm, idResCol) ;
            System.out.println("CORE02");
            System.out.println("CORE1 " + listaResColRes.size() );
            //hay que guardar los ids de reservas de rescolres antes de borrarlos
            List<Long> idsReservas = new ArrayList<Long>();
            if(listaResColRes.size()>0)
            {
            	
				for( Object tupla : listaResColRes)
				{
					
					Object[] datos = (Object[]) tupla;
					Long tempIdRes =  ((BigDecimal) datos[0]).longValue();
					idsReservas.add(tempIdRes);
					System.out.println("CORE2");
				}
            }
            //eliminar las rescolres relacionadas con la resCol
             sqlResColRes.eliminarResColResId(pm, idResCol);
            System.out.println("CORE3");
            //eliminarLasReservas
            if(idsReservas.size()>0)
            {
            	
				for( Long idABorrar: idsReservas)
				{
					
					sqlReserva.eliminarReservaPorId(pm, idABorrar);
					System.out.println("CORE4");
				}
            }
            //finalmente se borra la resCol
            long resp = sqlReservaColectiva.eliminarReservaColectivaPorId(pm, idResCol);
            System.out.println("CORE5");
            tx.commit();

            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	public List<Cliente> darClientesHabituales () 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		List<Cliente> respuesta = new LinkedList <Cliente> ();
		List<Object> tuplas = sqlResColRes.darClientesHabituales(pm);
		System.out.println(tuplas.size());
		for ( Object tupla : tuplas)
        {
        	System.out.println("CO2");
			Object [] datos = (Object []) tupla;
			long identificacion = ((BigDecimal) datos [0]).longValue ();
			String tipoid = (String) datos[1];
			String nom = (String) datos[2];
			String tipoc = (String) datos[3];
			
			Cliente tempCli = new Cliente(identificacion, tipoid, nom, tipoc);
			respuesta.add(tempCli);
        }

		return respuesta;
	}
	
}
