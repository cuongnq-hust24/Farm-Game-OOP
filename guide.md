# 🚜 Smart Farm Simulator - User Guide (v1.1)

Welcome to the **Smart Farm Simulator v1.1**, a simplified 2D resource management game. This version introduces the **monogram** bitmask font system, expanded crop varieties, and dynamic winter environments.

---

## 🎮 Interface Overview

### 1. The HUD (Heads-Up Display)

The HUD now features high-contrast **Bitmap Font** rendering with drop shadows:

- **Global Stats**: Track your **Balance ($)** and **Day** in the top-right.
- **Status Alerts**: Real-time feedback in the top-left (e.g., "Planted Rice!").
- **Weather State**: Displays the current climate (Sunny, Rainy, Heat Wave, or Snowy).
- **Cell Details**: Hover or navigate to a cell to see its **Moisture**, **Pest Status**, and **Crop Growth Stage**.

---

## ⌨️ Controls Reference

| Key               | Action                                                                 |
| :---------------- | :--------------------------------------------------------------------- |
| **Arrow Keys**    | Navigate the farm grid (Excel-style).                                  |
| **Space**         | Apply the selected Tool/Seed to the active cell.                       |
| **Enter**         | **Advance Day** (Triggers growth and weather changes).                 |
| **1 - 4**         | Quick-select Tools (1: Seed, 2: Harvest, 3: Water, 4: Sword).          |
| **Tab**           | Cycle through the **10 available seeds** when the Seed Tool is active. |
| **[ / ]**         | Adjust game resolution scale (1x to 6x).                               |
| **R / H / Y / S** | Cheat keys to force **R**ain, **H**eat, **Y**unny, or **S**now.        |
| **Esc**           | Return to the Main Menu.                                               |

---

## 🌾 The 10-Crop Roster

Crops now have unique growth rates and profit margins. Use **Tab** to cycle through them:

| Crop         | Cost | Harvest Value | Difficulty   |
| :----------- | :--- | :------------ | :----------- |
| **Rice**     | $5   | $10           | Easy         |
| **Cabbage**  | $8   | $20           | Easy         |
| **Corn**     | $10  | $25           | Normal       |
| **Carrot**   | $6   | $15           | Normal       |
| **Radish**   | $7   | $18           | Normal       |
| **Tomato**   | $12  | $30           | Hard         |
| **Pumpkin**  | $25  | $65           | Professional |
| **Eggplant** | $15  | $40           | Hard         |
| **Chili**    | $10  | $25           | Normal       |
| **Pepper**   | $12  | $30           | Normal       |

---

## ☁️ Weather & Dynamic Terrain

The environment now visually evolves based on the current weather:

- **☀️ Sunny**: Standard loss of moisture (**-5/day**). Tiles look vibrant and green.
- **🌧️ Rainy**: Natural watering (**+20/day**). Tiles become muddy and darker.
- **🔥 Heat Wave**: Severe drought (**-15/day**). Grass turns yellow; soil becomes parched and cracked.
- **❄️ Snowy**: Cold moisture (**+10/day**). Tiles are covered in snow, and falling flakes fill the air.

---

## 🕷️ Pest Management (New!)

Pests now spawn in **truly random locations** across your field.

- **Effect**: Pests halt all crop growth and moisture absorption.
- **Solution**: Equip the **Sword (4)** and use **Space** on the infested cell to clear the outbreak immediately.

---

## 💡 Success Tips

1.  **Tab Through Seeds**: Don't just plant Rice! Check your balance and the current weather. If it's Rainy, it's a great time to plant expensive, moisture-hungry crops like **Pumpkin**.
2.  **Read the Shadow**: The new text shadow allows you to read cell moisture even when standing on white Snow tiles.
3.  **Advance Wisely**: Before pressing **Enter**, ensure all your crops are watered. A sudden Heat Wave can kill a dry crop in just one or two days!
4.  **Save for Winter**: Snowy weather is beautiful but provides less moisture than Rain. Keep a buffer of funds to buy extra water during the colder months.

---

**Good luck, Farmer!** With the new v1.1 tools and font system, your path to a high-score farm is clearer than ever.
