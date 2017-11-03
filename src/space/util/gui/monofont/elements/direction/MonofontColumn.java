package space.util.gui.monofont.elements.direction;

import space.util.gui.elements.direction.GuiColumn;
import space.util.gui.monofont.MonofontGuiElement;
import space.util.gui.monofont.tableCreator.MonofontTableCreatorIncludingTable;
import space.util.string.CharSequence2D;

public class MonofontColumn extends MonofontElementList implements GuiColumn<MonofontGuiElement> {
	
	static {
		MonofontTableCreatorIncludingTable.toIncludeList.add(MonofontColumn.class);
	}
	
	@Override
	public CharSequence2D rebuild0() {
		return style.makeTable(this, buildList());
	}
}