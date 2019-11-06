package modelo;

public class Casilla {
	
	private boolean mina;
	private boolean destapada;
	private int id;
	
	public Casilla(boolean mina,int id) {
		this.mina= mina;
		this.id=id;
		this.destapada=false;
	}

	
	public boolean getDestapada() {
		return destapada;
	}

	public void setDestapada(boolean destapada) {
		this.destapada = destapada;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isMina() {
		return mina;
	}

	public void setMina(boolean mina) {
		this.mina = mina;
	}
	
	

}
