package ru.fedor.conway.life.stream.client.reactor.flow.book;

public final class BookReaderEng extends AbstractBookReader {
	private final static String BOOK_PATH_ENG_DEFAULT = "book/war-and-peace-eng.txt";

	public BookReaderEng(String bookPath) {
		super(bookPath);
	}

	public static BookReaderEng newInstance(String bookPath) {
		var result = new BookReaderEng(bookPath);
		result.openBook();
		return result;
	}

	public static BookReaderEng newInstance() {
		return newInstance(BOOK_PATH_ENG_DEFAULT);
	}

	@Override
	protected String getNonLetterRegExp() {
		return "[^a-z]";
	}
}
