package com.booklet.bookservice.service;

import com.booklet.bookservice.dto.BookDetailRes;
import com.booklet.bookservice.dto.BookDto;
import com.booklet.bookservice.dto.BookSearchRes;
import com.booklet.bookservice.entity.*;
import com.booklet.bookservice.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class BookServiceImpl implements BookService{
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final BookLikesRepository bookLikesRepository;
    private final UserImageRepository userImageRepository;
    private final BookGenreRepository bookGenreRepository;

    @Override
    public Book findBook(String bookIsbn){
        Book book = bookRepository.findById(bookIsbn).orElseGet(Book::new);
        return book;
    }

    @Override
    public HashMap<String, Object> searchBooks(String title, Pageable pageable) {
        HashMap<String, Object> result = new HashMap<>();
        List<BookSearchRes> list =new ArrayList<>();
        Slice<Book> books = bookRepository.findByBookTitleContaining(title,pageable);
        System.out.println(books.getContent());
        list = books.getContent().stream().map(i->new BookSearchRes(i.getBookTitle(), i.getBookIsbn(), i.getAuthor().getAuthorName(), i.getBookImage())).collect(toList());
        result.put("bookList", list);
        result.put("hasNext", books.hasNext());
        return result;
    }

    @Override
    public BookDetailRes findBookDetail(String bookIsbn, Long userId) {
        Book book = bookRepository.findById(bookIsbn).orElseGet(Book::new);
        User user = userRepository.findById(userId).orElseGet(User::new);
        Map<String, Object> result = new HashMap<>();
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setAmbiguityIgnored(true);
        // 도서 정보
        BookDetailRes bookInfo = new ModelMapper().map(book, BookDetailRes.class);
        List<String> genres = bookGenreRepository.findBookGenreNameByBook(book);
        bookInfo.setGenreNames(genres);
        // 저자
        Author author = book.getAuthor();
        bookInfo.setAuthorId(author.getAuthorId());
        bookInfo.setAuthorName(book.getAuthor().getAuthorName());
        // author의 다른 책 5권
        bookInfo.setAuthorOtherBooks(bookRepository.findBooksByAuthor(book.getBookIsbn(), book.getAuthor(), PageRequest.of(0,5))); // 임시
        // 회원이 책을 좋아하는지 여부
        BookLikes bookLikes = bookLikesRepository.findByUserIdAndParagraphId(userId, bookIsbn).orElseGet(BookLikes::new);
        if(bookLikes.getBookLikeId()!=null) bookInfo.setBookLike(true);
        else bookInfo.setBookLike(false);
        // 책의 좋아요 수
        bookInfo.setLikesNumber(bookLikesRepository.countBookLikesByBook(book));
        // 책을 좋아요하는 회원들 사진 3개
        List<User> users = bookLikesRepository.findTop3BookLikeUser(bookIsbn, PageRequest.of(0,3));
        ArrayList<String> userImageList = new ArrayList<>();
        for (User u : users) {
            userImageList.add(userImageRepository.findUserImageByUser(u));
        }
        bookInfo.setLikesUserImages(userImageList);
        return bookInfo;
    }
}
