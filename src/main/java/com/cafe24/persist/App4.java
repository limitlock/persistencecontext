package com.cafe24.persist;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.cafe24.persist.domain.Member;

public class App4 {
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

		// 5. Business Logic
		try {
			// 삭제 대상 엔티티 조회
			Member memberA = em.find(Member.class, "memberA");

			// 엔티티 삭제
			em.remove(memberA);

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

}
