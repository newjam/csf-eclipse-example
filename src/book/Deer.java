package book;
import java.awt.Color;
import java.util.Random;

/**
 * Class that represents a deer.  The deer class
 * tracks all living deer with a linked list.
 * 
 * @author Barb Ericson ericson@cc.gatech.edu
 */
public class Deer extends Turtle {
  
  /////////////// fields //////////////////////
  
  /** class constant for the color */
  public static final Color BROWN = new Color(116,64,35);
  
  /** class constant for probability of NOT turning */
  public static final double PROB_OF_STAY = 1.0/5;
  
  /** class constant for how far deer can smell */
  public static final double SMELL_RANGE = 50;
  
  /** class constant for top speed (max num steps can move in a timestep) */
  public static final int MAX_SPEED = 30;
  
  /** random number generator */
  protected static Random randNumGen = new Random();
  
  /** the simulation I'm in */
  private WolfDeerSimulation mySim;
  
  ////////////////////////////// Constructors ////////////////////////
  
  /**
   * Constructor that takes the model display (the original
   * position will be randomally assigned
   * @param modelDisplayer thing which will display the model
   * @param thisSim the simulation
   */
  public Deer (ModelDisplay modelDisplayer,
               WolfDeerSimulation thisSim) {
    super(randNumGen.nextInt(modelDisplayer.getWidth()),
          randNumGen.nextInt(modelDisplayer.getHeight()),
          modelDisplayer);
    init(thisSim);
  }
  
  /** Constructor that takes the x and y and a model
    * display to draw it on
    * @param x the starting x position
    * @param y the starting y position
    * @param modelDisplayer the thing that displays the model
    * @param thisSim the simulation
    */
  public Deer (int x, int y, ModelDisplay modelDisplayer,
               WolfDeerSimulation thisSim) {
    // let the parent constructor handle it
    super(x,y,modelDisplayer);
    init(thisSim);
  }
  
  ////////////////// methods ////////////////////////////////////////
  
  /**
   * Method to initialize the new deer object
   * @param thisSim the simulation to use
   */
  public void init(WolfDeerSimulation thisSim) {
    
    // set the color of this deer
    setColor(BROWN);
    
    // turn some random direction
    this.turn(randNumGen.nextInt(360));
    
    // know my simulation
    mySim = thisSim;
    
  }
  
  /**
   * Method to get the closest Wolf within the passed distance
   * to this Deer. We'll search the input list of the kind
   * of objects to compare to.
   * @param distance the distance to look within
   * @param list the list of agents to look at
   * @return the closest agent in the given distance or null
   */
  public AgentNode getClosest(double distance,
                              AgentNode list) {
    // get the head of the deer linked list
    AgentNode head = list;
    AgentNode curr = head;
    AgentNode closest = null;
    Wolf thisWolf;
    double closestDistance = 999;
    double currDistance = 0;
    
    // loop through the linked list looking for the closest wolf
    while (curr != null) {
      thisWolf = (Wolf) curr.getAgent();
      currDistance = thisWolf.getDistance(this.getXPos(),
                                          this.getYPos());
      if (currDistance < distance) {
        if (closest == null || 
            currDistance < closestDistance) {
          closest = curr;
          closestDistance = currDistance;
        }
      }
      curr = (AgentNode) curr.getNext();
    }
    return closest;
  }
  
  /**
   * Method to act during a time step 
   * pick a random direction and move some random amount up to top speed
   */
  public void act() {
    if (randNumGen.nextFloat() > PROB_OF_STAY) {
      this.turn(randNumGen.nextInt(360));
    }
    
    // go forward some random amount 
    forward(randNumGen.nextInt(MAX_SPEED));
    
  }
  
  
  /**
   * Method that handles when a deer dies
   */
  public void die() {
    
    // Leave a mark on the world where I died...
    this.setBodyColor(Color.RED); 
    
    // Remove me from the "live" list
    mySim.getDeer().remove(this);
    
    // say that the deer died
    System.out.println("<SIGH!> A deer died...");
  }
  
}
