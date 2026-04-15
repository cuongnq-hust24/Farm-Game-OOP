package com.cousersoft.game.simulation;

public class FarmGrid {
    private final int rows;
    private final int cols;
    private final FarmCell[][] cells;

    private Weather currentWeather = new Sunny();
    private final java.util.Random random = new java.util.Random();
    
    private int daysSinceLastPests = 0;
    private int pestSpawnThreshold = 2 + random.nextInt(2); // Randomly 2 or 3

    private char[][] tileMap;

    public FarmGrid(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.cells = new FarmCell[rows][cols];
        this.tileMap = new char[rows][cols];
        
        loadMap("/maps/farm_map.txt");

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                cells[i][j] = new FarmCell();
            }
        }
    }

    private void loadMap(String path) {
        try {
            java.io.InputStream is = getClass().getResourceAsStream(path);
            java.io.BufferedReader br = new java.io.BufferedReader(new java.io.InputStreamReader(is));
            
            for (int y = 0; y < cols; y++) {
                String line = br.readLine();
                if (line == null) break;
                for (int x = 0; x < rows; x++) {
                    if (x < line.length()) {
                        tileMap[x][y] = line.charAt(x);
                    } else {
                        tileMap[x][y] = 'G';
                    }
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
            // Default to grass if loading fails
            for (int x = 0; x < rows; x++) {
                for (int y = 0; y < cols; y++) {
                    tileMap[x][y] = 'G';
                }
            }
        }
    }

    public char getTileType(int row, int col) {
        if (row >= 0 && row < rows && col >= 0 && col < cols) {
            return tileMap[row][col];
        }
        return 'G';
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
                    // Check if cell is in farmable area (Soil tile)
                    if (getTileType(i, j) == 'S') {
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

    public void setWeather(Weather weather) {
        this.currentWeather = weather;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }
}
