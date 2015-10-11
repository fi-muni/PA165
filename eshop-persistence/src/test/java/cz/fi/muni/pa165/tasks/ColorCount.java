package cz.fi.muni.pa165.tasks;

import cz.fi.muni.pa165.dto.Color;

public class ColorCount {
	private Color color;
	private Long count;
	
	public ColorCount(Color color, Long count) {
		super();
		this.color = color;
		this.count = count;
	}

	public Color getColor() {
		return color;
	}

	public Long getCount() {
		return count;
	}
	
	
}
