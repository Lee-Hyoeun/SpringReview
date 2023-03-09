package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    //각 @Test가 끝날때 마다 @AfterEach 콜백. 클리어해주고 다음 테스트로 넘어간다
    @AfterEach
    public void afterEach(){
        repository.cleatStore();
    }

    @Test
    public void save() {
        Member member = new Member();
        member.setName("spring");

        repository.save(member);

        Member result = repository.findById(member.getId()).get();
        //테스트 방법1. 원초적인 println
        // System.out.println("result= "+(result==member));

        //방법2. Assertions(assertj.core.api), result대신 null값이다? 기대값과 다르니 에러확인 가능
        //Assertions.assertEquals(member, result);
        //Assertions.assertThat(member).isEqualTo(result);

        //방법3. static Assertions 임포트 import static org.assertj.core.api.Assertions.*;
        assertThat(member).isEqualTo(result);
    }

    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        //findByName 테스트
        Member result = repository.findByName("spring1").get();
        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);
    }
}
