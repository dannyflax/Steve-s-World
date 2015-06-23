package flaxapps;

import static java.awt.event.KeyEvent.VK_A;
import static java.awt.event.KeyEvent.VK_B;
import static java.awt.event.KeyEvent.VK_C;
import static java.awt.event.KeyEvent.VK_CONTROL;
import static java.awt.event.KeyEvent.VK_D;
import static java.awt.event.KeyEvent.VK_DOWN;
import static java.awt.event.KeyEvent.VK_E;
import static java.awt.event.KeyEvent.VK_ESCAPE;
import static java.awt.event.KeyEvent.VK_F;
import static java.awt.event.KeyEvent.VK_F1;
import static java.awt.event.KeyEvent.VK_G;
import static java.awt.event.KeyEvent.VK_LEFT;
import static java.awt.event.KeyEvent.VK_P;
import static java.awt.event.KeyEvent.VK_Q;
import static java.awt.event.KeyEvent.VK_R;
import static java.awt.event.KeyEvent.VK_RIGHT;
import static java.awt.event.KeyEvent.VK_S;
import static java.awt.event.KeyEvent.VK_SHIFT;
import static java.awt.event.KeyEvent.VK_SPACE;
import static java.awt.event.KeyEvent.VK_T;
import static java.awt.event.KeyEvent.VK_U;
import static java.awt.event.KeyEvent.VK_UP;
import static java.awt.event.KeyEvent.VK_V;
import static java.awt.event.KeyEvent.VK_W;
import static java.awt.event.KeyEvent.VK_X;
import static java.awt.event.KeyEvent.VK_Y;
import static java.awt.event.KeyEvent.VK_Z;
import static javax.media.opengl.GL.GL_COLOR_BUFFER_BIT;
import static javax.media.opengl.GL.GL_DEPTH_BUFFER_BIT;
import static javax.media.opengl.GL.GL_DEPTH_TEST;
import static javax.media.opengl.GL.GL_LEQUAL;
import static javax.media.opengl.GL.GL_NICEST;
import static javax.media.opengl.GL2ES1.GL_PERSPECTIVE_CORRECTION_HINT;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_AMBIENT;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_DIFFUSE;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_LIGHT1;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_POSITION;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_SMOOTH;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_SPECULAR;
import static javax.media.opengl.fixedfunc.GLMatrixFunc.GL_MODELVIEW;
import static javax.media.opengl.fixedfunc.GLMatrixFunc.GL_PROJECTION;

import java.awt.AWTException;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.color.ColorSpace;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLException;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.GLU;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;

import flaxapps.jogl_util.AnimationHolder;
import flaxapps.jogl_util.ModelControl;
import flaxapps.jogl_util.Shader_Manager;
import flaxapps.jogl_util.Vertex;


class indexedPoint{
	double x, y, z;
	int index;
}

/*
 *Controls:  
 */


public class SteveWorld implements GLEventListener, KeyListener, MouseListener {

	
	ArrayList<ArrayList<ArrayList<indexedPoint>>> master = new ArrayList<ArrayList<ArrayList<indexedPoint>>>();
	
	
	
	
	boolean canPlaceGP = false;
	final int MAX_SIZE = 20;
	float xbuffer = 0;
	float zbuffer = 0;
	float xpos;
	float zpos;
	float saveY = 0;
	double zrangle = 0;
	
	private static String TITLE = "Steve's World";
	private static final int CANVAS_WIDTH = 640; // width of the drawable
	private static final int CANVAS_HEIGHT = 480; // height of the drawable
	private static final int FPS = 15; // animator's target frames per second
	final static JFrame frame = new JFrame();
	boolean still = true;
	ModelControl mc;
	ModelControl mc2;

	ModelControl w2;
	ModelControl floor;
	ModelControl top;
	
	boolean godmode = false;
	
	float zoom = 30;
	float lastY = 0;
	
	Point firstClick = new Point(0,0);
	boolean mouseHeld = false;
	
	ArrayList<Prism> cprisms = new ArrayList<Prism>();
	
	ArrayList<Prism> stage_prisms = new ArrayList<Prism>();

	ArrayList<Vertex> collisionVerts = new ArrayList<Vertex>();

	Monster mydude;
	public boolean controlled = true;

	int frm = 0;

	boolean firstClicked = true;
	
	boolean up = false;
	boolean left = false;
	boolean right = false;
	boolean down = false;

	float setLuu = 0;
	float setHy  = 0;
	boolean steveOnGround = false;
	
	Prism godPrism = new Prism();
	
	
	Vertex snapPoint = new Vertex(0,-3,0);
	
	private GLU glu; // for the GL Utility
	int t;
	// The world
	Point c_mpos;
	Point p_mpos;
	int cmap_id;

	public float lookUpMax = (float) -45.0;
	public float lookUpMin = (float) 45.0;

	private boolean blendingEnabled; // Blending ON/OFF

	float[] cOffset = { 0.0f, 0.0f, 0.0f, 1.0f };

	Shader_Manager sm = new Shader_Manager();

	int shader1;
	int shader2;
	int shader3;

	float steveAngle = 0;
	
	
	ArrayList<Integer> pressedKeys = new ArrayList<Integer>();
	
	
	double xrad = -1;
	double yrad = -1;
	
	// Camera position!
	// Important, establishes the initial position and angle
	Vertex initPos = new Vertex(0, 50, 5);
	public float posX = initPos.x;
	public float posZ = initPos.y;
	public float posY = initPos.z;
	public float lookUpAngle = 0f;
	boolean asdf = false;
	public float headingY = 0; // heading of player, about y-axis
	
	cameraStats clickState = new cameraStats();
	
	private Vertex pos = new Vertex(0, 0, 0);
	private float moveIncrement = 1.0f;
	// private float turnIncrement = 1.5f; // each turn in degree
	//private float lookUpIncrement = 1.0f;

	// private float walkBias = 0;
	// private float walkBiasAngle = 0;

	private Texture[] textures = new Texture[3];
	//private int currTextureFilter = 0; // Which Filter To Use
	private String textureFilename = "/images/wall.jpg";
	static GLCanvas canvas;

	AnimationHolder steveIntoWalking;
	AnimationHolder steveWalking;
	// Texture image flips vertically. Shall use TextureCoords class to retrieve
	// the
	// top, bottom, left and right coordinates.

	// private float textureLeft;
	// private float textureRight;

