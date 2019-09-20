import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Main {
	public static void main(String[] args){
		JFrame frame = new JFrame("Fotag");
		ImageCollectionModel icm = new ImageCollectionModel();
		Toolbar toolbar = new Toolbar(icm);
		ImageCollectionView icv = new ImageCollectionView(icm);
		JScrollPane jsp = new JScrollPane(icv, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		icm.addView(icv);
		icm.enter();
		// frame setting
		frame.setLayout(new BorderLayout());
		frame.add(toolbar, BorderLayout.NORTH);
		frame.add(jsp, BorderLayout.CENTER);
		frame.setMaximumSize(new Dimension(1250, 9000));
		frame.setMinimumSize(new Dimension(600, 400));
		frame.setPreferredSize(new Dimension(900,900));
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		frame.addComponentListener(new ComponentAdapter() {
  		@Override
    	public void componentResized(ComponentEvent e) {
				icv.refresh();
			}
		});

		frame.addWindowListener(new WindowAdapter() {
		   @Override
		   public void windowClosing(WindowEvent we) {
				 super.windowClosing(we);
				 icm.exit();
			 }
		 });
	 }
}
