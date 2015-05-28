import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import processing.core.*;

public abstract class SaveLoad
{
	private static final int PROPERTY_KEY = 0;
	
	private static final String BGND_KEY = "background";
	private static final int BGND_NUM_PROPERTIES = 4;
	private static final int BGND_NAME = 1;
	private static final int BGND_COL = 2;
	private static final int BGND_ROW = 3;
	
	private static final String MINER_KEY = "miner";
	private static final int MINER_NUM_PROPERTIES = 7;
	private static final int MINER_NAME = 1;
	private static final int MINER_LIMIT = 4;
	private static final int MINER_COL = 2;
	private static final int MINER_ROW = 3;
	private static final int MINER_RATE = 5;
	private static final int MINER_ANIMATION_RATE = 6;
	
	private static final String OBSTACLE_KEY = "obstacle";
	private static final int OBSTACLE_NUM_PROPERTIES = 4;
	private static final int OBSTACLE_NAME = 1;
	private static final int OBSTACLE_COL = 2;
	private static final int OBSTACLE_ROW = 3;
	
	private static final String ORE_KEY = "ore";
	private static final int ORE_NUM_PROPERTIES = 5;
	private static final int ORE_NAME = 1;
	private static final int ORE_COL = 2;
	private static final int ORE_ROW = 3;
	private static final int ORE_RATE = 4;

	private static final String SMITH_KEY = "blacksmith";
	private static final int SMITH_NUM_PROPERTIES = 7;
	private static final int SMITH_NAME = 1;
	private static final int SMITH_LIMIT = 4;
	private static final int SMITH_COL = 2;
	private static final int SMITH_ROW = 3;
	private static final int SMITH_RATE = 5;
	private static final int SMITH_REACH = 6;  //may not be necessary
	
	private static final String VEIN_KEY = "vein";
	private static final int VEIN_NUM_PROPERTIES = 6;
	private static final int VEIN_NAME = 1;
	private static final int VEIN_COL = 2;
	private static final int VEIN_ROW = 3;
	private static final int VEIN_RATE = 4;
	private static final int VEIN_REACH = 5;
	
	//there's no saving functionality
	
