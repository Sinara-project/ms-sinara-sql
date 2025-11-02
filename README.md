# ms-sinara-sql

O **ms-sinara-sql** é um microsserviço responsável por gerenciar dados relacionais do sistema **Sinara**, lidando com entidades como empresas, operários, planos, pagamentos e registros de ponto.  
Ele expõe múltiplos endpoints RESTful, divididos entre áreas **admin**, **user** e **public**, seguindo boas práticas de arquitetura e autenticação.

---

## Controllers e Endpoints

### AcessoDiarioUsuariosController
- **GET** `/api/acessoDiarioUsuarios/listar`
- **POST** `/api/acessoDiarioUsuarios/registrar`

---

### RegistroPontoController
- **GET** `/api/user/registroPonto/buscarPorId/{id}`
- **GET** `/api/user/registroPonto/listar`
- **POST** `/api/user/registroPonto/inserir`
- **DELETE** `/api/user/registroPonto/excluir/{id}`
- **PUT** `/api/user/registroPonto/atualizar/{id}`
- **GET** `/api/user/registroPonto/horarioEntradaSaida/{id}`
- **GET** `/api/user/registroPonto/listarStatusOperario/{idOperario}`
- **GET** `/api/user/registroPonto/ultimoTurno/{idOperario}`
- **GET** `/api/user/registroPonto/quantidadeRegistroPonto/{idOperario}`
- **GET** `/api/user/registroPonto/quantidadePontosPorEmpresa/{idEmpresa}`
- **GET** `/api/user/registroPonto/horasTrabalhadas/{idOperario}`
- **GET** `/api/user/registroPonto/bancoHoras/{idOperario}`

---

### PlanosController
- **GET** `/api/admin/planos/buscarPorId/{id}`
- **GET** `/api/admin/planos/listar`
- **POST** `/api/admin/planos/inserir`
- **DELETE** `/api/admin/planos/excluir/{id}`
- **PUT** `/api/admin/planos/atualizar/{id}`

---

### PagamentoController
- **GET** `/api/admin/pagamento/buscarPorId{id}`
- **GET** `/api/admin/pagamento/listar`
- **POST** `/api/admin/pagamento/inserir`
- **DELETE** `/api/admin/pagamento/excluir/{id}`
- **PUT** `/api/admin/pagamento/atualizar/{id}`

---

### OperarioController
- **GET** `/api/user/operario/buscarPorId/{id}`
- **GET** `/api/user/operario/listar`
- **POST** `/api/user/operario/inserir`
- **DELETE** `/api/user/operario/excluir/{id}`
- **PUT** `/api/user/operario/atualizar/{id}`
- **POST** `/api/user/operario/verificarSenha`
- **GET** `/api/user/operario/perfilOperario/{id}`
- **GET** `/api/user/operario/obterId/{cpf}`
- **GET** `/api/user/operario/listarOperariosPorIdEmpresa/{idEmpresa}`
- **POST** `/api/user/operario/loginOperario`
- **POST** `/api/user/operario/atualizarStatus`
- **POST** `/api/user/operario/uploadReconhecimento/{id}`
- **POST** `/api/user/operario/verificarFacial`

---

### EsqueciSenhaController
- **POST** `/esqueciMinhaSenha/enviarEmailRedefinicao`

---

### EmpresaController
- **GET** `/api/admin/empresa/buscarPorId/{id}`
- **GET** `/api/admin/empresa/listar`
- **POST** `/api/admin/empresa/inserir`
- **DELETE** `/api/admin/empresa/excluir/{id}`
- **PUT** `/api/admin/empresa/atualizar/{id}`
- **PATCH** `/api/admin/empresa/atualizarSenhaAreaRestrita/{id}`
- **PATCH** `/api/admin/empresa/atualizarSenha/{id}`
- **POST** `/api/admin/empresa/loginAreaRestrita`
- **GET** `/api/admin/empresa/listarPerfilEmpresa/{id}`
- **GET** `/api/admin/empresa/obterEmpresaPorCnpj/{cnpj}`
- **POST** `/api/admin/empresa/loginEmpresa`
- **POST** `/api/admin/empresa/rebaixarPlanos`
- **POST** `/api/admin/empresa/mudarPlanoParaPremium`

---

### CartaoCreditoController
- **GET** `/api/user/CartaoCredito/buscarPorId/{id}`
- **GET** `/api/user/CartaoCredito/listar`
- **POST** `/api/user/CartaoCredito/inserir`
- **DELETE** `/api/user/CartaoCredito/excluir/{id}`
- **PATCH** `/api/user/CartaoCredito/atualizar/{id}`
- **GET** `/api/user/CartaoCredito/validarCartaoCredito`

---

## Estrutura de Pastas
```
src/
├── config/
├── controller/
├── dto/
│ ├── request/
│ └── response/
├── exception/
├── model/
├── repository/
├── service/
└── validation/
```
---

## Tecnologias Utilizadas

- Java  
- Spring Boot 3+  
- PostgreSQL  
- Spring Data JPA  
- Jakarta Validation  
- OpenAPI / Swagger  
- Lombok
- Spring Security

---

## Documentação Swagger
A documentação interativa está disponível em:  
**[Swagger UI – ms-sinara-sql](https://ms-sinara-sql-oox0.onrender.com/swagger-ui/index.html)**

---
