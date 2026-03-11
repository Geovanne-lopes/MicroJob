package br.com.fiap.microjob.model

/**
 * Dados mock para o MVP - usuários e jobs iniciais.
 */
object MockData {

    val defaultUser = User(
        id = "user_current",
        name = "João Silva",
        community = "Vila Esperança",
        jobsPostedCount = 3
    )

    private val user1 = User(
        id = "user_1",
        name = "João Santos",
        community = "Jardim Paulista",
        jobsPostedCount = 2
    )

    private val user2 = User(
        id = "user_2",
        name = "Ana Oliveira",
        community = "Vila Esperança",
        jobsPostedCount = 1
    )

    private val user3 = User(
        id = "user_3",
        name = "Carlos Souza",
        community = "Centro",
        jobsPostedCount = 4
    )

    val initialJobs = listOf(
        Job(
            id = "job_1",
            title = "Cortar grama",
            description = "Preciso cortar a grama do quintal. Área pequena, aproximadamente 30m².",
            paymentValue = 80.0,
            location = "Jardim Paulista, Rua das Flores 123",
            contactMethod = "WhatsApp",
            poster = user1
        ),
        Job(
            id = "job_2",
            title = "Passear com cachorro",
            description = "Passeio de 30 min com meu cachorro, 2x por semana.",
            paymentValue = 25.0,
            location = "Vila Esperança, Av. Principal 45",
            contactMethod = "Telefone",
            poster = user2
        ),
        Job(
            id = "job_3",
            title = "Ajuda na mudança",
            description = "Preciso de ajuda para carregar móveis no caminhão. 2 pessoas, meio período.",
            paymentValue = 150.0,
            location = "Centro, Rua Comercial 78",
            contactMethod = "WhatsApp",
            poster = user3
        ),
        Job(
            id = "job_4",
            title = "Reparo na torneira",
            description = "Torneira da cozinha pingando. Troca de vedação ou torneira nova.",
            paymentValue = 60.0,
            location = "Jardim Paulista, Rua das Palmeiras 56",
            contactMethod = "WhatsApp",
            poster = user1
        ),
        Job(
            id = "job_5",
            title = "Organizar garagem",
            description = "Ajuda para organizar e limpar a garagem. Aproximadamente 3 horas.",
            paymentValue = 100.0,
            location = "Vila Esperança, Rua Nova 12",
            contactMethod = "Telefone",
            poster = user2
        )
    )
}
