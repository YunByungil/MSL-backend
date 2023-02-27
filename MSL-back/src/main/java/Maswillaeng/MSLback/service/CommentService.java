package Maswillaeng.MSLback.service;

import Maswillaeng.MSLback.domain.entity.Comment;
import Maswillaeng.MSLback.domain.repository.CommentRepository;
import Maswillaeng.MSLback.domain.repository.PostRepository;
import Maswillaeng.MSLback.domain.repository.UserRepository;
import Maswillaeng.MSLback.dto.comment.request.CommentRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class CommentService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;


    public void saveComment(CommentRequestDto requestDto){

    }

    public void saveReply(){

    }

    public void getCommentList(){

    }

    public void updateComment(){

    }
    public void deleteComment(){

    }
}
