package com.liakot.librarymanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

public class CategoryActivity extends AppCompatActivity {

    Toolbar toolbar;
    LinearLayout agronomy, horticulture, soilScience, entomology, plantPathology, geneticsPlantBreediing,
            cropPhysiologyEcology, agriExtension, agriChemistry, agroEnvironment, bioCheMoleculer;
    LinearLayout CSE, EEE, ECE;
    LinearLayout accounting, financeBanking, management, marketing;
    LinearLayout fishBiologyGenetics, fishManagement, fishTechnology, aquaculture;
    LinearLayout microbiology, pathologyParasitology, dairyPoultry, anatomyHistology, animalScienceNutrition,
            geneticsAnimalBreeding, medicineSurgery, physiologyPharma;
    LinearLayout AIE, FPP, FET, FSN, architecture, CE, ME;
    LinearLayout chemistry, physics, math, statistics;
    LinearLayout english, economics, sociology, development;
    LinearLayout postgraduate, liberationWar, others;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        //---------for back button----------
        toolbar = findViewById(R.id.toolbarDemo);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        //------------Initialization Section-------------
        //---------Faculty of Agriculture--------
        agronomy = findViewById(R.id.agronomy);
        horticulture = findViewById(R.id.horticulture);
        soilScience = findViewById(R.id.soilScience);
        entomology = findViewById(R.id.entomology);
        plantPathology = findViewById(R.id.plantPathology);
        geneticsPlantBreediing = findViewById(R.id.geneticsPlantBreeding);
        cropPhysiologyEcology = findViewById(R.id.cropPhysiologyEcology);
        agriExtension= findViewById(R.id.agriculturalExtension);
        agriChemistry = findViewById(R.id.agriculturalChemistry);
        agroEnvironment = findViewById(R.id.agroforestryEnvironment);
        bioCheMoleculer = findViewById(R.id.biochemistryMolecularBiology);

        //-------Faculty of CSE-------
        CSE = findViewById(R.id.cse);
        EEE = findViewById(R.id.eee);
        ECE = findViewById(R.id.ece);

        //-------Faculty of Business Studies-------
        accounting = findViewById(R.id.accounting);
        financeBanking = findViewById(R.id.financeBanking);
        management = findViewById(R.id.management);
        marketing = findViewById(R.id.marketing);

        //-----Faculty of Fisheries---------
        fishBiologyGenetics = findViewById(R.id.fisheriesBiologyGenetics);
        fishManagement = findViewById(R.id.fisheriesManagement);
        fishTechnology = findViewById(R.id.fisheriesTechnology);
        aquaculture = findViewById(R.id.aquaculture);

         //-------Faculty of Vaterinary and Animal Science-------
        microbiology = findViewById(R.id.microbiology);
        pathologyParasitology = findViewById(R.id.pathologyParasitology);
        dairyPoultry = findViewById(R.id.dairyPoultryScience);
        anatomyHistology = findViewById(R.id.anatomyHistology);
        animalScienceNutrition = findViewById(R.id.generalAnimalScienceNutrition);
        geneticsAnimalBreeding= findViewById(R.id.geneticsAnimalBreeding);
        medicineSurgery = findViewById(R.id.medicineSurgeryObstetrics);
        physiologyPharma = findViewById(R.id.physiologyPharmacology);

        //-------------Faculty of Engineering-------------
        AIE = findViewById(R.id.agriculturalIndustrialEngineering);
        FPP = findViewById(R.id.foodProcessingPreservation);
        FET = findViewById(R.id.foodEngineeringTechnology);
        FSN = findViewById(R.id.foodScienceNutrition);
        architecture = findViewById(R.id.architecture);
        CE = findViewById(R.id.civilEngineering);
        ME = findViewById(R.id.mechanicalEngineering);

        //----------Faculty of Science-----------
        chemistry = findViewById(R.id.chemistry);
        physics = findViewById(R.id.physics);
        math = findViewById(R.id.mathematics);
        statistics = findViewById(R.id.statistics);

