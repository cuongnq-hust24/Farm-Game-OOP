# Smart Farm Simulator: Architectural Breakdown

This document provides a high-level overview of the game's architecture, emphasizing the Object-Oriented Programming (OOP) principles used to build the simulation.

## 1. System Overview

The game is divided into three main layers:

| Layer | Responsibility | Key Classes |
| :--- | :--- | :--- |
| **Engine** | Heartbeat, Window, Input | `Game.java`, `Keyboard.java`, `Mouse.java` |
| **Simulation** | The "Brain" of the farm | `FarmGrid.java`, `FarmCell.java` |
| **Data models** | The rules for plants and weather | `Crop.java`, `Weather.java` |

---

## 2. The Simulation Brain (Backend)

The simulation is a 2D grid where each cell is an independent object.

### `FarmGrid.java`
The container for the entire world. It handles the "Day/Night" cycle. When you click "Advance Day":
1. It randomly selects a new **Weather** event.
2. It triggers the **Pest** outbreak logic.
3. It iterates through every `FarmCell` and tells it to update.

### `FarmCell.java`
The most important logic container. It encapsulates the state of a single patch of dirt:
- **Moisture Level**: Tracks water.
- **Pests**: Tracks if there is an infestation.
- **Current Crop**: Holds a reference to a `Crop` object.

---

## 3. Core OOP Concepts in Action

### Polymorphism (Crops & Weather)
Instead of using complex `if/else` statements for every plant, we use **Polymorphism**:
- `Crop` is an abstract class. `Wheat` and `Tomato` inherit from it.
- When `grid` tells a cell to grow, it doesn't care if it's Wheat or Tomato; it just calls `crop.grow()`.
- Each plant defines its own growth speed, water consumption, and value.

### Encapsulation (State Management)
The `FarmCell` protects its data. You cannot change a crop's growth stage directly from the outside. You must go through the cell's methods, ensuring that moisture and pest status are checked first.

### Interface (Weather)
`Weather` is an interface. Each weather type (`Sunny`, `Rainy`, `HeatWave`) implements an `apply()` method. This allows the `FarmGrid` to apply environmental effects to every cell without knowing the specifics of how "Rain" works.

---

## 4. The Interaction Loop

1. **Input**: `Mouse` and `Keyboard` detect clicks or key presses.
2. **Action**: `Game.java` translates an input (e.g., Key '1') into a `Tool` selection.
3. **Application**: Clicking a cell calls `cell.applyTool(selectedTool)`.
4. **State Change**: The `FarmCell` updates its internal state (plants a seed, adds water, or clears pests).
5. **Rendering**: The `Game.render()` loop reads the new state and draws the correct pixels.

---

## 5. Summary of Mechanics

- **Economy**: You start with $100. Planting costs money; harvesting mature crops earns money.
- **Survival**: If a cell's moisture stays at **0** for 2 consecutive days, the crop will **DIE**.
- **Pests**: Pests block growth but don't kill the plant immediately. You must use the **Sword** to clear them.
