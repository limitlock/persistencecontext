package com.cafe24.persist;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import com.cafe24.persist.domain.Member;

public class App6 {
	public static void main(String[] args) {

		// 1. Entity Manager Factory 생성
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("persitencecontext"); // db

		// 2. Entity Manager 생성
		EntityManager em = emf.createEntityManager();

		// 3.Get TX
		EntityTransaction tx = em.getTransaction();

		// 4. TX Begins
		// 엔티티 매니저는 데이터 변경 시 트랜잭션을 시작하여야 한다.
		tx.begin(); // [트랜잭션] 시작

		// 5. Business Logic (준영속 테스트)
		try {
			testDetached(em);
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		}

		// 6. TX Commit
		// 커밋되는 순간 데이터베이스에 delete SQL를 보낸다.
		tx.commit(); // [트랜잭션] 커밋

		// 7. Entity Manager 종료
		em.clear();

		// 8. Entity Manager Factory 종료
		emf.close();

	}

	public static void testDetached(EntityManager em) {
		// Entity 생성, 비영속 상태 == new
		Member user3 = new Member();
		user3.setId("user3");
		user3.setName("사용자3");

		// 영속 상태 == persist
		em.persist(user3);

		// 회원 엔티티를 영속성 컨텍스트에서 분리, 준영속성 == detach
		em.detach(user3);
		System.out.println(user3);

		// update가 안됨, why? 준영속 상태이기 때문에
		// 준영속 상태이기 때문에 DB에 커밋되지 않는다.
		user3.setName("사용자4444");
		System.out.println(user3);

	}

	public static void testClear(EntityManager em) {
		// 엔티티 조회, 영속상태
		Member member = em.find(Member.class, "member1");

		// 영속성 컨텍스트 전체를 초기화
		em.clear();

		// 준영속 상태
	}

}
