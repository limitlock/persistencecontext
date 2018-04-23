package com.cafe24.persist;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.cafe24.persist.domain.Member;

public class App {
	public static void main(String[] args) {

		// 1. Entity Manager Factory 생성
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("persitencecontext"); // db

		// 2. Entity Manager 생성
		EntityManager em = emf.createEntityManager();

		// 3.Get TX
		EntityTransaction tx = em.getTransaction();

		// 4. TX Begins
		tx.begin();

		// 5. Business Logic
		try {

			// testInsert(em);
			// testFind01(em);
			// testFind02(em);
			testIdentity(em);
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		}

		// 6. TX Commit
		tx.commit();

		// 7. Entity Manager 종료
		em.clear();

		// 8. Entity Manager Factory 종료
		emf.close();

	}

	public static void testIdentity(EntityManager em) {

		Member a = em.find(Member.class, "member1");

		// 위의 구문에서 이미 'member1'을 가져와 캐쉬에 저장했기 때문에
		// 밑의 구문은 db조회가 아니라 1차 캐시 조회다
		Member b = em.find(Member.class, "member1");

		// 어디서 조회하든지 간에 동질성을 보장한다.
		System.out.println(a == b);

	}

	public static void testFind02(EntityManager em) {

		// DB에서 조회
		Member findMember = em.find(Member.class, "member1");
		System.out.println(findMember);

	}

	public static void testFind01(EntityManager em) {
		Member member = new Member();
		member.setId("member1");
		member.setName("회원1");

		// 1. 1차 캐시에 저장
		em.persist(member);

		// 2. 1차 캐시에서 조회
		Member findMember = em.find(Member.class, "member1");
		System.out.println(findMember);

	}

	public static void testInsert(EntityManager em) {
		Member member = new Member();
		member.setId("member1");
		member.setName("회원1");
		em.persist(member);
	}

}
