[![build](https://github.com/phm-unsam/backend-2024-losnohomeros/actions/workflows/build.yml/badge.svg?branch=main)](https://github.com/phm-unsam/backend-2024-losnohomeros/actions/workflows/build.yml) ![Coverage](./.github/badges/jacoco.svg) ![branches](./.github/badges/branches.svg)
## Noches Magicas

Repositorio donde se encuentra la logica de negocio del proyecto Noches Magicas realizado con kotlin y spring boot

#### Developers:
- Juan Caceffo
- Alejo Menini
- Pablo Foglia
- Sol Lopez
- Denise Deutsch

#### INICIALIZACION DE LOS DOCKERS EN MODO MONO USAR
```shell
./init-compose.sh
```

#### INICIALIZACION DE LOS DOCKERS EN MODO SHARDING ELIMINAR LA BASE MONGO Y USAR
```shell
docker-compose -f ./Docker/shards/docker-compose.mongosh.yml up -d
# En windows puede ser necesario modificar el archivo de host en c:\windows\system32\drivers\etc
# e incluir en este todos los nombres de los containers ej.: 127.0.0.1 router1 router2 ... 
# en principio con incluir los routers debería funcionar, caso contrario incluir cada host. 
```

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

##### Lleva un control de las veces que un usuario modifica su saldo, de manera de saber: a) la fecha en la que se modificó, b) el nuevo saldo y el anterior saldo.
```SQL
CREATE TABLE balance_history (
                                 id SERIAL PRIMARY KEY,
                                 spectator_id INT,
                                 previous_balance DECIMAL(10,2),
                                 new_balance DECIMAL(10,2),
                                 change_date TIMESTAMP
);

CREATE OR REPLACE FUNCTION balance_trigger_function()
RETURNS TRIGGER AS $$
BEGIN
    IF EXISTS (SELECT 1 FROM balance_history WHERE spectator_id = NEW.id) THEN
UPDATE balance_history
SET previous_balance = OLD.balance,
    new_balance = NEW.balance,
    change_date = NOW()
WHERE spectator_id = NEW.id;
ELSE
        INSERT INTO balance_history (spectator_id, previous_balance, new_balance, change_date)
        VALUES (NEW.id, OLD.balance, NEW.balance, NOW());
END IF;

RETURN NULL;
END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER Update_saldo
    AFTER UPDATE OF balance ON Spectator
    FOR EACH ROW
    EXECUTE FUNCTION balance_trigger_function();
```


##### Lista las instalaciones que tengan más de 2 shows realizados.

```SQL
DROP VIEW IF EXISTS facilities_con_mas_de_2_shows;

CREATE VIEW facilities_con_mas_de_2_shows AS
SELECT s.facility_id, COUNT(DISTINCT s.id) as show_count
FROM Show s
         JOIN show_dates sd ON s.id = sd.show_id
         JOIN show_date sdate ON sd.dates_id = sdate.id
WHERE to_timestamp(sdate.id) < CURRENT_TIMESTAMP
GROUP BY s.facility_id
HAVING COUNT(DISTINCT s.id) >= 2;
```