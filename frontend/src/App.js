"use client"

import { useState } from "react"
import RulesScreen from "./screens/RulesScreen"
import PlayerFormScreen from "./screens/PlayerFormScreen"
import GameScreen from "./screens/GameScreen"
import GameOverScreen from "./screens/GameOverScreen"
import LeaderboardScreen from "./screens/LeaderboardScreen"

export default function App() {
  const [currentScreen, setCurrentScreen] = useState("rules")
  const [player, setPlayer] = useState(null)
  const [gameId, setGameId] = useState(null)
  
  const [gameResult, setGameResult] = useState(null)

  const startNewGame = async () => {
    if (!player) {
      setCurrentScreen("playerForm")
      return
    }

    try {
      const res = await fetch(`http://localhost:8080/api/game/start?playerId=${player.id}`, {
        method: "POST",
      })
      if (!res.ok) {
        alert("Failed to start new game")
        return
      }
      const gameData = await res.json()
      setGameId(gameData.id)
      setCurrentScreen("game")
    } catch (error) {
      alert("Failed to start new game")
    }
  }

  const handleGameOver = (result) => {
    setGameResult(result)
    setCurrentScreen("gameover")
  }

  const handleNewGameClick = () => {
    setGameResult(null)
    startNewGame()
  }

  const handleExitClick = () => {
    setPlayer(null)
    setGameId(null)
    setGameResult(null)
    setCurrentScreen("rules")
  }

  return (
    <>
      {currentScreen === "rules" && (
        <RulesScreen
          onStart={() => {
            if (player) {
              startNewGame()
            } else {
              setCurrentScreen("playerForm")
            }
          }}
        />
      )}

      {currentScreen === "playerForm" && (
        <PlayerFormScreen
          player={player}
          setCurrentScreen={setCurrentScreen}
          setPlayer={setPlayer}
          setGameId={setGameId}
          onStartGame={() => {
            setCurrentScreen("game")
          }}
        />
      )}

      {currentScreen === "game" && player && gameId && (
        <GameScreen
          player={player}
          gameId={gameId}
          setCurrentScreen={setCurrentScreen}
          onGameOver={handleGameOver}
          onShowLeaderboard={() => setCurrentScreen("leaderboard")}
          onNewGame={handleNewGameClick}
          onExit={handleExitClick}
        />
      )}

      {currentScreen === "gameover" && gameResult && (
        <GameOverScreen
          player={player}
          secretNumber={gameResult.secretNumber}
          won={gameResult.won}
          onNewGame={handleNewGameClick}
          onShowLeaderboard={() => setCurrentScreen("leaderboard")}
          onExit={handleExitClick}
        />
      )}

      {currentScreen === "leaderboard" && (
        <LeaderboardScreen
          setCurrentScreen={setCurrentScreen}
          onNewGame={handleNewGameClick}
          onExit={handleExitClick}
        />
      )}
    </>
  )
}
