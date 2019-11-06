package modelo;

public class Tablero {
	
	public static final int FACIL=8;
	public static final int MEDIO=16;
	public static final int DIFICIL=30;
	
	public static final int EASY_MINES= 10;
	public static final int MIDDLE_MINES= 40;
	public static final int HARD_MINES=99;
	
	private Casilla[][] tablero;
	private int dificultad;
	private int columnas;
	private int filas;
	
	
	public Tablero(int dificultad) {
		this.dificultad= dificultad;
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
		crearTablero();
	}
	
	public boolean revisarCoordenada(String coordenada) throws FormatoInvalidoException , FueraDeRangoException, CasillaDestapadaException, NumberFormatException{
		boolean valido=true;
		String[] numeros= null;
		boolean continuar= true;
		for (int i = 0; i < coordenada.length()&&valido; i++) {
			if (!(i==0||i==coordenada.length()-1)) {
				valido=coordenada.charAt(i)>=49&&coordenada.charAt(i)<=57||coordenada.charAt(i)==45;
			}else {
				valido=coordenada.charAt(i)>=49&&coordenada.charAt(i)<=57;
			}
		}
		
		if (!valido) {
			continuar=false;
			throw new FormatoInvalidoException();
		} else {
			numeros=coordenada.split("-");
			
			try {
			if (!(Integer.parseInt(numeros[0])>=0&&Integer.parseInt(numeros[0])<=filas&&Integer.parseInt(numeros[1])>=0&&Integer.parseInt(numeros[1])<=columnas)) {
				continuar=false;
				throw new FueraDeRangoException();
			}
			
			if (tablero[Integer.parseInt(numeros[0])-1][Integer.parseInt(numeros[1])-1].getDestapada()) {
				continuar=false;
				throw new CasillaDestapadaException();
			}
	}catch(NumberFormatException e) {
		
	}
	}
		
			return valido;
	
		
	}
	
	
	public void crearTablero() {
		
		switch(dificultad) {
		case 1:
			int x=0;
			tablero = new Casilla[filas][columnas];
			for (int i = 0; i < tablero.length; i++) {
				for (int j = 0; j < tablero[i].length; j++) {
					Casilla casilla = new Casilla(false, x);
					tablero[i][j] = casilla;
					x++;
				}
			}

			int m = 0;
			while (m < EASY_MINES) {
				int fila = (int) ((Math.random() * FACIL-1) + 1);
				int columna = (int) ((Math.random() * FACIL-1) + 1);
				if (!tablero[fila][columna].isMina()) {
					tablero[fila][columna].setMina(true);
					m++;
				}
			}
			break;
			
		case 2:
			int y=0;
			tablero= new Casilla[filas][columnas];
			for (int i = 0; i < tablero.length; i++) {
				for (int j = 0; j < tablero[i].length; j++) {
					Casilla casilla = new Casilla(false,y);
					tablero[i][j] = casilla;
					y++;
				}
			}

			int n = 0;
			while (n < 	MIDDLE_MINES) {
				int fila = (int) ((Math.random() * filas-1) + 1);
				int columna = (int) ((Math.random() * columnas-1) + 1);
				if (!tablero[fila][columna].isMina()) {
					tablero[fila][columna].setMina(true);
					n++;
				}
			}
			break;
			
		case 3:
			int z=0;
			tablero= new Casilla[filas][columnas];
			for (int i = 0; i < filas; i++) {
				for (int j = 0; j <columnas; j++) {
					Casilla casilla = new Casilla(false,z);
					tablero[i][j] = casilla;
					z++;
				}
			}

			int o = 0;
			while (o < HARD_MINES) {
				int fila = (int) ((Math.random() * filas-1) + 1);
				int columna = (int) ((Math.random() * columnas-1) + 1);
				if (!tablero[fila][columna].isMina()) {
					tablero[fila][columna].setMina(true);
					o++;
				}
			}
			break;
		}
		
		
	}

	public int[] revisarAlrededor(int fila, int columna,int pos) {
		int[] datos= new int[4];
		int numeroMinas=0;
		if (!tablero[fila][columna].isMina()) {
			for (int i=fila-1; i <= fila+1; i++) {
				if(i>=0 && i<filas) {
				for (int j=columna-1; j <= columna+1; j++) {
					if(j>=0&&j<columnas) {
						if (tablero[i][j].isMina()) {
						numeroMinas++;
						}
					}
				}
				}
			}
		}else {
			numeroMinas=-1;
		}
		datos[0]=numeroMinas;
		datos[1]=fila;
		datos[2]=columna;
		datos[3]=pos;
		return datos;
	}
	public Casilla[][] getTablero() {
		return tablero;
	}

	public void setTablero(Casilla[][] tablero) {
		this.tablero = tablero;
	}

	public int getDificultad() {
		return dificultad;
	}

	public void setDificultad(int dificultad) {
		this.dificultad = dificultad;
	}
	
	public int[] convertirRevisar(String posicion) {
		
		
		double pos=(double)( Integer.parseInt(posicion));
		int fila=0;
		int columna=0;
		
		switch (dificultad) {
		case 1:
			double prime=(double)(pos/columnas);
			fila= (int)prime;
			double s= (double)(100.0/columnas);
			columna = (int)(((prime-fila)/s)*100);
			break;
		case 2:
			double primer=(double)(pos/columnas);
			fila= (int)primer;
			double sa= (double)(100.0/columnas);
			columna = (int)(((primer-fila)/sa)*100);
			break;
		case 3:
			double primeo=(double)(pos/columnas);
			fila= (int)primeo;
			double sp= (double)(100.0/columnas);
			columna = (int)(((primeo-fila)/sp)*100);
			break;
		}
		tablero[fila][columna].setDestapada(true);
		return revisarAlrededor(fila, columna,(int)(pos));
	}

}
