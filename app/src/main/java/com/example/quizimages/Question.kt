package com.example.quizimages

data class Question(
    val texte: String,
    val imageResId: Int,
    val choix: Array<String>,
    val bonneReponse: Int,
    val descriptionImage: String
)