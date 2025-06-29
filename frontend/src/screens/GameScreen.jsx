import { useState, useRef, useEffect } from "react"

export default function GameScreen({
  player,
  gameId,
  setCurrentScreen,
  onGameOver,
  onNewGame,
  onExit,
  onShowLeaderboard,
}) {
  const [guessInput, setGuessInput] = useState(["", "", "", ""])
  const [guesses, setGuesses] = useState([])
  const [error, setError] = useState(null)
  const [triesLeft, setTriesLeft] = useState(8)
  const [finished, setFinished] = useState(false)
  const [won, setWon] = useState(false)
  const [secretNumber, setSecretNumber] = useState(null)
  const [isSubmitting, setIsSubmitting] = useState(false)

  const inputRefs = useRef([])

  useEffect(() => {
    inputRefs.current[0]?.focus()
  }, [])

  useEffect(() => {
    if (finished && onGameOver) {
      onGameOver({
        won,
        secretNumber,
        guesses,
        triesLeft,
      })
    }
  }, [finished, won, secretNumber, guesses, triesLeft, onGameOver])

  const handleChange = (index, value) => {
    if (/^\d?$/.test(value)) {
      const newInput = [...guessInput]
      newInput[index] = value
      setGuessInput(newInput)
      if (value && index < 3) {
        inputRefs.current[index + 1]?.focus()
      }
    }
  }

  const handleKeyDown = (index, e) => {
    if (e.key === "Backspace" && !guessInput[index] && index > 0) {
      inputRefs.current[index - 1]?.focus()
    }
  }

  const handleGuess = async () => {
    if (isSubmitting) return
    setIsSubmitting(true)

    const guess = guessInput.join("")

    if (!/^\d{4}$/.test(guess) || new Set(guess).size < 4) {
      setError("Enter 4 unique digits.")
      setGuessInput(["", "", "", ""])
      inputRefs.current[0]?.focus()
      setIsSubmitting(false)
      return
    }

    try {
      const res = await fetch(`http://localhost:8080/api/game/${gameId}/guess`, {
        method: "POST",
        headers: { "Content-Type": "text/plain" },
        body: guess,
      })

      const data = await res.json()

      setGuesses((prev) => [...prev, data.guess])
      setTriesLeft(data.triesLeft)
      setFinished(data.finished)
      setWon(data.won)

      if (data.secretNumber) {
        setSecretNumber(data.secretNumber)
      }

      setGuessInput(["", "", "", ""])
      inputRefs.current[0]?.focus()
      setError(null)
    } catch {
      setError("Something went wrong.")
    } finally {
      setIsSubmitting(false)
    }
  }

  return (
    <div className="min-h-screen bg-gray-100 flex items-center justify-center p-6">
      <div className="nes-container is-rounded w-full max-w-lg" style={{ backgroundColor: "#fff" }}>
        <p className="title text-center">Enter your guess</p>

        <form
          onSubmit={(e) => {
            e.preventDefault()
            handleGuess()
          }}
          style={{ display: "flex", justifyContent: "center", gap: "1rem", marginBottom: "1rem" }}
        >
          {guessInput.map((digit, idx) => (
            <div key={idx} className="nes-field" style={{ minWidth: "3rem" }}>
              <input
                ref={(el) => (inputRefs.current[idx] = el)}
                value={digit}
                onChange={(e) => handleChange(idx, e.target.value)}
                onKeyDown={(e) => handleKeyDown(idx, e)}
                maxLength={1}
                className="nes-input"
                inputMode="numeric"
                type="text"
                style={{
                  textAlign: "center",
                  fontSize: "1.5rem",
                  padding: "0.25rem",
                  width: "3rem",
                }}
                autoComplete="off"
              />
            </div>
          ))}
          <button type="submit" className="nes-btn is-primary" disabled={isSubmitting} style={{ minWidth: "6rem" }}>
            Submit
          </button>
        </form>

        {error && <p className="text-red-600 text-sm mb-2 text-center">{error}</p>}

        <div className="nes-container is-dark with-title">
          <p className="title">Your Guesses</p>
          <ul className="nes-list is-disc text-sm">
            {guesses.map((g) => (
              <li key={g.id}>
                {g.guessInput} → M:{g.mCount}, P:{g.pCount}
              </li>
            ))}
          </ul>
        </div>

        <div className="mt-4 text-sm text-gray-600 text-center">
          <div>
            Game ID: <b>{gameId}</b>
          </div>
          <div style={{ marginTop: "0.25rem" }}>
            Tries left:&nbsp;
            {[...Array(8)].map((_, i) =>
              i < triesLeft ? (
                <i key={i} className="nes-icon is-small heart" />
              ) : (
                <i key={i} className="nes-icon is-small is-transparent heart" />
              ),
            )}
          </div>
        </div>
      </div>
    </div>
  )
}
