# 🌾 Smart Farm Simulator

> A 2D tile-based farming game built with Java, inspired by Harvest Moon. Manage crops, handle weather changes, fight pests, and grow your farm into a thriving operation!

---

## 📸 Overview

**Smart Farm Simulator** is an Object-Oriented Programming project featuring:

- 🌱 **10 unique crops** with different growth rates and profit margins
- ☁️ **4 dynamic weather types** (Sunny, Rainy, Heat Wave, Snowy) that visually transform the world
- 🕷️ **Pest management system** with randomized infestation events
- 💰 **Economy system** — buy seeds, harvest crops, and manage your balance
- 🎨 **Custom pixel renderer** built from scratch (no external game engine)
- ⌨️ **Full keyboard + mouse controls**

---

## 🏗️ Architecture (MVC Pattern)

The project follows a **Model-View-Controller** architecture:

```
com.cousersoft.game/
│
├── model/          ← MODEL: Pure domain logic
│   ├── FarmGrid.java, FarmCell.java
│   ├── Crop.java (abstract) + 10 crop subclasses
│   ├── CropData.java       ← crop catalog + factory
│   ├── GrowthStage.java    ← enum
│   └── Weather.java (interface) + Sunny, Rainy, HeatWave, Snowy
│
├── view/           ← VIEW: Read-only rendering
│   ├── StateRenderer.java  ← interface
│   ├── GameRenderer.java
│   ├── MenuRenderer.java
│   ├── ShopRenderer.java
│   └── HelpRenderer.java
│
├── controller/     ← CONTROLLER: Input → Model updates
│   ├── StateUpdater.java   ← interface
│   ├── GameController.java
│   ├── MenuController.java
│   ├── ShopController.java
│   └── HelpController.java
│
├── graphics/       ← Rendering primitives (Screen, Sprite, BitmapFont)
├── input/          ← Input abstraction (Keyboard, Mouse, InputManager, Tool)
│
├── Game.java           ← Game loop (Canvas + Runnable)
├── GameLauncher.java   ← Entry point: main() + JFrame setup
├── GameContext.java    ← Shared state holder (connects all layers)
├── GameConstants.java  ← All magic numbers centralized
├── GameState.java      ← Enum: MENU / GAME / SHOP / HELP
└── StateHandler.java   ← State machine
```

| Layer | Package | Responsibility |
|-------|---------|----------------|
| **Model** | `model/` | Game rules, crop growth, weather simulation |
| **View** | `view/` | Read `GameContext`, draw pixels to `Screen` |
| **Controller** | `controller/` | Process input events, mutate Model |
| **Core** | root package | Game loop, bootstrapping, shared context |

---

## 🛠️ Requirements

| Tool | Version |
|------|---------|
| **Java JDK** | 21+ (project targets JDK 25) |
| **Apache Maven** | 3.8+ |

---

## ⚙️ Installation & Running

### 1. Clone the repository

```bash
git clone https://github.com/your-username/Farm-Game-OOP.git
cd Farm-Game-OOP
```

### 2. Build with Maven

```bash
mvn clean package
```

This compiles the project and produces a fat JAR in `target/`.

### 3. Run the game

**Option A — Via Maven (recommended for development):**

```bash
mvn exec:java
```

**Option B — Run the packaged JAR directly:**

```bash
java -jar target/FarmGame-1.1.jar
```

> **Windows note:** If `mvn` is not on PATH, use the full path:
> ```powershell
> C:\apache-maven-3.9.15-bin\apache-maven-3.9.15\bin\mvn.cmd exec:java
> ```

> **Java note:** Set `JAVA_HOME` if the build fails:
> ```powershell
> $env:JAVA_HOME = "C:\Program Files\Java\jdk-25"
> ```

---

## 🎮 Controls

### In-Game

| Key | Action |
|-----|--------|
| **Arrow Keys** | Navigate the farm grid |
| **Space** | Apply selected tool to the active cell |
| **Enter** | Advance to the next day |
| **1** | Select Seed tool |
| **2** | Select Harvest tool |
| **3** | Select Watering Can |
| **4** | Select Sword (clears pests) |
| **5** | Select Fertilizer |
| **Tab** | Cycle through available seeds |
| **P** | Open / Close the Seed Shop |
| **[ / ]** | Decrease / Increase display scale (1× – 6×) |
| **Esc** | Return to Main Menu |

### Debug / Cheat Keys

