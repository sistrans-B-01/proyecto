package uniandes.isis2304.parranderos.negocio;

import org.apache.log4j.Logger;  

import com.google.gson.JsonObject;
import java.sql.Timestamp;
import java.util.List;

import javax.jdo.PersistenceManager;

import uniandes.isis2304.parranderos.persistencia.PersistenciaAlohAndes;

public class AlohAndes 
{

	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(AlohAndes.class.getName());
	
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El manejador de persistencia
	 */
	private PersistenciaAlohAndes pp;
	
	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * El constructor por defecto
	 */
	public AlohAndes ()
	{
		pp = PersistenciaAlohAndes.getInstance ();
	}
	
	/**
	 * El constructor qye recibe los nombres de las tablas en tableConfig
	 * @param tableConfig - Objeto Json con los nombres de las tablas y de la unidad de persistencia
	 */
	public AlohAndes (JsonObject tableConfig)
	{
		pp = PersistenciaAlohAndes.getInstance (tableConfig);
	}
	
	/**
	 * Cierra la conexión con la base de datos (Unidad de persistencia)
	 */
	public void cerrarUnidadPersistencia ()
	{
		pp.cerrarUnidadPersistencia ();
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar los PROPIETARIOS
	 *****************************************************************/
	/**
	 * Adiciona de manera persistente un propietario 
	 * Adiciona entradas al log de la aplicación
	 * @param nom - El nombre del propietario
	 * @param numid - El numero de identificacion del propietario
	 * @param tipoid - El tipo de identificacion del propietario
	 * @param tipop - El tipo de propietario
	 * @return El objeto Propietario adicionado. null si ocurre alguna Excepción
	 */
	public Propietario adicionarPropietario (String nom, long numid, String tipoid, String tipop)
	{
        log.info ("Adicionando Propietario: " + nom);
        Propietario prop = pp.adicionarPropietario(nom, numid, tipoid, tipop);
        log.info ("Adicionando propietario: " + prop);
        return prop;
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar los DOMINIOS
	 *****************************************************************/
	/**
	 * Adiciona de manera persistente un DOMINIO
	 * Adiciona entradas al log de la aplicación
	 * @param reg - El registro del dominio
	 * @param tiporeg - El tipo de registro
	 * @param nom - El nombre del dominio
	 * @param horario - El horario del dominio
	 * @param ubi - La ubicacion del dominio
	 * @return El objeto Dominio adicionado. null si ocurre alguna Excepción
	 */
	public Dominio adicionarDominio (long reg, String tiporeg, String nom, String horario, String ubi)
	{
        log.info ("Adicionando Dominio: " + nom);
        Dominio dom = pp.adicionarDominio(reg, tiporeg, nom, horario, ubi);
        log.info ("Adicionando Dominio: " + dom);
        return dom;
	}
	
	public List<Object []> darDineroRecibidPorProveedor ()
	{
        log.info ("Listando el nombre del proveedor y el dinero que recibe");
        List<Object []> tuplas = pp.darDineroRecibidoPorProveedores( );
        log.info ("Listando el nombre del proveedor y el dinero recibe: Listo!");
        return tuplas;
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar los VECINOS
	 *****************************************************************/
	/**
	 * Adiciona de manera persistente un VECINO
	 * Adiciona entradas al log de la aplicación
	 * @param nom - El nombre del vecino
	 * @param numid - El numero de identificacion del vecino
	 * @param tipoid - El tipo de identificacion del vecino
	 * @return El objeto Vecino adicionado. null si ocurre alguna Excepción
	 */
	public Vecinos adicionarVecino (String nom, long numid, String tipoid)
	{
        log.info ("Adicionando Propietario: " + nom);
        Vecinos veci = pp.adicionarVecino(nom, numid, tipoid);
        log.info ("Adicionando vecino: " + veci);
        return veci;
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar las HABITACIONES
	 *****************************************************************/
	/**
	 * Adiciona de manera persistente una habitacion
	 * Adiciona entradas al log de la aplicación
	 * @param cap - La capacidad
	 * @param con - El numero de personas con quienes se comprate
	 * @param dias - Los dias usada
	 * @param est - Las estrellas que tiene
	 * @param hab - El numero de habitacion
	 * @param precio - El precio de la habitacion
	 * @param serv - Los servicios
	 * @param tam - El tamaño
	 * @param cat - La categoria
	 * @param idd - El id del dominio
	 * @param tipoid - El tipo de id del dominio
	 * @return El objeto Habitacion adicionado. null si ocurre alguna Excepción
	 */
	public Habitacion adicionarHabitacion (int cap, int con, int dias, int est, int hab, double precio, String serv, String tam, String cat, long idd, String tipoid)
	{
        log.info ("Adicionando Habitacion: " + hab);
        Habitacion habit = pp.adicionarHabitacion(cap, con, dias, est, hab, precio, serv, tam, cat, idd, tipoid);
        log.info ("Adicionando habitacion: " + habit);
        return habit;
	}
	
	public List<Object[]> darAnalisisOcupacion( )
	{
		log.info("Listando el analisis de ocupacion");
		List<Object[]> tuplas= pp.darAnalisisOperacion();
		log.info("Listando el analisis de ocupacion");
		return tuplas;
	}
	
	
	/* ****************************************************************
	 * 			Métodos para manejar las OFERTAS
	 *****************************************************************/
	/**
	 * Adiciona de manera persistente una oferta
	 * Adiciona entradas al log de la aplicación
	 * @param desc - El descuento
	 * @param dias - Los dias activa
	 * @param usada - Los dias usada
	 * @param fin - La fecha final
	 * @param lle - La fecha de inicio
	 * @param tiem - El tiempo del contrato
	 * @param idh - El id de la habitacion
	 * @return El objeto Oferta adicionado. null si ocurre alguna Excepción
	 */
	public Oferta adicionarOferta (int desc, int dias, int usada, Timestamp fin, Timestamp lle, String tiem, String activa, String disponible)
	{
        log.info ("Adicionando Oferta: [" + lle + ", " + fin + "]");
        Oferta oferta = pp.adicionarOferta(desc, dias, usada, fin, lle, tiem, activa, disponible);
        log.info ("Adicionando oferta: " + oferta);
        return oferta;
	}
	
	public long eliminarOfertaPorId (long ido)
	{
		log.info ("Eliminando Oferta por id: " + ido);
        long resp = pp.eliminarOfertaPorId(ido);		
        log.info ("Eliminando Oferta por id: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	public List<Oferta> dar20OfertasMasPopulares ()
	{
        log.info ("Listando las 20 ofertas mas populares");
        List<Oferta> tuplas = pp.dar20ofertasMasPopulares();
        log.info ("Listando las 20 ofertas mas populares: Listo!");
        return tuplas;
	}
	
	public List<Object[]> darIndiceOcupacion( )
	{
		log.info("Listando el índice de ocupacion de cada oferta");
		List<Object[]> tuplas= pp.darIndiceOcupacion();
		log.info("Listando el indice de ocupacion de cada oferta");
		return tuplas;
	}
	
	public long actulizarOfertaActiva (long ido)
	{
		log.info("Actualizando desactivacion de oferta: " + ido);
		long resp= pp.actulizarOfertaActiva(ido);
		log.info ("Actualizando desactivacion de oferta: " + resp);
        return resp;
	}
	
	public long actualizarReservas()
	{
		log.info("Actualizando las reservas ");
		long resp= pp.actualizarReservas();
		log.info ("Actualizando las reservas: " + resp);
        return resp;
	}
	
	public List<long[]> darReservasPorCambiar()
	{
		log.info("Listando las reservas a cambiar");
		List<long[]> tuplas= pp.darReservasPorCambiar();
		log.info("Listando las reservas a cambiar");
		return tuplas;
	}
	
	public long actulizarOfertaDesactiva (long ido)
	{
		log.info("Actualizando activacion de oferta: " + ido);
		long resp= pp.actualizarOfertaDesactiva(ido);
		log.info ("Actualizando activacion de oferta: " + resp);
        return resp;
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar los CLIENTES
	 *****************************************************************/
	/**
	 * Adiciona de manera persistente un cliente
	 * Adiciona entradas al log de la aplicación
	 * @param nom - El nombre 
	 * @param numid - El numero de identificacion
	 * @param tipoid - El tipo de identificacion
	 * @param tipoc - El tipo de cliente
	 * @return El objeto Cliente adicionado. null si ocurre alguna Excepción
	 */
	public Cliente adicionarCliente (String nom, long numid, String tipoid, String tipoc)
	{
        log.info ("Adicionando Cliente: " + nom);
        Cliente cliente = pp.adicionarCliente(nom, numid, tipoid, tipoc);
        log.info ("Adicionando cliente: " + cliente);
        return cliente;
	}
	
	public List<Object[]> darInfoGeneral( )
	{
		log.info("Listando la info general de clientes");
		List<Object[]> tuplas= pp.darInfoGeneral();
		log.info("Listando la info general del clientes");
		return tuplas;
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar las OFERTAS HABITACION
	 *****************************************************************/
	/**
	 * Adiciona de manera persistente una oferta habitacion
	 * Adiciona entradas al log de la aplicación
	 * @param ido - El id de la oferta
	 * @param idh - El id de la habitacion
	 * @return El objeto OfertaHabitacion adicionado. null si ocurre alguna Excepción
	 */
	public OfertaHabitacion adicionarOfertaHabitacion (long ido, long idh)
	{
        log.info ("Adicionando OfertaHabitacion: [" + ido + ", " + idh + "]");
        OfertaHabitacion ofertaH = pp.adicionarOfertaHabitacion(ido, idh);
        log.info ("Adicionando ofertaHabitacion: " + ofertaH);
        return ofertaH;
	}
	
	public long eliminarOfertaHabitacionPorIds (long ido, long idh)
	{
		log.info ("Eliminando OfertaHabitacion por ids: [" + ido + " ," + idh + "]");
        long resp = pp.eliminarOfertaHabitacionPorIds(ido, idh);		
        log.info ("Eliminando OfertaHabitacion por ids: " + resp + " tuplas eliminadas");
        return resp;
	}

	/* ****************************************************************
	 * 			Métodos para manejar las OFERTAS VIVIENDA
	 *****************************************************************/
	/**
	 * Adiciona de manera persistente una oferta vivienda
	 * Adiciona entradas al log de la aplicación
	 * @param ido - El id de la oferta
	 * @param idh - El id de la vivienda (ubicacion)
	 * @return El objeto OfertaVivienda adicionado. null si ocurre alguna Excepción
	 */
	public OfertaVivienda adicionarOfertaVivienda (long ido, String idv)
	{
        log.info ("Adicionando OfertaVivienda: [" + ido + ", " + idv + "]");
        OfertaVivienda ofertaV = pp.adicionarOfertaVivienda(ido, idv);
        log.info ("Adicionando ofertaVivienda: " + ofertaV);
        return ofertaV;
	}
	
	public long eliminarOfertaViviendaPorIds (long ido, String idv)
	{
		log.info ("Eliminando OfertaVivienda por ids: [" + ido + " ," + idv + "]");
        long resp = pp.eliminarOfertaViviendaPorIds(ido, idv);		
        log.info ("Eliminando OfertaVivienda por ids: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar las OFERTAS APARTAMENTO
	 *****************************************************************/
	/**
	 * Adiciona de manera persistente una oferta apartamento
	 * Adiciona entradas al log de la aplicación
	 * @param ido - El id de la oferta
	 * @param idh - El id del apartamento (ubicacion)
	 * @return El objeto OfertaVivienda adicionado. null si ocurre alguna Excepción
	 */
	public OfertaApartamento adicionarOfertaApartamento (long ido, String ida)
	{
        log.info ("Adicionando OfertaApartamento: [" + ido + ", " + ida + "]");
        OfertaApartamento ofertaA = pp.adicionarOfertaApartamento(ido, ida);
        log.info ("Adicionando ofertaApartamento: " + ofertaA);
        return ofertaA;
	}
	
	public long eliminarOfertaApartamentoPorIds (long ido, String ida)
	{
		log.info ("Eliminando OfertaApartamento por ids: [" + ido + " ," + ida + "]");
        long resp = pp.eliminarOfertaApartamentoPorIds(ido, ida);		
        log.info ("Eliminando OfertaApartamento por ids: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar las VIVIENDAS
	 *****************************************************************/
	/**
	 * Adiciona de manera persistente una vivienda
	 * Adiciona entradas al log de la aplicación
	 * @param cara - Las caracteristicas del seguro
	 * @param men - El menaje
	 * @param dias - Los dias usada
	 * @param hab - El numero de habitaciones
	 * @param serv - Los servicios
	 * @param ubi - La ubicacion
	 * @param idv - El id del vecino
	 * @param tipoId - El tipo de id del vecino
	 * @return El objeto Vivienda adicionado. null si ocurre alguna Excepción
	 */
	public Vivienda adicionarVivienda (String cara, String men, int dias, int num, String serv, String ubi, long idv, String tipoId)
	{
        log.info ("Adicionando Vivienda: " + ubi );
        Vivienda vivi = pp.adicionarVivienda(cara, men, dias, num, serv, ubi, idv, tipoId);
        log.info ("Adicionando Vivienda: " + vivi);
        return vivi;
	}

	/* ****************************************************************
	 * 			Métodos para manejar los APARTAMENTO
	 *****************************************************************/
	/**
	 * Adiciona de manera persistente un APARTAMENTO
	 * Adiciona entradas al log de la aplicación
	 * @param num - El numero de habitaciones
	 * @param serv - Los servicios
	 * @param ubi - La ubicacion
	 * @param idp - El id del propietario
	 * @param tipoId - El tipo de id del propietario
	 * @return El objeto Apartamento adicionado. null si ocurre alguna Excepción
	 */
	public Apartamento adicionarApartamento (int num, String serv, String ubi, long idp, String tipoId)
	{
        log.info ("Adicionando Apartamento: " + ubi );
        Apartamento apto = pp.adicionarApartamento(num, serv, ubi, idp, tipoId);
        log.info ("Adicionando Apartamento: " + apto);
        return apto;
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar las RESERVA
	 *****************************************************************/
	/**
	 * Adiciona de manera persistente una reserva
	 * Adiciona entradas al log de la aplicación
	 * @param costoP - El costo pagado
	 * @param costoT - El costo total
	 * @param ida - La fecha de ida
	 * @param lle - La fecha de llegada
	 * @param tiem - El tiempo de alojamiento
	 * @param idc - El id del cliente
	 * @param tipoc - El tipo de id del cliente
	 * @param ido - El id de la oferta
	 * @return El objeto Reserva adicionado. null si ocurre alguna Excepción
	 */
	public Reserva adicionarReserva (double costoP, double costoT, Timestamp ida, Timestamp lle, String tiem, long idc, String tipoc, long ido)
	{
        log.info ("Adicionando Reserva: [" + idc + ", " + ido + "]" );
        Reserva reserva = pp.adicionarReserva(costoP, costoT, ida, lle, tiem, idc, tipoc, ido);
        log.info ("Adicionando Reserva: " + reserva);
        return reserva;
	}
	
	public long eliminarReservaPorId (long idr)
	{
		log.info ("Eliminando Reserva por id: " + idr);
        long resp = pp.eliminarReservaPorId(idr);		
        log.info ("Eliminando Reserva por id: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	public long elimResSiOfeInac (long idr)
	{
		log.info("Eliminando reserva porque la oferta esta inactiva: " + idr);
		long resp= pp.elimResSiOfeInac(idr);
		log.info ("Eliminando Reserva: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	public long cambiarDisponibleOferta(long ido)
	{
		log.info("Actualizando disponibilidad de oferta: " + ido);
		long resp= pp.cambiarDisponibleOferta(ido);
		log.info ("Actualizando oferta: " + resp);
        return resp;
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar las reservasColectivas
	 *****************************************************************/
	/**
	 * de ser factible Adiciona de manera persistente una reserva colectiva
	 */
	public ReservaColectiva registrarReservaColectiva (long idresCol, long idc, String tipoc, Timestamp lle,Timestamp ida, Timestamp  fePago,int cantidadRes, String tipoAlojamiento, double costo, String servicios)
	{
        log.info ("Adicionando reserva colectiva: " );
        ReservaColectiva resCol = pp.registrarReservaColectiva( idresCol,  idc,  tipoc,  lle, ida,   fePago, cantidadRes,  tipoAlojamiento,  costo,  servicios);
        log.info ("Adicionando reserva colectiva: ");
        return resCol;
	}
	
	public long eliminarReservaColectivaPorId (long idResCol)
	{
		log.info ("Eliminando Reserva colectiva por id: " + idResCol);
        long resp = pp.eliminarReservaColectivaPorId(idResCol);		
        log.info ("Eliminando Reserva colectiva por id: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	public List<Cliente> darClientesHabituales () 
	{
		log.info ("consultandoClientesHabituales "  );
		List<Cliente> resp = pp.darClientesHabituales();		
        log.info ("consultandoClientesHabituales");
        return resp;
	}
	
	/* ****************************************************************
	 * 			Métodos para administración
	 *****************************************************************/

	/**
	 * Elimina todas las tuplas de todas las tablas de la base de datos de AlohAndes
	 * @return Un arreglo con 13 números que indican el número de tuplas borradas en las tablas respectivamente
	 */
	public long [] limpiarAlohandes ()
	{
        log.info ("Limpiando la BD de Alohandes");
        long [] borrrados = pp.limpiarAlohandes();	
        log.info ("Limpiando la BD de Alohandes: Listo!");
        return borrrados;
	}
	
}
