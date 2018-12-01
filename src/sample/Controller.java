package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static sample.Operators.*;

public class Controller {
	
	@FXML
	private TextField outputTextField;
	
	
	@FXML
	private Button enter;

	@FXML
	private Button b7;

	@FXML
	private Button b8;

	@FXML
	private Button b9;

	@FXML
	private Button b4;

	@FXML
	private Button b5;

	@FXML
	private Button b6;

	@FXML
	private Button b1;

	@FXML
	private Button b2;

	@FXML
	private Button b3;

	@FXML
	private Button clear;

	@FXML
	private Button del;

	@FXML
	private Button mod;

	@FXML
	private Button divi;

	@FXML
	private Button multi;

	@FXML
	private Button minus;

	@FXML
	private Button plus;

	@FXML
	private Button b0;
	
	@FXML
	private Button minusing;
	
	@FXML
	private Button reset;
	
	@FXML
	private Button dot;
	
	@FXML
	private TextField operator;
	
	private BigDecimal inputNumber;
	private BigDecimal aNumber;
	private BigDecimal bNumber;
	private BigDecimal result;
	private Operators currentOperator;
	private boolean isShiftPressed;
	private boolean isDecimal;
	private String state;
	
	private final String SETTING_A = "set A";
	private final String SETTING_B = "set B";
	private final String SETTING_RESULT = "set Result";
	private final int MAX_DISPLAY_LENGTH = 12;
	
	private void init() {
		aNumber = new BigDecimal(0);
		bNumber = new BigDecimal(0);
		result = new BigDecimal(0);
		inputNumber = new BigDecimal(0);
		isShiftPressed = false;
		isDecimal = false;
		
		state = SETTING_A;
		currentOperator = NOT;
		operator.setText(currentOperator.getValue());
		resetInputNumber();
	}
	
	@FXML
	private void initialize() {
		
		init();
		
		Main.stage.addEventHandler(KeyEvent.KEY_PRESSED, this::keyDown);
		Main.stage.addEventHandler(KeyEvent.KEY_RELEASED, this::keyUp);
		
		b0.setOnAction( ae -> enterNumber("0"));
		b1.setOnAction( ae -> enterNumber("1"));
		b2.setOnAction( ae -> enterNumber("2"));
		b3.setOnAction( ae -> enterNumber("3"));
		b4.setOnAction( ae -> enterNumber("4"));
		b5.setOnAction( ae -> enterNumber("5"));
		b6.setOnAction( ae -> enterNumber("6"));
		b7.setOnAction( ae -> enterNumber("7"));
		b8.setOnAction( ae -> enterNumber("8"));
		b9.setOnAction( ae -> enterNumber("9"));
		
		dot.setOnAction( ae -> enterNumber("."));
		minusing.setOnAction( ae -> enterNumber("-"));
		clear.setOnAction( ae -> clear());
		del.setOnAction( ae -> delete());
	}
	
	@FXML
	private void resetBtnListener(MouseEvent e) {
		if (e.getSource().equals(reset))
			init();
	}
	
	private void clear() {
		if (!outputTextField.getText().equals("0")) {
			if (state.equals(SETTING_RESULT))
				aNumber = new BigDecimal(0);
			resetInputNumber();
		}
		
		isDecimal = false;
	}
	
	private void delete() {
		String inputStringerNum = outputTextField.getText();
		int lengthWithMinus = 1;
		
		if (inputStringerNum.contains("-"))
			lengthWithMinus = 2;
			
		if (inputStringerNum.length() > lengthWithMinus) {
			
			inputStringerNum = inputStringerNum.substring(0, inputStringerNum.length() - 1);
			
			// cut '.'
			if (inputStringerNum.charAt(inputStringerNum.length() - 1) == '.') {
				inputStringerNum = inputStringerNum.substring(0, inputStringerNum.length() - 1);
				if (isDecimal)
					isDecimal = false;
			}
			
			inputNumber = new BigDecimal(inputStringerNum);
			
			if (!state.equals(SETTING_B))
				aNumber = inputNumber;
			else if (state.equals(SETTING_B))
				bNumber = inputNumber;
			
			outputTextField.setText(inputStringerNum);
		} else {
			if (!outputTextField.getText().equals("0")) {
				if (state.equals(SETTING_RESULT))
					aNumber = new BigDecimal(0);
				resetInputNumber();
			}
		}
	}
	
