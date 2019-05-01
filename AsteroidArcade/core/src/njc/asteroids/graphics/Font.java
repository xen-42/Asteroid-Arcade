package njc.asteroids.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Font {
	private static String chars = ""
			+ "ABCDEFGHIJ"
			+ "KLMNOPQRST"
			+ "UVWXYZ    "
			+ "abcdefghij"
			+ "klmnopqrst"
			+ "uvwxyz    "
			+ "0123456789"
			+ ".:?";
			//+ ".,:\"!?+-<>"
			//+ "()%/$#=*  ";
	public static final int SIZE = 16;
	
	private static final int LINE_LENGTH = 10;
	private Texture texture, coloredTexture;
	
	public static Color[] yellow = {
			Color.BLACK,
			Color.ORANGE,
			Color.GOLD,
			Color.YELLOW
	};
	
	public static Color[] white = {
			Color.BLACK,
			Color.GRAY,
			Color.LIGHT_GRAY,
			Color.WHITE
	};
	
	public Font(Color[] colors) {
		this.texture = new Texture("gui/betterfont.png");
		setColor(this, colors);
	}
	
	public static void setColor(Font font, Color[] colors) {
		TextureData textureData = font.texture.getTextureData();
		textureData.prepare();
		
		Pixmap pixmap = textureData.consumePixmap();
		for(int y = 0; y < pixmap.getHeight(); y++) {
			for(int x = 0; x < pixmap.getWidth(); x++) {
				Color color = new Color();
				Color.rgba8888ToColor(color, pixmap.getPixel(x, y));
				
				Color newColor = color;
				if(color.equals(Color.BLACK)) newColor = colors[0];
				if(color.equals(new Color(127f / 255f, 127f / 255f, 127 / 255f, 1f))) newColor = colors[1];
				if(color.equals(new Color(175f / 255f, 175f / 255f, 175f / 255f, 1f))) newColor = colors[2];
				if(color.equals(Color.WHITE)) newColor = colors[3];
				
				pixmap.setColor(newColor);
				pixmap.fillRectangle(x, y, 1, 1);
			}
		}
		
		font.coloredTexture = new Texture(pixmap);
		textureData.disposePixmap();
		pixmap.dispose();
	}
	
	public void write(SpriteBatch batch, String msg, float x, float y, float scale) {
		msg = msg.toUpperCase();
		for(int i = 0; i < msg.length(); i++) {
			int ind = chars.indexOf(msg.charAt(i));
			int row = ind / LINE_LENGTH; int column = ind % LINE_LENGTH;
			TextureRegion tr = new TextureRegion(coloredTexture, column * SIZE, row * SIZE, SIZE, SIZE);
			batch.draw(tr, (i * SIZE * scale) + x, y, SIZE * scale, SIZE * scale);
		}
	}
	
	public void dispose() {
		this.texture.dispose();
	}
}
