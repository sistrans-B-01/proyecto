--RFC10 Administrador
create index fechaofeclien on reserva(fechallegada, idoferta, idcliente);

select 'HABITACION' as ALOJAMIENTO, cliente.*, oferta.id as idOferta, count(reserva.id) as reservas
from cliente, oferta, reserva, ofertahabitacion
where oferta.id= reserva.idoferta and cliente.numeroidentificacion= reserva.idcliente 
      and reserva.fechallegada BETWEEN '01-ene-2019' and '20-ENE-2019' 
      and ofertahabitacion.idoferta= oferta.id
group by oferta.id, cliente.numeroidentificacion, cliente.tipocliente, cliente.tipoidentificacion, cliente.nombre

UNION

select 'VIVIENDA' as ALOJAMIENTO, cliente.*, oferta.id as idOferta, count(reserva.id) as reservas
from cliente, oferta, reserva, ofertavivienda
where oferta.id= reserva.idoferta and cliente.numeroidentificacion= reserva.idcliente 
      and reserva.fechallegada BETWEEN '01-ene-2019' and '20-ENE-2019' 
      and ofertavivienda.idoferta= oferta.id
group by oferta.id, cliente.numeroidentificacion, cliente.tipocliente, cliente.tipoidentificacion, cliente.nombre

UNION

select 'APARTAMENTO' as ALOJAMIENTO, cliente.*, oferta.id as idOferta, count(reserva.id) as reservas
from cliente, oferta, reserva, ofertaapartamento
where oferta.id= reserva.idoferta and cliente.numeroidentificacion= reserva.idcliente 
      and reserva.fechallegada BETWEEN '19-ene-2019' and '20-ENE-2019' 
      and ofertaapartamento.idoferta= oferta.id
group by oferta.id, cliente.numeroidentificacion, cliente.tipocliente, cliente.tipoidentificacion, cliente.nombre;

drop index fechaofeclien;



--RFC10 Cliente
create index fechaofeclie on reserva(fechallegada, idcliente, idoferta);

select 'HABITACION' as ALOJAMIENTO, cliente.*, oferta.id as idOferta, count(reserva.id) as reservas
from cliente, oferta, reserva, ofertahabitacion
where oferta.id= reserva.idoferta and cliente.numeroidentificacion= 94871 and cliente.numeroidentificacion= reserva.idcliente 
      and reserva.fechallegada BETWEEN '01-ene-2019' and '20-ENE-2019' 
      and ofertahabitacion.idoferta= oferta.id
group by oferta.id, cliente.numeroidentificacion, cliente.tipocliente, cliente.tipoidentificacion, cliente.nombre

UNION

select 'VIVIENDA' as ALOJAMIENTO, cliente.*, oferta.id as idOferta, count(reserva.id) as reservas
from cliente, oferta, reserva, ofertavivienda
where oferta.id= reserva.idoferta and cliente.numeroidentificacion= 94871 and cliente.numeroidentificacion= reserva.idcliente 
      and reserva.fechallegada BETWEEN '01-ene-2019' and '20-ENE-2019' 
      and ofertavivienda.idoferta= oferta.id
group by oferta.id, cliente.numeroidentificacion, cliente.tipocliente, cliente.tipoidentificacion, cliente.nombre

UNION

select 'APARTAMENTO' as ALOJAMIENTO, cliente.*, oferta.id as idOferta, count(reserva.id) as reservas
from cliente, oferta, reserva, ofertaapartamento
where oferta.id= reserva.idoferta and cliente.numeroidentificacion= 94871 and cliente.numeroidentificacion= reserva.idcliente 
      and reserva.fechallegada BETWEEN '01-ene-2019' and '20-ENE-2019' 
      and ofertaapartamento.idoferta= oferta.id
group by oferta.id, cliente.numeroidentificacion, cliente.tipocliente, cliente.tipoidentificacion, cliente.nombre;

drop index fechaofeclie;