	private void keyDown(KeyEvent e) {
		
		if (e.getCode() == KeyCode.ENTER) {
			enter.arm();
			
			System.out.println("█1");
		}
		
		switch (e.getCode()) {
			case NUMPAD0:
				b0.arm();
				break;
			case NUMPAD1:
				b1.arm();
				break;
			case NUMPAD2:
				b2.arm();
				break;
			case NUMPAD3:
				b3.arm();
				break;
			case NUMPAD4:
				b4.arm();
				break;
			case NUMPAD5:
				b5.arm();
				break;
			case NUMPAD6:
				b6.arm();
				break;
			case NUMPAD7:
				b7.arm();
				break;
			case NUMPAD8:
				b8.arm();
				break;
			case NUMPAD9:
				b9.arm();
				break;
		}
		
		switch (e.getCode()) {
			case ADD:
				plus.arm();
				break;
			case SUBTRACT:
				minus.arm();
				break;
			case MULTIPLY:
				multi.arm();
				break;
			case DIVIDE:
				divi.arm();
				break;
		}
		
		if (e.getCode() == KeyCode.DECIMAL) {
			dot.arm();
		}
		
		if (e.getCode() == KeyCode.SHIFT)
			isShiftPressed = true;
		if (isShiftPressed && e.getCode() == KeyCode.DIGIT5)
			mod.arm();
		
		switch (e.getCode()) {
			case ESCAPE:
				reset.arm();
				break;
			case BACK_SPACE:
				clear.arm();
				break;
			case DELETE:
				del.arm();
				break;
		}
		
		if (e.getCode() == KeyCode.MINUS) {
			minusing.arm();
		}
	}
	
	private void keyUp(KeyEvent e) {
		
		if (e.getCode().toString().equals("ENTER")) {
			enterResultListener();
			enter.disarm();
		}
		
		switch (e.getCode()) {
			case NUMPAD0:
				enterNumber("0");
				b0.disarm();
				break;
			case NUMPAD1:
				enterNumber("1");
				b1.disarm();
				break;
			case NUMPAD2:
				enterNumber("2");
				b2.disarm();
				break;
			case NUMPAD3:
				enterNumber("3");
				b3.disarm();
				break;
			case NUMPAD4:
				enterNumber("4");
				b4.disarm();
				break;
			case NUMPAD5:
				enterNumber("5");
				b5.disarm();
				break;
			case NUMPAD6:
				enterNumber("6");
				b6.disarm();
				break;
			case NUMPAD7:
				enterNumber("7");
				b7.disarm();
				break;
			case NUMPAD8:
				enterNumber("8");
				b8.disarm();
				break;
			case NUMPAD9:
				enterNumber("9");
				b9.disarm();
				break;
		}
		
		if (e.getCode() == KeyCode.DECIMAL) {
			enterNumber(".");
			dot.disarm();
		}
		
		if (e.getCode() == KeyCode.SHIFT) isShiftPressed = false;
		if (e.getCode() == KeyCode.DIGIT5) {
			mod.disarm();
		}
		
		switch (e.getCode()) {
			case ESCAPE:
				reset.disarm();
				init();
				break;
			case BACK_SPACE:
				clear.disarm();
				clear();
				break;
			case DELETE:
				del.disarm();
				delete();
				break;
		}
		
		if (e.getCode() == KeyCode.MINUS) {
			minusing.disarm();
			enterNumber("-");
		}
		
		switch (e.getCode()) {
			case ADD:
				plus.disarm();
				currentOperator = PLUS;
				break;
			case SUBTRACT:
				minus.disarm();
				currentOperator = MINUS;
				break;
			case MULTIPLY:
				multi.disarm();
				currentOperator = MULTIPLY;
				break;
			case DIVIDE:
				divi.disarm();
				currentOperator = DIVIDE;
				break;
		}
		
		if (e.getCode() == KeyCode.ADD ||
			e.getCode() == KeyCode.SUBTRACT ||
			e.getCode() == KeyCode.MULTIPLY ||
			e.getCode() == KeyCode.DIVIDE )
			switchState();
	}
	
