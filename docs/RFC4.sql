SELECT *
FROM OFERTA OFE, OFERTAHABITACION OFEHAB, OFERTAVIVIENDA OFEVIV, OFERTAAPARTAMENTO OFEAPA, APARTAMENTO APA, VIVIENDA VIV, HABITACION HAB  
WHERE FECHAFIN <='20-MAR-2020' 
AND FECHAINICIO >='19-MAR-2020' 
AND (OFE.ID = OFEHAB.IDOFERTA
    OR OFE.ID = OFEVIV.IDOFERTA
    OR OFE.ID = OFEAPA.IDOFERTA)
AND (OFEHAB.IDHABITACION = HAB.ID
    OR OFEAPA.IDAPARTAMENTO = APA.UBICACION
    OR OFEVIV.IDVIVIENDA = VIV.UBICACION)
AND (HAB.SERVICIOS = 'LUZ, AGUA'
    OR VIV.SERVICIOS = 'LUZ, AGUA'
    OR APA.SERVICIOS = 'LUZ, AGUA'
    )
;