"use strict";

// ======== Data Models ========
const winningTriples = [
  [0, 1, 2],
  [3, 4, 5],
  [6, 7, 8],
  [0, 3, 6],
  [1, 4, 7],
  [2, 5, 8],
  [0, 4, 8],
  [2, 4, 6],
];

const scoreboardStorageKey = "ttt_scoreboard_v1";

// ======== App State ========
const appState = {
  board: Array(9).fill(null),
  humanSymbol: "X",
  aiSymbol: "O",
  currentPlayer: "human", // "human" | "ai"
  isGameOver: false,
  difficulty: "medium", // "easy" | "medium" | "hard"
};

const elements = {
  cells: [],
  statusText: null,
  scoreX: null,
  scoreO: null,
  scoreDraw: null,
  symbolSelect: null,
  starterSelect: null,
  difficultySelect: null,
  restartBtn: null,
  resetScoreBtn: null,
};

let scoreboard = { X: 0, O: 0, D: 0 };

// ======== Initialization ========
document.addEventListener("DOMContentLoaded", () => {
  cacheElements();
  loadScoreboard();
  bindControls();
  setupBoardButtons();
  newGame();
});

function cacheElements() {
  elements.cells = Array.from(document.querySelectorAll(".cell"));
  elements.statusText = document.getElementById("statusText");
  elements.scoreX = document.getElementById("scoreX");
  elements.scoreO = document.getElementById("scoreO");
  elements.scoreDraw = document.getElementById("scoreDraw");
  elements.symbolSelect = document.getElementById("symbolSelect");
  elements.starterSelect = document.getElementById("starterSelect");
  elements.difficultySelect = document.getElementById("difficultySelect");
  elements.restartBtn = document.getElementById("restartBtn");
  elements.resetScoreBtn = document.getElementById("resetScoreBtn");
}

function bindControls() {
  elements.symbolSelect.addEventListener("change", () => {
    appState.humanSymbol = elements.symbolSelect.value;
    appState.aiSymbol = appState.humanSymbol === "X" ? "O" : "X";
    newGame();
  });

  elements.starterSelect.addEventListener("change", () => {
    newGame();
  });

  elements.difficultySelect.addEventListener("change", () => {
    appState.difficulty = elements.difficultySelect.value;
    newGame();
  });

  elements.restartBtn.addEventListener("click", () => {
    newGame();
  });

  elements.resetScoreBtn.addEventListener("click", () => {
    scoreboard = { X: 0, O: 0, D: 0 };
    persistScoreboard();
    renderScoreboard();
    announce("Scores reset");
  });
}

function setupBoardButtons() {
  elements.cells.forEach((btn) => {
    btn.addEventListener("click", () => {
      const index = Number(btn.getAttribute("data-index"));
      handleHumanMove(index);
    });

    btn.addEventListener("keydown", (event) => {
      if (event.key === "Enter" || event.key === " ") {
        event.preventDefault();
        const index = Number(btn.getAttribute("data-index"));
        handleHumanMove(index);
      }
    });
  });
}

// ======== Game Flow ========
function newGame() {
  appState.board = Array(9).fill(null);
  appState.isGameOver = false;
  appState.humanSymbol = elements.symbolSelect.value;
  appState.aiSymbol = appState.humanSymbol === "X" ? "O" : "X";
  appState.difficulty = elements.difficultySelect.value;

  const starter = elements.starterSelect.value; // "human" | "ai"
  appState.currentPlayer = starter;

  renderBoard();
  updateStatusText();

  if (appState.currentPlayer === "ai") {
    // slight delay for UX
    window.setTimeout(aiMove, 300);
  }
}

function handleHumanMove(index) {
  if (appState.isGameOver) return;
  if (appState.currentPlayer !== "human") return;
  if (appState.board[index] !== null) return;

  appState.board[index] = appState.humanSymbol;
  renderBoard();

  const result = evaluateTerminal(appState.board);
  if (result !== null) {
    endGame(result);
    return;
  }

  appState.currentPlayer = "ai";
  updateStatusText();
  window.setTimeout(aiMove, 250);
}

