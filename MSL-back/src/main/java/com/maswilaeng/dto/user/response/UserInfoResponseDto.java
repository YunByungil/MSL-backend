package com.maswilaeng.dto.user.response;

import com.maswilaeng.domain.entity.Post;
import com.maswilaeng.domain.entity.PostLike;
import com.maswilaeng.domain.entity.User;
import com.maswilaeng.dto.post.response.PostResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class UserInfoResponseDto {

    private String email;

    private String nickName;

    private String userImage;

    private String introduction;

    private boolean followState;

    private int followerCount;

    private int followingCount;

    private List<PostResponseDto> postList;

    private List<PostResponseDto> likedPostList;

    public UserInfoResponseDto(User user, boolean isFollowed){
        this.email = user.getEmail();
        this.nickName = user.getNickName();
        this.userImage = user.getUserImage();
        this.introduction = user.getIntroduction();
        this.followState =  isFollowed;
        this.followingCount = user.getFollowingList().size();
        this.followerCount = user.getFollowerList().size();
        this.likedPostList = user.getPostLikeList().stream()
                .map(postLike -> postLike.getPost())
                .map(PostResponseDto::new)
                .collect(Collectors.toList());
        this.postList = user.getPostList().stream()
                .map(PostResponseDto::new)
                .collect(Collectors.toList());
    }

    public UserInfoResponseDto (User user) {
        this.email = user.getEmail();
        this.nickName = user.getNickName();
        this.userImage = user.getUserImage();
        this.introduction = user.getIntroduction();
        this.followState =  false;
        this.followingCount = user.getFollowingList().size();
        this.followerCount = user.getFollowerList().size();
        this.likedPostList = user.getPostLikeList().stream()
                .map(postLike -> postLike.getPost())
                .map(PostResponseDto::new)
                .collect(Collectors.toList());
        this.postList = user.getPostList().stream()
                .map(PostResponseDto::new)
                .collect(Collectors.toList());
    }
}
