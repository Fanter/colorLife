package ru.fanter.colorlife;

public enum Direction {
	UP() {
		public Direction next() {
			return RIGHT;
		}
	},
	DOWN{
		public Direction next() {
			return LEFT;
		}
	}, 
	RIGHT{
		public Direction next() {
			return DOWN;
		}
	}, 
	LEFT{
		public Direction next() {
			return UP;
		}
	}, 
	NONE{
		public Direction next() {
			return NONE;
		}
	},
	END{
		public Direction next() {
			return END;
		}
	};

	public abstract Direction next();
}