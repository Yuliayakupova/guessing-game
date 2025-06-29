SELECT
    p.name AS player_name,
    COUNT(g.id) AS total_games,
    SUM(CASE WHEN g.won THEN 1 ELSE 0 END) AS games_won,
    COALESCE(SUM(guess_counts.totalGuesses), 0) AS total_tries,
    ROUND(CAST(SUM(CASE WHEN g.won THEN 1 ELSE 0 END) AS DOUBLE) / COUNT(g.id), 3) AS success_rate
FROM player p
JOIN game g ON p.id = g.player_id
LEFT JOIN (
    SELECT game_id, COUNT(*) AS totalGuesses
    FROM guess
    GROUP BY game_id
) guess_counts ON guess_counts.game_id = g.id
GROUP BY p.name
HAVING COUNT(g.id) >= ?
ORDER BY success_rate DESC, total_tries ASC
