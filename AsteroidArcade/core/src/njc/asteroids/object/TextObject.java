package njc.asteroids.object;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TextObject extends GameObject {
	private String msg;
	private float scale;
	private BitmapFont font;
	private GlyphLayout layout;
	public TextObject(String msg, float scale, BitmapFont font) {
		this.scale = scale;
		this.font = font;

		this.setMsg(msg);
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
		
		this.layout = new GlyphLayout();
		this.font.getData().setScale(this.scale);
		layout.setText(this.font, msg);
		
		this.width = this.layout.width;
		this.height = this.layout.height;
	}

	@Override
	public void render(SpriteBatch batch) {
		if(!this.getVisibility()) return;
		
		this.font.draw(batch, layout, this.getPosition().x, this.getPosition().y);
	}
}
