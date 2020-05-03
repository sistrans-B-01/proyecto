SELECT 'HABITACION' AS ALOJAMIENTO, MAX(SUMAS) AS MAXIMO, MEJORPAGADA, MAX(OCU) AS MAXOCU, MEJOROCUPA
FROM( SELECT SUM(RES.COSTOPAGADO) AS SUMAS, RES.FECHADEPAGO AS MEJORPAGADA, COUNT(disponible) AS OCU, res.fechallegada AS MEJOROCUPA
      FROM OFERTAHABITACION OFEHAB, OFERTA OFE, RESERVA RES
      WHERE( OFEHAB.IDOFERTA= OFE.ID AND RES.IDOFERTA= OFE.ID AND ofe.disponible='N')
      GROUP BY RES.FECHADEPAGO, res.fechallegada
      HAVING(RES.FECHADEPAGO <= '09-ENE-2020' AND RES.FECHADEPAGO>= '28-DIC-2020')
     )
WHERE ROWNUM <=5    
GROUP BY MEJORPAGADA, MEJOROCUPA     

UNION

SELECT 'VIVIENDA' AS ALOJAMIENTO, MAX(SUMAS) AS MAXIMO, MEJORPAGADA, MAX(OCU) AS MAXOCU, MEJOROCUPA
FROM( SELECT SUM(RES.COSTOPAGADO) AS SUMAS, RES.FECHADEPAGO AS MEJORPAGADA, COUNT(disponible) AS OCU, res.fechallegada AS MEJOROCUPA
      FROM OFERTAVIVIENDA OFEVIV, OFERTA OFE, RESERVA RES
      WHERE (OFEVIV.IDOFERTA= OFE.ID AND RES.IDOFERTA= OFE.ID AND ofe.disponible='N')
      GROUP BY RES.FECHADEPAGO, res.fechallegada
      HAVING(RES.FECHADEPAGO <= '09-ENE-2020' AND RES.FECHADEPAGO>= '28-DIC-2019')
    )
WHERE ROWNUM<= 5
GROUP BY MEJORPAGADA, MEJOROCUPA

UNION
SELECT 'APARTAMENTO' AS ALOJAMIENTO, MAX(SUMAS) AS MAXIMO, MEJORPAGADA, MAX(OCU) AS MAXOCU, MEJOROCUPA
FROM( SELECT SUM(RES.COSTOPAGADO) AS SUMAS, RES.FECHADEPAGO AS MEJORPAGADA, COUNT(disponible) AS OCU, res.fechallegada AS MEJOROCUPA
      FROM OFERTAAPARTAMENTO OFEAPT, OFERTA OFE, RESERVA RES
      WHERE (OFEAPT.IDOFERTA= OFE.ID AND RES.IDOFERTA= OFE.ID AND ofe.disponible='N')
      GROUP BY RES.FECHADEPAGO, res.fechallegada
      HAVING(RES.FECHADEPAGO <= '09-ENE-2020' AND RES.FECHADEPAGO>= '28-DIC-2019')
    )
WHERE ROWNUM<= 5
GROUP BY MEJORPAGADA, MEJOROCUPA
ORDER BY MAXIMO DESC;