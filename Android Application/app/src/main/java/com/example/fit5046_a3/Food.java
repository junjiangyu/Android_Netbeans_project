package com.example.fit5046_a3;

public class Food {

    String name;
    String category;
    String calorieAmount;
    String servingAmount;
    String servingUnit;
    String fat;

    public Food(String name, String category, String calorieAmount, String servingAmount, String servingUnit, String fat) {
        this.name = name;
        this.category = category;
        this.calorieAmount = calorieAmount;
        this.servingAmount = servingAmount;
        this.servingUnit = servingUnit;
        this.fat = fat;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCalorieAmount() {
        return calorieAmount;
    }

    public void setCalorieAmount(String calorieAmount) {
        this.calorieAmount = calorieAmount;
    }

    public String getServingAmount() {
        return servingAmount;
    }

    public void setServingAmount(String servingAmount) {
        this.servingAmount = servingAmount;
    }

    public String getServingUnit() {
        return servingUnit;
    }

    public void setServingUnit(String servingUnit) {
        this.servingUnit = servingUnit;
    }

    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }




}
