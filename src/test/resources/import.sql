/* Populate tables */
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Andres', 'Guzman', 'profesor@bolsadeideas.com', '2017-08-01', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('John', 'Doe', 'john.doe@gmail.com', '2017-08-02', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Linus', 'Torvalds', 'linus.torvalds@gmail.com', '2017-08-03', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Jane', 'Doe', 'jane.doe@gmail.com', '2017-08-04', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Rasmus', 'Lerdorf', 'rasmus.lerdorf@gmail.com', '2017-08-05', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Erich', 'Gamma', 'erich.gamma@gmail.com', '2017-08-06', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Richard', 'Helm', 'richard.helm@gmail.com', '2017-08-07', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Ralph', 'Johnson', 'ralph.johnson@gmail.com', '2017-08-08', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('John', 'Vlissides', 'john.vlissides@gmail.com', '2017-08-09', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('James', 'Gosling', 'james.gosling@gmail.com', '2017-08-010', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Bruce', 'Lee', 'bruce.lee@gmail.com', '2017-08-11', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Johnny', 'Doe', 'johnny.doe@gmail.com', '2017-08-12', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('John', 'Roe', 'john.roe@gmail.com', '2017-08-13', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Jane', 'Roe', 'jane.roe@gmail.com', '2017-08-14', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Richard', 'Doe', 'richard.doe@gmail.com', '2017-08-15', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Janie', 'Doe', 'janie.doe@gmail.com', '2017-08-16', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Phillip', 'Webb', 'phillip.webb@gmail.com', '2017-08-17', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Stephane', 'Nicoll', 'stephane.nicoll@gmail.com', '2017-08-18', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Sam', 'Brannen', 'sam.brannen@gmail.com', '2017-08-19', '');  
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Juergen', 'Hoeller', 'juergen.Hoeller@gmail.com', '2017-08-20', ''); 
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Janie', 'Roe', 'janie.roe@gmail.com', '2017-08-21', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('John', 'Smith', 'john.smith@gmail.com', '2017-08-22', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Joe', 'Bloggs', 'joe.bloggs@gmail.com', '2017-08-23', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('John', 'Stiles', 'john.stiles@gmail.com', '2017-08-24', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Richard', 'Roe', 'stiles.roe@gmail.com', '2017-08-25', '');

/* Populate tabla productos */
INSERT INTO productos (nombre, precio, create_at) VALUES('Panasonic Pantalla LCD', 259990, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('Sony Camara digital DSC-W320B', 123490, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('Apple iPod shuffle', 1499990, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('Sony Notebook Z110', 37990, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('Hewlett Packard Multifuncional F2280', 69990, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('Bianchi Bicicleta Aro 26', 69990, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('Mica Comoda 5 Cajones', 299990, NOW());

/* Creamos algunas facturas */
INSERT INTO facturas (descripcion, observacion, cliente_id, create_at) VALUES('Factura equipos de oficina', null, 1, NOW());
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(1, 1, 1);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(2, 1, 4);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(1, 1, 5);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(1, 1, 7);

INSERT INTO facturas (descripcion, observacion, cliente_id, create_at) VALUES('Factura Bicicleta', 'Alguna nota importante!', 1, NOW());
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(3, 2, 6);

/* Creamos algunos usuarios con sus roles */
INSERT INTO `users` (username, password, enabled) VALUES ('paciente','$2a$10$O9wxmH/AeyZZzIS09Wp8YOEMvFnbRVJ8B4dmAMVSGloR62lj.yqXG',1);
INSERT INTO `users` (username, password, enabled) VALUES ('admin','$2a$10$DOMDxjYyfZ/e7RcBfUpzqeaCs8pLgcizuiQWXPkU35nOhZlFcE9MS',1);
INSERT INTO `users` (username, password, enabled) VALUES ('doctor','$2a$10$DOMDxjYyfZ/e7RcBfUpzqeaCs8pLgcizuiQWXPkU35nOhZlFcE9MS',1);


INSERT INTO `authorities` (user_id, authority) VALUES (1,'ROLE_PACIENTE');
INSERT INTO `authorities` (user_id, authority) VALUES (1,'ROLE_USER');
INSERT INTO `authorities` (user_id, authority) VALUES (2,'ROLE_ADMIN');
INSERT INTO `authorities` (user_id, authority) VALUES (3,'ROLE_DOCTOR');

/** Estados **/
INSERT INTO estados (nombre) VALUES('ACTIVO');
INSERT INTO estados (nombre) VALUES('CANCELADO');
INSERT INTO estados (nombre) VALUES('INACTIVO');

/** Creacion de especialidades */
INSERT INTO especialidades (nombre, create_at) VALUES('Odontolog??a', NOW());
INSERT INTO especialidades (nombre, create_at) VALUES('Fisioterapia', NOW());
INSERT INTO especialidades (nombre, create_at) VALUES('Medicina General', NOW());
INSERT INTO especialidades (nombre, create_at) VALUES('Cardiolog??a', NOW());

/** Creacion de doctores */
INSERT INTO doctores (correo,create_at,direccion,nombres,apellidos,telefono, username) VALUES ('correo@correo.com',now(),'cra 14 #20-15','doctor 1','apellido 1','3204960202', 3);
INSERT INTO doctores (correo,create_at,direccion,nombres,apellidos,telefono, username) VALUES ('correo@correo.com',now(),'cra 14 #20-15','doctor 2','apellido 2','3204960202', null);
INSERT INTO doctores (correo,create_at,direccion,nombres,apellidos,telefono, username) VALUES ('correo@correo.com',now(),'cra 14 #20-15','doctor 3','apellido 3','3204960202', null);
INSERT INTO doctores (correo,create_at,direccion,nombres,apellidos,telefono, username) VALUES ('correo@correo.com',now(),'cra 14 #20-15','doctor 4','apellido 4','3204960202', null);


/** Creacion de pacientes */
INSERT INTO pacientes (identificacion,apellidos,ciudad,correo,create_at,direccion,genero,nombres,telefonos, username) VALUES ('123','Villamil','Zipaquira','paciente@paciente.com',now(),'calle 3 #14-14','M','Carlos Andres','8554636',1);
INSERT INTO pacientes (identificacion,apellidos,ciudad,correo,create_at,direccion,genero,nombres,telefonos, username) VALUES ('124','Villamil','Zipaquira','paciente@paciente.com',now(),'calle 3 #14-14','M','paciente 2','8554636',null);
INSERT INTO pacientes (identificacion,apellidos,ciudad,correo,create_at,direccion,genero,nombres,telefonos, username) VALUES ('125','Villamil','Zipaquira','paciente@paciente.com',now(),'calle 3 #14-14','M','paciente 3','8554636',null);
INSERT INTO pacientes (identificacion,apellidos,ciudad,correo,create_at,direccion,genero,nombres,telefonos, username) VALUES ('126','Villamil','Zipaquira','paciente@paciente.com',now(),'calle 3 #14-14','M','paciente 4','8554636',null);
INSERT INTO pacientes (identificacion,apellidos,ciudad,correo,create_at,direccion,genero,nombres,telefonos, username) VALUES ('127','Villamil','Zipaquira','paciente@paciente.com',now(),'calle 3 #14-14','M','paciente 5','8554636',null);
INSERT INTO pacientes (identificacion,apellidos,ciudad,correo,create_at,direccion,genero,nombres,telefonos, username) VALUES ('128','Villamil','Zipaquira','paciente@paciente.com',now(),'calle 3 #14-14','M','paciente 6','8554636',null);



/* Creacion de Citas */
INSERT INTO citas (create_at,hora_cita,id_doctor,id_especialidad,id_paciente, tarifa, id_estado) VALUES (now(),'09:20',1,1,1,50000,1);
INSERT INTO citas (create_at,hora_cita,id_doctor,id_especialidad,id_paciente, tarifa, id_estado) VALUES (now(),'10:23',1,2,1,50000,1);
INSERT INTO citas (create_at,hora_cita,id_doctor,id_especialidad,id_paciente, tarifa, id_estado) VALUES (now(),'13:30',1,3,1,50000,1);
INSERT INTO citas (create_at,hora_cita,id_doctor,id_especialidad,id_paciente, tarifa, id_estado) VALUES (now(),'07:00',1,1,2,50000,1);

/* Especialidades doctores*/
INSERT INTO especialidades_doctores (id_doctor,id_especialidad) VALUES (1,1);
INSERT INTO especialidades_doctores (id_doctor,id_especialidad) VALUES (1,2);
INSERT INTO especialidades_doctores (id_doctor,id_especialidad) VALUES (1,3);
INSERT INTO especialidades_doctores (id_doctor,id_especialidad) VALUES (2,3);








