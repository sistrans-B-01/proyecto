SELECT CLI.NOMBRE, cli.tipocliente, SUM(RES.COSTOPAGADO) AS DINEROPAGADO, (RES.FECHAIDA - RES.FECHALLEGADA) AS DIAS, COUNT(res.id) AS RESERVAS 
FROM CLIENTE CLI, RESERVA RES
WHERE(
      cli.numeroidentificacion = res.idcliente
      )
GROUP BY CLI.NOMBRE, cli.tipocliente, res.fechallegada, res.fechaida
;