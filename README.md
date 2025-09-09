# Sistema de Aluguel de Carros

## Integrantes
* Carlos José Gomes Batista Figueiredo
* Gabriela Alvarenga Cardoso
* Luísa Oliveira Jardim

## Orientadores
* João Pedro Oliveira Batisteli

### Autenticação e Cadastro
- **HU001**: Como usuário, quero me cadastrar no sistema para poder acessar os serviços de aluguel.
- **HU002**: Como usuário, quero fazer login no sistema para utilizar a plataforma.
- **HU003**: Como usuário, quero fazer logout do sistema para manter minha sessão segura.

### Cliente
- **HU004**: Como cliente, quero criar um pedido de aluguel para poder alugar um veículo.
- **HU005**: Como cliente, quero modificar meu pedido de aluguel para atualizar minhas necessidades.
- **HU006**: Como cliente, quero consultar meus pedidos de aluguel para acompanhar o status.
- **HU007**: Como cliente, quero cancelar meu pedido de aluguel para retirar minha solicitação.
- **HU008**: Como usuário, quero pesquisar veículos disponíveis para escolher o que alugar.

### Agente (Empresa/Banco)
- **HU009**: Como agente, quero visualizar pedidos de aluguel para poder avaliá-los.
- **HU010**: Como agente, quero modificar pedidos de aluguel para atualizar termos.
- **HU011**: Como agente, quero aprovar ou rejeitar pedidos de aluguel para controlar o processo.
- **HU012**: Como agente, quero executar contratos aprovados para que os aluguéis possam prosseguir.

### Gestão de Veículos
- **HU013**: Como administrador, quero cadastrar veículos para que estejam disponíveis para aluguel.
- **HU014**: Como administrador, quero atualizar informações de veículos para manter os dados atualizados.

---

## Regras de Negócio

| Código  | Descrição                                                                 |
|---------|---------------------------------------------------------------------------|
| **RN001** | Apenas usuários cadastrados podem acessar o sistema.                    |
| **RN002** | Clientes só podem gerenciar seus próprios pedidos de aluguel.           |
| **RN003** | Agentes podem avaliar e modificar qualquer pedido de aluguel.           |
| **RN004** | Pedidos de aluguel devem ser analisados financeiramente antes da aprovação. |
| **RN005** | Apenas agentes podem aprovar ou rejeitar pedidos de aluguel.            |
| **RN006** | Pedidos aprovados tornam-se contratos executáveis.                      |
| **RN007** | Veículos podem ser registrados como propriedade de clientes, empresas ou bancos. |
| **RN008** | Clientes devem fornecer RG, CPF, Nome e Endereço.                       |
| **RN009** | Máximo de 3 empregadores e rendimentos por cliente.                     |
| **RN010** | Veículos devem ter matrícula, ano, marca, modelo e placa.               |
| **RN011** | Contratos de crédito podem ser associados ao aluguel de veículos.       |
| **RN012** | Clientes não podem avaliar pedidos de outros clientes.                  |
| **RN013** | Empresas e bancos (agentes) têm permissões especiais de modificação.    |
| **RN014** | Pedidos devem ter parecer positivo dos agentes para execução.           |
| **RN015** | Contratos só podem ser executados após aprovação financeira.            |
| **RN016** | Automóveis alugados devem ter registro de propriedade definido.         |
