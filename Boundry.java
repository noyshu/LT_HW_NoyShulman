import java.util.ArrayList;

public final class Boundry {
    //members
    private ArrayList<Location> boundryLocations;

    //puts the locations of the hole's boundry to the boundry array.
    public Boundry(Image image){
        boundryLocations = new ArrayList<Location>();
        Location corner = Image.getHoleCorner(image);
        Location prevBoundaryPixel = new Location(corner.getRow()-1,corner.getCol()-1);
        Location curBoundaryPixel = new Location(corner.getRow()-1, corner.getCol());
        Location firstBoundryPixel = curBoundaryPixel;
        Location temp;
        //boundryLocations.add(prevBoundaryPixel);
        do{
            boundryLocations.add(curBoundaryPixel);
            //image.setPixel(curBoundaryPixel,new Float(-3));//Todo remove
            //System.out.println("");
            //image.printImageToConsule();
            temp = curBoundaryPixel;
            curBoundaryPixel = getNextBorderPixel(prevBoundaryPixel,curBoundaryPixel,image);
            prevBoundaryPixel = temp;
        }while(!curBoundaryPixel.equals(firstBoundryPixel));
    }

    //returns the array
    public ArrayList<Location> getBoundryLocations(){
        return boundryLocations;
    }

    //returns n border pixels distributed evenly
    public ArrayList<Location> getNBorderPixels(int n){
        int originalSize = boundryLocations.size();
        if(originalSize <= n){return boundryLocations;}
        ArrayList<Location>  nBorder = new ArrayList<>();
        int stepSize = originalSize/n;
        for(int i =0 ; i < originalSize ; i++){
            nBorder.add(boundryLocations.get(i));
        }
        return nBorder;
    }

    //helper function to return the next border pixel location.
    private Location getNextBorderPixel(Location prevPixel, Location curPixel, Image image){
        Location borderCandidate = Image.getNextClockwisePixel(curPixel,prevPixel);
        Location previousCandidate = null;
        while (!Image.isInImage(borderCandidate,image) || !(image.getPixel(borderCandidate) <= -1)){
            previousCandidate = borderCandidate;
            borderCandidate = Image.getNextClockwisePixel(curPixel,previousCandidate);
        }
        if(previousCandidate == null) throw new IllegalStateException();
        return previousCandidate;
    }

}
