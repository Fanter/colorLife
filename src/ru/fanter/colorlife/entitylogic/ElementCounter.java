package ru.fanter.colorlife.entitylogic;

import ru.fanter.colorlife.*;

public class ElementCounter {
	private int splitterCount;
	private int colorChangerCount;
	private int shapeChangerCount;
	private int recyclerCount;
	private int detectorColorCount;
	private int detectorShapeCount;
	private int mergerCount;
	
	//number of elements placed on element panel
	public void setNumberOfElements() {
		splitterCount = 5;
		colorChangerCount = 2;
		shapeChangerCount = 1;
		recyclerCount = 1;
		detectorColorCount = 1;
		detectorShapeCount= 1;
		mergerCount = 1;
	}

	public int getElementCount(ElementType elementType) {
		switch(elementType) {
			case SPLITTER:
				return splitterCount;
			case COLOR_CHANGER:
				return colorChangerCount;
			case SHAPE_CHANGER:
				return shapeChangerCount;
			case RECYCLER:
				return recyclerCount;
			case DETECTOR_COLOR:
				return detectorColorCount;
			case DETECTOR_SHAPE:
				return detectorShapeCount;
			case MERGER:
				return mergerCount;
			case ARROW_UP: case ARROW_DOWN: case ARROW_RIGHT:
			case ARROW_LEFT:
			    return 2; //number of arrows infinite
			default:
			    break;
		}
		return -1;
	}

	public void decElementCount(ElementType elementType) {
		switch(elementType) {
			case SPLITTER:
				splitterCount--;
				break;
			case COLOR_CHANGER:
				colorChangerCount--;
				break;
			case SHAPE_CHANGER:
				shapeChangerCount--;
				break;
			case RECYCLER:
				recyclerCount--;
				break;
			case DETECTOR_COLOR:
				detectorColorCount--;
				break;
			case DETECTOR_SHAPE:
				detectorShapeCount--;
				break;
			case MERGER:
				mergerCount--;
				break;
			default:
				break;
		}
	}

	public void incElementCount(ElementType elementType) {
		switch(elementType) {
			case SPLITTER:
				splitterCount++;
				break;
			case COLOR_CHANGER:
				colorChangerCount++;
				break;
			case SHAPE_CHANGER:
				shapeChangerCount++;
				break;
			case RECYCLER:
				recyclerCount++;
				break;
			case DETECTOR_COLOR:
				detectorColorCount++;
				break;
			case DETECTOR_SHAPE:
				detectorShapeCount++;
				break;
			case MERGER:
				mergerCount++;
				break;
			default:
				break;
		}
	}
}
