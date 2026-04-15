export interface Empleado {
  clave: string;
  nombre: string;
  direccion: string;
  telefono: string;
  version: number;
  departamentoClave: string | null;
}

export interface EmpleadoCreateRequest {
  clave: string;
  nombre: string;
  direccion: string;
  telefono: string;
  departamentoClave?: string | null;
}

export interface EmpleadoUpdateRequest {
  nombre: string;
  direccion: string;
  telefono: string;
  version: number;
  departamentoClave?: string | null;
}
