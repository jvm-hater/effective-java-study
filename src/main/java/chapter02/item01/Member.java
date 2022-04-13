package chapter02.item01;

public class Member {

    private String name;

    private int age;

    private String hobby;

    private MemberStatus memberStatus;

    public Member(String name, int age, String hobby, MemberStatus memberStatus) {
        this.name = name;
        this.age = age;
        this.hobby = hobby;
        this.memberStatus = memberStatus;
    }

    public static Member basicMember(String name, int age, String hobby) {
        return new Member(name, age, hobby, MemberStatus.BASIC);
    }

    public static Member mediumMember(String name, int age, String hobby) {
        return new Member(name, age, hobby, MemberStatus.INTERMEDIATE);
    }

    public static Member advancedMember(String name, int age, String hobby) {
        return new Member(name, age, hobby, MemberStatus.ADVANCED);
    }

}
