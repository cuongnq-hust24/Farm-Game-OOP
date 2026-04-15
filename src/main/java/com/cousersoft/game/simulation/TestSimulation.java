package com.cousersoft.game.simulation;

public class TestSimulation {
    public static void main(String[] args) {
        System.out.println("--- SMART FARM SIMULATOR: FINAL QUALITY ASSURANCE ---\n");

        FarmGrid grid = new FarmGrid(25, 14);
        int balance = 100;

        // 1. Test Planting & Economic Deduction
        System.out.println("[ECONOMY TEST]");
        FarmCell cell1 = grid.getCell(5, 5);
        cell1.plantCrop(new Wheat());
        balance -= 5;
        System.out.println("Planted Wheat at 5,5 (-$5). Balance: " + balance);
        
        FarmCell cell2 = grid.getCell(10, 5);
        cell2.plantCrop(new Tomato());
        balance -= 10;
        System.out.println("Planted Tomato at 10,5 (-$10). Balance: " + balance);

        // 2. Test Weather & Moisture Loop
        System.out.println("\n[WEATHER & MOISTURE TEST]");
        for (int i = 0; i < 3; i++) {
            grid.advanceDay();
            System.out.println("Day " + (i+1) + " Weather: " + grid.getCurrentWeather().getName());
            System.out.println(" -> 5,5 Moisture: " + cell1.getMoistureLevel());
            System.out.println(" -> 10,5 Moisture: " + cell2.getMoistureLevel());
        }

        // 3. Test Crop Growth Stages
        System.out.println("\n[CROP GROWTH TEST]");
        System.out.println("Wheat 5,5 Stage: " + cell1.getCurrentCrop().getStage());
        System.out.println("Tomato 10,5 Stage: " + cell2.getCurrentCrop().getStage());

        // 4. Test Pests & Growth Inhibition
        System.out.println("\n[PEST TEST]");
        boolean foundPests = false;
        for (int i = 0; i < 10; i++) {
            grid.advanceDay();
            for (int r = 0; r < 25; r++) {
                for (int c = 0; c < 14; c++) {
                    if (grid.getCell(r, c).hasPests()) {
                        System.out.println("Pest outbreak detected at " + r + "," + c + " on advance day " + (i+4));
                        foundPests = true;
                        break;
                    }
                }
                if (foundPests) break;
            }
            if (foundPests) break;
        }

        // 5. Test Harvesting & Payout
        System.out.println("\n[HARVEST TEST]");
        // Force a crop to maturity for testing harvest payout
        for (int i = 0; i < 20; i++) {
            cell1.waterCell();
            cell1.clearPests();
            cell1.getCurrentCrop().grow();
            if (cell1.getCurrentCrop().getStage() == GrowthStage.MATURE) break;
        }
        
        if (cell1.getCurrentCrop().getStage() == GrowthStage.MATURE) {
            int payout = cell1.getCurrentCrop().harvest();
            balance += payout;
            System.out.println("Harvested MATURE Wheat! Payout: +$" + payout + ". New Balance: " + balance);
        }

        System.out.println("\n--- FINAL QA TEST COMPLETE ---");
    }
}