| Key | Effect |
|-----|--------|
| **R** | Force Rainy weather |
| **H** | Force Heat Wave |
| **Y** | Force Sunny weather |
| **S** | Force Snowy weather |

### Mouse Controls

| Action | Effect |
|--------|--------|
| **Left Click** on grid cell | Select cell & apply current tool |
| **Left Click** on tool icons | Switch active tool |
| **Left Click** "ADV DAY" button | Advance one day |
| **Left Click** in Shop | Navigate pages / equip seed |

---

## 🌾 Crop Reference

| Crop | Cost | Sell Price | Growth (Days) | Difficulty |
|------|:----:|:----------:|:-------------:|:----------:|
| Rice | $5 | $15 | 5 | ⭐ Easy |
| Carrot | $6 | $20 | 5 | ⭐ Easy |
| Radish | $7 | $22 | 6 | ⭐ Easy |
| Cabbage | $8 | $25 | 6 | ⭐ Easy |
| Corn | $10 | $35 | 7 | ⭐⭐ Normal |
| Chili | $10 | $38 | 8 | ⭐⭐ Normal |
| Tomato | $12 | $40 | 8 | ⭐⭐ Normal |
| Pepper | $12 | $45 | 9 | ⭐⭐ Normal |
| Eggplant | $15 | $55 | 10 | ⭐⭐⭐ Hard |
| Pumpkin | $25 | $100 | 12 | ⭐⭐⭐⭐ Expert |

---

## ☁️ Weather System

| Weather | Moisture/Day | Tile Appearance |
|---------|:------------:|-----------------|
| ☀️ Sunny | −5 | Vibrant green grass |
| 🌧️ Rainy | +20 | Muddy, dark tiles |
| 🔥 Heat Wave | −15 | Dry yellow grass, cracked soil |
| ❄️ Snowy | +10 | Snow-covered tiles, falling flakes |

---

## 📦 Project Structure

```
Farm-Game-OOP/
├── pom.xml
├── README.md
├── guide.md                              ← Detailed gameplay guide
└── src/main/
    ├── java/com/cousersoft/game/
    │   ├── Game.java                     ← Game loop (Canvas + Runnable)
    │   ├── GameLauncher.java             ← Entry point: main() + JFrame
    │   ├── GameContext.java              ← Shared state holder
    │   ├── GameConstants.java            ← Centralized constants
    │   ├── GameState.java                ← State enum
    │   ├── StateHandler.java             ← State machine
    │   │
    │   ├── model/                        ← Domain Model
    │   │   ├── Weather.java              ← Interface (apply + sprite methods)
    │   │   ├── Sunny.java / Rainy.java / HeatWave.java / Snowy.java
    │   │   ├── FarmGrid.java / FarmCell.java
    │   │   ├── Crop.java (abstract)
    │   │   ├── CropData.java             ← Catalog + createCrop() factory
    │   │   ├── GrowthStage.java
    │   │   └── Rice/Corn/Tomato/...      ← 10 crop classes
    │   │
    │   ├── view/                         ← View (rendering only)
    │   │   ├── StateRenderer.java        ← Interface
    │   │   ├── GameRenderer.java
    │   │   ├── MenuRenderer.java
    │   │   ├── ShopRenderer.java
    │   │   └── HelpRenderer.java
    │   │
    │   ├── controller/                   ← Controller (input handling)
    │   │   ├── StateUpdater.java         ← Interface
    │   │   ├── GameController.java
    │   │   ├── MenuController.java
    │   │   ├── ShopController.java
    │   │   └── HelpController.java
    │   │
    │   ├── graphics/                     ← Rendering primitives
    │   │   ├── Screen.java
    │   │   ├── Sprite.java
    │   │   ├── SpriteSheet.java
    │   │   └── text/BitmapFont.java
    │   │
    │   └── input/                        ← Input abstraction
    │       ├── Keyboard.java / Mouse.java
    │       ├── InputManager.java
    │       └── Tool.java
    │
    └── resources/
        ├── maps/farm_map.txt             ← Tile layout
        ├── textures/                     ← Sprite sheets
        └── font maps/                    ← Bitmap font JSON
```

---

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch: `git checkout -b feature/my-feature`
3. Commit your changes: `git commit -m 'Add my feature'`
4. Push to the branch: `git push origin feature/my-feature`
5. Open a Pull Request

---

## 📄 License

This project is developed for educational purposes as part of an Object-Oriented Programming course.
