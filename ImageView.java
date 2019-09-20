import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.Dimension;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ImageView extends JPanel implements IView{
  private ImageModel im;
  private ImageCollectionView icv;
  public ImageView(ImageModel im, ImageCollectionView icv){
    this.im = im;
    this.icv = icv;
    this.setLayout(new GridBagLayout());
    this.setBackground(Color.WHITE);
    //paint
    String path = im.getPath();
    String name = im.getName();
    String creation = im.getCreation();
    int stars = im.getStars();
  	BufferedImage img = im.getImg();
    JLabel icon = new JLabel(new ImageIcon(img));
    GridBagConstraints gc = new GridBagConstraints();
    gc.weightx = 1;
    gc.gridx = 0;
    gc.gridy = 0;
    add(icon, gc);
    this.setPreferredSize(new Dimension(380,550));

    icon.addMouseListener(new MouseAdapter(){
      public void mouseClicked(MouseEvent e) {
        try{
          BufferedImage image = ImageIO.read(new File(im.getPath()));
          image = Toolbar.standardize(580, 800, image);
          GridBagConstraints gc = new GridBagConstraints();
          JFrame frame = new JFrame();
          frame.setLayout(new BorderLayout());
          JPanel pane = new JPanel();
          pane.setPreferredSize(new Dimension(800, 600));
          JLabel imageIcon = new JLabel(new ImageIcon(image));
          gc.weightx = 1;
          gc.gridx = 0;
          gc.gridy = 1;
          pane.add(imageIcon, gc);
          //add stars
          JPanel p = new JPanel();
          p.setLayout(new GridBagLayout());
          int c = 0;
          ArrayList<JLabel> stars = icv.putStar(im);
          for(JLabel s: stars){
            gc.weightx = 0;
            gc.gridx = c;
            gc.gridy = 1;
            p.add(s, gc);
            c++;
          }
          //////frame setting
          frame.setBackground(Color.WHITE);
          pane.setBackground(Color.WHITE);
          p.setBackground(Color.WHITE);
          frame.add(pane, BorderLayout.CENTER);
          frame.add(p, BorderLayout.SOUTH);
          frame.setVisible(true);
          frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
          frame.setMaximumSize(new Dimension(800, 600));
          frame.setMinimumSize(new Dimension(800, 600));
        }catch(IOException ioe){
          System.out.println("new window exception");
        }
      }
    });

  }


  public void updateView(int state) {
		System.out.println("ImageView: updateView");
	}

}
