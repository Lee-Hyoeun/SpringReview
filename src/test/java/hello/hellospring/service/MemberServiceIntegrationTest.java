package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

// 스프링 통합 테스트 (JDBC와 연결하여 통합테스트. 보통은 test를 위한 db를 따로 구축하여 테스트한다.)
@SpringBootTest
@Transactional
class MemberServiceIntegrationTest {

//    MemberService memberService;
//    MemoryMemberRepository memberRepository;

//    @BeforeEach
//    public void beforeEach(){
//        memberRepository = new MemoryMemberRepository();
//        memberService = new MemberService(memberRepository);
//    }
    //위보다
    //스프링 컨테이너한테 멤버리포짓을 받기 => constructure injection
    //근데 테스트 injection은 그냥 @Autowired하는게 편함

    @Autowired    MemberService memberService;
    @Autowired    MemberRepository memberRepository;

    //메모리리포짓 영향 안받게 clear했던 아래코드도 지우기->@Trancsactionl 대체
//    @AfterEach
//    public void afterEach(){
//        memberRepository.clearStore();
//    }

    @Test
    void 회원가입(){
        //상황이 주어졌을때 테스트 문법 패턴 given-when-then
        //given 뭔가 주어졌는데
        Member member = new Member();
        member.setName("spring");

        //when 이걸 실행하면
        Long saveId = memberService.join(member);

        //then 결과는 이렇게 나와야 한다
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    //예외의 경우까지 테스트
    @Test
    public void 중복_회원_예외(){
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        //try-catch문 가능 추천은x
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, ()->memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

//        try {
//            memberService.join(member2);
//            fail();
//        } catch (IllegalStateException e){
//            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
//        }

        //then
    }
//
    //단위테스트(통합테스트보다 단위테스트위주로 테스트진행할 수 있도록)
//    @Test
//    void findMember(){
//    }
//
//    @Test
//    void findOne(){
//    }

}