import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.awt.Dimension;

public class ImageModel {
	private ImageCollectionModel icm;
	private String path;
	private String name;
	private String creation;
	private int stars;
	private BufferedImage img;
	private ImageView iv;

	public ImageModel(ImageCollectionModel icm, String p, String n, String c, int stars, BufferedImage img){
		this.icm = icm;
		path = p;
		name = n;
		creation = c;
		this.stars = stars;
		this.img = img;
	}


	public void addView(ImageView iv){
		this.iv = iv;
	}

	public ImageView getView(){
		return iv;
	}

	public String getPath(){
		return path;
	}

	public void setPath(String p){
		path = p;
	}

	public String getName(){
		return name;
	}

	public void setName(String n){
		name = n;
	}

	public String getCreation(){
		return creation;
	}

	public void setCreation(String c){
		 creation = c;
	}

	public int getStars(){
		return stars;
	}

	public void setStars(int num){
		stars = num;
	}



	public BufferedImage getImg(){
		return img;
	}

	public void printInfo(){
		System.out.println("path: " + path);
		System.out.println("name: " + name);
		System.out.println("creation: " + creation);
		System.out.println("stars: " + stars);
	}

}
