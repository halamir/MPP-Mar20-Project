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

	public void login(String id, String password) throws LoginException {
		DataAccess da = new DataAccessFacade();
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
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readMemberMap().keySet());
		return retval;
	}

	@Override
	public List<String> allBookIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readBooksMap().keySet());
		return retval;
	}

	///// Added by Hatem /////
	public CheckoutRecord checkoutABook(Book book, String userId, Date checkoutDate, Date dueDate) {
		BookCopy bookCopy = book.getNextAvailableCopy();
		if (null == bookCopy)
			return null;
		DataAccess da = new DataAccessFacade();
		HashMap<String, LibraryMember> members = da.readMemberMap();
		LibraryMember member = members.get(userId);
		CheckoutEntry entry = new CheckoutEntry(member, bookCopy, checkoutDate, dueDate);
		CheckoutRecord record = member.getCheckoutRecord();
		record.addChecoutEntries(entry);
		return record;
	}
}
