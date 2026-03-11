# MicroJob — Documentação para Entrega do Projeto

**Projeto:** MicroJob  
**Pilar ESG:** Social  
**Data:** Março 2025  

---

## 1. Objetivo do aplicativo

O **MicroJob** é um aplicativo móvel (MVP) que tem como objetivo conectar pessoas da mesma comunidade para a oferta e a busca de **pequenos trabalhos locais**. Nele, usuários podem publicar tarefas como cortar grama, passear com cachorro, ajudar em mudança, pequenos reparos e organização, e outros usuários podem encontrar e entrar em contato para realizar esses serviços.

O aplicativo visa fortalecer o **pilar Social do ESG**, promovendo trabalho local, geração de renda complementar e interação entre vizinhos, sem depender de intermediários ou serviços externos.

---

## 2. Tecnologia escolhida

- **Linguagem:** Kotlin  
- **UI:** Jetpack Compose (interface declarativa e moderna para Android)  
- **Navegação:** Navigation Compose (rotas e fluxos entre telas)  
- **Arquitetura:** MVVM (ViewModel + StateFlow para estado reativo)  
- **Tema:** Material Design 3 (cores primária em rosa/magenta e secundária em amarelo)  
- **Persistência:** SharedPreferences + JSON para cadastro e login de usuários (sem backend)  
- **Dados em memória:** Lista de jobs e favoritos mantidos no ViewModel (sem API externa)  
- **Plataforma:** Android (SDK 36, mínimo compatível conforme configuração do projeto)  

Não foi utilizada API REST; o MVP consome apenas dados locais.

---

## 3. Aplicação no contexto ESG

O MicroJob se insere no **E (Environmental), S (Social) e G (Governance)** da seguinte forma:

- **Social (S):** Foco principal. O app facilita a oferta e a demanda de serviços entre pessoas da mesma região, incentivando trabalho local, renda complementar e fortalecimento de vínculos comunitários. Reduz dependência de deslocamentos longos e favorece a economia de proximidade.
- **Environmental (E):** Ao priorizar serviços locais, tende a reduzir deslocamentos desnecessários e a incentivar reutilização e reparos (ex.: “reparo na torneira”, “organizar garagem”), alinhado a práticas mais sustentáveis.
- **Governance (G):** O uso de cadastro e login com dados armazenados de forma controlada no dispositivo, e a possibilidade futura de regras de uso e transparência na divulgação de serviços, podem ser expandidos para refletir boas práticas de governança e confiança na plataforma.

---

## 4. Descrição e funcionalidades das telas (com imagens)

*As imagens são obrigatórias na entrega. Abaixo, cada tela tem um espaço indicado para você inserir a captura de tela correspondente (ex.: “[INSERIR IMAGEM: Nome da tela]”). Substitua o texto pelo arquivo de imagem ou insira a figura no documento ao gerar o PDF.*

---

### 4.1 Tela de Boas-vindas (Welcome)

**Descrição:** Primeira tela exibida ao abrir o app. Apresenta o nome “MicroJob” (ou “MicroJobs”) e dois botões: **LOG IN** e **SIGN UP**.

**Funcionalidades:**
- **LOG IN:** Navega para a tela de Login (usuários já cadastrados).
- **SIGN UP:** Navega para a tela de Cadastro (novos usuários).
- Se já existir um usuário logado, o app pode redirecionar automaticamente para o Feed de jobs.

**Comentário:** Ponto central de entrada; define se o usuário vai autenticar ou se cadastrar.

**[INSERIR IMAGEM OBRIGATÓRIA: Tela Welcome – botões LOG IN e SIGN UP]**

---

### 4.2 Tela de Login

**Descrição:** Formulário para acesso com e-mail e senha. Inclui campo de e-mail, campo de senha e botão de entrar. Link ou opção para ir ao Cadastro.

**Funcionalidades:**
- Validação de e-mail e senha.
- Login validado contra base local (SharedPreferences); senha conferida via hash (ex.: SHA-256).
- Em caso de sucesso: navega para o Feed de jobs e limpa a pilha até a Welcome.
- Botão/links para voltar ou ir para Sign Up.

**Comentário:** Apenas usuários cadastrados conseguem acessar o restante do app.

**[INSERIR IMAGEM OBRIGATÓRIA: Tela de Login]**

---

### 4.3 Tela de Cadastro (Sign Up)

**Descrição:** Formulário para criar nova conta: nome, e-mail, senha (e confirmação, se houver), e possível campo de comunidade.

**Funcionalidades:**
- Validação dos campos (ex.: e-mail único, senha com confirmação).
- Registro armazenado localmente (UserStore / SharedPreferences).
- Após sucesso: navega para o Feed e usuário já fica logado.
- Opção de voltar para a Welcome ou Login.

**Comentário:** Geração do primeiro usuário de teste (ex.: Teste / teste@teste.com) pode ser feita na primeira abertura para facilitar testes.

**[INSERIR IMAGEM OBRIGATÓRIA: Tela de Cadastro – Sign Up]**

---

### 4.4 Feed de Jobs (Home)

**Descrição:** Tela principal após o login. Exibe saudação “Olá, [nome]”, campo de busca, lista de jobs em cards (imagem, título, valor) e barra inferior com abas Home, Favoritos e Perfil. Botão flutuante “+” para criar job.

