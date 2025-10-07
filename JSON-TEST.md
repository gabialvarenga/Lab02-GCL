# 🧪 JSONs de Teste para o Sistema de Locadora

## 📋 **ÍNDICE**
1. [Autenticação](#1-autenticação)
2. [Cadastro de Automóveis](#2-cadastro-de-automóveis)
3. [Cadastro de Empregos](#3-cadastro-de-empregos)
4. [Criação de Pedidos](#4-criação-de-pedidos)
5. [Modificação de Pedidos](#5-modificação-de-pedidos)
6. [Aprovação de Pedidos](#6-aprovação-de-pedidos)
7. [Contratos](#7-contratos)
8. [Crédito](#8-crédito)
9. [Testes de Validação](#9-testes-de-validação)
10. [Fluxos Completos](#10-fluxos-completos-de-teste)

---

## 1. **AUTENTICAÇÃO**

### 1.1 Registrar Cliente
**Endpoint:** `POST /api/auth/register`

```json
{
  "nome": "João Silva",
  "email": "joao.silva@email.com",
  "password": "senha123",
  "role": "CLIENTE",
  "cpf": "123.456.789-00",
  "rg": "12.345.678-9",
  "endereco": "Rua das Flores, 123, São Paulo - SP",
  "profissao": "Engenheiro Civil"
}
```

### 1.2 Registrar Outro Cliente
**Endpoint:** `POST /api/auth/register`

```json
{
  "nome": "Maria Santos",
  "email": "maria.santos@email.com",
  "password": "senha456",
  "role": "CLIENTE",
  "cpf": "987.654.321-00",
  "rg": "98.765.432-1",
  "endereco": "Av. Paulista, 1000, São Paulo - SP",
  "profissao": "Médica"
}
```

### 1.3 Registrar Atendente
**Endpoint:** `POST /api/auth/register`

```json
{
  "nome": "Carlos Atendente",
  "email": "carlos.atendente@locadora.com",
  "password": "atendente123",
  "role": "ATENDENTE"
}
```

### 1.4 Registrar Banco
**Endpoint:** `POST /api/auth/register`

```json
{
  "nome": "Banco Financeiro S.A.",
  "email": "financeiro@banco.com",
  "password": "banco123",
  "role": "BANCO",
  "cnpj": "12.345.678/0001-99",
  "departamento": "Crédito Veicular"
}
```

### 1.5 Registrar Empresa
**Endpoint:** `POST /api/auth/register`

```json
{
  "nome": "AutoFrota Locadora",
  "email": "contato@autofrota.com",
  "password": "empresa123",
  "role": "EMPRESA",
  "cnpj": "98.765.432/0001-11",
  "departamento": "Gestão de Frotas"
}
```

### 1.6 Login (Use após registrar)
**Endpoint:** `POST /api/auth/login`

```json
{
  "email": "joao.silva@email.com",
  "password": "senha123"
}
```

---

## 2. **CADASTRO DE AUTOMÓVEIS**

### 2.1 Automóvel Popular
**Endpoint:** `POST /api/automoveis`

```json
{
  "matricula": "MAT001",
  "placa": "ABC-1234",
  "marca": "Fiat",
  "modelo": "Uno",
  "ano": 2022,
  "disponivel": true,
  "valorDiaria": 80.00
}
```

### 2.2 Automóvel Intermediário
**Endpoint:** `POST /api/automoveis`

```json
{
  "matricula": "MAT002",
  "placa": "DEF-5678",
  "marca": "Chevrolet",
  "modelo": "Onix",
  "ano": 2023,
  "disponivel": true,
  "valorDiaria": 120.00
}
```

### 2.3 Automóvel Sedan
**Endpoint:** `POST /api/automoveis`

```json
{
  "matricula": "MAT003",
  "placa": "GHI-9012",
  "marca": "Toyota",
  "modelo": "Corolla",
  "ano": 2023,
  "disponivel": true,
  "valorDiaria": 180.00
}
```

### 2.4 Automóvel SUV
**Endpoint:** `POST /api/automoveis`

```json
{
  "matricula": "MAT004",
  "placa": "JKL-3456",
  "marca": "Jeep",
  "modelo": "Compass",
  "ano": 2024,
  "disponivel": true,
  "valorDiaria": 250.00
}
```

### 2.5 Automóvel Premium
**Endpoint:** `POST /api/automoveis`

```json
{
  "matricula": "MAT005",
  "placa": "MNO-7890",
  "marca": "BMW",
  "modelo": "320i",
  "ano": 2024,
  "disponivel": true,
  "valorDiaria": 400.00
}
```

### 2.6 Automóvel Indisponível (para testes)
**Endpoint:** `POST /api/automoveis`

```json
{
  "matricula": "MAT006",
  "placa": "PQR-1234",
  "marca": "Volkswagen",
  "modelo": "Jetta",
  "ano": 2023,
  "disponivel": false,
  "valorDiaria": 200.00
}
```

---

## 3. **CADASTRO DE EMPREGOS**

### 3.1 Emprego Principal
**Endpoint:** `POST /api/clientes/{clienteId}/empregos`

```json
{
  "empregador": "TechCorp Engenharia Ltda",
  "rendimentoMensal": 8500.00
}
```

### 3.2 Emprego Secundário
**Endpoint:** `POST /api/clientes/{clienteId}/empregos`

```json
{
  "empregador": "Consultoria Silva & Santos",
  "rendimentoMensal": 3000.00
}
```

### 3.3 Emprego Terceiro (Autônomo)
**Endpoint:** `POST /api/clientes/{clienteId}/empregos`

```json
{
  "empregador": "Freelancer - Projetos Diversos",
  "rendimentoMensal": 2500.00
}
```

---

## 4. **CRIAÇÃO DE PEDIDOS**

### 4.1 Pedido Curta Duração (3 dias)
**Endpoint:** `POST /api/pedidos`

```json
{
  "automovelId": 1,
  "dataInicio": "2025-10-15",
  "dataFim": "2025-10-18",
  "observacoes": "Preciso para viagem de negócios - São Paulo para Campinas"
}
```

### 4.2 Pedido Média Duração (1 semana)
**Endpoint:** `POST /api/pedidos`

```json
{
  "automovelId": 2,
  "dataInicio": "2025-10-20",
  "dataFim": "2025-10-27",
  "observacoes": "Férias com a família - viagem para o litoral"
}
```

### 4.3 Pedido Longa Duração (15 dias)
**Endpoint:** `POST /api/pedidos`

```json
{
  "automovelId": 3,
  "dataInicio": "2025-11-01",
  "dataFim": "2025-11-15",
  "observacoes": "Substituição do carro que está em manutenção"
}
```

### 4.4 Pedido Mensal
**Endpoint:** `POST /api/pedidos`

```json
{
  "automovelId": 4,
  "dataInicio": "2025-11-10",
  "dataFim": "2025-12-10",
  "observacoes": "Contrato mensal - trabalho temporário em outra cidade"
}
```

### 4.5 Pedido Premium
**Endpoint:** `POST /api/pedidos`

```json
{
  "automovelId": 5,
  "dataInicio": "2025-12-01",
  "dataFim": "2025-12-05",
  "observacoes": "Evento especial - casamento no interior"
}
```

### 4.6 Pedido Final de Semana
**Endpoint:** `POST /api/pedidos`

```json
{
  "automovelId": 1,
  "dataInicio": "2025-10-25",
  "dataFim": "2025-10-27",
  "observacoes": "Final de semana - passeio em família"
}
```

---

## 5. **MODIFICAÇÃO DE PEDIDOS**

### 5.1 Alterar Datas
**Endpoint:** `PUT /api/pedidos/{id}`

```json
{
  "automovelId": 1,
  "dataInicio": "2025-10-16",
  "dataFim": "2025-10-19",
  "observacoes": "Mudança de planos - nova data de viagem"
}
```

### 5.2 Trocar Automóvel
**Endpoint:** `PUT /api/pedidos/{id}`

```json
{
  "automovelId": 2,
  "dataInicio": "2025-10-15",
  "dataFim": "2025-10-18",
  "observacoes": "Prefiro um modelo maior para a viagem"
}
```

### 5.3 Estender Período
**Endpoint:** `PUT /api/pedidos/{id}`

```json
{
  "automovelId": 1,
  "dataInicio": "2025-10-15",
  "dataFim": "2025-10-22",
  "observacoes": "Necessito estender o período de locação por mais dias"
}
```

### 5.4 Adicionar Observações
**Endpoint:** `PUT /api/pedidos/{id}`

```json
{
  "automovelId": 1,
  "dataInicio": "2025-10-15",
  "dataFim": "2025-10-18",
  "observacoes": "Preciso do carro com GPS e cadeirinha para bebê"
}
```

---

## 6. **APROVAÇÃO DE PEDIDOS (Atendente)**

### 6.1 Aprovar Pedido
**Endpoint:** `POST /api/pedidos/{id}/aprovar`

**⚠️ Sem body - apenas o ID no path**

### 6.2 Rejeitar Pedido
**Endpoint:** `POST /api/pedidos/{id}/rejeitar`

```json
{
  "motivo": "Cliente não possui renda suficiente comprovada"
}
```

```json
{
  "motivo": "Documentação incompleta - falta RG autenticado"
}
```

```json
{
  "motivo": "Cliente possui débitos pendentes com a locadora"
}
```

### 6.3 Enviar para Análise do Banco
**Endpoint:** `POST /api/pedidos/{id}/enviar-analise`

**⚠️ Sem body - apenas o ID no path**

---

## 7. **CONTRATOS**

### 7.1 Criar Contrato de Aluguel
**Endpoint:** `POST /api/contratos`

```json
{
  "pedidoAluguelId": 1,
  "dataAssinatura": "2025-10-14",
  "dataRetirada": "2025-10-15",
  "dataDevolucao": "2025-10-18",
  "valorTotal": 240.00,
  "valorCaucao": 500.00,
  "tipoContrato": "ALUGUEL"
}
```

### 7.2 Criar Contrato com Crédito
**Endpoint:** `POST /api/contratos`

```json
{
  "pedidoAluguelId": 3,
  "dataAssinatura": "2025-10-31",
  "dataRetirada": "2025-11-01",
  "dataDevolucao": "2025-11-15",
  "valorTotal": 2520.00,
  "valorCaucao": 1000.00,
  "tipoContrato": "CREDITO"
}
```

### 7.3 Assinar Contrato
**Endpoint:** `PUT /api/contratos/{id}/assinar`

**⚠️ Sem body - apenas o ID no path**

### 7.4 Registrar Retirada
**Endpoint:** `PUT /api/contratos/{id}/retirada`

```json
{
  "quilometragemInicial": 45230
}
```

### 7.5 Registrar Devolução
**Endpoint:** `PUT /api/contratos/{id}/devolucao`

```json
{
  "quilometragemFinal": 45850
}
```

---

## 8. **CRÉDITO**

### 8.1 Conceder Crédito Curto Prazo (6 meses)
**Endpoint:** `POST /api/creditos`

```json
{
  "pedidoId": 1,
  "valorFinanciado": 1500.00,
  "prazoMeses": 6,
  "parcelas": 6
}
```

### 8.2 Conceder Crédito Médio Prazo (12 meses)
**Endpoint:** `POST /api/creditos`

```json
{
  "pedidoId": 3,
  "valorFinanciado": 3000.00,
  "prazoMeses": 12,
  "parcelas": 12
}
```

### 8.3 Conceder Crédito Longo Prazo (24 meses)
**Endpoint:** `POST /api/creditos`

```json
{
  "pedidoId": 4,
  "valorFinanciado": 7500.00,
  "prazoMeses": 24,
  "parcelas": 24
}
```

### 8.4 Conceder Crédito Parcelado (entrada + parcelas)
**Endpoint:** `POST /api/creditos`

```json
{
  "pedidoId": 5,
  "valorFinanciado": 5000.00,
  "prazoMeses": 18,
  "parcelas": 17
}
```

**OBS:** Neste caso, 1 parcela como entrada e 17 parcelas restantes.

---

## 9. **TESTES DE VALIDAÇÃO (Devem Retornar Erro)**

### 9.1 Pedido com Data no Passado
**Endpoint:** `POST /api/pedidos`

```json
{
  "automovelId": 1,
  "dataInicio": "2025-09-01",
  "dataFim": "2025-09-05",
  "observacoes": "Data no passado - deve falhar"
}
```
**❌ Erro esperado:** 400 - "Data de início não pode ser no passado"

---

### 9.2 Pedido com Data Fim Antes do Início
**Endpoint:** `POST /api/pedidos`

```json
{
  "automovelId": 1,
  "dataInicio": "2025-10-20",
  "dataFim": "2025-10-15",
  "observacoes": "Data fim antes do início - deve falhar"
}
```
**❌ Erro esperado:** 400 - "Data fim deve ser posterior à data início"

---

### 9.3 Pedido com Automóvel Indisponível
**Endpoint:** `POST /api/pedidos`

```json
{
  "automovelId": 6,
  "dataInicio": "2025-10-15",
  "dataFim": "2025-10-18",
  "observacoes": "Automóvel indisponível - deve falhar"
}
```
**❌ Erro esperado:** 400 - "Automóvel não está disponível"

---

### 9.4 Pedido com Automóvel Inexistente
**Endpoint:** `POST /api/pedidos`

```json
{
  "automovelId": 999,
  "dataInicio": "2025-10-15",
  "dataFim": "2025-10-18",
  "observacoes": "ID inexistente - deve falhar"
}
```
**❌ Erro esperado:** 404 - "Automóvel não encontrado"

---

### 9.5 Login com Credenciais Inválidas
**Endpoint:** `POST /api/auth/login`

```json
{
  "email": "usuario@inexistente.com",
  "password": "senhaerrada"
}
```
**❌ Erro esperado:** 401 - "Credenciais inválidas"

---

### 9.6 Registro com Email Duplicado
**Endpoint:** `POST /api/auth/register`

```json
{
  "nome": "Pedro Silva",
  "email": "joao.silva@email.com",
  "password": "senha789",
  "role": "CLIENTE",
  "cpf": "111.222.333-44",
  "rg": "11.222.333-4",
  "endereco": "Rua B, 456",
  "profissao": "Professor"
}
```
**❌ Erro esperado:** 400 - "Email já cadastrado"

---

### 9.7 Modificar Pedido de Outro Cliente
**Cliente A logado tentando modificar pedido do Cliente B**

**❌ Erro esperado:** 403 - "Acesso negado" ou 404 - "Pedido não encontrado"

---

### 9.8 Cliente Tentando Aprovar Próprio Pedido
**Endpoint:** `POST /api/pedidos/{id}/aprovar`

**❌ Erro esperado:** 403 - "Acesso negado - apenas ATENDENTE pode aprovar"

---

## 10. **FLUXOS COMPLETOS DE TESTE**

### **Cenário 1: Locação Simples (Sucesso)** ✅

1. ✅ **Registrar Cliente:** João Silva (JSON 1.1)
2. ✅ **Login:** João Silva (JSON 1.6)
3. ✅ **Cadastrar Automóvel:** Fiat Uno (JSON 2.1) - Login Atendente
4. ✅ **Criar Pedido:** 3 dias, Fiat Uno (JSON 4.1)
5. ✅ **Consultar Status:** `GET /api/pedidos/{id}/status` → PENDENTE
6. ✅ **Login:** Atendente (JSON 1.3 → 1.6)
7. ✅ **Aprovar Pedido:** `POST /api/pedidos/{id}/aprovar`
8. ✅ **Consultar Status:** APROVADO
9. ✅ **Criar Contrato:** tipo ALUGUEL (JSON 7.1)
10. ✅ **Assinar Contrato:** `PUT /api/contratos/{id}/assinar`
11. ✅ **Registrar Retirada:** (JSON 7.4)
12. ✅ **Registrar Devolução:** (JSON 7.5)

---

### **Cenário 2: Locação com Crédito (Sucesso)** ✅

1. ✅ **Registrar Cliente:** Maria Santos (JSON 1.2)
2. ✅ **Login:** Maria Santos
3. ✅ **Cadastrar Emprego:** TechCorp (JSON 3.1)
4. ✅ **Cadastrar Automóvel:** Toyota Corolla (JSON 2.3)
5. ✅ **Criar Pedido:** 15 dias, Corolla (JSON 4.3)
6. ✅ **Login:** Atendente
7. ✅ **Enviar para Análise Bancária:** `POST /api/pedidos/{id}/enviar-analise`
8. ✅ **Login:** Banco (JSON 1.4 → 1.6)
9. ✅ **Analisar Financiamento:** Lógica no backend
10. ✅ **Conceder Crédito:** 12 parcelas (JSON 8.2)
11. ✅ **Aprovar Pedido:** Via Atendente
12. ✅ **Criar Contrato:** tipo CREDITO (JSON 7.2)

---

### **Cenário 3: Rejeição de Pedido** ❌

1. ✅ **Registrar Cliente**
2. ✅ **Login:** Cliente
3. ✅ **Criar Pedido**
4. ✅ **Login:** Atendente
5. ✅ **Rejeitar Pedido:** "Documentação incompleta" (JSON 6.2)
6. ✅ **Consultar Status:** REJEITADO

---

### **Cenário 4: Modificação de Pedido** ✏️

1. ✅ **Registrar Cliente**
2. ✅ **Login:** Cliente
3. ✅ **Criar Pedido**
4. ✅ **Consultar Pedido:** `GET /api/pedidos/{id}`
5. ✅ **Modificar Pedido:** novas datas (JSON 5.1)
6. ✅ **Consultar Status:** ainda PENDENTE

---

### **Cenário 5: Cancelamento de Pedido** 🚫

1. ✅ **Registrar Cliente**
2. ✅ **Login:** Cliente
3. ✅ **Criar Pedido**
4. ✅ **Cancelar Pedido:** `DELETE /api/pedidos/{id}`
5. ✅ **Consultar Status:** CANCELADO
6. ✅ **Verificar Automóvel:** `GET /api/automoveis/{id}` → disponível novamente

---

## 🎯 **RESUMO DE ENDPOINTS E JSONS**

| Ação | Método | Endpoint | JSON |
|------|--------|----------|------|
| Registrar Cliente | POST | `/api/auth/register` | 1.1, 1.2 |
| Registrar Atendente | POST | `/api/auth/register` | 1.3 |
| Registrar Banco | POST | `/api/auth/register` | 1.4 |
| Registrar Empresa | POST | `/api/auth/register` | 1.5 |
| Login | POST | `/api/auth/login` | 1.6 |
| Cadastrar Automóvel | POST | `/api/automoveis` | 2.1-2.6 |
| Cadastrar Emprego | POST | `/api/clientes/{id}/empregos` | 3.1-3.3 |
| Criar Pedido | POST | `/api/pedidos` | 4.1-4.6 |
| Modificar Pedido | PUT | `/api/pedidos/{id}` | 5.1-5.4 |
| Cancelar Pedido | DELETE | `/api/pedidos/{id}` | - |
| Consultar Status | GET | `/api/pedidos/{id}/status` | - |
| Aprovar Pedido | POST | `/api/pedidos/{id}/aprovar` | - |
| Rejeitar Pedido | POST | `/api/pedidos/{id}/rejeitar` | 6.2 |
| Enviar p/ Banco | POST | `/api/pedidos/{id}/enviar-analise` | - |
| Criar Contrato | POST | `/api/contratos` | 7.1-7.2 |
| Assinar Contrato | PUT | `/api/contratos/{id}/assinar` | - |
| Registrar Retirada | PUT | `/api/contratos/{id}/retirada` | 7.4 |
| Registrar Devolução | PUT | `/api/contratos/{id}/devolucao` | 7.5 |
| Conceder Crédito | POST | `/api/creditos` | 8.1-8.4 |

---

## 📝 **OBSERVAÇÕES IMPORTANTES**

### **Sobre Autenticação:**
- Todos os endpoints (exceto `/api/auth/register` e `/api/auth/login`) requerem autenticação JWT
- Use o token retornado no login no header: `Authorization: Bearer {token}`
- No Swagger, use o botão **"Authorize"** para inserir o token

### **Sobre IDs:**
- Os IDs nos JSONs (`automovelId`, `pedidoId`, `clienteId`, etc.) são exemplos
- Use os IDs retornados pelas operações de criação
- IDs começam em 1 e incrementam automaticamente

### **Sobre Datas:**
- Use formato ISO: `YYYY-MM-DD`
- Datas devem ser futuras para criar pedidos
- `dataFim` deve ser posterior a `dataInicio`

### **Sobre Permissões:**
- **CLIENTE:** Criar/modificar/cancelar seus próprios pedidos
- **ATENDENTE:** Aprovar/rejeitar pedidos, cadastrar automóveis
- **BANCO:** Analisar financiamentos, conceder créditos
- **EMPRESA:** Fornecer veículos

---

## **PRONTO PARA TESTAR!**

Use estes JSONs no Swagger (`http://localhost:8080/swagger-ui.html`) para validar todo o sistema!
