import java.util.Random;


public class Point {

	int state;
	static Random rand = new Random();

	public Point(int state){
		this.state = state;
	}

	public int getState(){
		return state;
	}
	public void setState(int state){
		this.state = state;
	}


	public static Point[][] DisorderedLattice(int N, double c){

		Point[][] lattice = new Point[N][N];

		for(int i = 0; i < N; i++){
			for(int j = 0; j < N; j++){
				lattice[i][j] = new Point(9);
			}
		}

		for(int i = 0; i < N; i++){
			for(int j = 0; j < N; j++){
				if(rand.nextDouble() <= c){
					if(rand.nextDouble() <= 0.5) lattice[i][j] = new Point(1);
					else  lattice[i][j] = new Point(-1);
				}
				else lattice[i][j] = new Point(0);
			}
		}

		return lattice;
	}

}