function aiMove() {
  if (appState.isGameOver) return;
  if (appState.currentPlayer !== "ai") return;

  const best = chooseAiMove(appState.board, appState.aiSymbol, appState.difficulty);
  if (best.index !== -1) {
    appState.board[best.index] = appState.aiSymbol;
  }

  renderBoard();

  const result = evaluateTerminal(appState.board);
  if (result !== null) {
    endGame(result);
    return;
  }

  appState.currentPlayer = "human";
  renderBoard();
  updateStatusText();
}

function endGame(result) {
  appState.isGameOver = true;
  const [state, winner] = result; // state: 'win' | 'draw', winner: 'X' | 'O' | null

  if (state === "win" && winner) {
    const label = winner === appState.humanSymbol ? "You win!" : "AI wins.";
    highlightWinningCells(appState.board, winner);
    announce(`${label} (${winner})`);
    scoreboard[winner] += 1;
  } else {
    announce("It's a draw.");
    scoreboard.D += 1;
  }
  persistScoreboard();
  renderScoreboard();
  updateStatusText();
}

// ======== Rendering ========
function renderBoard() {
  elements.cells.forEach((btn, index) => {
    const value = appState.board[index];
    btn.textContent = value ? value : "";
    btn.classList.toggle("is-o", value === "O");
    btn.classList.remove("is-win");
    btn.disabled = appState.isGameOver || value !== null || appState.currentPlayer !== "human";
    btn.setAttribute("aria-label", `Cell ${index + 1}${value ? `, ${value}` : ""}`);
  });
}

function updateStatusText() {
  if (appState.isGameOver) {
    const terminal = evaluateTerminal(appState.board);
    if (terminal && terminal[0] === "win") {
      elements.statusText.textContent = `Game over: ${terminal[1]} wins.`;
      return;
    }
    if (terminal && terminal[0] === "draw") {
      elements.statusText.textContent = "Game over: Draw.";
      return;
    }
  }
  const who = appState.currentPlayer === "human" ? "Your" : "AI";
  elements.statusText.textContent = `${who} turn (${appState.currentPlayer === "human" ? appState.humanSymbol : appState.aiSymbol}).`;
}

function highlightWinningCells(board, winner) {
  const line = winningTriples.find(([a, b, c]) => board[a] === winner && board[b] === winner && board[c] === winner);
  if (!line) return;
  line.forEach((i) => elements.cells[i].classList.add("is-win"));
}

function renderScoreboard() {
  elements.scoreX.textContent = String(scoreboard.X);
  elements.scoreO.textContent = String(scoreboard.O);
  elements.scoreDraw.textContent = String(scoreboard.D);
}

// ======== Scoreboard Persistence ========
function loadScoreboard() {
  try {
    const raw = localStorage.getItem(scoreboardStorageKey);
    if (raw) {
      const parsed = JSON.parse(raw);
      if (typeof parsed?.X === "number" && typeof parsed?.O === "number" && typeof parsed?.D === "number") {
        scoreboard = parsed;
      }
    }
  } catch (err) {
    // ignore
  }
  renderScoreboard();
}

function persistScoreboard() {
  try {
    localStorage.setItem(scoreboardStorageKey, JSON.stringify(scoreboard));
  } catch (err) {
    // ignore
  }
}

// ======== Game Rules ========
function evaluateTerminal(board) {
  // Returns [state, winner] where state in { 'win', 'draw' } or null if ongoing
  for (const [a, b, c] of winningTriples) {
    if (board[a] && board[a] === board[b] && board[a] === board[c]) {
      return ["win", board[a]];
    }
  }
  if (board.every((cell) => cell !== null)) {
    return ["draw", null];
  }
  return null;
}

function getAvailableMoves(board) {
  const moves = [];
  for (let i = 0; i < board.length; i++) {
    if (board[i] === null) moves.push(i);
  }
  return moves;
}

