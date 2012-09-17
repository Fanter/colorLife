package ru.fanter.colorlife;

public enum FigureShape {
	CIRCLE {
		public FigureShape next() {
			return FigureShape.SQUARE;
		}
	},
	SQUARE {
		public FigureShape next() {
			return FigureShape.ROUND_SQUARE;
		}
	}, 
	ROUND_SQUARE {
		public FigureShape next() {
			return FigureShape.CIRCLE;
		}
	};

	public abstract FigureShape next();
}