package ru.fedor.conway.life.stream.client.reactor.flow.book;

public final  class BookReaderRus extends AbstractBookReader {
	private final static String BOOK_PATH_RUS_DEFAULT = "book/war-and-peace-rus.txt";

	public BookReaderRus(String bookPath) {
		super(bookPath);
	}

	@Override
	protected String getNonLetterRegExp() {
		return "[^а-я]";
	}

	public static BookReaderRus newInstance(String bookPath) {
		var result = new BookReaderRus(bookPath);
		result.openBook();
		return result;
	}


	public static BookReaderRus newInstance() {
		return newInstance(BOOK_PATH_RUS_DEFAULT);
	}
}
