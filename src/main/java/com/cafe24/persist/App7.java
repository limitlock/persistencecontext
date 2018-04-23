package com.cafe24.persist;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import com.cafe24.persist.domain.Member;

public class App7 {
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("persitencecontext");

	public static void main(String[] args) {
		Member member = createMember("user3", "회원3");
		// 준영속 상태에서 변경
		member.setName("도우넛");
		mergeMember(member);

	}

	private static Member createMember(String id, String name) {
		// 영속성 컨텍스트 시작
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();

		// Entity 생성, 비영속 상태 == new
		Member member = new Member();
		member.setId(id);
		member.setName(name);
		em.persist(member);

		// flush() 호출
		tx.commit();

		// 영속성 컨텍스트 종료
		// member 엔티티는 준영속 상태
		em.close();

		// 영속성 컨텍스트 종료

		return member;
	}

	public static void mergeMember(Member member) {
		// 영속성 컨텍스트 시작
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();

		// 영속 상태로 변경
		Member mergeMember = em.merge(member);

		// flush() 호출
		tx.commit();

		System.out.println("member=" + member.getName());
		System.out.println("mergeMember=" + mergeMember.getName());
		System.out.println("em contains member=" + em.contains(member));
		System.out.println("em contains mergeMember=" + em.contains(mergeMember));

		// 영속성 컨텍스트 종료
		em.close();

		// 영속성 컨텍스트 종료
	}

}
