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
	public CheckoutRecord checkoutABook(Book book, String userId, Date checkoutDate, Date dueDate) {
		BookCopy bookCopy = book.getNextAvailableCopy();
		if (null == bookCopy)
			return null;
		HashMap<String, LibraryMember> members = da.readMemberMap();
		LibraryMember member = members.get(userId);
		CheckoutEntry entry = new CheckoutEntry(member, bookCopy, checkoutDate, dueDate);
		CheckoutRecord record = member.getCheckoutRecord();
		record.addChecoutEntries(entry);
		return record;
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
}
