import java.text.DecimalFormat;
import java.util.ArrayList;

public class Image {
    //members
    private ArrayList<ArrayList<Float>> image;
    private Integer height;
    private Integer width;

    //constructors
    public Image(Integer height,Integer width){
        ImageGenerator imageGenerator = new ImageGenerator();
        image = imageGenerator.makeAHole(imageGenerator.generateImage(height,width));
    }
    public Image(Image image_){
        height = image_.getHeight();
        width = image_.getWidth();
        image = new ArrayList<>(image_.image);
    }
    public Image(ArrayList<ArrayList<Float>> imageAsArray){
        image = new ArrayList<>(imageAsArray);
        height = imageAsArray.size();
        width = imageAsArray.get(0).size();
    }

    //member functions
    public int getHeight(){
        return height;
    }

    public int getWidth(){
        return width;
    }

    public Float getPixel(Integer rowInd, Integer colInd){
        return image.get(rowInd).get(colInd);
    }

    public void setPixel(Integer rowInd, Integer colInd, Float value){
        image.get(rowInd).set(colInd,value);
    }

    public Float getPixel(Location location){
        return getPixel(location.getRow(),location.getCol());
    }

    public void setPixel(Location location, Float value){
        setPixel(location.getRow(),location.getCol(), value);
    }

    public ArrayList getImageAsArray(){return  image;}

    //ints float vals of image to console
    public void printImageToConsule(){
        //Iterator row = image.iterator();
        DecimalFormat decimalFormat = new DecimalFormat("0.000");
        for(ArrayList<Float> row : image){
            for(Float pixel : row){
                if(pixel > 0) {
                    System.out.print(" " + decimalFormat.format(pixel) + " ");
                }
                else{
                    System.out.print(decimalFormat.format(pixel) + " ");
                }
            }
            System.out.print("\n");
        }
    }

    //returns the location of the hole's top left corner
    public static Location getHoleCorner(Image image){
        for(int i =0 ; i < image.getHeight() ; i++){
            for (int j = 0 ; j < image.getWidth() ; j++){
                if(image.getPixel(i,j) <= -1){
                    return new Location(i,j);
                }
            }
        }
        throw(new IllegalArgumentException());
    }

    //returns the pixel is in the image
    public static boolean isInImage(Location location, Image image){
        if(location.getCol() < 0 || location.getRow() < 0 ||
                location.getCol() > image.getWidth() -1 || location.getRow() > image.getHeight() -1){
            return false;
        }
        return true;
    }

    //returns the next clockwise pixel if centerLocation is the center and curLocation is around it.
    public static Location getNextClockwisePixel(Location centerLocation, Location curLocation){
        int y = curLocation.getRow() - centerLocation.getRow();
        int x = curLocation.getCol() - centerLocation.getCol();
        int newX,newY;
        if(x == -1 && y == 1) {newX = 0; newY = 1;}
        else if(x == 0 && y ==1){newX = 1; newY = 1;}
        else if(x == 1 && y ==1){newX = 1; newY = 0;}
        else if(x == 1 && y ==0){newX = 1; newY = -1;}
        else if(x == 1 && y ==-1){newX = 0; newY = -1;}
        else if(x == 0 && y ==-1){newX = -1; newY = -1;}
        else if(x == -1 && y ==-1){newX = -1; newY = 0;}
        else {newX = -1; newY = 1;}
        return new Location(centerLocation.getRow()+newY,centerLocation.getCol()+newX);
    }
}
