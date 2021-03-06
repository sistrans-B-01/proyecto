--oferta proveedor hotel
INSERT INTO HABITACION
(ID, CAPACIDAD,COMPARTIDACONCUANTOS,DIASUSADA,NUMEROESTRELLAS,NUMEROHAB,PRECIO,SERVICIOS,TAMANO,CATEGORIA,IDDOMINIO,TIPOREGDOMINIO)
VALUES
(201, 3, 0, 6, 5,201, 80000, 'LUZ, AGUA, GAS', '50X50', 'SUITE',111, 'HOTEL');

INSERT INTO OFERTA
(ID, DESCUENTO,DIASACTIVA,DIASUSADA, FECHAFIN,FECHAINICIO,TIEMPOCONTRATO, ACTIVA, DISPONIBLE)
VALUES
(205, 10, 7, 6, '31-ENE-2020', '01-ENE-2020', 'UNA SEMANA', '1', 'Y');

INSERT INTO OFERTAHABITACION
(IDOFERTA, IDHABITACION)
VALUES
(205, 201)
 ;


INSERT INTO HABITACION
(ID, CAPACIDAD,COMPARTIDACONCUANTOS,DIASUSADA,NUMEROESTRELLAS,NUMEROHAB,PRECIO,SERVICIOS,TAMANO,CATEGORIA,IDDOMINIO,TIPOREGDOMINIO)
VALUES
(211, 3, 0, 7, 5,201, 80000, 'LUZ, AGUA, GAS', '50X50', 'SUITE',111, 'HOTEL');

INSERT INTO OFERTA
(ID, DESCUENTO,DIASACTIVA,DIASUSADA, FECHAFIN,FECHAINICIO,TIEMPOCONTRATO, ACTIVA, DISPONIBLE)
VALUES
(210, 10, 7, 7, '18-ENE-2020', '11-ENE-2020', 'UNA SEMANA', '1', 'Y');

INSERT INTO OFERTAHABITACION
(IDOFERTA, IDHABITACION)
VALUES
(210, 211);


INSERT INTO HABITACION
(ID, CAPACIDAD,COMPARTIDACONCUANTOS,DIASUSADA,NUMEROESTRELLAS,NUMEROHAB,PRECIO,SERVICIOS,TAMANO,CATEGORIA,IDDOMINIO,TIPOREGDOMINIO)
VALUES
(221, 3, 1, 5, 5,201, 80000, 'LUZ, AGUA, GAS', '50X50', 'SUITE',121, 'HOTEL');

INSERT INTO OFERTA
(ID, DESCUENTO,DIASACTIVA,DIASUSADA, FECHAFIN,FECHAINICIO,TIEMPOCONTRATO, ACTIVA, DISPONIBLE)
VALUES
(215, 10, 7, 5, '17-ENE-2020', '01-ENE-2020', 'UNA SEMANA', '1', 'Y');

INSERT INTO OFERTAHABITACION
(IDOFERTA, IDHABITACION)
VALUES
(215, 221);

COMMIT;

--oferta proveedor hostal
INSERT INTO HABITACION
(ID, CAPACIDAD,COMPARTIDACONCUANTOS,DIASUSADA,NUMEROESTRELLAS,NUMEROHAB,PRECIO,SERVICIOS,TAMANO,CATEGORIA,IDDOMINIO,TIPOREGDOMINIO)
VALUES
(231, 5, 4, 3, 1,101, 30000, 'ninguno', '10 X 10', 'STANDAR', 131, 'HOSTAL');

INSERT INTO OFERTA
(ID, DESCUENTO,DIASACTIVA,DIASUSADA,FECHAFIN,FECHAINICIO,TIEMPOCONTRATO, ACTIVA, DISPONIBLE)
VALUES
(220, 0, 7, 3, '21-ENE-2020', '01-ENE-2020', 'UNA SEMANA', '1', 'Y');

INSERT INTO OFERTAHABITACION
(IDOFERTA, IDHABITACION)
VALUES
(220, 231);


INSERT INTO HABITACION
(ID, CAPACIDAD,COMPARTIDACONCUANTOS,DIASUSADA,NUMEROESTRELLAS,NUMEROHAB,PRECIO,SERVICIOS,TAMANO,CATEGORIA,IDDOMINIO,TIPOREGDOMINIO)
VALUES
(241, 5, 4, 7, 1,101, 30000, 'ninguno', '10 X 10', 'STANDAR', 131, 'HOSTAL');

INSERT INTO OFERTA
(ID, DESCUENTO,DIASACTIVA,DIASUSADA,FECHAFIN,FECHAINICIO,TIEMPOCONTRATO, ACTIVA, DISPONIBLE)
VALUES
(225, 0, 7, 7, '25-ENE-2020', '11-ENE-2020', 'UNA SEMANA', '1', 'Y');

