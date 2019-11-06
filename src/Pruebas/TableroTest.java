package Pruebas;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import javax.security.auth.login.FailedLoginException;

import org.junit.jupiter.api.Test;

import modelo.Casilla;
import modelo.CasillaDestapadaException;
import modelo.FormatoInvalidoException;
import modelo.FueraDeRangoException;
import modelo.Tablero;

class TableroTest {
	public static final int FACIL=8;
	public static final int MEDIO=16;
	public static final int DIFICIL=30;
	
	public static final int EASY_MINES= 10;
	public static final int MIDDLE_MINES= 40;
	public static final int HARD_MINES=99;
	
	
	private int dificultad;
	private int columnas;
	private int filas;
	
	private Tablero tablerio;
	
	public void escenario1(int dificulta) {
		tablerio= new Tablero(dificulta);
		this.dificultad= dificulta;
		switch(dificultad) {
		case 1:
			columnas=FACIL;
			filas=FACIL;
			break;
			
		case 2:
			columnas=MEDIO;
			filas=MEDIO;
			break;
			
		case 3:
			filas=MEDIO;
			columnas=DIFICIL;
			
			break;
		}
			
	}

	@Test
	void testCrearTablero() {
		escenario1(1);
		tablerio.crearTablero();
		assertTrue(tablerio.getTablero()!=null);
	}
	
	@Test
	void testRevisarCoordenada1() throws NumberFormatException, FormatoInvalidoException, FueraDeRangoException, CasillaDestapadaException {
		escenario1(1);
		String coordenadaInvalida = "hsfkjdhf";
		try {
		tablerio.revisarCoordenada(coordenadaInvalida);
		 fail("Se esperaba excepcion FormatoInvalidoException");
		}catch (FormatoInvalidoException e) {
			assertTrue(true);
		}catch (FueraDeRangoException e) {
			fail("no se esperaba excepcion");
		}catch (CasillaDestapadaException e) {
			fail("no se esperaba excepcion");
		}
	}
	
	@Test
	void testRevisarCoordenada2() throws NumberFormatException, FormatoInvalidoException, FueraDeRangoException, CasillaDestapadaException {
		escenario1(1);
		String coordenadaInvalida = "9-9";
		try {
		tablerio.revisarCoordenada(coordenadaInvalida);
		 fail("Se esperaba excepcion FueraDeRangoExceptio");
		}catch (FormatoInvalidoException e) {
			fail("no se esperaba excepcion");
		}catch (FueraDeRangoException e) {
			assertTrue(true);
		}catch (CasillaDestapadaException e) {
			fail("no se esperaba excepcion");
		}
	}
	
	
	@Test
	void testRevisarCoordenada3() throws NumberFormatException, FormatoInvalidoException, FueraDeRangoException, CasillaDestapadaException {
		escenario1(1);
		String coordenadaInvalida = "5-5";
		int cont=0;
		while(cont<2) {
		try {
		tablerio.revisarCoordenada(coordenadaInvalida);
		if (cont==1) {
			fail("Se esperaba excepcion CasillaDestapadaException");
		}
		 
		}catch (FormatoInvalidoException e) {
			
		}catch (FueraDeRangoException e) {
			fail("no se esperaba excepcion");
		}catch (CasillaDestapadaException e) {
			if (cont==0) {
				fail("no se esperaba excepcion");
			} else {
				assertTrue(true);
			}		
		}
		}
	}
	
	@Test
	void testRevisarCoordenada4() throws NumberFormatException, FormatoInvalidoException, FueraDeRangoException, CasillaDestapadaException {
		escenario1(1);
		String coordenadaInvalida = "3-3";
		try {
		tablerio.revisarCoordenada(coordenadaInvalida);
		assertTrue(true);
		}catch (FormatoInvalidoException e) {
			fail("no se esperaba excepcion");
		}catch (FueraDeRangoException e) {
			fail("no se esperaba excepcion");
		}catch (CasillaDestapadaException e) {
			fail("no se esperaba excepcion");
		}
	}

}
