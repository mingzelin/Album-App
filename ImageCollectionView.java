import javax.swing.*;
import java.awt.*;
import java.lang.Math;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.event.*;

public class ImageCollectionView extends JPanel implements IView {
	//private JButton button;
	private ImageCollectionModel icm;
	private int rating;
	private int status;

	public ImageCollectionView(ImageCollectionModel icm) {
		this.status = 1;
		this.icm = icm;
		this.setBackground(Color.WHITE);
		this.setVisible(true);
		setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();

	}

	public void paintGrid(int width){
		ArrayList<ImageModel> imgModels = icm.getImgModels();
		int num = 0;
		for(ImageModel imgModel: imgModels){
			if(imgModel.getStars() >= icm.getRating()){
				num++;
			}
		}
		System.out.println("num of candidates: " + num + " on rating: " + icm.getRating());
		int colNum = (int) Math.floor((double) width /(double) 400);
		int numCol;
		if(num <= colNum){
			numCol = num;
		}else{
			numCol = colNum;
		}

		int numRow = (int) Math.ceil((double) num /(double) colNum);

		System.out.println("The image collection has: ");
		System.out.println(width+" width");

		System.out.println(numCol+" cols");
		System.out.println(numRow+" rows");

		int xcount = 0;
		int ycount = 0;

		GridBagConstraints gc = new GridBagConstraints();

		JFrame frame = new JFrame();
		JPanel p = new JPanel();
		frame.setVisible(false);

		for(ImageModel imgModel: imgModels){
			if(imgModel.getStars() >= icm.getRating()){
				ImageView iv = imgModel.getView();
				if(xcount==colNum){
					xcount=0;
					ycount++;
				}
				gc.gridx = xcount;
				gc.gridy = ycount;
				gc.weightx = 3;
				gc.weighty = 10;

				add(iv, gc);

				GridBagConstraints gcc = new GridBagConstraints();
				//name
				JLabel nlabel = new JLabel();
				nlabel.setText(imgModel.getName());
				nlabel.setFont(new Font("Arial", 0, 18));
				gcc.weightx = 1;
				gcc.gridx = 0;
				gcc.gridy = 1;
				iv.add(nlabel, gcc);
				//creation date
				JLabel clabel = new JLabel();
				clabel.setText(imgModel.getCreation());
				clabel.setFont(new Font("Arial", 0, 18));
				gcc.weightx = 1;
				gcc.gridx = 0;
				gcc.gridy = 2;
				iv.add(clabel, gcc);
				//stars
				ArrayList<JLabel> stars = putStar(imgModel);
				JPanel pane = new JPanel();
				pane.setLayout(new GridBagLayout());
				int c = 0;
				for(JLabel s: stars){
					gcc.weightx = 0;
					gcc.gridx = c;
					gcc.gridy = 1;
					pane.add(s, gcc);
					//iv.add(s, gcc);
					c++;
				}
				gcc.weightx = 1;
				gcc.gridx = 0;
				gcc.gridy = 3;
				iv.add(pane, gcc);
			  xcount++;
			}
		}

		revalidate();
		repaint();
	}

	public void paintList(int width){
		ArrayList<ImageModel> imgModels = icm.getImgModels();
		int num = 0;
		for(ImageModel imgModel: imgModels){
			if(imgModel.getStars() >= icm.getRating()){
				num++;
			}
		}
		System.out.println("num of candidates: " + num);
		GridBagConstraints gc = new GridBagConstraints();

		int ycount = 0;

		for(ImageModel imgModel: imgModels){
			if(imgModel.getStars() >= icm.getRating()){
				ImageView iv = imgModel.getView();
				gc.gridx = 0;
				gc.gridy = ycount;
				gc.weightx = 3;
				gc.weighty = 10;
				add(iv, gc);
				ycount++;

				GridBagConstraints gcc = new GridBagConstraints();
				//name
				JLabel nlabel = new JLabel();
				nlabel.setText(imgModel.getName());
				nlabel.setFont(new Font("Arial", 0, 18));
				gcc.weightx = 1;
				gcc.gridx = 0;
				gcc.gridy = 1;
				iv.add(nlabel, gcc);
				//creation date
				JLabel clabel = new JLabel();
				clabel.setText(imgModel.getCreation());
				clabel.setFont(new Font("Arial", 0, 18));
				gcc.weightx = 1;
				gcc.gridx = 0;
				gcc.gridy = 2;
				iv.add(clabel, gcc);
				//stars
				JPanel pane = new JPanel();
				pane.setLayout(new GridBagLayout());
				ArrayList<JLabel> stars = putStar(imgModel);
				int c = 1;
				for(JLabel s: stars){
					gcc.weightx = 0;
					gcc.gridx = c;
					gcc.gridy = 1;
					pane.add(s, gcc);
					c++;
				}
				gcc.weightx = 1;
				gcc.gridx = 0;
				gcc.gridy = 3;
				iv.add(pane, gcc);
			}
		}
		revalidate();
		repaint();

	}