INSERT INTO OFERTAHABITACION
(IDOFERTA, IDHABITACION)
VALUES
(225, 241);


INSERT INTO HABITACION
(ID, CAPACIDAD,COMPARTIDACONCUANTOS,DIASUSADA,NUMEROESTRELLAS,NUMEROHAB,PRECIO,SERVICIOS,TAMANO,CATEGORIA,IDDOMINIO,TIPOREGDOMINIO)
VALUES
(251, 5, 4, 6, 4,101, 30000, 'ninguno', '10 X 10', 'STANDAR', 141, 'HOSTAL');

INSERT INTO OFERTA
(ID, DESCUENTO,DIASACTIVA,DIASUSADA,FECHAFIN,FECHAINICIO,TIEMPOCONTRATO, ACTIVA, DISPONIBLE)
VALUES
(230, 0, 7, 6, '18-ENE-2020', '11-ENE-2020', 'UNA SEMANA', '1', 'Y');

INSERT INTO OFERTAHABITACION
(IDOFERTA, IDHABITACION)
VALUES
(230, 251);

COMMIT;

--oferta proveedor EMPRESA
INSERT INTO HABITACION
(ID, CAPACIDAD,COMPARTIDACONCUANTOS,DIASUSADA,NUMEROESTRELLAS,NUMEROHAB,PRECIO,SERVICIOS,TAMANO,CATEGORIA,IDDOMINIO,TIPOREGDOMINIO)
VALUES
(261, 1, 0, 5, 1,101, 40000, 'LUZ, AGUA', '20 X 10', 'DOBLE', 151, 'EMPRESA');

INSERT INTO OFERTA
(ID, DESCUENTO,DIASACTIVA,DIASUSADA,FECHAFIN,FECHAINICIO,TIEMPOCONTRATO, ACTIVA, DISPONIBLE)
VALUES
(235, 0, 7,5, '08-ENE-2020', '01-ENE-2020', 'UNA SEMANA', '1', 'Y');

INSERT INTO OFERTAHABITACION
(IDOFERTA, IDHABITACION)
VALUES
(235, 261);


INSERT INTO HABITACION
(ID, CAPACIDAD,COMPARTIDACONCUANTOS,DIASUSADA,NUMEROESTRELLAS,NUMEROHAB,PRECIO,SERVICIOS,TAMANO,CATEGORIA,IDDOMINIO,TIPOREGDOMINIO)
VALUES
(271, 1, 0, 7, 1,101, 40000, 'LUZ, AGUA', '20 X 10', 'DOBLE', 151, 'EMPRESA');

INSERT INTO OFERTA
(ID, DESCUENTO,DIASACTIVA,DIASUSADA,FECHAFIN,FECHAINICIO,TIEMPOCONTRATO, ACTIVA, DISPONIBLE)
VALUES
(240, 0, 7,7, '18-ENE-2020', '11-ENE-2020', 'UNA SEMANA', '1', 'Y');

INSERT INTO OFERTAHABITACION
(IDOFERTA, IDHABITACION)
VALUES
(240, 271);


INSERT INTO HABITACION
(ID, CAPACIDAD,COMPARTIDACONCUANTOS,DIASUSADA,NUMEROESTRELLAS,NUMEROHAB,PRECIO,SERVICIOS,TAMANO,CATEGORIA,IDDOMINIO,TIPOREGDOMINIO)
VALUES
(281, 1, 0, 2, 1,101, 40000, 'LUZ, AGUA', '20 X 10', 'DOBLE', 161, 'EMPRESA');

INSERT INTO OFERTA
(ID, DESCUENTO,DIASACTIVA,DIASUSADA,FECHAFIN,FECHAINICIO,TIEMPOCONTRATO, ACTIVA, DISPONIBLE)
VALUES
(245, 0, 7,2, '08-ENE-2020', '01-ENE-2020', 'UNA SEMANA', '1', 'Y');

INSERT INTO OFERTAHABITACION
(IDOFERTA, IDHABITACION)
VALUES
(245, 281);

COMMIT;

--oferta proveedor PROPIETARIO
INSERT INTO APARTAMENTO
(NUMEROHABITACIONES, SERVICIOS,UBICACION,IDPROPIETARIO,TIPOIDENTIFICACION)
VALUES
(2, 'LUZ, AGUA', 'CL 1', 171, 'CC');

INSERT INTO OFERTA
(ID, DESCUENTO,DIASACTIVA,DIASUSADA,FECHAFIN,FECHAINICIO,TIEMPOCONTRATO, ACTIVA, DISPONIBLE)
VALUES
(250, 0, 7,7, '08-ENE-2020', '01-ENE-2020', 'UNA SEMANA', '1', 'Y');

