import React, { useEffect } from "react";

export default function RulesScreen({ onStart }) {
  useEffect(() => {
    const handleKeyDown = (e) => {
      if (e.key === "Enter") {
        onStart();
      }
    };

    window.addEventListener("keydown", handleKeyDown);
    return () => window.removeEventListener("keydown", handleKeyDown);
  }, [onStart]);

  return (
    <>
      <div className="min-h-screen bg-gray-100 flex items-center justify-center p-4">
        <div className="nes-container is-rounded with-title w-full max-w-3xl">
          <p className="title">Game Rules</p>

          <section className="mb-6">
            <ul className="nes-list is-disc">
              <li>Program chooses a random secret number with 4 digits.</li>
              <li>All digits in the secret number are different.</li>
              <li>Player has 8 tries to guess the number.</li>
              <li>
                After each guess, you see: <code>M:m; P:p</code>
                <ul>
                  <p>m — matching digits in wrong positions</p>
                  <p>p — matching digits in correct positions</p>
                </ul>
              </li>
              <li>Game ends after 8 tries or correct guess.</li>
            </ul>
          </section>

          <section className="nes-container is-rounded is-dark">
            <h4 className="mb-2">Examples:</h4>
            <p>
              Secret: 7046<br />
              Guess: 8724 → <code>M:2; P:0</code>
            </p>
            <p className="mt-2">
              Guess: 7842 → <code>M:0; P:2</code>
            </p>
            <p className="mt-2">
              Guess: 7640 → <code>M:2; P:2</code>
            </p>
          </section>

          <button onClick={onStart} className="nes-btn is-primary w-full">
            Start Game
          </button>
        </div>
      </div>
    </>
  );
}
