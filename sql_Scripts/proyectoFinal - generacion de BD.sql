drop database prestamos;
create database prestamos;
use prestamos; 

create table usuario (
    id_usuario int auto_increment primary key auto_increment,
    nombre varchar(45),
    apellido varchar(45),
    correo_electronico varchar(45),
    contraseña varchar(100),
    capacidad_prestamo double,
    url_foto varchar(50),
    guardar boolean,
    rol varchar(45) 
);
create table cliente (
    id_cliente int auto_increment primary key,
    id_usuario int not null,
    nombre varchar(45),
    apellido varchar(45),
    edad int,
    ine varchar(150),
    domicilio varchar(150),
    comprobante_domicilio varchar(150),
    numero_celular varchar(45),
    correo_electronico varchar(45),
    empleo varchar(45),
    telf_empleo varchar(45),
    domicilio_empleo varchar(45),
    ingresos_mensuales double,
    numero_cuenta_bancaria varchar(45),
    nombre_banco varchar(45),
    curp varchar(45),
    reputacion enum('excelente','buena','regular','mala','no medido'),
    foreign key (id_usuario) references usuario(id_usuario)
);

create table prestamo (
    id_prestamo int auto_increment primary key,
    id_usuario int,
    id_cliente int,
    estado enum('activo','concluso','cancelado'),
    monto double,
    numero_quincenas int,
    monto_quincenal double,
    monto_total double,
    interes double,
    interes_retraso double,
    fecha date,
    foreign key (id_usuario) references usuario(id_usuario),
    foreign key (id_cliente) references cliente(id_cliente)
);


create table estado_prestamo (
    id_estado_prestamo int auto_increment primary key,
    id_prestamo int,
    quincenas_restantes int,
    monto_restante double,
    fecha_proximo_pago date,
    monto_proximo_pago double,
    estado enum('correcto','atrasado'),
    dinero_atrasado double,
    foreign key (id_prestamo) references prestamo(id_prestamo)
);

