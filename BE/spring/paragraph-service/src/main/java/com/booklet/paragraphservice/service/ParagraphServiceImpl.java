package com.booklet.paragraphservice.service;

import com.booklet.paragraphservice.dto.*;
import com.booklet.paragraphservice.dto.paragraph.*;
import com.booklet.paragraphservice.entity.Book;
import com.booklet.paragraphservice.entity.Paragraph;
import com.booklet.paragraphservice.entity.User;
import com.booklet.paragraphservice.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ParagraphServiceImpl implements ParagraphService {
    private final ParagraphRepository paragraphRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final ScrapRepository scrapRepository;
    private final UserImageRepository userImageRepository;
    private final FollowRepository followRepository;


    @Transactional
    @Override
    public Long saveParagraph(ParagraphCreateReq req) { // 문장 id 리턴
        Long result = 0L;
        try {
            Paragraph paragraph = Paragraph.builder()
                    .paragraphContent(req.getParagraphContent())
                    .paragraphPage(req.getParagraphPage())
                    .paragraphColor(req.getParagraphColor())
                    .book(bookRepository.findById(req.getBookIsbn()).orElse(null))
                    .user(userRepository.findById(req.getUserId()).orElse(null))
                    .build();
            result = paragraphRepository.save(paragraph).getParagraphId();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }

    @Override
    public boolean isExist(Long paragraphId) {
        Paragraph paragraph = paragraphRepository.findById(paragraphId).orElse(null);
        if (paragraph == null) return false;
        return true;
    }

    @Override
    public Map<String, Object> findParagraph(Long paragraphId) { // 한개의 문장 상세 보기
        Paragraph paragraph = paragraphRepository.findById(paragraphId).orElse(null);
        if (paragraph == null) return null;
        Book book = paragraph.getBook();
        User user = paragraph.getUser();
        try {
            Map<String, Object> result = new HashMap<>();
            ModelMapper mapper = new ModelMapper();
            mapper.getConfiguration().setAmbiguityIgnored(true);
            // 문장 정보
            ParagraphDto paragraphDto = new ModelMapper().map(paragraph, ParagraphDto.class);
            // 책 정보
            BookDto bookDto = new ModelMapper().map(book, BookDto.class);
            // 작성자 정보
            UserDto userDto = new ModelMapper().map(user, UserDto.class);
            userDto.setUserImage(userImageRepository.findUserImageByUser(user));
            // 댓글 수
            int commentCnt = commentRepository.countByParagraphId(paragraphId);
            ParagraphScrapDto paragraphScrapDto = getParagraphScrapDto(paragraphId, paragraph, user);

            result.put("paragraph", paragraphDto);
            result.put("book", bookDto);
            result.put("user", userDto);
            result.put("commentCnt", commentCnt);
            result.put("scrapInfo", paragraphScrapDto);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public HashMap<String, Object> findParagraphs(User user, Pageable pageable) { // 내 문장 목록 조회
        try {
            Slice<Paragraph> paragraphs = paragraphRepository.findParagraphByUser(user, pageable);

            List<ParagraphListDto> listDto = paragraphs.getContent().stream()
                    .map(i -> new ParagraphListDto(i, getParagraphScrapDto(i.getParagraphId(), i, i.getUser()), commentRepository.countByParagraphId(i.getParagraphId())))
                    .collect(Collectors.toList());
            HashMap<String, Object> result = new HashMap<>();
            result.put("paragraphs", listDto);
            result.put("hasNextPage", paragraphs.hasNext());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public HashMap<String, Object> findFollowParagraph(User user, Pageable pageable) {

        try {
            // 1. 해당 user가 following한 user들의 paragraph
            Slice<Paragraph> paragraphs = paragraphRepository.findParagraphJoinFollow(user, pageable);
            // 2. scrap 정보, 댓글 수, 해당 user 정보
            List<ParagraphFollowListDto> listDto = new ArrayList<>();
            ModelMapper mapper = new ModelMapper();
            mapper.getConfiguration().setAmbiguityIgnored(true);
            // 문장 정보

            for (Paragraph p : paragraphs) {
                // 1. 해당 paragraph user Info
                UserDto userDto = new ModelMapper().map(p.getUser(), UserDto.class);
                userDto.setUserImage(userImageRepository.findUserImageByUser(p.getUser()));
                // 2. 해당 paragraph scrap 정보
                ParagraphScrapDto scrapInfo = getParagraphScrapDto(p.getParagraphId(), p, p.getUser());
                // 3. 해당 paragraph comment 수
                int commentCnt = commentRepository.countByParagraphId(p.getParagraphId());
                listDto.add(new ParagraphFollowListDto(userDto, p, scrapInfo, commentCnt));
            }
            HashMap<String, Object> result = new HashMap<>();
            result.put("paragraphs", listDto);
            result.put("hasNextPage", paragraphs.hasNext());
            return result;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Long updateParagraph(ParagraphSetReq req) {
        return null;
    }


    @Override
    public boolean deleteParagraph(Long paragraphId) { // 등록된 문장 삭제
        try {
            paragraphRepository.deleteById(paragraphId);
        } catch (Exception e) {
//            log.info("error : {}", e.getStackTrace());
            return false;
        }
        return true;
    }

    private ParagraphScrapDto getParagraphScrapDto(Long paragraphId, Paragraph paragraph, User user) {
        // 스크랩 정보
        ParagraphScrapDto scrapInfo;
        // 1. 스크랩한 사람들의 이미지 3개
        ArrayList<User> userList = scrapRepository.findTop3ScrapUserImages(paragraphId);
        ArrayList<String> userImageList = new ArrayList<>();
        for (User u : userList) {
            userImageList.add(userImageRepository.findUserImageByUser(u));
        }

        // 2. 스크랩 count
        int scrapCount = scrapRepository.countByParagraphId(paragraphId);
        // 3. 해당 유저가 스크랩을 했는지 안 헀는지...

        if (!scrapRepository.findByUserAndParagraph(user, paragraph).isPresent()) {
            scrapInfo = ParagraphScrapDto.builder()
                    .scrapUserImages(userImageList)
                    .scrapCount(scrapCount)
                    .userScrape(0)
                    .build();
        } else {
            scrapInfo = ParagraphScrapDto.builder()
                    .scrapUserImages(userImageList)
                    .scrapCount(scrapCount)
                    .userScrape(1)
                    .build();
        }
        return scrapInfo;
    }
}