// ======== AI ========
function chooseAiMove(board, aiSymbol, difficulty) {
  const availableMoves = getAvailableMoves(board);
  if (availableMoves.length === 0) return { index: -1, score: 0 };

  if (difficulty === "easy") {
    // 60% random, 40% shallow minimax
    if (Math.random() < 0.6) {
      const randomIndex = availableMoves[Math.floor(Math.random() * availableMoves.length)];
      return { index: randomIndex, score: 0 };
    }
    return shallowBestMove(board, aiSymbol, 1);
  }

  if (difficulty === "medium") {
    return shallowBestMove(board, aiSymbol, 3);
  }

  // hard: full minimax with alpha-beta
  return fullBestMove(board, aiSymbol);
}

function shallowBestMove(board, aiSymbol, depthLimit) {
  const maximizing = true;
  const humanSymbol = aiSymbol === "X" ? "O" : "X";
  let bestScore = -Infinity;
  let bestIndex = -1;
  const availableMoves = getAvailableMoves(board);
  for (const move of availableMoves) {
    board[move] = aiSymbol;
    const score = minimax(board, depthLimit - 1, !maximizing, -Infinity, Infinity, aiSymbol, humanSymbol);
    board[move] = null;
    if (score > bestScore) {
      bestScore = score;
      bestIndex = move;
    }
  }
  return { index: bestIndex, score: bestScore };
}

function fullBestMove(board, aiSymbol) {
  const maximizing = true;
  const humanSymbol = aiSymbol === "X" ? "O" : "X";
  let bestScore = -Infinity;
  let bestIndex = -1;
  const availableMoves = getAvailableMoves(board);

  for (const move of availableMoves) {
    board[move] = aiSymbol;
    const score = minimax(board, Infinity, !maximizing, -Infinity, Infinity, aiSymbol, humanSymbol);
    board[move] = null;
    if (score > bestScore) {
      bestScore = score;
      bestIndex = move;
    }
  }
  return { index: bestIndex, score: bestScore };
}

function minimax(board, depth, isMaximizing, alpha, beta, aiSymbol, humanSymbol) {
  const terminal = evaluateTerminal(board);
  if (terminal) {
    if (terminal[0] === "win") {
      if (terminal[1] === aiSymbol) return 10;
      if (terminal[1] === humanSymbol) return -10;
      return 0;
    }
    return 0; // draw
  }
  if (depth <= 0) return heuristic(board, aiSymbol, humanSymbol);

  const availableMoves = getAvailableMoves(board);

  if (isMaximizing) {
    let best = -Infinity;
    for (const move of availableMoves) {
      board[move] = aiSymbol;
      const score = minimax(board, depth - 1, false, alpha, beta, aiSymbol, humanSymbol);
      board[move] = null;
      best = Math.max(best, score);
      alpha = Math.max(alpha, best);
      if (beta <= alpha) break; // beta cut-off
    }
    return best;
  } else {
    let best = Infinity;
    for (const move of availableMoves) {
      board[move] = humanSymbol;
      const score = minimax(board, depth - 1, true, alpha, beta, aiSymbol, humanSymbol);
      board[move] = null;
      best = Math.min(best, score);
      beta = Math.min(beta, best);
      if (beta <= alpha) break; // alpha cut-off
    }
    return best;
  }
}

function heuristic(board, aiSymbol, humanSymbol) {
  // Simple heuristic: count potential lines
  let score = 0;
  for (const [a, b, c] of winningTriples) {
    const line = [board[a], board[b], board[c]];
    const aiCount = line.filter((v) => v === aiSymbol).length;
    const humanCount = line.filter((v) => v === humanSymbol).length;
    if (aiCount > 0 && humanCount === 0) {
      // prefer 2-in-a-row
      score += aiCount === 2 ? 5 : 1;
    } else if (humanCount > 0 && aiCount === 0) {
      score -= humanCount === 2 ? 5 : 1;
    }
  }
  return score;
}

// ======== Utilities ========
function announce(message) {
  elements.statusText.textContent = message;
}

