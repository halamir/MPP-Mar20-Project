package business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class CheckoutRecord implements Serializable{

	private static final long serialVersionUID = 1989601000494385881L;
	private List<CheckoutEntry> checkoutEntries;

	CheckoutRecord() {
		checkoutEntries = new ArrayList<CheckoutEntry>();
	}

	public void addChecoutEntries(CheckoutEntry checkoutEntry) {
		checkoutEntries.add(checkoutEntry);
	}

	public void changeCheckoutDueDate(CheckoutEntry checkoutEntry, Date dueDate) {
		Iterator<CheckoutEntry> it = checkoutEntries.iterator();
		while (it.hasNext()) {
			CheckoutEntry ent = (CheckoutEntry) it.next();
			if (ent.equals(checkoutEntry))
				ent.setDueDate(dueDate);
		}
	}
}
