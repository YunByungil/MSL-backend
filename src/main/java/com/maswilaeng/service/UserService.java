package com.maswilaeng.service;

import com.maswilaeng.domain.entity.User;
import com.maswilaeng.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long join(User user){
        // 같은 닉네임의 중복 회원 X
        User result = userRepository.findByNickName(user.getNickName());

        if(result != null) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }

        userRepository.save(user);
        return user.getId();
    }


    public List<User> findUsers(){
        /**
         * 전체 회원 조회
         */
        return userRepository.findAll();
    }

    public Optional<User> findOne(Long userId){
        return userRepository.findById(userId);
    }


    public void deleteByUserId(Long id) {
        userRepository.deleteById(id);
    }
}
