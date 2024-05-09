[![build](https://github.com/phm-unsam/backend-2024-losnohomeros/actions/workflows/build.yml/badge.svg?branch=main)](https://github.com/phm-unsam/backend-2024-losnohomeros/actions/workflows/build.yml) ![Coverage](./.github/badges/jacoco.svg) ![branches](./.github/badges/branches.svg)
## Noches Magicas

Repositorio donde se encuentra la logica de negocio del proyecto Noches Magicas realizado con kotlin y spring boot

#### Developers:
- Juan Caceffo
- Alejo Menini
- Pablo Foglia
- Sol Lopez
- Denise Deutsch

#### QUERYS


##### Devuelve el historial de tickets de determinado año para un usuario

```SQL
CREATE OR REPLACE FUNCTION public.history_tickets(IN "userId" bigint,IN yearr bigint)
RETURNS TABLE(quantity integer, ticketid bigint, showdateid bigint, showid bigint, seat character varying)
LANGUAGE 'plpgsql'
VOLATILE
PARALLEL UNSAFE
COST 100    ROWS 1000

AS $BODY$
BEGIN
RETURN QUERY
SELECT ti.quantity, ti.id, ti.show_date_id, ti.show_id, ti.seat
FROM public.ticket AS ti
JOIN public.spectator_tickets AS st ON st.tickets_id = ti.id
JOIN public.spectator AS s ON s.id = st.user_id
JOIN public.show_date AS sd ON sd.id = ti.show_date_id
WHERE s.id = "userId"
AND date_part('year',sd.date) = yearr;
END;
$BODY$;
```

##### Devuelve los usuarios tienen más de N tickets.

```SQL
 CREATE FUNCTION users_with_more_tickets_than(ticketsQuantity integer) RETURNS SETOF spectator AS $$
 BEGIN
     RETURN query
     SELECT s.*
     FROM spectator s
     JOIN spectator_tickets st ON s.id = st.user_id
     JOIN ticket t ON t.id = st.tickets_id
     GROUP BY s.id
     HAVING COUNT(*) > ticketsQuantity;
 END;
 $$ LANGUAGE plpgsql;
 ```