	public static void loadWorld(WorldModel world,  Map<String, List<PImage>> i_store, File filename)
	{
		System.out.println("loading the world");
		Scanner file;
		try
		{
			file = new Scanner(filename);
			SaveLoad.loadWorldInternal(world, i_store, file, true);
			file.close();
		} 
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void loadWorldInternal(WorldModel world, Map<String, List<PImage>> images, Scanner file, Boolean run) //run default false
	{
		while (file.hasNextLine())
		{
			String[] properties = file.nextLine().split("\\s"); //look at this in python again later
			if (properties[PROPERTY_KEY].equals(BGND_KEY))
			{
				SaveLoad.addBackground(world, properties, images);
			}
			else
			{
				SaveLoad.addEntity(world, properties, images, run);
			}
		}
	}
	
	public static void addBackground(WorldModel world, String[] properties, Map<String, List<PImage>> i_store)
	{
		if (properties.length >= BGND_NUM_PROPERTIES)
		{
			Point pt = new Point(Integer.parseInt(properties[BGND_COL]),
								 Integer.parseInt(properties[BGND_ROW]));
			String name = properties[BGND_NAME];
			world.setTileBackground(pt, new Background(name, ImageStore.getImages(i_store, name)));
		}
	}
	
	public static Background createDefaultBackground(List<PImage> img)
	{
		return new Background(ImageStore.DEFAULT_IMAGE_NAME, img);
	}
	
	public static void addEntity(WorldModel world, String[] properties, Map<String, List<PImage>> i_store, Boolean run)
	{
		Entity new_entity = SaveLoad.createFromProperties(properties, i_store, world, run);
		if (new_entity != null)
		{
			world.addEntity(new_entity);
		}
	}
	
	public static Entity createFromProperties(String[] properties, Map<String, List<PImage>> i_store, WorldModel world, boolean run)
	{
		String key = properties[PROPERTY_KEY];
		if (key.equals(MINER_KEY))
		{
			MinerNotFull new_miner = createMiner(properties, i_store);
			if (run)
			{
				new_miner.scheduleMiner(world, System.currentTimeMillis(), i_store);
			}
			return new_miner;
		}
		else if (key.equals(VEIN_KEY))
		{
			Vein new_vein = createVein(properties, i_store);
			if (run)
			{
				new_vein.scheduleVein(world, System.currentTimeMillis(), i_store);
			}
			return new_vein;
		}
		else if (key.equals(ORE_KEY))
		{
			Ore new_ore = createOre(properties, i_store);
			if (run)
			{
				new_ore.scheduleOre(world, System.currentTimeMillis(), i_store);
			}
			return new_ore;
		}
		else if (key.equals(SMITH_KEY))
		{
			return createBlacksmith(properties, i_store);
		}
		else if (key.equals(OBSTACLE_KEY))
		{
			return createObstacle(properties, i_store);
		}
		else
		{
			return null;
		}
	}
	
	public static MinerNotFull createMiner(String[] properties, Map<String, List<PImage>> i_store)
	{
		if (properties.length == MINER_NUM_PROPERTIES)
		{
			return new MinerNotFull(properties[MINER_NAME],
					                new Point(Integer.parseInt(properties[MINER_COL]),
					                		  Integer.parseInt(properties[MINER_ROW])),
					                ImageStore.getImages(i_store, properties[PROPERTY_KEY]),
					                Integer.parseInt(properties[MINER_ANIMATION_RATE]),
					                Integer.parseInt(properties[MINER_RATE]),
					                Integer.parseInt(properties[MINER_LIMIT]));
		}
		else
		{
			return null;
		}
	}
	
	public static Vein createVein(String[] properties, Map<String, List<PImage>> i_store)
	{
		if (properties.length == VEIN_NUM_PROPERTIES)
		{
			return new Vein(properties[VEIN_NAME],
					        new Point(Integer.parseInt(properties[VEIN_COL]),
					                  Integer.parseInt(properties[VEIN_ROW])),
					        ImageStore.getImages(i_store, properties[PROPERTY_KEY]),
					        Integer.parseInt(properties[VEIN_RATE]),
					        Integer.parseInt(properties[VEIN_REACH]));
		}
		else
		{
			return null;
		}
	}
	
	public static Ore createOre(String[] properties, Map<String, List<PImage>> i_store)
	{
		if (properties.length == ORE_NUM_PROPERTIES)
		{
			return new Ore(properties[ORE_NAME],
					       new Point(Integer.parseInt(properties[ORE_COL]),
					                 Integer.parseInt(properties[ORE_ROW])),
					       ImageStore.getImages(i_store, properties[PROPERTY_KEY]),
					       Integer.parseInt(properties[ORE_RATE]));
		}
		else
		{
			return null;
		}
	}
	
	public static Blacksmith createBlacksmith(String[] properties, Map<String, List<PImage>> i_store)
	{
		if (properties.length == SMITH_NUM_PROPERTIES)
		{
			return new Blacksmith(properties[SMITH_NAME],
					              new Point(Integer.parseInt(properties[SMITH_COL]),
					                        Integer.parseInt(properties[SMITH_ROW])),
					              ImageStore.getImages(i_store, properties[PROPERTY_KEY]));
		}
		else
		{
			return null;
		}
	}
	
	public static Obstacle createObstacle(String[] properties, Map<String, List<PImage>> i_store)
	{
		if (properties.length == OBSTACLE_NUM_PROPERTIES)
		{
			return new Obstacle(properties[OBSTACLE_NAME],
					              new Point(Integer.parseInt(properties[OBSTACLE_COL]),
					                        Integer.parseInt(properties[OBSTACLE_ROW])),
					              ImageStore.getImages(i_store, properties[PROPERTY_KEY]));
		}
		else
		{
			return null;
		}
	}
}