**Funcionalidades:**
- Listagem de jobs em cards clicáveis (LazyColumn).
- Busca por título ou descrição (filtro em memória).
- Toque no card: abre a tela de Detalhes do job.
- Ícone de coração no card: adiciona/remove dos favoritos.
- FAB “+”: navega para Criar Job.
- Bottom bar: Home (atual), Favoritos, Perfil.
- Dados do usuário logado (nome) vêm do AuthViewModel.

**Comentário:** Centro do app; aqui o usuário descobre oportunidades e acessa criação de job e favoritos.

**[INSERIR IMAGEM OBRIGATÓRIA: Feed de Jobs – lista de cards e FAB]**

---

### 4.5 Favoritos

**Descrição:** Tela acessada pela aba “Favoritos” da barra inferior. Lista apenas os jobs marcados como favoritos pelo usuário.

**Funcionalidades:**
- Lista em cards (mesmo layout do Feed) somente jobs favoritados.
- Toque no card: abre Detalhes do job.
- Ícone de coração: remove dos favoritos (e o job sai da lista).
- Mensagem quando não há favoritos: “Nenhum favorito ainda. Toque no coração em um job para adicionar.”
- Bottom bar: Home, Favoritos (atual), Perfil.

**Comentário:** Favoritos são mantidos em memória (ViewModel); ao fechar o app, a lista é reiniciada, a menos que seja implementada persistência futura.

**[INSERIR IMAGEM OBRIGATÓRIA: Tela Favoritos – com ou sem jobs]**

---

### 4.6 Criar Job (Create Job)

**Descrição:** Formulário para publicar um novo job: título, descrição, valor (R$), local, método de contato. Botão “Publicar Job”.

**Funcionalidades:**
- Campos: título, descrição, valor, local, contato.
- Validação antes de publicar.
- Job criado com “poster” = usuário logado; ID único (ex.: job_timestamp).
- Após publicar: volta à tela anterior (Feed) e o novo job aparece na lista; contador “jobs publicados” do perfil pode ser atualizado.

**Comentário:** Essencial para o pilar Social; permite que qualquer usuário ofereça serviços à comunidade.

**[INSERIR IMAGEM OBRIGATÓRIA: Tela Criar Job – formulário]**

---

### 4.7 Detalhes do Job

**Descrição:** Tela exibida ao tocar em um job no Feed ou em Favoritos. Mostra imagem no topo, título, local, valor, descrição, publicante, contato e botão “Entrar em contato”. Ícone de coração na barra superior para favoritar/desfavoritar.

**Funcionalidades:**
- Exibição completa dos dados do job (título, descrição, valor, local, contato, poster).
- Botão “Entrar em contato”: pode abrir o Chat do job ou apenas exibir o método de contato.
- Ícone de coração no topo: adiciona/remove dos favoritos (estado refletido no Feed e em Favoritos).
- Barra inferior: Home, Favoritos, Perfil; voltar retorna à tela de origem.

**Comentário:** Ponto de decisão entre favoritar, entrar em contato ou voltar à lista.

**[INSERIR IMAGEM OBRIGATÓRIA: Detalhes do Job]**

---

### 4.8 Chat

**Descrição:** Tela de conversa associada a um job. Lista de mensagens (ex.: enviadas e recebidas) e campo de texto com botão enviar.

**Funcionalidades:**
- Mensagens em memória por job (ChatViewModel), sem backend.
- Digitar texto e enviar: nova mensagem aparece na lista.
- Navegação: voltar ou pela bottom bar (Home, Favoritos, Perfil).
- Rota tipicamente: `chat/{jobId}`.

**Comentário:** Simula o primeiro contato entre quem publicou e quem se interessou; em uma versão futura pode integrar notificações ou API de mensagens.

**[INSERIR IMAGEM OBRIGATÓRIA: Tela de Chat]**

---

### 4.9 Perfil

**Descrição:** Tela do usuário logado. Exibe nome, comunidade, quantidade de jobs publicados, opção “Editar perfil” e botão **Sair**.

**Funcionalidades:**
- Dados exibidos vêm do usuário atual (AuthViewModel/UserRepository).
- Botão “Editar perfil”: pode navegar para tela de edição (se implementada) ou permanecer como placeholder.
- Botão **Sair**: chama `logout()` no AuthViewModel e navega para a tela Welcome, limpando a pilha para que o usuário não volte ao Feed sem estar logado.
- Bottom bar: Home, Favoritos, Perfil (atual).

**Comentário:** Único ponto de logoff do app; centraliza identidade e encerramento de sessão.

**[INSERIR IMAGEM OBRIGATÓRIA: Tela de Perfil – com botão Sair]**

---

## 5. Endereço (https) do serviço consumido

**Não aplicável.**

O aplicativo **não consome nenhum serviço externo via HTTPS**. Todas as operações utilizam:

- **Usuários (cadastro e login):** armazenamento local com SharedPreferences e persistência em JSON (UserStore).
- **Jobs e favoritos:** dados em memória no ViewModel (lista de jobs e conjunto de IDs favoritos).

Portanto, não há URL de API REST ou outro endpoint para informar nesta entrega. Em uma evolução futura, pode-se integrar um backend (ex.: Firebase ou API própria) e então documentar o endereço do serviço consumido.

---

**Fim do documento.**  
Para gerar o PDF: abra este arquivo em um editor (Word, Google Docs, Notion, etc.), substitua os trechos “[INSERIR IMAGEM OBRIGATÓRIA: …]” pelas capturas de tela reais do app e exporte como PDF.
