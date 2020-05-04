select distinct oferta.id
from oferta, reserva
where reserva.idoferta = oferta.id and fechaida <= '01-ENE-2020'
      or oferta.id not in ( select oferta.id from oferta, reserva where reserva.idoferta = oferta.id)