	public void setRating(int num){
		rating = num;
	}

	public int getRating(int num){
		return rating;
	}

	public ArrayList<JLabel> putStar(ImageModel im){
		//star icon
		ArrayList<JLabel> stars = new ArrayList<JLabel>();
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
			//TODO BUG though not shown due to limited repaint
			//when refreshed yellow stars are not coloured
			//star1

			if(im.getStars() > 0){
				istar1 = ImageIO.read(new File("./resources/filled.png"));
			}else{
				istar1 = ImageIO.read(new File("./resources/unfilled.png"));
			}

			istar1 = Toolbar.standardize(30, 30, istar1);
			star1 = new JLabel(new ImageIcon(istar1));
			stars.add(0, star1);
			star1.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						ArrayList<ImageModel> imgModels = icm.getImgModels();
						System.out.println("testing hit-----------------------------------");
						if (e.getClickCount() > 1) {
							System.out.println("double clicked => clear rating");
							im.setStars(0);
							for(ImageModel i: imgModels){
								if(im.getName().equals(i.getName())){
									i.setStars(0);
									break;
								}
							}
							Toolbar.clearStar(stars);
								//updateView(status);
						}else{
							im.setStars(1);
							for(ImageModel i: imgModels){
								if(im.getName().equals(i.getName())){
									i.setStars(1);
									break;
								}
							}
							Toolbar.refreshStar(star1, stars);
								//updateView(status);
						}
						//TODO
						icm.exit();
						icm.removeAll();
						icm.enter();
					}
			});
			//star2
			if(im.getStars() > 1){
				istar2 = ImageIO.read(new File("./resources/filled.png"));
			}else{
				istar2 = ImageIO.read(new File("./resources/unfilled.png"));
			}
			istar2 = Toolbar.standardize(30, 30, istar2);
			star2 = new JLabel(new ImageIcon(istar2));
			stars.add(1, star2);
			star2.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					ArrayList<ImageModel> imgModels = icm.getImgModels();
					System.out.println("testing hit-----------------------------------");
					if (e.getClickCount() > 1) {
						System.out.println("double clicked => clear rating");
						im.setStars(0);
						for(ImageModel i: imgModels){
							if(im.getName().equals(i.getName())){
								i.setStars(0);
								break;
							}
						}
						Toolbar.clearStar(stars);
							//updateView(status);
					}else{
						im.setStars(2);
						for(ImageModel i: imgModels){
							if(im.getName().equals(i.getName())){
								i.setStars(2);
								break;
							}
						}
						Toolbar.refreshStar(star2, stars);
							//updateView(status);
					}
					//TODO`
					icm.exit();
					icm.removeAll();
					icm.enter();
				}
			});
			//star3
			if(im.getStars() > 2){
				istar3 = ImageIO.read(new File("./resources/filled.png"));
			}else{
				istar3 = ImageIO.read(new File("./resources/unfilled.png"));
			}
			istar3 = Toolbar.standardize(30, 30, istar3);
			star3 = new JLabel(new ImageIcon(istar3));
			stars.add(2, star3);
			star3.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					ArrayList<ImageModel> imgModels = icm.getImgModels();
					System.out.println("testing hit-----------------------------------");

					if (e.getClickCount() > 1) {
						System.out.println("double clicked => clear rating");
						im.setStars(0);
						for(ImageModel i: imgModels){
							if(im.getName().equals(i.getName())){
								i.setStars(0);
								break;
							}
						}
						Toolbar.clearStar(stars);
							//updateView(status);
					}else{
						im.setStars(3);
						for(ImageModel i: imgModels){
							if(im.getName().equals(i.getName())){
								i.setStars(3);
								break;
							}
						}
						Toolbar.refreshStar(star3, stars);
							//updateView(status);
					}
					//TODO
			//		System.out.println("testing hit-----------------------------------");
					icm.exit();
					icm.removeAll();
					icm.enter();
				}
			});
			//star4
			if(im.getStars() > 3){
				istar4 = ImageIO.read(new File("./resources/filled.png"));
			}else{
				istar4 = ImageIO.read(new File("./resources/unfilled.png"));
			}
			istar4 = Toolbar.standardize(30, 30, istar4);
			star4 = new JLabel(new ImageIcon(istar4));
			stars.add(3, star4);
			star4.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					ArrayList<ImageModel> imgModels = icm.getImgModels();
					System.out.println("testing hit-----------------------------------");

					if (e.getClickCount() > 1) {
						System.out.println("double clicked => clear rating");
						im.setStars(0);
						for(ImageModel i: imgModels){
							if(im.getName().equals(i.getName())){
								i.setStars(0);
								break;
							}
						}
						Toolbar.clearStar(stars);
							//updateView(status);
					}else{
						im.setStars(4);
						for(ImageModel i: imgModels){
							if(im.getName().equals(i.getName())){
								i.setStars(4);
								break;
							}
						}
						Toolbar.refreshStar(star4, stars);
							//updateView(status);
					}
					//TODO
			//		System.out.println("testing hit-----------------------------------");
					icm.exit();
					icm.removeAll();
					icm.enter();
				}
			});
			//star5
			if(im.getStars() > 4){
				istar5 = ImageIO.read(new File("./resources/filled.png"));
			}else{
				istar5 = ImageIO.read(new File("./resources/unfilled.png"));
			}
			istar5 = Toolbar.standardize(30, 30, istar5);
			star5 = new JLabel(new ImageIcon(istar5));
			stars.add(4, star5);
			star5.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					ArrayList<ImageModel> imgModels = icm.getImgModels();
					System.out.println("testing hit-----------------------------------");

					if (e.getClickCount() > 1) {
						System.out.println("double clicked => clear rating");
						im.setStars(0);
						for(ImageModel i: imgModels){
							if(im.getName().equals(i.getName())){
								i.setStars(0);
								break;
							}
						}
						Toolbar.clearStar(stars);
							//updateView(status);
					}else{
						im.setStars(5);
						for(ImageModel i: imgModels){
							if(im.getName().equals(i.getName())){
								i.setStars(5);
								break;
							}
						}
						Toolbar.refreshStar(star5, stars);
							//updateView(status);
					}
					//TODO
				//	System.out.println("testing hit-----------------------------------");
					icm.exit();
					icm.removeAll();
					icm.enter();
				}
			});
		}catch(IOException ex){
			System.out.println("ImageCollectionView: failed to find unfilled");
		}
		return stars;
	}

	public void refresh() {
		this.removeAll();
		if(status == 1){
			System.out.println("ImageCollectionView: updateView Grid");
			paintGrid(this.getSize().width);
		}else if(status == 2){
			System.out.println("ImageCollectionView: updateView List");
			paintList(this.getSize().width);
		}else{
			System.out.println("unexpected value");
		}
	}

	public void updateView(int state) {
		status = state;
		this.removeAll();
		if(state == 1){
			System.out.println("ImageCollectionView: updateView Grid");
			paintGrid(this.getSize().width);
		}else if(state == 2){
			System.out.println("ImageCollectionView: updateView List");
			paintList(this.getSize().width);
		}else{
			System.out.println("unexpected value");
		}
	}



}
