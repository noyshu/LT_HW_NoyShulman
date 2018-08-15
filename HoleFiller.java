import java.util.ArrayList;

public class HoleFiller {
    //members
    private WeightFunction weightFunction;

    private final static float HOLE = -1.0f;
    private final static float VISITED = -5.0f;

    //constructors
    public  HoleFiller(WeightFunction weightFunction_){
        weightFunction = weightFunction_;
    }

    //member fuctions

    //this function returns the image with the hole filled according to the exact solution
    public Image fillHoleExact(Image image_){
        Image image = new Image(image_);
        ArrayList<Location> hole = getHolePixelsLocation(image);
        ArrayList<Location> boundry = (new Boundry(image)).getBoundryLocations();
        for(Location holePixel : hole){
            image.setPixel(holePixel,calcPixelValExact(image,holePixel,boundry));
        }
        return image;
    }

    //this function returns the image with the hole filled according to the aproximate solution
    public Image fillHoleAprox(Image image_, int numOfBorderPixels){
        Image image = new Image(image_);
        ArrayList<Location> hole = getHolePixelsLocation(image);
        ArrayList<Location> boundry = (new Boundry(image)).getNBorderPixels(numOfBorderPixels);
        ArrayList<Location> aproxBoundryLocations = new ArrayList<>();
        ArrayList<Float> aproxBoundryVals = new ArrayList<>();

        int stepSize = boundry.size()/numOfBorderPixels;
        float sum;
        for (int i = 0; i < boundry.size() ; i+=stepSize) {
            sum = 0;
            int j;
            for (j = 0; j < stepSize ; j++) {
                if(((i+ j) >= boundry.size())) {break;}
                    sum += image.getPixel(boundry.get(i + j));
            }
            
            aproxBoundryLocations.add(boundry.get(i + j/2));
            aproxBoundryVals.add(sum/stepSize);
        }
        for(Location holePixel : hole){
            image.setPixel(holePixel,calcPixelValAprox(image,holePixel,aproxBoundryLocations,aproxBoundryVals));
        }
        return image;
    }

    //returns an array of hole pixel locations
    public ArrayList<Location> getHolePixelsLocation(Image image_){
        Image image = new Image(image_);
        Location curPixel = Image.getHoleCorner(image);
        ArrayList<Location> locations = new ArrayList();
        locations.add(curPixel); //TODO maybe remove
        //image.setPixel(curPixel,PREVIOUS);
        Location lastVisitedPixel = new Location(curPixel.getRow(),curPixel.getCol() - 1);
        Location nextPixelCandidate = Image.getNextClockwisePixel(curPixel,lastVisitedPixel);
        Location firstVisitedPixelThisIteration;
        int i =0;

        while(!nextPixelCandidate.equals(lastVisitedPixel)){
            image.setPixel(curPixel,VISITED);
            locations.add(curPixel);
            //image.printImageToConsule();
            firstVisitedPixelThisIteration = nextPixelCandidate == curPixel ? lastVisitedPixel : nextPixelCandidate;
            while(image.getPixel(nextPixelCandidate) != HOLE){
                lastVisitedPixel = nextPixelCandidate == curPixel ? lastVisitedPixel : nextPixelCandidate;
                nextPixelCandidate= Image.getNextClockwisePixel(curPixel,lastVisitedPixel);
                if(nextPixelCandidate.equals(firstVisitedPixelThisIteration)){
                    return locations;
                }
            }
            curPixel = nextPixelCandidate;
        }
        return locations;
    }

    //return the exact value of a specific hole pixel
    private Float calcPixelValExact(Image image, Location pixel, ArrayList<Location> boundry){
        Float retVal = new Float(0);
        Float denomanator = new Float(0);
        for(Location boundryPixel : boundry){
            retVal += image.getPixel(boundryPixel)*weightFunction.weight(pixel,boundryPixel);
            denomanator += weightFunction.weight(pixel,boundryPixel);
        }
        return retVal/denomanator;
    }

    //return the aproximate value of a specific hole pixel
    private Float calcPixelValAprox(Image image, Location pixel, ArrayList<Location> boundry,ArrayList<Float> averageBoundryVals){
        Float retVal = new Float(0);
        Float denomanator = new Float(0);
        int i =0;
        for(Location boundryPixel : boundry){
            retVal += averageBoundryVals.get(i)*weightFunction.weight(pixel,boundryPixel);
            denomanator += weightFunction.weight(pixel,boundryPixel);
            i++;
        }
        return retVal/denomanator;
    }


}
