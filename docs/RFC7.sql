SELECT 'HABITACION' AS ALOJAMIENTO, MAX(SUMAS) AS MAXIMO, MEJORPAGADA
FROM( SELECT SUM(RESERVA.COSTOPAGADO) AS SUMAS, RESERVA.FECHADEPAGO AS MEJORPAGADA
      FROM OFERTAHABITACION, OFERTA, RESERVA
      WHERE( OFERTAHABITACION.IDOFERTA= OFERTA.ID AND RESERVA.IDOFERTA= OFERTA.ID)
      GROUP BY RESERVA.FECHADEPAGO
      HAVING(RESERVA.FECHADEPAGO <= '10-ENE-2020' AND RESERVA.FECHADEPAGO>= '28-DIC-2019')
     )
WHERE ROWNUM <=5    
GROUP BY MEJORPAGADA     

UNION

SELECT 'VIVIENDA' AS ALOJAMIENTO, MAX(SUMAS) AS MAXIMO, MEJORPAGADA
FROM( SELECT SUM(RESERVA.COSTOPAGADO) AS SUMAS, RESERVA.FECHADEPAGO AS MEJORPAGADA
      FROM OFERTAVIVIENDA, OFERTA, RESERVA
      WHERE (OFERTAVIVIENDA.IDOFERTA= OFERTA.ID AND RESERVA.IDOFERTA= OFERTA.ID)
      GROUP BY RESERVA.FECHADEPAGO
      HAVING(RESERVA.FECHADEPAGO <= '10-ENE-2020' AND RESERVA.FECHADEPAGO>= '28-DIC-2019')
    )
WHERE ROWNUM<= 5
GROUP BY MEJORPAGADA

UNION
SELECT 'APARTAMENTO' AS ALOJAMIENTO, MAX(SUMAS) AS MAXIMO, MEJORPAGADA
FROM( SELECT SUM(RESERVA.COSTOPAGADO) AS SUMAS, RESERVA.FECHADEPAGO AS MEJORPAGADA
      FROM OFERTAAPARTAMENTO, OFERTA, RESERVA
      WHERE (OFERTAAPARTAMENTO.IDOFERTA= OFERTA.ID AND RESERVA.IDOFERTA= OFERTA.ID)
      GROUP BY RESERVA.FECHADEPAGO
      HAVING(RESERVA.FECHADEPAGO <= '10-ENE-2020' AND RESERVA.FECHADEPAGO>= '28-DIC-2019')
    )
WHERE ROWNUM<= 5
GROUP BY MEJORPAGADA
ORDER BY MAXIMO DESC;