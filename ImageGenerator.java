import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.awt.*;

public class ImageGenerator{
    private int height;
    private int width;

    //generate a random pixels image
    public ArrayList generateImage(int width, int height){
        ArrayList image = new ArrayList<ArrayList<Float>>();
        Random randomGenerator = new Random();
        for(int i =0 ; i < height ; i++){
            ArrayList row = new ArrayList<Float>();
            for (int j =0 ; j < width ; j++){
                row.add(randomGenerator.nextFloat());
            }
            image.add(row);
        }
        return image;
    }

    // load an image from file, make it greyscale and store it to an array
    public ArrayList loadImageToArray(String fileName){
        BufferedImage colorImage = null;
        try{
            colorImage = ImageIO.read(new File((fileName + ".jpg")));
        }catch (IOException e){
            e.printStackTrace();
        }
        height = colorImage.getHeight();
        width = colorImage.getWidth();
        ArrayList imageAsArray = new ArrayList<ArrayList<Float>>();

        for(int i = 0 ; i < height; i ++){
            ArrayList row = new ArrayList<Integer>();
            for(int j = 0 ; j < width ; j ++){
                Color c = new Color(colorImage.getRGB(j, i)).brighter();
                float red = (float) (c.getRed() * 0.299);
                float green = (float) (c.getGreen() * 0.587);
                float blue = (float) (c.getBlue() * 0.114);
                row.add((red + green + blue)/256.0f);
            }
            imageAsArray.add(row);
        }
        return imageAsArray;
    }

    //this function converts an array back to image and saves a file named newFileName.jpg with the image
    public BufferedImage arrayToBufferdImageAndFile(ArrayList<ArrayList<Float>> imageAsArray,String newFileName){
        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_BYTE_GRAY);
        for(int i = 0 ; i < height; i ++){
            ArrayList row = new ArrayList<Integer>();
            for(int j = 0 ; j < width ; j ++){
                Integer intColor = (int)((imageAsArray.get(i).get(j))*256);
                intColor = intColor >= 0 ? intColor : 255;
                Color newColor = new Color(intColor,intColor,intColor);
                image.setRGB(j,i,newColor.getRGB());
            }
        }
        File newImage = new File(newFileName + ".jpg");
        try {
            ImageIO.write(image,"jpg",newImage);
        }catch (IOException e){
            e.printStackTrace();
        }
        return image;
    }

    //make a rectengular hole in the image
    public ArrayList makeAHole(ArrayList<ArrayList<Float>> image_){ //only rectengles now
        ArrayList<ArrayList<Float>> image = new ArrayList<>(image_);
        Random rand = new Random();
        Integer holeStartRow =  height/2;//rand.nextInt(width - 40);
        Integer holeStartCol = 70 + width/2; // + rand.nextInt(height- 40);
        Integer holeRowSize =  30;//rand.nextInt(20);
        Integer holeColSize = 30;//rand.nextInt(20);
        holeRowSize = holeRowSize > 2 ? holeRowSize : 3;
        holeColSize = holeColSize > 2 ? holeColSize : 3;

        ArrayList newRow;//;mage.get(holeStartRow);
        for(int i = 0 ; i <  holeRowSize ; i++){
            newRow = image.get(holeStartRow + i);
            for(int j = 0 ; j < holeColSize ; j++){
                newRow.set(holeStartCol + j,new Float(-1));
            }
            image.set(holeStartRow + i,newRow);
        }
        return image;
    }

}
