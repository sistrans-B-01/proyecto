--PRUEBA RF9

--Crea un dominio
INSERT INTO DOMINIO
(REGISTRO, TIPOREGISTRO, NOMBRE, HORARIO, UBICACION)
VALUES
(10, 'HOTEL', 'PRUEBA', 'DIURNO', 'CL 0');
COMMIT;

--Crea una habitacion
INSERT INTO HABITACION
(ID, CAPACIDAD,COMPARTIDACONCUANTOS,DIASUSADA,NUMEROESTRELLAS,NUMEROHAB,PRECIO,SERVICIOS,TAMANO,CATEGORIA,IDDOMINIO,TIPOREGDOMINIO)
VALUES
(10, 3, 0, 6, 5,201, 80000, 'LUZ, AGUA, GAS', '50X50', 'SUITE',10, 'HOTEL');
COMMIT;

--Crea un cliente
INSERT INTO CLIENTE
(NOMBRE, NUMEROIDENTIFICACION,TIPOIDENTIFICACION,TIPOCLIENTE)
VALUES
('JUAN', 10, 'CC', 'PROFESOR');
COMMIT;

--Crea una oferta1
INSERT INTO OFERTA
(ID, DESCUENTO,DIASACTIVA,DIASUSADA, FECHAFIN,FECHAINICIO,TIEMPOCONTRATO, ACTIVA, DISPONIBLE)
VALUES
(10, 10, 7, 6, '31-ENE-2020', '01-ENE-2020', 'UNA SEMANA', '1', 'Y');
COMMIT;

--Crea una oferta2
INSERT INTO OFERTA
(ID, DESCUENTO,DIASACTIVA,DIASUSADA, FECHAFIN,FECHAINICIO,TIEMPOCONTRATO, ACTIVA, DISPONIBLE)
VALUES
(11, 10, 7, 6, '31-ENE-2020', '01-ENE-2020', 'UNA SEMANA', '1', 'Y');
COMMIT;

--Crea una ofertaHabitacion1
INSERT INTO OFERTAHABITACION
(IDOFERTA, IDHABITACION)
VALUES
(10, 10);
COMMIT;

--Crea una ofertaHabitacion2
INSERT INTO OFERTAHABITACION
(IDOFERTA, IDHABITACION)
VALUES
(11, 10);
COMMIT;

--Crea una reserva1

INSERT INTO RESERVA
(ID, COSTOPAGADO,COSTOTOTAL,FECHAIDA,FECHALLEGADA,FECHADEPAGO,TIEMPOALOJAMIENTO,IDCLIENTE,TIPOIDCLIENTE,IDOFERTA)
VALUES
(10, 100000, 150000, '08-ENE-2020', '01-ENE-2020', '01-ENE-2020', 'UNA SEMANA', 10, 'CC', 10);


DELETE 
FROM RESERVA
WHERE RESERVA.ID=10 AND RESERVA.IDOFERTA IN( SELECT OFERTA.ID
                                               FROM OFERTA
                                               WHERE OFERTA.ACTIVA='0');                                               
UPDATE OFERTA
SET OFERTA.DISPONIBLE= 'N'
WHERE OFERTA.ID= 10 AND OFERTA.ID IN (SELECT RESERVA.IDOFERTA FROM RESERVA);

COMMIT;

--Crear una reserva2
INSERT INTO RESERVA
(ID, COSTOPAGADO,COSTOTOTAL,FECHAIDA,FECHALLEGADA,FECHADEPAGO,TIEMPOALOJAMIENTO,IDCLIENTE,TIPOIDCLIENTE,IDOFERTA)
VALUES
(11, 100000, 150000, '28-ENE-2020', '21-ENE-2020', '09-ENE-2020', 'UNA SEMANA', 10, 'CC', 10);

DELETE 
FROM RESERVA
WHERE RESERVA.ID=10 AND RESERVA.IDOFERTA IN( SELECT OFERTA.ID
                                               FROM OFERTA
                                               WHERE OFERTA.ACTIVA='0');                                               
UPDATE OFERTA
SET OFERTA.DISPONIBLE= 'N'
WHERE OFERTA.ID= 10 AND OFERTA.ID IN (SELECT RESERVA.IDOFERTA FROM RESERVA);

COMMIT;

--Actualiza Oferta1
UPDATE OFERTA
SET oferta.activa='0'
WHERE OFERTA.ID= 10;
COMMIT;

select distinct oferta.id as ofertas, reserva.id as cambiar
from oferta, reserva
where(oferta.id in (select distinct oferta.id
                    from oferta, reserva
                    where (oferta.id!= reserva.idoferta 
                          and oferta.disponible= 'Y'
                          and reserva.id in( select reserva.id
                                             from reserva, oferta
                                             where (reserva.idoferta=oferta.id and oferta.activa='0')
                                            )
                          and oferta.fechainicio= reserva.fechallegada
                          )   
                     ) 
       and reserva.id in ( select distinct reserva.id
                                             from reserva, oferta
                                             where (reserva.idoferta=oferta.id and oferta.activa='0')
                          )
       --and rownum<10
        )
        ;

update reserva
set reserva.idoferta=( select distinct oferta.id 
                      from oferta, reserva
                      where (oferta.id!= reserva.idoferta 
                          and oferta.fechainicio<= reserva.fechallegada and oferta.fechafin >= reserva.fechaida
                          and reserva.id in( select reserva.id
                                             from reserva, oferta
                                               where (reserva.idoferta=oferta.id and oferta.activa='0')
                                            )
                          ) 
                     ) 
where reserva.id in ( select reserva.id
                                             from reserva, oferta
                                             where (reserva.idoferta=oferta.id and oferta.activa='0')
                          )
                 


