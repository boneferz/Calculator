package sample;

public enum Operators {
	PLUS("+"), MINUS("-"), MULTIPLY("*"), DIVIDE("/"), MODULE("%"), NOT(" ");
	
	private String value = "";
	
	Operators(String s) {
		value = s;
	}
	
	public String getValue() {
		return value;
	}
}
