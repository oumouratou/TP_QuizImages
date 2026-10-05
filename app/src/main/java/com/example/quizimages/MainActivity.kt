package com.example.quizimages

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.quizimages.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // Indice de la question actuelle
    private var questionActuelle = 0

    // Nombre de bonnes réponses
    private var score = 0

    // Permet de savoir si la question a déjà été répondue
    private var aRepondu = false

    // Permet de savoir si le quiz est terminé
    private var partieTerminee = false

    // Les 5 questions du quiz
    private val questions = arrayOf(

        Question(
            texte = "Quelle forme géométrique voyez-vous sur l'image ?",
            imageResId = R.drawable.triangle,
            choix = arrayOf(
                "Triangle",
                "Carré",
                "Cercle"
            ),
            bonneReponse = 0,
            descriptionImage = "Un triangle bleu"
        ),

        Question(
            texte = "Quelle forme géométrique voyez-vous sur l'image ?",
            imageResId = R.drawable.carre,
            choix = arrayOf(
                "Cercle",
                "Carré",
                "Rectangle"
            ),
            bonneReponse = 1,
            descriptionImage = "Un carré vert"
        ),

        Question(
            texte = "Quelle forme géométrique voyez-vous sur l'image ?",
            imageResId = R.drawable.cercle,
            choix = arrayOf(
                "Rectangle",
                "Triangle",
                "Cercle"
            ),
            bonneReponse = 2,
            descriptionImage = "Un cercle rouge"
        ),

        Question(
            texte = "Quelle forme géométrique voyez-vous sur l'image ?",
            imageResId = R.drawable.etoile,
            choix = arrayOf(
                "Cercle",
                "Étoile",
                "Carré"
            ),
            bonneReponse = 1,
            descriptionImage = "Une étoile jaune"
        ),

        Question(
            texte = "Quelle forme géométrique voyez-vous sur l'image ?",
            imageResId = R.drawable.rectangle,
            choix = arrayOf(
                "Rectangle",
                "Triangle",
                "Cercle"
            ),
            bonneReponse = 0,
            descriptionImage = "Un rectangle violet"
        )
    )
    override fun onSaveInstanceState(outState: Bundle) {

        outState.putInt("questionActuelle", questionActuelle)
        outState.putInt("score", score)
        outState.putBoolean("aRepondu", aRepondu)
        outState.putBoolean("partieTerminee", partieTerminee)

        super.onSaveInstanceState(outState)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialisation du View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Afficher la première question
        if (savedInstanceState != null) {

            questionActuelle =
                savedInstanceState.getInt("questionActuelle", 0)

            score =
                savedInstanceState.getInt("score", 0)

            aRepondu =
                savedInstanceState.getBoolean("aRepondu", false)

            partieTerminee =
                savedInstanceState.getBoolean("partieTerminee", false)
        }

        if (partieTerminee) {
            afficherResultat()
        } else {
            afficherQuestion()

            if (aRepondu) {
                val question = questions[questionActuelle]

                binding.btnChoix1.isEnabled = false
                binding.btnChoix2.isEnabled = false
                binding.btnChoix3.isEnabled = false

                binding.txtCorrection.text =
                    "Réponse déjà donnée. Bonne réponse : " +
                            question.choix[question.bonneReponse]

                binding.btnNavigation.visibility = View.VISIBLE
            }
        }

        // Gestion des trois boutons de réponse
        binding.btnChoix1.setOnClickListener {
            verifierReponse(0)
        }

        binding.btnChoix2.setOnClickListener {
            verifierReponse(1)
        }

        binding.btnChoix3.setOnClickListener {
            verifierReponse(2)
        }

        // Bouton Question suivante / Voir le résultat
        binding.btnNavigation.setOnClickListener {

            if (questionActuelle < questions.size - 1) {
                questionActuelle++
                afficherQuestion()
            } else {
                afficherResultat()
            }
        }

        // Bouton Rejouer
        binding.btnRejouer.setOnClickListener {
            recommencerQuiz()
        }
    }

    /**
     * Affiche la question actuelle.
     */
    private fun afficherQuestion() {

        val question = questions[questionActuelle]

        // Afficher le numéro de la question
        binding.txtNumeroQuestion.text =
            "Question ${questionActuelle + 1} sur ${questions.size}"

        // Afficher l'image
        binding.imgQuestion.setImageResource(question.imageResId)

        // Description pour l'accessibilité
        binding.imgQuestion.contentDescription =
            question.descriptionImage

        // Afficher le texte de la question
        binding.txtQuestion.text = question.texte

        // Afficher les trois choix
        binding.btnChoix1.text = question.choix[0]
        binding.btnChoix2.text = question.choix[1]
        binding.btnChoix3.text = question.choix[2]

        // Réinitialiser l'état de réponse
        aRepondu = false

        // Réactiver les trois boutons
        binding.btnChoix1.isEnabled = true
        binding.btnChoix2.isEnabled = true
        binding.btnChoix3.isEnabled = true

        // Effacer la correction précédente
        binding.txtCorrection.text = ""

        // Masquer le bouton de navigation
        binding.btnNavigation.visibility = View.GONE

        // Mettre à jour le texte du bouton selon la position
        if (questionActuelle == questions.size - 1) {
            binding.btnNavigation.text = "Voir le résultat"
        } else {
            binding.btnNavigation.text = "Question suivante"
        }

        // Afficher les choix
        binding.btnChoix1.visibility = View.VISIBLE
        binding.btnChoix2.visibility = View.VISIBLE
        binding.btnChoix3.visibility = View.VISIBLE

        // Masquer le résultat final
        binding.layoutResultat.visibility = View.GONE

        // Le quiz est en cours
        partieTerminee = false

        // Mettre à jour score et progression
        mettreAJourBandeau()
    }

    /**
     * Vérifie la réponse sélectionnée.
     */
    private fun verifierReponse(indiceChoisi: Int) {

        // Si la question a déjà été répondue,
        // on ne fait absolument rien.
        if (aRepondu) {
            return
        }

        // La question est maintenant considérée comme répondue.
        aRepondu = true

        val question = questions[questionActuelle]

        // Vérifier si la réponse est correcte
        if (indiceChoisi == question.bonneReponse) {

            // Ajouter exactement un point
            score++

            binding.txtCorrection.text =
                "✓ Bonne réponse ! +1 point"

        } else {

            binding.txtCorrection.text =
                "✗ Mauvaise réponse. La bonne réponse était : " +
                        question.choix[question.bonneReponse]
        }

        // Désactiver les trois boutons
        binding.btnChoix1.isEnabled = false
        binding.btnChoix2.isEnabled = false
        binding.btnChoix3.isEnabled = false

        // La progression augmente dans tous les cas
        mettreAJourBandeau()

        // Afficher le bouton de navigation
        binding.btnNavigation.visibility = View.VISIBLE
    }

    /**
     * Met à jour le score et la progression.
     */
    private fun mettreAJourBandeau() {

        binding.txtScore.text =
            "Score : $score/${questions.size}"

        // Nombre de réponses données
        val nombreReponses = if (aRepondu) {
            questionActuelle + 1
        } else {
            questionActuelle
        }

        binding.txtProgression.text =
            "Progression : $nombreReponses/${questions.size}"

        binding.progressQuiz.progress = nombreReponses
    }

    /**
     * Affiche le résultat final.
     */
    private fun afficherResultat() {

        partieTerminee = true

        // Masquer les éléments de la question
        binding.txtNumeroQuestion.visibility = View.GONE
        binding.imgQuestion.visibility = View.GONE
        binding.txtQuestion.visibility = View.GONE

        binding.btnChoix1.visibility = View.GONE
        binding.btnChoix2.visibility = View.GONE
        binding.btnChoix3.visibility = View.GONE

        binding.txtCorrection.visibility = View.GONE
        binding.btnNavigation.visibility = View.GONE

        // Afficher le résultat
        binding.layoutResultat.visibility = View.VISIBLE

        binding.txtResultat.text =
            "Résultat final : $score/${questions.size}"

        binding.txtMessageFinal.text =
            when {
                score == questions.size ->
                    "Bravo ! Toutes les réponses sont correctes."

                score == 0 ->
                    "Aucune bonne réponse. Tu peux réessayer !"

                else ->
                    "Quiz terminé ! Tu peux améliorer ton score."
            }

        // La progression finale doit être 5/5
        binding.txtProgression.text =
            "Progression : ${questions.size}/${questions.size}"

        binding.progressQuiz.progress = questions.size
    }

    /**
     * Réinitialise complètement le quiz.
     */
    private fun recommencerQuiz() {

        questionActuelle = 0
        score = 0
        aRepondu = false
        partieTerminee = false

        // Rendre à nouveau visibles les éléments du quiz
        binding.txtNumeroQuestion.visibility = View.VISIBLE
        binding.imgQuestion.visibility = View.VISIBLE
        binding.txtQuestion.visibility = View.VISIBLE

        binding.txtCorrection.visibility = View.VISIBLE

        afficherQuestion()
    }
}