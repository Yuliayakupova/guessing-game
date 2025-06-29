const API_BASE = "http://localhost:8080/api/player";

export async function createPlayer(name) {
  const response = await fetch(API_BASE, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ name }),
  });

  if (!response.ok) {
    let errorMessage = "Failed to create player";
    try {
      const errorText = await response.text();
      if (errorText) {
        errorMessage = errorText;
      }
    } catch {
     }
     if (errorMessage.toLowerCase().includes("exists")) {
      errorMessage = "Player name already exists. Please choose another name.";
    }
    throw new Error(errorMessage);
  }
}

export async function getPlayer(name) {
  const response = await fetch(`${API_BASE}/${encodeURIComponent(name)}`);
  if (response.status === 404) {
    throw new Error("Player not found");
  }
  if (!response.ok) {
    throw new Error("Failed to load player");
  }
  return response.json();
}

export async function loadOrCreatePlayer(name) {
  try {
    return await getPlayer(name);
  } catch (error) {
    if (error.message === "Player not found") {
      await createPlayer(name);
      return await getPlayer(name);
    }
    throw error;
  }
}
