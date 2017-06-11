
public class FluidMain {
	public static void main(String args[]){
		
		int N = 250;
		double T = 5.;
		double c = 0.8;
		Point[][] points = Point.DisorderedLattice(N, c);
		Lattice lattice = new Lattice(points, T);
		//lattice.printLattice();

		LatticeFrame lf = new LatticeFrame(lattice);
		
	}
}
