package com.cousersoft.game.simulation;

public class FarmGrid {
    private final int rows;
    private final int cols;
    private final FarmCell[][] cells;

    private Weather currentWeather = new Sunny();
    private final java.util.Random random = new java.util.Random();
    
    private int daysSinceLastPests = 0;
    private int pestSpawnThreshold = 2 + random.nextInt(2); // Randomly 2 or 3

    public FarmGrid(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.cells = new FarmCell[rows][cols];
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                cells[i][j] = new FarmCell();
            }
        }
    }

    public FarmCell getCell(int row, int col) {
        if (row >= 0 && row < rows && col >= 0 && col < cols) {
            return cells[row][col];
        }
        return null;
    }

    /**
     * Advances the simulation by one day.
     * Updates resources and grows crops for all cells.
     */
    public void advanceDay() {
        // Update weather
        double weatherRoll = random.nextDouble();
        if (weatherRoll < 0.2) currentWeather = new Rainy();
        else if (weatherRoll < 0.4) currentWeather = new HeatWave();
        else currentWeather = new Sunny();

        daysSinceLastPests++;
        boolean spawnPests = false;
        if (daysSinceLastPests >= pestSpawnThreshold) {
            spawnPests = true;
            daysSinceLastPests = 0;
            pestSpawnThreshold = 2 + random.nextInt(2);
        }

        int pestsSpawned = 0;
        int maxPests = 2 + random.nextInt(3); // 2 to 4

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                FarmCell cell = cells[i][j];
                
                // Apply weather
                currentWeather.apply(cell);

                // Handle discrete pest spawning
                if (spawnPests && pestsSpawned < maxPests) {
                    // Check if cell is in farmable area (logic from Game.java)
                    // FARM_X_START = 2, FARM_Y_START = 2, FARM_X_END = 22, FARM_Y_END = 11
                    if (i >= 2 && i <= 22 && j >= 2 && j <= 11) {
                        if (!cell.hasPests() && random.nextDouble() < 0.1) { // 10% chance to pick this specific cell until max reached
                            cell.setPests(true);
                            pestsSpawned++;
                        }
                    }
                }

                Crop crop = cell.getCurrentCrop();
                if (crop != null) {
                    crop.consumeResources(cell, currentWeather);
                    if (!cell.hasPests()) {
                        crop.grow();
                    }
                }
            }
        }
    }

    public Weather getCurrentWeather() {
        return currentWeather;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }
}
