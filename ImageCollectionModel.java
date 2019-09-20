import java.util.ArrayList;
import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class ImageCollectionModel {
	private int state;
	//start = 0, grid = 1, list = 2
	private int width;
	private int rating;
	private ImageCollectionView icv;
	private ArrayList<ImageModel> imgModels;

	public ImageCollectionModel(){
		state = 2;//TODO
		imgModels = new ArrayList<ImageModel>();
	}

	public ImageCollectionView getIcv(){
		return icv;
	}
	public void addModel(ImageModel model, String name) {
		for(ImageModel im: imgModels){
			if(im.getName().equals(name)){
					System.out.println("File: " + name + " already exist");
					return;
			}
		}
		imgModels.add(model);
		System.out.println("File: " + name + " is added");
		notifyObservers();
	}

	public void removeAll(){
		int size = imgModels.size();
		for(int i=0; i<size; i++){
			imgModels.remove(0);
		}
	}

	public void exit(){
		System.out.println("exit special execution-----------------------------------");
		try{
			BufferedWriter writer=new BufferedWriter(new FileWriter("info"));
			for(ImageModel im: imgModels){
				writer.write(im.getPath());
				writer.newLine();
				writer.write("" + im.getName());
				writer.newLine();
				writer.write("" + im.getCreation());
				writer.newLine();
				writer.write("" + im.getStars());
				writer.newLine();
			}
			writer.flush();
			writer.close();
		}catch(IOException e){
			System.out.println("exit file saving failed");
		}
	}

	public void enter(){
		System.out.println("enter special execution-----------------------------------");
		ArrayList<ImageModel> imgModels = new ArrayList<ImageModel>();
		try{
			String f = "./info";
			File file = new File(f);
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String path;
			String name;
			String creation;
			String s;
			int stars;
			while((path = br.readLine()) != null){
    		//process the line
				name = br.readLine();
				creation = br.readLine();
				s = br.readLine();
				stars = Integer.valueOf(s);
				System.out.println("name " + name);
				System.out.println("creation " + creation);
				System.out.println("stars " + stars);
				System.out.println("path " + path);
				BufferedImage img = ImageIO.read(new File(path));
				img = Toolbar.standardize(350, 400, img);
				ImageModel im = new ImageModel(this, path, name, creation, stars, img);
				ImageView iv = new ImageView(im, this.getIcv());
				im.addView(iv);
				this.addModel(im, name);
			}
		}catch(IOException e){
			System.out.println("exit file saving failed");
		}
	}

	public void changeState(int num){
		if(state != num){
			state = num;
			notifyObservers();
		}
	}


	public int getRating(){
		return rating;
	}

	public void setRating(int num){
		rating = num;
		exit();
		removeAll();
		enter();
		notifyObservers();
	}

	public ArrayList<ImageModel> getImgModels(){
		return imgModels;
	}

	public void addView(ImageCollectionView icv) {
		this.icv = icv;
	}


	private void notifyObservers() {
		icv.updateView(state);

	}
}
