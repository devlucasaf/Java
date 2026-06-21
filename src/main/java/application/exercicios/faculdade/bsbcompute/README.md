<img
    width=100%
    src="https://capsule-render.vercel.app/api?type=waving&color=A020F0&height=120&section=header"
/>

# ⚙️ BSB Compute – Sistema de Orquestração de Tarefas

Este repositório contém a implementação do sistema de orquestração de tarefas da BSB Compute, uma simulação de cluster de servidores de inferência responsável por distribuir requisições de IA em tempo real utilizando políticas de escalonamento de sistemas operacionais.

O objetivo é reproduzir o cenário de empresas de IA em nuvem, lidando com múltiplas requisições simultâneas e servidores com diferentes capacidades.

### Desenvolvedor
- **👤💻 [devlucasaf (Lucas Freitas)](https://github.com/devlucasaf)**

`projeto desenvolvido para a disciplina de Sistemas Operacionais da UniCEUB`

---

### 🎯 Objetivo do Sistema

- Distribuir requisições de IA entre múltiplos servidores simulados
- Garantir justiça e eficiência através de diferentes políticas de escalonamento
- Utilizar IPC (pipes, filas ou sockets) para comunicação entre processos
- Monitorar o desempenho do cluster
- Simular chegada contínua de novas requisições
  <br>

---

### 📁 Estrutura do projeto

```
BSB-Compute/
└── bsb-compute/
    ├── src/
    │   └── main/
    │       ├── config/
    │       │   ├── Config.java
    │       │   ├── RequisicaoConfig.java
    │       │   └── ServidorConfig.java
    │       ├── core/
    │       │   └── Master.java
    │       ├── model/
    │       │   ├── EventMessage.java
    │       │   ├── Request.java
    │       │   ├── TaskMessage.java
    │       │   └── WorkerInfo.java
    │       ├── scheduler/
    │       │   └── Scheduler.java
    │       ├── worker/
    │       │   └── Worker.java
    │       └── Main.java
    ├── out/production/bsb-compute/main
    ├── projeto_pratico-2025.2.pdf
    └── README.md
```

---

### 🛠️ Tecnologias Utilizadas
<img
    align="left"
    alt="Java"
    title="Java"
    width="30px"
    style="padding-right: 10px;"
    src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/java/java-original.svg"
/>
<img
    align="left"
    alt="IntelliJ IDEA"
    title="IntelliJ IDEA"
    width="30px"
    style="padding-right: 10px;"
    src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/intellij/intellij-original.svg"
/>
<img
    align="left"
    alt="GitHub"
    title="GitHub"
    width="30px"
    style="padding-right: 10px;"
    src="https://skillicons.dev/icons?i=github"
/>

<br><br>

---

### 🧩 Componentes do Sistema:

``🧠 Orquestrador Central (Master)``
- Receber novas requisições em tempo real
- Gerenciar a fila de tarefas
- Aplicar a política de escalonamento selecionada
- Enviar tarefas para os servidores via IPC
- Coletar resultados e métricas

<br>

``🖥️ Servidores de Inferência (Workers)``
- Cada servidor possui capacidade de processamento distinta
- Executa tarefas simuladas (cpu-bound / sleep)
- Envia ao orquestrador os resultados e libera capacidade
  <br>

---

### 📌 Políticas de Escalonamento Suportadas
| Política       | Descrição |
|----------------|-----------|
| **Round Robin (RR)** | Distribuição cíclica das requisições |
| **Shortest Job First (SJF)** | Execução pela menor estimativa de tempo |
| **Prioridade** | Baseado em urgência (1 = alta) |
<br>

---

### 📥 Entrada de Dados

```json
{
    "servidores": [
        {"id": 1, "capacidade": 3},
        {"id": 2, "capacidade": 2},
        {"id": 3, "capacidade": 1}
    ],
    "requisicoes": [
        {"id": 101, "tipo": "visao_computacional", "prioridade": 1, "tempo_exec": 8},
        {"id": 102, "tipo": "nlp", "prioridade": 3, "tempo_exec": 3},
        {"id": 103, "tipo": "voz", "prioridade": 2, "tempo_exec": 5}
    ]
}
```

<br>

---

### 🚀 Como Rodar o Projeto Localmente

`1. Clone o repositório`
```bash
git clone https://github.com/devlucasaf/BSB-Compute---Sistema-de-Orquestracao-de-Tarefas.git
```

`2. Abra o projeto no IntelliJ IDEA e rode o arquivo Main.java`

---

### 📊 Métricas Monitoradas

- Tempo médio de resposta
- Carga de cada servidor
- Número de tarefas concluídas
- Throughput total
- Ocupação média da CPU simulada
- Tempo de espera máximo

<img
    width=100%
    src="https://capsule-render.vercel.app/api?type=waving&color=A020F0&height=120&section=footer"
/>