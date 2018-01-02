package hu.elte.progtech.calculator;

import hu.elte.progtech.calculator.model.CalculatorEngine;
import hu.elte.progtech.calculator.view.CalculatorFrame;

public class Launcher {

    public static void main(String[] args) {
        CalculatorEngine model = new CalculatorEngine();
        CalculatorFrame view = new CalculatorFrame(model);
        view.showFrame();
    }

}
