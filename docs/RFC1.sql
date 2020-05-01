SELECT DO.NOMBRE, PRO.NOMBRE, VEC.NOMBRE, SUM(RES.COSTOPAGADO)
FROM DOMINIO DO, PROPIETARIO PRO, VECINOS VEC, HABITACION HAB, VIVIENDA VIV, APARTAMENTO APA, OFERTA OFE, OFERTAHABITACION OFEHAB, OFERTAVIVIENDA OFEVIV, OFERTAAPARTAMENTO OFEAPA, RESERVA RES
WHERE  
        --UNIR DUE�OS CON SUS PROPIEDADES
        (
            (DO.REGISTRO = HAB.IDDOMINIO AND DO.TIPOREGISTRO = HAB.TIPOREGDOMINIO)
            OR (PRO.NUMEROIDENTIFICACION = APA.IDPROPIETARIO AND PRO.TIPOIDENTIFICACION = APA.TIPOIDENTIFICACION)
            OR (VEC.NUMEROIDENTIFICACION = VIV.IDVECINO AND VEC.TIPOIDENTIFICACION = VIV.TIPOIDENTIFICACION)
        )
        AND
        --UNIR LAS PROPIEDADES CON SUS OFERTAS
        (   
            (HAB.ID= OFEHAB.IDHABITACION AND OFEHAB.IDHABITACION = OFE.ID)
            OR(APA.UBICACION = OFEAPA.IDAPARTAMENTO AND OFEAPA.IDAPARTAMENTO = OFE.ID)
            OR(VIV.UBICACION = OFEVIV.IDVIVIENDA AND OFEVIV.IDVIVIENDA = OFE.ID)
        )
        AND
        --UNIR LAS OFERTAS CON LAS RESERVAS
        OFE.ID= RES.IDOFERTA
        --AND  RES.FECHAIDA > '31/12/2018'
GROUP BY DO.NOMBRE, PRO.NOMBRE, VEC.NOMBRE     
        
;