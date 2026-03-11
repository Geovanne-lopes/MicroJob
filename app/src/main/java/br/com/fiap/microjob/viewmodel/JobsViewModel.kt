package br.com.fiap.microjob.viewmodel

import androidx.lifecycle.ViewModel
import br.com.fiap.microjob.model.Job
import br.com.fiap.microjob.model.MockData
import br.com.fiap.microjob.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * ViewModel para a lista de jobs e criação de novos jobs.
 */
class JobsViewModel : ViewModel() {

    private val _jobs = MutableStateFlow(MockData.initialJobs.toMutableList())
    val jobs: StateFlow<List<Job>> = _jobs.asStateFlow()

    private val _favoriteIds = MutableStateFlow<Set<String>>(emptySet())
    val favoriteIds: StateFlow<Set<String>> = _favoriteIds.asStateFlow()

    fun getJobById(id: String): Job? = _jobs.value.find { it.id == id }

    fun toggleFavorite(jobId: String) {
        _favoriteIds.update { ids ->
            if (ids.contains(jobId)) ids - jobId else ids + jobId
        }
    }

    fun addJob(
        title: String,
        description: String,
        paymentValue: Double,
        location: String,
        contactMethod: String,
        poster: User
    ) {
        val newJob = Job(
            id = "job_${System.currentTimeMillis()}",
            title = title,
            description = description,
            paymentValue = paymentValue,
            location = location,
            contactMethod = contactMethod,
            poster = poster
        )
        _jobs.update { list ->
            (list + newJob).toMutableList()
        }
    }
}
