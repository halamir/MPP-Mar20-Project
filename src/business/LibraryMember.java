package business;

import java.io.Serializable;

final public class LibraryMember extends Person implements Serializable {
	private static final long serialVersionUID = -2226197306790714013L;
	private String memberId;
	private CheckoutRecord checkoutRecord = new CheckoutRecord();

	public static String generateLibraryMemberId() {
		return "" + System.currentTimeMillis();
	}

	public LibraryMember(String memberId, String fname, String lname, String tel, Address add) {
		super(fname, lname, tel, add);
		this.memberId = memberId;
	}

	public String getMemberId() {
		return memberId;
	}

	public CheckoutRecord getCheckoutRecord() {
		if(checkoutRecord == null)
			checkoutRecord = new CheckoutRecord();
		return checkoutRecord;
	}

	@Override
	public String toString() {
		return "Member Info: " + "ID: " + memberId + ", name: " + getFirstName() + " " + getLastName() + ", "
				+ getTelephone() + " " + getAddress();
	}
}
