# Quiz en images — Android Kotlin

## Présentation

Application Android réalisée dans le cadre d'un travail pratique
de développement Android.

L'application propose un quiz de 5 questions illustrées portant
sur les formes géométriques.

Pour chaque question, l'utilisateur doit observer une image et
choisir une réponse parmi trois propositions.

## Technologies utilisées

- Kotlin
- Android Studio
- XML
- Android Views
- View Binding

## Fonctionnalités

- 5 questions illustrées
- 3 choix par question
- Une seule bonne réponse par question
- Calcul automatique du score
- Barre de progression
- Correction après chaque réponse
- Désactivation des boutons après réponse
- Affichage du résultat final
- Possibilité de rejouer

## Thème

Le thème choisi est celui des formes géométriques :

- Triangle
- Carré
- Cercle
- Étoile
- Rectangle

## Structure du projet

- `MainActivity.kt` : logique principale du quiz
- `Question.kt` : structure d'une question
- `activity_main.xml` : interface utilisateur
- `res/drawable/` : images des cinq questions
- `captures/` : captures d'écran
- `compte-rendu/` : compte rendu du TP

## Lancer le projet

1. Ouvrir le projet avec Android Studio.
2. Attendre la synchronisation Gradle.
3. Connecter un téléphone Android ou démarrer un émulateur.
4. Cliquer sur le bouton Run.
5. Sélectionner l'appareil.
6. L'application démarre sur la première question.

## Tests réalisés

Les tests suivants ont été réalisés :

- lancement d'une nouvelle partie ;
- bonne réponse ;
- mauvaise réponse ;
- plusieurs clics sur une même réponse ;
- passage à la question suivante ;
- cinq bonnes réponses ;
- cinq mauvaises réponses ;
- mélange de bonnes et mauvaises réponses ;
- bouton Rejouer ;
- vérification sur petit écran.

## Auteur

Nom : À compléter

## Année universitaire

2026