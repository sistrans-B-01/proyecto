UPDATE OFERTA
SET oferta.activa='0'
WHERE OFERTA.ID= 205;

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

--update reserva
--set reserva.idoferta= oferta.id, reserva.id= reserva.id
--from oferta, reserva
--where(oferta.id in (select distinct oferta.id
--                    from oferta, reserva
--                    where (oferta.id!= reserva.idoferta 
--                          and oferta.fechainicio<= reserva.fechallegada and oferta.fechafin >= reserva.fechaida
--                          and reserva.id in( select reserva.id
--                                             from reserva, oferta
--                                               where (reserva.idoferta=oferta.id and oferta.activa='0')
--                                            )
--                          ) 
--                     ) 
--       and reserva.id in ( select reserva.id
--                                             from reserva, oferta
--                                             where (reserva.idoferta=oferta.id and oferta.activa='0')
--                          )
--               )         
                    
