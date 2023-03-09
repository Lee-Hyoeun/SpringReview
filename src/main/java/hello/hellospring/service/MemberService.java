package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

//참고. 서비스클래스는 네이밍이 비즈니스용어에 가까움. 기획자와 회의할 때 매핑하기 용이해서
//(예. 기획자:회원가입로직이 이상해요, 개발자:(join메소드 바로 확인)
//반대로 repository같은 경우 예. findByName, findById..


public class MemberService {

    private final MemberRepository memberRepository = new MemoryMemberRepository() {
        /**
         * 회원 가입
         */
        public Long join(Member member) {
            //같은 이름이 있는 중복 회원은 가입 안됨
            // 1번.Optional이 붙어서 Optional안에 멤버 객체가 있음. 만약 값이 있으면 throw
            //Optional을 바로 반환하는 것은 좋지x 2번처럼 ifPresent를 붙여서 쓸수 있다.
//            Optional<Member> result = memberRepository.findByName(member.getName());
//            result.ifPresent(m -> {
//                throw  new IllegalStateException("이미 존재하는 회원입니다.");
//            });
            //값을 꺼내기 orElseGet('디폴트값') (get()은 권장x(무작정꺼내기라서))
            //result.orElseGet();

            // 2. 아래같은 findByName에 관한 로직은 메서드로 뽑는것이 좋음(Extracts Method 드래그 +단축키 ctrl+t)
            // Optional<Member> result = memberRepository.findByName(member.getName());
            //      result.ifPresent(m -> {
            //          throw  new IllegalStateException("이미 존재하는 회원입니다.");
            //      });

            // 3.Extracts Method로 Optional<Member>를 validateDuplicateMember메소드로 뽑아냄
            validateDuplicateMember(member); //중복 회원 검증

            memberRepository.save(member);
            return member.getId();


        }

        private void validateDuplicateMember(Member member) {
            memberRepository.findByName(member.getName())
                    .ifPresent(m -> {
                        throw new IllegalStateException("이미 존재하는 회원입니다.");
                    });
        }


         /**
          * 전체 회원 조회
          */
         public List<Member> findMembers(){
             return memberRepository.findAll();
         }


         public Optional<Member> findOne(Long memberId) {
             return memberRepository.findById(memberId);
         }


    };
}
