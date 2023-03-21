## 기능 구현 목록
<hr>

- [x] 싫어요, 좋아요, 팔로우 개수 Body에 담아서 보내기
  - [ ] 팔로우 개수 아직 해결X
- [x] 유저 정보 조회할 때, N + 1 문제 해결하기
  - [x] batch_size로 해결 완료
- [x] 유저 정보 비회원도 허용할 수 있게 로직 수정하기
- [x] 게시판 상세정보 구현 - PostController#getPost()
  - [x] 쿼리 개선 PostDetailDto, CommentResponseDto 
  - post - user => fetchJoin, post - hashTag => fetchJoin (뻥튀기)
  - [x] 비회원, 회원 접근 여부 확인 후 likeState, 좋아요 개수, 댓글 개수, 대댓글 개수 추가하기
- [ ] `게시글 전체 목록 HashTag 추가하기!`
- [ ] 대댓글 불러오는 api 생성
- [ ] 팔로우, 팔로워 갖고오는 api 생성
- [ ] 로그아웃 로직 수정하기
- [ ] 회원 탈퇴 구현하기
- [ ] 게시글 검색 구현하기
- [x] 해쉬태그 저장 구현하기
  - [x] 해쉬태그 삭제
  - [x] 해쉬태그 수정
  - [ ] 성능 개선 문제가 많다. for문을 통한 삭제라 쿼리가 엄청 많이 나감
- [ ] 유저 페이지에서 그 유저가 좋아요 누른 글 api 생성
<hr>

## 회원가입에 필요한 기능 구현 목록
<hr>

- [x] 모든 회원 이메일, 닉네임, 전화번호 정보 받는 api 생성 (프론트 유효성 검사) - UserController#memberList()
<hr>

## 회원가입 예외 목록
<hr>

- [ ] 회원가입 눌렀을 때, 중복 검사 로직 추가



<hr>

UserDetailResponseDto를 보면 stream을 이용해서 size를 갖고 오는데  
CommentHate나, like등을 쿼리로 안 돌리고 stream을 통해서 가능할까?  

