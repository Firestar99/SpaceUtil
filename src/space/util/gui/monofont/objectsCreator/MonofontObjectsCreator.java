package space.util.gui.monofont.objectsCreator;

import space.util.gui.monofont.elements.tsh.MonofontObjects;
import space.util.string.CharSequence2D;

public interface MonofontObjectsCreator {
	
	CharSequence2D makeTable(MonofontObjects tsh);
}