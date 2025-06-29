import React, { useEffect } from "react";

export default function GameOverScreen({
  player,
  secretNumber,
  won,
  onNewGame,
  onShowLeaderboard,
  onExit,
}) {
  useEffect(() => {
    const handleKeyDown = (e) => {
      if (e.key === "Enter" && typeof onNewGame === "function") {
        onNewGame();
      }
    };

    window.addEventListener("keydown", handleKeyDown);
    return () => window.removeEventListener("keydown", handleKeyDown);
  }, [onNewGame]);

  return (
    <div
      className="nes-container is-rounded with-title"
      style={{ padding: "2rem", marginTop: "2rem" }}
    >
      <p className="title">Game Over</p>
      <p className="nes-text is-primary">
        {won ? "🎉 You won!" : "💥 You lost!"}
      </p>
      <p>
        The secret number was: <strong>{secretNumber}</strong>
      </p>

      <div className="button-line" style={{ marginTop: "1.5rem" }}>
        <button
          className="nes-btn is-primary flex-button"
          onClick={onNewGame}
        >
          New Game
        </button>
        <button
          className="nes-btn is-success flex-button"
          onClick={onShowLeaderboard}
        >
          Leaderboard
        </button>
        <button
          className="nes-btn is-error flex-button"
          onClick={onExit}
        >
          Exit
        </button>
      </div>
    </div>
  );
}
