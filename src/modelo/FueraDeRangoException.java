package modelo;

public class FueraDeRangoException extends Exception{

	public FueraDeRangoException() {
		super("La coordenada esta fuera del rango del tablero");
		
	}

}
