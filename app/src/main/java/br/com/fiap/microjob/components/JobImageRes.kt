package br.com.fiap.microjob.components

import br.com.fiap.microjob.R

/**
 * Retorna o drawable da imagem da tarefa pelo jobId, ou null para usar gradiente.
 * Usado no JobCard (feed) e na JobDetailsScreen (detalhes).
 */
fun getJobImageResId(jobId: String): Int? = when (jobId) {
    "job_1" -> R.drawable.job_cortar_grama
    "job_2" -> R.drawable.job_passear_cachorro
    "job_3" -> R.drawable.job_mudanca
    "job_4" -> R.drawable.job_consertar_torneira
    "job_5" -> R.drawable.jog_organizar_garagem
    else -> null
}
