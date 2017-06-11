import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

class LatticePanel extends JPanel{
	Lattice lattice;
	int N;
	int iteration = 0;

	public LatticePanel(Lattice lattice){
		this.lattice = lattice;
		this.N = lattice.getLattice().length;
		setBackground(Color.BLACK);
	}

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);

		int w = getWidth()/N;
		int h = getHeight()/N;

		for(int i = 0; i < N; i++){
			for(int j = 0; j < N; j++){

				if(lattice.getState(i, j) == 1){
					g.setColor(Color.RED);
					g.fillRect(w*i, h*j, w, h);

				}else if (lattice.getState(i, j) == -1){
					g.setColor(Color.BLUE);
					g.fillRect(w*i, h*j, w, h);

				}else if (lattice.getState(i, j) == 0){
					g.setColor(Color.WHITE);
					g.fillRect(w*i, h*j, w, h);
				}
			}
		}
	}


	public void animate(){
		for(int i = 0; i < N*N; i++){
			lattice.update();
		}
		repaint();
	}

	public void changeTemperature(double T){
		lattice.setT(T);
		repaint();
	}

	public void changeC(double c){
		double T = lattice.getT();
		Point[][] points = Point.DisorderedLattice(N, c);
		lattice = new Lattice(points, T);
	}
}

	public class LatticeFrame extends JFrame{

		LatticePanel lPan;
		Lattice lattice;
		Timer timer;
		JLabel tempLabel = new JLabel("Temperature: ");
		JTextField tempField = new JTextField("T", 10);
		JLabel cLabel = new JLabel("c: ");
		JTextField cField = new JTextField("c", 10);

		JButton goButton = new JButton("Go");


		public LatticeFrame(Lattice lattice){

			this.lattice = lattice;
			this.lPan = new LatticePanel(lattice);
			lPan.setPreferredSize(new Dimension(500, 500));

			JPanel controlPanel = new JPanel();
			controlPanel.add(goButton);
			controlPanel.add(tempLabel);
			controlPanel.add(tempField);
			controlPanel.add(cLabel);
			controlPanel.add(cField);

			getContentPane().add(lPan, BorderLayout.CENTER);
			getContentPane().add(controlPanel, BorderLayout.SOUTH);
			pack();

			addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					e.getWindow().dispose();
					System.exit(0);
				}
			});

			setTitle("Superfluid");
			setLocation(100, 20);
			setVisible(true);
			setBackground(Color.LIGHT_GRAY);

			go();
			changeT();
			changeC();

		}


		public void go(){
			timer = new Timer(0, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// update action
					lPan.animate();
				}
			});

			goButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){

					if(!timer.isRunning()){
						timer.start();
						goButton.setText("Stop");
					}else{
						timer.stop();
						goButton.setText("Go");
					}
				}
			});
		}


		public void changeT(){
			tempField.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					double T = Double.parseDouble(tempField.getText());
					lPan.changeTemperature(T);
				}
			});
		}

		public void changeC(){
			cField.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					double c = Double.parseDouble(cField.getText());
					lPan.changeC(c);
				}
			});
		}
	}












