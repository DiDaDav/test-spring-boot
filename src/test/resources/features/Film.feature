Feature: Film operations

    Scenario: client makes call to GET /api/film with known film
        When the client calls api with id 1
        Then the client receives status code 200
        Then the client receives film with following data
          | 1 | Film 1 | First film | John Doe |

    Scenario: client makes call to GET /api/film with known film
        When the client calls api with id 999999999
        Then the client receives status code 204

    Scenario: client makes call to POST /api/film
        When the client calls api with following data
          | Film 4 | Fourth film | Acteur un |
        Then the client receives status code 201
        Then a film is added with following data
          | 4 | Film 4 | Fourth film | Acteur un |