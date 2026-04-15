export interface Departamento {
  clave: string;
  nombre: string;
}

export interface DepartamentoCreateRequest {
  clave: string;
  nombre: string;
}

export interface DepartamentoUpdateRequest {
  nombre: string;
}
