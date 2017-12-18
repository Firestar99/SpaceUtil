package space.util.gui.monofont.tableCreator;

import space.util.delegate.iterator.Iteratorable;
import space.util.gui.monofont.MonofontGuiElement;
import space.util.indexmap.IndexMap;
import space.util.indexmap.IndexMap.IndexMapEntry;
import space.util.indexmap.axis.IndexAxisMapInt;
import space.util.math.MathUtils;
import space.util.string.CharSequence2D;
import space.util.string.builder.CharBufferBuilder2D;

/**
 * makes a table with full borders out of single characters
 */
public class MonofontColumnCreatorArray implements MonofontColumnCreator {
	
	@SuppressWarnings("unused")
	public static MonofontColumnCreatorArray INSTANCE = new MonofontColumnCreatorArray('[', ']', "|", '-');
	
	public char fillChar;
	public char leftBound;
	public char rightBound;
	public String separator;
	public char line;
	
	public MonofontColumnCreatorArray(char leftBound, char rightBound, String separator, char line) {
		this(' ', leftBound, rightBound, separator, line);
	}
	
	public MonofontColumnCreatorArray(char fillChar, char leftBound, char rightBound, String separator, char line) {
		this.fillChar = fillChar;
		this.leftBound = leftBound;
		this.rightBound = rightBound;
		this.separator = separator;
		this.line = line;
	}
	
	@Override
	public CharSequence2D makeTable(String className, MonofontGuiElement guiElement, ColumnDirection direction, IndexMap<CharSequence2D> elements) {
		//axis size
		IndexAxisMapInt axis = new IndexAxisMapInt();
		Iteratorable<IndexMapEntry<CharSequence2D>> iter = elements.tableIterator();
		for (IndexMapEntry<CharSequence2D> elem : iter) {
			int index = elem.getIndex();
			CharSequence2D value = elem.getValue();
			axis.put(new int[] {0, index}, new int[] {value.maxLength(), value.height()});
		}
		
		//sizes
		int separatorLength = separator.length();
		int maxY = axis.getIndex(0, 1);
		int maxIndexX = axis.size(1);
		int maxX = MathUtils.max(axis.getIndex(1, maxIndexX) + maxIndexX * separatorLength + 1, className.length() + 1);
		
		//buffer creation
		CharBufferBuilder2D<?> buffer = new CharBufferBuilder2D<>().setNoFillMissingSpaces();
		buffer.startEdit();
		
		//first line
		buffer.setY(0).setX(0).append(leftBound).append(className).fill(maxX - className.length(), line).append(rightBound);
		
		//entries
		iter = elements.tableIterator();
		for (IndexMapEntry<CharSequence2D> elem : iter) {
			int index = elem.getIndex();
			int startx = axis.getIndex(1, index) + index * separatorLength + 1;
			int untilx = axis.getIndex(1, index + 1) + (index + 1) * separatorLength + 1;
			
			buffer.setY(1).setX(startx).append(elem.getValue(), maxY, untilx, fillChar);
		}
		
		//sides
		fillDown(buffer, 0, 0, maxY, leftBound);
		fillDown(buffer, maxX, 0, maxY, rightBound);
		
		//separator
		for (int i = 0; i < maxIndexX; i++)
			fillDown(buffer, axis.getIndex(1, i), 0, maxY, separator);
		
		//last line
		buffer.setY(maxY).setX(0).append(leftBound).fill(0, line).append(rightBound);
		return buffer.toString2D();
	}
	
	@SuppressWarnings("SameParameterValue")
	protected static void fillDown(CharBufferBuilder2D<?> buffer, int x, int fromy, int toy, char c) {
		for (int y = fromy; y < toy; y++) {
			buffer.setY(y);
			buffer.setX(x);
			buffer.append(c);
		}
	}
	
	@SuppressWarnings("SameParameterValue")
	protected static void fillDown(CharBufferBuilder2D<?> buffer, int x, int fromy, int toy, String c) {
		for (int y = fromy; y < toy; y++) {
			buffer.setY(y);
			buffer.setX(x);
			buffer.append(c);
		}
	}
}