# Bowling Application

## Description

This server side application demonstrate bowling case study game.

Assumptions:

1. Max players per game: 3
2. Max points: 10
3. Max frames: 10
4. Strike bonus: 10
5. Spare bonus: 10
6. No max limit for lanes
7. No validation of player duplication across ongoing games


## Running Locally

To run the application locally, please follow following steps:

* Enable Annotation Processing in Intellij.
* Build using `./gradlew clean build`
* Run using `./gradlew bootRun`

## APIS provided

* To start the game  `http://localhost:8080/bowling/start`
* To roll with player names and points provided`http://localhost:8080/bowling/:gameId/roll`
* To roll with just player names and app generating points `http://localhost:8080/bowling/v2/:gameId/roll`
* To get scoreboard of all players `http://localhost:8080/bowling/:gameId/score`


Please find automated client side application here.
*  [Blowing client side app](https://github.com/Rupaligangarde/bowling-client-app)
