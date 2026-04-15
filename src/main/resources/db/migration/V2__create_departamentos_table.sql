CREATE TABLE departamentos (
  clave VARCHAR(100) PRIMARY KEY,
  nombre VARCHAR(100) NOT NULL
);

ALTER TABLE empleados
  ADD COLUMN departamento_clave VARCHAR(100);

ALTER TABLE empleados
  ADD CONSTRAINT fk_empleado_departamento
  FOREIGN KEY (departamento_clave) REFERENCES departamentos(clave);
