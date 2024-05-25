##### Devuelve las reservas de un tipo de asiento

```
CREATE OR REPLACE FUNCTION get_seat_counts(filter_id INT)
RETURNS TABLE(count BIGINT) AS $$
BEGIN
    RETURN QUERY (
        SELECT    
            COUNT(*) AS count
        FROM
            ticket AS tk
        WHERE
            tk.show_date_id = filter_id
        GROUP BY
            tk.show_date_id,
            tk.seat_id
    );
END;
$$ LANGUAGE plpgsql;
```

##### Devuelve la disponibilidad total de un tipo de asiento

```
CREATE OR REPLACE FUNCTION get_seat_capacity(SD_ID INT, SEAT_ID INT)
RETURNS TABLE(capacity INT) AS $$
BEGIN
    RETURN QUERY (
        SELECT  
            st.capacity
        FROM show_date as sd
        INNER JOIN show as s
            ON s.id = sd.show_id
        INNER JOIN facility as f
            ON f.id = s.facility_id
        INNER JOIN facility_seats as fst
            ON fst.facility_id = f.id
        INNER JOIN seat as st
            ON st.id = fst.seats_id
        WHERE s.id = SD_ID and st.id = SEAT_ID
    );
END;
$$ LANGUAGE plpgsql;
```

CREATE OR REPLACE FUNCTION check_seat_capacity()
RETURNS TRIGGER AS $$
DECLARE
total_reservations INT;
seat_capacity INT;
seat_id INT;
show_id INT;
BEGIN
show_id := NEW.show_date_id;

    -- Obtener todos los asientos disponibles para el espectáculo
    FOR seat_id IN SELECT st.id FROM seat AS st
                    INNER JOIN facility_seats AS fst ON fst.seat_id = st.id
                    INNER JOIN show AS s ON s.facility_id = fst.facility_id
                    WHERE s.id = show_id
    LOOP
        -- Obtener el número total de reservas para el asiento actual
        SELECT count INTO total_reservations FROM get_seat_counts(show_id, seat_id);
        
        -- Obtener la capacidad total del asiento actual
        SELECT capacity INTO seat_capacity FROM get_seat_capacity(show_id, seat_id);
        
        -- Comprobar si se ha alcanzado la capacidad máxima para el asiento actual
        IF total_reservations >= seat_capacity THEN
            RAISE EXCEPTION 'La capacidad máxima del asiento % ha sido alcanzada', seat_id;
        END IF;
    END LOOP;
    
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Crear el trigger
CREATE TRIGGER check_seat_capacity_trigger
AFTER INSERT ON ticket
FOR EACH ROW
EXECUTE FUNCTION check_seat_capacity();
