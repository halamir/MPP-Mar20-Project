package business;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;

public class SystemController implements ControllerInterface {
	public static Auth currentAuth = null;
	private DataAccess da = new DataAccessFacade();

	public void login(String id, String password) throws LoginException {
		HashMap<String, User> map = da.readUserMap();
		if (!map.containsKey(id)) {
			throw new LoginException("ID " + id + " not found");
		}
		String passwordFound = map.get(id).getPassword();
		if (!passwordFound.equals(password)) {
			throw new LoginException("Password incorrect");
		}
		currentAuth = map.get(id).getAuthorization();
	}

	@Override
	public List<String> allMemberIds() {
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readMemberMap().keySet());
		return retval;
	}

	@Override
	public List<String> allBookIds() {
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readBooksMap().keySet());
		return retval;
	}

	///// Added by Hatem /////
	public void checkoutABook(Book book, String memberId, Date checkoutDate, Date dueDate)
			throws LibrarySystemException {
		BookCopy bookCopy = book.getNextAvailableCopy();
		if (null == bookCopy)
			throw new LibrarySystemException("Book with ISBN " + book.getIsbn() + " has no available copies!");

		bookCopy.changeAvailability();
		book.updateCopies(bookCopy);
		LibraryMember member = da.readMemberMap().get(memberId);
		CheckoutEntry entry = new CheckoutEntry(member, bookCopy, checkoutDate, dueDate);
		member.getCheckoutRecord().addChecoutEntries(entry);
		da.saveMember(member);
		da.saveBook(book);
	}

	public void saveLibraryMemebr(String memberId, String fName, String lName, Address address, String tel) {
		HashMap<String, LibraryMember> members = da.readMemberMap();
		LibraryMember member;
		if (members.keySet().contains(memberId))
			member = members.get(memberId);
		else
			member = new LibraryMember(memberId, fName, lName, tel, address);
		da.saveMember(member);
	}

	public void addBookCopies(Book book, int numCopies) {
		book.addCopies(numCopies);
		da.saveBook(book);
	}

	public void saveNewdBook(String isbn, String title, List<Author> authors, int maxCkeckoutLength)
			throws LibrarySystemException {
		if (allBookIds().contains(isbn)) {
			throw new LibrarySystemException("Book with ISBN " + isbn + " already exists!");
		}
		Book book = new Book(isbn, title, maxCkeckoutLength, authors);
		da.saveBook(book);
	}

	public void updateBook(Book book) throws LibrarySystemException {
		if (!allBookIds().contains(book.getIsbn())) {
			throw new LibrarySystemException("Book with ISBN " + book.getIsbn() + " doesn't exist!");
		}
		da.saveBook(book);
	}

	public List<LibraryMember> allMembers() {
		return new ArrayList<LibraryMember>(da.readMemberMap().values());
	}

	public List<Book> allBooks() {
		return new ArrayList<Book>(da.readBooksMap().values());
	}

	public List<CheckoutEntry> allCheckoutEntries() {
		List<CheckoutEntry> entries = new ArrayList<CheckoutEntry>();
		List<LibraryMember> members = allMembers();
		if (null == members)
			return entries;
		for (LibraryMember member : members)
			if (null != member && null != member.getCheckoutRecord()
					&& null != member.getCheckoutRecord().getCheckoutEntries())
				entries.addAll(member.getCheckoutRecord().getCheckoutEntries());
		return entries;
	}
}
