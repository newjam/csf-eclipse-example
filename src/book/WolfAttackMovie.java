package book;
/**
 * Class to make a wolf attack movie
 * @author Mark Guzdial
 * @author Barb Ericson
 */
public class WolfAttackMovie {
  
  /** The root of the scene data structure */
  private Branch sceneRoot;
  
  /** FrameSequencer where the animation is created */
  private FrameSequencer frames;
  
  /** The nodes we need to track between methods */
  private MoveBranch wolfEntry, wolfRetreat, hero;
  
  /**
   * Constructor that takes no arguments and
   * sets up the movie
   */
  public WolfAttackMovie() {
    setUp();
  }
  
  /**
   * Set up all the pieces of the tree.
   */
  private void setUp(){
    Picture wolf = 
      new Picture(FileChooser.getMediaPath("dog-blue.jpg"));
    Picture house = 
      new Picture(FileChooser.getMediaPath("house-blue.jpg"));
    Picture tree = 
      new Picture(FileChooser.getMediaPath("tree-blue.jpg"));
    Picture monster = 
      new Picture(FileChooser.getMediaPath("mscary.jpg"));

    //Make the forest
    MoveBranch forest = new MoveBranch(10,400); // at bottom 
    HBranch trees = new HBranch(50); // 50 pixels between
    BlueScreenNode treenode;
    for (int i=0; i < 8; i++) { // insert 8 trees
      treenode = new BlueScreenNode(tree.scale(0.5));
      trees.addChild(treenode);
    }
    forest.addChild(trees);
    
    // Make the cluster of attacking "wolves"
    wolfEntry = new MoveBranch(10,50); // starting position
    VBranch wolves = new VBranch(20); // space out by 20 pixels 
    BlueScreenNode wolf1 = new BlueScreenNode(wolf.scale(0.5));
    BlueScreenNode wolf2 = new BlueScreenNode(wolf.scale(0.5));
    BlueScreenNode wolf3 = new BlueScreenNode(wolf.scale(0.5));
    wolves.addChild(wolf1);
    wolves.addChild(wolf2);
    wolves.addChild(wolf3);
    wolfEntry.addChild(wolves);
    
    // Make the cluster of retreating "wolves"
    wolfRetreat = new MoveBranch(400,50); // starting position
    wolves = new VBranch(20); // space them out by 20 pixels 
    wolf1 = new BlueScreenNode(wolf.scale(0.5).flip());
    wolf2 = new BlueScreenNode(wolf.scale(0.5).flip());
    wolf3 = new BlueScreenNode(wolf.scale(0.5).flip());
    wolves.addChild(wolf1);
    wolves.addChild(wolf2);
    wolves.addChild(wolf3);
    wolfRetreat.addChild(wolves);
    
    // Make the village
    MoveBranch village = new MoveBranch(300,450); // on bottom
    HBranch hhouses = new HBranch(40); // 40 pixels apart 
    BlueScreenNode house1 = new BlueScreenNode(house.scale(0.25));
    BlueScreenNode house2 = new BlueScreenNode(house.scale(0.25));
    BlueScreenNode house3 = new BlueScreenNode(house.scale(0.25));
    VBranch vhouses = new VBranch(-50); // move UP 50 pixels
    BlueScreenNode house4 = new BlueScreenNode(house.scale(0.25));
    BlueScreenNode house5 = new BlueScreenNode(house.scale(0.25));
    BlueScreenNode house6 = new BlueScreenNode(house.scale(0.25));
    vhouses.addChild(house4); 
    vhouses.addChild(house5); 
    vhouses.addChild(house6);
    hhouses.addChild(house1); 
    hhouses.addChild(house2); 
    hhouses.addChild(house3);
    hhouses.addChild(vhouses); // a VBranch can be a child 
    village.addChild(hhouses);
    
    // Make the monster
    hero = new MoveBranch(400,300);
    BlueScreenNode heronode = 
      new BlueScreenNode(monster.scale(0.75));
    hero.addChild(heronode);
    
    //Assemble the base scene
    sceneRoot = new Branch();
    sceneRoot.addChild(forest);
    sceneRoot.addChild(village);
    sceneRoot.addChild(wolfEntry);
  }
  
  /**
   * Method to get the scene root
   * @return the sceneRoot
   */
  public Branch getSceneRoot() { return sceneRoot;}

  /**
   * Render just the first scene
   */
  public void renderScene() {
    Picture bg = new Picture(500,500);
    sceneRoot.drawOn(bg);
    bg.show();
  }
  
  /**
   * Render the whole animation
   */
  public void renderAnimation() {
    frames = new FrameSequencer("C:/Temp/");
    frames.show();
    Picture bg;
    
    // First, the nasty wolvies come closer to the poor village
    // Cue the scary music
    for (int i=0; i<25; i++) {

      // Render the frame
      bg = new Picture(500,500);
      sceneRoot.drawOn(bg);
      frames.addFrame(bg);
      
      // Tweak the data structure
      wolfEntry.moveTo(wolfEntry.getXPos()+5,
                       wolfEntry.getYPos()+10);
    }
    
    // Now, our hero arrives!
    this.getSceneRoot().addChild(hero);
    
    // Render the frame
    bg = new Picture(500,500);
    sceneRoot.drawOn(bg);
    frames.addFrame(bg);
    
    // Remove the wolves entering, and insert the wolves 
    // retreating
    Branch root = this.getSceneRoot();
    root.getFirstChild().remove(wolfEntry);
    root.addChild(wolfRetreat);
    
    // Make sure that they retreat from the same place 
    wolfRetreat.moveTo(wolfEntry.getXPos(),wolfEntry.getYPos());
    
    // Render the frame
    bg = new Picture(500,500);
    root.drawOn(bg);
    frames.addFrame(bg);
    
    // Now, the cowardly wolves hightail it out of there!
    // Cue the triumphant music
    for (int i=0; i<10; i++) {

      // Render the frame
      bg = new Picture(500,500);
      root.drawOn(bg);
      frames.addFrame(bg);
      
      // Tweak the data structure
      wolfRetreat.moveTo(wolfRetreat.getXPos()-10,
                         wolfRetreat.getYPos()-20);
    }
  }
  
  /**
   * Replay the animation
   */
  public void replay() {
    // Probably about 5 frames per second will work
    frames.replay(200);
  }
  
  /** Main method for testing */
  public static void main (String[] args) {
    WolfAttackMovie movie = new WolfAttackMovie();
    movie.renderAnimation();
  }
}