	/** The entry main() method */
	public static void main(String[] args) {
		// Create the OpenGL rendering canvas
		canvas = new GLCanvas(); // heavy-weight GLCanvas

		canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
		SteveWorld renderer = new SteveWorld();
		canvas.addGLEventListener(renderer);

		// For Handling KeyEvents
		canvas.addKeyListener(renderer);
		canvas.addMouseListener(renderer);
		canvas.setFocusable(true);

		canvas.requestFocus();

		// Create a animator that drives canvas' display() at the specified FPS.
		final FPSAnimator animator = new FPSAnimator(canvas, FPS, true);

		// Create the top-level container frame
		// Swing's JFrame or AWT's Frame
		//frame.setUndecorated(true);
		frame.getContentPane().add(canvas);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// Use a dedicate thread to run the stop() to ensure that the
				// animator stops before program exits.
				new Thread() {
					@Override
					public void run() {
						animator.stop(); // stop the animator loop
						System.exit(0);
					}
				}.start();
			}
		});
		frame.setTitle(TITLE);
		frame.pack();

		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

		frame.setVisible(true);
		animator.start(); // start the animation loop
	}

	// ------ Implement methods declared in GLEventListener ------

	/**
	 * Called back immediately after the OpenGL context is initialized. Can be
	 * used to perform one-time initialization. Run only once.
	 */

	public static BufferedImage componentToImage(Component component,
			Rectangle region) throws IOException {
		BufferedImage img = new BufferedImage(component.getWidth(),
				component.getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);
		Graphics2D g = (Graphics2D) img.getGraphics();
		g.setColor(component.getForeground());
		g.setFont(component.getFont());
		component.paintAll(g);
		if (region == null) {
			region = new Rectangle(0, 0, img.getWidth(), img.getHeight());
		}
		return img.getSubimage(region.x, region.y, region.width, region.height);
	}

	@Override
	public void init(GLAutoDrawable drawable) {

		godPrism.bounds = new Vertex(20.0f, 1.0f, 20.0f);
		pos.x = 0.0f;
		pos.y = 0.0f;
		pos.z = 0.0f;

		GL2 gl = drawable.getGL().getGL2(); // get the OpenGL graphics context
		glu = new GLU(); // get GL Utilities
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f); // set background (clear) color
		gl.glClearDepth(10.0f); // set clear depth value to farthest
		gl.glEnable(GL_DEPTH_TEST); // enables depth testing
		gl.glDepthFunc(GL_LEQUAL); // the type of depth test to do
		gl.glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST); // best
																// perspective
																// correction
		gl.glShadeModel(GL_SMOOTH); // blends colors nicely, and smoothes out
									// lighting

		// Read the world
		try {
			shader1 = sm.init("platform", gl);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try {
			shader2 = sm.init("steve", gl);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		try {
			shader3 = sm.init("platform_godmode", gl);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		// Load the texture image
		try {
			// Use URL so that can read from JAR and disk file.
			BufferedImage image = ImageIO.read(this.getClass().getResource(
					textureFilename));

			// Create a OpenGL Texture object
			textures[0] = AWTTextureIO.newTexture(GLProfile.getDefault(),
					image, false);
			// Nearest filter is least compute-intensive
			// Use nearer filter if image is larger than the original texture
			gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER,
					GL.GL_NEAREST);
			// Use nearer filter if image is smaller than the original texture
			gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER,GL.GL_NEAREST);

			// For texture coordinates more than 1, set to wrap mode to
			// GL_REPEAT for
			// both S and T axes (default setting is GL_CLAMP)
			gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S,
					GL.GL_REPEAT);
			gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T,
					GL.GL_REPEAT);

		} catch (GLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// glimage img = new glimage("/images/mud.png");

		// Blending control
		gl.glColor4f(1.0f, 1.0f, 1.0f, 0.5f); // Brightness with alpha
		// Blending function For translucency based On source alpha value
		//gl.glBlendFunc(GL_SRC_ALPHA, GL_ONE);

		Prism p1 = new Prism();
		p1.bounds = new Vertex(50.0f,1.0f,50.0f);
		p1.center = new Vertex(0,-3.0f,0);
		stage_prisms.add(p1);
		
		
		/*
		this.drawPlatform(gl, new Vertex(0,-3.0f,0), new Vertex(50.0f,1.0f,50.0f));

		this.drawPlatform(gl, new Vertex(0,.9f,45.0f), new Vertex(15.0f,1.0f,15.0f));

		this.drawPlatform(gl, new Vertex(0,25f,45.0f), new Vertex(15.0f,1.0f,15.0f));

		this.drawPlatform(gl, new Vertex(0,36f,30.0f), new Vertex(15.0f,1.0f,15.0f));
		
		this.drawPlatform(gl, new Vertex(0,13f,70.0f), new Vertex(150.0f,1.0f,15.0f));

		this.drawPlatform(gl, new Vertex(30f,25f,85f), new Vertex(15.0f,1.0f,15.0f));
		
		*/
		
		
		
		
		
		
		
		
		
		steveIntoWalking = new AnimationHolder("/flaxapps/stevemodels/Steve", 0, 10, 4);
		steveWalking = new AnimationHolder("/flaxapps/stevemodels/Steve", 10, 40, 4);
		
		
		
		
		
		
		
		
		
		
		
		AnimationHolder vase = new AnimationHolder("/flaxapps/stevemodels/Steve", 1, 20, 1);

		mydude = new Monster(vase, new Vertex(0.0f, 0.0f, -200.0f));
		mydude.stop();
		mydude.rangle = 0;
		mydude.speed = 3.0f;

		w2 = new ModelControl();
		mc2 = new ModelControl();
		floor = new ModelControl();
		top = new ModelControl();

		
		try {
			w2.loadModelData("walls2.obj");
			floor.loadModelData("cplatform.obj");
			top.loadModelData("top.obj");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		this.setUp2DText(gl, "/images/wall.jpg");

		// Set up the lighting for Light-1
		// Ambient light does not come from a particular direction. Need some
		// ambient
		// light to light up the scene. Ambient's value in RGBA
		float[] lightAmbientValue = { 1.0f, 0.0f, 0.0f, 1.0f };
		// Diffuse light comes from a particular location. Diffuse's value in
		// RGBA
		float[] lightDiffuseValue = { 1.0f, 0.0f, 0.0f, 1.0f };
		// Diffuse light location xyz (in front of the screen).
		float lightDiffusePosition[] = { 1.0f, 1.0f, 0.0f, 1.0f };

		float[] lightSpecularValue = { 1.0f, 1.0f, 1.0f, 1.0f };

		gl.glLightfv(GL_LIGHT1, GL_SPECULAR, lightSpecularValue, 0);
		gl.glLightfv(GL_LIGHT1, GL_AMBIENT, lightAmbientValue, 0);
		gl.glLightfv(GL_LIGHT1, GL_DIFFUSE, lightDiffuseValue, 0);
		gl.glLightfv(GL_LIGHT1, GL_POSITION, lightDiffusePosition, 0);

		// gl.glEnable(GL_COLOR_MATERIAL);
		// gl.glColorMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE);

		// gl.glMaterialfv(GL_FRONT, GL_SPECULAR, specReflection);
		// gl.glMateriali(GL_FRONT, GL_SHININESS, 56);

		// gl.glEnable(GL_LIGHT1); // Enable Light-1
		// gl.glEnable(GL_LIGHTING); // But disable lighting
		// isLightOn = false;

		Robot r = null;
		try {
			r = new Robot();
		} catch (AWTException e2) {
			
			e2.printStackTrace();
		}
		
		if (r != null) {
			//Uncomment for mouselock
			//r.mouseMove(frame.getWidth() / 2, frame.getHeight() / 2);
		}

		//BufferedImage cursorImg = new BufferedImage(16, 16,BufferedImage.TYPE_INT_ARGB);

		// Uncomment to hide the mouse
		/*Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
				cursorImg, new Point(0, 0), "blank cursor");
		  frame.setCursor(blankCursor);*/
		p_mpos = MouseInfo.getPointerInfo().getLocation();

	}

	/**
	 * Call-back handler for window re-size event. Also called when the drawable
	 * is first set to visible.
	 */
	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width,
			int height) {
		GL2 gl = drawable.getGL().getGL2(); // get the OpenGL 2 graphics context

		if (height == 0)
			height = 1; // prevent divide by zero
		float aspect = (float) width / height;

		// Set the view port (display area) to cover the entire window
		gl.glViewport(0, 0, width, height);

		// Setup perspective projection, with aspect ratio matches viewport
		gl.glMatrixMode(GL_PROJECTION); // choose projection matrix
		gl.glLoadIdentity(); // reset projection matrix
		glu.gluPerspective(45.0f, aspect, 0.1, 100.0); // fovy, aspect, zNear,
														// zFar

		// Enable the model-view transform
		gl.glMatrixMode(GL_MODELVIEW);
		gl.glLoadIdentity(); // reset
	}

	/**
	 * Called back by the animator to perform rendering.
	 */

	class AABB {
		Vertex c; // center point
		float[] r; // halfwidths

		public AABB(float[] ar, Vertex ac) {
			c = ac;
			r = ar;
		}

	};

	public boolean testAABBAABB(AABB a, AABB b) {
		if (Math.abs(a.c.x - b.c.x) > (a.r[0] + b.r[0]))
			return false;
		if (Math.abs(a.c.y - b.c.y) > (a.r[1] + b.r[1]))
			return false;
		if (Math.abs(a.c.z - b.c.z) > (a.r[2] + b.r[2]))
			return false;
		return true;
	}

	public void draw2doorRoom(Vertex l, double a, GL2 gl) {
		
		gl.glColor3d(1.0, 0.0, 0.0);
		//collisionVerts.addAll(floor.drawModel(l, gl, 0));
		gl.glColor3d(0.0, 1.0, 0.0);
		
		//collisionVerts.addAll(w2.drawModel(l, gl, (float) a));
		w2.restore();
		gl.glColor3d(0.0, 0.0, 1.0);
		//top.drawModel(l, gl, 0);
		
		
	}

	public void captureScreen(String fileName) throws Exception {
		/*
		 * Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		 * Rectangle screenRectangle = new Rectangle(screenSize); Robot robot =
		 * new Robot(); Component component = canvas;
		 * 
		 * BufferedImage img = new BufferedImage(component.getWidth(),
		 * component.getHeight(), BufferedImage.TYPE_INT_ARGB_PRE); Graphics2D g
		 * = (Graphics2D) img.getGraphics();
		 * g.setColor(component.getForeground());
		 * g.setFont(component.getFont());
		 * 
		 * 
		 * ImageIO.write(img,"png",new File(fileName));
		 */
	}

	class Prism2D{
		
		
		Vertex center;
		Vertex bounds;
		public boolean containsPoint(Vertex point){
			
			
			//Bounds refers to the total width/height of the circle!
			//Must divide them by two when placing in the equation
			double sx = Math.pow((point.x - center.x)/(bounds.x/2), 50);
			double sy = Math.pow((point.y - center.y)/(bounds.y/2), 50);
			
			return sx + sy <= 1;
			
		}
		
		
		boolean setInPrism(ArrayList<Vertex> s, Prism2D p){
			for(int i = 0; i<s.size(); i++){
				if(p.containsPoint(s.get(i)))
					return true;
			}
			return false;
		}
		
		
		boolean setInPrisms(ArrayList<Vertex> s, Prism2D p, Prism2D q){
			for(int i = 0; i<s.size(); i++){
				if(p.containsPoint(s.get(i)) && q.containsPoint(s.get(i)))
					return true;
			}
			return false;
		}
		
		
		public ArrayList<Vertex> getCorners(){
			
			ArrayList<Vertex> set = new ArrayList<Vertex>();
			
			set.add(new Vertex(center.x + bounds.x/2, center.y + bounds.y/2, 0));
			set.add(new Vertex(center.x + bounds.x/2, center.y - bounds.y/2, 0));
			set.add(new Vertex(center.x - bounds.x/2, center.y + bounds.y/2, 0));
			set.add(new Vertex(center.x - bounds.x/2, center.y - bounds.y/2, 0));
			
			return set;
		}
		
		
		public boolean hitsPrism(Prism2D p){
			

			if(setInPrism(this.getCorners(),p))
				return true;

			if(setInPrism(p.getCorners(),this))
				return true;
			
			ArrayList<Vertex> set = new ArrayList<Vertex>();
			
			Vertex dif = new Vertex(center.x - p.center.x, center.y - p.center.y, 0);
			
			set.add(new Vertex(p.center.x + dif.x, p.center.y + dif.y, 0));
			set.add(new Vertex(p.center.x + 0	  , p.center.y + dif.y, 0));
			set.add(new Vertex(p.center.x + dif.x, p.center.y + 0	  , 0));
			set.add(new Vertex(p.center.x + 0	  , p.center.y + 0	  , 0));
			
			if(setInPrisms(set,p,this))
				return true;
			 
			
			
			return false;
		}
		
	}
	
	class Prism{
		Vertex center;
		Vertex bounds;
		
		public Prism(){
			center = new Vertex(0, 0, 0);
			bounds = new Vertex(0, 0, 0);
		}
		
		
		public boolean hitsPrism(Prism p){
			

			
			int count = 0;
			Prism2D x1 = new Prism2D();
			x1.center = new Vertex(center.y, center.z, 0);
			x1.bounds = new Vertex(bounds.y, bounds.z, 0);
			Prism2D x2 = new Prism2D();
			x2.center = new Vertex(p.center.y, p.center.z, 0);
			x2.bounds = new Vertex(p.bounds.y, p.bounds.z, 0);
			if(x1.hitsPrism(x2)){
				count++;
			}
			
			Prism2D y1 = new Prism2D();
			y1.center = new Vertex(center.x, center.z, 0);
			y1.bounds = new Vertex(bounds.x, bounds.z, 0);
			Prism2D y2 = new Prism2D();
			y2.center = new Vertex(p.center.x, p.center.z, 0);
			y2.bounds = new Vertex(p.bounds.x, p.bounds.z, 0);
			if(y1.hitsPrism(y2))
			{
				count++;
			}
			
			Prism2D z1 = new Prism2D();
			z1.center = new Vertex(center.x, center.y, 0);
			z1.bounds = new Vertex(bounds.x, bounds.y, 0);
			Prism2D z2 = new Prism2D();
			z2.center = new Vertex(p.center.x, p.center.y, 0);
			z2.bounds = new Vertex(p.bounds.x, p.bounds.y, 0);
			if(z1.hitsPrism(z2))
			{
				count++;
			}
			
			//System.out.println(s);
			if(count==2){
				System.out.println("DEFYING PHYSICS; PLEASE HOLD");
			}
			return count==3;
		}
		
	}
	
	public indexedPoint masterObjectAtIndex(int x, int y, int z){return master.get(z).get(y).get(x);}
	
	public void addDepth(){
		ArrayList<indexedPoint> block = new ArrayList<indexedPoint>();
		for(int i = 0; i<master.get(0).get(0).size(); i++){
			block.add(null);
		}
		ArrayList<ArrayList<indexedPoint>> bblock = new ArrayList<ArrayList<indexedPoint>>();
		for(int i = 0; i<master.get(0).size(); i++){
			bblock.add(block);
		}
		master.add(bblock);
	}
	
	public void addColumn(){
		ArrayList<indexedPoint> block = new ArrayList<indexedPoint>();
		for(int i = 0; i<master.get(0).get(0).size(); i++){
			block.add(null);
		}
		
		
		
	}
	
	public boolean hitsWall(Prism pris) {
		
		for (int i = 0; i < cprisms.size(); i++) {
			
			if(pris.hitsPrism(cprisms.get(i))){
				
				return true;
			}
		}
		
		return false;
		
	}
	
	public boolean hitsWall(Vertex pis) {

		
		
		Prism steveBox = new Prism();
		steveBox.center = new Vertex(pis.x,pis.y,pis.z);
		double h = 8.4;
		steveBox.center.y += h/2f;
		double x = 3.5, z = 3.5;
		//double x = Math.abs(s*Math.cos(steveAngle)) + Math.abs(s*Math.sin(steveAngle));
		//double z = Math.abs(s*Math.sin(steveAngle)) + Math.abs(s*Math.cos(steveAngle));
		steveBox.bounds = new Vertex((float)x, (float)h, (float)z);
		
		//System.out.println("Testing " + cprisms.size() + " prisms:");
		
		for (int i = 0; i < cprisms.size(); i++) {

			if(steveBox.hitsPrism(cprisms.get(i))){
				
				return true;
			}
		}
		
		return false;
		
	}

	public void handleKeyEvents(){
		
		if(!godmode){
			boolean moving = false;
			
			float oldAngle = (float)steveAngle;
			for(int i = 0; i<pressedKeys.size(); i++){
				
				
				switch(pressedKeys.get(i)){
					case VK_SPACE:
							if(steveOnGround){
								downV = 3.0f;
								steveOnGround = false;
							}
						break;
					case VK_T:
						
						double ds = Math.sqrt(Math.pow(posX - pos.x,2) + Math.pow(posZ - pos.z,2));
						//dis = 5.0;
						headingY+=10.0;
						float tz = (float) (ds*Math.cos(Math.toRadians(headingY))) + pos.z;
						float tx = (float) (ds*Math.sin(Math.toRadians(headingY))) + pos.x;
						//if(!this.hitsWall(new Vertex(tx, 10, tz), hls)){
							posZ = tz;
							posX = tx;
						/*}
						else{
							headingY-=10.0;
						}*/
						break;
					case VK_R:
						
						double dis = Math.sqrt(Math.pow(posX - pos.x,2) + Math.pow(posZ - pos.z,2));
						//dis = 5.0;
						headingY-=10.0;
						float tz1 = (float) (dis*Math.cos(Math.toRadians(headingY))) + pos.z;
						float tx1 = (float) (dis*Math.sin(Math.toRadians(headingY))) + pos.x;
						
						//if(!this.hitsWall(new Vertex(tx1, 10, tz1), hls)){
							posZ = tz1;
							posX = tx1;
						/*}
						else{
							headingY+=10.0;
						}*/
						
						break;
					case VK_W:
						moving = true;
						steveAngle = (float) (Math.toRadians(headingY) + 3*Math.PI/2 - 0);
						break;
					case VK_E:
						moving = true;
						steveAngle = (float) (Math.toRadians(headingY) + 3*Math.PI/2 - 1*Math.PI/4);
						break;
					case VK_D:
						moving = true;
						steveAngle = (float) (Math.toRadians(headingY) + 3*Math.PI/2 - 2*Math.PI/4);
						break;
					case VK_Z:
						moving = true;
						steveAngle = (float) (Math.toRadians(headingY) + 3*Math.PI/2 - 5*Math.PI/4);
						break;
					case VK_X:
						moving = true;
						steveAngle = (float) (Math.toRadians(headingY) + 3*Math.PI/2 - 4*Math.PI/4);
						break;
					case VK_C:
						moving = true;
						steveAngle = (float) (Math.toRadians(headingY) + 3*Math.PI/2 - 3*Math.PI/4);
						break;
					case VK_A:
						moving = true;
						steveAngle = (float) (Math.toRadians(headingY) + 3*Math.PI/2 - 6*Math.PI/4);;
						break;
					case VK_Q:
						moving = true;
						steveAngle = (float) (Math.toRadians(headingY) + 3*Math.PI/2 - 7*Math.PI/4);
						break;
				
				}
				
				
				
			}
			
			if(this.hitsWall(new Vertex(pos.x,pos.y,pos.z))){
				//System.out.println("o.");
				steveAngle = oldAngle;
				moving = false;
			}
			
			still = !moving;
			
		}
		else{
			
			
			
			
			
			if(this.pressedKeys.contains(VK_X)){
				
				if(this.pressedKeys.contains(VK_S)){
					this.pressedKeys = new ArrayList<Integer>();
					System.out.println("DOING");
					String ret = this.askUserForInput("X VALUE");
					if(ret!=null && Float.valueOf(ret)<this.MAX_SIZE){
						float size = Float.valueOf(ret);
						
						if(size>0){
							godPrism.bounds.x = size;
						}
					}
				}
				else{
					if(this.pressedKeys.contains(VK_CONTROL))
						zoom = Math.abs(zoom)*-1;
					else
						zoom = Math.abs(zoom);
					posX = zoom + snapPoint.x;
					posY = 0 + snapPoint.y;
					posZ = 0 + snapPoint.z;
				}
			}
			if(this.pressedKeys.contains(VK_Y)){
				if(this.pressedKeys.contains(VK_S)){
					this.pressedKeys = new ArrayList<Integer>();
					System.out.println("DOING");
					String ret = this.askUserForInput("Y VALUE");
					if(ret!=null && Float.valueOf(ret)<this.MAX_SIZE){
						float size = Float.valueOf(ret);
						
						if(size>0){
							godPrism.bounds.y = size;
						}
					}
				}
				else{
					if(this.pressedKeys.contains(VK_CONTROL))
						zoom = Math.abs(zoom)*-1;
					else
						zoom = Math.abs(zoom);
					posX = 0 + snapPoint.x;
					posY = zoom + snapPoint.y;
					posZ = 0 + snapPoint.z;
				}
			}
			if(this.pressedKeys.contains(VK_Z)){
				if(this.pressedKeys.contains(VK_S)){
					this.pressedKeys = new ArrayList<Integer>();
					System.out.println("DOING");
					String ret = this.askUserForInput("Z VALUE");
					if(ret!=null && Float.valueOf(ret)<this.MAX_SIZE){
						float size = Float.valueOf(ret);
						
						if(size>0){
							godPrism.bounds.z = size;
						}
					}
				}
				else{
					if(this.pressedKeys.contains(VK_CONTROL))
						zoom = Math.abs(zoom)*-1;
					else
						zoom = Math.abs(zoom);
					posX = 0 + snapPoint.x;
					posY = 0 + snapPoint.y;
					posZ = zoom + snapPoint.z;
				}
			}
			
			if(mouseHeld){
				
				if(this.pressedKeys.contains(VK_CONTROL)){
					
					Point current = MouseInfo.getPointerInfo().getLocation();
					
					double dis1 = Math.sqrt(Math.pow(firstClick.x + 8 - SteveWorld.frame.getSize().width/2, 2) + Math.pow(firstClick.y + 30 - SteveWorld.frame.getSize().height/2, 2));
					double dis2 = Math.sqrt(Math.pow((current.x - SteveWorld.frame.getLocation().x) - SteveWorld.frame.getSize().width/2, 2) + Math.pow((current.y - SteveWorld.frame.getLocation().y) - SteveWorld.frame.getSize().height/2, 2));
					//System.out.println(firstClick.x - 8 - (current.x - this.frame.getLocation().x - 16));
					//System.out.println(firstClick.y + 30 - (current.y - this.frame.getLocation().y));
					zoom = (float) (this.clickState.zoom + (dis1 - dis2)/10);
					
					if((Math.abs(this.clickState.zoom)/this.clickState.zoom)==1){
						if(zoom<.5){
							zoom = .5f;
						}
					}
					else{
						
						if(zoom>-.5){
							
							zoom = -.5f;
						}
					}
					
					
					if(posX!=snapPoint.x){
						posX = snapPoint.x + zoom;
					}
					if(posY!=snapPoint.y){
						posY = snapPoint.y + zoom;
					}
					if(posZ!=snapPoint.z){
						posZ = snapPoint.z + zoom;
					}
					
					
				}
				
				else if(this.pressedKeys.contains(VK_SHIFT)){
					
					Point current = MouseInfo.getPointerInfo().getLocation();
					
					
					
					float x = ((current.x - SteveWorld.frame.getLocation().x) - firstClick.x - 8) / 10;
					float y = ((current.y - SteveWorld.frame.getLocation().y) - firstClick.y - 30) / 10;
					
					float dx = -(float) (x*Math.cos(Math.toRadians(this.clickState.lua))*Math.cos(Math.toRadians(this.clickState.hy)) + -y*Math.sin(Math.toRadians(this.clickState.lua)));
					float dy = (float) (y*Math.cos(Math.toRadians(this.clickState.lua)));
					float dz = (float) (x*Math.cos(Math.toRadians(this.clickState.lua))*Math.sin(Math.toRadians(this.clickState.hy)) + -x*Math.abs(Math.sin(Math.toRadians(this.clickState.lua))));
					
					
					
					snapPoint = new Vertex(this.clickState.snapPoint.x + dx,this.clickState.snapPoint.y + dy,this.clickState.snapPoint.z + dz);
					posX= this.clickState.pos.x + dx;
					posY= this.clickState.pos.y + dy;
					posZ= this.clickState.pos.z + dz;
					
					
				}
				
				else if(this.pressedKeys.contains(VK_P)){
					if(this.canPlaceGP){
						Prism addPrism = new Prism();
						addPrism.bounds = new Vertex(godPrism.bounds.x, godPrism.bounds.y, godPrism.bounds.z);
						addPrism.center = new Vertex(godPrism.center.x, godPrism.center.y, godPrism.center.z);
						stage_prisms.add(addPrism);
					}
					
				}
								
			}
			
			
			makeCameraFacePoint(snapPoint);
			godPrism.center = new Vertex(snapPoint.x,snapPoint.y,snapPoint.z);
			
		}
		
	}
	
	public String askUserForInput(String infoMessage)
    {
		return JOptionPane.showInputDialog(infoMessage);
    }
	
	
	
	
	
	private void makeCameraFacePoint(Vertex point){
		
		double dz = posZ - point.z;
		double dx = posX - point.x;
		double dy = posY - point.y;
		
		
		headingY = (float) Math.toDegrees(Math.atan(dx/dz));
			
		if(dz<0){
			headingY+=180;
		}
		/*
		if(dx == 0){
			headingY = 0;
		}
		*/
		if(dz == 0){
			if(dx>0){
				headingY = 90;
			}
			else{
				headingY = -90;
			}
		}
		
		double xzdis = Math.sqrt(Math.pow(dz, 2) + Math.pow(dx,2));
		
		lookUpAngle = (float) Math.toDegrees(Math.atan(dy/xzdis));
		
		if(dy == 0){
			lookUpAngle = 0;
		}
		if(xzdis == 0){
			
			if(dy>0){
				lookUpAngle = 90;
			}
			else{
				lookUpAngle = -90;
			}
		}
		
		
	}
	
	public Vertex rotatePointAroundOrigin(Vertex p, double angle){
		//ANGLE IN RADIANS!!!!!!!
		Vertex np = new Vertex(0, 0, 0);
		np.x = (float) (p.x*Math.cos(angle) - p.y*Math.sin(angle));
		np.y = (float) (p.x*Math.sin(angle) + p.y*Math.cos(angle));
		return np;
		
	}
	
	long last = -1;
	float downV = 0;
	boolean first = true;
	
	@Override
	public void display(GLAutoDrawable drawable) {

	
		GL2 gl = drawable.getGL().getGL2(); // get the OpenGL 2 graphics context
		gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear color
    	gl.glLoadIdentity(); // reset the model-view matrix
    
    	gl.glDisable(GL.GL_TEXTURE11);
		gl.glColor3d(1.0, 0.0, 0.0);
		
	    if(first){
			long lDateTime = new Date().getTime();
			
			if(last == -1){
				last = lDateTime;
			}
			
			
			if(lDateTime - last>1000)
				first = false;
	    	
			
	    
			
	    
		}
	    
		
		
	    	
	    	
	    		
	    	// System.out.println("F: " + dlta);
	    
	    	 if(!first){
		
	    float graviT = -0.3f;
	    downV+=graviT;
	    
	    if(pos.y<=-30){
	    	pos = new Vertex(0, 0, 0);
	    	posX = initPos.x;
	    	posZ = initPos.y;
	    	posY = initPos.z;
	    	steveAngle = 0;
	    	headingY = 0;
	    }
	    
	    //downV=-.1f;
		pos.y+=downV;
		posY+=downV;
		//posY+=downV;
		
		
		
		if(this.hitsWall(new Vertex(pos.x,pos.y,pos.z))){
			pos.y-=downV;
			posY-=downV;
			
			while(downV>.001){
				
				
				downV+=(graviT);
				
				pos.y+=downV;
				posY+=downV;
				
				
				if(this.hitsWall(new Vertex(pos.x,pos.y,pos.z))){
					pos.y-=downV;
					posY-=downV;
				}
				else{
					downV = 0;
					
				}
			}
			while(downV<-.001){
				//System.out.println("loop");
				downV-=(graviT);
				//System.out.println(downV);
				pos.y+=downV;
				posY+=downV;
				
				
				if(this.hitsWall(new Vertex(pos.x,pos.y,pos.z))){
					pos.y-=downV;
					posY-=downV;
				}
				else{
					downV = 0;
					steveOnGround = true;		
				}
			}
			
			//System.out.println("Loop 2 processed " + i + " times\n");
			
			
		}
		else{
			steveOnGround = false;
		}

		
		
		
			this.handleKeyEvents();
		

		
		if (up) {
			posX -= (float) Math.sin(Math.toRadians(headingY)) * moveIncrement;
			posZ -= (float) Math.cos(Math.toRadians(headingY)) * moveIncrement;

			if (this.hitsWall(new Vertex(posX, posY + 10.0f, posZ))) {
				posX += (float) Math.sin(Math.toRadians(headingY))
						* moveIncrement;
				posZ += (float) Math.cos(Math.toRadians(headingY))
						* moveIncrement;
			}

		}
		if (down) {
			// Player move out, posX and posZ become bigger
			posX += (float) Math.sin(Math.toRadians(headingY)) * moveIncrement;
			posZ += (float) Math.cos(Math.toRadians(headingY)) * moveIncrement;

			if (this.hitsWall(new Vertex(posX, posY + 10.0f, posZ))) {
				posX -= (float) Math.sin(Math.toRadians(headingY))
						* moveIncrement;
				posZ -= (float) Math.cos(Math.toRadians(headingY))
						* moveIncrement;
			}
		}
		if (left) {
			// Player move out, posX and posZ become bigger
			posX -= (float) Math.sin(Math.toRadians(headingY + 90.0))
					* moveIncrement;
			posZ -= (float) Math.cos(Math.toRadians(headingY + 90.0))
					* moveIncrement;
			if (this.hitsWall(new Vertex(posX, posY + 10.0f, posZ))) {
				posX += (float) Math.sin(Math.toRadians(headingY + 90.0))
						* moveIncrement;
				posZ += (float) Math.cos(Math.toRadians(headingY + 90.0))
						* moveIncrement;
			}
		}
		if (right) {
			// Player move out, posX and posZ become bigger
			posX -= (float) Math.sin(Math.toRadians(headingY - 90.0))
					* moveIncrement;
			posZ -= (float) Math.cos(Math.toRadians(headingY - 90.0))
					* moveIncrement;

			if (this.hitsWall(new Vertex(posX, posY + 10.0f, posZ))) {
				posX += (float) Math.sin(Math.toRadians(headingY - 90.0))
						* moveIncrement;
				posZ += (float) Math.cos(Math.toRadians(headingY - 90.0))
						* moveIncrement;
			}
		}

	    	 }
		if (controlled) {
			c_mpos = MouseInfo.getPointerInfo().getLocation();
			
			
			//Uncomment to unlock the camera rotation
			/*lookUpAngle += (ydif / 5.0);
			
			if ((lookUpAngle <= lookUpMax || lookUpAngle >= lookUpMin)) {
				lookUpAngle -= (ydif / 5.0);
			}

			headingY -= (xdif / 5.0);*/

			Robot r = null;
			try {
				r = new Robot();
			} catch (AWTException e2) {
				
				e2.printStackTrace();
			}
			if (r != null) {
				/*//Uncomment for mouse lock
				r.mouseMove(frame.getWidth() / 2, frame.getHeight() / 2);
				*/
			}

		}

		t = (t + 1);
		

		
		
		
		gl.glEnable(GL_SMOOTH);

		// Blending control
		
		gl.glEnable(GL.GL_BLEND);
		
		
		gl.glRotatef((float) this.zrangle, 0, 0, 1.0f);
		
		// Rotate up and down to look up and down
		gl.glRotatef(lookUpAngle, 1.0f, 0, 0);

		// Player at headingY. Rotate the scene by -headingY instead (add 360 to
		// get a
		// positive angle)
		gl.glRotatef(360.0f - headingY, 0, 1.0f, 0);

		// Player is at (posX, 0, posZ). Translate the scene to (-posX, 0,
		// -posZ)
		// instead.
		// float[] po = {posX,-(posY + (-walkBias - 0.25f)),posZ};
		//gl.glEnable(GL.GL_TEXTURE_2D);
		//gl.glBindTexture(GL.GL_TEXTURE_2D, 11);
		
		gl.glTranslatef(-posX, -posY, -posZ);

		gl.glUseProgram(shader2);
		
		int l = gl.glGetUniformLocation(shader2, "lightDir");
		int pp = gl.glGetUniformLocation(shader2, "ppos");
		int sh = gl.glGetUniformLocation(shader2, "shadow");
		
		
		//TODO: Work with camera
			
		if(!still){
			
			float vel = 1.0f;
			double dx = -vel*Math.cos(steveAngle);
			double dz = vel*Math.sin(steveAngle);
			
			
			//Apply a rotation matrix to account for rotated axes
			//when camera is rotated
			double rhy = Math.toRadians(headingY);
			Vertex rps = this.rotatePointAroundOrigin(new Vertex((float)dx,(float)dz,0), -rhy);
			double rdx = rps.x;
			double rdz = rps.y;
			
			
			pos.x+=dx;
			pos.z+=dz;
			posX+=dx;
			posZ+=dz;
			if(this.hitsWall(new Vertex(pos.x,pos.y,pos.z))){
				System.out.println("Ouch..");
				pos.x-=dx;
				pos.z-=dz;
				posX-=dx;
				posZ-=dz;
			}
			
			
			
			//System.out.println("Z: " + zbuffer + " " + zpos + " X: " + xbuffer + " " + xpos);
			
			boolean zdoes = false;
			boolean xdoes = false; 
			
			//Only move the camera if Steve isn't too close
			if(zbuffer*zpos <= 0.001){
				zdoes = true;
				//posX+=delta.x;
				//posZ+=delta.y;
			}else if(zbuffer*zpos > 0.001){
				zbuffer+=rdz;
			}

			if(xbuffer*xpos <= 0.001){
				xdoes = true;
				//posX+=delta.x;
				//posZ+=delta.y;
				
			}else if(xbuffer*xpos > 0.001){
				xbuffer+=rdx;
			}
			
			
			
			
			
			
			if(this.hitsWall(new Vertex(posX,10,posZ))){
				//posX-=dx;
				//posZ-=dz;
				
				//Keep track of how close Steve is to the camera
				//Done by recording every movement he makes
				//while the camera is still
				
				if(xdoes){
					xpos = (float)rdx;
				}
				if(zdoes){
					zpos = (float)rdz;
				}
				
				xbuffer+=rdx;
				zbuffer+=rdz;
				
			}
			
			
			
			if(this.hitsWall(new Vertex(posX,10,posZ))){
				
				
			}
			
			
		}
		
		
		gl.glUniform3f(l, -pos.x + posX, -pos.y + posY, -pos.z + posZ);
		gl.glUniform3f(pp, pos.x, pos.y, pos.z);
		gl.glUniform1f(sh, 0.0f);
		
		if(still){
			
			//Draw steve standing still - Make sure he's at frame 0
			steveIntoWalking.currentFrame = 0;
			steveWalking.currentFrame = 0;
			steveIntoWalking.drawStillFrame(new Vertex(pos.x,pos.y,pos.z), gl, steveAngle);
		}
		else{
			
			//Little walking preparation animation
			//When this animation ends, draw him actually walking
			if(steveIntoWalking.currentFrame< steveIntoWalking.maxFrame - 1){
				steveIntoWalking.drawFrame(new Vertex(pos.x,pos.y,pos.z), gl, steveAngle);
				
			}
			else{
				
				steveWalking.drawFrame(new Vertex(pos.x,pos.y,pos.z), gl, steveAngle);
				
			}
		}
		
		
		canPlaceGP = false;
		
		if(godmode){
			gl.glUseProgram(shader3);
			
			//int txt = gl.glGetUniformLocation(shader1, "mytext");
			//gl.glUniform1f(txt, 11);
			
			int ps = gl.glGetUniformLocation(shader3, "lightDir");
			int gp = gl.glGetUniformLocation(shader3, "godPlatform");
			int color = gl.glGetUniformLocation(shader3, "clr");
			
			
			gl.glUniform4f(ps, -snapPoint.x + posX, -snapPoint.y + posY + 50, -snapPoint.z + posZ, 1.0f);
			
			if(this.hitsWall(godPrism)){
				gl.glUniform4f(color, 1.0f, 0.0f, 0.0f, 1.0f);
			}
			else{
				gl.glUniform4f(color, 0.0f, 1.0f, 0.0f, 1.0f);
				canPlaceGP = true;
			}
			
			
			gl.glUniform4f(ps, 1,1,1, 1.0f);
			gl.glUniform1f(gp, 1);
			
			
			this.drawPlatform(gl, godPrism);
			
			gl.glUniform1f(gp, 0);
			gl.glUniform4f(color, 0.0f, 0.0f, 1.0f, 1.0f);
			
			//gl.glUniform4f(ps, 0,0,0, 1.0f);
			
			int vps = gl.glGetUniformLocation(shader3, "ppos");
			gl.glUniform4f(vps, pos.x, pos.y, pos.z, 1.0f);
		}
		else{
		gl.glUseProgram(shader1);

		//int txt = gl.glGetUniformLocation(shader1, "mytext");
		//gl.glUniform1f(txt, 11);
		
		int ps = gl.glGetUniformLocation(shader1, "lightDir");
		gl.glUniform4f(ps, -pos.x + posX, -pos.y + posY + 50, -pos.z + posZ, 1.0f);
		//gl.glUniform4f(ps, 0,0,0, 1.0f);
		
		int vps = gl.glGetUniformLocation(shader1, "ppos");
		gl.glUniform4f(vps, pos.x, pos.y, pos.z, 1.0f);
		
		
		}
		
		
		
		gl.glDisable(GL.GL_BLEND);
		
		
		cprisms = new ArrayList<Prism>();
		
		//gl.glUniform1f(sh, 0.2f);
		
		//gl.glUniform3f(l, -pos.x + posX, -pos.y + posY + 50, -pos.z + posZ);
		
		for(int i = 0; i<stage_prisms.size(); i++){
			this.drawPlatform(gl, stage_prisms.get(i));
		}
		
		/*
		this.drawPlatform(gl, new Vertex(0,-3.0f,0), new Vertex(50.0f,1.0f,50.0f));

		this.drawPlatform(gl, new Vertex(0,.9f,45.0f), new Vertex(15.0f,1.0f,15.0f));

		this.drawPlatform(gl, new Vertex(0,25f,45.0f), new Vertex(15.0f,1.0f,15.0f));

		this.drawPlatform(gl, new Vertex(0,36f,30.0f), new Vertex(15.0f,1.0f,15.0f));
		
		this.drawPlatform(gl, new Vertex(0,13f,70.0f), new Vertex(150.0f,1.0f,15.0f));

		this.drawPlatform(gl, new Vertex(30f,25f,85f), new Vertex(15.0f,1.0f,15.0f));
		*/
		
		
		gl.glFlush();
	    
		 	  
	    
	    }
	
		public void drawPlatform(GL2 gl, Vertex center, Vertex bounds){
			Prism p1 = new Prism();
			p1.center = new Vertex(center.x,center.y,center.z);
			p1.bounds = new Vertex(bounds.x,bounds.y,bounds.z);
			// rtrgl.glUniform3f(lightLoc, 0.0f, 1.0f, 0.0f);
			
			//gl.glUniform3f(lightLoc, -p1.center.x + posX, -p1.center.y + posY, -p1.center.z + posZ);
			cprisms.add(p1);
			
			floor.drawModel(p1.center, gl, 0, new Vertex(bounds.x/2,bounds.y/2,bounds.z/2));
		}
		
		public void drawPlatform(GL2 gl, Prism p1){
			
			
			//gl.glUniform3f(lightLoc, -p1.center.x + posX, -p1.center.y + posY, -p1.center.z + posZ);
			cprisms.add(p1);
			
			floor.drawModel(p1.center, gl, 0, new Vertex(p1.bounds.x/2,p1.bounds.y/2,p1.bounds.z/2));
		}
	

	/**
	 * Called back before the OpenGL context is destroyed. Release resource such
	 * as buffers.
	 */

	public im makeImg(String txt) {
		BufferedImage bufferedImage = null;
		int w = 0;
		int h = 0;
		try {
			bufferedImage = ImageIO.read(SteveWorld.class
					.getResource(txt));
			w = bufferedImage.getWidth();
			h = bufferedImage.getHeight();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		WritableRaster raster = Raster.createInterleavedRaster(
				DataBuffer.TYPE_BYTE, w, h, 4, null);
		ComponentColorModel colorModel = new ComponentColorModel(
				ColorSpace.getInstance(ColorSpace.CS_sRGB), new int[] { 8, 8,
						8, 8 }, true, false, ComponentColorModel.TRANSLUCENT,
				DataBuffer.TYPE_BYTE);
		BufferedImage dukeImg = new BufferedImage(colorModel, raster, false,
				null);

		Graphics2D g = dukeImg.createGraphics();
		g.drawImage(bufferedImage, null, null);
		DataBufferByte dukeBuf = (DataBufferByte) raster.getDataBuffer();
		byte[] dukeRGBA = dukeBuf.getData();
		ByteBuffer bb = ByteBuffer.wrap(dukeRGBA);
		bb.position(0);
		bb.mark();
		im i = new im();
		i.b = bb;
		i.he = h;
		i.wi = w;
		return i;
	}

	class im {
		public ByteBuffer b;
		public int wi;
		public int he;
	}

	public int setUp2DText(GL2 gl, String txt) {

		im mud = this.makeImg(txt);

		gl.glBindTexture(GL.GL_TEXTURE_2D, 11);
		gl.glPixelStorei(GL.GL_UNPACK_ALIGNMENT, 1);
		gl.glTexImage2D(GL2.GL_TEXTURE_2D, 0, GL.GL_RGBA, mud.wi, mud.he, 0,
				GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, mud.b);

		// Use nearer filter if image is larger than the original texture
		gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER,
				GL.GL_NEAREST);
		// Use nearer filter if image is smaller than the original texture
		gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER,
				GL.GL_NEAREST);

		// For texture coordinates more than 1, set to wrap mode to GL_REPEAT
		// for
		// both S and T axes (default setting is GL_CLAMP)
		gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_LINEAR);
		gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_LINEAR);
		return 11;
	}

	public int setUpText(GL2 gl, String txt) {
		im b = this.makeImg(txt);
		im mud = this.makeImg("/images/mars2.jpg");
		ByteBuffer bb = b.b;
		int w = b.wi;
		int h = b.he;
		gl.glBindTexture(GL.GL_TEXTURE_CUBE_MAP, 13);
		gl.glPixelStorei(GL.GL_UNPACK_ALIGNMENT, 1);
		gl.glTexImage2D(GL2.GL_TEXTURE_CUBE_MAP_POSITIVE_X, 0, GL.GL_RGBA,
				mud.wi, mud.he, 0, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, mud.b);
		// postive x
		gl.glTexImage2D(GL2.GL_TEXTURE_CUBE_MAP_NEGATIVE_X, 0, GL.GL_RGBA, w,
				h, 0, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, bb);
		// negative x
		gl.glTexImage2D(GL2.GL_TEXTURE_CUBE_MAP_POSITIVE_Y, 0, GL.GL_RGBA, w,
				h, 0, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, bb);
		// postive y
		gl.glTexImage2D(GL2.GL_TEXTURE_CUBE_MAP_NEGATIVE_Y, 0, GL.GL_RGBA, w,
				h, 0, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, bb);
		// negative y
		gl.glTexImage2D(GL2.GL_TEXTURE_CUBE_MAP_POSITIVE_Z, 0, GL.GL_RGBA,
				mud.wi, mud.he, 0, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, mud.b);
		// positive z
		gl.glTexImage2D(GL2.GL_TEXTURE_CUBE_MAP_NEGATIVE_Z, 0, GL.GL_RGBA, w,
				h, 0, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, bb);

		// gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER,
		// GL.GL_LINEAR);
		// gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER,
		// GL.GL_LINEAR_MIPMAP_NEAREST);
		// gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S,
		// GL.GL_REPEAT);
		// gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T,
		// GL.GL_REPEAT);

		gl.glTexParameteri(GL2.GL_TEXTURE_CUBE_MAP, GL2.GL_TEXTURE_MAG_FILTER,
				GL2.GL_LINEAR);
		gl.glTexParameteri(GL2.GL_TEXTURE_CUBE_MAP, GL2.GL_TEXTURE_MIN_FILTER,
				GL2.GL_LINEAR);
		gl.glTexParameteri(GL2.GL_TEXTURE_CUBE_MAP, GL2.GL_TEXTURE_WRAP_S,
				GL2.GL_CLAMP_TO_EDGE);
		gl.glTexParameteri(GL2.GL_TEXTURE_CUBE_MAP, GL2.GL_TEXTURE_WRAP_T,
				GL2.GL_CLAMP_TO_EDGE);
		gl.glTexParameteri(GL2.GL_TEXTURE_CUBE_MAP, GL2.GL_TEXTURE_WRAP_R,
				GL2.GL_CLAMP_TO_EDGE);

		return 13;
	}

	public int setUpCubeMap(GL2 gl, int width, int height, ByteBuffer data_px,
			ByteBuffer data_nx, ByteBuffer data_py, ByteBuffer data_ny,
			ByteBuffer data_pz, ByteBuffer data_nz) {

		// Make texture
		gl.glBindTexture(GL2.GL_TEXTURE_CUBE_MAP, 13); // Make it a cubemap
		gl.glPixelStorei(GL.GL_UNPACK_ALIGNMENT, 1);
		gl.glTexImage2D(GL2.GL_TEXTURE_CUBE_MAP_POSITIVE_X, 0, GL.GL_RGBA,
				width, height, 0, GL.GL_RGB, GL.GL_UNSIGNED_BYTE, data_px);
		// postive x
		gl.glTexImage2D(GL2.GL_TEXTURE_CUBE_MAP_NEGATIVE_X, 0, GL.GL_RGBA,
				width, height, 0, GL.GL_RGB, GL.GL_UNSIGNED_BYTE, data_nx);
		// negative x
		gl.glTexImage2D(GL2.GL_TEXTURE_CUBE_MAP_POSITIVE_Y, 0, GL.GL_RGBA,
				width, height, 0, GL.GL_RGB, GL.GL_UNSIGNED_BYTE, data_py);
		// postive y
		gl.glTexImage2D(GL2.GL_TEXTURE_CUBE_MAP_NEGATIVE_Y, 0, GL.GL_RGBA,
				width, height, 0, GL.GL_RGB, GL.GL_UNSIGNED_BYTE, data_ny);
		// negative y
		gl.glTexImage2D(GL2.GL_TEXTURE_CUBE_MAP_POSITIVE_Z, 0, GL.GL_RGBA,
				width, height, 0, GL.GL_RGB, GL.GL_UNSIGNED_BYTE, data_pz);
		// positive z
		gl.glTexImage2D(GL2.GL_TEXTURE_CUBE_MAP_NEGATIVE_Z, 0, GL.GL_RGBA,
				width, height, 0, GL.GL_RGB, GL.GL_UNSIGNED_BYTE, data_nz);
		// negative z
		gl.glTexParameteri(GL2.GL_TEXTURE_CUBE_MAP, GL.GL_TEXTURE_MAG_FILTER,
				GL.GL_LINEAR);
		// Set far filtering mode
		gl.glTexParameteri(GL2.GL_TEXTURE_CUBE_MAP, GL.GL_TEXTURE_MIN_FILTER,
				GL.GL_LINEAR);
		// Set near filtering mode

		return 13;
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
	}

	// ----- Implement methods declared in KeyListener -----

	@Override
	public void keyPressed(KeyEvent e) {
		if(!pressedKeys.contains(e.getKeyCode()))
			pressedKeys.add(e.getKeyCode());
		
		switch (e.getKeyCode()) {
		case VK_G:
			godmode=!godmode;
			if(!godmode){
				
				posX = pos.x + initPos.x;
				posZ = pos.z + initPos.y;
				posY = pos.y + initPos.z;
				zrangle = 0;
				//Make camera face Steve
				this.makeCameraFacePoint(pos);
				lookUpAngle = 0;
				snapPoint = new Vertex(0,-3,0);
			}
			else{
				snapPoint = new Vertex(pos.x,pos.y,pos.z);
				posX = 0 + snapPoint.x;
				posZ = zoom + snapPoint.z;
				posY = 0 + snapPoint.y;
			}
			
			break;
		case VK_F1:
			if (controlled) {

				frame.setCursor(Cursor.getDefaultCursor());

				Robot r = null;
				try {
					r = new Robot();
				} catch (AWTException e2) {
					
					e2.printStackTrace();
				}
				if (r != null) {

					r.mouseMove(frame.getWidth() / 2, frame.getHeight() / 2);

				}

				p_mpos = MouseInfo.getPointerInfo().getLocation();

				controlled = false;

			} else {
				BufferedImage cursorImg = new BufferedImage(16, 16,
						BufferedImage.TYPE_INT_ARGB);

				// Create a new blank cursor.
				Cursor blankCursor = Toolkit.getDefaultToolkit()
						.createCustomCursor(cursorImg, new Point(0, 0),
								"blank cursor");
				frame.setCursor(blankCursor);
				controlled = true;
			}

			break;
		case VK_ESCAPE:
			frame.dispose();
			System.exit(0);
			

			
			break;
		case VK_LEFT: // player turns left (scene rotates right)
			pos.x-=0.2;
			break;
		case VK_RIGHT: // player turns right (scene rotates left)
			pos.x+=0.2;
			break;
		case VK_UP:
			//steveAngle+=Math.PI/8;
			break;
		case VK_DOWN:
			//steveAngle-=Math.PI/8;
			break;
		/*
		case KeyEvent.VK_PAGE_UP:
			
			// player looks up, scene rotates in negative x-axis
			if (lookUpAngle >= lookUpMax)
				lookUpAngle -= lookUpIncrement;
			break;
		case KeyEvent.VK_PAGE_DOWN:
			// player looks down, scene rotates in positive x-axis
			if (lookUpAngle <= lookUpMin)
				lookUpAngle += lookUpIncrement;
			break;
		*/
		case VK_T: 
			
			break;
		case VK_U: 
			
			break;
		
		/*//Uncomment to enable camera panning
		case VK_W: // toggle blending mode

			up = true;

			break;

		case VK_S:

			down = true;
			break;
		case VK_D:
			right = true;
			break;
		case VK_A:
			left = true;
			break;
		*/
		case VK_B: // toggle blending mode
			blendingEnabled = !blendingEnabled;
			break;
		case VK_F:
			cOffset[0] += 0.1;
			break;

		
		
		case VK_V:
			pos.z += 0.1;
			break;
		
		
		
		
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		pressedKeys.remove((Integer)(e.getKeyCode()));
		switch (e.getKeyCode()) {
		case VK_W: // toggle blending mode

			up = false;

			break;

		case VK_S:
			down = false;
			break;
		
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
		switch (e.getKeyChar()) {

		}
	}

	class glimage {
		public byte[] dataInBytes;
		public ByteBuffer data;
		public int width;
		public int height;

		public glimage(String src) {
			BufferedImage bufferedImage = null;
			int w = 0;
			int h = 0;
			try {
				bufferedImage = ImageIO.read(SteveWorld.class
						.getResource(src));
				w = bufferedImage.getWidth();
				h = bufferedImage.getHeight();
			} catch (IOException e) {
				e.printStackTrace();
			}
			WritableRaster raster = Raster.createInterleavedRaster(
					DataBuffer.TYPE_BYTE, w, h, 4, null);
			ComponentColorModel colorModel = new ComponentColorModel(
					ColorSpace.getInstance(ColorSpace.CS_sRGB), new int[] { 8,
							8, 8, 8 }, true, false,
					ComponentColorModel.TRANSLUCENT, DataBuffer.TYPE_BYTE);
			BufferedImage dukeImg = new BufferedImage(colorModel, raster,
					false, null);

			Graphics2D g = dukeImg.createGraphics();
			g.drawImage(bufferedImage, null, null);
			DataBufferByte dukeBuf = (DataBufferByte) raster.getDataBuffer();
			byte[] dukeRGBA = dukeBuf.getData();
			ByteBuffer bb = ByteBuffer.wrap(dukeRGBA);
			bb.position(0);
			bb.mark();
			data = bb;

		}

	}


	@Override
	public void mouseClicked(MouseEvent e) {
		
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		//zrangle = 0;
		this.firstClick = e.getPoint();
		mouseHeld = true;
		
		this.clickState.snapPoint = new Vertex(snapPoint.x,snapPoint.y,snapPoint.z);
		
		this.clickState.lua = lookUpAngle;
		this.clickState.hy  = headingY;
		this.clickState.pos = new Vertex(posX, posY, posZ);
		this.clickState.dis = (float) Math.sqrt(Math.pow(posX - snapPoint.x,2) + Math.pow(posY - snapPoint.y,2) + Math.pow(posZ - snapPoint.z,2));
		this.clickState.zoom = zoom;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouseHeld = false;
		this.firstClick.x = 0;
		this.firstClick.y = 0;
		yrad = -1;
		xrad = -1;
		firstClicked = true;
		lastY = saveY;
		//System.out.println(lastY);
		this.clickState = new cameraStats();
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
		
	}
	
	

	class cameraStats{
		Vertex pos;
		Vertex snapPoint;
		float hy;
		float lua; 
		float dis;
		float zoom;
	}

	
}

