package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

//@Controller를 붙이면 spring컨테이너에서 컨트롤러라고 객체를 생성해서 spring에 넣어두고 spring 빈이 관리함
//참고.스프링은 스프링 컨테이너에 스프링 빈을 등록할 때 기본으로 싱글톤 등록. 따라서 같은 스프링 빈이면 모두 같은 인스턴스
//(유일하게 하나만 등록하는 싱글톤 등록 예.memberRepository-memberService-memberController/ hostRepository-hostService-hostController등)
@Controller
public class MemberController {
    //등록을하고
    private final MemberService memberService;// = new MemberService();

    //@Autowired해서 생성한 memberService를 spring빈 안에 등록된 @Service와 @Repository의 memberService 객체와 서로 연결시켜줌
    //-> DI. 1.컴포넌트 스캔과 자동 의존관계 설정
    //3. 생성자 주입
    @Autowired
    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }
    //스프링 빈을 등록하는 두번째방법 2. 자바코드로 직접 스프링 빈 등록하기


    /*
    * DI에는 1.field주입, 2.setter주입, 3.생성자주입-> 의존관계가 실행(런타임)중에 동적으로 변하는 경우는 거의 없어서 생성자 주입을 권장
    //옛날에는 setter주입도 많이 썼는데 애플리케이션이 조립될때(스프링 컨테이너에 세팅되는 시점)생성자는 한번생성되고 변경 없이 막을 수 있지만, setter는 아무나 호출 가능해서
    //최근에는 생성자주입은 권장
    * 1. field주입: 필드에 고정이라 나중에 변경하기가 어려움->권장x
    * 생성자를 빼고 필드에 @Autowired
    * @Autowired private final MemberService memberService;
    *
     * 2. setter주입: setter는 public이라서 아무나 호출가능->권장x(필요한곳에 권한있는자가 호출하도록하는게 좋은 코드)
    * @Autowired
    * public void setMemberService(MemberService memberService){
    *     this.memberService = memberService;}
    * */


    //회원가입
    @GetMapping("/members/new")
    public String createForm(){
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(MemberForm form){
        Member member = new Member();
        member.setName(form.getName()); //form에서 getName으로 멤버 만들어지고

        System.out.println("member= "+member.getName());

        memberService.join(member); //join해서 멤버를 넘김

        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members); //model리스트에 담아서 넘김
        return "members/memberList";
    }



}
