import java.util.Random;


public class Lattice {

	Point[][] Lattice;
	static double J = 1., kB = 1;
	double T;
	int N;
	static Random rand = new Random();

	public Lattice(Point[][] Lattice, double T){
		this.Lattice = Lattice;
		this.N = Lattice.length;

		this.T = T;
	}


	public Point[][] getLattice(){
		return Lattice;
	}
	public void setLattice(Point[][] Lattice){
		this.Lattice = Lattice;
	}
	public int getState(int i, int j){
		i = (i+N)%N;
		j = (j+N)%N;

		return getLattice()[i][j].getState();
	}
	public void setState(int i, int j, int state){
		getLattice()[i][j].setState(state);
	}
	public double getT(){
		return T;
	}
	public void setT(double T){
		this.T = T;
	}

	
	public void printLattice(){
		for(int i = 0; i < N; i++){
			for(int j = 0; j < N; j++){
				System.out.print(getState(i, j));
			}
		System.out.println();
		}
	}
	
	public double systemEnergy(){
		
		double runningTotal = 0.;
		
		for(int i = 0; i < N; i++){
			for(int j = 0; j < N; j++){
				runningTotal+= getState(i, j)*getState(i+1, j) + getState(i, j)*getState(i, j+1);
			}
		}
		return -J*runningTotal;
	}

	public double localEnergy(int i, int j){

		double E1 = getState(i, j)*getState(i+1, j);
		double E2 = getState(i, j)*getState(i-1, j);
		double E3 = getState(i, j)*getState(i, j+1);
		double E4 = getState(i, j)*getState(i, j-1);

		return -J*(E1+E2+E3+E4);
	}

	public double boltzmannWeight(double E){
		return Math.exp(-E/(kB*getT()));
	}

	public boolean boltzmannSuccess(double deltaE){

		double prob = rand.nextDouble();

		if(prob <= boltzmannWeight(deltaE)) return true;

		return false;
	}
	
	public void spinFlipper(int i, int j){

		if(getState(i, j) == 1) setState(i, j, -1);
		else setState(i, j, 1);
	}
	
	public void spinSwapper(int i1, int j1, int i2, int j2){
		int state1 = getState(i1, j1);
		int state2 = getState(i2, j2);

		setState(i1, j1, state2);
		setState(i2, j2, state1);
	}
	
	public void spinFlip(){
		
		int iRand = rand.nextInt(N);
		int jRand = rand.nextInt(N);
		
		if(getState(iRand, jRand) != 0){

		double origLE = localEnergy(iRand, jRand);

		spinFlipper(iRand, jRand);

		double newLE = localEnergy(iRand, jRand);
		double deltaE = newLE - origLE;

		if(deltaE > 0 && !boltzmannSuccess(deltaE)) spinFlipper(iRand, jRand);
		}
	}
	
	public void spinSwap(){
		int iRand1 = rand.nextInt(N);
		int jRand1 = rand.nextInt(N);
		int iRand2 = rand.nextInt(N);
		int jRand2 = rand.nextInt(N);

		double origLE = localEnergy(iRand1, jRand1) + localEnergy(iRand2, jRand2);

		spinSwapper(iRand1, jRand1, iRand2, jRand2);

		double newLE = localEnergy(iRand1, jRand1) + localEnergy(iRand2, jRand2);
		double deltaE = newLE - origLE;

		if(Math.abs(iRand1 - iRand2) == 1 && Math.abs(jRand1 - jRand2) == 1) deltaE += 4*J; //need to correct for periodic bc

		if(deltaE > 0 && !boltzmannSuccess(deltaE)) spinSwapper(iRand1, jRand1, iRand2, jRand2);
	}
	
	
	public void update(){
		
		double p = rand.nextDouble();
		
		if(p<=0.5) spinFlip();
		else if(p >0.5) spinSwap();
	}
	
	
	public static void heatCapacity(){
		
		int N = 50;
		double c = 0.8;
		double T = 0.1;
		double increment = 
		
		
	}
	
	
	
	
	


}
