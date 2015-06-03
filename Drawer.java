import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Random;

import processing.core.*;

public class Drawer extends PApplet
	{
	public static final int WORLD_WIDTH_SCALE = 2;
	public static final int WORLD_HEIGHT_SCALE = 2;
	public static final int SCREEN_WIDTH = 640;
	public static final int SCREEN_HEIGHT = 480;
	public static final int TILE_WIDTH = 32;
	public static final int TILE_HEIGHT = 32;
	
	private WorldModel world;
	private WorldView view;
	
	public static final String IMAGE_LIST_FILE_NAME = "imagelist";
	public static final String WORLD_FILE = "gaia.sav";
	public static final String DEFAULT_IMAGE_NAME = "background_default";
	private static final int COLOR_MASK = 0xffffff;

	long next_time;
	Map<String, List<PImage>> i_store;

	
	public static void main(String[] args)
	{
		PApplet.main("Drawer");
	}
	
	public void setup()
	{
		int num_cols = SCREEN_WIDTH / TILE_WIDTH * WORLD_WIDTH_SCALE;
		int num_rows = SCREEN_HEIGHT / TILE_HEIGHT * WORLD_HEIGHT_SCALE;
		
		i_store = ImageStore.loadImages(this, IMAGE_LIST_FILE_NAME, TILE_WIDTH, TILE_HEIGHT);
		Background default_background = SaveLoad.createDefaultBackground(ImageStore.getImages(i_store, ImageStore.DEFAULT_IMAGE_NAME));

		world = new WorldModel(num_rows, num_cols, default_background);
		view = new WorldView(SCREEN_WIDTH/TILE_WIDTH, SCREEN_HEIGHT/TILE_HEIGHT, world, TILE_WIDTH, TILE_HEIGHT);
		SaveLoad.loadWorld(world, i_store, new File(WORLD_FILE));

		next_time = System.currentTimeMillis() + 100;
		
		size(SCREEN_WIDTH, SCREEN_HEIGHT);
		background(0);
		
	}
	
	public void draw()
	{
		long time = System.currentTimeMillis();
		
		if (time >= next_time)
		{
			System.out.println(world.getActionQueue().getSize());
			world.updateOnTime(time);
			//draw background
			for (int y = 0; y < view.getViewport().getHeight(); y++)
			{
				for (int x = 0; x < view.getViewport().getWidth(); x++)
				{
					Point w_pt = view.viewportToWorld(new Point(x, y));
					PImage img = world.getBackgroundImage(w_pt);
					image(img, x*view.getTileWidth(), y*view.getTileHeight());
				}
			}
			
			//draw entities
			for (Entity e : world.getEntities())
			{
				if (view.getViewport().pointInRectangle(e.getPosition()))
				{
					Point v_pt = view.worldToViewport(e.getPosition());
					image(e.getImage(), v_pt.getX() * view.getTileWidth(), v_pt.getY() * view.getTileHeight());
				}
			}
			
			//draw path if there's an entity under the mouse
			Point mouse_pt = mousePoint(TILE_WIDTH, TILE_HEIGHT, view.getViewport());
			if ((world.isOccupied(mouse_pt)) && (world.getTileOccupant(mouse_pt)) instanceof Mover)
			{
				Entity entity_under_mouse = world.getTileOccupant(mouse_pt);
				for (Point draw_pt : entity_under_mouse.getSearched())
				{
					fill(0);
					draw_pt = view.worldToViewport(draw_pt);
					rect(draw_pt.getX() * view.getTileWidth() + 14,
						 draw_pt.getY() * view.getTileHeight() + 14,
						 4, 4);
				}
				for (Point draw_pt : entity_under_mouse.getPath())
				{
					fill(255, 100, 100);
					draw_pt = view.worldToViewport(draw_pt);
					rect(draw_pt.getX() * view.getTileWidth() + 12,
						 draw_pt.getY() * view.getTileHeight() + 12,
						 8, 8);
				}
			}
			
			next_time = time + 100;
		}
	}
	
	public void keyPressed()
	{
		switch (key)
		{
			case 'w':
				if (view.getViewport().getTop() > 0)
				{
					view.updateView(0, -1);
				}
				break;
			case 'a':
				if (view.getViewport().getLeft() > 0)
				{
					view.updateView(-1, 0);
				}
				break;
			case 's':
				if (view.getViewport().getTop() + view.getViewport().getHeight() < SCREEN_HEIGHT/TILE_HEIGHT*WORLD_HEIGHT_SCALE)
				{
					view.updateView(0, 1);
				}
				break;
			case 'd':
				if (view.getViewport().getLeft() + view.getViewport().getWidth() < SCREEN_WIDTH/TILE_WIDTH*WORLD_WIDTH_SCALE)
				{
					view.updateView(1, 0);
				}
				break;

		}
	}
	
	public void mouseClicked()
	{
		System.out.println("click");
		Point mouse_pt = mousePoint(TILE_WIDTH, TILE_HEIGHT, view.getViewport());

		world.scheduleAction(world.effectRing(mouse_pt, 0, 3, i_store),
							 System.currentTimeMillis());
		
		Knight knight;
		if (world.isOccupied(mouse_pt))
		{
			knight = world.createKnight("knight", world.findOpenAround(mouse_pt, 1), i_store);
		}
		else
		{
			knight = world.createKnight("knight", mouse_pt, i_store);
		}
		
		knight.scheduleKnight(world, System.currentTimeMillis(), i_store);
		world.addEntity(knight);
		

	}
	
	public Point mousePoint(int tile_width, int tile_height, Rectangle viewport
			)
	{
		int xcoord = mouseX/tile_width + viewport.getLeft();
		int ycoord = mouseY/tile_height + viewport.getTop();
		return new Point(xcoord, ycoord);
	}
	
	public static PImage setAlpha(PImage img, int maskColor, int alpha)
	   {
	      int alphaValue = alpha << 24;
	      int nonAlpha = maskColor & COLOR_MASK;
	      img.format = PApplet.ARGB;
	      img.loadPixels();
	      for (int i = 0; i < img.pixels.length; i++)
	      {
	         if ((img.pixels[i] & COLOR_MASK) == nonAlpha)
	         {
	            img.pixels[i] = alphaValue | nonAlpha;
	         }
	      }
	      img.updatePixels();
	      return img;
	   }
}
