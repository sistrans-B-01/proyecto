--RFC10 ADMINISTRADOR
create index fechaofeclien on reserva(fechallegada, idoferta, idcliente);

select 'HABITACION' as ALOJAMIENTO, cliente.nombre as Nombre, cliente.numeroidentificacion as Identificacion, cliente.tipoidentificacion as TipoIdentificacion, cliente.tipocliente as Relacion, oferta.id as idOferta
from cliente, oferta, reserva, ofertahabitacion
where oferta.id= reserva.idoferta and cliente.numeroidentificacion= reserva.idcliente 
      and reserva.fechallegada BETWEEN '01-ene-2019' and '20-ENE-2019' 
      and ofertahabitacion.idoferta= oferta.id
group by oferta.id, cliente.numeroidentificacion, cliente.tipocliente, cliente.tipoidentificacion, cliente.nombre

UNION

select 'VIVIENDA' as ALOJAMIENTO, cliente.nombre as Nombre, cliente.numeroidentificacion as Identificacion, cliente.tipoidentificacion as TipoIdentificacion, cliente.tipocliente as Relacion , oferta.id as idOferta
from cliente, oferta, reserva, ofertavivienda
where oferta.id= reserva.idoferta and cliente.numeroidentificacion= reserva.idcliente 
      and reserva.fechallegada BETWEEN '01-ene-2019' and '20-ENE-2019' 
      and ofertavivienda.idoferta= oferta.id
group by oferta.id, cliente.numeroidentificacion, cliente.tipocliente, cliente.tipoidentificacion, cliente.nombre

UNION

select 'APARTAMENTO' as ALOJAMIENTO, cliente.nombre as Nombre, cliente.numeroidentificacion as Identificacion, cliente.tipoidentificacion as TipoIdentificacion, cliente.tipocliente as Relacion, oferta.id as idOferta
from cliente, oferta, reserva, ofertaapartamento
where oferta.id= reserva.idoferta and cliente.numeroidentificacion= reserva.idcliente 
      and reserva.fechallegada BETWEEN '19-ene-2019' and '20-ENE-2019' 
      and ofertaapartamento.idoferta= oferta.id
group by oferta.id, cliente.numeroidentificacion, cliente.tipocliente, cliente.tipoidentificacion, cliente.nombre
order by Identificacion;

drop index fechaofeclien;



--RFC10 CLIENTE
create index fechaofeclie on reserva(fechallegada, idcliente, idoferta);

select 'HABITACION' as ALOJAMIENTO, cliente.nombre as Nombre, cliente.numeroidentificacion as Identificacion, cliente.tipoidentificacion as TipoIdentificacion, cliente.tipocliente as Relacion, oferta.id as idOferta
from cliente, oferta, reserva, ofertahabitacion
where oferta.id= reserva.idoferta and cliente.numeroidentificacion= 94871 and cliente.numeroidentificacion= reserva.idcliente 
      and reserva.fechallegada BETWEEN '01-ene-2019' and '20-ENE-2019' 
      and ofertahabitacion.idoferta= oferta.id
group by oferta.id, cliente.numeroidentificacion, cliente.tipocliente, cliente.tipoidentificacion, cliente.nombre

UNION

select 'VIVIENDA' as ALOJAMIENTO, cliente.nombre as Nombre, cliente.numeroidentificacion as Identificacion, cliente.tipoidentificacion as TipoIdentificacion, cliente.tipocliente as Relacion , oferta.id as idOferta
from cliente, oferta, reserva, ofertavivienda
where oferta.id= reserva.idoferta and cliente.numeroidentificacion= 94871 and cliente.numeroidentificacion= reserva.idcliente 
      and reserva.fechallegada BETWEEN '01-ene-2019' and '20-ENE-2019' 
      and ofertavivienda.idoferta= oferta.id
group by oferta.id, cliente.numeroidentificacion, cliente.tipocliente, cliente.tipoidentificacion, cliente.nombre

UNION

select 'APARTAMENTO' as ALOJAMIENTO, cliente.nombre as Nombre, cliente.numeroidentificacion as Identificacion, cliente.tipoidentificacion as TipoIdentificacion, cliente.tipocliente as Relacion, oferta.id as idOferta
from cliente, oferta, reserva, ofertaapartamento
where oferta.id= reserva.idoferta and cliente.numeroidentificacion= 94871 and cliente.numeroidentificacion= reserva.idcliente 
      and reserva.fechallegada BETWEEN '01-ene-2019' and '20-ENE-2019' 
      and ofertaapartamento.idoferta= oferta.id
group by oferta.id, cliente.numeroidentificacion, cliente.tipocliente, cliente.tipoidentificacion, cliente.nombre
order by Identificacion;

drop index fechaofeclie;



