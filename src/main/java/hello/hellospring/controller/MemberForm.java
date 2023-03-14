package hello.hellospring.controller;

public class MemberForm {
    private String name; //createMemberForm.html의 name이랑 매칭되서 컨트롤러에 들어옴

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
