package ru.fanter.colorlife;

import java.awt.Color;

public enum FigureColor {
	RED(Color.RED) {
		public FigureColor next() {
			return FigureColor.GREEN;
		}
	}, 
	GREEN(Color.GREEN){
		public FigureColor next() {
			return FigureColor.BLUE;
		}
	}, 
	BLUE(Color.BLUE){
		public FigureColor next() {
			return FigureColor.RED;
		}
	},
	CYAN(Color.CYAN){
		public FigureColor next() {
			return FigureColor.CYAN;
		}
	},
	YELLOW(Color.YELLOW){
		public FigureColor next() {
			return FigureColor.YELLOW;
		}
	},
	MAGENTA(Color.MAGENTA){
		public FigureColor next() {
			return FigureColor.MAGENTA;
		}
	};

	private final Color color;

	FigureColor(Color color) {
		this.color = color;
	}

	public abstract FigureColor next();

	public Color getColor() {
		return color;
	}
}