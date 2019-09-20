import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.awt.event.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.util.Date;
import java.nio.file.Files;

public class Toolbar extends JPanel implements IView {
	private ImageCollectionModel icm;
	private ArrayList<JLabel> stars;
	private JLabel gridIcon;
	private JLabel listIcon;
	private JLabel uploadIcon;

	public Toolbar(ImageCollectionModel icm) {
		this.icm = icm;
		setLayout(new GridBagLayout());
		this.setBackground(Color.WHITE);
		this.setPreferredSize(new Dimension(600, 120));
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));

		GridBagConstraints gc = new GridBagConstraints();
		//grid icon
		BufferedImage gridImage;
		try{
			gridImage = ImageIO.read(new File("./resources/grid.png"));
			gridImage = standardize(60, 60, gridImage);
			gridIcon = new JLabel(new ImageIcon(gridImage));

			gc.weightx = 1;
			gc.gridx = 0;
			gc.gridy = 0;
			add(gridIcon, gc);
			gridIcon.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					icm.changeState(1);
				}
			});
		}catch(IOException ex){
			System.out.println("Toolbar: failed to find gridImage");
		}



		//list icon
		BufferedImage listImage;
		try{
			listImage = ImageIO.read(new File("./resources/list.png"));
			listImage = standardize(88, 88, listImage);
			listIcon = new JLabel(new ImageIcon(listImage));
			gc.gridx = 1;
			gc.weightx = 0;
			add(listIcon, gc);
			listIcon.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					icm.changeState(2);
				}
			});
		}catch(IOException ex){
			System.out.println("Toolbar: failed to find listImage");
		}



		//Fotag
		JLabel label1 = new JLabel();
		label1.setText("FOTAG");
		label1.setFont(new Font("Arial", 0, 45));
		gc.gridx = 2;
		gc.weightx = 0;
		this.add(label1, gc);

		//empty space
		JLabel name = new JLabel(" ");
		gc.gridx = 3;
		gc.weightx = 20;
		this.add(name, gc);

		//upload image
		BufferedImage uploadImage;
		try{
			uploadImage = ImageIO.read(new File("./resources/load.png"));
			uploadImage = standardize(45, 45, uploadImage);
			uploadIcon = new JLabel(new ImageIcon(uploadImage));
			gc.gridx = 4;
			gc.weightx = 0;
			this.add(uploadIcon, gc);
		}catch(IOException ex){
			System.out.println("Toolbar: failed to find uploadImage");
		}

		uploadIcon.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				upload();
			}
		});


		//Filtered by
		JLabel label2 = new JLabel();
		label2.setText("Filtered by:");
		label2.setFont(new Font("Arial", 0, 20));
		gc.gridx = 5;
		gc.weightx = 0.5;
		this.add(label2, gc);


		//star icon
		stars = new ArrayList<JLabel>();
		//fill images for stars
		BufferedImage istar1;
		BufferedImage istar2;
		BufferedImage istar3;
		BufferedImage istar4;
		BufferedImage istar5;
		JLabel star1;
		JLabel star2;
		JLabel star3;
		JLabel star4;
		JLabel star5;

		try{
			//star1
			istar1 = ImageIO.read(new File("./resources/unfilled.png"));
			istar1 = standardize(30, 30, istar1);
			star1 = new JLabel(new ImageIcon(istar1));
			stars.add(0, star1);
			gc.gridx = 6;
			gc.weightx = 0;
			this.add(star1, gc);
			star1.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						if (e.getClickCount() > 1) {
							System.out.println("double clicked => clear rating");
							clearStar(stars);
							icm.setRating(0);
						}else{
							refreshStar(star1, stars);
							icm.setRating(1);
						}
					}
			});
			//star2
			istar2 = ImageIO.read(new File("./resources/unfilled.png"));
			istar2 = standardize(30, 30, istar2);
			star2 = new JLabel(new ImageIcon(istar2));
			stars.add(1, star2);
			gc.gridx = 7;
			this.add(star2, gc);
			star2.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() > 1) {
						clearStar(stars);
						icm.setRating(0);
					}else{
						refreshStar(star2, stars);
						icm.setRating(2);
					}
				}
			});
			//star3
			istar3 = ImageIO.read(new File("./resources/unfilled.png"));
			istar3 = standardize(30, 30, istar3);
			star3 = new JLabel(new ImageIcon(istar3));
			stars.add(2, star3);
			gc.gridx = 8;
			this.add(star3, gc);
			star3.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() > 1) {
						clearStar(stars);
						icm.setRating(0);
					}else{
						refreshStar(star3, stars);
						icm.setRating(3);
					}
				}
			});
			//star4
			istar4 = ImageIO.read(new File("./resources/unfilled.png"));
			istar4 = standardize(30, 30, istar4);
			star4 = new JLabel(new ImageIcon(istar4));
			stars.add(3, star4);
			gc.gridx = 9;
			this.add(star4, gc);
			star4.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() > 1) {
						clearStar(stars);
						icm.setRating(0);
					}else{
						refreshStar(star4, stars);
						icm.setRating(4);
					}
				}
			});
			//star5
			istar5 = ImageIO.read(new File("./resources/unfilled.png"));
			istar5 = standardize(30, 30, istar5);
			star5 = new JLabel(new ImageIcon(istar5));
			stars.add(4, star5);
			gc.gridx = 10;
			this.add(star5, gc);
			star5.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() > 1) {
						clearStar(stars);
						icm.setRating(0);
					}else{
						refreshStar(star5, stars);
						icm.setRating(5);
					}
				}
			});
		}catch(IOException ex){
			System.out.println("Toolbar: failed to find unfilled");
		}
	}


	public void updateView(int state) {
		System.out.println("Toolbar: updateView");
	}


	protected static void refreshStar(JLabel star, ArrayList<JLabel> strs){
		try{
			int len = strs.size();
			int index = strs.indexOf(star);
			BufferedImage fstar = ImageIO.read(new File("./resources/filled.png"));
			fstar = standardize(30, 30, fstar);
			BufferedImage ustar = ImageIO.read(new File("./resources/unfilled.png"));
			ustar = standardize(30, 30, ustar);
			for(int i=0; i<len; i++){
				if(i <= index){
					strs.get(i).setIcon(new ImageIcon(fstar));
					strs.get(i).revalidate();
				}else{
					strs.get(i).setIcon(new ImageIcon(ustar));
					strs.get(i).revalidate();
				}
			}
		}catch(IOException ex){
			System.out.println("Toolbar: failed to replace with filled");
		}
	}

	protected static void clearStar(ArrayList<JLabel> strs){
		try{
			int len = strs.size();
			BufferedImage ustar = ImageIO.read(new File("./resources/unfilled.png"));
			ustar = standardize(30, 30, ustar);
			for(int i=0; i<len; i++){
					strs.get(i).setIcon(new ImageIcon(ustar));
					strs.get(i).revalidate();
			}
		}catch(IOException ex){
			System.out.println("Toolbar: failed to replace with filled");
		}
	}

	public static BufferedImage standardize( int h, int w, BufferedImage image) {
		BufferedImage reshaped = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graph = reshaped.createGraphics();
		graph.drawImage(image.getScaledInstance(w, h, Image.SCALE_SMOOTH), 0, 0, null);
		graph.dispose();
		return reshaped;
	}

	protected void upload() {
		JFileChooser jfc = new JFileChooser();
		FileFilter filter = new FileNameExtensionFilter("pick your images","gif","png", "jpg", "jpeg");
		jfc.addChoosableFileFilter(filter);
		jfc.setMultiSelectionEnabled(true);
		jfc.setFileFilter(filter);

		int returnValue = jfc.showOpenDialog(null);
		if (returnValue == jfc.APPROVE_OPTION) {
			File[] selectedFile = jfc.getSelectedFiles();
			for(File file: selectedFile){
				//double check for extension match
				String path = file.getAbsolutePath();
				File tmp = new File(path);
				String name = tmp.getName();

				int i = name.lastIndexOf('.');
				String extension = new String();
				if (i > 0) {
    			extension = name.substring(i+1);
				}
				if(extension.equals("gif")||extension.equals("png")||extension.equals("jpg")||extension.equals("jpeg")){
					System.out.println("uploading: " + path);
					try{
						BufferedImage img = ImageIO.read(file);
						img = standardize(350, 400, img);
						Object tmpTime = Files.getAttribute(file.toPath(), "basic:creationTime");
						String creation = String.valueOf(tmpTime).substring(0, 10);
						ImageModel im = new ImageModel(this.icm, path, name, creation, 0, img);
						ImageView iv = new ImageView(im, icm.getIcv());
						im.addView(iv);
						im.printInfo();
						icm.addModel(im, name);


					}catch(IOException e){
						System.out.println("Toolbar: failed to load image");
					}
				}else{
					System.out.println("extension: " + extension + " is not supported");
				}
			}
		}else{
			System.out.println("File access cancelled by user.");
		}
}
}
