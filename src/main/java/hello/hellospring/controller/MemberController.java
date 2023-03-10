package hello.hellospring.controller;

import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

//@Controller를 붙이면 spring컨테이너에서 컨트롤러라고 객체를 생성해서 spring에 넣어두고 spring 빈이 관리함
//참고.스프링은 스프링 컨테이너에 스프링 빈을 등록할 때 기본으로 싱글톤 등록. 따라서 같은 스프링 빈이면 모두 같은 인스턴스
//(유일하게 하나만 등록하는 싱글톤 등록 예.memberRepository-memberService-memberController/ hostRepository-hostService-hostController등)
@Controller
public class MemberController {
    //등록을하고
    private final MemberService memberService;// = new MemberService();

    //@Autowired해서 생성한 memberService를 spring빈 안에 등록된 @Service와 @Repository의 memberService 객체와 서로 연결시켜줌
    //-> DI. 1.컴포넌트 스캔과 자동 의존관계 설정
    @Autowired
    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }
    //스프링 빈을 등록하는 두번째방법 2. 자바코드로 직접 스프링 빈 등록하기


}
