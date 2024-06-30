 # Test Java Spring Boot
 
## Réflexions et améliorations

### Réflexions - architecture

Je suis parti sur une architecture "classique" basée sur 3 couches:
API <-> Service <-> Repository

Cela offre une bonne base pour un nouveau projet, et est suffisamment scalable pour pouvoir ajouter quelques services.

#### API

La couche API se compose des controllers, des DTO et des mappers.
L'utilisation des DTO est utilisée parce que le schéma d'entrée pour un film (et acteur) est différente du modèle de la base de données (ajout dun id notamment).
Les mappers (via MapStruct) servent à faire la liaison entre le schéma d'entrée et le schéma de la base.

Chaque controller a 1 lien avec 1 service.

#### Service

Chaque service a un lien avec 1 repository, et potentiellement d'autres services (ex: le `FilmService` utilise `ActeurService`).

#### Repository

Les repository se basent sur la classe `JpaRepository`. Cela permets d'avoir le panel d'opérations suffisant pour les opérations nécessaires (CRUD en l'occurence).
J'aurais pu faire un repository 'custom', mais cela complexifie l'implemantation pour un apport très minime pour ce projet.

### Réflexions - fonctionnel

Le fait d'assumer qu'une requête sera toujours correct implique que la réponse sera, théoriquement, toujours correcte.

Cela signifie que l'on ne valide pas l'entrée de l'utilisateur (ce qui est une bonne pratique), mais aussi que les cas d'erreurs sont limités.

Par exemple, dans le cas du POST, il aurait tout à fait été possible de gérer le cas où l'on tente d'ajouter un film qui existe déjà.

### Réflexions - base de données

La base de données est utilisée en mémoire. Celle-ci aurait pu être persistée, mais n'ayant aucune indication sur l'exercice, j'ai préfére m'en tenir à du in-memory.
En temps normal, j'aurais probablement créé la base sur une autre instance, à laquelle je me serais connecté, pour que celle-ci puisse être gérée indépendamment.

### Réflexions - tests

Avec les tests unitaires JUnit, je souhaite uniquement savoir si le comportement de mes services est bien celui attendu.

Avec les tests Cucumber, je me rapproche plus de tests E2E, où je cherche à tester la chaîne d'évènement dans un environnement controlé.

Les tests écrits devraient couvrir une bonne partie du fonctionnel décrit dans l'exercice.

### Améliorations - tests

Je n'ai pas fait de configuration spéciale pour que mes tests se jouent sur 2 instructions différentes.

J'aurais pu créer une task pour les tests cucumber (ex: `test:cucumber`) qui m'aurait permis de dissocier les 2, et notamment l'utilisation de la base.

La base de données est partagée entre les tests Cucumber et les tests JUnit, ce qui n'est pas le meilleur cas de figure à cause d'effets de bord potentiels, surtout par la suite en cas d'ajout d'opération DELETE ou UPDATE.

Il y a toujours moyen d'ajouter plus de tests.

### Améliorations - configuration

La portée de l'exercice ainsi que l'absence d'environnements et de CI ne m'ont pas poussé à faire des configurations différentes pour chaque environnement.

Pour les mêmes raisons, je n'utilise pas de `Profile`, ce que j'aurais fait sinon.

### Améliorations - log

Aucun système de log n'a été mis en place. Dans l'idéal, si l'application part en production, il faudrait ajouter des log (avec log4j par exemple) pour avoir des données en cas de problème.

## Objectif du test

Le but de ce test est de créer un API Rest avec Java et Spring Boot pour ajouter et obtenir les détails d'un film.

Vous devez créer deux endpoints dans l'API :

- Un GET pour obtenir un film par ID dans la base de données en mémoire (H2).
- Un POST pour ajouter un film dans la base de données en mémoire (H2).

## Présomptions

- Ne vous souciez pas de valider l'entrée de l'utilisateur, vous pouvez présumer que la requête sera toujours valide.
- Il n'est pas nécessaire d'implémenter un mécanisme de sécurité.

## Critères

- 2 endpoints REST fonctionnels
- Structure des classes
- Qualité du code
- Utilisation des meilleurs pratiques
- Création de tests unitaires et de tests Cucumber (optionnel)

## Outils à utiliser

Le projet contient déjà les dépendances Gradle requises et la configuration nécessaire pour H2.  
Vous avez aussi, optionnellement, la possibilité d'utiliser les librairies Lombok et Mapstruct si vous le désirez.

- Java 21
- Gradle
- Spring Boot
- Spring Data JPA
- Base de données en mémoire H2
- Lombok (Optionnel)
- Mapstruct (Optionnel)
- JUnit (optionnel)
- Cucumber (optionnel)

## Modèle

#### Film

```
{
    "id": long,
    "titre": string,
    "description": string
    "acteurs": [
        {
            "id": long,
            "nom": string,
            "prenom": string
        }
    ]
}
```

## Endpoints

#### GET /api/film/{id}

- Requête : ID dans l'URI
- Réponse : Objet Film (Voir modèle)
- Statut : 200 OK

```
http://localhost:8080/api/film/1
{
   "id":1,
   "titre":"Star Wars: The Empire Strikes Back",
   "description":"Darth Vader is adamant about turning Luke Skywalker to the dark side.",
   "acteurs":[
      {
         "id":2,
         "nom":"Ford",
         "prenom":"Harrison"
      },
      {
         "id":3,
         "nom":"Hamill",
         "prenom":"Mark"
      }
   ]
}
```

#### POST /api/film

- Requête : Objet Film dans le body
- Réponse : Objet Film crée
- Statut : 201 CREATED

```
'{
   "titre":"Star Wars: The Empire Strikes Back",
   "description":"Darth Vader is adamant about turning Luke Skywalker to the dark side.",
   "acteurs":[
      {
         "nom":"Ford",
         "prenom":"Harrison"
      },
      {
         "nom":"Hamill",
         "prenom":"Mark"
      }
   ]
}'
http://localhost:8080/api/film --header "Content-Type:application/json"

{
   "id":4,
   "titre":"Star Wars: The Empire Strikes Back",
   "description":"Darth Vader is adamant about turning Luke Skywalker to the dark side.",
   "acteurs":[
      {
         "id":5,
         "nom":"Ford",
         "prenom":"Harrison"
      },
      {
         "id":6,
         "nom":"Hamill",
         "prenom":"Mark"
      }
   ]
}
```

## Validation des endpoints

Vous pouvez utiliser la collection postman incluse dans le projet si vous désirez valider votre API avec des assertions.  
Celle-ci se retrouve dans le dossier **postman** du projet.

## Soumettre le test

Une fois terminé, veuillez créer un nouveau dépot sur GitHub et l'envoyer par courriel.
Merci de ne pas faire un fork du dépôt de code existant.

Bonne chance !
