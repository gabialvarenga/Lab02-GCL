# *Sistema de Aluguel de Carros*

## Integrantes
* Carlos José Gomes Batista Figueiredo
* Gabriela Alvarenga Cardoso
* Luísa Oliveira Jardim

## Orientador
* João Pedro Oliveira Batisteli


## *História de Usuários*

### Usuário
- **HU001**: Como usuário, quero me cadastrar no sistema para usar todos os recursos disponíveis.
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

## Requisitos

### Requisitos Funcionais (RF)

| RF   | Descrição                                                                                      | Complexidade |
|:-----|:----------------------------------------------------------------------------------------------|:------------|
| RF01 | Permitir cadastro de novos usuários (clientes e agentes).                                      | 2           |
| RF02 | Permitir login/logout de usuários cadastrados.                                                 | 1           |
| RF03 | Permitir ao cliente criar, modificar, consultar e cancelar pedidos de aluguel.                 | 2           |
| RF04 | Permitir ao cliente pesquisar veículos disponíveis.                                            | 1           |
| RF05 | Permitir ao agente visualizar, modificar, aprovar ou rejeitar pedidos de aluguel.              | 2           |
| RF06 | Permitir ao agente executar contratos aprovados.                                               | 2           |
| RF07 | Permitir ao administrador cadastrar e atualizar informações de veículos.                       | 1           |

### Requisitos Não Funcionais (RNF)

| RNF  | Descrição                                                                                      | Complexidade |
|:-----|:----------------------------------------------------------------------------------------------|:------------|
| RNF01| Apenas usuários cadastrados podem acessar o sistema.                                           | 1           |
| RNF02| Clientes só podem gerenciar seus próprios pedidos de aluguel.                                  | 2           |
| RNF03| Pedidos de aluguel devem ser analisados financeiramente antes da aprovação.                    | 3           |
| RNF04| Máximo de 3 empregadores e rendimentos por cliente.                                            | 1           |
| RNF05| Veículos devem ter matrícula, ano, marca, modelo e placa.                                      | 1           |
| RNF06| Contratos de crédito podem ser associados ao aluguel de veículos.                              | 2           |
| RNF07| Empresas e bancos (agentes) têm permissões especiais de modificação.                           | 2           |
| RNF08| Pedidos devem ter parecer positivo dos agentes para execução.                                  | 2           |
| RNF09| Contratos só podem ser executados após aprovação financeira.                                   | 2           |
| RNF10| Automóveis alugados devem ter registro de propriedade definido.                                | 1           |

---

## Projeto

### Diagrama de Casos de Uso

![UseCaseDiagram](/docs/DiagramaDeCasoDeUso.drawio.png)

### Diagrama de Classes

![UML](/docs/DiagramaDeClasse.png)

### Diagrama de Pacotes

![PackageDiagram]
