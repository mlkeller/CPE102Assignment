import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


import processing.core.*;


public abstract class ImageStore
{
	public static final String DEFAULT_IMAGE_NAME = "background_default";
	public static final RGBColor DEFAULT_IMAGE_COLOR = new RGBColor(128, 128, 128);
	
 //create_default_image - we may have to add this back in later
	
	public static Map<String, List<PImage>> loadImages(PApplet papp, String filename, int tile_width, int tile_height)
	{
		Map<String, List<PImage>> images = new HashMap<String, List<PImage>>();
		try
		{
			Scanner in = new Scanner(new FileInputStream(new File("imagelist.txt")));
			while(in.hasNextLine())
			{
				processImageLine(papp, images, in.nextLine());
			}
			in.close();
		}
		catch (FileNotFoundException e)
		{
			System.out.println("File imagelist.txt may be missing.\n\n" + e);
			System.exit(0);
		}
		return images;
	}
	
	public static void processImageLine(PApplet papp, Map<String, List<PImage>> images, String line)
	{
		String[] attrs = line.split("\\s");
		if (attrs.length >= 2)
		{
			String key = attrs[0];

			PImage img = papp.loadImage(attrs[1]);
					
			List<PImage> imgs = getImagesInternal(images, key);
			imgs = getImagesInternal(images, key);
			imgs.add(img);
			images.put(key, imgs);
				
			if (attrs.length == 6)
			{
				int r = Integer.parseInt(attrs[2]);
				int g = Integer.parseInt(attrs[3]);
				int b = Integer.parseInt(attrs[4]);
				int a = Integer.parseInt(attrs[5]);
				img = Drawer.setAlpha(img, papp.color(r, g, b), a);
				//FIND JAVA EQUIVALENT
			}
		}
	}

	public static List<PImage> getImagesInternal(Map<String, List<PImage>> images, String key)
	{
		if (images.containsKey(key))
		{
			return images.get(key);
		}
		else
		{
			return new ArrayList<PImage>();
		}
	}
	
	public static List<PImage> getImages(Map<String, List<PImage>> images, String key)
	{
		if (images.containsKey(key))
		{
			return images.get(key);
		}
		else
		{
			return images.get(DEFAULT_IMAGE_NAME);
		}
	}
}
