package co.rahulchowdhury.memories.data.model.remote

sealed class ResponseState

object Success : ResponseState()
data class Error(val message: String) : ResponseState()
