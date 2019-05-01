package njc.asteroids.object;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import njc.asteroids.graphics.Font;

public class TextObject extends GameObject {
	private String msg;
	private float scale;
	private Font font;
	public TextObject(String msg, float scale, Font font) {
		this.scale = scale;
		this.font = font;

		this.setMsg(msg);
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
		this.width = Font.SIZE * msg.length() * scale;
		this.height = Font.SIZE * scale;
	}

	@Override
	public void render(SpriteBatch batch) {
		if(!this.getVisibility()) return;
		font.write(batch, msg, this.getPosition().x, this.getPosition().y, scale);
	}
}
