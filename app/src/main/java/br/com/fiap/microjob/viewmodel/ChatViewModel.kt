package br.com.fiap.microjob.viewmodel

import androidx.lifecycle.ViewModel
import br.com.fiap.microjob.model.Message
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * ViewModel para o chat - mensagens em memória por conversa (jobId).
 */
class ChatViewModel : ViewModel() {

    // jobId -> lista de mensagens (simulação por job)
    private val _messagesByJob = MutableStateFlow<Map<String, List<Message>>>(emptyMap())
    val messagesByJob: StateFlow<Map<String, List<Message>>> = _messagesByJob.asStateFlow()

    fun getMessagesForJob(jobId: String): List<Message> {
        return _messagesByJob.value[jobId].orEmpty()
    }

    fun sendMessage(jobId: String, text: String) {
        if (text.isBlank()) return
        val newMessage = Message(
            id = "msg_${System.currentTimeMillis()}",
            text = text.trim(),
            isFromCurrentUser = true
        )
        _messagesByJob.update { map ->
            val current = map[jobId].orEmpty()
            map + (jobId to (current + newMessage))
        }
    }

    fun addReplyFromPoster(jobId: String, text: String) {
        val newMessage = Message(
            id = "msg_${System.currentTimeMillis()}_reply",
            text = text,
            isFromCurrentUser = false
        )
        _messagesByJob.update { map ->
            val current = map[jobId].orEmpty()
            map + (jobId to (current + newMessage))
        }
    }
}
