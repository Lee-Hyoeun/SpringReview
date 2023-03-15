package hello.hellospring;

import hello.hellospring.repository.JdbcMemberRepository;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

//자바코드로 직접 스프링 빈 등록하기
//멤버서비스와 멤버리포짓의 @Service, @Repository, @Autowired 애노테이션을 제거후에 SpringConfig 자바코드 작성
@Configuration
public class SpringConfig {

    private DataSource dataSource;

    @Autowired
    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    // SpringConfig가 뜰때 멤버서비스랑 멤버리포짓을 스프링빈에 등록하고 스프링빈에 등록된 멤버리포짓을 멤버서비스에 넣어줌
    //멤버컨트롤러는 어쨌든 스프링이 관리해야하기 때문에 @Controller로 컴포넌트스캔으로 올라감 그리고 @Autowired로 자동설정
    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository(){
//        return new MemoryMemberRepository();

        //메모리가 아닌 jdbc레포짓으로 =>이게바로 스프링을 쓰는 이유!
        //객체지향적인 설계. 다형성. 인페두고 구현체 바꿔끼기. 스프링이 이게 가능하도록 스프링컨테이너가 지원을 해줌
        //이를 SOLID원칙중 개방폐쇄 원칙이라고 한다(OCP:확장에는 열려있고 수정,변경에는 닫혀있다.)
        return new JdbcMemberRepository(dataSource);
    }

    // 위 내용은 @Service한줄로 스프링빈 정리 가능,, 간결하지만 그래도 자바코드로 직접 스프링빈 등록할 줄 알기
    // 실무에서는
    //주로 정형화된 컨트롤러, 서비스, 리포짓 같은 코드는 컴포넌트 스캔을 사용하고,
    //정형화x, 상황에 따라 구현클래스를 변경해야 하면 설정을 통해 스프링 빈으로 등록한다.
    // 우리 예시의 상황은 아직 데이터 저장소가 선정되지 않아서 메모리로 만들고 나중에 교체할 예정
    //그래서 지금까지 인터페이스로 설계를 하고 MemoryMemberRepository로 구현체를 만드는 과정을 한것!
    //이제 MemberyMemberRepository를 다른 레포짓으로 바꿔치기하려고 함. 단, 기존의 코드를 하나도 건드리지 않고!
    //이를 SpringConfig에서 한다.

}