INSERT INTO OFERTAAPARTAMENTO
(IDOFERTA, IDAPARTAMENTO)
VALUES
(250, 'CL 1');


INSERT INTO APARTAMENTO
(NUMEROHABITACIONES, SERVICIOS,UBICACION,IDPROPIETARIO,TIPOIDENTIFICACION)
VALUES
(2, 'LUZ, AGUA', 'CL 2', 171, 'CC');

INSERT INTO OFERTA
(ID, DESCUENTO,DIASACTIVA,DIASUSADA,FECHAFIN,FECHAINICIO,TIEMPOCONTRATO, ACTIVA, DISPONIBLE)
VALUES
(255, 0, 7,7, '18-ENE-2020', '11-ENE-2020', 'UNA SEMANA', '1', 'Y');

INSERT INTO OFERTAAPARTAMENTO
(IDOFERTA, IDAPARTAMENTO)
VALUES
(255, 'CL 2');


INSERT INTO APARTAMENTO
(NUMEROHABITACIONES, SERVICIOS,UBICACION,IDPROPIETARIO,TIPOIDENTIFICACION)
VALUES
(2, 'LUZ, AGUA', 'CL 3', 181, 'CC');

INSERT INTO OFERTA
(ID, DESCUENTO,DIASACTIVA,DIASUSADA,FECHAFIN,FECHAINICIO,TIEMPOCONTRATO, ACTIVA, DISPONIBLE)
VALUES
(260, 0, 7,3, '08-ENE-2020', '01-ENE-2020', 'UNA SEMANA', '1', 'Y');

INSERT INTO OFERTAAPARTAMENTO
(IDOFERTA, IDAPARTAMENTO)
VALUES
(260, 'CL 3');

COMMIT;

--oferta proveedor VECINOS
INSERT INTO VIVIENDA
(CARACTERISTICASSEGURO, MENAJE,NUMERODIASUSADA,NUMEROHABITACIONES,SERVICIOS,UBICACION,IDVECINO,TIPOIDENTIFICACION)
VALUES
('SEGURO CONTRA INCENDIOS', '2 CAMAS,2 ARMARIOS', 3, 2, 'LUZ, AGUA','CL 4', 191, 'CC');

INSERT INTO OFERTA
(ID, DESCUENTO,DIASACTIVA,DIASUSADA,FECHAFIN,FECHAINICIO,TIEMPOCONTRATO, ACTIVA, DISPONIBLE)
VALUES
(265, 0, 7,3, '08-ENE-2020', '01-ENE-2020', 'UNA SEMANA', '1', 'Y');

INSERT INTO OFERTAVIVIENDA
(IDOFERTA, IDVIVIENDA)
VALUES
(265, 'CL 4');


INSERT INTO VIVIENDA
(CARACTERISTICASSEGURO, MENAJE,NUMERODIASUSADA,NUMEROHABITACIONES,SERVICIOS,UBICACION,IDVECINO,TIPOIDENTIFICACION)
VALUES
('SEGURO CONTRA INCENDIOS', '2 CAMAS,2 ARMARIOS', 5, 2, 'LUZ, AGUA','CL 5', 191, 'CC');

INSERT INTO OFERTA
(ID, DESCUENTO,DIASACTIVA,DIASUSADA,FECHAFIN,FECHAINICIO,TIEMPOCONTRATO, ACTIVA, DISPONIBLE)
VALUES
(270, 0, 7,5, '18-ENE-2020', '11-ENE-2020', 'UNA SEMANA', '1', 'Y');

INSERT INTO OFERTAVIVIENDA
(IDOFERTA, IDVIVIENDA)
VALUES
(270, 'CL 5');


INSERT INTO VIVIENDA
(CARACTERISTICASSEGURO, MENAJE,NUMERODIASUSADA,NUMEROHABITACIONES,SERVICIOS,UBICACION,IDVECINO,TIPOIDENTIFICACION)
VALUES
('SEGURO CONTRA INCENDIOS', '2 CAMAS,2 ARMARIOS', 7, 2, 'LUZ, AGUA','CL 6', 200, 'CC');

INSERT INTO OFERTA
(ID, DESCUENTO,DIASACTIVA,DIASUSADA,FECHAFIN,FECHAINICIO,TIEMPOCONTRATO, ACTIVA, DISPONIBLE)
VALUES
(275, 0, 7,7, '08-ENE-2020', '01-ENE-2020', 'UNA SEMANA', '1', 'Y');

INSERT INTO OFERTAVIVIENDA
(IDOFERTA, IDVIVIENDA)
VALUES
(275, 'CL 6');

COMMIT;