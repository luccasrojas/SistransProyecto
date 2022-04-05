--RF1
--Parametros: Rol
INSERT INTO ROL_USUARIO (rol) values (?);

--RF2
--Parametros: Nombre, Tipo_Documento, Numero_Documento, Correo, Contrasena, Rol_Usuario
INSERT INTO USUARIO (NOMBRE, TIPO_DOCUMENTO, NUMERO_DOCUMENTO, CORREO, CONTRASENA, ROL_USUARIO) values (?, ?, ?, ?, ?, ?);

--RF3
--Parametros:tipoHabitacion
INSERT INTO TIPO_HABITACION(TIPO_HABITACION) values (?);

--RF4
--Parametros: Nombre_Hotel, Numero, Tipo_Habitacion, Costo_Por_Noche, Descripcion, Capacidad_Min, Capacidad_Max
INSERT INTO HOTEL(NOMBRE_HOTEL, numero, tipo_habitacion, costo_por_noche, descripcion, capacidad_min, capacidad_max) values (?, ?, ?, ?, ?, ?, ?);

--RF5
--Parametros: Nombre_Hotel, ID_Servicio, Hora_Inicio, Hora_Fin, Tipo_Servicio
INSERT INTO SERVICIO(NOMBRE_HOTEL, ID_SERVICIO, HORA_INICIO, HORA_FIN, TIPO_SERVICIO) values (?, ?, ?, ?, ?);

--RF6
--Parametros: ID_Plan_Consumo, Nombre_Hotel, Nombre, Duracion_Min, Descuento
INSERT INTO PLAN_CONUSMO(ID_PLAN_CONSUMO, NOMBRE_HOTEL, nombre, DURACION_MIN, descuento) values (?, ?, ?, ?, ?);

--RF7
--Parametros: ID_Reserva_Habitacion, ID_Servicio, ID_Habitacion, ID_Plan_Consumo
INSERT INTO RESERVA_HABITACION(ID_RESERVA_HABITACION, FECHA_IN, FECHA_OUT, NUM_PERSONAS, CUENTA_MINIBAR, NOMBRE_HOTEL, ID_PLAN_CONSUMO, PAGADO) values (?, ?, ?, ?, ?, ?, ?, ?);

--RF8
--Parametros: ID_Reserva_Habitacion, ID_Servicio, ID_Habitacion, ID_Plan_Consumo
INSERT INTO RESERVA_SERVICIO(ID_RESERVA_SERVICIO, ID_SERVICIO, ID_RESERVA_HABITACION, fecha, duracion) values (?, ?, ?, ?, ?);

--RF9
--Parametros: ID_Reserva_Habitacion, ID_Servicio, ID_Habitacion, ID_Plan_Consumo
INSERT INTO HUESPED_RESERVA(ID_RESERVA_HABITACION, TIPO_DOCUMENTO, NUMERO_DOCUMENTO) values (?, ?, ?,?);

--RF10
--Parametros: ID_Cuenta, Costo, Concepto, Fecha, ID_Servicio, ID_Reserva_Habitacion, REGISTRADO_POR_TIPO_DOCUMENTO, REGISTRADO_POR_NUMERO_DOCUMENTO
INSERT INTO CUENTA_SERVICIO(ID_CUENTA, costo, concepto, fecha, ID_SERVICIO, ID_RESERVA_HABITACION, REGISTRADO_POR_TIPO_DOCUMENTO, REGISTRADO_POR_NUMERO_DOCUMENTO) values (?, ?, ?, ?, ?, ?, ?, ?)

--RF11 
--Parametros:idReserva
SELECT A.A_PAGAR + B.A_PAGAR AS A_PAGAR
FROM(
        (
            SELECT ID_RESERVA_HABITACION AS ID_RESERVA_HABITACION,SUM(COSTO) AS A_PAGAR
            FROM CUENTA_SERVICIO
            WHERE ID_RESERVA_HABITACION = ?
            GROUP BY ID_RESERVA_HABITACION
        ) A FULL OUTER JOIN
        (
            SELECT ID_RESERVA_HABITACION AS ID_RESERVA_HABITACION,CUENTA_MINIBAR AS A_PAGAR
            FROM RESERVA_HABITACION
            WHERE ID_RESERVA_HABITACION = ?
        ) B ON  A.ID_RESERVA_HABITACION = B.ID_RESERVA_HABITACION
    );

UPDATE RESERVA_HABITACION
SET pagado = 1
WHERE ID_RESERVA_HABITACION = 1;