# MicroJob

Plataforma mobile para conectar sua comunidade: publique e encontre pequenos trabalhos locais.

---

## Sobre o projeto

O **MicroJob** é um aplicativo Android (MVP) que aproxima vizinhos através da oferta e da busca de **tarefas do dia a dia**: cortar grama, passear com cachorro, ajudar na mudança, pequenos reparos, organização e muito mais. Quem precisa anuncia o job; quem pode fazer entra em contato. Tudo local, sem intermediários.

O projeto foi desenvolvido no contexto do **pilar Social (ESG)**, incentivando trabalho local, geração de renda complementar e fortalecimento de vínculos comunitários.

---

## Funcionalidades

| Funcionalidade | Descrição |
|----------------|-----------|
| **Cadastro e login** | Criação de conta e acesso com e-mail e senha (armazenamento local) |
| **Feed de jobs** | Lista de tarefas com busca, cards com imagem e valor |
| **Favoritos** | Marque jobs com o ícone de coração e acesse pela aba Favoritos |
| **Criar job** | Formulário para publicar nova tarefa (título, descrição, valor, local, contato) |
| **Detalhes** | Visualização completa do job e botão para entrar em contato |
| **Chat** | Conversa em memória associada a cada job |
| **Perfil** | Dados do usuário logado e botão de sair |

---

## Tecnologias

- **Linguagem:** Kotlin  
- **UI:** Jetpack Compose + Material Design 3  
- **Arquitetura:** MVVM  
- **Navegação:** Navigation Compose  
- **Persistência:** SharedPreferences (usuários); jobs e favoritos em memória  

O app **não consome APIs externas**; todos os dados são locais.

---

## Pré-requisitos

- **Android Studio** (recomendado: versão mais recente, ex. Ladybug ou superior)  
- **JDK 11** (geralmente incluído com o Android Studio)  
- **Android SDK:** compileSdk 36, minSdk 28, targetSdk 36  

Nenhuma conta de serviço externo (Firebase, API key etc.) é necessária para rodar o projeto.

---

## Como rodar

1. **Clone o repositório**
   ```bash
   git clone https://github.com/Geovanne-lopes/MicroJob.git
   cd MicroJob
   ```

2. **Abra o projeto no Android Studio**  
   File → Open → selecione a pasta `MicroJob`.

3. **Sincronize o Gradle**  
   O Android Studio deve sincronizar automaticamente; se não, use **Sync Project with Gradle Files**.

4. **Execute no emulador ou dispositivo**  
   Conecte um aparelho com depuração USB ou inicie um emulador (API 28+). Depois clique em **Run** (ou `Shift+F10`).

Na primeira execução, um usuário de teste pode ser criado automaticamente (ex.: **teste@teste.com** / **teste123**) para você experimentar o fluxo completo.

---

## Dependências principais

O projeto usa o **Version Catalog** (`gradle/libs.versions.toml`). Principais bibliotecas:

| Biblioteca | Uso |
|------------|-----|
| AndroidX Core KTX | Extensões Kotlin para Android |
| Jetpack Compose (BOM) | UI declarativa (Material 3, Navigation, etc.) |
| Material Icons Extended | Ícones (coração, logout, etc.) |
| Navigation Compose | Navegação entre telas |
| Lifecycle / ViewModel Compose | MVVM e estado reativo |

Todas são resolvidas pelo Gradle a partir de `google()` e `mavenCentral()`; não é necessário instalar nada manualmente além do Android Studio e do SDK.

---

## Estrutura do projeto (resumo)

```
app/src/main/java/br/com/fiap/microjob/
├── MainActivity.kt
├── components/          # JobCard, BottomBar, etc.
├── local/               # UserStore (SharedPreferences)
├── model/               # Job, User
├── navigation/          # NavGraph, Routes
├── repository/          # UserRepository
├── ui/screens/          # Welcome, Login, Feed, Favoritos, Perfil, etc.
└── viewmodel/           # AuthViewModel, JobsViewModel, ChatViewModel
```

---

## Repositório

**GitHub:** [github.com/Geovanne-lopes/MicroJob](https://github.com/Geovanne-lopes/MicroJob)

---

*Projeto desenvolvido no contexto acadêmico FIAP — pilar Social (ESG).*
