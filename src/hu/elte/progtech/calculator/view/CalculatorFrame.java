package hu.elte.progtech.calculator.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import hu.elte.progtech.calculator.model.CalculatorEngine;

public class CalculatorFrame extends JFrame {

    private static final long serialVersionUID = -2926906882100803757L;
    private static final String ASSIGNMENT_LABEL = "=";
    private static final String PLUS_LABEL = "+";

    private CalculatorEngine model;
    private Container buttons;
    private JTextField display;

    public CalculatorFrame(CalculatorEngine model) {
        super("Calculator");
        this.model = model;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

    public void showFrame() {
        addComponents();
        pack();
        setVisible(true);
    }

    private void addComponents() {
        addDisplay();
        addButtonsPart();
    }

    private void addDisplay() {
        display = new JTextField();
        display.setFont(new Font("Arial", Font.BOLD, 30));
        display.setHorizontalAlignment(JTextField.TRAILING);
        display.setEditable(false);
        updateDisplay();
        getContentPane().add(display, BorderLayout.NORTH);
    }

    private void addButtonsPart() {
        addButtonsContainer();
        addButtons();
    }

    private void addButtonsContainer() {
        buttons = new Container();
        getContentPane().add(buttons, BorderLayout.SOUTH);
        int countOfColumns = (int) Math.sqrt(model.getFieldSize());
        buttons.setLayout(new GridLayout(countOfColumns + 1, countOfColumns));
    }

    private void addButtons() {
        for (int i = 1; i <= model.getFieldSize(); ++i) {
            addNumericButton(i);
        }
        addPlusButton();
        addNumericButton(0);
        addAssignmentButton();
    }

    private void addNumericButton(int value) {
        addButton(Integer.toString(value), (e) -> pushButton(model::pushNumericButton, value));
    }

    private void addPlusButton() {
        addButton(PLUS_LABEL, (e) -> pushButton(model::pushPlusButton));
    }

    private void addAssignmentButton() {
        JButton button = addButton(ASSIGNMENT_LABEL, (e) -> pushButton(model::pushAssignmentButton));
        button.setEnabled(false);
    }

    private JButton addButton(String label, ActionListener listener) {
        JButton button = new JButton(label);
        button.addActionListener(listener);
        button.setFont(new Font("Arial", Font.BOLD, 30));
        button.setPreferredSize(new Dimension(100, 100));
        buttons.add(button);
        return button;
    }

    private void pushButton(Runnable fun) {
        fun.run();
        update();
    }

    private void pushButton(Consumer<Integer> fun, int i) {
        fun.accept(i);
        update();
    }

    private void update() {
        updateDisplay();
        updateButtons();
    }

    private void updateDisplay() {
        display.setText(Integer.toString(model.getCurrentValue()));
    }

    private void updateButtons() {
        for (Component component : buttons.getComponents()) {
            component.setEnabled(shouldBeEnabled((JButton) component));
        }
    }

    private boolean shouldBeEnabled(JButton button) {
        return !model.isCompleted() && !isPlusWhenRightIsCurrentOperand(button) && !isAssignmentWhenLeftIsCurrentOperand(button);
    }

    private boolean isPlusWhenRightIsCurrentOperand(JButton button) {
        return button.getText().equals(PLUS_LABEL) && !model.isLeftTheCurrentOperand();
    }

    private boolean isAssignmentWhenLeftIsCurrentOperand(JButton button) {
        return button.getText().equals(ASSIGNMENT_LABEL) && model.isLeftTheCurrentOperand();
    }

}
