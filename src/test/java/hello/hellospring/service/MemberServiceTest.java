package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.*;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService;// = new MemberService();
    MemoryMemberRepository memberRepository; //= new MemoryMemberRepository();
    //테스트의 new MemoryMemberRepository()는 MemberService클래스의 new MemoryMemberRepository()와 서로 다른 레포지토리(서로다른 인스턴스)
    //MemoryMemberRepository클래스에서 Map<Long, Member> store이 static이 아니면 바로 문제(db가 다른 db가 되니깐)
    //같은 repository를 쓰게 하려면(같은 인스턴스를 쓰게 하려면)
    //MemberService에서 MemoryMemberRepository memberRepository = new MemoryMemberRepository();를 수정

    @BeforeEach
    public void beforeEach(){
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }



    @AfterEach
    public void afterEach(){
        memberRepository.cleatStore();
    }


    @Test
    void join(){
        //상황이 주어졌을때 테스트 문법 패턴 given-when-then
        //given 뭔가 주어졌는데
        Member member = new Member();
        member.setName("hello");

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


    @Test
    void findMember(){

    }

    @Test
    void findOne(){

    }


}