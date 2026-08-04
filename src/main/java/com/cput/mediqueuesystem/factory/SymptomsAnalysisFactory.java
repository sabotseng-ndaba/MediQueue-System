package com.cput.mediqueuesystem.factory;

import java.time.LocalDateTime;

import org.apache.commons.validator.GenericValidator;

import com.cput.mediqueuesystem.domain.Patient;
import com.cput.mediqueuesystem.domain.SymptomsAnalysis;

/*
 * SymptomsAnalysisFactory.java
 * Validates input and builds SymptomsAnalysis objects.
 *
 * Author: Uya
 * Date: 03 August 2026
 */

public class SymptomsAnalysisFactory {

    public static SymptomsAnalysis createSymptomsAnalysis(String analysisId, Patient patient,
                                                           String inputText, String predictedConditions,
                                                           String suggestedSymptoms, double confidenceScore,
                                                           LocalDateTime createdAt) {

        if (GenericValidator.isBlankOrNull(analysisId) || patient == null
                || GenericValidator.isBlankOrNull(inputText)) {
            return null;
        }

        return new SymptomsAnalysis.Builder()
                .setAnalysisId(analysisId)
                .setPatient(patient)
                .setInputText(inputText)
                .setPredictedConditions(predictedConditions)
                .setSuggestedSymptoms(suggestedSymptoms)
                .setConfidenceScore(confidenceScore)
                .setCreatedAt(createdAt)
                .build();
    }
}
