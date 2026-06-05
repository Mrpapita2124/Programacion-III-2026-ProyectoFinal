-- =====================================================
-- SCRIPT PARA REINICIAR DATOS CON PRÉSTAMOS Y ESTADOS
-- =====================================================

-- Papita debe aparecer: 2 Prestamos Correctos, 2 Atrasados y 1 Concluido

USE prestamos;

-- 1. Desactivar verificaciones de claves foráneas
SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE usuario;
-- 🔥 IMPORTANTE: Insertar un usuario base para que funcionen las FK
INSERT INTO usuario (id_usuario, nombre, apellido, correo_electronico, contraseña, capacidad_prestamo, url_foto, guardar, rol) VALUES
(1, 'Admin', 'Cuenta', 'admin@gmail.com', '$2a$10$hl3qLAIF/g8Q7qFfXCyQAOl1nob7qZethG18ZvovJBsb61shnlnx2', 100000.00, NULL, TRUE, 'admin');

-- SELECT * FROM usuario;


-- 2. Limpiar las tablas en orden inverso
TRUNCATE TABLE estado_prestamo;
TRUNCATE TABLE prestamo;
TRUNCATE TABLE cliente;

-- 3. Reactivar verificaciones
SET FOREIGN_KEY_CHECKS = 1;

-- 4. Insertar clientes
INSERT INTO cliente (id_usuario, nombre, apellido, edad, ine, domicilio, comprobante_domicilio, numero_celular, correo_electronico, empleo, telf_empleo, domicilio_empleo, ingresos_mensuales, numero_cuenta_bancaria, nombre_banco, curp, reputacion) VALUES
(1, 'Juan', 'Pérez García', 32, '.\\images\\f2df042a-508a-4bb1-88f7-be67c6bc4cb5.png', 'Av. Reforma 123, CDMX', '.\\PDFs\\db488464-e47b-434c-98be-848b20e6543c.pdf', '5512345678', 'juan.perez@email.com', 'Consultor TI', '5551234567', 'Av. Insurgentes 456, CDMX', 25000.00, '123456789012345678', 'BBVA', 'PEGJ920313HDFRRN09', 'buena'),
(1, 'María', 'López Hernández', 28, '.\\images\\f2df042a-508a-4bb1-88f7-be67c6bc4cb5.png', 'Calle Juárez 45, Guadalajara', '.\\PDFs\\db488464-e47b-434c-98be-848b20e6543c.pdf', '3312345678', 'maria.lopez@email.com', 'Médica', '3334567890', 'Hospital Central 789, GDL', 32000.00, '234567890123456789', 'Santander', 'LOHM950617MJCRNR03', 'excelente'),
(1, 'Carlos', 'Ramírez Díaz', 45, '.\\images\\f2df042a-508a-4bb1-88f7-be67c6bc4cb5.png', 'Blvd. Miguel Alemán 200, Monterrey', '.\\PDFs\\db488464-e47b-434c-98be-848b20e6543c.pdf', '8112345678', 'carlos.ramirez@email.com', 'Ingeniero Civil', '8187654321', 'Zona Industrial 1000, Mty', 45000.00, '345678901234567890', 'Banorte', 'RADC790101HNLZRL04', 'regular'),
(1, 'Ana', 'Martínez Sánchez', 37, '.\\images\\f2df042a-508a-4bb1-88f7-be67c6bc4cb5.png', 'Av. Universidad 500, Puebla', '.\\PDFs\\db488464-e47b-434c-98be-848b20e6543c.pdf', '2223456789', 'ana.martinez@email.com', 'Arquitecta', '2229876543', 'Zona Angelópolis 321, Pue', 38000.00, '456789012345678901', 'Citibanamex', 'MASA850403MPLNNN07', 'no medido'),
(1, 'Luis', 'Torres Jiménez', 29, '.\\images\\f2df042a-508a-4bb1-88f7-be67c6bc4cb5.png', 'Calle Madero 77, Querétaro', '.\\PDFs\\db488464-e47b-434c-98be-848b20e6543c.pdf', '4421234567', 'luis.torres@email.com', 'Abogado', '4427654321', 'Jurídico Norte 150, Qro', 30000.00, '567890123456789012', 'Scotiabank', 'TOJL950101HQTRRN05', 'excelente');

-- 5. Insertar préstamos de ejemplo
INSERT INTO prestamo (id_usuario, id_cliente, estado, monto, numero_quincenas, monto_quincenal, monto_total, interes, interes_retraso, fecha) VALUES
(1, 1, 'activo', 5000.00, 10, 550.00, 5500.00, 10.00, 5.00, '2026-05-01'),
(1, 2, 'activo', 8000.00, 12, 733.33, 8800.00, 10.00, 5.00, '2026-05-15'),
(1, 3, 'activo', 3000.00, 6, 550.00, 3300.00, 10.00, 5.00, '2026-04-01'),
(1, 4, 'activo', 10000.00, 15, 733.33, 11000.00, 10.00, 5.00, '2026-03-20'),
(1, 5, 'concluso', 6000.00, 10, 660.00, 6600.00, 10.00, 5.00, '2026-05-10');

-- 6. Insertar estados de préstamo (2 atrasados)
INSERT INTO estado_prestamo (id_prestamo, quincenas_restantes, monto_restante, fecha_proximo_pago, monto_proximo_pago, estado, dinero_atrasado) VALUES
(1, 8, 4400.00, '2026-06-15', 550.00, 'correcto', 0.00),
(2, 10, 7333.30, '2026-06-30', 733.33, 'correcto', 0.00),
(3, 5, 2750.00, '2026-05-01', 550.00, 'atrasado', 1100.00),
(4, 12, 8800.00, '2026-04-20', 733.33, 'atrasado', 1466.66);


-- 7. Mostrar resultados

-- ---- CLIENTES --------
SELECT * FROM cliente;
 -- SELECT * FROM prestamo;
 -- SELECT * FROM estado_prestamo;
