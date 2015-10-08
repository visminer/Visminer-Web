package br.edu.ufba.softvis.visminerweb.view;

public enum Perspective {

	DEFAULT,
	TECHNICAL_DEBT;

	public static String toString(Perspective perspective) {
		String str = "Default";
		
		if (perspective == TECHNICAL_DEBT) {
			str = "Technical Debt";
		}
		
		return str;
	}
	
}
