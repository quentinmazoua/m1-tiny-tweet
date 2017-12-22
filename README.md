# Projet M1 Tiny-Tweet
## Par Quentin MAZOUA, Merlin BARZILAI et Corentin MARIONNEAU

## Liens

Lien de l'API : https://apis-explorer.appspot.com/apis-explorer/?base=https://webclouddist.appspot.com/_ah/api#p/messages/v1/

Lien de l'application : https://plnkr.co/edit/qVq2jXIetBArl1Pc81aD?p=preview

## Mesures

### Ecriture

Temps d'écriture en millisecondes, sur 30 mesures, d'un tweet dans le cas d'un compte à 100, 500 et 1000 followers :

![GraphiqueTempsEcriture](/images/GraphiqueEcriture.png)

Variance et moyenne pour chaque compte :
* 100 followers : Variance = 15759 / Moyenne = 506 ms
* 500 followers : Variance = 14756 / Moyenne = 507 ms
* 1000 followers : Variance = 57408 / Moyenne = 666 ms

Evolution de la moyenne en fonction du nombre de follower :

![GraphiqueTempsEcritureMoyenne](/images/GraphiqueEcritureMoyenne.png)

### Lecture

Temps de lecture en millisecondes, sur 30 mesures, des 10, 50 et 100 derniers tweets :

![GraphiqueTempsLecture](/images/GraphiqueLecture.png)

Variance et moyenne pour chaque nombre de tweets :
* 10 tweets : Variance = 17923 / Moyenne = 380 ms
* 50 tweets : Variance = 10782 / Moyenne = 766 ms
* 100 tweets : Variance = 61606 / Moyenne = 1542 ms

Evolution de la moyenne en fonction du nombre de tweets récupérés :

![GraphiqueTempsEcritureMoyenne](/images/GraphiqueLectureMoyenne.png)
