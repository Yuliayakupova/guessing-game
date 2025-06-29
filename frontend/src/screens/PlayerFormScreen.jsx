"use client"

import { useState, useRef, useEffect } from "react"
import { createPlayer, getPlayer } from "../api/usePlayerApi"
import GoogleLoginCustom from "./GoogleLoginCustom"

export default function PlayerFormScreen({ player, setCurrentScreen, setPlayer, setGameId, onStartGame }) {
  const [name, setName] = useState("")
  const [error, setError] = useState(null)
  const [loading, setLoading] = useState(false)
  const [gameStarted, setGameStarted] = useState(false)
  const inputRef = useRef()

  useEffect(() => {
    inputRef.current?.focus()
  }, [])

  useEffect(() => {
    if (player && !gameStarted) {
      setGameStarted(true)
      startGameWithPlayer(player)
    }
  }, [player, gameStarted])

  const startGameWithPlayer = async (playerData) => {
    setLoading(true)
    setError(null)

    try {
      const gameResponse = await fetch(`http://localhost:8080/api/game/start?playerId=${playerData.id}`, {
        method: "POST",
        credentials: "include",
      })

      if (!gameResponse.ok) {
        throw new Error("Failed to create game")
      }

      const gameData = await gameResponse.json()
      setGameId(gameData.id)
      onStartGame()
    } catch (e) {
      setError(e.message || "Failed to start game.")
      setGameStarted(false)
    } finally {
      setLoading(false)
    }
  }

  const handleStart = async () => {
    if (loading) return

    if (player && !gameStarted) {
      setGameStarted(true)
      await startGameWithPlayer(player)
      return
    }

    if (!name.trim()) {
      setError("Name cannot be empty")
      return
    }

    setError(null)
    setLoading(true)

    try {
      await createPlayer(name.trim())
      const playerData = await getPlayer(name.trim())
      setPlayer(playerData)
    } catch (e) {
      setError(e.message || "Could not create player.")
      setLoading(false)
    }
  }

  const handleKeyDown = (e) => {
    if (e.key === "Enter") {
      handleStart()
    }
  }

  const handleGoogleLogin = async (token) => {
    if (!token) {
      setError("Invalid Google token")
      return
    }

    setError(null)
    setLoading(true)

    try {
      const response = await fetch("http://localhost:8080/api/auth/google", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ idToken: token }),
        credentials: "include",
      })

      if (!response.ok) throw new Error("Google login failed")

      const playerData = await response.json()
      setPlayer(playerData)
    } catch (e) {
      setError(e.message || "Failed to login with Google.")
      setLoading(false)
    }
  }

  return (
    <div className="nes-container is-rounded with-title" style={{ backgroundColor: "#fff", padding: "2rem" }}>
      <p className="title">Enter your name</p>

      <div className="nes-field mb-4">
        <label htmlFor="name_input">Name</label>
        <input
          id="name_input"
          type="text"
          className="nes-input"
          value={name}
          ref={inputRef}
          onChange={(e) => setName(e.target.value)}
          onKeyDown={handleKeyDown}
          disabled={loading || !!player}
          autoComplete="off"
        />
      </div>

      <div className="button-line">
        <button onClick={handleStart} className="nes-btn is-primary w-full mb-4" disabled={loading}>
          {loading ? "Loading..." : "Start"}
        </button>

        <GoogleLoginCustom onLogin={handleGoogleLogin} />
      </div>

      {error && <p className="text-red-600 mt-4">{error}</p>}
    </div>
  )
}
