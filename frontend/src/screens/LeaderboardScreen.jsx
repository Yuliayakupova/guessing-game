import { useEffect, useState } from "react"

export default function LeaderboardScreen({ setCurrentScreen, onNewGame, onExit }) {
  const [leaderboard, setLeaderboard] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)
  const [minGames, setMinGames] = useState(1)

  const fetchLeaderboard = async () => {
    try {
      setLoading(true)
      setError(null)
      const res = await fetch("http://localhost:8080/api/leaderboard")
      if (!res.ok) throw new Error("Failed to fetch leaderboard")
      const data = await res.json()
      console.log("Leaderboard data:", data)
      setLeaderboard(data || [])
    } catch (error) {
      console.error("Leaderboard error:", error)
      setError(error.message)
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => {
    fetchLeaderboard()
  }, [])

  const handleRetry = () => {
    setError(null)
    fetchLeaderboard()
  }


  const getFilteredAndSortedLeaderboard = () => {
    return leaderboard
      .filter((player) => player.totalGames >= minGames)
      .sort((a, b) => {
        if (a.successRate !== b.successRate) {
          return b.successRate - a.successRate
        }
        return a.totalTries - b.totalTries
      })
  }

  const filteredLeaderboard = getFilteredAndSortedLeaderboard()

  return (
    <div className="min-h-screen bg-gray-100 flex items-center justify-center p-4">
      <div
        className="nes-container is-rounded with-title w-full"
        style={{ maxWidth: "700px", backgroundColor: "#fff" }}
      >
        <h3 className="title">🏆 Leaderboard</h3>

        <div className="nes-container is-rounded" style={{ margin: "1rem 0", padding: "1rem" }}>
          <div className="nes-field">
            <label htmlFor="min-games" style={{ color: "#212529" }}>
              Minimum games played:
            </label>
            <div style={{ display: "flex", alignItems: "center", gap: "0.5rem", marginTop: "0.5rem" }}>
              <input
                id="min-games"
                type="number"
                min="0"
                max="100"
                value={minGames}
                onChange={(e) => setMinGames(Math.max(0, Number.parseInt(e.target.value) || 0))}
                className="nes-input"
                style={{ width: "80px" }}
              />
              <span style={{ color: "#666", fontSize: "0.9em" }}>
                (Showing {filteredLeaderboard.length} of {leaderboard.length} players)
              </span>
            </div>
          </div>
        </div>

        {loading && (
          <div className="text-center py-8">
            <p>Loading leaderboard...</p>
            <div className="mt-4">
              {[...Array(3)].map((_, i) => (
                <div key={i} className="nes-container is-dark mb-2" style={{ padding: "0.5rem" }}>
                  <div style={{ height: "20px", backgroundColor: "#666", borderRadius: "4px" }}></div>
                </div>
              ))}
            </div>
          </div>
        )}

        {error && (
          <div className="nes-container is-rounded" style={{ margin: "1rem 0", border: "4px solid #e76e55" }}>
            <p className="text-center" style={{ color: "#e76e55" }}>
              ❌ Error: {error}
            </p>
            <div className="text-center mt-4">
              <button className="nes-btn is-warning" onClick={handleRetry}>
                Try Again
              </button>
            </div>
          </div>
        )}

        {!loading && !error && filteredLeaderboard.length === 0 && (
          <div className="nes-container is-rounded is-dark text-center" style={{ margin: "1rem 0", padding: "2rem" }}>
            <p>
              ⭐ No players match the criteria!
              <br />
              <small>
                {leaderboard.length > 0
                  ? `Try lowering the minimum games filter (currently ${minGames})`
                  : "Be the first to play and make it to the leaderboard!"}
              </small>
            </p>
          </div>
        )}

        {!loading && !error && filteredLeaderboard.length > 0 && (
          <div style={{ margin: "1rem 0" }}>
            <div className="nes-container is-dark" style={{ padding: "1rem", color: "#fff" }}>
              <h4 style={{ marginBottom: "1rem", textAlign: "center", color: "#fff" }}>
                Top Players (Ranked by Success Rate)
              </h4>

              {filteredLeaderboard.map((entry, index) => {
                const winRate = Math.round(entry.successRate * 100)
                const rankIcon = index === 0 ? "🥇" : index === 1 ? "🥈" : index === 2 ? "🥉" : `#${index + 1}`
                const avgTries = entry.gamesWon > 0 ? (entry.totalTries / entry.gamesWon).toFixed(1) : "N/A"

                return (
                  <div
                    key={`${entry.playerName}-${index}`}
                    className="nes-container is-rounded"
                    style={{
                      margin: "0.5rem 0",
                      padding: "0.75rem",
                      backgroundColor: index < 3 ? "#f8f9fa" : "#fff",
                      border: index < 3 ? "4px solid #ffd700" : "2px solid #ccc",
                      color: "#212529",
                    }}
                  >
                    <div
                      style={{
                        display: "flex",
                        justifyContent: "space-between",
                        alignItems: "center",
                        flexWrap: "wrap",
                        gap: "0.5rem",
                      }}
                    >
                      <div style={{ display: "flex", alignItems: "center", gap: "0.5rem" }}>
                        <span style={{ fontSize: "1.2em", minWidth: "2.5rem" }}>{rankIcon}</span>
                        <strong style={{ color: "#212529" }}>{entry.playerName}</strong>
                      </div>

                      <div style={{ display: "flex", gap: "1rem", fontSize: "0.85em", flexWrap: "wrap" }}>
                        <span style={{ color: "#212529" }}>
                          <strong style={{ color: "#92cc41" }}>{entry.gamesWon}</strong>W
                        </span>
                        <span style={{ color: "#212529" }}>
                          <strong style={{ color: "#212529" }}>{entry.totalGames}</strong>G
                        </span>
                        <span style={{ color: "#212529" }}>
                          <strong
                            style={{
                              color: winRate >= 70 ? "#92cc41" : winRate >= 40 ? "#f7d51d" : "#e76e55",
                            }}
                          >
                            {winRate}%
                          </strong>
                        </span>
                        <span style={{ color: "#666" }}>
                          <strong>{entry.totalTries}</strong>T
                        </span>
                        <span style={{ color: "#666" }}>
                          Avg: <strong>{avgTries}</strong>
                        </span>
                      </div>
                    </div>

                   
                    {process.env.NODE_ENV === "development" && (
                      <div style={{ fontSize: "0.7em", color: "#999", marginTop: "0.25rem" }}>
                        Success Rate: {entry.successRate.toFixed(3)}, Total Tries: {entry.totalTries}
                      </div>
                    )}
                  </div>
                )
              })}
            </div>

            <div
              className="nes-container is-rounded"
              style={{ margin: "1rem 0", padding: "0.5rem", fontSize: "0.8em" }}
            >
              <p style={{ color: "#666", textAlign: "center" }}>
                <strong>Legend:</strong> W = Wins, G = Games, T = Total Tries, Avg = Average tries per win
              </p>
            </div>
          </div>
        )}

        <div className="text-center" style={{ marginTop: "2rem" }}>
          <div className="nes-container is-rounded" style={{ padding: "1rem", backgroundColor: "#f8f8f8" }}>
            <p style={{ marginBottom: "1rem", fontSize: "0.9em", color: "#666" }}>Ready for another challenge? 🎮</p>
            <div
              style={{
                display: "flex",
                justifyContent: "center",
                gap: "0.5rem",
                flexWrap: "wrap",
              }}
            >
              <button className="nes-btn is-primary" onClick={onNewGame}>
                New Game
              </button>
              <button className="nes-btn is-success" onClick={() => setCurrentScreen("rules")}>
                Rules
              </button>
              <button className="nes-btn is-error" onClick={onExit}>
                Exit
              </button>
            </div>
          </div>
        </div>


        {process.env.NODE_ENV === "development" && (
          <div style={{ marginTop: "1rem", fontSize: "0.8em", color: "#666" }}>
            <details>
              <summary>Debug Info & Sorting Logic</summary>
              <div style={{ fontSize: "0.7em", overflow: "auto", marginTop: "0.5rem" }}>
                <p>
                  <strong>Sorting Rules:</strong>
                </p>
                <ol>
                  <li>Primary: Success Rate (highest first)</li>
                  <li>Secondary: Total Tries (lowest first, when success rate is equal)</li>
                </ol>
                <p>
                  <strong>Filter:</strong> Minimum {minGames} games played
                </p>
                <pre>
                  {JSON.stringify(
                    {
                      totalPlayers: leaderboard.length,
                      filteredPlayers: filteredLeaderboard.length,
                      minGames,
                      sampleSorting: filteredLeaderboard.slice(0, 3).map((p) => ({
                        name: p.playerName,
                        successRate: p.successRate,
                        totalTries: p.totalTries,
                      })),
                    },
                    null,
                    2,
                  )}
                </pre>
              </div>
            </details>
          </div>
        )}
      </div>
    </div>
  )
}
