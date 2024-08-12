package com.adovzhenko;

import jakarta.xml.bind.JAXBException;
import org.jpmml.evaluator.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScikitHistGradBoostRegressionMain {

    public static void main(String[] args) throws JAXBException, ParserConfigurationException, SAXException {
        String pmmlFile = "/cal_house_hgbr.pmml";
        InputStream is = ScikitHistGradBoostRegressionMain.class.getResourceAsStream(pmmlFile);
        Evaluator evaluator = new LoadingModelEvaluatorBuilder().load(is).build();
        evaluator.verify();

        List<InputField> inputFields = evaluator.getInputFields();
        System.out.println("Input fields: " + inputFields);

        List<TargetField> targetFields = evaluator.getTargetFields();
        System.out.println("Target field(s): " + targetFields);

        long time = System.currentTimeMillis();
        System.out.println("Time " + time);

        Map<String, Object> input = new HashMap<>() {
            {
                put("MedInc", 8.3252);
                put("HouseAge", 41.0);
                put("AveRooms", 6.984126984126984);
                put("AveBedrms", 1.0238095238095237);
                put("Population", 322.0);
                put("AveOccup", 2.5555555555555554);
                put("Latitude", 37.88);
                put("Longitude", -122.23);
            }
        };
        for (int i = 0; i < 1_000_000; i++) {
            Map<String, ?> target = evaluator.evaluate(input);
        }
//        System.out.println(target);
        System.out.println("Spent " + (System.currentTimeMillis() - time));

    }

}