	@FXML
	private void enterResultListener() {
		if (state.equals(SETTING_B) && !currentOperator.equals(NOT)) {
			state = SETTING_RESULT;
			
			if (inputNumber.toString().equals(outputTextField.getText()))
				bNumber = new BigDecimal(cutZerosAndDot(inputNumber.toString()));
			else
				bNumber = new BigDecimal(cutZerosAndDot(outputTextField.getText()));
			
			// math operations
			switch (currentOperator) {
				case PLUS:
					result = aNumber.add(bNumber);
					break;
				case MINUS:
					result = aNumber.subtract(bNumber);
					break;
				case MULTIPLY:
					result = aNumber.multiply(bNumber);
					break;
				case DIVIDE:
					if (bNumber.signum() == 0) {
						result = new BigDecimal(666);
						operator.setText("you summoned Satan");
					}
					else
						result = aNumber.divide(bNumber, 10, RoundingMode.DOWN);
					break;
				case MODULE:
					result = aNumber.remainder(bNumber);
					break;
			}
			
			// max display length
			if (result.toString().length() > MAX_DISPLAY_LENGTH)
				result = new BigDecimal(result.toString().substring(0, MAX_DISPLAY_LENGTH));
			
			// result
			result = new BigDecimal(cutZerosAndDot(result.toString()));
			inputNumber = result;
			outputTextField.setText(inputNumber.toString());
			
			if (!(currentOperator.equals(DIVIDE) && bNumber.signum() == 0))
				operator.setText(operator.getText() + " " + bNumber);
			
			currentOperator = NOT;
			aNumber = result;
			inputNumber = new BigDecimal(0);
		}
	}
	
	@FXML
	private void operatorsButtonsListener(MouseEvent e) {
		if (e.getSource().equals(mod)) {
			currentOperator = MODULE;
		} else if (e.getSource().equals(plus)) {
			currentOperator = PLUS;
		} else if (e.getSource().equals(minus)) {
			currentOperator = MINUS;
		} else if (e.getSource().equals(multi)) {
			currentOperator = MULTIPLY;
		} else if (e.getSource().equals(divi)) {
			currentOperator = DIVIDE;
		}
		
		switchState();
	}
	
	private void enterNumber(String n) {
		String inputNumberToString = inputNumber.toString();
		String screenText = outputTextField.getText();
		
		if (state.equals(SETTING_RESULT)) {
			state = SETTING_A;
			
			inputNumber = new BigDecimal(0);
			isDecimal = false;
			
			// continue with '-'
			if (n.equals("-")) {
				inputNumberToString = screenText;
			} else {
				inputNumberToString = "0";
				screenText = "0";
			}
			
			operator.setText(currentOperator.getValue());
			resetInputNumber();
		}
		if (inputNumberToString.length() == MAX_DISPLAY_LENGTH) return;
		
		// enter symbols '-' , '.' , '9'
		switch (n) {
			case "-":
				if (state.equals(SETTING_B)) {
					inputNumberToString = "-" + screenText;
				} else {
					// cut '-'
					if (inputNumberToString.contains("-"))
						inputNumberToString = inputNumberToString.substring(1, inputNumberToString.length());
					else
						inputNumberToString = "-" + inputNumberToString;
				}
				break;
			case ".":
				if (isDecimal) return;
				isDecimal = true;
				break;
			default:
				// N. ? -> N.N
				if (isDecimal && screenText.charAt(screenText.length() - 1) == '.') {
					inputNumberToString += ".";
				}
				// '0' or N
				if (inputNumberToString.equals("0")) {
					if (n.equals("0") && state.equals(SETTING_A)) return;
					inputNumberToString = n;
				} else {
					inputNumberToString += n;
				}
				break;
		}
		
		// set inputNumber
		if (inputNumberToString.contains(".")) {
			int scaleLength = inputNumberToString.split("\\.")[1].length();
			
			inputNumber = new BigDecimal(inputNumberToString).setScale(scaleLength, BigDecimal.ROUND_HALF_UP);
		
		} else {
			inputNumber = new BigDecimal(inputNumberToString);
		}
		
		// output text
		if (isDecimal) {
			if (!screenText.contains(".")) {
				outputTextField.setText(inputNumberToString + ".");
			} else {
				outputTextField.setText(inputNumberToString);
			}
		} else {
			outputTextField.setText(inputNumberToString);
		}
	}
	
	private void switchState() {
		if (state.equals(SETTING_A)) {
			inputNumber = new BigDecimal(cutZerosAndDot(inputNumber.toString()));
			aNumber = inputNumber;
		}
		
		state = SETTING_B;
		inputNumber = new BigDecimal(0);
		
		isDecimal = false;
		operator.setText(cutZerosAndDot(aNumber.toString()) + " " + currentOperator.getValue());
	}
	
	private void resetInputNumber() {
		inputNumber = new BigDecimal(0);
		outputTextField.setText("0");
	}
	
	private String cutZerosAndDot(String s) {
		if (s.contains(".")) {
			// cut zero: 1.01OOOO -> 1.01
			s = s.replaceAll("0*$", "");
			
			// 234.0 -> 234
			if (s.contains(".")) {
				String[] resultPart = s.split("\\.");
				if (resultPart.length == 1 || resultPart[1].equals("0"))
					s = resultPart[0];
			}
		}
		
		return s;
	}
}