        //------------Faculty of Social Science & Humanities-----------
        english = findViewById(R.id.english);
        economics = findViewById(R.id.economics);
        sociology = findViewById(R.id.sociology);
        development = findViewById(R.id.developmentStudies);

        //------------Others---------
        postgraduate = findViewById(R.id.postgraduateStudies);
        liberationWar = findViewById(R.id.liberationWar);
        others = findViewById(R.id.others);


        //--------------On Click Section----------------
        //-----------Faculty of Agriculture-------
        agronomy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this, DepartmentActivity.class);
                intent.putExtra("department", "Agronomy");
                startActivity(intent);
            }
        });
        horticulture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this, DepartmentActivity.class);
                intent.putExtra("department", "Horticulture");
                startActivity(intent);
            }
        });
        soilScience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this, DepartmentActivity.class);
                intent.putExtra("department", "Soil Science");
                startActivity(intent);
            }
        });
        entomology.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this, DepartmentActivity.class);
                intent.putExtra("department", "Entomology");
                startActivity(intent);
            }
        });
        plantPathology.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this, DepartmentActivity.class);
                intent.putExtra("department", "Plant Pathology");
                startActivity(intent);
            }
        });
        geneticsPlantBreediing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this, DepartmentActivity.class);
                intent.putExtra("department", "Genetics & Plant Breeding");
                startActivity(intent);
            }
        });
        cropPhysiologyEcology.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this, DepartmentActivity.class);
                intent.putExtra("department", "Crop Physiology & Ecology");
                startActivity(intent);
            }
        });
        agriExtension.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this, DepartmentActivity.class);
                intent.putExtra("department", "Agricultural Extension");
                startActivity(intent);
            }
        });
        agriChemistry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this, DepartmentActivity.class);
                intent.putExtra("department", "Agricultural Chemistry");
                startActivity(intent);
            }
        });
        agroEnvironment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this, DepartmentActivity.class);
                intent.putExtra("department", "Agroforestry & Environment");
                startActivity(intent);
            }
        });
        bioCheMoleculer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this, DepartmentActivity.class);
                intent.putExtra("department", "Biochemistry & Molecular Biology");
                startActivity(intent);
            }
        });

        //----------Faculty of CSE-----------
        CSE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this, DepartmentActivity.class);
                intent.putExtra("department", "Computer Science & Engineering");
                startActivity(intent);
            }
        });
        EEE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this, DepartmentActivity.class);
                intent.putExtra("department", "Electrical & Electronic Engineering");
                startActivity(intent);
            }
        });
        ECE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this, DepartmentActivity.class);
                intent.putExtra("department", "Electronics & Communication Engineering");
                startActivity(intent);
            }
        });

        //---------Faculty of Business Studies-----------
        accounting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this, DepartmentActivity.class);
                intent.putExtra("department", "Accounting");
                startActivity(intent);
            }
        });
        financeBanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this, DepartmentActivity.class);
                intent.putExtra("department", "Finance & Banking");
                startActivity(intent);
            }
        });
        management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this, DepartmentActivity.class);
                intent.putExtra("department", "Management");
                startActivity(intent);
            }
        });
        marketing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this, DepartmentActivity.class);
                intent.putExtra("department", "Marketing");
                startActivity(intent);
            }
        });

        //----------------Faculty of Fisheries------------
        fishBiologyGenetics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this, DepartmentActivity.class);
                intent.putExtra("department", "Fisheries Biology & Genetics");
                startActivity(intent);
            }
        });
        fishManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this, DepartmentActivity.class);
                intent.putExtra("department", "Fisheries Management");
                startActivity(intent);
            }
        });
        fishTechnology.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this, DepartmentActivity.class);
                intent.putExtra("department", "Fisheries Technology");
                startActivity(intent);
            }
        });
        aquaculture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this, DepartmentActivity.class);
                intent.putExtra("department", "Aquaculture");
                startActivity(intent);
            }
        });

        //---------------Faculty of Vaterinary & Animal Science----------
        microbiology.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this, DepartmentActivity.class);
                intent.putExtra("department", "Microbiology");
                startActivity(intent);
            }
        });
        pathologyParasitology.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this, DepartmentActivity.class);
                intent.putExtra("department", "Pathology & Parasitology");
                startActivity(intent);
            }
        });
        dairyPoultry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this, DepartmentActivity.class);
                intent.putExtra("department", "Dairy & Poultry Science");
                startActivity(intent);
            }
        });
        anatomyHistology.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this, DepartmentActivity.class);
                intent.putExtra("department", "Anatomy & Histology");
                startActivity(intent);
            }
        });
        animalScienceNutrition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this, DepartmentActivity.class);
                intent.putExtra("department", "General Animal Science & Nutrition");
                startActivity(intent);
            }
        });
        geneticsAnimalBreeding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this, DepartmentActivity.class);
                intent.putExtra("department", "Genetics & Animal Breeding");
                startActivity(intent);
            }
        });
        medicineSurgery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this, DepartmentActivity.class);
                intent.putExtra("department", "Medicine, Surgery & Obstetrics");
                startActivity(intent);
            }
        });
        physiologyPharma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this, DepartmentActivity.class);
                intent.putExtra("department", "Physiology & Pharmacology");
                startActivity(intent);
            }
        });

        //-----------Faculty of Engineering---------------
        AIE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this, DepartmentActivity.class);
                intent.putExtra("department", "Agricultural & Industrial Engineering");
                startActivity(intent);
            }
        });
        FPP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this, DepartmentActivity.class);
                intent.putExtra("department", "Food Processing & Preservation");
                startActivity(intent);
            }
        });
        FET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this, DepartmentActivity.class);
                intent.putExtra("department", "Food Engineering & Technology");
                startActivity(intent);
            }
        });
        FSN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this, DepartmentActivity.class);
                intent.putExtra("department", "Food Science & Nutrition");
                startActivity(intent);
            }
        });
        architecture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this, DepartmentActivity.class);
                intent.putExtra("department", "Architecture");
                startActivity(intent);
            }
        });
        CE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this, DepartmentActivity.class);
                intent.putExtra("department", "Civil Engineering");
                startActivity(intent);
            }
        });
        ME.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this, DepartmentActivity.class);
                intent.putExtra("department", "Mechanical Engineering");
                startActivity(intent);
            }
        });

        //-----------Faculty of Science----------
        chemistry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this, DepartmentActivity.class);
                intent.putExtra("department", "Chemistry");
                startActivity(intent);
            }
        });
        physics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this, DepartmentActivity.class);
                intent.putExtra("department", "Physics");
                startActivity(intent);
            }
        });
        math.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this, DepartmentActivity.class);
                intent.putExtra("department", "Mathematics");
                startActivity(intent);
            }
        });
        statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this, DepartmentActivity.class);
                intent.putExtra("department", "Statistics");
                startActivity(intent);
            }
        });

        //----------Faculty of Social Science & Humanities-----------
        english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this, DepartmentActivity.class);
                intent.putExtra("department", "English");
                startActivity(intent);
            }
        });
        economics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this, DepartmentActivity.class);
                intent.putExtra("department", "Economics");
                startActivity(intent);
            }
        });
        sociology.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this, DepartmentActivity.class);
                intent.putExtra("department", "Sociology");
                startActivity(intent);
            }
        });
        development.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this, DepartmentActivity.class);
                intent.putExtra("department", "Development Studies");
                startActivity(intent);
            }
        });

        //-------------------Others---------
        postgraduate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this, DepartmentActivity.class);
                intent.putExtra("department", "Postgraduate");
                startActivity(intent);
            }
        });
        liberationWar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this, DepartmentActivity.class);
                intent.putExtra("department", "Liberation War");
                startActivity(intent);
            }
        });
        others.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this, DepartmentActivity.class);
                intent.putExtra("department", "Others");
                startActivity(intent);
            }
        });

    }

    //---------for back to home-------------
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
