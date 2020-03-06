package business;

import java.io.Serializable;
import java.util.Date;

public class CheckoutEntry implements Serializable {
	private static final long serialVersionUID = -4014199903079420412L;
	private LibraryMember libraryMember;
	private BookCopy bookCopy;
	private Date checkoutDate;
	private Date dueDate;

	CheckoutEntry(LibraryMember libraryMember, BookCopy bookCopy, Date checkoutDate, Date dueDate) {
		this.libraryMember = libraryMember;
		this.bookCopy = bookCopy;
		this.checkoutDate = checkoutDate;
		this.dueDate = dueDate;
	}

	public LibraryMember getLibraryMember() {
		return libraryMember;
	}

	public BookCopy getBookCopy() {
		return bookCopy;
	}

	public Date getCheckoutDate() {
		return checkoutDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CheckoutEntry) {
			CheckoutEntry checkoutEntry = (CheckoutEntry) obj;
			if (this.libraryMember.getMemberId() == checkoutEntry.getLibraryMember().getMemberId()
					&& this.bookCopy.getBook().getIsbn() == checkoutEntry.getBookCopy().getBook().getIsbn()
					&& this.bookCopy.getCopyNum() == checkoutEntry.bookCopy.getCopyNum()
					&& this.checkoutDate.equals(checkoutEntry.getCheckoutDate())
					&& this.dueDate.equals(checkoutEntry.getDueDate()))
				return true;
		}
		return false;
	}
}
