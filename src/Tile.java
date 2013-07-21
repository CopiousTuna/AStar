import java.awt.*;

public class Tile implements Comparable<Tile> {

	private Tile prev = null;	// Previous tile that was moved from. Used to keep track of path
	private final int x, y, width, height;
	private int f, g, h;		// F: Total cost. G: Actual cost. H: Estimated cost to reach end
	private boolean isObstacle = false;
	private final static Color defaultOutline = new Color(204, 0, 0), defaultFill = new Color(240, 49, 49),
			obstacleOutline = new Color(153, 51, 204), obstacleFill = new Color(186, 117, 220),
			selectedOutline = new Color(255, 138, 0), selectedFill = new Color(255, 174, 24);
	private Color outline = defaultOutline, fill = defaultFill;
	private final Font font = new Font("Consolas", Font.PLAIN, 11);

	public Tile(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		// Initialize with a very high value so an initial path can be established
		this.f = this.g = this.h = Integer.MAX_VALUE;
	}
	
	public void draw(Graphics g, boolean isSelected) {
		g.setColor(isSelected ? selectedOutline : outline);
		g.drawRect(x, y, width, height);
		g.setColor(isSelected ? selectedFill : fill);
		g.fillRect(x + 1, y + 1, width - 1, height - 1);
		g.setColor(Color.WHITE);
		g.setFont(font);
		if(this.f != Integer.MAX_VALUE) {
			g.drawString(("F: " + f), x + 20, y + 20);
			g.drawString(("G: " + this.g), x + 20, y + 35);
			g.drawString(("H: " + h), x + 20, y + 50);
		}
	}
	
	public void print() {
		System.out.println("(" + x / 60 + ", " + y / 60 + ")" +
				" F: " + f + " G: " + g + " H: " + h);
		if(prev != null) {
			System.out.print("\tPrev: ");
			prev.print();
		}	
	}
	
	/**
	 * Resets all of the tile values for calculating a new path
	 */
	public void reset() {
		f = g = h = Integer.MAX_VALUE;
		prev = null;
		if(!isObstacle) {
			outline = defaultOutline;
			fill = defaultFill;
		}
	}
	
	/**
	 * Returns the tile's distance from the end tile using Manhattan Distance
	 */
	public int getDistance(Tile tile) {
		return Math.abs(x - tile.x) / 60 + Math.abs(y - (tile.y)) / 60;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getF() {
		return f;
	}

	public void setF(int f) {
		this.f = f;
	}
	
	public void setF(Tile tile) {
		h = getDistance(tile);	// Calculate estimated distance to the end tile
		f = g + h;	// Set F equal to the sum of steps taken (G) and remaining steps estimated (H)
	}

	public int getG() {
		return g;
	}

	public void setG(int g) {
		this.g = g;
	}

	public int getH() {
		return h;
	}
	
	public void setH(int h) {
		this.h = h;
	}

	public Tile getPrev() {
		return prev;
	}

	public void setPrev(Tile prev) {
		this.prev = prev;
	}
	
	public boolean isObstacle() {
		return isObstacle;
	}

	/**
	 * Toggles a tile as an impassable obstacle
	 */
	public void toggleObstacle() {
		isObstacle = !isObstacle;
		if(isObstacle) {
			outline = obstacleOutline;
			fill = obstacleFill;
		} else {
			outline = defaultOutline;
			fill = defaultFill;
		}
	}
	
	public void setOutline(Color outline) {
		this.outline = outline;
	}

	public void setFill(Color fill) {
		this.fill = fill;
	}
	
	@Override
	// Compares F values to keep tiles with lowest F at the top of the queue
	public int compareTo(Tile tile) {
		if(f < tile.getF()) {
			return -1;
		} else if(f == tile.getF()) {
			return 0;
		} else {
			return 1;
		}
	}

}